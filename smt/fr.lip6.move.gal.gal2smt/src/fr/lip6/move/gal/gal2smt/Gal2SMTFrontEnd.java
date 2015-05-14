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

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;

public class Gal2SMTFrontEnd {

	private String Z3path;
	
	public Gal2SMTFrontEnd(String z3path) {
		Z3path = z3path;
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
		for (int depth = 1 ; depth <= 50 && ! todo.isEmpty() && ( System.currentTimeMillis() - loopstamp <= 30000 ); depth += 5 ) {
			loopstamp = System.currentTimeMillis();
			List<Property> done = new ArrayList<Property>();
			
			/* Pour chaque property */
			for (Property prop : todo) {
				timestamp = System.currentTimeMillis();

				// a script
				IScript script = new Script();
				
				// old school
				/* Build a reachability problem */
				builder.buildReachabilityProblem(prop, depth, script.commands());
				boolean isSat = solve(script);
				
				Result res = Result.UNKNOWN;
				/* Invoke solver, new school */
//				boolean isSat = solve(prop, depth, builder);

				if (isSat) {
					res  = Result.SAT;
					if (prop.getBody() instanceof ReachableProp) {
						// a trace to state P
						System.out.println("FORMULA " + prop.getName() + " TRUE " + "TECHNIQUES SAT_SMT" );
					} else {
						// a c-e trace 
						System.out.println("FORMULA " + prop.getName() + " FALSE " + "TECHNIQUES SAT_SMT" );						
					}
					done.add(prop);
				} else {
					res = Result.UNSAT;
				}
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

		IPrinter printer = GalToSMT.getSMT().smtConfig.defaultPrinter;

		// debug
		//	System.out.println(printer.toString(script));
		
		String textReply = printer.toString(response);
		System.out.println(printer.toString(response));
		solver.exit();
		return "sat".equals(textReply);
	}

	
	ISolver solver = null;
	
	private boolean solve(Property prop, int depth, SMTBuilder builder) throws Exception {
		if (solver == null) {
			// get and start solver
			solver = getSolver();
			// load header and semantics
			IScript sem = new Script();
			builder.addHeader("AUFLIA", sem.commands());
			builder.addSemantics(sem.commands());
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
		ISolver solver = new org.smtlib.solvers.Solver_z3_4_3(GalToSMT.getSMT().smtConfig, Z3path);
		solver.start();
		return solver;
	}
	
	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}
	
}
