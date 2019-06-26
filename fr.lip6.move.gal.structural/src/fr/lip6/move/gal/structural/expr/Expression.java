package fr.lip6.move.gal.structural.expr;

import android.util.SparseIntArray;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.semantics.INextBuilder;

public interface Expression {
	int eval(SparseIntArray state);

	public static Expression buildExpression(BooleanExpression be, INextBuilder inb) {
		ExpressionBuilder eb = new ExpressionBuilder(inb);
		return eb.doSwitch(be);
	}

	static Expression not(Expression be) {
		return new BinOp(Operator.NOT, be, null);
	}

	<T> T accept(ExprVisitor<T> v);
}
