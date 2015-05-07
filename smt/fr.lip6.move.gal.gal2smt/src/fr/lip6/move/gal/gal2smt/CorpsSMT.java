package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;

public class CorpsSMT {
	private static IExpr.IFactory efactory = GalToSMT.getSMT().smtConfig.exprFactory;
	
	public static List<ICommand> smtFooter(List<IExpr> vars) {
		List<ICommand> list = new ArrayList<ICommand>();
		
		list.add(new org.smtlib.command.C_check_sat());		
		list.add(new org.smtlib.command.C_get_value(vars));		
		list.add(new org.smtlib.command.C_exit());
		
		return list;
	}
	
	public static List<ICommand> smtHeaders(String logic){
		List<ICommand> commands = new ArrayList<ICommand>();
		
		commands.add(new org.smtlib.command.C_set_option(efactory.keyword(":produce-models"), efactory.symbol("true")));
		commands.add(new org.smtlib.command.C_set_logic(efactory.symbol(logic)));
		
		return commands;
	}
}
