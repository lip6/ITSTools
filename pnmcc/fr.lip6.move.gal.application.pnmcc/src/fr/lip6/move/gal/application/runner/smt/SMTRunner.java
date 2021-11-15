package fr.lip6.move.gal.application.runner.smt;

import java.util.Map;
import java.util.logging.Logger;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.application.AbstractRunner;
import fr.lip6.move.gal.application.runner.Ender;
import fr.lip6.move.gal.application.runner.IRunner;
import fr.lip6.move.gal.gal2smt.Gal2SMTFrontEnd;
import fr.lip6.move.gal.gal2smt.ISMTObserver;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.mcc.properties.DoneProperties;

public class SMTRunner extends AbstractRunner implements IRunner {
	
	private String pwd;
	private String solverPath;
	private Solver solver;
	private long timeout;
	private boolean isSafe;

	public SMTRunner(String pwd, String solverPath, Solver solver, long timeout, boolean isSafe) {
		this.pwd = pwd;
		this.solverPath = solverPath;
		this.solver = solver;
		this.timeout = timeout * 1000;
		this.isSafe = isSafe ;
	}

	private Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");

	}

	public Thread runSMT(final String pwd, final String solverPath, final Solver solver, final Specification z3Spec,
			final Ender ender, DoneProperties doneProps) {
		 runnerThread = new Thread(new Runnable() {
			int nbsolve = 0;

			@Override
			public void run() {
				Gal2SMTFrontEnd gsf = new Gal2SMTFrontEnd(solverPath, solver, timeout);

				gsf.addObserver(new ISMTObserver() {
					@Override
					public synchronized void notifyResult(Property prop, Result res, String desc) {
						if (res == Result.TRUE || res == Result.FALSE) {
//							System.out.println(
//									"FORMULA " + prop.getName() + " " + res + " " + "TECHNIQUES SAT_SMT " + desc);
							nbsolve++;
						} else {
							// a ambiguous verdict
							// System.out.println("Obtained " + prop.getName() +
							// " " + res +" TECHNIQUES SAT_SMT "+desc );
						}
					}
				});
				try {
					Map<String, Result> satresult = gsf.checkProperties(z3Spec, pwd, doneProps, isSafe);
					// test for and handle properties
					if (nbsolve == satresult.size()) {
						getLog().info(
								"SMT solved all " + nbsolve + " properties. Interrupting other analysis methods.");
						ender.killAll();
					} else {
						getLog().info("SMT solved " + nbsolve + "/ " + satresult.size()
								+ " properties. Interrupting SMT analysis.");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		runnerThread.start();
		return runnerThread;
	}

	@Override
	public void solve(Ender ender) {
		 runnerThread = new Thread(new Runnable() {
			int nbsolve = 0;

			@Override
			public void run() {
				Gal2SMTFrontEnd gsf = new Gal2SMTFrontEnd(solverPath, solver, timeout);

				gsf.addObserver(new ISMTObserver() {
					@Override
					public synchronized void notifyResult(Property prop, Result res, String desc) {
						if (res == Result.TRUE || res == Result.FALSE) {
//							System.out.println(
//									"FORMULA " + prop.getName() + " " + res + " " + "TECHNIQUES SAT_SMT " + desc);
							nbsolve++;
						} else {
							// a ambiguous verdict
							// System.out.println("Obtained " + prop.getName() +
							// " " + res +" TECHNIQUES SAT_SMT "+desc );
						}
					}
				});
				try {
					Map<String, Result> satresult = gsf.checkProperties(spec, pwd, doneProps, isSafe);
					// test for and handle properties
					if (nbsolve == satresult.size()) {
						getLog().info(
								"SMT solved all " + nbsolve + " properties. Interrupting other analysis methods.");
						ender.killAll();
					} else {
						getLog().info("SMT solved " + nbsolve + "/ " + satresult.size()
								+ " properties. Interrupting SMT analysis.");
					}

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
			}
		});
		runnerThread.setContextClassLoader(Thread.currentThread().getClass().getClassLoader());
		runnerThread.start();
	}
}