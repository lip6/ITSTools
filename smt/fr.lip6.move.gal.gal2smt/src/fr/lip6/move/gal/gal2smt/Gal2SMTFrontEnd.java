package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.smtlib.ICommand.IScript;
import org.smtlib.IPrinter;
import org.smtlib.IResponse;
import org.smtlib.ISolver;
import org.smtlib.impl.Script;

import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;

public class Gal2SMTFrontEnd {

	private String solverPath;
	private List<ISMTObserver> callbacks = new ArrayList<ISMTObserver>();
	public enum Solver { Z3, YICES2 };
	private Solver engine;
	private int timeout;
	
	public Gal2SMTFrontEnd(String solverPath, Solver engine, int timeout) {
		this.solverPath = solverPath;
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
		
		long timestamp = System.currentTimeMillis();			
//		getLog().info("Translation to SMT took " + ( System.currentTimeMillis() - timestamp ) + " ms");		

		Map<String, Result> result = new HashMap<String, Result>();

		SMTBuilder builder = new SMTBuilder(spec);

		List<Property> todo = new ArrayList<Property>(spec.getProperties());
		
		// 300 secs timeout for full loop
		long loopstamp = System.currentTimeMillis();
		for (int depth = 0 ; depth <= 50 && ! todo.isEmpty() && ! timeout(loopstamp); depth += 5 ) {
			loopstamp = System.currentTimeMillis();
			List<Property> done = new ArrayList<Property>();
			
			/* Pour chaque property */
			for (Property prop : todo) {
				timestamp = System.currentTimeMillis();
				if (timeout(loopstamp)) {
					break;
				}
				// a script
				IScript script = new Script();
				
				// old school
				/* Build a reachability problem */
				builder.buildReachabilityProblem(prop, depth, script.commands());
				boolean isSat = solve(script);
				
			//	getLog().info(script.commands().toString());
				Result res = Result.UNKNOWN;
				/* Invoke solver, new school */
//				boolean isSat = solve(prop, depth, builder);

				if (isSat) {
					if (prop.getBody() instanceof ReachableProp) {
						res  = Result.TRUE;					
						getLog().info(" Result is SAT, found a trace to state matching reachability predicate " + prop.getName());
					} else {
						res = Result.FALSE;
						getLog().info(" Result is SAT, found a counter-example trace to a state that contradicts invariant/never predicate " + prop.getName());						
					}
					done.add(prop);
				} else {
					res = Result.UNSAT;
					// try to disprove property

					// a script
					IScript inductionScript = new Script();
					
					// old school
					builder.buildInductionProblem(prop, depth, inductionScript.commands());
			//		getLog().info(inductionScript.commands().toString());
					boolean isSatInduction = solve(inductionScript);
					if (isSatInduction) {
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
				}
				notifyObservers(prop, res, depth);

				result.put(prop.getName(), res);

				long solverTime =  System.currentTimeMillis() - timestamp ;
				getLog().info("SMT solution for property "+ prop.getName() +"("+ res +") depth K="+ depth +" took " + solverTime + " ms");		

				/* Ajout du resultat */
				result.put(prop.getName(), res);
			
//			/* supprime les fichiers .txt.gal */
//			SmtSerializationUtil.deleteFiles(directory, ".txt.gal"); 
			}
			
			// remove SAT properties
			todo.removeAll(done);

			///// Handle test for termination
			// a script
			boolean isDepthEnough = checkMaxDepth (depth, builder);

			if (isDepthEnough) {
				// we are done !
				return result;
			} else {				
				// SAT means k is less than breadth of state space
				continue;
			}
		}

		return result;
	}
	
	private boolean timeout(long loopstamp) {
		// TODO Auto-generated method stub
		return ( System.currentTimeMillis() - loopstamp >= timeout*1000 );
	}

	private boolean checkMaxDepth(int depth, SMTBuilder builder) throws Exception {
		
		return depth >= 128;
//		IScript script = new Script();
//		long timestamp = System.currentTimeMillis();
//
//		// test for full exploration
//		builder.buildMaxDepthReachedProblem(depth, script.commands());
//
//		/* Invoke solver */
//		IResponse response = solve(script);
//
//		getLog().info("SMT solution for MaxDepthReached ("+ (response.isOK()?Result.SAT:Result.UNSAT) +") for depth K="+ depth +"took " + (System.currentTimeMillis() - timestamp) + " ms");		
//		
//		// SAT means k is less than breadth of state space
//		// UNSAT means k is enough
//		return ! response.isOK();
	}

	private boolean solve(IScript script) throws Exception {
		ISolver solver = getSolver();
		script.commands().add(new org.smtlib.command.C_check_sat());		
		
		IResponse response = script.execute(solver);

		// debug trace
		IPrinter printer = GalToSMT.getSMT().smtConfig.defaultPrinter;
	//	System.out.println(printer.toString(script));
		

		solver.exit();
		
		String textReply = printer.toString(response);
		System.out.println(printer.toString(response));
		if ("sat".equals(textReply)) {
			return true;
		} else if ("unsat".equals(textReply)) {
			return false ;
		} else {
			throw new RuntimeException("SMT solver raised an error :" + textReply);
		}
		
	}

	
	ISolver solver = null;
	
	private boolean solve(Property prop, int depth, SMTBuilder builder) throws Exception {
		if (solver == null) {
			// get and start solver
			solver = getSolver();
			// load header and semantics
			IScript sem = new Script();
			builder.addHeader(sem.commands());
			builder.addSemantics(sem.commands(),true);
			builder.unrollTransitionRelation(depth, sem.commands());
			sem.execute(solver);
		}
		IScript script = new Script();
		/* Build a reachability problem */
		builder.addProperty(prop, depth, script.commands());
		script.commands().add(new org.smtlib.command.C_check_sat());		
		
		IResponse response = script.execute(solver);
		IPrinter printer = GalToSMT.getSMT().smtConfig.defaultPrinter;
		String textReply = printer.toString(response);
		System.out.println(printer.toString(response));
		
		//cleanup
		IScript undo = new Script();
		builder.removeProperty(undo.commands());
		undo.execute(solver);
		
		return "sat".equals(textReply);
	}

	private ISolver getSolver () {
		ISolver solver;
		//	GalToSMT.getSMT().smtConfig.verbose = 1;
		GalToSMT.getSMT().smtConfig.timeout = timeout;
		if (engine == Solver.Z3) {
			solver = new org.smtlib.solvers.Solver_z3_4_3(GalToSMT.getSMT().smtConfig, solverPath);
		} else {
			solver = new org.smtlib.solvers.Solver_yices2(GalToSMT.getSMT().smtConfig, solverPath);
		}
		IResponse err = solver.start();
		if (err.isError()) {
			throw new RuntimeException("Could not start solver "+engine+" from path "+ solverPath);
		}
		return solver;
	}
	
	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}
	
}
