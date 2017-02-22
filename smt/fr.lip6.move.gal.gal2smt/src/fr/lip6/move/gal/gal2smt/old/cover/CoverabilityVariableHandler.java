package fr.lip6.move.gal.gal2smt.old.cover;

import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.SMT.Configuration;
import org.smtlib.impl.SMTExpr.Numeral;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.gal2smt.old.AbstractVariableHandler;


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
			declarePositiveIntegerVariable(getArrName(array, efactory.numeral(i)), commands);
		}
	}
	
	
	@Override
	public IExpr accessVar(Variable vr, IExpr step) {
		return efactory.symbol(vr.getName());
	}

	@Override
	public IExpr accessArray(ArrayPrefix array, IExpr index, IExpr step) {
		return efactory.symbol(getArrName(array, index));
	}
	
	String getArrName(ArrayPrefix arr, IExpr i) {
		if (i instanceof Numeral) {
			int j = ((Numeral)i).intValue();
			return "|"+arr.getName()+"["+j+"]|";
		} else {
			throw new UnsupportedOperationException("All array index should be constant in coverability computation.");
		}
	}

}
