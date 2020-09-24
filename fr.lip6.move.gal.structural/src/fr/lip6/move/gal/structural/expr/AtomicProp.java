package fr.lip6.move.gal.structural.expr;

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