package fr.lip6.move.gal.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.smtlib.activator.SMTActivator;

import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.gal2smt.Gal2SMTFrontEnd;
import fr.lip6.move.gal.gal2smt.ISMTObserver;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.itstools.CommandLine;
import fr.lip6.move.gal.itstools.CommandLineBuilder;
import fr.lip6.move.gal.itstools.BinaryToolsPlugin.Tool;
import fr.lip6.move.gal.itstools.ProcessController.TimeOutException;
import fr.lip6.move.serialization.SerializationUtil;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication, Ender {

	
	
	private static final String APPARGS = "application.args";
	
	private static final String PNFOLDER = "-pnfolder";

	private static final String EXAMINATION = "-examination";
	private static final String Z3PATH = "-z3path";
	private static final String YICES2PATH = "-yices2path";
	private static final String SMT = "-smt";
	private static final String ITS = "-its";
	private static final String CEGAR = "-cegar";
	private static final String LTSMINPATH = "-ltsminpath";
	private static final String ONLYGAL = "-onlyGal";
	private static final String disablePOR = "-disablePOR";
	
	
	private Thread cegarRunner;
	private Thread z3Runner;
	private Thread itsRunner;
	private Thread itsReader;
	private Thread ltsminRunner;
	
	
	public synchronized void killAll () {
		if (cegarRunner != null)
			cegarRunner.interrupt();
		if (z3Runner != null)
			z3Runner.interrupt();
		if (itsRunner != null) 
			itsRunner.interrupt();
		if (ltsminRunner != null) 
			ltsminRunner.interrupt();		
		System.exit(0);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		
		String [] args = (String[]) context.getArguments().get(APPARGS);
		
		String pwd = null;
		String examination = null;
		String z3path = null;
		String yices2path = null;
		String ltsminpath = null;
		
		boolean doITS = false;
		boolean doSMT = false;
		boolean doCegar = false;
		boolean onlyGal = false;
		boolean doLTSmin = false;
		boolean doPOR = true;
		
		for (int i=0; i < args.length ; i++) {
			if (PNFOLDER.equals(args[i])) {
				pwd = args[++i];
			} else if (EXAMINATION.equals(args[i])) {
				examination = args[++i]; 
			} else if (Z3PATH.equals(args[i])) {
				z3path = args[++i]; 
			} else if (YICES2PATH.equals(args[i])) {
				yices2path = args[++i]; 
			} else if (SMT.equals(args[i])) {
				doSMT = true;
			} else if (LTSMINPATH.equals(args[i])) {
				ltsminpath = args[++i];
				doLTSmin = true;
			} else if (CEGAR.equals(args[i])) {
				doCegar = true;
			} else if (ITS.equals(args[i])) {
				doITS = true;
			} else if (disablePOR.equals(args[i])) {
				doPOR = false;
			} else if (ONLYGAL.equals(args[i])) {
				onlyGal = true;
			}
		}
		
		SerializationUtil.setStandalone(true);
		
		MccTranslator reader = new MccTranslator(pwd,examination);
		
		try {			
			reader.transformPNML();
		} catch (IOException e) {
			System.err.println("Incorrect file or folder " + pwd + "\n Error :" + e.getMessage());
			if (e.getCause() != null) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
			return null;
		}
		
		// for debug and control
		if (pwd.contains("COL")) {
			String outpath =  pwd + "/model.pnml.img.gal";
			SerializationUtil.systemToFile(reader.getSpec(), outpath);
		}
		
		// force classloader
		SMTActivator.getContext();
		
		CommandLine cl =null;
		boolean withStructure = reader.hasStructure(); 
		
		Set<String> doneProps = ConcurrentHashMap.newKeySet();
		
		reader.loadProperties();
		
		if (examination.equals("StateSpace")) {
			
			reader.flattenSpec(true);
			String outpath = reader.outputGalFile();

			cl = buildCommandLine(outpath);
			cl.addArg("--stats");
		} else if (examination.equals("ReachabilityDeadlock")) {
			reader.flattenSpec(true);
			
			if (doITS || onlyGal) {
				String outpath = reader.outputGalFile();

				assert ( reader.getSpec().getProperties().size() == 1);				
				
				cl = buildCommandLine(outpath, Tool.ctl);
				cl.addArg("-ctl");
				cl.addArg("DEADLOCK");
			}
		} else if (examination.startsWith("CTL")) {
			reader.flattenSpec(true);
			
			if (doITS || onlyGal) {								
				String outpath = reader.outputGalFile(); 
				
				String ctlpath = reader.outputPropertyFile(); 
				
				cl = buildCommandLine(outpath, Tool.ctl);

				cl.addArg("-ctl");
				cl.addArg(ctlpath);	
				
				//cl.addArg("--backward");
			}
		} else if (examination.startsWith("LTL")) {
			reader.flattenSpec(true);
						
			if (doITS || onlyGal) {
				String outpath = reader.outputGalFile();
				String ltlpath = reader.outputPropertyFile();
				
				
				cl = buildCommandLine(outpath, Tool.ltl);
				cl.addArg("-LTL");
				cl.addArg(ltlpath);	

				cl.addArg("-c");
				//cl.addArg("-SSLAP-FSA");
				
				cl.addArg("-stutter-deadlock");
			}
				
		} else if (examination.startsWith("Reachability") || examination.contains("Bounds")) {
			reader.flattenSpec(false);
			
			if (examination.startsWith("Reachability")) {
				// get rid of trivial properties in spec
				checkInInitial(reader.getSpec(), doneProps);

				// cegar does not support hierarchy currently, time to start it, the spec won't get any better
				if ( (z3path != null || yices2path != null) && doSMT ) {
					Specification z3Spec = EcoreUtil.copy(reader.getSpec());
					Solver solver = Solver.YICES2;
					String solverPath = yices2path;
					if (z3path != null && yices2path == null) {
						solver = Solver.Z3 ; 
						solverPath = z3path;
					}
					// run on a fresh copy to avoid any interference with other threads.
					z3Runner = new SMTRunner().runSMT(pwd, solverPath, solver, z3Spec, this, doneProps);
				}

				// run on a fresh copy to avoid any interference with other threads.
				if (doCegar) {
					cegarRunner = CegarRunner.runCegar(EcoreUtil.copy(reader.getSpec()),  pwd, this);
				}
			}
			

			if (doITS || onlyGal) {				
				// decompose + simplify as needed
				reader.flattenSpec(true);
				String outpath = reader.outputGalFile();

				cl = buildCommandLine(outpath);

				// We will put properties in a file
				String propPath = reader.outputPropertyFile();

				// property file arguments
				cl.addArg("-reachable-file");
				cl.addArg(new File(propPath).getName());

				cl.addArg("--nowitness");				
			}						
		}
		if (cl != null) {
			cl.setWorkingDir(new File(pwd));
		}
				
		if (doITS) {
			ITSInterpreter interp = new ITSInterpreter(examination, withStructure, reader, doneProps);
			runITStool(cl, interp);
		}

		if (onlyGal || doLTSmin) {
			Solver solver = Solver.YICES2;
			String solverPath = yices2path;
			if (z3path != null && yices2path == null) {
			//if (z3path != null) {
				solver = Solver.Z3 ; 
				solverPath = z3path;
			}
			// || examination.startsWith("CTL")
			if (! reader.getSpec().getProperties().isEmpty() && ( examination.startsWith("Reachability") || examination.startsWith("LTL"))) {
				System.out.println("Using solver "+solver+" to compute partial order matrices.");
				ltsminRunner = LTSminRunner.runLTSmin(ltsminpath,reader,solverPath,solver,3600 / reader.getSpec().getProperties().size(), doneProps, this, doPOR, onlyGal);
			}
		}
		
		if (ltsminRunner != null) 
			ltsminRunner.join();
		if (cegarRunner != null)
			cegarRunner.join();
		if (z3Runner != null)
			z3Runner.join();
		if (itsRunner != null)
			itsRunner.join();
		if (itsReader != null)
			itsReader.join();
		return IApplication.EXIT_OK;
	}



	private void runITStool(final CommandLine cl, ITSInterpreter interp) {
		final PipedInputStream pin = new PipedInputStream(4096);
		PipedOutputStream pout= null;
		try {
			pout = new PipedOutputStream(pin);
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		
		
		itsRunner = new Thread (new ITSRunner(pout,cl));

		interp.setInput(pin);
		itsReader = new Thread (interp);
		itsReader.start();
		itsRunner.start();
	}

	class ITSInterpreter implements Runnable {
	
		private BufferedReader in;
		//private Map<String, List<Property>> boundProps;
		private String examination;
		private boolean withStructure;
		private MccTranslator reader;
		private Set<String> seen;

		public ITSInterpreter(String examination, boolean withStructure, MccTranslator reader, Set<String> doneProps) {			
			this.examination = examination;
			this.withStructure = withStructure;
			this.reader = reader;
			this.seen = doneProps;
		}

		public void setInput(InputStream pin) {
			this.in = new BufferedReader(new InputStreamReader(pin));
		}

		@Override
		public void run() {
			

			try {
				for (String line = ""; line != null ; line=in.readLine() ) {
					System.out.println(line);
					//stdOutput.toString().split("\\r?\\n")) ;
					if ( line.matches("Max variable value.*")) {
						if (examination.equals("StateSpace")) {
							System.out.println( "STATE_SPACE MAX_TOKEN_IN_PLACE " + line.split(":")[1] + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
						}
					}
					if ( line.matches("Maximum sum along a path.*")) {
						if (examination.equals("StateSpace")) {
							int nbtok = Integer.parseInt(line.split(":")[1].replaceAll("\\s", ""));
							nbtok += reader.countMissingTokens();
							System.out.println( "STATE_SPACE MAX_TOKEN_PER_MARKING " + nbtok + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
						}
					}
					if ( line.matches("Exact state count.*")) {
						if (examination.equals("StateSpace")) {
							System.out.println( "STATE_SPACE STATES " + line.split(":")[1] + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
						}
					}
					if ( line.matches("Total edges in reachability graph.*")) {
						if (examination.equals("StateSpace")) {
							System.out.println( "STATE_SPACE UNIQUE_TRANSITIONS " + line.split(":")[1] + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
						}
					}
					if ( line.matches("System contains.*deadlocks.*")) {
						if (examination.equals("ReachabilityDeadlock")) {
							
							Property dead = reader.getSpec().getProperties().get(0);
							String pname = dead.getName();
							int nbdead = Integer.parseInt(line.split("\\s+")[2]);
							String res ;
							if (nbdead == 0)
								res = "FALSE";
							else
								res = "TRUE";
							System.out.println( "FORMULA " + pname + " " +res + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
							seen.add(pname);
						}
					}
					if ( line.matches("Bounds property.*")) {
						if (examination.contains("Bounds") ) {
							String [] words = line.split(" ");
							String pname = words[2];
							String [] tab = line.split("<=");

							String sbound = tab[2].replaceAll("\\s", "");
							
							int bound = Integer.parseInt(sbound);
							Property target = null;
							for (Property prop : reader.getSpec().getProperties()) {
								if (prop.getName().equals(pname) ) {
									target = prop;
									break;
								}
							}
							int toadd=0;
							for (TreeIterator<EObject> it = target.eAllContents() ; it.hasNext() ; ) {
								EObject obj = it.next();
								if (obj instanceof Constant) {
									Constant cte = (Constant) obj;
									toadd += cte.getValue();
								} else if (obj instanceof Reference) {
									it.prune();
								}
							}
							seen.add(pname);
							System.out.println( "FORMULA " + pname  + " " + (bound+toadd) +  " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
						}
					}
					if ( examination.startsWith("CTL")) {
						if (line.matches(".*formula \\d+,\\d+,.*")) {
							String [] tab = line.split(",");
							int formindex = Integer.parseInt(tab[0].split(" ")[1]);
							int verdict = Integer.parseInt(tab[1]);
							String res ;
							if (verdict == 0)
								res = "FALSE";
							else
								res = "TRUE";
							String pname = reader.getSpec().getProperties().get(formindex).getName();
							System.out.println( "FORMULA " + pname + " " +res + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
							seen.add(pname);
						}
					}
					if ( examination.startsWith("LTL")) {
						if (line.matches("Formula \\d+ is .*")) {
							String [] tab = line.split(" ");
							int formindex = Integer.parseInt(tab[1]);
							String res = tab[3];
							String pname = reader.getSpec().getProperties().get(formindex).getName();
							System.out.println( "FORMULA " + pname + " " +res + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
							seen.add(pname);
						}
					}
					
					if ( line.matches(".*-"+examination+"-\\d+.*")) {
						//System.out.println(line);
						String res;
						if (line.matches(".*property.*") && ! line.contains("Bounds")) {
							String pname = line.split(" ")[2];
							if (line.contains("does not hold")) {
								res = "FALSE";
							} else if (line.contains("No reachable states")) {
								res = "FALSE";
								pname = line.split(":")[1];
							} else {
								res = "TRUE";
							}
							pname = pname.replaceAll("\\s", "");
							if (!seen.contains(pname)) {
								System.out.println("FORMULA "+pname+ " "+ res + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL COLLATERAL_PROCESSING " + (withStructure?"USE_NUPN":""));
								seen.add(pname);
							}
						}
					}
				}
				in.close();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			killAll();
		}
		
	}
	
	class ITSRunner implements Runnable {
		private OutputStream pout;
		private CommandLine cl;
		
		public ITSRunner(OutputStream pout, CommandLine cl) {
			this.pout = pout;
			this.cl = cl;
		}

		@Override
		public void run() {

			try {		
				Runner.runTool(3500, cl, pout, false);
			} catch (TimeOutException e) {
				System.out.println("COULD_NOT_COMPUTE");
				return;
				//					return new Status(IStatus.ERROR, ID,
				//							"Check Service process did not finish in a timely way."
				//									+ errorOutput.toString());
			} catch (IOException e) {
				System.out.println("COULD_NOT_COMPUTE");
				return;
				//					return new Status(IStatus.ERROR, ID,
				//							"Unexpected exception executing service."
				//									+ errorOutput.toString(), e);
			} finally {
				try {
					pout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}




	/**
	 * Structural analysis and reduction : test in initial state.
	 * @param specWithProps spec which will be modified : trivial properties will be removed
	 * @param doneProps 
	 */
	private void checkInInitial(Specification specWithProps, Set<String> doneProps) {
		List<Property> props = new ArrayList<Property>(specWithProps.getProperties());
				
		// iterate down so indexes are consistent
		for (int i = props.size()-1; i >= 0 ; i--) {
			Property propp = props.get(i);

			if (doneProps.contains(propp.getName())) {
				continue;
			}
			if (propp.getBody() instanceof SafetyProp) {
				SafetyProp prop = (SafetyProp) propp.getBody();
				

				// discard property
				if (prop.getPredicate() instanceof True || prop.getPredicate() instanceof False) {
					specWithProps.getProperties().remove(i);
				}
				boolean solved = false;
				// output verdict
				if (prop instanceof ReachableProp || prop instanceof InvariantProp) {

					if (prop.getPredicate() instanceof True) {
						// positive forms : EF True , AG True <=>True
						System.out.println("FORMULA "+propp.getName() + " TRUE TECHNIQUES TOPOLOGICAL INITIAL_STATE");
						solved = true;
					} else if (prop.getPredicate() instanceof False) {
						// positive forms : EF False , AG False <=> False
						System.out.println("FORMULA "+propp.getName() + " FALSE TECHNIQUES TOPOLOGICAL INITIAL_STATE");
						solved = true;
					}
				} else if (prop instanceof NeverProp) {
					if (prop.getPredicate() instanceof True) {
						// negative form : ! EF P = AG ! P, so ! EF True <=> False
						System.out.println("FORMULA "+propp.getName() + " FALSE TECHNIQUES TOPOLOGICAL INITIAL_STATE");
						solved = true;
					} else if (prop.getPredicate() instanceof False) {
						// negative form : ! EF P = AG ! P, so ! EF False <=> True
						System.out.println("FORMULA "+propp.getName() + " TRUE TECHNIQUES TOPOLOGICAL INITIAL_STATE");
						solved = true;
					}
				}
				if (solved) {
					doneProps.add(propp.getName());
				}
			}
		}
	}

	private CommandLine buildCommandLine(String modelff) throws IOException {
		return buildCommandLine(modelff,Tool.reach);
	}

	private CommandLine buildCommandLine(String modelff, Tool tool) throws IOException {
		CommandLineBuilder cl = new CommandLineBuilder(tool);
		cl.setModelFile(modelff);
		cl.setModelType("CGAL");
		return cl.getCommandLine();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		killAll();
	}
	
	
	class SMTRunner {
		private Logger getLog() {
			return Logger.getLogger("fr.lip6.move.gal");
			
		}
		
		public Thread runSMT(final String pwd, final String z3path, final Solver solver,
				final Specification z3Spec, final Ender ender, Set<String> doneProps) {
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
						Map<String, Result> satresult = gsf.checkProperties(z3Spec, pwd, doneProps);
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
			z3Runner.setContextClassLoader(Thread.currentThread().getClass().getClassLoader());
			z3Runner.start();
			return z3Runner;
		}
	}

}
