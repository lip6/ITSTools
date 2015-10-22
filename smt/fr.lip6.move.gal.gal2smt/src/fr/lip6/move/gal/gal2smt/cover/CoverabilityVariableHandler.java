package fr.lip6.move.gal.gal2smt.cover;

import java.util.Collections;
import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.SMT.Configuration;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.gal2smt.bmc.AbstractVariableHandler;


public class CoverabilityVariableHandler extends AbstractVariableHandler {

	public CoverabilityVariableHandler(Configuration conf) {
		super(conf);
	}

	@Override
	public void declareVariable(Variable var, List<ICommand> commands) {
		declarePositiveIntegerVariable(var.getName(), commands);
	}


	@Override
	public void declareArray(ArrayPrefix array, List<ICommand> commands) {
		for (int i=0 ; i < ((Constant) array.getSize()).getValue(); i++) {
			declarePositiveIntegerVariable(getArrName(array, i), commands);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void declarePositiveIntegerVariable(String name, List<ICommand> commands) {
		ISymbol sname = efactory.symbol(name);
		commands.add(new org.smtlib.command.C_declare_fun(sname , Collections.EMPTY_LIST, ints ));
		
		// assert >= 0
		commands.add(new org.smtlib.command.C_assert(efactory.fcn(efactory.symbol(">="), sname , efactory.numeral(0))));
	}

	
	@Override
	public IExpr accessVar(Variable vr, IExpr step) {
		return efactory.symbol(vr.getName());
	}

	@Override
	public IExpr accessArray(ArrayPrefix array, int index, IExpr step) {
		return efactory.symbol(getArrName(array, index));
	}
	
	String getArrName(ArrayPrefix arr, int i) {
		return "|"+arr.getName()+"["+i+"]|";
	}

}
