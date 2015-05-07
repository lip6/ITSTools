package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.ISort;
import org.smtlib.ISort.IApplication;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Constant;

public class ArraySMT {
	private static IExpr.IFactory efactory = GalToSMT.getSMT().smtConfig.exprFactory;
	private static ISort.IFactory sortfactory = GalToSMT.getSMT().smtConfig.sortFactory;			
	
	public static ICommand arrayToSmt(ArrayPrefix array) {
		IExpr.ISymbol name = efactory.symbol(array.getName());		
		ISort index = sortfactory.createSortExpression(efactory.symbol("Int"));
		ISort varType = sortfactory.createSortExpression(efactory.symbol("Int"));
		
		/* (Array Int Int) */
		IApplication elem = sortfactory.createSortExpression(efactory.symbol("Array"), index, varType);
		/* (Array Int (Array Int Int)) */
		IApplication arraySort = sortfactory.createSortExpression(efactory.symbol("Array"), index, elem);
		
		List<ISort> variables = new ArrayList<ISort>();		
		ICommand command = new org.smtlib.command.C_declare_fun(name, variables, arraySort );
		return command;
	}
	
	public static ICommand initArray(ArrayPrefix array){
		IExpr expr, selectIndex, selectInit;
		
		List<IExpr> list = new ArrayList<IExpr>();
		
			for (int index = 0; index < array.getValues().size(); index++) {
				selectIndex = getIndex(array, index);				
				selectInit = efactory.fcn(efactory.symbol("select"), selectIndex, efactory.numeral("0"));
				
				if(array.getValues().get(index) instanceof Constant){
					Constant c = (Constant) array.getValues().get(index);	
					expr = efactory.fcn(efactory.symbol("="), selectInit, efactory.numeral(c.getValue()));	
					list.add(expr);
				}else{
					GalToSMT.getLog().warning("Invalide type " + array.getValues().get(index).getClass().getSimpleName());
				}
			}
			
			IExpr res = efactory.fcn(efactory.symbol("and"), list);		
			ICommand command = new org.smtlib.command.C_assert(res);	
		
		return command;		
	}
	
	/**
	 * Retourne le tableau a l'index 'index'
	 * @param array Le tableau
	 * @param index l'index
	 * @return
	 */
	public static IExpr getIndex(ArrayPrefix array, int index){
		return efactory.fcn(efactory.symbol("select"), efactory.symbol(array.getName()), efactory.numeral(index));
	}
	
	public static IExpr getIndex(ArrayPrefix array, IExpr index){
		if (index instanceof Constant) {
			Constant ind = (Constant) index;
			return getIndex(array, ind.getValue());			
		}
		GalToSMT.getLog().info("index is not a number");
		return null;
	}
	
}
