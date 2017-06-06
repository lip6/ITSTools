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
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

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
import fr.lip6.move.gal.itstools.Runner;
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
	
	
	private IRunner cegarRunner;
	private IRunner z3Runner;
	private IRunner itsRunner;
	private IRunner ltsminRunner;
	
	
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
		
		boolean withStructure = reader.hasStructure(); 
		
		Set<String> doneProps = ConcurrentHashMap.newKeySet();
		
		reader.loadProperties();
		
		if (examination.equals("StateSpace")) {
			
			reader.flattenSpec(true);
			
		} else if (examination.equals("ReachabilityDeadlock")) {
			reader.flattenSpec(true);
			
		} else if (examination.startsWith("CTL")) {
			reader.removeAdditionProperties();
			
			reader.flattenSpec(true);
			
		} else if (examination.startsWith("LTL")) {
			reader.flattenSpec(true);
						
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
					z3Runner = new SMTRunner(pwd, solverPath, solver );
					z3Runner.configure(z3Spec, doneProps);
					z3Runner.solve(this);
				}

				// run on a fresh copy to avoid any interference with other threads.
				if (doCegar) {
					cegarRunner = new CegarRunner(pwd);
					cegarRunner.configure(EcoreUtil.copy(reader.getSpec()), doneProps);
					cegarRunner.solve(this);
				}
			}
			

			if (doITS || onlyGal) {				
				// decompose + simplify as needed
				reader.flattenSpec(true);
				
				itsRunner = new ITSRunner(examination, reader, doITS, onlyGal, reader.getFolder());
				itsRunner.configure(reader.getSpec(), doneProps);
			}						
		}
				
		if (doITS) {
			itsRunner.solve(this);
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
				ltsminRunner = new LTSminRunner(ltsminpath, solverPath, solver, doPOR, onlyGal, reader.getFolder(), 3600 / reader.getSpec().getProperties().size() );				
				ltsminRunner.configure(reader.getSpec(), doneProps);
				ltsminRunner.solve(this);
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
		return IApplication.EXIT_OK;
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

	

	
	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		killAll();
	}
	
	
	

}
