package fr.lip6.move.gal.structural.expr;

import android.util.SparseIntArray;

public class TransRef implements Expression {
	public int index;

	public TransRef(int index) {
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
	
	@Override
	public String toString() {
		return "t"+index;
	}

	@Override
	public int evalDistance(SparseIntArray state, boolean isNeg) {		
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int getValue() {
		return index;
	}

	@Override
	public Op getOp() {
		return Op.TRANSREF;
	}

	@Override
	public int hashCode() {
		return 2357 * (index +1);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransRef other = (TransRef) obj;
		if (index != other.index)
			return false;
		return true;
	}	
}
