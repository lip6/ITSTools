package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.util.MatrixCol;

public class SparsePetriNet extends PetriNet {
	private List<Integer> marks=new ArrayList<>();
	private MatrixCol flowPT = new MatrixCol(0,0);
	private MatrixCol flowTP = new MatrixCol(0,0);
	private List<String> tnames=new ArrayList<>();
	private List<String> pnames=new ArrayList<>();
	private int maxArcValue=0;
	private BitSet untouchable=new BitSet();
	
	public int addTransition (String tname) {
		flowPT.appendColumn(new SparseIntArray());
		flowTP.appendColumn(new SparseIntArray());
		tnames.add(tname);
		return tnames.size()-1;
	}
	
	public int addPlace (String pname, int init) {
		flowPT.addRow();
		flowTP.addRow();
		pnames.add(pname);
		marks.add(init);
		return pnames.size()-1;
	}

	public void addPreArc (int p, int t, int val) {
		flowPT.getColumn(t).put(p,val);
		maxArcValue = Math.max(maxArcValue, val);
	}

	public void addPostArc (int p, int t, int val) {
		flowTP.getColumn(t).put(p,val);		
		maxArcValue = Math.max(maxArcValue, val);
	}

	@Override
	public int getTransitionCount() {
		return tnames.size();
	}
	
	@Override
	public int getPlaceCount() {
		return pnames.size();
	}
	
	public List<String> getTnames() {
		return tnames;
	}
	
	public List<String> getPnames() {
		return pnames;
	}

	@Override
	public int getTransitionIndex(String name) {
		return tnames.indexOf(name);
	}

	@Override
	public int getPlaceIndex(String name) {
		return pnames.indexOf(name);
	}

	public void toPredicates() {
		for (Property prop : getProperties()) {
			prop.setBody(replacePredicates(prop.getBody()));
		}
	}

	private Expression replacePredicates(Expression expr) {
		if (expr == null) {
			return expr;
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			Expression l = replacePredicates(bin.left);
			Expression r = replacePredicates(bin.right);
			if (l == bin.left && r == bin.right) {
				return expr;
			}
			return Expression.op(bin.op, l, r);
		} else if (expr instanceof NaryOp) {
			NaryOp nop = (NaryOp) expr;
			List<Expression> resc = new ArrayList<>();
			if (expr.getOp() == Op.CARD || expr.getOp() == Op.BOUND) {
				for (Expression child : nop.getChildren()) {
					if (child.getOp() == Op.PLACEREF) {
						resc.add(Expression.var(child.getValue()));
					} else {
						resc.add(child);
					}
				}
				return Expression.nop(Op.ADD, resc);
			} else if (expr.getOp() == Op.ENABLED) {
				Set<SparseIntArray> pres = new HashSet<>();
				int skipped = 0;
				for (Expression child : nop.getChildren()) {
					if (child.getOp() == Op.TRANSREF) {
						int tid = child.getValue();
						if (!pres.add(flowPT.getColumn(tid))) {
							skipped++;
						}
					}					
				}
				if (skipped > 0) {
					Logger.getLogger("fr.lip6.move.gal").info("Reduced "+skipped+" identical enabling conditions.");
				}
				for (SparseIntArray pre : pres) {
					List<Expression> conds = new ArrayList<>();
					for (int i=0,ie=pre.size();i<ie;i++) {
						conds.add(Expression.op(Op.GEQ, Expression.var(pre.keyAt(i)), Expression.constant(pre.valueAt(i))));
					}
					resc.add(Expression.nop(Op.AND, conds));
				}
				return Expression.nop(Op.OR, resc);				
			} else {
				boolean changed = false;
				for (Expression child : nop.getChildren()) {
					Expression nc = replacePredicates(child);
					resc.add(nc);
					if (nc != child) {
						changed = true;
					}
				}
				if (!changed) {
					return expr;
				}
				return Expression.nop(nop.getOp(), resc);
			}
		} 
		return expr;
	}

	public void testInInitial () {
		SparseIntArray spinit = new SparseIntArray(marks);
		int proved = 0;
		for (Property prop : getProperties()) {
			if (prop.getType() == PropertyType.INVARIANT && prop.getBody().getOp() != Op.BOOLCONST) {
				try {
					int val = ((BinOp)prop.getBody()).left.eval(spinit);
					if (prop.getBody().getOp() == Op.EF && val==1) {
						prop.setBody(Expression.constant(true));
						proved++;
					} else if (prop.getBody().getOp() == Op.AG && val==0) {
						prop.setBody(Expression.constant(false));
						proved++;
					}
				} catch (UnsupportedOperationException e) {}
			}
		}
		if (proved > 0) {
			Logger.getLogger("fr.lip6.move.gal").info("Initial state test concluded for "+proved+ " properties.");
		}
	}
	
	public void simplifyLogic() {
		for (Property prop : getProperties()) {
			prop.setBody(pushNegation(prop.getBody(),false));
			prop.setBody(simplifyBoolean(prop.getBody(),new HashSet<>(),new HashSet<>(),new HashSet<>()));
		}
		
	}

	private Expression simplifyBoolean(Expression expr, Set<Expression> seenPos, Set<Expression> seenNeg, Set<Expression> dominant) {
		if (expr == null) {
			return expr;
		}
		switch (expr.getOp()) {
		case CARD: case ENABLED: case BOUND:
		case GEQ: case GT : case EQ : case NEQ : case LT : case LEQ :
		{
			if (seenPos.contains(expr)) {
				return Expression.constant(true);
			} else if (seenNeg.contains(expr)) {
				return Expression.constant(false);
			} else {
				if (expr instanceof BinOp) {
					BinOp cmp = (BinOp) expr;
					if (cmp.left.equals(cmp.right)) {
						switch (expr.getOp()) {
						case GEQ: case EQ : case LEQ : 
						{
							return Expression.constant(true);
						}
						case GT : case NEQ : case LT :
						{
							return Expression.constant(false);
						}
						default :
							break;
						}
					}
				}
				return expr;
				
			}
		}
		case NOT:
		{
			Expression l = ((BinOp) expr).left;
			if (seenPos.contains(l)) {
				return Expression.constant(false);
			} else if (seenNeg.contains(l)) {
				return Expression.constant(true);
			} else {
				return expr;
			}
		}	
		case BOOLCONST:
			return expr;
		default :
			break;
		}
		if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			Expression l = simplifyBoolean(bin.left,new HashSet<>(),new HashSet<>(),new HashSet<>()); 
			Expression r = simplifyBoolean(bin.right,new HashSet<>(),new HashSet<>(),new HashSet<>());
			if (l == bin.left && r == bin.right) {
				return expr;
			}
			return Expression.op(bin.op, l, r);			
		} else if (expr instanceof NaryOp) {
			NaryOp nop = (NaryOp) expr;
			List<Expression> resc = new ArrayList<>();
						
			boolean changed = false;
			if (nop.getOp() == Op.AND) {
				List<Expression> tovisit = new ArrayList<>();				
				Set<Expression> nseenPos = new HashSet<>(seenPos);
				Set<Expression> nseenNeg = new HashSet<>(seenNeg);
				for (Expression child : nop.getChildren()) {
					if (child.getOp() == Op.NOT) {
						Expression e = ((BinOp)child).left;
						if (nseenNeg.contains(e)) {
							continue;
						} else if (nseenPos.contains(e)) {
							return Expression.constant(false);
						} else {
							nseenNeg.add(e);
							resc.add(child);
						}						
					} else if (child.getOp() == Op.OR) {
						tovisit.add(child);
					} else if (child.getOp() == Op.BOOLCONST) {
						if (child.getValue() == 1) {
							continue;
						} else {
							return Expression.constant(false);
						}						
					} else {
						if (nseenPos.contains(child)) {
							continue;
						} else if (nseenNeg.contains(child)) {
							return Expression.constant(false);
						} else {
							nseenPos.add(child);
							resc.add(child);
						}						
					}
				}
				for (Expression e : tovisit) {					
					Expression img = simplifyBoolean(e, nseenPos, nseenNeg, dominant);
					if (img.getOp() == Op.BOOLCONST) {
						if (img.getValue() == 1) {
							continue;
						} else {
							return Expression.constant(false);
						}
					}
					resc.add(img);
				}
				return Expression.nop(nop.getOp(), resc);
			} else if (nop.getOp() == Op.OR) {
				List<Expression> tovisit = new ArrayList<>();				
				Set<Expression> ndominant = new HashSet<>(dominant);
				Set<Expression> nseenPos = new HashSet<>();
				Set<Expression> nseenNeg = new HashSet<>();
				for (Expression child : nop.getChildren()) {
					if (child.getOp() == Op.NOT) {
						Expression e = ((BinOp)child).left;
						if (seenNeg.contains(e)) {
							return Expression.constant(true);
						} else if (seenPos.contains(e)) {
							continue;
						} else if (nseenPos.contains(e)){
							return Expression.constant(true);
						} else {
							if (nseenNeg.add(e)) {
								ndominant.add(child);							
								resc.add(child);
							}
						}						
					} else if (child.getOp() == Op.AND) {
						tovisit.add(child);
					} else if (child.getOp() == Op.BOOLCONST) {
						if (child.getValue() == 0) {
							continue;
						} else {
							return Expression.constant(true);
						}						
					} else {
						if (seenPos.contains(child)) {
							return Expression.constant(true);
						} else if (seenNeg.contains(child)) {
							continue;
						} else if (nseenNeg.contains(child)){
							return Expression.constant(true);
						} else {
							if (nseenPos.add(child)) {						
								ndominant.add(child);
								resc.add(child);
							}
						}						
					}
				}
				for (Expression e : tovisit) {					
					Expression img = simplifyBoolean(e, seenPos, seenNeg, ndominant);
					if (img.getOp() == Op.BOOLCONST) {
						if (img.getValue() == 0) {
							continue;
						} else {
							return Expression.constant(true);
						}
					} else if (img.getOp() == Op.NOT) {
						if (! nseenNeg.add(((BinOp)img).left)) {
							continue;
						}
					} else {
						if (! nseenPos.add(img)) {
							continue;
						}
					}
					resc.add(img);
				}
				return Expression.nop(nop.getOp(), resc);
			}
			return Expression.nop(nop.getOp(), resc);
		} 
		return expr;
		
	}

	private Expression pushNegation(Expression expr, boolean isNeg) {
		if (expr == null) {
			return expr;
		} else if (expr.getOp() == Op.BOOLCONST) {
			if (!isNeg) {
				return expr;
			} else {
				return Expression.constant(expr.getValue()==0);
			}		
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			switch (bin.getOp()) {
			case GEQ: case GT: case LEQ: case LT: case EQ: case NEQ:
				if (isNeg) {
					return Expression.not(bin);
				} else {
					return bin;
				}
			default : 
				break;
			}
			if (bin.getOp() == Op.NOT) {
				return pushNegation(bin.left,!isNeg);
			}
			Expression l = pushNegation(bin.left,false);
			Expression r = pushNegation(bin.right,false);
			if (l == bin.left && r == bin.right) {
				if (!isNeg) {
					return expr;
				} else {
					return Expression.not(expr);
				}
			}
			if (!isNeg) {
				return Expression.op(bin.op, l, r);
			} else {
				return Expression.not(Expression.op(bin.op, l, r));
			}
		} else if (expr instanceof NaryOp) {
			NaryOp nop = (NaryOp) expr;
			List<Expression> resc = new ArrayList<>();
			
			switch (nop.getOp()) {
			case CARD: case ENABLED: case BOUND:
			{
				if (!isNeg)
					return expr;
				else 
					return Expression.not(expr);
			}
			default:
				break;
			}
			
			boolean changed = false;
			for (Expression child : nop.getChildren()) {
				Expression nc = pushNegation(child,isNeg);
				resc.add(nc);
				if (nc != child) {
					changed = true;
				}
			}
			if (!changed) {
				if (!isNeg)
					return expr;
				else 
					return Expression.not(expr);
			}
			if (isNeg) {
				if (nop.getOp() == Op.OR) {
					return Expression.nop(Op.AND, resc);
				} else if (nop.getOp() == Op.AND) {
					return Expression.nop(Op.OR, resc);
				} else {
					return Expression.not(Expression.nop(nop.getOp(), resc));
				}
			}
			return Expression.nop(nop.getOp(), resc);
		} 
		return expr;
	}

}
