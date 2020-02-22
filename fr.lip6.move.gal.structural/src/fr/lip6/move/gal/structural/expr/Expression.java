package fr.lip6.move.gal.structural.expr;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import android.util.SparseIntArray;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.semantics.INextBuilder;

public interface Expression {
	int eval(SparseIntArray state);
	
	/**
	 * A heuristic to choose successor states "Best-First Search".
	 * Compute "distance" metric to a target state, based on 
	 * TAPAAL and Reachability Analysis of P/T NetsJonas F. Jensen, Thomas Nielsen, Lars K. Oestergaard, and Jiˇr ́ı Srba
	 * TopNoc 2016 http://people.cs.aau.dk/~srba/files/JNOS:ToPNoC:16.pdf
	 * @param state a current or successor state
	 * @param isNeg whether we are negated
	 * @return an estimation of how far we are from satisfying the property.
	 */
	int evalDistance(SparseIntArray state, boolean isNeg);		

	<T> T accept(ExprVisitor<T> v);
	
	String toString();
	
	Op getOp();
	
	default public <T> void forEachChild (Function<Expression,T> foo) { /* NOP */} 
	
	// only valid for constantly evaluable expressions
	default public int getValue() {
		throw new UnsupportedOperationException();
	}
	
	static Expression buildExpression(BooleanExpression be, INextBuilder inb) {
		ExpressionBuilder eb = new ExpressionBuilder(inb);
		return eb.doSwitch(be);
	}

	static Expression not(Expression be) {
		return op(Op.NOT, be, null);
	}

	static Expression op(Op op, Expression l, Expression r) {
		switch (op) {
		case AND : {
			if (l.getOp() == Op.BOOLCONST) {
				if (l.getValue()==1)
					return r;
				else 
					return l;
			} else if (r.getOp() == Op.BOOLCONST) {
				if (r.getValue()==1)
					return l;
				else 
					return r;
			} else if (l.getOp() == Op.NOT) {
				if ( ((BinOp)l).left.equals(r) ) {
					return constant(false);
				}
			} else if (r.getOp() == Op.NOT) {
				if ( ((BinOp)r).left.equals(l) ) {
					return constant(false);
				}
			}
			break;
		}
		case OR : {
			if (l.getOp() == Op.BOOLCONST) {
				if (l.getValue()==1)
					return l;
				else 
					return r;
			} else if (r.getOp() == Op.BOOLCONST) {
				if (r.getValue()==1)
					return r;
				else 
					return l;
			} else if (l.getOp() == Op.NOT) {
				if ( ((BinOp)l).left.equals(r) ) {
					return constant(true);
				}
			} else if (r.getOp() == Op.NOT) {
				if ( ((BinOp)r).left.equals(l) ) {
					return constant(true);
				}
			}
			break;
		}
		case NOT : {
			if (l.getOp() == Op.BOOLCONST) {
				if (l.getValue() == 1) {
					return constant(false);
				} else {
					return constant(true);
				}
			} else if (l.getOp() == Op.NOT) {
				return ((BinOp) l).left;
			}
			break;
		}
		case MULT : {
			if (l.getOp() == Op.CONST) {
				if (l.getValue() == 0)
					return l;
				else if (l.getValue() == 1)
					return r;
			}
			if (r.getOp() == Op.CONST) {
				if (r.getValue() == 0)
					return r;
				else if (r.getValue() == 1)
					return l;
			}
			break;
		}
		case ADD : {
			if (l.getOp() == Op.CONST) {
				if (l.getValue() == 0)
					return r;				
			}
			if (r.getOp() == Op.CONST) {
				if (r.getValue() == 0)
					return l;
			}
			break;
		}
		default :
			break;
		}
		if (op == Op.MULT || op == Op.ADD || op == Op.AND || op == Op.OR) {
			List<Expression> children = new ArrayList<>();
			if (l.getOp() == op || r.getOp() == op) {				
				if (l.getOp() == op) {
					l.forEachChild(e ->  children.add(e));
				} else {
					children.add(l);
				}
				if (r.getOp() == op) {
					r.forEachChild(e -> children.add(e));
				} else {
					children.add(r);
				}
			} else {
				children.add(l);
				children.add(r);
			}
			return new NaryOp(op, children);

		}
		return new BinOp(op, l, r);
	}
	static Expression var(int index) {
		return new VarRef(index);
	}
	static Expression array(int base, Expression index) {
		return new ArrayVarRef(base, index);
	}
	static Expression paramRef(Param p) {
		return new ParamRef(p);
	}

	static Expression constant(boolean value) {
		return new BoolConstant(value);
	}

	static Expression constant(int i) {
		return new Constant(i);
	}
	static Expression nop(Op op) {
		return nop(op, new ArrayList<>());
	}
	static Expression nop(Op op, List<Expression> children) {
		switch (op) {
		case AND: {	
			List<Expression> resc = new ArrayList<>(children.size());
			boolean changed = false;
			for (Expression c : children) {
				if (c.getOp()==Op.BOOLCONST && c.getValue()==1) {
					changed = true;
					continue;
				} else if (c.getOp()==Op.BOOLCONST && c.getValue()==0) {
					return Expression.constant(false);
				} else if (c.getOp()==Op.AND) {
					changed = true;
					resc.addAll(((NaryOp)c).getChildren());
				} else {
					resc.add(c);
				}
			}
			if (resc.size() == 1) {
				return resc.get(0);
			}
			if (changed)
				children = resc;
			break;
		}
		case OR: {
			List<Expression> resc = new ArrayList<>(children.size());
			boolean changed = false;
			for (Expression c : children) {
				if (c.getOp()==Op.BOOLCONST && c.getValue()==0) {
					changed = true;
					continue;
				} else if (c.getOp()==Op.BOOLCONST && c.getValue()==1) {
					return Expression.constant(true);
				} else if (c.getOp()==Op.OR) {
					changed = true;
					resc.addAll(((NaryOp)c).getChildren());
				} else {
					resc.add(c);
				}
			}
			if (resc.size() == 1) {
				return resc.get(0);
			}
			if (changed)
				children = resc;
			break;
		}
		case ADD: {
			children.removeIf(c-> c.getOp()==Op.CONST && c.getValue()==0);			
			if (children.size() == 1) {
				return children.get(0);
			}
			break;
		}
		default:
			break;
		}
			
		return new NaryOp(op, children);
	}
	static Expression trans(int transitionIndex) {
		return new TransRef(transitionIndex);
	}
}
