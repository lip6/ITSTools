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
	public int getValue() {
		return index;
	}
	
	@Override
	public <T> T accept(ExprVisitor<T> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return "s"+index;
	}

	@Override
	public int evalDistance(SparseIntArray state, boolean isNeg) {		
		if (!isNeg) {
			return eval(state);
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public Op getOp() {
		return Op.PLACEREF;
	}

	@Override
	public int hashCode() {
		return 2969 * (index +1);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VarRef other = (VarRef) obj;
		if (index != other.index)
			return false;
		return true;
	}
	
}
