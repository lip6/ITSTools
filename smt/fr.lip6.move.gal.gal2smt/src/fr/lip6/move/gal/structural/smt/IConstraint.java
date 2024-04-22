package fr.lip6.move.gal.structural.smt;

import org.smtlib.ICommand;

public interface IConstraint {
	VarSet getSupport();
	ICommand getSMTConstraint();
}
