package fr.lip6.move.gal.application;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import fr.lip6.move.gal.False;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.gal2smt.DeadlockTester;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.structural.NoDeadlockExists;
import fr.lip6.move.gal.structural.StructuralReduction;
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
	private static final String disableSDD = "-disableSDD";
	private static final String READ_GAL = "-readGAL";
	private static final String USE_LOUVAIN = "-louvain";
	private static final String ORDER_FLAG = "-order";
	private static final String GSPN_PATH = "-greatspnpath";

	
	private IRunner cegarRunner;
	private IRunner z3Runner;
	private IRunner itsRunner;
	private IRunner ltsminRunner;
	
	private static Logger logger = Logger.getLogger("fr.lip6.move.gal"); 
	
	@Override
	public synchronized void killAll () {
		if (cegarRunner != null)
			cegarRunner.interrupt();
		if (z3Runner != null)
			z3Runner.interrupt();
		if (itsRunner != null) 
			itsRunner.interrupt();
		if (ltsminRunner != null) 
			ltsminRunner.interrupt();		
		
		try {
			Runtime.getRuntime().exec("killall cc1 z3 its-reach its-ctl its-ltl pins2lts-seq pins2lts-mc");
			Thread.yield();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//System.exit(0);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	@Override
	public Object start(IApplicationContext context) throws Exception {
		
		String [] args = (String[]) context.getArguments().get(APPARGS);
		
		logger.info("Running its-tools with arguments : " + Arrays.toString(args));
		
		String pwd = null;
		String examination = null;
		String z3path = null;
		String yices2path = null;
		String ltsminpath = null;
		String readGAL = null;
		String gspnpath = null;
		String orderHeur = null;
		
		boolean doITS = false;
		boolean doSMT = false;
		boolean doCegar = false;
		boolean onlyGal = false;
		boolean doLTSmin = false;
		boolean doPOR = true;
		boolean doHierarchy = true;
		boolean useLouvain = false;
		
		
		for (int i=0; i < args.length ; i++) {
			if (PNFOLDER.equals(args[i])) {
				pwd = args[++i];
			} else if (EXAMINATION.equals(args[i])) {
				examination = args[++i]; 
			} else if (Z3PATH.equals(args[i])) {
				z3path = args[++i]; 
			} else if (YICES2PATH.equals(args[i])) {
				yices2path = args[++i]; 
			} else if (GSPN_PATH.equals(args[i])) {
				gspnpath = args[++i]; 
			} else if (ORDER_FLAG.equals(args[i])) {
				orderHeur = args[++i]; 
			} else if (SMT.equals(args[i])) {
				doSMT = true;
			} else if (LTSMINPATH.equals(args[i])) {
				ltsminpath = args[++i];
				doLTSmin = true;
			} else if (READ_GAL.equals(args[i])) {
				readGAL = args[++i];
			} else if (CEGAR.equals(args[i])) {
				doCegar = true;
			} else if (ITS.equals(args[i])) {
				doITS = true;
			} else if (disablePOR.equals(args[i])) {
				doPOR = false;
			} else if (ONLYGAL.equals(args[i])) {
				onlyGal = true;
			} else if (USE_LOUVAIN.equals(args[i])) {
				useLouvain = true;
			} else if (disableSDD.equals(args[i])) {
				doHierarchy = false;
			}
		}
		
		// use Z3 in preference to Yices if both are available
		Solver solver = Solver.Z3;
		String solverPath = z3path;
		if (z3path == null && yices2path != null) {
			solver = Solver.YICES2 ; 
			solverPath = yices2path;
		}
		
		// EMF registration 
		SerializationUtil.setStandalone(true);
		
		// setup a "reader" that parses input property files correctly and efficiently
		MccTranslator reader = new MccTranslator(pwd,examination,useLouvain);
		
		try {			
			if (readGAL == null) {
				// parse the model from PNML to GAL using PNMLFW for COL or fast SAX for PT models
				reader.transformPNML();
			} else {
				reader.loadGAL(readGAL);
			}
		} catch (IOException e) {
			System.err.println("Incorrect file or folder " + pwd + "\n Error :" + e.getMessage());
			if (e.getCause() != null) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
			return null;
		}

		// for debug and control COL files are small, otherwise 1MB PNML limit (i.e. roughly 200kB GAL max).
		if (pwd.contains("COL") || new File(pwd + "/model.pnml").length() < 1000000) {
			String outpath = pwd + "/model.pnml.img.gal";
			SerializationUtil.systemToFile(reader.getSpec(), outpath);
		}
		
		// initialize a shared container to detect help detect termination in portfolio case
		Set<String> doneProps = ConcurrentHashMap.newKeySet();

		// reader now has a spec and maybe a ITS decomposition
		// no properties yet.
		
		// A filename to store the variable ordering, if we compute it with e.g. GreatSPN
		String orderff = null;
		
		if (examination.equals("StateSpace")) {
			// ITS is the only method we will run.
			
			
			if (orderHeur != null && gspnpath != null) {
				// No hierarchy in this path
				reader.flattenSpec(false);
				try {
					
					INextBuilder nb = INextBuilder.build(reader.getSpec());
					IDeterministicNextBuilder idnb = IDeterministicNextBuilder.build(nb);			
					StructuralReduction sr = new StructuralReduction(idnb);
					StructuralToGreatSPN s2gspn =  new StructuralToGreatSPN();
					String gspnmodelff = pwd+"/gspn";
					s2gspn.transform(sr, gspnmodelff);
					try {
						GreatSPNRunner pinvrun = new GreatSPNRunner(pwd, gspnmodelff, gspnpath+"/bin/pinvar", 30);
						pinvrun.run();
					} catch (TimeoutException e) {
						System.out.println("P-invariant computation with GreatSPN timed out. Skipping.");
					}
					
					GreatSPNRunner run = new GreatSPNRunner(pwd, gspnmodelff, gspnpath+"/bin/RGMEDD2",60);
					run.configure("-" +orderHeur);
					run.configure("-varord-only");
					run.run();
					String[] order = run.getOrder();

					Specification reduced = sr.rebuildSpecification();
					//reduced.getProperties().addAll(reader.getSpec().getProperties());
					reader.setSpec(reduced);

					orderff = pwd+"/"+"model.ord";
					PrintWriter out = new PrintWriter( new BufferedOutputStream(new FileOutputStream(orderff)));
					out.println("#TYPE "+reduced.getMain().getName() );										
					for (int i = 0 ; i < order.length ; i++) {
						String var = order [i];
						out.println(var);
					}
					out.println("#END");
					out.flush();
					out.close();
					
					
					System.out.println("Using order generated by GreatSPN with heuristic : " + orderHeur);
				} catch (TimeoutException e) {
					System.out.println("Order computation with GreatSPN timed out. Skipping.");
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			} else {
				reader.flattenSpec(doHierarchy);
			}

			
			if (doITS || onlyGal) {				
				// decompose + simplify as needed
				itsRunner = new ITSRunner(examination, reader, doITS, onlyGal, reader.getFolder(),3600, orderff);
				itsRunner.configure(reader.getSpec(), doneProps);
			}			
					
			if (doITS) {
				itsRunner.solve(this);
				itsRunner.join();				
			}
			return 0;
		}

		
		// Now translate and load properties into GAL
		// uses a SAX parser to load to Logic MM, then an M2M to GAL properties.
		reader.loadProperties();
		
		
		
		// are we going for CTL ? only ITSRunner answers this.
		if (examination.startsWith("CTL") || examination.equals("UpperBounds")) {
			
			if (examination.startsWith("CTL")) {
				// due to + being OR in the CTL syntax, we don't support this type of props
				// TODO: make CTL syntax match the normal predicate syntax in ITS tools
				//reader.removeAdditionProperties();
			}
			// we support hierarchy
			reader.flattenSpec(doHierarchy);
			if (doITS || onlyGal) {				
				// decompose + simplify as needed
				itsRunner = new ITSRunner(examination, reader, doITS, onlyGal, reader.getFolder(),3600,orderff);
				itsRunner.configure(reader.getSpec(), doneProps);
			}			
					
			if (doITS) {
				itsRunner.solve(this);
				itsRunner.join();				
			}
			return 0;
		}
		
		System.out.println("Working with output stream " + System.out.getClass());
		// LTL, Deadlocks are ok for LTSmin and ITS
		if (examination.startsWith("LTL") || examination.equals("ReachabilityDeadlock")|| examination.equals("GlobalProperties")) {
			if (examination.startsWith("LTL")) {
				reader.flattenSpec(doHierarchy);					
			} else if (examination.equals("ReachabilityDeadlock")|| examination.equals("GlobalProperties")) {					
				
				long debut = System.currentTimeMillis();

				// remove parameters
				reader.flattenSpec(false);
				Specification spec = reader.getSpec();
				System.out.println("Flatten gal took : " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$				
				String outpath = pwd + "/model.pnml.simple.gal";
				SerializationUtil.systemToFile(reader.getSpec(), outpath);
				
				
				try {
					INextBuilder nb = INextBuilder.build(spec);
					IDeterministicNextBuilder idnb = IDeterministicNextBuilder.build(nb);			
					StructuralReduction sr = new StructuralReduction(idnb);
					sr.reduce();
					if (sr.getTnames().isEmpty()) {
						System.out.println( "FORMULA " + reader.getSpec().getProperties().get(0).getName()  + " TRUE TECHNIQUES TOPOLOGICAL STRUCTURAL_REDUCTION");
						return null;
					}
					Specification reduced = sr.rebuildSpecification();
					reduced.getProperties().addAll(reader.getSpec().getProperties());
					reader.setSpec(reduced);
					
					if (solverPath != null) {
						String res = DeadlockTester.testDeadlocksWithSMT(sr,solverPath);
						if ("unsat".equals(res)) {
							System.out.println( "FORMULA " + reader.getSpec().getProperties().get(0).getName()  + " FALSE TECHNIQUES TOPOLOGICAL SAT_SMT STRUCTURAL_REDUCTION");
							return null;
						}
					}
				} catch (NoDeadlockExists e) {
					System.out.println( "FORMULA " + reader.getSpec().getProperties().get(0).getName()  + " FALSE TECHNIQUES TOPOLOGICAL STRUCTURAL_REDUCTION");
					return null;
				} catch (Exception e) {
					System.out.println("Failed to apply structural reductions, skipping reduction step.");
				}
				
			}

			if (onlyGal || doLTSmin) {
				// || examination.startsWith("CTL")
				if (! reader.getSpec().getProperties().isEmpty()) {
					System.out.println("Using solver "+solver+" to compute partial order matrices.");
					ltsminRunner = new LTSminRunner(ltsminpath, solverPath, solver, doPOR, onlyGal, reader.getFolder(), 3600 / reader.getSpec().getProperties().size() );				
					ltsminRunner.configure(EcoreUtil.copy(reader.getSpec()), doneProps);
					ltsminRunner.solve(this);
				}
			}
			if (doITS || onlyGal) {	
				if (examination.equals("ReachabilityDeadlock")|| examination.equals("GlobalProperties"))
						reader.flattenSpec(true);
				// decompose + simplify as needed
				itsRunner = new ITSRunner(examination, reader, doITS, onlyGal, reader.getFolder(),3600,orderff);
				itsRunner.configure(reader.getSpec(), doneProps);
				if (doITS) {
					itsRunner.solve(this);
				}
			}			
			
			if (itsRunner != null)
				itsRunner.join();
			if (ltsminRunner != null) 
				ltsminRunner.join();
		
			return 0;
		}
		
		
		// ReachabilityCard and ReachFire are ok for everybody
		if (examination.equals("ReachabilityFireability") || examination.equals("ReachabilityCardinality") ) {
			reader.flattenSpec(false);
			// get rid of trivial properties in spec
			checkInInitial(reader.getSpec(), doneProps);

			// SMT does support hierarchy theoretically but does not like it much currently, time to start it, the spec won't get any better
			if ( (z3path != null || yices2path != null) && doSMT ) {
				Specification z3Spec = EcoreUtil.copy(reader.getSpec());
				// run on a fresh copy to avoid any interference with other threads. (1 hour timeout)
				z3Runner = new SMTRunner(pwd, solverPath, solver, 3600);
				z3Runner.configure(z3Spec, doneProps);
				z3Runner.solve(this);
			}

			// run on a fresh copy to avoid any interference with other threads.
			if (doCegar) {
				cegarRunner = new CegarRunner(pwd);
				cegarRunner.configure(EcoreUtil.copy(reader.getSpec()), doneProps);
				cegarRunner.solve(this);
			}								

			// run LTS min
			if (onlyGal || doLTSmin) {
				if (! reader.getSpec().getProperties().isEmpty() ) {
					System.out.println("Using solver "+solver+" to compute partial order matrices.");
					ltsminRunner = new LTSminRunner(ltsminpath, solverPath, solver, doPOR, onlyGal, reader.getFolder(), 3600 / reader.getSpec().getProperties().size() );				
					ltsminRunner.configure(EcoreUtil.copy(reader.getSpec()), doneProps);
					ltsminRunner.solve(this);
				}
			}

			
			if (doITS || onlyGal) {				
				// decompose + simplify as needed
				reader.flattenSpec(doHierarchy);
			}					
			if (doITS || onlyGal) {				
				// decompose + simplify as needed
				itsRunner = new ITSRunner(examination, reader, doITS, onlyGal, reader.getFolder(),3600,orderff);
				itsRunner.configure(reader.getSpec(), doneProps);
			}			
					
			if (doITS) {
				itsRunner.solve(this);
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
	@Override
	public void stop() {
		killAll();
	}
	
	
	

}
