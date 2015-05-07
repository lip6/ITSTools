package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IExpr.INumeral;
import org.smtlib.ISort;
import org.smtlib.ISort.IApplication;

import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.Variable;

public class VariableSMT {
	private static IExpr.IFactory efactory = GalToSMT.getSMT().smtConfig.exprFactory;
	private static ISort.IFactory sortfactory = GalToSMT.getSMT().smtConfig.sortFactory;
	
	
	public static ICommand variableToSmt(Variable var) {
		IExpr.ISymbol p = efactory.symbol(var.getName());		
		ISort _int = sortfactory.createSortExpression(efactory.symbol("Int"));
		ISort varType = sortfactory.createSortExpression(efactory.symbol("Int"));

		List<ISort> sorts = new ArrayList<ISort>();
		
		IApplication arraySort = sortfactory.createSortExpression(efactory.symbol("Array"), _int, varType);
		ICommand command = new org.smtlib.command.C_declare_fun(p, sorts, arraySort );
		
		return command;
	}
	
	public static ICommand initVariable(Variable var){
		IExpr.ISymbol p = efactory.symbol(var.getName());		
		ICommand command = null;
		
		if(var.getValue() instanceof Constant){
			Constant varConst = (Constant) var.getValue();
			
			INumeral index = efactory.numeral("0");
			INumeral value = efactory.numeral(varConst.getValue());
			
			IExpr select = efactory.fcn(efactory.symbol("select"), p, index);
			IExpr expr = efactory.fcn(efactory.symbol("="), select, value);	
									
			command = new org.smtlib.command.C_assert(expr);	
		}
		return command;		
	}
}
