package fr.lip6.move.gal.gal2smt.bmc;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.smtlib.IExpr;
import org.smtlib.SMT.Configuration;

import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;

public class NecessaryEnablingsolver extends KInductionSolver {

	public NecessaryEnablingsolver(Configuration smtConfig, Solver engine) {
		super(smtConfig, engine, false);
	}

	public int [] computeEnablers (List<Transition> trans, BooleanExpression guard) {
		int [] toret = new int[trans.size()];
		
		// push a context
		solver.push(1);
		
		List<IExpr> asserts = new ArrayList<IExpr>();
		// assert guard in initial 
		asserts.add(et.translateBool(guard, efactory.numeral(0)));
		// not guard in successor
		asserts.add(efactory.fcn(efactory.symbol("not"), et.translateBool(guard, efactory.numeral(1))));
		IExpr sprop = efactory.fcn(efactory.symbol("and"), asserts);
		solver.assertExpr(sprop);
		
		int i=0;
		for (Transition t :  trans) {
			solver.push(1);
			solver.assertExpr(efactory.fcn(efactory.symbol(t.getName()),efactory.numeral(0)));
		
			Logger.getLogger("fr.lip6.move.gal").fine("Checking "+t.getName());
			Result res = checkSat();
			if (res == Result.SAT) {
				toret[i] = 1;
			} else {
				toret[i] = 0;
			}
			solver.pop(1);
			i++;
		}

		solver.pop(1);
		return toret;
	}
	
}
