package fr.lip6.move.gal.structural;

import fr.lip6.move.gal.structural.expr.Expression;

public class Property {
	private Expression prop;
	private PropertyType type;
	private String name;
	public Property(Expression prop, PropertyType type, String name) {
		this.prop = prop;
		this.type = type;
		this.name = name;
	}
	public Property() {
	}
	public Property copy () {
		return new Property(prop, type, name);
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(PropertyType type) {
		this.type = type;
	}
	public void setBody(Expression prop) {
		this.prop = prop;
	}
	public String getName() {
		return name;
	}
	public Expression getBody() {
		return prop;
	}
	public PropertyType getType() {
		return type;
	}
	@Override
	public String toString() {
		return "Property [prop=" + prop + ", type=" + type + ", name=" + name + "]";
	}
}
