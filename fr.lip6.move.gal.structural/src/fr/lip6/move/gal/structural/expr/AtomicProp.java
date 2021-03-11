package fr.lip6.move.gal.structural.expr;

public class AtomicProp {
	private Expression expression;
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
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
}