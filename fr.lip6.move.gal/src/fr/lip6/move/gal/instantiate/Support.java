package fr.lip6.move.gal.instantiate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.VariableRef;

public class Support implements Iterable<ISupportVariable> {

	private Set<ISupportVariable> vars = new HashSet<ISupportVariable>();
	
	@Override
	public Iterator<ISupportVariable> iterator() {
		return vars.iterator();
	}

	public void add(VariableRef vref) {
		vars.add(new SupportVariable(vref.getReferencedVar()));
	}

	public void add(ArrayPrefix prefix, int value) {
		vars.add(new SupportConstArrayAccess(prefix,value));
	}

	public void addAll(ArrayVarAccess ava) {
		for (int i = 0 ; i  < ava.getPrefix().getSize(); i++) {
			add(ava.getPrefix(),i);
		}
	}

	public boolean intersects(Support support) {
		return ! Collections.disjoint(vars, support.vars);
	}
	
	@Override
	public String toString() {
		return vars.toString();
	}
	
}
