package fr.lip6.move.gal.structural.expr;

import android.util.SparseIntArray;

public class VarRef implements Expression {
	public int index;

	public VarRef(int index) {
		this.index = index;
	}

	@Override
	public int eval(SparseIntArray state) {
		return state.get(index);
	}

	@Override
	public <T> T accept(ExprVisitor<T> v) {
		return v.visit(this);
	}
}
