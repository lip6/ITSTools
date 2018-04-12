package fr.lip6.move.gal.order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.lip6.move.gal.support.ISupportVariable;
import fr.lip6.move.gal.support.Support;

public class VarOrder implements IOrder {

	private List<String> vars;
	private String name;
	
	public VarOrder(List<String> vars,String name) {
		this.vars = vars;
		this.name = name;
	}

	public VarOrder(Support supp, String name) {
		this.name = name;
		vars = new ArrayList<String>();
		for (ISupportVariable var : supp ) {
			vars.add(var.toString());
		}
	}

	@Override
	public <T> T accept(IOrderVisitor<T> v) {
		return v.visitVars(this);
	}

	
	public List<String> getVars() {
		return vars;
	}

	@Override
	public String toString() {
		return vars.toString();
	}

	@Override
	public Set<String> getAllVars() {
		return new HashSet<String>(vars);
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public VarOrder clone()  {
		return new VarOrder(new ArrayList<String>(vars), name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((vars == null) ? 0 : vars.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VarOrder))
			return false;
		VarOrder other = (VarOrder) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (vars == null) {
			if (other.vars != null)
				return false;
		} else if (!vars.equals(other.vars))
			return false;
		return true;
	}
	
}
