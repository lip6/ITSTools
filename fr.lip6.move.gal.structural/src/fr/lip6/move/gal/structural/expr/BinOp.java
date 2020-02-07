package fr.lip6.move.gal.structural.expr;

import android.util.SparseIntArray;

public class BinOp implements Expression {
	public Operator op;
	public Expression left;
	public Expression right;

	public BinOp(Operator op, Expression left, Expression right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

	@Override
	public int eval(SparseIntArray state) {
		// lazy cases
		switch (op) {
		case AND:
			return (left.eval(state) == 1) && (right.eval(state) == 1) ? 1 : 0;
		case OR:
			return (left.eval(state) == 1) || (right.eval(state) == 1) ? 1 : 0;
		default:
			break;
		}
		
		int l = left.eval(state);
		int r = right != null ? right.eval(state) : 0;
		switch (op) {
		case EQ:
			return l == r ? 1 : 0;
		case NEQ:
			return l != r ? 1 : 0;
		case LT:
			return l < r ? 1 : 0;
		case GT:
			return l > r ? 1 : 0;
		case LEQ:
			return l <= r ? 1 : 0;
		case GEQ:
			return l >= r ? 1 : 0;
		case NOT:
			return (l == 0) ? 1 : 0;
		case ADD:
			return l + r;
		case MULT:
			return l * r;
		case DIV:
			return l / r;
		case MINUS:
			return l - r;
		default : break;
		}
		throw new RuntimeException("Unexpected operator type in expression " + op);
	}
	
	@Override
	public String toString() {
		return "(" + left.toString() + " " + op + (right!=null ? " "+right.toString() : "") + ")";
	}

	@Override
	public <T> T accept(ExprVisitor<T> v) {
		return v.visit(this);
	}

	@Override
	public int evalDistance(SparseIntArray state, boolean isNeg) {
		if (! isNeg) {
			// Boolean cases
			switch (op) {
			case AND:
				return left.evalDistance(state, isNeg) + right.evalDistance(state,isNeg) ; 
			case OR:
				return Math.min(left.evalDistance(state, isNeg), right.evalDistance(state, isNeg));
			case NOT:
				return left.evalDistance(state, ! isNeg);
			case EQ:
				return Math.abs(left.eval(state) - right.eval(state));
			case NEQ:
				return left.eval(state)==right.eval(state) ? 1 : 0;
			case LT: 
				return Math.max( left.eval(state)-right.eval(state)+1, 0);
			case GT:
				return Math.max( right.eval(state)-left.eval(state)+1, 0);
			case LEQ:
				return Math.max( left.eval(state)-right.eval(state), 0);
			case GEQ:
				return Math.max( right.eval(state)-left.eval(state), 0);
			default:
			}
		} else {
			// Boolean cases
			switch (op) {
			case AND:
				return Math.min(left.evalDistance(state, isNeg), right.evalDistance(state, isNeg));				 
			case OR:
				return left.evalDistance(state, isNeg) + right.evalDistance(state,isNeg) ;
			case NOT:
				return left.evalDistance(state, ! isNeg);
			case EQ:
				return left.eval(state)==right.eval(state) ? 1 : 0;
			case NEQ:
				return Math.abs(left.eval(state) - right.eval(state));
			case LT: 
				return Math.max( right.eval(state)-left.eval(state), 0);
			case GT:
				return Math.max( left.eval(state)-right.eval(state), 0);
			case LEQ:
				return Math.max( right.eval(state)-left.eval(state)+1, 0);
			case GEQ:
				return Math.max( left.eval(state)-right.eval(state)+1, 0);
			default:
			}			
		}
		throw new RuntimeException("Unexpected operator type in expression " + op);		
	}
}
