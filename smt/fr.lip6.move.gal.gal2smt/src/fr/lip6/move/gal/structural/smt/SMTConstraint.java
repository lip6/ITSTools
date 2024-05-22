package fr.lip6.move.gal.structural.smt;

import org.smtlib.ICommand;

/** Represents a constraint we can directly feed the solver.
 * Also keeps track of the variables it depends on.
 * Typical examples include an "assert" commands, or maybe a minimize/maximize command. 
 */
public class SMTConstraint implements IConstraint {

	private ICommand constraint;
	private VarSet support;
	
	public SMTConstraint(ICommand constraint, VarSet support) {
		this.constraint = constraint;
		this.support = support;
	}
	
	public ICommand getSMTConstraint() {
		return constraint;
	}
	public VarSet getSupport() {
		return support;
	}


	@Override
	public String toString() {
		return constraint.toString();
	}
	
}
