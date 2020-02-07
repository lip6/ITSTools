package fr.lip6.move.gal.structural.expr;

import android.util.SparseIntArray;

public class BoolConstant implements Expression {
	public boolean value;

	public BoolConstant(boolean value) {
		this.value = value;
	}

	@Override
	public int eval(SparseIntArray state) {
		return value ? 1 : 0;
	}

	@Override
	public <T> T accept(ExprVisitor<T> v) {
		return v.visitBool(this);
	}
	
	@Override
	public String toString() {
		return Integer.toString(eval(null));
	}

	@Override
	public int evalDistance(SparseIntArray state, boolean isNegated) {		
		if (value ^ isNegated) {
			return 0;
		} else {
			return 1000;
		}
	}
}
