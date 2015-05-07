package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;

import fr.lip6.move.gal.LogicProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;

public class PropertySMT {
	private static IExpr.IFactory efactory = GalToSMT.getSMT().smtConfig.exprFactory;

	public static List<ICommand> makeProperty(Property property, int portee) {
		List<ICommand> list = new ArrayList<ICommand>();
		
		LogicProp body = property.getBody();
		IExpr index;
		List<IExpr> exprs = new ArrayList<IExpr>();
		
		for (int i = 0; i < portee; i++) {
			index = efactory.numeral(i);
			exprs.add(ExpressionTranslator.translateProperty(body, index));
		}
		
		if (body instanceof ReachableProp) {
			list.add(new org.smtlib.command.C_assert(efactory.fcn(efactory.symbol("or"), exprs)));	
		}else{
			list.add(new org.smtlib.command.C_assert(efactory.fcn(efactory.symbol("and"), exprs)));
		}
		
		return list;
	}

}
