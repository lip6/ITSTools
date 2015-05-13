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
import fr.lip6.move.gal.Specification;

public class Gal2SMTFrontEnd {

	private String Z3path;
	
	public Gal2SMTFrontEnd(String z3path) {
		Z3path = z3path;
	}

	public Map<String, Result> checkProperties (Specification spec, String folder) throws Exception {
		long timestamp = System.currentTimeMillis();			
		getLog().info("Translation to SMT took " + ( System.currentTimeMillis() - timestamp ) + " ms");		

		Map<String, Result> result = new HashMap<String, Result>();

		SMTBuilder builder = new SMTBuilder(spec);

		List<Property> todo = new ArrayList<Property>(spec.getProperties());
		
		for (int depth = 1 ; depth < 36 && ! todo.isEmpty() ; depth *= 2 ) {
			List<Property> done = new ArrayList<Property>();
			
			/* Pour chaque property */
			for (Property prop : todo) {
				timestamp = System.currentTimeMillis();

				// a script
				IScript script = new Script();
				/* Build a reachability problem */
				builder.buildReachabilityProblem(prop, depth, script.commands());

				Result res = Result.UNKNOWN;
				/* Invoke solver */
				IResponse response = solve(script);

				if (response.isOK()) {
					res  = Result.SAT;
					System.out.println("FORMULA " + prop.getName() + " TRUE " + "TECHNIQUES SAT_SMT" );
					done.add(prop);
				} else {
					res = Result.UNSAT;
				}
				result.put(prop.getName(), res);

				long solverTime =  System.currentTimeMillis() - timestamp ;
				getLog().info("SMT solution for property "+ prop.getName() +"("+ res +") depth K="+ depth +"took " + solverTime + " ms");		

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
		
		IScript script = new Script();
		long timestamp = System.currentTimeMillis();

		// test for full exploration
		builder.buildMaxDepthReachedProblem(depth, script.commands());

		/* Invoke solver */
		IResponse response = solve(script);

		getLog().info("SMT solution for MaxDepthReached ("+ (response.isOK()?Result.SAT:Result.UNSAT) +") for depth K="+ depth +"took " + (System.currentTimeMillis() - timestamp) + " ms");		
		
		// SAT means k is less than breadth of state space
		// UNSAT means k is enough
		return ! response.isOK();
	}

	private IResponse solve(IScript script) throws Exception {
		ISolver solver = getSolver();
		
		IResponse response = script.execute(solver);
		IPrinter printer = GalToSMT.getSMT().smtConfig.defaultPrinter;
		System.out.println(printer.toString(response));
		return response;
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
