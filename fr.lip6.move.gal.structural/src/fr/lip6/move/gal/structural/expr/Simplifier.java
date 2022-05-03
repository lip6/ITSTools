package fr.lip6.move.gal.structural.expr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;

public class Simplifier {

	
	/**
	 * Rewrite the expression so that NOT only bear on leaves (comparisons), never on a OR or AND.
	 * This helps recognize positive/negative literals in the formula and trigger more simplifications.
	 * @param expr an expression to push negations in
	 * @return a rewritten expression, that could be the same as the original.
	 */
	public static Expression pushNegation(Expression expr) {
		return pushNegation(expr, false);
	}
	
	/**
	 * Attempt to simplify the formula by using boolean equivalences.
	 * a && ( b || a ) => a
	 * !a && ( b || a ) => !a && b
	 * x cmp x => true or false depending on comparator
	 * a || ( a & b ) => a
	 * + boolean constants reductions.
	 * @param expr an expression to reduce
	 * @return a simplified expression, could be the same as the original.
	 */
	public static Expression simplifyBoolean(Expression expr) {
		return simplifyBoolean(expr,new HashSet<>(),new HashSet<>(),new HashSet<>());
	}
	
	private static Expression simplifyBoolean(Expression expr, Set<Expression> seenPos, Set<Expression> seenNeg, Set<Expression> dominant) {		
		if (expr == null) {
			return expr;
		}
		switch (expr.getOp()) {
		case CARD: case ENABLED: case BOUND:
		case GEQ: case GT : case EQ : case NEQ : case LT : case LEQ :
		case APREF:
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
				if (!dominant.isEmpty()) {
					for (Expression child : nop.getChildren()) {
						if (dominant.contains(child)) {
							return child;
						}
					}
				}
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
					if (img != e) {
						changed = true;
					}
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
						} else if (nseenNeg.contains(child)){
							return Expression.constant(true);
						} else if (seenNeg.contains(child)) {
							continue;
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

	
	/**
	 * Recursive helper for push negations.
	 * @param expr expression to rewrite
	 * @param isNeg current polarity, i.e. is the number of not nodes traversed %2 == 1
	 * @return a rewritten expression.
	 */
	private static Expression pushNegation(Expression expr, boolean isNeg) {
		if (expr == null) {
			return expr;
		} else if (expr.getOp() == Op.BOOLCONST) {
			// Boolean leaves
			if (!isNeg) {
				return expr;
			} else {
				return Expression.constant(expr.getValue()==0);
			}		
		} else if (Op.isComparison(expr.getOp())) {
			// Comparisons => push on comparison operator
			if (isNeg) {					
				return Expression.op(Op.negate(expr.getOp()),expr.childAt(0), expr.childAt(1));
			} else {
				return expr;
			}
		} else if (expr.getOp() == Op.ENABLED || expr.getOp()==Op.EU || expr.getOp()==Op.AU) {
			if (isNeg) {
				return Expression.not(expr);
			} else {
				return expr;
			}
		} else if (expr.getOp() == Op.NOT) {
			return pushNegation(expr.childAt(0),!isNeg);
		} else {
			// recurse with current polarity
			List<Expression> resc = new ArrayList<>(expr.nbChildren());
			boolean changed = false;
			for (int cid = 0, cide = expr.nbChildren() ; cid < cide ; cid++) {
				Expression child = expr.childAt(cid);
				Expression e = pushNegation(child,isNeg);
				resc.add(e);
				if (e != child) {
					changed = true;
				}
			}
			if (isNeg) {
				return Expression.nop(Op.negate(expr.getOp()), resc);
			} else if (!changed) {
				return expr;
			} else {
				return Expression.nop(expr.getOp(), resc);
			}
		}		
	}
	
	public static Expression assumeVarsPositive (Expression expr) {
		if (expr == null) {
			return null;
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			switch (bin.getOp()) {
			case GEQ:case GT:case LT:case LEQ:
			{
				// comparisons are our target
				Expression l = bin.left;
				Expression r = bin.right;
				
				if ((l.getOp() == Op.CONST && l.getValue() == 0 
							&& (r.getOp() == Op.PLACEREF || r.getOp() == Op.CARD || r.getOp() == Op.HLPLACEREF)
							&& bin.getOp() == Op.LEQ)
						||
						(r.getOp() == Op.CONST && r.getValue() == 0 
						&& (l.getOp() == Op.PLACEREF || l.getOp() == Op.CARD || l.getOp() == Op.HLPLACEREF)
						&& bin.getOp() == Op.GEQ) )
					return Expression.constant(true);
				
				if ((r.getOp() == Op.CONST && r.getValue() == 0 
						&& (l.getOp() == Op.PLACEREF || l.getOp() == Op.CARD || l.getOp() == Op.HLPLACEREF)
						&& bin.getOp() == Op.LT)
						||
						(l.getOp() == Op.CONST && l.getValue() == 0 
						&& (r.getOp() == Op.PLACEREF || r.getOp() == Op.CARD || r.getOp() == Op.HLPLACEREF)
						&& bin.getOp() == Op.GT) )
					return Expression.constant(false);
			}
			default : break;
			}
		}
		
		if (expr.nbChildren() == 0) {
			return expr;
		}
		
		List<Expression> resc = new ArrayList<>(expr.nbChildren());
		boolean changed = false;
		for (int cid = 0, cide = expr.nbChildren() ; cid < cide ; cid++) {
			Expression child = expr.childAt(cid);
			Expression e = assumeVarsPositive(child);
			resc.add(e);
			if (e != child) {
				changed = true;
			}
		}
		if (! changed) {
			return expr;
		} else {
			return Expression.nop(expr.getOp(), resc);
		}			
	}
	

	public static Expression assumeOnebounded(Expression expr) {
		if (expr == null) {
			return null;
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			switch (bin.getOp()) {
			case GEQ:case GT:case EQ:case NEQ:case LT:case LEQ:
			{
				// comparisons are our target
				Expression l = bin.left;
				Expression r = bin.right;
				
				// case 1 : variable vs constant
				if (l.getOp() == Op.PLACEREF && r.getOp() == Op.CONST
						|| l.getOp() == Op.CONST && r.getOp() == Op.PLACEREF ) {
					// try both possible values of x
					List<Expression> res= new ArrayList<>();
					Expression var = l.getOp()==Op.PLACEREF ? l : r;
					for (int val =0; val <= 1; val++) {
						Expression valExpr = Expression.constant(val);
						Expression eval = Expression.op(bin.getOp()
								, l.getOp()==Op.PLACEREF ? valExpr : l
								, r.getOp()==Op.PLACEREF ? valExpr : r);
						if (eval.eval(null)==1) {
							res.add(Expression.op(Op.EQ, var, valExpr));
						}
						// else : guard is false
					}
					return Expression.nop(Op.OR, res);				
				} else if (l.getOp() == Op.PLACEREF && r.getOp() == Op.PLACEREF){
					// case 2 : arbitrary variable vs variable (no add !)
					
					Op op = bin.getOp();
					// normalize
					switch (op) {
					case GEQ :
						l = bin.right;
						r = bin.left;
						op = Op.LEQ;
						break;
					case GT :
						l = bin.right;
						r = bin.left;
						op = Op.LT;
						break;
					default :
					}
					Expression res;
					// break into cases
					switch (op) {
					case EQ :
						// both 0 or both 1
						res = Expression.op(Op.AND,
								Expression.op(Op.EQ, l, Expression.constant(0)),
								Expression.op(Op.EQ, r, Expression.constant(0)));
						res = Expression.op( Op.OR , 
								Expression.op(Op.EQ, l, Expression.constant(1)),
								Expression.op(Op.EQ, r, Expression.constant(1)));
						break;
					case NEQ :
						// 01 or 10
						res = Expression.op(Op.AND,
								Expression.op(Op.EQ, l, Expression.constant(0)),
								Expression.op(Op.EQ, r, Expression.constant(1)));
						res = Expression.op( Op.OR , 
								Expression.op(Op.EQ, l, Expression.constant(1)),
								Expression.op(Op.EQ, r, Expression.constant(0)));
						break;
					case LT :
						// 01
						res = Expression.op(Op.AND,
								Expression.op(Op.EQ, l, Expression.constant(0)),
								Expression.op(Op.EQ, r, Expression.constant(1)));
						break;
					case LEQ :
						// 0* or 11 => r is 1 or l is 0 => 0* or *1
						res = Expression.op(Op.OR,
								Expression.op(Op.EQ, l, Expression.constant(0)),
								Expression.op(Op.EQ, r, Expression.constant(1)));
						break;	
					default :
						throw new RuntimeException("Unexpected comparison operator in conversion "+ expr);
					}
					return res;
				}
			}
			default :
				break;			
			}
			
			Expression l = assumeOnebounded(bin.left);
			Expression r = assumeOnebounded(bin.right);
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
				Expression e = assumeOnebounded(child);
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
		}
		return expr;
	}

	
	public static boolean allEnablingsAreNegated(Property p, SparsePetriNet spn) {
		SparseIntArray init = new SparseIntArray(spn.getMarks());
		switch (p.getType()) {
		case INVARIANT:
			return allEnablingsAreNegated(p.getBody(), false);
		case CTL:
		case LTL:
			return allEnablingsAreInitiallyFalse(p.getBody(), init, spn);
		default:
		// mostly CTL : we are scared of initial state test and SMT solver unreachable
		{
			// any AP that is testing enabling, if it returns true is suspect.
			// however false is ok.
			// we just give up currently
			return !containsEnablings(p.getBody());
		}
		}
	}
	
	private static boolean allEnablingsAreInitiallyFalse(Expression e, SparseIntArray init, SparsePetriNet spn) {
		if (e == null) {
			return true;
		} 
		if (e.getOp() == Op.ENABLED) {
			for (int cid = 0, cide = e.nbChildren() ; cid < cide ; cid++) {
				if (SparseIntArray.greaterOrEqual(init, spn.getFlowPT().getColumn(e.childAt(cid).getValue()))) {
					return false;
				}
			}
			return true;
		}
		if (Op.isComparison(e.getOp())) {
			return true;			
		}
		for (int cid = 0, cide = e.nbChildren() ; cid < cide ; cid++) {
			if (! allEnablingsAreInitiallyFalse(e.childAt(cid),init,spn)) {
				return false;
			}
		}
		return true;
	}

	private static boolean containsEnablings(Expression e) {
		if (e==null) {
			return false;
		}
		if (e.getOp() == Op.ENABLED) {
			return true;
		}
		if (Op.isComparison(e.getOp())) {
			return false;
		}
		for (int i=0,ie=e.nbChildren(); i < ie ; i++) {
			if (containsEnablings(e.childAt(i))) {
				return true;
			}
		}		
		return false;
	}

	private static boolean allEnablingsAreNegated (Expression e, boolean isNeg) {
		if (e == null) {
			return true;
		} 
		if (e.getOp() == Op.ENABLED) {
			return isNeg;
		}
		if (e.getOp() == Op.NOT) {
			return allEnablingsAreNegated(e.childAt(0), !isNeg);
		}
		if (Op.isComparison(e.getOp())) {
			return true;			
		}
		for (int cid = 0, cide = e.nbChildren() ; cid < cide ; cid++) {
			if (! allEnablingsAreNegated(e.childAt(cid),isNeg)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isACTLstar (Property prop) {
		switch (prop.getType()) {
		case CTL:
			return isACTLstar(prop.getBody(), false);
		case LTL:			
		default:
			return true;			
		}	
	}

	private static boolean isACTLstar (Expression body, boolean isNeg) {
		if (body == null) {
			return true;
		} else if (body.getOp() == Op.AX || body.getOp() == Op.AF || body.getOp() == Op.AU || body.getOp() == Op.AG) {
			return !isNeg;
		} else if (body.getOp() == Op.EX || body.getOp() == Op.EF || body.getOp() == Op.EU || body.getOp() == Op.EG) {
			return isNeg;
		} else if (Op.isComparison(body.getOp())) {
			return true;
		} else if (body.getOp() == Op.NOT) {
			return isACTLstar(body.childAt(0),!isNeg);
		} else {
			for (int cid=0, cide=body.nbChildren() ; cid < cide ; cid++) {
				if (! isACTLstar(body.childAt(cid),isNeg)) {
					return false;
				}
			}
			return true;
		}
	}
	
	public static boolean isSyntacticallyStuttering(Property prop) {
		switch (prop.getType()) {
		case CTL:
		case LTL:
			return isSyntacticallyStuttering(prop.getBody());
		default:
			return true;			
		}	
	}

	private static boolean isSyntacticallyStuttering(Expression body) {
		if (body == null) {
			return true;
		} else if (body.getOp() == Op.AX || body.getOp() == Op.EX || body.getOp() == Op.X) {
			return false;			
		} else {
			for (int cid=0, cide=body.nbChildren() ; cid < cide ; cid++) {
				if (! isSyntacticallyStuttering(body.childAt(cid))) {
					return false;
				}
			}
			return true;
		}
	}

	public static boolean isAnInvariant(Property property) {
		if (property.getType() == PropertyType.INVARIANT)
			return true;
		else if (property.getType() == PropertyType.CTL) {
			Op firstOp = property.getBody().getOp() ;
			if (firstOp == Op.AG || firstOp == Op.EF) {
				return AtomicPropManager.isPureBool(property.getBody().childAt(0)) ;
			}
		}
		return false;
	}

	
	public static Expression evalWithAPValue (AtomicProp ap, boolean value, Expression expr) {
		if (expr == null) {
			return null;
		} else if (expr instanceof AtomicPropRef) {
			AtomicPropRef apref = (AtomicPropRef) expr;
			if (apref.getAp().equals(ap)) {
				return Expression.constant(value);
			}			
		} 							
		if (expr.nbChildren() == 0) {
			return expr;
		}
		
		List<Expression> resc = new ArrayList<>(expr.nbChildren());
		boolean changed = false;
		for (int cid = 0, cide = expr.nbChildren() ; cid < cide ; cid++) {
			Expression child = expr.childAt(cid);
			Expression e = evalWithAPValue(ap,value,child);
			resc.add(e);
			if (e != child) {
				changed = true;
			}
		}
		if (! changed) {
			return expr;
		} else {
			return Expression.nop(expr.getOp(), resc);
		}	
	}
	
	public static Expression existentialQuantification (AtomicProp ap, Expression expr) {				
		return simplifyBoolean(Expression.op(Op.OR, evalWithAPValue(ap, false, expr), evalWithAPValue(ap, true, expr)));
	}

	public static Expression simplifySumComparisons(Expression expr) {
		if (expr == null) {
			return null;
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			switch (bin.getOp()) {
			case GEQ:case GT:case EQ:case NEQ:case LT:case LEQ:
			{
				Op operator = Op.ADD;
				Expression left = bin.left;
				Expression right = bin.right;
				
				if (left.getOp() == Op.CARD || right.getOp() == Op.CARD) {
					operator = Op.CARD;
				}
				
				if (left.getOp() == operator || right.getOp()==operator) {
					// collect
					List<Expression> onl = new ArrayList<>();
					if (left.getOp() == operator) {
						for (int i=0; i < left.nbChildren() ; i++) {
							onl.add(left.childAt(i));
						}
					} else {
						onl.add(left);
					}
					
					boolean changed = false;
					List<Expression> onr = new ArrayList<>();
					if (right.getOp() == operator) {
						for (int i=0; i < right.nbChildren() ; i++) {
							Expression child = right.childAt(i);
							int li = onl.indexOf(child);
							if (li==-1) {
								onr.add(child);
							} else {
								onl.remove(li);
								// and don't add to onr
								changed = true;
							}
						}
					} else {
						int li = onl.indexOf(right);
						if (li==-1) {
							onr.add(right);
						} else {
							onl.remove(li);
							// and don't add to onr
							changed = true;
						}						
					}
					
					if (changed) {
						Expression nl ;
						if (onl.size() == 0) {
							nl = Expression.constant(0);
						} else {
							nl = Expression.nop(operator,onl); 
						}
						Expression nr ;
						if (onr.size() == 0) {
							nr = Expression.constant(0);
						} else {
							nr = Expression.nop(operator,onr); 
						}
						return Expression.op(bin.getOp(), nl, nr);
					} else {
						return bin;
					}
				}
			}
			default :
				break;
			}
		}

		if (expr.nbChildren() == 0) {
			return expr;
		}
		
		List<Expression> resc = new ArrayList<>(expr.nbChildren());
		boolean changed = false;
		for (int cid = 0, cide = expr.nbChildren() ; cid < cide ; cid++) {
			Expression child = expr.childAt(cid);
			Expression e = simplifySumComparisons(child);
			resc.add(e);
			if (e != child) {
				changed = true;
			}
		}
		if (! changed) {
			return expr;
		} else {
			return Expression.nop(expr.getOp(), resc);
		}
	}
	
}
