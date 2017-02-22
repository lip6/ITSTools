package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.smtlib.SMT;
import org.smtlib.SMT.Configuration;

import fr.lip6.move.gal.False;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.gal2smt.bmc.KInductionSolver;
import fr.lip6.move.gal.gal2smt.bmc.NecessaryEnablingsolver;
import fr.lip6.move.gal.gal2smt.bmc.NextBMCSolver;
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
		//smt.smtConfig.logicPath = "/data/ythierry/workspaces/neon/lip6.smtlib.SMT/logics";
		this.engine = engine;
		this.timeout = timeout;
	}
	
	public Gal2SMTFrontEnd(String solverPath, Solver engine) {
		// 5 minute = 300 seconds
		this(solverPath,engine,300000);
	}
	
	public void addObserver (ISMTObserver callback) {
		callbacks.add(callback);
	}

	public void remObserver (ISMTObserver callback) {
		callbacks.remove(callback);
	}

	public void notifyObservers(Property prop, Result res, String desc) {
		for (ISMTObserver callback : callbacks) {
			callback.notifyResult(prop,res, desc);
		}
	}
	
	public NecessaryEnablingsolver buildNecessaryEnablingSolver () {
		final Configuration smtConfig = smt.smtConfig;
		smtConfig.timeout = timeout;
		return new NecessaryEnablingsolver(smtConfig, engine);
	}
	
	public Map<String, Result> checkProperties (final Specification spec, String folder) throws Exception {
		GALRewriter.flatten(spec, true);
		
//		getLog().info("Translation to SMT took " + ( System.currentTimeMillis() - timestamp ) + " ms");		

		final Configuration smtConfig = smt.smtConfig;
		smtConfig.timeout = timeout;
		boolean withAllDiff = false;
		
		final Map<String, Result> result = new ConcurrentHashMap<String, Result>();
		final List<Property> todo = new ArrayList<Property>(spec.getProperties());
		for (Property prop : todo) {
			result.put(prop.getName(), Result.UNKNOWN);
		}
		
		//final Map<Property, Integer> expectedLength = runCoverability(todo, smtConfig, spec, result);
		
//		for (Entry<Property, Integer> ent : expectedLength.entrySet()) {
//			System.out.println("Property : " + ent.getKey().getName() + " expected :" + ent.getValue());
//		}

		long timestamp = System.currentTimeMillis();			
		final IBMCSolver bmc = new NextBMCSolver(smtConfig, engine,withAllDiff);
		bmc.init(spec);		

		// check tautology with false
		List<Property> taut = new ArrayList<Property>();
		for (Property prop : todo) {
			// check trivial true/false property
			if (prop.getBody() instanceof SafetyProp) {
				SafetyProp sp = (SafetyProp) prop.getBody();
				if (sp.getPredicate() instanceof True) {
					Result res;
					if (sp instanceof ReachableProp || sp instanceof InvariantProp) {
						res = Result.TRUE;
					} else {
						res = Result.FALSE;
					}					
					notifyObservers(prop, res, "TAUTOLOGY");
					result.put(prop.getName(), res);
					taut.add(prop);
					continue;
				} else if (sp.getPredicate() instanceof False) {
					Result res;
					if (sp instanceof ReachableProp || sp instanceof InvariantProp) {
						res = Result.FALSE;
					} else {
						res = Result.TRUE;
					}					
					notifyObservers(prop, res, "TAUTOLOGY");
					result.put(prop.getName(), res);
					taut.add(prop);
					continue;
				}
			}
			
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
				notifyObservers(prop, res, "TAUTOLOGY");
				result.put(prop.getName(), res);
				taut.add(prop);
			}
		}
		getLog().info(" Ran tautology test, simplified " +taut.size() + " / " + todo.size() +" in " + (System.currentTimeMillis()- timestamp)+ " ms.");						
		todo.removeAll(taut);
		timestamp = System.currentTimeMillis();

		// now we have done tautology, add initial constraint
		bmc.assertInitialState();
		
		final Set<Property> done = ConcurrentHashMap.newKeySet();
		Thread bmcthread = new Thread(new Runnable() {			
			@Override
			public void run() {
				runBMC(bmc,todo,100,result,done, new HashMap<>()/* expectedLength*/ );				
			}
		});

		Thread kindthread = new Thread(new Runnable() {			
			@Override
			public void run() {
				runKInduction(smtConfig,engine,new ArrayList<Property>(todo),30,result, spec, done);
			}
		});
		
		bmcthread.start();
		kindthread.start();
		try {
			bmcthread.join();
			kindthread.join();
		} catch (InterruptedException ie) {
			getLog().warning("Interrupting SMT solver.");
			bmcthread.interrupt();
			kindthread.interrupt();
			bmcthread.join();
			kindthread.join();
		}

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
			List<Property> todo, int i, Map<String, Result> result, Specification spec, Set<Property> done) {
		if (todo.isEmpty()) { return ; }
		long timestamp = System.currentTimeMillis();
		KInductionSolver kind = new KInductionSolver(smtConfig, engine,true);
		kind.init(spec);		
		// 300 secs timeout for full loop
		long loopstamp = System.currentTimeMillis();
		for (int depth = 0 ; depth <= 50 && ! todo.isEmpty() && ! timeout(loopstamp); depth += 1 ) {
			loopstamp = System.currentTimeMillis();

			/* Pour chaque property */
			for (Property prop : todo) {
				if (done.contains(prop)) {
					continue;
				}
				timestamp = System.currentTimeMillis();
				if (timeout(loopstamp)) {
					break;
				}

				Result 	res = Result.UNKNOWN;
				// try to disprove property

				Result kindres = kind.verify(prop);
					
				if (kindres == Result.SAT) {
					getLog().info(" Induction result is SAT, non conclusive we might be starting from unreachable states" + prop.getName());
					res= Result.SAT;
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
				notifyObservers(prop, res, "K_INDUCTION("+depth+")");
				result.put(prop.getName(), res);

				long solverTime =  System.currentTimeMillis() - timestamp ;
				getLog().info("KInduction solution for property "+ prop.getName() +"("+ res +") depth K="+ depth +" took " + solverTime + " ms");		

				/* Ajout du resultat */
				if (res == Result.TRUE || res == Result.FALSE) {
					result.put(prop.getName(), res);
				}
			} // foreach prop

			// remove Proved properties at this depth
			todo.removeAll(done);

			kind.incrementDepth();
		}
		
		
	}

	private void runBMC(IBMCSolver bmc, List<Property> todo, int maxd, Map<String, Result> result, Set<Property> done, Map<Property, Integer> expectedLength) throws RuntimeException {
		try {

			// 300 secs timeout for full loop
			long loopstamp = System.currentTimeMillis();
			for (int depth = 0 ; depth <= maxd && ! todo.isEmpty() && ! timeout(loopstamp); depth ++) {
				loopstamp = System.currentTimeMillis();

				/* Pour chaque property */
				for (Property prop : todo) {
					if (done.contains(prop)) 
						continue;
					long timestamp = System.currentTimeMillis();
					if (timeout(loopstamp)) {
						break;
					}

					Integer expected = expectedLength.get(prop);
					if (expected != null) {
						if (depth  < expected) {
							// skip this test, coverability says its unfeasible at this depth
							continue;
						}
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
					notifyObservers(prop, res, "BMC("+depth+")");
					long solverTime =  System.currentTimeMillis() - timestamp ;
					getLog().info("BMC solution for property "+ prop.getName() +"("+ res +") depth K="+ depth +" took " + solverTime + " ms");		

					/* Ajout du resultat */
					if (res == Result.TRUE || res == Result.FALSE) {
						result.put(prop.getName(), res);
					}
				} // foreach prop

				// remove Proved properties at this depth
				todo.removeAll(done);
				bmc.incrementDepth();

			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			getLog().info("During BMC, SMT solver timed out at depth " + bmc.getDepth());
		} 
		if (! todo.isEmpty() ) {
			getLog().info("BMC solving timed out ("+timeout+" secs) at depth " + bmc.getDepth());
		}
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
