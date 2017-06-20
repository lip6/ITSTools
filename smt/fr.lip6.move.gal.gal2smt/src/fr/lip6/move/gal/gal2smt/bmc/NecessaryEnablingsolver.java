package fr.lip6.move.gal.gal2smt.bmc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.smtlib.IExpr;
import org.smtlib.IPrinter;
import org.smtlib.IResponse;
import org.smtlib.ISolver;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.ISort.IApplication;
import org.smtlib.SMT.Configuration;
import org.smtlib.command.C_assert;
import org.smtlib.command.C_define_fun;
import org.smtlib.impl.Script;
import org.smtlib.impl.Sort;

import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.semantics.DependencyMatrix;
import fr.lip6.move.gal.semantics.INext;
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
	
	private long lastPrint = 0;
	private void printStats(boolean force, String message) {
		// unless force will only report every 1000 ms
		long time = System.currentTimeMillis();
		long duration = time - timestamp ;
		if (! force) {
			if (time - lastPrint < 1000) {
				return;
			}
		}
		Logger.getLogger("fr.lip6.move.gal").info("Computation of "+ message +" took " + duration + " ms. Total solver calls (SAT/UNSAT): "+ (sat+unsat) + "("+ sat + "/" + unsat + ")");
		lastPrint = time;
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
			printStats(false, (isEnabler ? "enable" : "disable")+ " matrix completed :" + tindex + "/" + nbTransition);
		}
		printStats(true, "Complete "+ (isEnabler ? "enable" : "disable")+ " matrix.");
		return matrix;
	}

	private ISolver buildSolver() {
		ISolver solver = engine.getSolver(conf);
		// start the solver
		IResponse err = solver.start();
		if (err.isError()) {
			throw new RuntimeException("Could not start solver "+ engine+" from path "+ conf.executable + " raised error :"+err);
		}
		err = solver.set_logic("QF_AUFLIA", null);
		if (err.isError()) {
			throw new RuntimeException("Could not set logic :"+err);
		}
		return solver;
	}
	
	private int [] computeAbling (int target, boolean isEnabler, DependencyMatrix dm) {
		int [] toret = new int[nbTransition];
		ISolver solver = buildSolver();

		Script scriptInit = new Script();
		IApplication ints = sortfactory.createSortExpression(efactory.symbol("Int"));
		// an array, indexed by integers, containing integers : (Array Int Int) 
		IApplication arraySort = sortfactory.createSortExpression(efactory.symbol("Array"), ints, ints);
		
		ISymbol s0 = efactory.symbol("s0");
		ISymbol s1 = efactory.symbol("s1");
		
		// declare two states : two different arrays of Int
		scriptInit.add(
				new org.smtlib.command.C_declare_fun(
						s0,
						Collections.emptyList(),
						arraySort								
						)
				);
		scriptInit.add(
				new org.smtlib.command.C_declare_fun(
						s1,
						Collections.emptyList(),
						arraySort								
						)
				);
		
		// a translator to map them to SMT syntax
		final GalExpressionTranslator et = new GalExpressionTranslator(conf);

		{
			// the relevant transitions in manipulable form
			List<INext> ttarget = nb.getDeterministicNext().get(target);
			// do the translation as a Visit of INext : building assertions over SRC
			NextTranslator translator = new NextTranslator(efactory.symbol("src"), et);

			// To hold all constraints corresponding to this transition
			List<IExpr> conds = new ArrayList<IExpr>();			

			for (INext statement : ttarget) {
				IExpr cond = statement.accept(translator);
				// the visit returns a new condition (Predicate case) or updates its state to reflect assignment
				if (cond != null)
					conds.add(cond);
			}

			// build up the full boolean function for the transition
			IExpr bodyExpr = efactory.fcn(efactory.symbol("and"), conds);
			if (conds.size() == 1) {
				bodyExpr = conds.get(0);
			} else if (conds.isEmpty()) {
				bodyExpr = efactory.symbol("true");
			}

			// declare enabling predicate for t1				
			// enabling in a given state: enabledsrcXX ( int [] state ) : bool
			ISymbol enabsrcname = efactory.symbol(ENABLEDSRC+target);
			C_define_fun enabsrctr = new org.smtlib.command.C_define_fun(
					enabsrcname,    // name
					Collections.singletonList(efactory.declaration(efactory.symbol("src"), arraySort)), // param (int [] state) 
					Sort.Bool(), // return type
					bodyExpr); // effect : assertions over src
			scriptInit.commands().add(enabsrctr);
		}
		if (isEnabler) {
			// assert not enabled in initial
			scriptInit.add(new C_assert( efactory.fcn(efactory.symbol("not"),
					efactory.fcn(efactory.symbol(ENABLEDSRC+target), s0))));

			// assert enabled in successor step 1
			scriptInit.add(new C_assert( 
					efactory.fcn(efactory.symbol(ENABLEDSRC+target), s1)));		
		} else {
			// assert enabled in initial
			scriptInit.add(new C_assert( 
					efactory.fcn(efactory.symbol(ENABLEDSRC+target), s0)));

			// assert not enabled in successor step 1
			scriptInit.add(new C_assert( efactory.fcn(efactory.symbol("not"),
					efactory.fcn(efactory.symbol(ENABLEDSRC+target), s1))));		
			
		}
		scriptInit.execute(solver);
		
		
		for (int i =0; i < nbTransition ; i++) {
			if (! dm.getWrite(i).intersects(dm.getRead(target)) ) {
				// no need for SAT call
				toret[i] = 0;		
			} else {
				
//				// find relevant support : union of  :
//				// read and write sets of the transition we are examining i
//				BitSet full = (BitSet) dm.getWrite(i).clone();
//				full.or(dm.getRead(i));
//				// read set of the target transition (write set is irrelvant, we don't try to fire it)
//				full.or(dm.getRead(target));
				
				// build a reindexer
				// add relevant invariants				
				Script script = new Script();
				{
					// the relevant transitions in manipulable form
					List<INext> tti= nb.getDeterministicNext().get(i);
					// do the translation as a Visit of INext : building assertions over SRC
					NextTranslator translator = new NextTranslator(s0, et);

					// To hold all constraints corresponding to this transition
					List<IExpr> conds = new ArrayList<IExpr>();			

					for (INext statement : tti) {
						IExpr cond = statement.accept(translator);
						// the visit returns a new condition (Predicate case) or updates its state to reflect assignment
						if (cond != null)
							conds.add(cond);
					}
					
					IExpr guard;
					if (i != target) {
						// build up the full boolean function for the transition
						IExpr bodyExpr = efactory.fcn(efactory.symbol("and"), conds);
						if (conds.size() == 1) {
							bodyExpr = conds.get(0);
						} else if (conds.isEmpty()) {
							bodyExpr = efactory.symbol("true");
						}
						guard = bodyExpr;
					} else {
						guard = efactory.fcn(efactory.symbol(ENABLEDSRC+i), s0);
					}
					
					script.add(new C_assert(  efactory.fcn(efactory.symbol("and"),
							// enabledSrcXX ( src ) 
							guard, 
							// dst = image(src)
							efactory.fcn(efactory.symbol("="), translator.getState(), s1))));

				}				
				// we should be good to go
				// compute conds for target in this new index base
				
				
				// compute conds + state for tr i in this index
				
				// s[0] |= enab(target)
				// s[0], s[1] |= fire(i)
				// s[1] |= ! enab(target)
				
				solver.push(1);
				script.execute(solver);
				
//				for ( ICommand c : script.commands()) {
//					System.out.println(c);
//				}
				
				IResponse res = solver.check_sat();
				solver.pop(1);
				
				if (res.isError()) {
					throw new RuntimeException("SMT solver raised an exception or timeout.");
				}
				IPrinter printer = conf.defaultPrinter;
				String textReply = printer.toString(res);
				if ("sat".equals(textReply)) {
					toret[i] = 1;
					sat++;
				} else if ("unsat".equals(textReply)) {
					toret[i] = 0;
					unsat++;
				} else {
					throw new RuntimeException("SMT solver raised an error :" + textReply);
				}
											
			}
		}
		solver.exit();
		
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
			printStats(false,"co-enabling matrix(" + t1 + "/" + nbTransition +")");
		}
		printStats(true,"Finished co-enabling matrix.");
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
		printStats(true,"Completed DNA matrix.");
		
		return dnaMatrix;
	}
	
	
}
