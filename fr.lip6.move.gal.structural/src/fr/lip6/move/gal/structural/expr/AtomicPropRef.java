package fr.lip6.move.gal.structural.expr;

import java.util.function.Function;

import android.util.SparseIntArray;

public class AtomicPropRef implements Expression {
	private AtomicProp ap;

	public AtomicPropRef(AtomicProp ap) {
		this.ap = ap;
	}

	@Override
	public int eval(SparseIntArray state) {
		return ap.getExpression().eval(state);
	}

	@Override
	public <T> T accept(ExprVisitor<T> v) {
		return v.visit(this);
	}
	
	public AtomicProp getAp() {
		return ap;
	}
	
	@Override
	public String toString() {
		return ap.getName();
	}

	@Override
	public int evalDistance(SparseIntArray state, boolean isNeg) {		
		return ap.getExpression().evalDistance(state, isNeg);
	}
	
	@Override
	public int getValue() {
		return ap.getExpression().getValue();
	}

	@Override
	public Op getOp() {
		return Op.APREF;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ap == null) ? 0 : ap.hashCode());
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
		AtomicPropRef other = (AtomicPropRef) obj;
		if (ap == null) {
			if (other.ap != null)
				return false;
		} else if (!ap.equals(other.ap))
			return false;
		return true;
	}

	@Override
	public Expression childAt(int index) {
		return ap.getExpression().childAt(index);
	}
	@Override
	public int nbChildren() {
		return ap.getExpression().nbChildren();		
	}
	
	@Override
	public <T> void forEachChild(Function<Expression, T> foo) {
		ap.getExpression().forEachChild(foo);
	}
	
}
