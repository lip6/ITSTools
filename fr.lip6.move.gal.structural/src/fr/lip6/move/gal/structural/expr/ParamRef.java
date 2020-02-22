package fr.lip6.move.gal.structural.expr;

import android.util.SparseIntArray;

public class ParamRef implements Expression {
	public Param parameter;
	
	public ParamRef(Param parameter) {
		this.parameter = parameter;
	}

	@Override
	public int eval(SparseIntArray state) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int evalDistance(SparseIntArray state, boolean isNeg) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T accept(ExprVisitor<T> v) {		
		return v.visit(this);
	}

	@Override
	public String toString() {
		return "$" + parameter.getName();
	}

	@Override
	public Op getOp() {
		return Op.PARAMREF;
	}

	@Override
	public int hashCode() {
		return 7823 * parameter.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParamRef other = (ParamRef) obj;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		return true;
	}	
}
