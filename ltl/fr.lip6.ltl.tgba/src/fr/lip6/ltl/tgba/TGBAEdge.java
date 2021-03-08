package fr.lip6.ltl.tgba;

import android.util.SparseBoolArray;
import fr.lip6.move.gal.structural.expr.Expression;

public class TGBAEdge {
	private Expression condition;
	private SparseBoolArray acceptance;
	public TGBAEdge(Expression condition, SparseBoolArray acceptance) {
		this.condition = condition;
		this.acceptance = acceptance;
	}
	
}
