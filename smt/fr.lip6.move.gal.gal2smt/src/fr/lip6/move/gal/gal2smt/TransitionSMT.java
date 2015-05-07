package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IExpr.IDeclaration;
import org.smtlib.ISort;
import org.smtlib.impl.Sort;

import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Transition;

public class TransitionSMT {
	private static IExpr.IFactory efactory = GalToSMT.getSMT().smtConfig.exprFactory;
	private static ISort.IFactory sortfactory = GalToSMT.getSMT().smtConfig.sortFactory;	
	
	
	public static ICommand transitionToSmt(Transition transit, List<IExpr> vars, GALTypeDeclaration gal) {		
		IExpr.ISymbol symbol = efactory.symbol(transit.getName());
		
		ISort bool = Sort.Bool();
		ISort Int = sortfactory.createSortExpression(efactory.symbol("Int"));
		
		IDeclaration IntDeclaration = efactory.declaration(efactory.symbol("i"), Int);
		
		List<IDeclaration> declarations = new ArrayList<IDeclaration>();
		declarations.add(IntDeclaration);
		
		List<IExpr> termArgs = new ArrayList<IExpr>();
		
		
		BooleanExpression guard = transit.getGuard();
		IExpr indexNow = efactory.symbol("i");
		IExpr indexNext = efactory.fcn(efactory.symbol("+"),efactory.symbol("i"),efactory.symbol("1"));
		
		IExpr condition = ExpressionTranslator.translateBool(guard, indexNow);
		termArgs.add(condition);		
		
		List<IExpr> actions = GalToSmtFunctions.translateActions(indexNow, indexNext, transit.getActions(), gal );
		
		if(actions.size() > 1){
			termArgs.add(efactory.fcn(efactory.symbol("and"),actions));
		}else if(actions.size() == 1){
			termArgs.add(actions.get(0));
		}
		
		IExpr.ISymbol termName = efactory.symbol("ite"); // if guard true; than else
		IExpr.ISymbol termEnding = efactory.symbol("false");
		termArgs.add(termEnding);
		IExpr transitionExpr = efactory.fcn(termName,termArgs);

		
		ICommand command = new org.smtlib.command.C_define_fun(symbol, declarations, bool, transitionExpr);
		return command;
	}
	
	public static List<ICommand> deroulementTransition(List<String> transitions, int portee) {
		List<ICommand> list = new ArrayList<ICommand>();
		List<IExpr> sorts = new ArrayList<IExpr>();
		
		for (int j = 0; j < portee; j++) {	
			sorts.clear();
			for (int i = 0; i < transitions.size(); i++) {
				IExpr corps = efactory.fcn(efactory.symbol(transitions.get(i)), efactory.symbol(j+""));				
				sorts.add(corps);							
			}
			IExpr expr;
			if(transitions.size() == 1){
				sorts.add(efactory.symbol("false"));
				expr = efactory.fcn(efactory.symbol("or"), sorts);
			}
			else
				expr = efactory.fcn(efactory.symbol("or"), sorts);
			
			ICommand term = new org.smtlib.command.C_assert(expr);
			list.add(term);
		}
		
		return list;
	}
}
