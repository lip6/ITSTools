package fr.lip6.move.gal.gal2smt.bmc;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.smtlib.SMT.Configuration;

import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;

public class NecessaryEnablingsolver extends KInductionSolver {

	public NecessaryEnablingsolver(Configuration smtConfig, Solver engine) {
		super(smtConfig, engine, false);
	}
	
	public List<int[]> computeAblingMatrix (boolean isEnabler) {
		List<int[]> matrix = new ArrayList<int[]>(nbTransition);

		// push a context
		solver.push(1);
		
		if (isPresburger) {
			addFlowConstraints(1);
		}

		for (int tindex = 0 ; tindex < nbTransition ; tindex++) {
			if (isEnabler)
				matrix.add(computeEnablers(tindex));
			else
				matrix.add(computeDisablers(tindex));
		}

		solver.pop(1);

		return matrix;
	}
	
	private int [] computeAbling (int target, boolean isEnabler) {
		int [] toret = new int[nbTransition];
		
		solver.push(1);
		
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
			solver.assertExpr(efactory.fcn(efactory.symbol(TRANSNAME+i),efactory.numeral(0)));
		
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
		}

		solver.pop(1);
		return toret;
	}

	public boolean canBeCoenabled (int t1,int t2) {
		// push a context
		solver.push(1);
		
		// assert enabled in initial
		solver.assertExpr(
				efactory.fcn(efactory.symbol(ENABLED+t1), efactory.numeral(0)));

		solver.assertExpr(
				efactory.fcn(efactory.symbol(ENABLED+t2), efactory.numeral(0)));
		
		Result res = checkSat();
		Logger.getLogger("fr.lip6.move.gal").info("Checking co enabling of "+t1 + " and " + t2 + " : " + res);
		solver.pop(1);
		if (res == Result.SAT) {
			return true;
		} else if (res == Result.UNSAT){
			return false;
		} else {
			throw new RuntimeException("SMT solver raised an error in enabler solving :"+res);
		}
	}
	
	
	
	private int [] computeDisablers (int target) {
		return computeAbling(target, false);
	}
	
	private int [] computeEnablers (int target) {
		return computeAbling(target, true);
	}

	
	
}
