package fr.lip6.move.gal.application;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import android.util.SparseIntArray;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.BoolProp;
import fr.lip6.move.gal.BoundsProp;
import fr.lip6.move.gal.CTLProp;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.LTLProp;
import fr.lip6.move.gal.LogicProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.gal2smt.DeadlockTester;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.instantiate.Simplifier;
import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.logic.saxparse.PropertyParser;
import fr.lip6.move.gal.logic.togal.ToGalTransformer;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.mcc.properties.PropertiesToPNML;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.NoDeadlockExists;
import fr.lip6.move.gal.structural.RandomExplorer;
import fr.lip6.move.gal.structural.SparseHLPetriNet;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.util.MatrixCol;
import fr.lip6.move.gal.structural.StructuralToGreatSPN;
import fr.lip6.move.gal.structural.StructuralToPNML;
import fr.lip6.move.serialization.SerializationUtil;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication, Ender {

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
		Logger logger = Logger.getLogger("fr.lip6.move.gal");
		logger.setUseParentHandlers(false);
		logger.addHandler(new ConsoleHandler() {
			{
				setOutputStream(System.out);
			}
		});
	}

	static final int DEBUG = 0;
	private static final String APPARGS = "application.args";

	private static final String PNFOLDER = "-pnfolder";

	private static final String EXAMINATION = "-examination";
	private static final String Z3PATH = "-z3path";
	private static final String YICES2PATH = "-yices2path";
	private static final String SMT = "-smt";
	private static final String ITS = "-its";
	private static final String MANYORDER = "-manyOrder";
	private static final String CEGAR = "-cegar";
	private static final String LTSMIN = "-ltsmin";
	private static final String ONLYGAL = "-onlyGal";
	private static final String disablePOR = "-disablePOR";
	private static final String disableSDD = "-disableSDD";
	private static final String READ_GAL = "-readGAL";
	private static final String USE_LOUVAIN = "-louvain";
	private static final String ORDER_FLAG = "-order";
	private static final String GSPN_PATH = "-greatspnpath";
	private static final String BLISS_PATH = "-blisspath";
	private static final String SPOT_PATH = "-spotpath";
	private static final String SPOTMC_PATH = "-spotmcpath";
	private static final String TIMEOUT = "-timeout";
	private static final String REBUILDPNML = "-rebuildPNML";

	private List<IRunner> runners = new ArrayList<>();

	private static Logger logger = Logger.getLogger("fr.lip6.move.gal");

	private boolean wasKilled = false;
	private long startTime;

	@Override
	public synchronized void killAll() {
		wasKilled = true;
		for (IRunner runner : runners) {
			if (runner != null) {
				runner.interrupt();
			}
		}
		try {
			Runtime.getRuntime().exec("killall cc1 z3 its-reach its-ctl its-ltl pins2lts-seq pins2lts-mc modelcheck");
			Thread.yield();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// System.exit(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.
	 * IApplicationContext)
	 */
	@Override
	public Object start(IApplicationContext context) throws Exception {
		try {
			return startNoEx(context);
		} catch (Exception e) {
			System.err.println("Application raised an uncaught exception " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public Object startNoEx(IApplicationContext context) throws Exception {
		System.setErr(System.out);
		String[] args = (String[]) context.getArguments().get(APPARGS);

		logger.info("Running its-tools with arguments : " + Arrays.toString(args));
		startTime = System.currentTimeMillis();

		String pwd = null;
		String examination = null;
		String z3path = null;
		String yices2path = null;
		String readGAL = null;
		String gspnpath = null;
		String blisspath = null;
		String spotPath = null;
		String spotmcPath = null;
		String orderHeur = null;

		boolean doITS = false;
		boolean doSMT = false;
		boolean doCegar = false;
		boolean onlyGal = false;
		boolean doLTSmin = false;
		boolean doPOR = true;
		boolean doHierarchy = true;
		boolean useLouvain = false;
		boolean useManyOrder = false;
		boolean rebuildPNML = false;

		long timeout = 3600;

		for (int i = 0; i < args.length; i++) {
			if (PNFOLDER.equals(args[i])) {
				pwd = args[++i];
			} else if (EXAMINATION.equals(args[i])) {
				examination = args[++i];
			} else if (Z3PATH.equals(args[i])) {
				z3path = args[++i];
			} else if (YICES2PATH.equals(args[i])) {
				yices2path = args[++i];
			} else if (SPOT_PATH.equals(args[i])) {
				spotPath = args[++i];
			} else if (SPOTMC_PATH.equals(args[i])) {
				spotmcPath = args[++i];
			} else if (GSPN_PATH.equals(args[i])) {
				gspnpath = args[++i];
			} else if (BLISS_PATH.equals(args[i])) {
				blisspath = args[++i];
			} else if (ORDER_FLAG.equals(args[i])) {
				orderHeur = args[++i];
			} else if (SMT.equals(args[i])) {
				doSMT = true;
			} else if (LTSMIN.equals(args[i])) {
				doLTSmin = true;
			} else if (READ_GAL.equals(args[i])) {
				readGAL = args[++i];
			} else if (TIMEOUT.equals(args[i])) {
				timeout = Long.parseLong(args[++i]);
			} else if (REBUILDPNML.equals(args[i])) {
				rebuildPNML = true;
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
			} else if (MANYORDER.equals(args[i])) {
				useManyOrder = true;
			}
		}

		// use Z3 in preference to Yices if both are available
		Solver solver = Solver.Z3;
		String solverPath = z3path;
		if (z3path == null && yices2path != null) {
			solver = Solver.YICES2;
			solverPath = yices2path;
		}

		// EMF registration
		SerializationUtil.setStandalone(true);

		// setup a "reader" that parses input property files correctly and efficiently
		MccTranslator reader = new MccTranslator(pwd, examination, useLouvain);

		try {
			if (readGAL == null) {
				// parse the model from PNML to GAL using PNMLFW for COL or fast SAX for PT
				// models
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

		// for debug and control COL files are small, otherwise 1MB PNML limit (i.e.
		// roughly 200kB GAL max).
		if (pwd.contains("COL") || new File(pwd + "/model.pnml").length() < 1000000) {
			String outpath = pwd + "/model.pnml.img.gal";
			// SerializationUtil.systemToFile(reader.getSpec(), outpath);
		}

		if (examination.equals("StableMarking") || examination.equals("OneSafe")
				|| examination.equals("QuasiLiveness")) {
			GlobalPropertySolver gps = new GlobalPropertySolver(solverPath);
			boolean b = gps.solveProperty(examination, reader);

			if (b) {
				return null;
			}

		}

		boolean isSafe = false;
		// load "known" stuff about the model
		if (reader.isSafeNet()) {
			// NUPN implies one safe
			isSafe = true;
		}

		// initialize a shared container to detect help detect termination in portfolio
		// case
		DoneProperties doneProps = new MccDonePropertyPrinter();

		// reader now has a spec and maybe a ITS decomposition
		// no properties yet.

		// A filename to store the variable ordering, if we compute it with e.g.
		// GreatSPN
		String orderff = null;
		if (orderHeur != null && gspnpath != null) {
			doHierarchy = false;
		}

		if (examination.equals("StateSpace")) {
			int totaltok = reader.getSPN().removeConstantPlaces();
			reader.getSPN().removeRedundantTransitions(true);
			// above step may lead to additional simplifications
			totaltok += reader.getSPN().removeConstantPlaces();
			if (totaltok > 0) {
				reader.setMissingTokens(totaltok);
			}
			System.out.println("Final net has " + reader.getSPN().getPlaceCount() + " places and "
					+ reader.getSPN().getTransitionCount() + " transitions.");
			reader.rebuildSpecification(doneProps);
			// ITS is the only method we will run.
			reader = runMultiITS(pwd, examination, gspnpath, orderHeur, doITS, onlyGal, doHierarchy, useManyOrder,
					reader, doneProps, useLouvain, timeout);

			return 0;
		}

		Specification specnocol = null;
		// // Abstraction case
		if (false && pwd.contains("COL")
				&& (examination.equals("ReachabilityFireability") || examination.equals("ReachabilityCardinality"))) {
			ToGalTransformer.setWithAbstractColors(true);
			String pname = pwd + "/" + examination + ".xml";
			specnocol = EcoreUtil.copy(reader.getSpec());
			Properties props = PropertyParser.fileToProperties(pname, specnocol);
			Instantiator.instantiateParametersWithAbstractColors(specnocol);
			specnocol = ToGalTransformer.toGal(props);
			GALRewriter.flatten(specnocol, true);
			ToGalTransformer.setWithAbstractColors(false);
		}

		// Now translate and load properties into GAL
		// uses a SAX parser to load to Logic MM, then an M2M to GAL properties.
		reader.loadProperties();

		// are we going for CTL ? only ITSRunner answers this.
		if (examination.startsWith("CTL") || examination.equals("UpperBounds")) {

			if (examination.startsWith("CTL")) {
				reader.createSPN();
				new AtomicReducerSR().strongReductions(solverPath, reader, isSafe, doneProps);
				reader.getSPN().simplifyLogic();
				reader.rebuildSpecification(doneProps);
				checkInInitial(reader.getSpec(), doneProps, isSafe);
				reader.flattenSpec(false);
				checkInInitial(reader.getSpec(), doneProps, isSafe);
//				new AtomicReducer().strongReductions(solverPath, reader, isSafe, doneProps);
//				Simplifier.simplify(reader.getSpec());

				// due to + being OR in the CTL syntax, we don't support this type of props
				// TODO: make CTL syntax match the normal predicate syntax in ITS tools
				// reader.removeAdditionProperties();
			}
			if (examination.equals("UpperBounds")) {
				List<Integer> skelBounds = null;
				if (reader.getHLPN() != null) {
					skelBounds = UpperBoundsSolver.treatSkeleton(reader, doneProps, solverPath);

					reader.getHLPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
				}

				reader.createSPN();
				reader.getSPN().simplifyLogic();
				reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));

				UpperBoundsSolver.checkInInitial(reader, doneProps);

				UpperBoundsSolver.applyReductions(reader, doneProps, solverPath, isSafe, skelBounds);

				reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
				// checkInInitial(reader.getSpec(), doneProps, isSafe);

				if (doneProps.keySet().containsAll(
						reader.getSPN().getProperties().stream().map(p -> p.getName()).collect(Collectors.toList()))) {
					System.out.println("All properties solved without resorting to model-checking.");
					return null;
				} else {
					for (int pid = 0; pid < reader.getSPN().getProperties().size(); pid++) {
						long time = System.currentTimeMillis();
						MccTranslator r2 = reader.copy();
						fr.lip6.move.gal.structural.Property p = r2.getSPN().getProperties().get(pid);
						System.out.println("Starting property specific reduction for " + p.getName());
						r2.getSPN().getProperties().clear();
						r2.getSPN().getProperties().add(p);
						UpperBoundsSolver.checkInInitial(r2, doneProps);
						UpperBoundsSolver.applyReductions(r2, doneProps, solverPath, isSafe, null);
						System.out.println("Ending property specific reduction for " + p.getName() + " in "
								+ (System.currentTimeMillis() - time) + " ms.");
					}
					reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
				}
				reader.rebuildSpecification(doneProps);

			}

			tryRebuildPNML(pwd, examination, rebuildPNML, reader, doneProps);

			reader = runMultiITS(pwd, examination, gspnpath, orderHeur, doITS, onlyGal, doHierarchy, useManyOrder,
					reader, doneProps, useLouvain, timeout);
			return 0;
		}

		System.out.println("Working with output stream " + System.out.getClass());
		// LTL, Deadlocks are ok for LTSmin and ITS
		if (examination.startsWith("LTL") || examination.equals("ReachabilityDeadlock")
				|| examination.equals("GlobalProperties")) {

			if (examination.startsWith("LTL")) {
				reader.createSPN();
				if (spotPath != null) {
					SpotRunner sr = new SpotRunner(spotPath, pwd, 10);
					sr.runLTLSimplifications(reader.getSPN());
				}
				int solved = new AtomicReducerSR().strongReductions(solverPath, reader, isSafe, doneProps);
				reader.rebuildSpecification(doneProps);
				solved += checkInInitial(reader.getSpec(), doneProps, isSafe);

				reader.flattenSpec(false);
				Simplifier.simplify(reader.getSpec());
				solved += checkInInitial(reader.getSpec(), doneProps, isSafe);

				if (solved > 0) {
					reader.rebuildSPN();
					reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
					if (spotPath != null) {
						SpotRunner sr = new SpotRunner(spotPath, pwd, 10);
						sr.runLTLSimplifications(reader.getSPN());
					}
				}
				reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
			} else if (examination.equals("ReachabilityDeadlock") || examination.equals("GlobalProperties")) {

				long debut = System.currentTimeMillis();

				if (reader.getHLPN() != null) {
					SparsePetriNet spn = reader.getHLPN().skeleton();
					spn.toPredicates();
					spn.testInInitial();

					// this might break the consistency between hlpn and skeleton place indexes,
					// let's avoid it.
					spn.removeConstantPlaces();
					// spn.removeRedundantTransitions(false);
					// spn.removeConstantPlaces();
					spn.simplifyLogic();

					StructuralReduction sr = new StructuralReduction(spn);
					try {
						Set<String> before = new HashSet<>(sr.getPnames());
						Set<Integer> safeNodes = sr.findSCCSuffixes(ReductionType.DEADLOCKS);

						if (safeNodes != null) {
							Set<String> torem = new HashSet<>(before);
							torem.removeAll(sr.getPnames());

							Set<Integer> hlSafeNodes = new HashSet<>();
							SparseHLPetriNet hlpn = reader.getHLPN();
							for (int pid = 0; pid < hlpn.getPlaces().size(); pid++) {
								if (!torem.contains(hlpn.getPlaces().get(pid).getName())) {
									hlSafeNodes.add(pid);
								}
							}
							hlpn.dropAllExcept(hlSafeNodes);
							// System.out.println(hlpn);
						}

					} catch (DeadlockFound e) {
						System.out.println("FORMULA " + reader.getHLPN().getProperties().get(0).getName()
								+ " TRUE TECHNIQUES CPN_APPROX TOPOLOGICAL STRUCTURAL_REDUCTION");
						return null;
					}

				}

				reader.createSPN();

				// remove parameters
//				reader.flattenSpec(false);
//				Specification spec = reader.getSpec();
//				System.out.println("Flatten gal took : " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$				
//				String outpath = pwd + "/model.pnml.simple.gal";
//				SerializationUtil.systemToFile(reader.getSpec(), outpath);

				try {
					long tt = System.currentTimeMillis();
					SparsePetriNet spn = reader.getSPN();
					StructuralReduction sr = new StructuralReduction(spn);

					System.out.println("Built sparse matrix representations for Structural reductions in "
							+ (System.currentTimeMillis() - tt) + " ms."
							+ ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000)
							+ "KB memory used");

					if (false && blisspath != null) {
						List<List<List<Integer>>> generators = null;
						BlissRunner br = new BlissRunner(blisspath, pwd, 100);
						generators = br.run(sr);
						System.out.println("Obtained generators : " + generators);
						br.computeMatrixForm(generators);
					}
					try {
						if (!ReachabilitySolver.applyReductions(sr, reader, ReductionType.DEADLOCKS, solverPath, isSafe,
								false, true))
							ReachabilitySolver.applyReductions(sr, reader, ReductionType.DEADLOCKS, solverPath, isSafe,
									true, false);
					} catch (DeadlockFound d) {
						System.out.println("FORMULA " + reader.getSPN().getProperties().get(0).getName()
								+ " TRUE TECHNIQUES TOPOLOGICAL STRUCTURAL_REDUCTION");
						return null;
					}

					if (false) {
						FlowPrinter.drawNet(sr, "initial");
						String outsr = pwd + "/model.sr.pnml";
						StructuralToPNML.transform(reader.getSPN(), outsr);
						String outform = pwd + "/" + examination + ".sr.xml";
						PropertiesToPNML.transform(spn, outform, doneProps);
					}

					if (blisspath != null) {
						boolean hasConcluded = runBlissSymmetryAnalysis(reader, sr, isSafe, blisspath, pwd, solverPath);
						if (hasConcluded) {
							return null;
						}
					}

					RandomExplorer re = new RandomExplorer(sr);
					long time = System.currentTimeMillis();
					// 25 k step
					int steps = 1250000;
					re.runDeadlockDetection(steps, true, 30);
					if (sr.getTnames().size() < 20000) {
						time = System.currentTimeMillis();
						re.runDeadlockDetection(steps, false, 30);
					}

					if (solverPath != null) {
						try {
							List<Integer> repr = new ArrayList<>();
							SparseIntArray parikh = DeadlockTester.testDeadlocksWithSMT(sr, solverPath, isSafe, repr);
							if (parikh == null) {
								System.out.println("FORMULA " + reader.getSPN().getProperties().get(0).getName()
										+ " FALSE TECHNIQUES TOPOLOGICAL SAT_SMT STRUCTURAL_REDUCTION");
								return null;
							} else {
								int sz = 0;
								for (int i = 0; i < parikh.size(); i++) {
									sz += parikh.valueAt(i);
								}
								if (sz != 0) {
									if (DEBUG >= 1) {
										StringBuilder sb = new StringBuilder();
										for (int i = 0; i < parikh.size(); i++) {
											sb.append(sr.getTnames().get(parikh.keyAt(i)) + "=" + parikh.valueAt(i)
													+ ", ");
										}
										System.out.println("SMT solver thinks a deadlock is likely to occur in " + sz
												+ " steps after firing vector : " + sb.toString());
									}
									// FlowPrinter.drawNet(sr, "Parikh Test :" + sb.toString());
									time = System.currentTimeMillis();
									re.runGuidedDeadlockDetection(100 * sz, parikh, repr, 30);
								}
							}
						} catch (ArithmeticException e) {
							// in particular java.lang.ArithmeticException
							// at
							// uniol.apt.analysis.invariants.InvariantCalculator.test1b2(InvariantCalculator.java:448)
							// can occur here.
							System.out.println("Failed to apply SMT based deadlock test, skipping this step.");
							e.printStackTrace();
						}
					}

					time = System.currentTimeMillis();
					// 75 k steps in 3 traces
					int nbruns = 4;
					steps = 500000;
					for (int i = 1; i <= nbruns; i++) {
						re.runDeadlockDetection(steps, i % 2 == 0, 30);
					}

					re = null;

					reader.rebuildSpecification(doneProps);

				} catch (DeadlockFound e) {
					System.out.println("FORMULA " + reader.getSPN().getProperties().get(0).getName()
							+ " TRUE TECHNIQUES TOPOLOGICAL STRUCTURAL_REDUCTION RANDOM_WALK");
					return null;
				} catch (NoDeadlockExists e) {
					System.out.println("FORMULA " + reader.getSPN().getProperties().get(0).getName()
							+ " FALSE TECHNIQUES TOPOLOGICAL STRUCTURAL_REDUCTION");
					return null;
				} catch (Exception e) {
					System.out.println("Failed to apply structural reductions, skipping reduction step.");
					e.printStackTrace();
				}

			}
			if (doneProps.keySet().containsAll(
					reader.getSPN().getProperties().stream().map(p -> p.getName()).collect(Collectors.toList()))) {
				System.out.println("All properties solved without resorting to model-checking.");
				return null;
			} else
				tryRebuildPNML(pwd, examination, rebuildPNML, reader, doneProps);
			if (onlyGal || doLTSmin) {
				// || examination.startsWith("CTL")
				if (!reader.getSpec().getProperties().isEmpty()) {
					System.out.println("Using solver " + solver + " to compute partial order matrices.");
					LTSminRunner ltsRunner = new LTSminRunner(solverPath, solver, doPOR, onlyGal, reader.getFolder(),
							timeout / reader.getSpec().getProperties().size(), isSafe);
					ltsRunner.configure(EcoreUtil.copy(reader.getSpec()), doneProps);
					ltsRunner.setNet(reader.getSPN());
					runners.add(ltsRunner);
					ltsRunner.solve(this);
				}
			}
			if (spotmcPath != null) {
				SpotLTLRunner spotRun = new SpotLTLRunner(solverPath, solver, reader.getFolder(), timeout, isSafe,
						spotmcPath);
				spotRun.configure(EcoreUtil.copy(reader.getSpec()), doneProps);
				spotRun.setNet(reader.getSPN());
				runners.add(spotRun);
				spotRun.solve(this);
			}

			if (doITS || onlyGal) {
				reader = runMultiITS(pwd, examination, gspnpath, orderHeur, doITS, onlyGal, doHierarchy, useManyOrder,
						reader, doneProps, useLouvain, timeout);
			}

			for (IRunner r : runners) {
				if (r != null)
					r.join();
			}

			return 0;
		}

		// ReachabilityCard and ReachFire are ok for everybody
		if (examination.equals("ReachabilityFireability") || examination.equals("ReachabilityCardinality")) {

			if (true) {
				reader.createSPN();
				ReachabilitySolver.checkInInitial(reader, doneProps);
				if (!reader.getSPN().getProperties().isEmpty())
					ReachabilitySolver.applyReductions(reader, doneProps, solverPath, isSafe);

			} else {

				reader.flattenSpec(false);
				// get rid of trivial properties in spec
				checkInInitial(reader.getSpec(), doneProps, isSafe);

				if (specnocol != null) {
					specnocol.getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
					if (pwd.contains("COL") || new File(pwd + "/model.pnml").length() < 1000000) {
						String outpath = pwd + "/model.pnml.unc.gal";
						SerializationUtil.systemToFile(specnocol, outpath);
					}
					INextBuilder nb = INextBuilder.build(specnocol);
					IDeterministicNextBuilder idnb = IDeterministicNextBuilder.build(nb);
					StructuralReduction sr = new StructuralReduction(idnb);

					// need to protect some variables
					List<Property> l = reader.getSpec().getProperties();
					List<Expression> tocheck = translateProperties(l, idnb);
					if (solverPath != null) {
						List<Integer> repr = new ArrayList<>();
						List<SparseIntArray> paths = DeadlockTester.testUnreachableWithSMT(tocheck, sr, solverPath,
								isSafe, repr, 100, true);
						int iter = 0;
						for (int v = paths.size() - 1; v >= 0; v--) {
							SparseIntArray parikh = paths.get(v);
							if (parikh == null) {
								Property prop = specnocol.getProperties().get(v);
								if (prop.getBody() instanceof ReachableProp) {
									doneProps.put(prop.getName(), false,
											"COLOR_ABSTRACTION STRUCTURAL_REDUCTION TOPOLOGICAL SAT_SMT");
								} else {
									doneProps.put(prop.getName(), true,
											"COLOR_ABSTRACTION STRUCTURAL_REDUCTION TOPOLOGICAL SAT_SMT");
								}
								iter++;
							}
						}
						if (reader.getSpec().getProperties().removeIf(p -> doneProps.containsKey(p.getName()))) {
							System.out.println("Colored abstraction solved " + iter + " properties.");
						}
					}
				}

				if (!reader.getSpec().getProperties().isEmpty())
					ReachabilitySolver.applyReductions(reader, doneProps, solverPath, isSafe);

				// Per property approach = WIP
//				for (Property prop : new ArrayList<>(reader.getSpec().getProperties())) {
//					if (! doneProps.contains(prop.getName())) {
//						INextBuilder nb2 = INextBuilder.build(reader.getSpec());
//						IDeterministicNextBuilder idnb2 = IDeterministicNextBuilder.build(nb2);			
//						StructuralReduction sr2 = new StructuralReduction(idnb2);
//						BitSet support2 = new BitSet();
//						NextSupportAnalyzer.computeQualifiedSupport(prop, support2, idnb2);						
//						sr2.setProtected(support);
//						MccTranslator reader2 = reader.copy();
//						applyReductions(sr2, reader2, ReductionType.SAFETY, solverPath, isSafe);
//						
//						Specification reduced2 = sr2.rebuildSpecification();
//						reduced2.getProperties().add(EcoreUtil.copy(prop));
//						Instantiator.normalizeProperties(reduced2);
//						reader2.setSpec(reduced2);
//						reader2.flattenSpec(false);
//						checkInInitial(reader2.getSpec(), doneProps);						
//					}
//				}

				// }

			}

			if (rebuildPNML && false)
				regeneratePNML(reader, doneProps, solverPath, isSafe);

			if (doneProps.keySet().containsAll(
					reader.getSPN().getProperties().stream().map(p -> p.getName()).collect(Collectors.toList()))) {
				System.out.println("All properties solved without resorting to model-checking.");
				return null;
			} else
				tryRebuildPNML(pwd, examination, rebuildPNML, reader, doneProps);

			if (false) {
				MatrixCol sumP = MatrixCol.sumProd(-1, reader.getSPN().getFlowPT(), 1, reader.getSPN().getFlowTP());
				Set<SparseIntArray> invar = InvariantCalculator.computePInvariants(sumP, reader.getSPN().getPnames(),
						true);
				InvariantCalculator.printInvariant(invar, reader.getSPN().getPnames(), reader.getSPN().getMarks());
			}
			reader.rebuildSpecification(doneProps);
			// SMT does support hierarchy theoretically but does not like it much currently,
			// time to start it, the spec won't get any better
			if ((z3path != null || yices2path != null) && doSMT) {
				Specification z3Spec = EcoreUtil.copy(reader.getSpec());
				// run on a fresh copy to avoid any interference with other threads. (1 hour
				// timeout)
				IRunner z3Runner = new SMTRunner(pwd, solverPath, solver, timeout, isSafe);
				z3Runner.configure(z3Spec, doneProps);
				runners.add(z3Runner);
				z3Runner.solve(this);
			}

			// run on a fresh copy to avoid any interference with other threads.
			if (doCegar) {
				IRunner cegarRunner = new CegarRunner(pwd);
				cegarRunner.configure(EcoreUtil.copy(reader.getSpec()), doneProps);
				runners.add(cegarRunner);
				cegarRunner.solve(this);
			}

			// run LTS min
			if (onlyGal || doLTSmin) {
				if (!reader.getSpec().getProperties().isEmpty()) {
					System.out.println("Using solver " + solver + " to compute partial order matrices.");
					IRunner ltsminRunner = new LTSminRunner(solverPath, solver, doPOR, onlyGal, reader.getFolder(),
							timeout / reader.getSpec().getProperties().size(), isSafe);
					ltsminRunner.configure(EcoreUtil.copy(reader.getSpec()), doneProps);
					runners.add(ltsminRunner);
					ltsminRunner.solve(this);
				}
			}

			reader = runMultiITS(pwd, examination, gspnpath, orderHeur, doITS, onlyGal, doHierarchy, useManyOrder,
					reader, doneProps, useLouvain, timeout);

		}

		for (IRunner r : runners) {
			if (r != null) {
				r.join();
			}
		}
		return IApplication.EXIT_OK;
	}

	private boolean runBlissSymmetryAnalysis(MccTranslator reader, StructuralReduction sr, boolean isSafe,
			String blisspath, String pwd, String solverPath) throws TimeoutException {
		boolean hasConcluded = false;
		List<List<List<Integer>>> generators = null;
		BlissRunner br = new BlissRunner(blisspath, pwd, 100);
		generators = br.run(sr);
		System.out.println("Obtained generators : " + generators);
		List<Set<List<Integer>>> gen = br.computeMatrixForm(generators);
		if (!gen.isEmpty()) {
			StructuralReduction sr2 = sr.clone();
			// attempt fusion

			for (Set<List<Integer>> set : gen) {
				if (set.size() >= 2) {
					Iterator<List<Integer>> ite = set.iterator();
					List<Integer> base = ite.next();
					while (ite.hasNext()) {
						sr2.fusePlaces(base, ite.next());
					}
				}
			}
			boolean conti = true;
			try {
				sr2.reduce(ReductionType.DEADLOCKS);
			} catch (DeadlockFound df) {
				conti = false;
			} catch (NoDeadlockExists ne) {
				System.out.println("FORMULA " + reader.getSpec().getProperties().get(0).getName()
						+ " FALSE TECHNIQUES TOPOLOGICAL SAT_SMT STRUCTURAL_REDUCTION SYMMETRIES");
				hasConcluded = true;
				conti = false;
			}
			if (conti) {
				List<Integer> repr = new ArrayList<>();
				SparseIntArray parikh = DeadlockTester.testDeadlocksWithSMT(sr2, solverPath, isSafe, repr);
				if (parikh == null) {
					System.out.println("FORMULA " + reader.getSpec().getProperties().get(0).getName()
							+ " FALSE TECHNIQUES TOPOLOGICAL SAT_SMT STRUCTURAL_REDUCTION SYMMETRIES");
					hasConcluded = true;
				}
			}
			if (!hasConcluded)
				System.out.println("Symmetry overapproximation was not able to conclude.");
		}
		return hasConcluded;
	}

	private void tryRebuildPNML(String pwd, String examination, boolean rebuildPNML, MccTranslator reader,
			DoneProperties doneProps) throws IOException {
		if (rebuildPNML) {
			String outform = pwd + "/" + examination + ".sr.xml";
			boolean usesConstants = PropertiesToPNML.transform(reader.getSPN(), outform, doneProps);
			if (usesConstants) {
				// we exported constants to a place with index = current place count
				// to be consistent now add a trivially constant place with initial marking 1
				// token
				reader.getSPN().addPlace("one", 1);
			}
			String outsr = pwd + "/model.sr.pnml";
			StructuralToPNML.transform(reader.getSPN(), outsr);
		}
	}

	public int rebuildSpecification(MccTranslator reader, StructuralReduction sr) {
		Specification reduced = sr.rebuildSpecification();
		reduced.getProperties().addAll(reader.getSpec().getProperties());
		Instantiator.normalizeProperties(reduced);
		Set<String> constants = sr.computeConstants().stream().map(n -> sr.getPnames().get(n))
				.collect(Collectors.toSet());
		Map<ArrayPrefix, Set<Integer>> constantArrs = new HashMap<>();
		Set<Variable> constvars = new HashSet<>();
		GALTypeDeclaration gal = (GALTypeDeclaration) reduced.getTypes().get(0);
		for (Variable var : gal.getVariables()) {
			if (constants.contains(var.getName())) {
				constvars.add(var);
			}
		}
		int done = Simplifier.replaceConstants(gal, constvars, constantArrs);
		reader.setSpec(reduced);
		return done;
	}

	private void regeneratePNML(MccTranslator reader, DoneProperties doneProps, String solverPath, boolean isSafe) {
		reader.flattenSpec(false);
		System.out.println(
				"Initial size " + ((GALTypeDeclaration) reader.getSpec().getTypes().get(0)).getVariables().size());
		for (Entry<String, Boolean> prop : doneProps.entrySet()) {
			System.out.println("For property " + prop.getKey() + " final size  0 : handled without model checking");
		}
		for (Property prop : reader.getSpec().getProperties()) {
			try {
				if (((BoolProp) prop.getBody()).getPredicate() instanceof True
						|| ((BoolProp) prop.getBody()).getPredicate() instanceof False) {
					System.out.println(
							"For property " + prop.getName() + " final size  0 : handled without model checking");
				} else {
					MccTranslator copy = reader.copy();
					copy.getSpec().getProperties().removeIf(p -> !p.getName().equals(prop.getName()));
					ReachabilitySolver.applyReductions(copy, doneProps, solverPath, isSafe);
					System.out.println("For property " + prop.getName() + " final size "
							+ ((GALTypeDeclaration) copy.getSpec().getTypes().get(0)).getVariables().size());
				}
			} catch (NoDeadlockExists | DeadlockFound e) {
				e.printStackTrace();
			}
		}

	}

	private List<Expression> translateProperties(List<Property> props, IDeterministicNextBuilder idnb) {
		List<Expression> tocheck = new ArrayList<Expression>();
		for (Property prop : props) {
			if (prop.getBody() instanceof NeverProp) {
				NeverProp never = (NeverProp) prop.getBody();
				tocheck.add(Expression.buildExpression(never.getPredicate(), idnb));
			} else if (prop.getBody() instanceof InvariantProp) {
				InvariantProp invar = (InvariantProp) prop.getBody();
				tocheck.add(Expression.not(Expression.buildExpression(invar.getPredicate(), idnb)));
			} else if (prop.getBody() instanceof ReachableProp) {
				ReachableProp reach = (ReachableProp) prop.getBody();
				tocheck.add(Expression.buildExpression(reach.getPredicate(), idnb));
			}
		}
		return tocheck;
	}

	private MccTranslator runMultiITS(String pwd, String examination, String gspnpath, String orderHeur, boolean doITS,
			boolean onlyGal, boolean doHierarchy, boolean useManyOrder, MccTranslator reader, DoneProperties doneProps,
			boolean useLouvain, long timeout) throws IOException, InterruptedException {
		MccTranslator reader2 = null;
		long elapsed = (startTime - System.currentTimeMillis()) / 1000;
		timeout -= elapsed;
		if (useManyOrder) {
			reader2 = reader.copy();
			timeout /= 3;
		} else {
			reader2 = reader;
		}

		if (!wasKilled && (useLouvain || useManyOrder)) {
//			if (useManyOrder)
//				reader = reader2.copy();
			reader.getSpec().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
			reader.setLouvain(true);
			reader.setOrder(null);
			reader.flattenSpec(true);

			if (doITS || onlyGal) {
				// decompose + simplify as needed
				IRunner itsRunner = new ITSRunner(examination, reader, doITS, onlyGal, reader.getFolder(), timeout,
						null);
				itsRunner.configure(reader.getSpec(), doneProps);
				runners.add(itsRunner);
				if (doITS) {
					itsRunner.solve(this);
					itsRunner.join();
				}
				runners.remove(itsRunner);
			}

		}

		if (!wasKilled && (doITS || onlyGal) && (!useLouvain || useManyOrder)) {
			if (useManyOrder)
				reader = reader2.copy();
			reader.getSpec().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
			if (reader.getHLPN() != null) {
				reader.setOrder(reader.getHLPN().computeOrder());
			}
			reader.flattenSpec(true);

			if (doITS || onlyGal) {
				// decompose + simplify as needed
				IRunner itsRunner = new ITSRunner(examination, reader, doITS, onlyGal, reader.getFolder(), timeout,
						null);
				itsRunner.configure(reader.getSpec(), doneProps);
				runners.add(itsRunner);
				if (doITS) {
					itsRunner.solve(this);
					itsRunner.join();
				}
				runners.remove(itsRunner);
			}

		}

		if (!wasKilled && orderHeur != null && gspnpath != null) {
			if (useManyOrder)
				reader = reader2.copy();

			reader.flattenSpec(false);
			reader.getSpec().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
			String myOrderff = null;
			if (orderHeur != null) {
				myOrderff = computeOrderWithGreatSPN(pwd, gspnpath, orderHeur, reader, myOrderff);
			}

			if (doITS || onlyGal) {
				// decompose + simplify as needed
				IRunner itsRunner = new ITSRunner(examination, reader, doITS, onlyGal, reader.getFolder(), timeout,
						myOrderff);
				itsRunner.configure(reader.getSpec(), doneProps);
				runners.add(itsRunner);
				if (doITS) {
					itsRunner.solve(this);
					itsRunner.join();
				}
				runners.remove(itsRunner);
			}

		}

		return reader;
	}

	private String computeOrderWithGreatSPN(String pwd, String gspnpath, String orderHeur, MccTranslator reader,
			String orderff) {
		if (orderHeur != null && gspnpath != null) {
			// No hierarchy in this path
			try {

				INextBuilder nb = INextBuilder.build(reader.getSpec());
				IDeterministicNextBuilder idnb = IDeterministicNextBuilder.build(nb);
				StructuralReduction sr = new StructuralReduction(idnb);
				StructuralToGreatSPN s2gspn = new StructuralToGreatSPN();
				String gspnmodelff = pwd + "/gspn";
				s2gspn.transform(sr, gspnmodelff);
				try {
					GreatSPNRunner pinvrun = new GreatSPNRunner(pwd, gspnmodelff, gspnpath + "/bin/pinvar", 30);
					pinvrun.run();
				} catch (TimeoutException e) {
					System.out.println("P-invariant computation with GreatSPN timed out. Skipping.");
				}

				GreatSPNRunner run = new GreatSPNRunner(pwd, gspnmodelff, gspnpath + "/bin/RGMEDD2", 60);
				run.configure("-" + orderHeur);
				run.configure("-varord-only");
				run.run();
				String[] order = run.getOrder();

				Specification reduced = sr.rebuildSpecification();
				reduced.getProperties().addAll(reader.getSpec().getProperties());
				Instantiator.normalizeProperties(reduced);
				reader.setSpec(reduced);

				orderff = pwd + "/" + "model.ord";
				PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(orderff)));
				out.println("#TYPE " + reduced.getMain().getName());
				for (int i = 0; i < order.length; i++) {
					String var = order[i];
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
		}
		return orderff;
	}

	/**
	 * Structural analysis and reduction : test in initial state.
	 * 
	 * @param specWithProps spec which will be modified : trivial properties will be
	 *                      removed
	 * @param doneProps
	 */
	private int checkInInitial(Specification specWithProps, DoneProperties doneProps, boolean isSafe) {
		List<Property> props = new ArrayList<Property>(specWithProps.getProperties());
		int done = 0;
		// iterate down so indexes are consistent
		for (int i = props.size() - 1; i >= 0; i--) {
			Property propp = props.get(i);

			if (doneProps.containsKey(propp.getName())) {
				specWithProps.getProperties().remove(i);
				continue;
			}
			if (isSafe) {
				for (TreeIterator<EObject> ti = propp.getBody().eAllContents(); ti.hasNext();) {
					EObject obj = ti.next();
					if (obj instanceof Comparison) {
						Comparison cmp = (Comparison) obj;

						if (cmp.getLeft() instanceof Reference && cmp.getRight() instanceof Constant) {
							int val = ((Constant) cmp.getRight()).getValue();
							if ((val > 1 && (cmp.getOperator() == ComparisonOperators.LE
									|| cmp.getOperator() == ComparisonOperators.LT))
									|| (val == 1 && cmp.getOperator() == ComparisonOperators.LE)) {
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createTrue());
								ti.prune();
							} else if (val > 1 || (val == 1 && cmp.getOperator() == ComparisonOperators.GT)) {
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createFalse());
								ti.prune();
							}
						} else if (cmp.getRight() instanceof Reference && cmp.getLeft() instanceof Constant) {
							int val = ((Constant) cmp.getLeft()).getValue();
							if ((val > 1 && (cmp.getOperator() == ComparisonOperators.GE
									|| cmp.getOperator() == ComparisonOperators.GT))
									|| (val == 1 && cmp.getOperator() == ComparisonOperators.GE)) {
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createTrue());
								ti.prune();
							} else if (val > 1 || (val == 1 && cmp.getOperator() == ComparisonOperators.LT)) {
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createFalse());
								ti.prune();
							}
						}
					}
				}
			}
			LogicProp prop = propp.getBody();
			Simplifier.simplifyAllExpressions(prop);

			String tech = "TOPOLOGICAL INITIAL_STATE";
			boolean solved = false;
			// output verdict
			if (prop instanceof ReachableProp || prop instanceof InvariantProp) {

				if (((SafetyProp) prop).getPredicate() instanceof True) {
					// positive forms : EF True , AG True <=>True
					doneProps.put(propp.getName(), true, tech);
					solved = true;
				} else if (((SafetyProp) prop).getPredicate() instanceof False) {
					// positive forms : EF False , AG False <=> False
					doneProps.put(propp.getName(), false, tech);
					solved = true;
				}
			} else if (prop instanceof NeverProp) {
				if (((SafetyProp) prop).getPredicate() instanceof True) {
					// negative form : ! EF P = AG ! P, so ! EF True <=> False
					doneProps.put(propp.getName(), false, tech);
					solved = true;
				} else if (((SafetyProp) prop).getPredicate() instanceof False) {
					// negative form : ! EF P = AG ! P, so ! EF False <=> True
					doneProps.put(propp.getName(), true, tech);
					solved = true;
				}
			} else if (prop instanceof LTLProp) {
				LTLProp ltl = (LTLProp) prop;
				if (ltl.getPredicate() instanceof True) {
					// positive forms : EF True , AG True <=>True
					doneProps.put(propp.getName(), true, tech);
					solved = true;
				} else if (ltl.getPredicate() instanceof False) {
					// positive forms : EF False , AG False <=> False
					doneProps.put(propp.getName(), false, tech);
					solved = true;
				}
			} else if (prop instanceof CTLProp) {
				CTLProp ltl = (CTLProp) prop;
				if (ltl.getPredicate() instanceof True) {
					// positive forms : EF True , AG True <=>True
					doneProps.put(propp.getName(), true, tech);
					solved = true;
				} else if (ltl.getPredicate() instanceof False) {
					// positive forms : EF False , AG False <=> False
					doneProps.put(propp.getName(), false, tech);
					solved = true;
				}
			} else if (prop instanceof BoundsProp) {
				BoundsProp bp = (BoundsProp) prop;
				if (bp.getTarget() instanceof Constant) {
					doneProps.put(propp.getName(), ((Constant) bp.getTarget()).getValue(), tech);
					solved = true;
				}
			}

			if (solved) {
				// discard property
				specWithProps.getProperties().remove(i);
				done++;
			}

		}
		return done;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	@Override
	public void stop() {
		killAll();
	}

}
