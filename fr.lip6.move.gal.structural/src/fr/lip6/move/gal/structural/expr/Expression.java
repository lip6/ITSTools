package fr.lip6.move.gal.structural.expr;

import android.util.SparseIntArray;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.semantics.INextBuilder;

public interface Expression {
	int eval(SparseIntArray state);
	
	/**
	 * A heuristic to choose successor states "Best-First Search".
	 * Compute "distance" metric to a target state, based on 
	 * TAPAAL and Reachability Analysis of P/T NetsJonas F. Jensen, Thomas Nielsen, Lars K. Oestergaard, and Jiˇr ́ı Srba
	 * TopNoc 2016 http://people.cs.aau.dk/~srba/files/JNOS:ToPNoC:16.pdf
	 * @param state a current or successor state
	 * @param isNeg whether we are negated
	 * @return an estimation of how far we are from satisfying the property.
	 */
	int evalDistance(SparseIntArray state, boolean isNeg);
	
	public static Expression buildExpression(BooleanExpression be, INextBuilder inb) {
		ExpressionBuilder eb = new ExpressionBuilder(inb);
		return eb.doSwitch(be);
	}

	static Expression not(Expression be) {
		return new BinOp(Operator.NOT, be, null);
	}

	<T> T accept(ExprVisitor<T> v);
	
	String toString();
}
