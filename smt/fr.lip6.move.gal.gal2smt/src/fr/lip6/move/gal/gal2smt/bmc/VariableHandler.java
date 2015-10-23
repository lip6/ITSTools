package fr.lip6.move.gal.gal2smt.bmc;

import java.util.Collections;
import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.ISort.IApplication;
import org.smtlib.SMT.Configuration;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.gal2smt.smt.AbstractVariableHandler;
import fr.lip6.move.gal.gal2smt.smt.IVariableHandler;

public class VariableHandler extends AbstractVariableHandler implements IVariableHandler {

	private final IApplication arrayArraySort;

	public VariableHandler(Configuration conf) {
		super(conf);
		// an array, indexed by ints of such arrays : (Array Int (Array Int Int)) 
		arrayArraySort = sortfactory.createSortExpression(efactory.symbol("Array"), ints, arraySort);
	}
	
	/* (non-Javadoc)
	 * @see fr.lip6.move.gal.gal2smt.bmc.IVariableHandler#declareVariable(fr.lip6.move.gal.Variable, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void declareVariable (Variable var, List<ICommand> commands) {
		commands.add(
				new org.smtlib.command.C_declare_fun(
						efactory.symbol(var.getName()),
						Collections.EMPTY_LIST,
						arraySort								
						)
				);		
	}

	/* (non-Javadoc)
	 * @see fr.lip6.move.gal.gal2smt.bmc.IVariableHandler#declareArray(fr.lip6.move.gal.ArrayPrefix, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void declareArray (ArrayPrefix array, List<ICommand> commands) {
		commands.add(
				new org.smtlib.command.C_declare_fun(
						efactory.symbol(array.getName()),
						Collections.EMPTY_LIST,
						arrayArraySort								
						)
				);		
	}


	
	/* (non-Javadoc)
	 * @see fr.lip6.move.gal.gal2smt.bmc.IVariableHandler#accessArray(fr.lip6.move.gal.ArrayPrefix, int, org.smtlib.IExpr)
	 */
	@Override
	public IExpr accessArray (ArrayPrefix array, int index, IExpr step) {
		ISymbol select = efactory.symbol("select");
	
		return efactory.fcn(select,
				efactory.fcn(select,
						efactory.symbol(array.getName()), efactory.numeral(index)), 
						step);
	
	}

	/* (non-Javadoc)
	 * @see fr.lip6.move.gal.gal2smt.bmc.IVariableHandler#accessVar(fr.lip6.move.gal.Variable, org.smtlib.IExpr)
	 */
	@Override
	public IExpr accessVar (Variable vr, IExpr step) {
		return efactory.fcn(efactory.symbol("select"), efactory.symbol(vr.getName()), step);
	}
			
}
