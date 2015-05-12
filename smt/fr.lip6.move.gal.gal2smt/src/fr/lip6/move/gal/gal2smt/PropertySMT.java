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

	public static void addProperty(Property property, int depth, List<ICommand> commands) {
		
		LogicProp body = property.getBody();
		IExpr index;
		List<IExpr> exprs = new ArrayList<IExpr>();
		
		for (int i = 0; i < depth; i++) {
			index = efactory.numeral(i);
			exprs.add(ExpressionTranslator.translateProperty(body, index));
		}
		
		if (body instanceof ReachableProp) {
			commands.add(new org.smtlib.command.C_assert(efactory.fcn(efactory.symbol("or"), exprs)));	
		}else{
			commands.add(new org.smtlib.command.C_assert(efactory.fcn(efactory.symbol("and"), exprs)));
		}
	}

}
