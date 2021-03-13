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
	@Override
	public String toString() {
		return  name + ":" + expression ;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AtomicProp other = (AtomicProp) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}