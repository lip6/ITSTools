package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;

import fr.lip6.move.gal.LogicProp;
import fr.lip6.move.gal.Property;

public class PropertySMT {
	private static IExpr.IFactory efactory = GalToSMT.getSMT().smtConfig.exprFactory;

	public static void addProperty(Property property, int depth, List<ICommand> commands, boolean disjunct) {
		
		LogicProp body = property.getBody();
		List<IExpr> exprs = new ArrayList<IExpr>();
		
		for (int i = 0; i < depth; i++) {
			addPropertyAtStep(body, i, exprs);
		}
		
		if (exprs.isEmpty()) {
			return ;
		}
		if (exprs.size()==1) {
			commands.add(new org.smtlib.command.C_assert(exprs.get(0))); 
			return;
		}
		
		if (disjunct) {
			// test these expressions in any (or) state
			commands.add(new org.smtlib.command.C_assert(efactory.fcn(efactory.symbol("or"), exprs)));
		} else {
			// enforce these expressions/constraints in all states
			commands.add(new org.smtlib.command.C_assert(efactory.fcn(efactory.symbol("and"), exprs)));			
		}
		
	}

	private static void addPropertyAtStep(LogicProp body, int i, List<IExpr> exprs) {
		IExpr index = efactory.numeral(i);
		// expression built varies depending on type of prop
		// SAT = trace to state satisfying P for reach (verdict TRUE)
		// SAT = trace to c-e satisfying !P for invariant (verdict FALSE)
		// SAT = trace to c-e satisfying P for never (verdict FALSE)
		exprs.add(ExpressionTranslator.translateProperty(body, index));
	}
	
	public static void assertPropertyAtStep(Property property, int depth, List<ICommand> commands) {
		IExpr index = efactory.numeral(depth);
		LogicProp body = property.getBody();
		commands.add(new org.smtlib.command.C_assert(ExpressionTranslator.translateProperty(body, index)));
	}
}
