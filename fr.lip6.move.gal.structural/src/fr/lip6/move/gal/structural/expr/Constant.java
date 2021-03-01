package fr.lip6.move.gal.structural.expr;

import android.util.SparseIntArray;

public class Constant implements Expression {
	public int value;

	public Constant(int value) {
		this.value = value;
	}

	@Override
	public int eval(SparseIntArray state) {
		return value;
	}

	@Override
	public <T> T accept(ExprVisitor<T> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return Integer.toString(value);
	}

	@Override
	public int evalDistance(SparseIntArray state, boolean isNeg) {
		if (!isNeg) {
			// should be a bounds query
			return value;
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public Op getOp() {
		return Op.CONST;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return 3923+ value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Constant other = (Constant) obj;
		if (value != other.value)
			return false;
		return true;
	}

}
