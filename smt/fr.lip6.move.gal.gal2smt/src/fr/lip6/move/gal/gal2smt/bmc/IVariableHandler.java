package fr.lip6.move.gal.gal2smt.bmc;

import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.impl.Script;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;

public interface IVariableHandler {

	public abstract void declareVariable(Variable var, List<ICommand> commands);

	public abstract void declareArray(ArrayPrefix array, List<ICommand> commands);

	/**
	 * Declare all variables and arrays of the gal, using their name as symbol.
	 * All variables are arrays indexed by a time step.
	 * @param script script to add commands to
	 * @param gal to import
	 * @param withInitialState 
	 */
	public abstract void declareVariables(Script script, GALTypeDeclaration gal);

	/**
	 * Access array[index] at time "step"
	 * @param array array to access
	 * @param index cell to access 
	 * @param step time step to access
	 * @return a select expression to find the appropriate variable
	 */
	public abstract IExpr accessArray(ArrayPrefix array, int index, IExpr step);

	public abstract IExpr accessVar(Variable vr, IExpr step);

	public abstract void addInitialConstraint(Script script,
			GALTypeDeclaration gal);

	public IExpr translate(VariableReference vref, IExpr step) ;
}