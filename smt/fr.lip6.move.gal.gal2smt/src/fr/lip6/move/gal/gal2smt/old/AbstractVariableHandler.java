package fr.lip6.move.gal.gal2smt.old;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.ISort.IApplication;
import org.smtlib.SMT.Configuration;
import org.smtlib.command.C_assert;
import org.smtlib.impl.Script;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Variable;

public abstract class AbstractVariableHandler implements IVariableHandler {
	protected final IFactory efactory;
	protected final org.smtlib.ISort.IFactory sortfactory;
	// integer sort
	protected final IApplication ints;
	// an array, indexed by integers, containing integers : (Array Int Int) 
	protected final IApplication arraySort;

	protected final List<IExpr> allAccess = new ArrayList<IExpr>();

	public AbstractVariableHandler(Configuration conf) {
		efactory = conf.exprFactory;
		sortfactory = conf.sortFactory;
		// integer sort
		ints = sortfactory.createSortExpression(efactory.symbol("Int"));
		// an array, indexed by integers, containing integers : (Array Int Int) 
		arraySort = sortfactory.createSortExpression(efactory.symbol("Array"), ints, ints);
	}
	
	/* (non-Javadoc)
	 * @see fr.lip6.move.gal.gal2smt.bmc.IVariableHandler#addInitialConstraint(org.smtlib.impl.Script, fr.lip6.move.gal.GALTypeDeclaration)
	 */
	@Override
	public void addInitialConstraint(Script script, GALTypeDeclaration gal) {
		/* VARIABLES */				
		for (Variable var : gal.getVariables()) {
			script.commands().add(
					new C_assert(
							efactory.fcn(efactory.symbol("="), 
									accessVar(var, efactory.numeral(0)),
									efactory.numeral(((Constant)var.getValue()).getValue()))
							)
					);
			
		}
		/* ARRAYS */
		for (ArrayPrefix array : gal.getArrays()) {
			for (int index =0 ; index < ((Constant) array.getSize()).getValue() ; index++) {
				script.commands().add(
						new C_assert(
								efactory.fcn(efactory.symbol("="), 
										accessArray(array, efactory.numeral(index) ,efactory.numeral(0)),
										efactory.numeral( ((Constant)array.getValues().get(index)).getValue()))
								)
						);
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see fr.lip6.move.gal.gal2smt.bmc.IVariableHandler#declareVariables(org.smtlib.impl.Script, fr.lip6.move.gal.GALTypeDeclaration)
	 */
	@Override
	public void declareVariables(Script script, GALTypeDeclaration gal) {

		// Declare variables
		/* VARIABLES */				
		for (Variable var : gal.getVariables()) {
			// a new variable with this type
			declareVariable(var, script.commands());
			allAccess.add(accessVar(var, efactory.numeral(0)));
		}
		/* ARRAYS */
		for (ArrayPrefix array : gal.getArrays()) {
			// a new variable with this type
			declareArray(array, script.commands());
			for (int i = 0; i < ((Constant) array.getSize()).getValue(); i++) {
				allAccess.add(accessArray(array, efactory.numeral(i), efactory.numeral(0)));
			}
		}
	}

	@Override
	public List<IExpr> getAllAccess() {
		return allAccess;
	}
	
	public void declarePositiveIntegerVariable(String name, List<ICommand> commands) {
		declarePositiveIntegerVariable(name, commands, false);		
	}
	
	@SuppressWarnings("unchecked")
	public void declarePositiveIntegerVariable(String name, List<ICommand> commands, boolean addDecl) {
		ISymbol sname = efactory.symbol(name);
		commands.add(new org.smtlib.command.C_declare_fun(sname , Collections.EMPTY_LIST, ints ));
		
		if (addDecl) {
			allAccess.add(sname);
		}
		
		// assert >= 0
		commands.add(new org.smtlib.command.C_assert(efactory.fcn(efactory.symbol(">="), sname , efactory.numeral(0))));		
	}


}
