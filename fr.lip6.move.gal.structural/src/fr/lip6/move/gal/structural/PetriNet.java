package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import fr.lip6.move.gal.structural.expr.AtomicPropRef;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;
import fr.lip6.move.gal.structural.expr.VarRef;

public abstract class PetriNet {

	private String name = "Petri";
	private List<Property> properties = new ArrayList<>();

	public abstract int getPlaceCount();

	public abstract int getTransitionCount();

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public abstract int getTransitionIndex(String name);
	public abstract int getPlaceIndex(String name);
	
	public BitSet computeSupport() {
		BitSet supp = new BitSet();
		for (Property p : getProperties()) {
			addSupport(p.getBody(),supp);
		}
		return supp;
	}
	
	public static Void addSupport(Expression expr, BitSet supp) {
		if (expr == null) {
			return null;
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			bin.forEachChild(c -> addSupport(c, supp));			
		} else if (expr instanceof NaryOp) {
			NaryOp nop = (NaryOp) expr;
			nop.forEachChild(c -> addSupport(c, supp));
		} else if (expr instanceof VarRef) {
			supp.set(expr.getValue());
		} else if (expr instanceof AtomicPropRef) {
			addSupport(((AtomicPropRef) expr).getAp().getExpression(), supp);
		}
		return null;
	}
}