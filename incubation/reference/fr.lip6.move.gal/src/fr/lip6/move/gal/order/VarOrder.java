package fr.lip6.move.gal.order;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VarOrder implements IOrder {

	private List<String> vars;
	
	public VarOrder(List<String> vars) {
		this.vars = vars;
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
		return "VarOrder [vars=" + vars + "]";
	}

	@Override
	public Set<String> getAllVars() {
		return new HashSet<String>(vars);
	}
	
	
}
