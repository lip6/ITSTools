package fr.lip6.move.gal.pn2pins;

import fr.lip6.move.gal.structural.expr.Expression;

public class AtomicProp {
	private final String name;
	private final Expression expression;
	public AtomicProp(String name, Expression be) {
		this.name = name;
		this.expression = be;
	}
	public String getName() {
		return name;
	}
	public Expression getExpression() {
		return expression;
	}
}