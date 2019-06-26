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
		case AND:
			return (l == 1) && (r == 1) ? 1 : 0;
		case OR:
			return (l == 1) || (r == 1) ? 1 : 0;
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
		}
		throw new RuntimeException("Unexpected operator type in expression " + op);
	}

	@Override
	public <T> T accept(ExprVisitor<T> v) {
		return v.visit(this);
	}
}
