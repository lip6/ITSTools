package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.smtlib.SMT;
import org.smtlib.SMT.Configuration;

import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.gal2smt.bmc.BMCSolver;
import fr.lip6.move.gal.gal2smt.bmc.KInductionSolver;
import fr.lip6.move.gal.gal2smt.cover.CoverabilityChecker;
import fr.lip6.move.gal.gal2smt.smt.IBMCSolver;
import fr.lip6.move.gal.instantiate.GALRewriter;

public class Gal2SMTFrontEnd {

	private List<ISMTObserver> callbacks = new ArrayList<ISMTObserver>();

	
	private Solver engine;
	private int timeout;
	private SMT smt;
	
	public Gal2SMTFrontEnd(String solverPath, Solver engine, int timeout) {
		smt = new SMT();
		smt.smtConfig.executable = solverPath;
		this.engine = engine;
		this.timeout = timeout;
	}
	
	public Gal2SMTFrontEnd(String solverPath, Solver engine) {
		this(solverPath,engine,300);
	}
	
	public void addObserver (ISMTObserver callback) {
		callbacks.add(callback);
	}

	public void remObserver (ISMTObserver callback) {
		callbacks.remove(callback);
	}

	public void notifyObservers(Property prop, Result res, int depth) {
		for (ISMTObserver callback : callbacks) {
			callback.notifyResult(prop,res, depth);
		}
	}
	
	public Map<String, Result> checkProperties (Specification spec, String folder) throws Exception {
		GALRewriter.flatten(spec, true);
		
//		getLog().info("Translation to SMT took " + ( System.currentTimeMillis() - timestamp ) + " ms");		

		Configuration smtConfig = smt.smtConfig;
		smtConfig.timeout = timeout;
		boolean withAllDiff = false;
		
		Map<String, Result> result = new HashMap<String, Result>();
		List<Property> todo = new ArrayList<Property>(spec.getProperties());
		
		runCoverability(todo, smtConfig, spec, result);

		long timestamp = System.currentTimeMillis();			
		IBMCSolver bmc = new BMCSolver(smtConfig, engine,withAllDiff);
		bmc.init(spec);		

		// check tautology with false
		List<Property> taut = new ArrayList<Property>();
		for (Property prop : todo) {
			// check at depth 0
			Result bmcres = bmc.verify(prop);
			if (bmcres == Result.UNSAT) {
				Result res;
				// property cannot be realized, in any state, it is tautology for "false"
				if (prop.getBody() instanceof ReachableProp) {
					res  = Result.FALSE;					
					getLog().info(" Result for false tautology is UNSAT, reachability predicate is unrealizable " + prop.getName());
				} else {
					res = Result.TRUE;
					getLog().info(" Result for false tautology is UNSAT, invariant/never predicate is unrealizable " + prop.getName());						
				}
				notifyObservers(prop, res, -1);
				result.put(prop.getName(), res);
				taut.add(prop);
			}
		}
		getLog().info(" Ran tautology test, simplified " +taut.size() + " / " + todo.size() +" in " + (System.currentTimeMillis()- timestamp)+ " ms.");						
		todo.removeAll(taut);
		timestamp = System.currentTimeMillis();

		// now we have done tautology, add initial constraint
		bmc.assertInitialState(spec);
		
		runBMC(bmc,todo,50,result);
		
		runKInduction(smtConfig,engine,todo,30,result, spec);
		return result;
	}

//			
//				// a script
//				IScript script = new Script();
//				
//				// old school
//				/* Build a reachability problem */
//				builder.buildReachabilityProblem(prop, depth, script.commands());
//				boolean isSat = solve(script);
//				
//			//	getLog().info(script.commands().toString());
//				/* Invoke solver, new school */
////				boolean isSat = solve(prop, depth, builder);
//
//				if (isSat) {
//					if (prop.getBody() instanceof ReachableProp) {
//						res  = Result.TRUE;					
//						getLog().info(" Result is SAT, found a trace to state matching reachability predicate " + prop.getName());
//					} else {
//						res = Result.FALSE;
//						getLog().info(" Result is SAT, found a counter-example trace to a state that contradicts invariant/never predicate " + prop.getName());						
//					}
//					done.add(prop);
//				} else {
//					res = Result.UNSAT;
//					// try to disprove property
//
//					// a script
//					IScript inductionScript = new Script();
//					
//					// old school
//					builder.buildInductionProblem(prop, depth, inductionScript.commands());
//			//		getLog().info(inductionScript.commands().toString());
//					boolean isSatInduction = solve(inductionScript);
//					if (isSatInduction) {
//						// non conclusive we might be starting from unreachable states
//					} else {
//						if (prop.getBody() instanceof ReachableProp) {
//							res  = Result.FALSE;					
//							getLog().info(" Induction result is UNSAT, proved UNreachability of reachability predicate " + prop.getName());
//						} else if (prop.getBody() instanceof NeverProp) {
//							res = Result.TRUE;
//							getLog().info(" Induction result is UNSAT, proved never invariant  " + prop.getName());						
//						} else {
//							res = Result.TRUE;
//							getLog().info(" Induction result is UNSAT, proved positive invariant  " + prop.getName());						
//						}
//						// we disproved for all n !
//						getLog().info(" Induction result is UNSAT, successfully proved induction at step "+ depth +" for " + prop.getName());
//						done.add(prop);
//					}
//				}
//				notifyObservers(prop, res, depth);
//
//				result.put(prop.getName(), res);
//
//				long solverTime =  System.currentTimeMillis() - timestamp ;
//				getLog().info("SMT solution for property "+ prop.getName() +"("+ res +") depth K="+ depth +" took " + solverTime + " ms");		
//
//				/* Ajout du resultat */
//				result.put(prop.getName(), res);
//			
////			/* supprime les fichiers .txt.gal */
////			SmtSerializationUtil.deleteFiles(directory, ".txt.gal"); 
//			}
//			
//			// remove SAT properties
//			todo.removeAll(done);
//
//			///// Handle test for termination
//			// a script
//			boolean isDepthEnough = checkMaxDepth (depth, builder);
//
//			if (isDepthEnough) {
//				// we are done !
//				return result;
//			} else {				
//				// SAT means k is less than breadth of state space
//				continue;
//			}
//		}
//
//		return result;
//	}
	
	private void runKInduction(Configuration smtConfig, Solver engine2,
			List<Property> todo, int i, Map<String, Result> result, Specification spec) {
		if (todo.isEmpty()) { return ; }
		long timestamp = System.currentTimeMillis();
		KInductionSolver kind = new KInductionSolver(smtConfig, engine,true);
		kind.init(spec);		
		// 300 secs timeout for full loop
		long loopstamp = System.currentTimeMillis();
		for (int depth = 0 ; depth <= 50 && ! todo.isEmpty() && ! timeout(loopstamp); depth += 1 ) {
			loopstamp = System.currentTimeMillis();
			List<Property> done = new ArrayList<Property>();

			/* Pour chaque property */
			for (Property prop : todo) {
				timestamp = System.currentTimeMillis();
				if (timeout(loopstamp)) {
					break;
				}

				Result 	res = Result.UNKNOWN;
				// try to disprove property

				Result kindres = kind.verify(prop);
					
				if (kindres == Result.SAT) {
						// non conclusive we might be starting from unreachable states
				} else {
					if (prop.getBody() instanceof ReachableProp) {
						res  = Result.FALSE;					
						getLog().info(" Induction result is UNSAT, proved UNreachability of reachability predicate " + prop.getName());
					} else if (prop.getBody() instanceof NeverProp) {
						res = Result.TRUE;
						getLog().info(" Induction result is UNSAT, proved never invariant  " + prop.getName());						
					} else {
						res = Result.TRUE;
						getLog().info(" Induction result is UNSAT, proved positive invariant  " + prop.getName());						
					}
					// we disproved for all n !
					getLog().info(" Induction result is UNSAT, successfully proved induction at step "+ depth +" for " + prop.getName());
					done.add(prop);
				}
				notifyObservers(prop, res, depth);
				result.put(prop.getName(), res);

				long solverTime =  System.currentTimeMillis() - timestamp ;
				getLog().info("KInduction solution for property "+ prop.getName() +"("+ res +") depth K="+ depth +" took " + solverTime + " ms");		

				/* Ajout du resultat */
				result.put(prop.getName(), res);
			} // foreach prop

			// remove Proved properties at this depth
			todo.removeAll(done);

			kind.incrementDepth();
		}
		
		
	}

	private void runBMC(IBMCSolver bmc, List<Property> todo, int maxd, Map<String, Result> result) throws RuntimeException {
		try {
		
		// 300 secs timeout for full loop
		long loopstamp = System.currentTimeMillis();
		for (int depth = 0 ; depth <= maxd && ! todo.isEmpty() && ! timeout(loopstamp); depth ++) {
			loopstamp = System.currentTimeMillis();
			List<Property> done = new ArrayList<Property>();

			/* Pour chaque property */
			for (Property prop : todo) {
				long timestamp = System.currentTimeMillis();
				if (timeout(loopstamp)) {
					break;
				}


				Result res = Result.UNKNOWN;
				
				Result bmcres = bmc.verify(prop);
				if (bmcres == Result.SAT) {
					if (prop.getBody() instanceof ReachableProp) {
						res  = Result.TRUE;					
						getLog().info(" Result is SAT, found a trace to state matching reachability predicate " + prop.getName());
					} else {
						res = Result.FALSE;
						getLog().info(" Result is SAT, found a counter-example trace to a state that contradicts invariant/never predicate " + prop.getName());						
					}
					done.add(prop);					
				} else if (bmcres == Result.UNSAT) {
					res = Result.UNSAT;
				}
				notifyObservers(prop, res, depth);
				long solverTime =  System.currentTimeMillis() - timestamp ;
				getLog().info("BMC solution for property "+ prop.getName() +"("+ res +") depth K="+ depth +" took " + solverTime + " ms");		

				/* Ajout du resultat */
				result.put(prop.getName(), res);
			} // foreach prop

			// remove Proved properties at this depth
			todo.removeAll(done);

			bmc.incrementDepth();
			
		}
		} catch (RuntimeException e) {
			getLog().info("BMC solving timed out at depth " + bmc.getDepth());
		} 
	}

	private void runCoverability(List<Property> todo, Configuration smtConfig, Specification spec, Map<String, Result> result) {
		long time = System.currentTimeMillis();
		// first try to disprove property using Marking Equation
		CoverabilityChecker covc = new CoverabilityChecker(engine, smtConfig);
		covc.init(spec);
		List<Property> cov = new ArrayList<Property>();
		for (Property prop : todo) {
			Result covres = covc.verify(prop);
			if (covres == Result.UNSAT) {
				Result res;
				// property cannot be realized, in any state satisfying marking equation
				if (prop.getBody() instanceof ReachableProp) {
					res  = Result.FALSE;					
					getLog().info(" Result for coverability is UNSAT, reachability predicate is unrealizable " + prop.getName());
				} else {
					res = Result.TRUE;
					getLog().info(" Result for coverability is UNSAT, invariant/never predicate holds." + prop.getName());
				}
				notifyObservers(prop, res, -1);
				result.put(prop.getName(), res);
				cov.add(prop);
			}
		}
		covc.exit();
		getLog().info("Coverability managed to conclude for "+cov.size() + " / " + todo.size() +" in " + (System.currentTimeMillis() - time) + " ms.");
		todo.removeAll(cov);
	}

	private boolean timeout(long loopstamp) {
		// TODO Auto-generated method stub
		return ( System.currentTimeMillis() - loopstamp >= timeout*1000 );
	}

//	private boolean checkMaxDepth(int depth, SMTBuilder builder) throws Exception {
//		
//		return depth >= 128;
////		IScript script = new Script();
////		long timestamp = System.currentTimeMillis();
////
////		// test for full exploration
////		builder.buildMaxDepthReachedProblem(depth, script.commands());
////
////		/* Invoke solver */
////		IResponse response = solve(script);
////
////		getLog().info("SMT solution for MaxDepthReached ("+ (response.isOK()?Result.SAT:Result.UNSAT) +") for depth K="+ depth +"took " + (System.currentTimeMillis() - timestamp) + " ms");		
////		
////		// SAT means k is less than breadth of state space
////		// UNSAT means k is enough
////		return ! response.isOK();
//	}

	
	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}
	
}
