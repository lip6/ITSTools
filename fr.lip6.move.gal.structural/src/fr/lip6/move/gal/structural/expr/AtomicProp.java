package fr.lip6.move.gal.structural.expr;

public class AtomicProp {
	private final Expression expression;
	private final String name;
	public AtomicProp(String name, Expression be) {
		this.name = name;
		this.expression = be;
	}
	public Expression getExpression() {
		return expression;
	}
	public String getName() {
		return name;
	}
}