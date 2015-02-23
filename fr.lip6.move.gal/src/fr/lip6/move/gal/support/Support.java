package fr.lip6.move.gal.support;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;

/**
 * A class to describe the support of statements in GAL.
 * Holds a set of support variables, two support can be compared (equals and set-wise), unioned etc...
 * @author Yann
 *
 */
public class Support implements Iterable<ISupportVariable> {

	/**
	 * Holds the adapters for variable references
	 */
	private Set<ISupportVariable> vars = new LinkedHashSet<ISupportVariable>();
	
	@Override
	public Iterator<ISupportVariable> iterator() {
		return vars.iterator();
	}

	/**
	 * Add a reference to a single variable, x. See also Variable version.
	 * @param vref the variable ref we encountered in the current statement (expresssion).
	 */
	public void add(VariableReference vref) {
		if (vref.getRef() instanceof Variable) {
			add((Variable)vref.getRef());
		}
	}

	/**
	 * Add a reference to a single variable, x. See also VariableRef version.
	 * @param var the variable we want to add.
	 */
	public void add(Variable var) {
		vars.add(new SupportVariable(var));
	}

	/**
	 * Add a reference to a precise cell of an array, e.g. tab[3]
	 * @param prefix the array
	 * @param value the index, a constant.
	 */
	public void add(ArrayPrefix prefix, int value) {
		vars.add(new SupportConstArrayAccess(prefix,value));
	}

	/**
	 * Add a reference of the form tab[*], i.e. all cells in the array.
	 * @param ava the array we want to add to support
	 */
	public void addAll(VariableReference ava) {
		ArrayPrefix ap = (ArrayPrefix) ava.getRef();
		for (int i = 0 ; i  < ap.getSize(); i++) {
			add(ap,i);
		}
	}
	
	/**
	 * Modify this support : add all variables of argument support to this.
	 * @param other another support, that will not be modified.
	 */
	public void addAll(Support other) {
		vars.addAll(other.vars);
	}

	/**
	 * Test whether two supports intersect. 
	 * If this contains tab[*] and other contains tab[3], we intersect.
	 * @param other another support
	 * @return true iff. this and other have non empty intersection.
	 */
	public boolean intersects(Support other) {
		return ! Collections.disjoint(vars, other.vars);
	}
	
	@Override
	public String toString() {
		return vars.toString();
	}

	/**
	 * Test whether a given variable belongs to this. 
	 * @param var a variable to find
	 * @return true iff. var is in support
	 */
	public boolean contains(Variable var) {
		return vars.contains(new SupportVariable(var));
	}
	
}
