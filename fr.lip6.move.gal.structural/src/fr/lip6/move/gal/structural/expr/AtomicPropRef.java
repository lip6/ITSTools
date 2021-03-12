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
		return ap.getExpression().getOp();
	}

	public int hashCode() {
		return ap.getExpression().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		return ap.getExpression().equals(obj);		
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
