package fr.lip6.move.gal.gal2smt.bmc;

import java.util.logging.Logger;

import org.smtlib.SMT.Configuration;

import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;

public class NecessaryEnablingsolver extends KInductionSolver {

	public NecessaryEnablingsolver(Configuration smtConfig, Solver engine) {
		super(smtConfig, engine, false);
	}
	

	public int [] computeAbling (int target, boolean isEnabler) {
		int [] toret = new int[nbTransition];
		
		// push a context
		solver.push(1);
		
		if (isPresburger) {
			addFlowConstraints(1);
		}
		
		if (isEnabler) {
			// assert not enabled in initial
			solver.assertExpr(efactory.fcn(efactory.symbol("not"),
					efactory.fcn(efactory.symbol(ENABLED+target), efactory.numeral(0))));

			// assert enabled in successor step 1
			solver.assertExpr(
					efactory.fcn(efactory.symbol(ENABLED+target), efactory.numeral(1)));		
		} else {
			// assert enabled in initial
			solver.assertExpr(
					efactory.fcn(efactory.symbol(ENABLED+target), efactory.numeral(0)));

			// assert not enabled in successor step 1
			solver.assertExpr(efactory.fcn(efactory.symbol("not"),
					efactory.fcn(efactory.symbol(ENABLED+target), efactory.numeral(1))));		
			
		}
		for (int i =0; i < nbTransition ; i++) {
			solver.push(1);
			solver.assertExpr(efactory.fcn(efactory.symbol("tr"+i),efactory.numeral(0)));
		
			Logger.getLogger("fr.lip6.move.gal").fine("Checking enabling of "+i);
			Result res = checkSat();
			if (res == Result.SAT) {
				toret[i] = 1;
			} else if (res == Result.UNSAT){
				toret[i] = 0;
			} else {
				throw new RuntimeException("SMT solver raised an error in enabler solving :"+res);
			}
			solver.pop(1);
			i++;
		}

		solver.pop(1);
		return toret;
	}

	
	
	public int [] computeDisablers (int target) {
		return computeAbling(target, false);
	}
	
	public int [] computeEnablers (int target) {
		return computeAbling(target, true);
	}

	
	
}
