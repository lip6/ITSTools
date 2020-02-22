package fr.lip6.move.gal.structural.expr;

import android.util.SparseIntArray;

public class ArrayVarRef implements Expression {
	public int base;
	public Expression index;

	public ArrayVarRef(int base, Expression index) {
		this.base = base;
		this.index = index;
	}

	@Override
	public int eval(SparseIntArray state) {
		return state.get(base + index.eval(state));
	}
	
	@Override
	public int getValue() {
		return base + index.eval(null);
	}

	@Override
	public <T> T accept(ExprVisitor<T> v) {
		return v.visit(this);
	}
	
	@Override
	public String toString() {
		return "s"+base+"["+index+"]";
	}

	@Override
	public int evalDistance(SparseIntArray state, boolean isNeg) {		
		throw new UnsupportedOperationException();
	}

	@Override
	public Op getOp() {
		return Op.HLPLACEREF;
	}

	@Override
	public int hashCode() {
		final int prime = 11171;
		int result = 1;
		result = prime * result + base;
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArrayVarRef other = (ArrayVarRef) obj;
		if (base != other.base)
			return false;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		return true;
	}
	
}
