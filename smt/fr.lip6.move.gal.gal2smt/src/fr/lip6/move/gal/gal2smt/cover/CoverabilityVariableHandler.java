package fr.lip6.move.gal.gal2smt.cover;

import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.SMT.Configuration;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.gal2smt.smt.AbstractVariableHandler;


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
