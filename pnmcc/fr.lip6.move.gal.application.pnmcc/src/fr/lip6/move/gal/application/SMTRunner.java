package fr.lip6.move.gal.application;

import java.util.Map;
import java.util.logging.Logger;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.gal2smt.Gal2SMTFrontEnd;
import fr.lip6.move.gal.gal2smt.ISMTObserver;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;

public class SMTRunner {
	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
		
	}
	
	public static Thread runSMT(final String pwd, final String z3path, final Solver solver,
			final Specification z3Spec, final Ender ender) {
		Thread z3Runner = new Thread(new Runnable() {
			int nbsolve = 0;
			@Override
			public void run() {
				Gal2SMTFrontEnd gsf = new Gal2SMTFrontEnd(z3path, solver);
				
				gsf.addObserver(new ISMTObserver() {
					@Override
					public synchronized void notifyResult(Property prop, Result res, String desc) {
						if (res == Result.TRUE || res == Result.FALSE) {
								System.out.println("FORMULA " + prop.getName() + " "+ res +" "+ "TECHNIQUES SAT_SMT "+desc );
								nbsolve++;
						} else {
								// a ambiguous verdict  
								//System.out.println("Obtained  " + prop.getName() + " " + res +" TECHNIQUES SAT_SMT "+desc );						
						}
					}
				});
				try {
					Map<String, Result> satresult = gsf.checkProperties(z3Spec, pwd);
					// test for and handle properties
					if (nbsolve == satresult.size()) {
						getLog().info("SMT solved all "+nbsolve+" properties. Interrupting other analysis methods.");
						ender.killAll();
					} else {
						getLog().info("SMT solved "+nbsolve +"/ "+ satresult.size() +" properties. Interrupting other analysis methods.");						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}				
				// List<Property> todel = new ArrayList<Property>();
				//						for (Property prop : z3Spec.getProperties()) {
				//							if (satresult.get(prop.getName()) == Result.SAT) {
				//								todel.add(prop);
				//							}
				//						}
				//						specWithProps.getProperties().removeAll(todel);
				//					}
			}
		}
				);
		z3Runner.start();
		return z3Runner;
	}

}
