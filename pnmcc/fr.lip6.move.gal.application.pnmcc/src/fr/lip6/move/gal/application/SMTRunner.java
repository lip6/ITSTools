package fr.lip6.move.gal.application;

import java.util.Map;
import java.util.logging.Logger;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.gal2smt.Gal2SMTFrontEnd;
import fr.lip6.move.gal.gal2smt.ISMTObserver;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;

public class SMTRunner extends AbstractRunner {

	private String pwd;
	private String solverPath;
	private Solver solver;
	private int nbsolve = 0;
	private Map<String, Result> satresult;

	public SMTRunner(String pwd, String solverPath, Solver solver) {
		this.pwd = pwd;
		this.solverPath = solverPath;
		this.solver = solver;
	}

	public Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");

	}
	
	public Boolean taskDone() {
		// test for and handle properties
		if (nbsolve == satresult.size()) {
			getLog().info("SMT solved all " + nbsolve + " properties. Interrupting other analysis methods.");
			System.out.println("tasks resolved Smt");
			return true;
		} else {
			getLog().info("SMT solved " + nbsolve + "/ " + satresult.size() + " properties.");
		}
		System.out.println("tasks not all resolved Smt");
		return false;

	}
	
	public void solve() {
		Gal2SMTFrontEnd gsf = new Gal2SMTFrontEnd(solverPath, solver);
		gsf.addObserver(new ISMTObserver() {
			public synchronized void notifyResult(Property prop, Result res, String desc) {
				if (res == Result.TRUE || res == Result.FALSE) {
					System.out.println("FORMULA " + prop.getName() + " " + res + " " + "TECHNIQUES SAT_SMT " + desc);
					nbsolve++;
				} else {
					// a ambiguous verdict
					// System.out.println("Obtained " + prop.getName() +
					// " " + res +" TECHNIQUES SAT_SMT "+desc );
				}
			}
		});

		try {
			satresult = gsf.checkProperties(spec, pwd, doneProps);
		} catch (Exception e) {
			e.printStackTrace();

		}

		// List<Property> todel = new ArrayList<Property>();
		// for (Property prop : z3Spec.getProperties()) {
		// if (satresult.get(prop.getName()) == Result.SAT) {
		// todel.add(prop);
		// }
		// }
		// specWithProps.getProperties().removeAll(todel);
		// }
		// __________________________
		// runnerThread.setContextClassLoader(Thread.currentThread().getClass().getClassLoader());
		System.out.println("SMT HAS COMPLETELY FINISHED");
	}

}