package fr.lip6.move.gal.structural.expr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
