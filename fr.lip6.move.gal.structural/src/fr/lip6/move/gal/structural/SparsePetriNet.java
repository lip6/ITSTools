package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import android.util.SparseIntArray;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.VarRef;
import fr.lip6.move.gal.util.MatrixCol;

public class SparsePetriNet extends PetriNet {
	private List<Integer> marks=new ArrayList<>();
	private MatrixCol flowPT = new MatrixCol(0,0);
	private MatrixCol flowTP = new MatrixCol(0,0);
	private List<String> tnames=new ArrayList<>();
	private List<String> pnames=new ArrayList<>();
	private int maxArcValue=0;
	private static final int DEBUG = 0;
	
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
	
	public MatrixCol getFlowPT() {
		return flowPT;
	}
	public MatrixCol getFlowTP() {
		return flowTP;
	}
	public int getMaxArcValue() {
		return maxArcValue;
	}

	public List<Integer> getMarks() {
		return marks;
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
			if (bin.getOp() == Op.DEAD) {
				return Expression.not(Expression.op(Op.EX, Expression.constant(true), null));
			}
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

	private Expression simplifyConstants(Expression expr, int[] perm) {
		if (expr == null) {
			return null;
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			Expression l = simplifyConstants(bin.left, perm);
			Expression r = simplifyConstants(bin.right, perm);
			if (l != bin.left || r != bin.right) {
				return Expression.op(bin.op, l, r);
			} else {
				return expr;
			}
		} else if (expr instanceof NaryOp) {
			NaryOp nop = (NaryOp) expr;
			List<Expression> resc = new ArrayList<>(nop.getChildren().size());
			boolean changed = false;
			for (Expression child : nop.getChildren()) {
				Expression e = simplifyConstants(child, perm);
				resc.add(e);
				if (e != child) {
					changed = true;
				}
			}
			if (! changed) {
				return expr;
			} else {
				return Expression.nop(nop.getOp(), resc);
			}			
		} else if (expr instanceof VarRef) {
			VarRef vref = (VarRef) expr;
			int img = perm[vref.getValue()];
			if (img == -1) {
				return Expression.constant(marks.get(vref.getValue()));
			} else if (img == vref.getValue()) {
				return expr;
			} else {
				return Expression.var(img);
			}
		}
		return expr;
	}

	public int removeConstantPlaces() {
		int totalp = 0;
		// find constant marking places
		MatrixCol tflowPT = flowPT.transpose();
		MatrixCol tflowTP = flowTP.transpose();
		// reverse ordered set of tindexes to kill
		Set<Integer> todelTrans = new TreeSet<>((x,y) -> -Integer.compare(x, y));

		int [] perm = new int [tflowPT.getColumnCount()];
		int index = 0;
		List<String> prem = new ArrayList<>();
		List<String> trem = new ArrayList<>();
		Set<Integer> syphon = SiphonComputer.computeEmptySyphon(flowPT,flowTP,marks);
		// now scan for isomorphic/redundant/useless/constant places
		for (int pid = 0 , pe = pnames.size() ; pid < pe ; pid++) {
			
			SparseIntArray from = tflowPT.getColumn(pid);
			SparseIntArray to = tflowTP.getColumn(pid);
			if (syphon.contains(pid) || from.equals(to) || (to.size()==0 && marks.get(pid)==0)) {
				// constant marking place
				int m = marks.get(pid);
				for (int tpos = 0 ; tpos  < from.size() ; tpos++) {
					int taken = from.valueAt(tpos);
					if (taken <= m) {
						// always ok
						// deleting the line for p will be ok
					} else {
						// always disabled
						// delete t as well
						todelTrans.add(from.keyAt(tpos));
					}
				}
				perm[pid] = -1;
				totalp++;
			} else {
				perm[pid] = index++;
			}
		}
		if (totalp > 0) {
			for (Property prop : getProperties()) {
				prop.setBody(simplifyConstants(prop.getBody(),perm));
			}
			for (int pid = perm.length-1; pid >= 0 ; pid--) {
				if (perm[pid]==-1) {
					// delete line for p
					tflowPT.deleteColumn(pid);
					tflowTP.deleteColumn(pid);
					pnames.remove(pid);
					marks.remove(pid);
				}
			}
			// reconstruct updated flow matrices
			tflowPT.transposeTo(flowPT);
			tflowTP.transposeTo(flowTP);
		}
		if (! todelTrans.isEmpty()) {
			// delete transitions
			for (int tid : todelTrans) {
				flowPT.deleteColumn(tid);
				flowTP.deleteColumn(tid);
				trem.add(tnames.remove(tid));				
			}
		}
		if (!prem.isEmpty() || !trem.isEmpty())
			System.out.println("Reduce places removed "+totalp + " places and " + todelTrans.size() + " transitions. " + (DEBUG>=1 ? ("Places : " + prem + " Transitions:" + trem):""));

		return totalp;
	}
	
	public boolean rewriteConstantSums () {
		Map<Set<Integer>,Integer> constantSums = new HashMap<>();
		Set<Set<Integer>> varSums = new HashSet<>();
		for (int pind=0,pe=getProperties().size() ; pind < pe ; pind++) {
			Property p = getProperties().get(pind);
			p.setBody(findAndTestSums(p.getBody(),constantSums,varSums));
		}
		return ! constantSums.isEmpty();
	}
	
	private Expression findAndTestSums(Expression expr, Map<Set<Integer>,Integer> constantSums,
			Set<Set<Integer>> varSums) {
		if (expr == null) {
			return null;
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			Expression l = findAndTestSums(bin.left, constantSums, varSums);
			Expression r = findAndTestSums(bin.right, constantSums, varSums);
			if (l != bin.left || r != bin.right) {
				return Expression.op(bin.getOp(), l, r);
			} else {
				return expr;
			}
		} else if (expr instanceof NaryOp) {
			NaryOp nop = (NaryOp) expr;
			if (nop.getOp() == Op.ADD) {
				Set<Integer> vars = new HashSet<>();
				for (Expression child : nop.getChildren()) {
					if (child.getOp() == Op.PLACEREF) {
						vars.add(child.getValue());
					}
				}
				Integer val = constantSums.get(vars);
				if (val == null && varSums.contains(vars)) {
					return expr;
				}
				if (val == null) {
					boolean isCst = testIsConstantSum(vars);
					if (isCst) {
						int sumVal = 0;
						for (Integer pid : vars) {
							sumVal += marks.get(pid);
						}
						constantSums.put(vars, sumVal);
						val = sumVal;
					} else {
						varSums.add(vars);
					}
				}
				if (val != null) {
					List<Expression> resc = new ArrayList<>();
					for (Expression child : nop.getChildren()) {
						if (child.getOp() != Op.PLACEREF) {
							resc.add(child);
						}
					}
					resc.add(Expression.constant(val));
					return Expression.nop(nop.getOp(),resc);
				}
			} else {
				List<Expression> resc = new ArrayList<>();
				boolean changed = false;
				for (Expression child : nop.getChildren()) {
					Expression nc = findAndTestSums(child, constantSums, varSums);
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

	private boolean testIsConstantSum(Set<Integer> vars) {
		for (int tid=0,te=tnames.size(); tid < te ; tid++) {
			int taken = 0;
			SparseIntArray pt = flowPT.getColumn(tid);
			for (int i=0,ie=pt.size();i<ie;i++) {
				if (vars.contains(pt.keyAt(i))) {
					taken += pt.valueAt(i);
				}
			}
			int given = 0;
			SparseIntArray tp = flowTP.getColumn(tid);
			for (int i=0,ie=tp.size();i<ie;i++) {
				if (vars.contains(tp.keyAt(i))) {
					given += tp.valueAt(i);
				}
			}
			if (taken != given) {
				return false;
			}
		}
		return true;
	}

	public void simplifyLogic() {
		for (Property prop : getProperties()) {
			prop.setBody(pushNegation(prop.getBody(),false));
			prop.setBody(simplifyBoolean(prop.getBody(),new HashSet<>(),new HashSet<>(),new HashSet<>()));
		}
		rewriteConstantSums();
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
			return expr;
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

	public BitSet computeSupport() {
		BitSet supp = new BitSet();
		for (Property p : getProperties()) {
			addSupport(p.getBody(),supp);
		}
		return supp;
	}
	
	private static Void addSupport(Expression expr, BitSet supp) {
		if (expr == null) {
			return null;
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			bin.forEachChild(c -> addSupport(c, supp));			
		} else if (expr instanceof NaryOp) {
			NaryOp nop = (NaryOp) expr;
			nop.forEachChild(c -> addSupport(c, supp));
		} else if (expr instanceof VarRef) {
			supp.set(expr.getValue());
		} 
		return null;
	}

	public void readFrom(StructuralReduction sr) {
		this.flowPT = sr.getFlowPT();
		this.flowTP = sr.getFlowTP();
		this.marks = sr.getMarks();
		this.maxArcValue = sr.getMaxArcValue();
		this.tnames = sr.getTnames();
		int [] perm = new int [pnames.size()];
		Map<String,Integer> indexes = new HashMap<>();
		for (int i=0,ie=pnames.size(); i < ie; i++) {
			indexes.put(pnames.get(i), i);
		}
		Map<String,Integer> newindexes = new HashMap<>();
		for (int i=0,ie=sr.getPnames().size(); i < ie; i++) {
			newindexes.put(sr.getPnames().get(i), i);
		}
		for (int i=0,ie=pnames.size(); i < ie; i++) {
			Integer newind = newindexes.get(pnames.get(i));
			if (newind == null) {
				perm[i] = -1;
			} else {
				perm[i] = newind;
			}
		}
		this.pnames = sr.getPnames();
		for (Property prop : getProperties()) {
			prop.setBody(simplifyConstants(prop.getBody(), perm));
		}
	}

	public void removeRedundantTransitions(boolean andEmptyEffects) {
		int reduced = 0;
		if (andEmptyEffects) {
			// transitions with no effect => no use
			List<Integer> todrop = new ArrayList<>();
			for (int i = tnames.size()-1 ;  i >= 0 ; i--) {
				if (flowPT.getColumn(i).equals(flowTP.getColumn(i))) {
					todrop.add(i);
				} 
			}
			if (! todrop.isEmpty()) {
				System.out.println("Reduce no effect transitions removed "+ todrop.size() +" transitions.");
				reduced += todrop.size();
				for (int tid : todrop) {
					flowPT.deleteColumn(tid);
					flowTP.deleteColumn(tid);
					tnames.remove(tid);
				}
			}
		}
		reduced += ensureUnique(flowPT, flowTP, tnames); 
	
	}
	
	public Specification rebuildSpecification () {
		return SpecBuilder.buildSpec(flowPT, flowTP, pnames, tnames, marks);
	}
	
	private int ensureUnique(MatrixCol mPT, MatrixCol mTP, List<String> names) {
		Map<SparseIntArray, Map<SparseIntArray,Integer>> seen = new HashMap<>();
		List<Integer> todel = new ArrayList<>();
			
			// plain iteration order to collect decreasing tokill indexes
			for (int trid=mPT.getColumnCount()-1 ; trid >= 0 ; trid--) {
				SparseIntArray tcolPT = mPT.getColumn(trid);
				SparseIntArray tcolTP = mTP.getColumn(trid);
				Integer b = seen.computeIfAbsent(tcolPT, k -> new HashMap<>()).put(tcolTP, trid);
				if (b != null) {
					todel.add(trid);
				}
			}								
		List<String> rem = new ArrayList<>();
		for (int td : todel) {
			rem.add(names.remove(td));
			mPT.deleteColumn(td);
			mTP.deleteColumn(td);
		}
		if (!todel.isEmpty()) {
			System.out.println("Ensure Unique test removed "+ rem.size()+ " transitions" + (DEBUG>=1 ?" : "+ rem  : ""));
		}
		return todel.size();
	}

}
