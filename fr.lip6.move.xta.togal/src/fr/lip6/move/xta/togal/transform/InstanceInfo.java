package fr.lip6.move.xta.togal.transform;

import java.util.List;


class InstanceInfo {
	String name;
	List<fr.lip6.move.gal.IntExpression> paramValues;
	int id;
	public InstanceInfo(String name, List<fr.lip6.move.gal.IntExpression> paramValues, int id) {
		this.name = name;
		this.paramValues = paramValues;
		this.id = id;
	}

}