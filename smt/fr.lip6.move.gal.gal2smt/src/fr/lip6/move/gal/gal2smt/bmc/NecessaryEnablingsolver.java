package fr.lip6.move.gal.gal2smt.bmc;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.smtlib.IExpr;
import org.smtlib.SMT.Configuration;

import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.semantics.DependencyMatrix;
import fr.lip6.move.gal.semantics.INextBuilder;

public class NecessaryEnablingsolver extends KInductionSolver {

	private int sat=0;
	private int unsat=0;
	private long timestamp=0;
	
	public NecessaryEnablingsolver(Configuration smtConfig, Solver engine) {
		super(smtConfig, engine, false);		
	}
	
	private void clearStats() {
		timestamp=System.currentTimeMillis();
		sat=0;
		unsat=0;
	}
	
	private void printStats() {
		Logger.getLogger("fr.lip6.move.gal").info("Computation took " + ( System.currentTimeMillis() - timestamp ) + " ms. Total solver calls (SAT/UNSAT): "+ (sat+unsat) + "("+ sat + "/" + unsat + ")");
	}
	
	@Override
	public void init(INextBuilder nextb) {
		super.init(nextb);
		addKnownInvariants(0);
	}
	
	public List<int[]> computeAblingMatrix (boolean isEnabler, DependencyMatrix dm) {
		List<int[]> matrix = new ArrayList<int[]>(nbTransition);

		Logger.getLogger("fr.lip6.move.gal").info("Computing symmetric may "+ (isEnabler ? "enable" : "disable")+ " matrix : " + nbTransition + " transitions.");
		clearStats();
		for (int tindex = 0 ; tindex < nbTransition ; tindex++) {
			if (isEnabler)
				matrix.add(computeEnablers(tindex,dm));
			else
				matrix.add(computeDisablers(tindex,dm));
			System.out.println("Completed :" + tindex + "/" + nbTransition);
			printStats();
		}
		printStats();
		return matrix;
	}

	private int [] computeAbling (int target, boolean isEnabler, DependencyMatrix dm) {
		int [] toret = new int[nbTransition];
		
		solver.push(1);
		//addKnownInvariants(1);
		
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
			if (! dm.getWrite(i).intersects(dm.getRead(target)) ) {
				// no need for SAT call
				toret[i] = 0;				
			} else {
				solver.push(1);
				solver.assertExpr(efactory.fcn(efactory.symbol(TRANSNAME+i),efactory.numeral(0)));

				Result res = checkSat();
				// Logger.getLogger("fr.lip6.move.gal").info("Checking if "+i +" "+ (isEnabler ? "enables" : "disables") +"  "+target + " :" + res);
				if (res == Result.SAT) {
					toret[i] = 1;
					sat++;
				} else if (res == Result.UNSAT){
					toret[i] = 0;
					unsat++;
				} else {
					throw new RuntimeException("SMT solver raised an error in enabler solving :"+res);
				}
				solver.pop(1);
			}
		}

		solver.pop(1);
		return toret;
	}

	/**
	 * Answers true if there are potential states that allow both t1 and t2 to fire.
	 * If (t1==t2), this is changed to "are there states where the sequence t1.t1 is possible ?"
	 * @param t1 an index for t1
	 * @param t2 an index for t2
	 * @return
	 */
	public boolean canBeCoenabled (int t1,int t2) {
		// push a context
		solver.push(1);
		
		if (t1 != t2) {
			// assert t1 enabled in initial
			solver.assertExpr(
					efactory.fcn(efactory.symbol(ENABLED+t1), efactory.numeral(0)));

			// assert t2 enabled in initial
			solver.assertExpr(
					efactory.fcn(efactory.symbol(ENABLED+t2), efactory.numeral(0)));
		} else {
			// assert t1 fired from initial state[0]
			solver.assertExpr(
					efactory.fcn(efactory.symbol(TRANSNAME+t1), efactory.numeral(0)));

			// assert t1 enabled in s[1]
			solver.assertExpr(
					efactory.fcn(efactory.symbol(ENABLED+t1), efactory.numeral(1)));
		}
		
		Result res = checkSat();
		//Logger.getLogger("fr.lip6.move.gal").info("Checking co enabling of "+t1 + " and " + t2 + " : " + res);
		solver.pop(1);
		if (res == Result.SAT) {
			sat++;
			return true;
		} else if (res == Result.UNSAT){
			unsat++;
			return false;
		} else {
			throw new RuntimeException("SMT solver raised an error in enabler solving :"+res);
		}
	}
	
	
	
	private int [] computeDisablers (int target, DependencyMatrix dm) {
		return computeAbling(target, false, dm);
	}
	
	private int [] computeEnablers (int target, DependencyMatrix dm) {
		return computeAbling(target, true, dm);
	}

	public List<int[]> computeCoEnablingMatrix() {
		List<int[]> coEnabled = new ArrayList<>(nbTransition);
		for (int tindex = 0; tindex < nbTransition ; tindex++) {
			coEnabled.add(new int[nbTransition]);
		}
		Logger.getLogger("fr.lip6.move.gal").info("Computing symmetric co enabling matrix : " + nbTransition + " transitions.");
		clearStats();
		for (int t1 = 0 ; t1 < nbTransition ; t1++) {
			for (int t2 = t1 ; t2 < nbTransition ; t2++) {
				if ( canBeCoenabled(t1, t2) ) {
					coEnabled.get(t1)[t2] = 1;
					coEnabled.get(t2)[t1] = 1;
				}
			}
			System.out.println("Completed :" + t1 + "/" + nbTransition);
			printStats();
		}
		printStats();
		return coEnabled;
	}

	
	
	public List<int[]> computeDoNotAccord (List<int[]> coEnabled, List<int[]> mayDisable, DependencyMatrix dm) {
		List<int[]> dnaMatrix = new ArrayList<>(nbTransition);
		for (int tindex = 0; tindex < nbTransition ; tindex++) {
			dnaMatrix.add(new int[nbTransition]);
		}
		// push a context
		solver.push(1);
		

		// We need 4 states : s1, s2, s3, s4, s5
		IExpr s1 = accessStateAt(0);
		IExpr s2 = accessStateAt(1);
		IExpr s3 = accessStateAt(2);
		IExpr s4 = accessStateAt(3);
		IExpr s5 = accessStateAt(4);
		
		// s1 and s2 already constrained by invariants
		addKnownInvariants(s3);
		addKnownInvariants(s4);
		addKnownInvariants(s5);

		
		Logger.getLogger("fr.lip6.move.gal").info("Computing Do-Not-Accords matrix : " + nbTransition + " transitions.");
		clearStats();
		for (int t1 = 0 ; t1 < nbTransition ; t1++) {
			for (int t2 = t1+1 ; t2 < nbTransition ; t2++) {
				if (coEnabled.get(t1)[t2] == 0) {
					// we meet accords requirement : the implication is true if there is no coenabling
					// put 0 in dna(t1,t2) = do nothing
					continue;
				}
				if ( ! ( dm.getRead(t1).intersects(dm.getWrite(t2))
						 || dm.getWrite(t1).intersects(dm.getWrite(t2))
						 || dm.getWrite(t1).intersects(dm.getRead(t2)))) {
					// these transitions cannot interfere, at best they share some read only variables
					// we meet accords requirement 
					// put 0 in dna(t1,t2) = do nothing
					continue;
				}
				if (mayDisable.get(t1)[t2]==1 || mayDisable.get(t2)[t1]==1) {
					// mutual disabling criterion not met
					// t1 and t2 do not accord
					dnaMatrix.get(t1)[t2] = 1;
					dnaMatrix.get(t2)[t1] = 1;
					continue;
				}
				solver.push(1);
				
				// Express  assertions 
				// s1  --t1--> s2
				//     --t2--> s3
				solver.assertExpr(efactory.fcn(efactory.symbol(TRANSSRC+t1), s1, s2));
				solver.assertExpr(efactory.fcn(efactory.symbol(TRANSSRC+t2), s1, s3));
				

				// these additional conditions enforce a mutual disabling test, already done above
				//				efactory.fcn(efactory.symbol("or"), 
				//						efactory.fcn(efactory.symbol("not"),  efactory.fcn(efactory.symbol(ENABLEDSRC+t1), s3)),
				//						efactory.fcn(efactory.symbol("not"),  efactory.fcn(efactory.symbol(ENABLEDSRC+t2), s2)),

				// add commutativity test
				// s1  --t1--> s2 --t2--> s5
				//     --t2--> s3 --t1--> s4
				// can s4 != s5 be SAT ?
				// this test relies on deterministic behavior of transitions.
				IExpr tocheck = efactory.fcn(efactory.symbol("and"), 
								efactory.fcn(efactory.symbol(TRANSSRC+t1), s3, s4),
								efactory.fcn(efactory.symbol(TRANSSRC+t2), s2, s5),
								efactory.fcn(efactory.symbol("not"), efactory.fcn(efactory.symbol("="), s4, s5))
								);						
				solver.assertExpr(tocheck);
				
				Result res = checkSat();
			//	Logger.getLogger("fr.lip6.move.gal").info("Checking Do Not Accords relation of "+t1 + " and " + t2 + " : " + res);
				
				solver.pop(1);
				
				if (res == Result.SAT) {
					// we found s4 != s5 through t1.t2 and t2.t1
					dnaMatrix.get(t1)[t2] = 1;
					dnaMatrix.get(t2)[t1] = 1;
					sat++;
				} else if (res == Result.UNSAT){
					// we could not find s4 != s5 through t1.t2 and t2.t1
					// so they are indeed commutative, accords is true
					dnaMatrix.get(t1)[t2] = 0;
					dnaMatrix.get(t2)[t1] = 0;
					unsat++;
				} else {
					throw new RuntimeException("SMT solver raised an error in enabler solving :"+res);
				}
			}
		}
		
		solver.pop(1);
		printStats();
		
		return dnaMatrix;
	}
	
	
}
