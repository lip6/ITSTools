package fr.lip6.move.gal.application;

import static fr.lip6.move.gal.structural.smt.SMTUtils.computeReducedFlow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import android.util.SparseIntArray;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.BoolProp;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.application.runner.CegarRunner;
import fr.lip6.move.gal.application.runner.Ender;
import fr.lip6.move.gal.application.runner.IRunner;
import fr.lip6.move.gal.application.runner.MccDonePropertyPrinter;
import fr.lip6.move.gal.application.runner.its.MultiOrderRunner;
import fr.lip6.move.gal.application.runner.ltsmin.LTSminRunner;
import fr.lip6.move.gal.application.runner.smt.SMTRunner;
import fr.lip6.move.gal.application.runner.spot.SpotLTLRunner;
import fr.lip6.move.gal.application.runner.spot.SpotRunner;
import fr.lip6.move.gal.application.solver.GALSolver;
import fr.lip6.move.gal.application.solver.ReachabilitySolver;
import fr.lip6.move.gal.application.solver.UpperBoundsSolver;
import fr.lip6.move.gal.application.solver.global.DeadlockSolver;
import fr.lip6.move.gal.application.solver.global.GlobalPropertySolver;
import fr.lip6.move.gal.application.solver.logic.AtomicReducerSR;
import fr.lip6.move.gal.application.solver.ltl.LTLAnalyzer;
import fr.lip6.move.gal.application.solver.ltl.LTLPropertySolver;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.instantiate.Simplifier;
import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.logic.saxparse.PropertyParser;
import fr.lip6.move.gal.logic.togal.ToGalTransformer;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.mcc.properties.PropertiesToPNML;
import fr.lip6.move.gal.pnml.togal.OverlargeMarkingException;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparseHLPetriNet;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.smt.DeadlockTester;
import fr.lip6.move.gal.util.IntMatrixCol;
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

	public static final int DEBUG = 0;
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
	private static final String EXPORT_LTL = "-exportLTL";
	private static final String UNFOLD = "--unfold";
	private static final String SKELETON = "--skeleton";
	private static final String NOSIMPLIFICATION = "--nosimplification";
	private static final String INVARIANT = "--invariant";
	private static final String APPLYSR = "--applySR";
	private static final String GENDEADTR = "--gen-dead-transition";
	private static final String GENDEADPL= "--gen-dead-place";
	private static final String ANALYZE_SENSITIVITY="--analyze-sensitivity";
	private static final String NOSLCLTEST="--no-slcl";
	private static final String NOKNOWLEDGE="--no-knowledge";
	private static final String NOSTUTTERLTL="--no-stutterltl";
	
	
	private List<IRunner> runners = new ArrayList<>();

	private static Logger logger = Logger.getLogger("fr.lip6.move.gal");

	private AtomicBoolean wasKilled = new AtomicBoolean(false);
	private long startTime;

	@Override
	public synchronized void killAll() {
		wasKilled.set(true);
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
		boolean exportLTL = false;
		boolean unfold =false;
		boolean skeleton =false;
		boolean nosimplifications = false;
		boolean invariants = false;
		boolean applySR = false;
		
		boolean genDeadTransitions = false;
		boolean genDeadPlaces = false;
		
		boolean analyzeSensitivity = false;
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
			} else if (UNFOLD.equals(args[i])) {
				unfold = true;
			} else if (INVARIANT.equals(args[i])) {
				invariants = true;
			} else if (SKELETON.equals(args[i])) {
				unfold = true;
				skeleton = true;
			} else if (NOSIMPLIFICATION.equals(args[i])) {
				nosimplifications = true;
			} else if (APPLYSR.equals(args[i])) {
				applySR = true;
			} else if (GENDEADPL.equals(args[i])) {
				genDeadPlaces = true;
				examination = "DeadPlace";
			} else if (GENDEADTR.equals(args[i])) {
				genDeadTransitions = true;
				examination = "DeadTransition";
			} else if (ANALYZE_SENSITIVITY.equals(args[i])) {
				analyzeSensitivity = true;
			} else if (EXPORT_LTL.equals(args[i])) {
				exportLTL = true;
			} else if (NOSLCLTEST.equals(args[i])) {
				LTLPropertySolver.noSLCLtest = true;
			} else if (NOKNOWLEDGE.equals(args[i])) {
				LTLPropertySolver.noKnowledgetest = true;
			} else if (NOSTUTTERLTL.equals(args[i])) {
				LTLPropertySolver.noStutterTest = true;
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
				try {
					reader.transformPNML();
				} catch (OverlargeMarkingException e) {
					if ("OneSafe".equals(examination)) {
						System.out.println("FORMULA OneSafe FALSE TECHNIQUES STRUCTURAL INITIAL_STATE");
						return null;
					} else {
						throw e;
					}
				}
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

		if (analyzeSensitivity) {
			SpotRunner spot = new SpotRunner(spotPath, pwd, 10);
			LTLAnalyzer.doSensitivityAnalysis(reader,spot,pwd,examination,solverPath);
			return IApplication.EXIT_OK;			
		}
		
		if (invariants) {
			reader.createSPN();
			List<Integer> tnames = new ArrayList<>();
			List<Integer> repr = new ArrayList<>();
			IntMatrixCol sumMatrix = computeReducedFlow(reader.getSPN(), tnames, repr);
			SparsePetriNet spn = reader.getSPN();
			Set<SparseIntArray> invar = InvariantCalculator.computePInvariants(sumMatrix, reader.getSPN().getPnames());		
			InvariantCalculator.printInvariant(invar, spn.getPnames(), reader.getSPN().getMarks());
			
			Set<SparseIntArray> invarT = DeadlockTester.computeTinvariants(reader.getSPN(), sumMatrix, tnames);
			List<Integer> empty = new ArrayList<>(tnames.size());
			for (int i=0 ; i < tnames.size(); i++) empty.add(0);
			List<String> strtnames = repr.stream().map(id -> spn.getTnames().get(id)).collect(Collectors.toList());
			InvariantCalculator.printInvariant(invarT, strtnames, empty );
			return null;
		}
		
		// for debug and control COL files are small, otherwise 1MB PNML limit (i.e.
		// roughly 200kB GAL max).
		if (pwd.contains("COL") || new File(pwd + "/model.pnml").length() < 1000000) {
			String outpath = pwd + "/model.pnml.img.gal";
			// SerializationUtil.systemToFile(reader.getSpec(), outpath);
		}

		if (examination.equals("StableMarking") || examination.equals("OneSafe")
				|| examination.equals("QuasiLiveness") || examination.equals("Liveness")) {
			GlobalPropertySolver gps = new GlobalPropertySolver(solverPath);
			Optional<Boolean> b = gps.solveProperty(examination, reader);

			if (b.isPresent() ) {
				return null;
			}

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
			{
				SparsePetriNet spn = reader.getSPN();
				StructuralReduction sr = new StructuralReduction(spn);
				ReachabilitySolver.applyReductions(sr,ReductionType.STATESPACE,solverPath,true,true);

				// Breaks max token per marking metric.
				int curtok = spn.getMarks().stream().mapToInt(i->i).sum();
				int newtok = sr.getMarks().stream().mapToInt(i->i).sum();				
				spn.readFrom(sr);
				reader.setMissingTokens( (curtok-newtok) + reader.countMissingTokens());
			}
			System.out.println("Final net has " + reader.getSPN().getPlaceCount() + " places and "
					+ reader.getSPN().getTransitionCount() + " transitions.");
			reader.rebuildSpecification(doneProps);
			// ITS is the only method we will run.
			reader = MultiOrderRunner.runMultiITS(pwd, examination, gspnpath, orderHeur, doITS, onlyGal, doHierarchy, useManyOrder,
					reader, doneProps, useLouvain, timeout, wasKilled, startTime, runners, this);

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

		if (genDeadPlaces || genDeadTransitions) {
			reader.createSPN(false,false);
			SparsePetriNet spn = reader.getSPN();

			if (genDeadPlaces) {
				for (int pid=0, pide=spn.getPlaceCount(); pid < pide; ++pid) {
					spn.getProperties().add(new fr.lip6.move.gal.structural.Property(Expression.op(Op.EF, Expression.op(Op.LEQ, Expression.constant(1), Expression.var(pid)), null),PropertyType.INVARIANT,"p"+pid));
				}
				examination = "ReachabilityCardinality";
			} else if (genDeadTransitions) {
				for (int tid=0, tide=spn.getTransitionCount(); tid < tide; ++tid) {
					spn.getProperties().add(new fr.lip6.move.gal.structural.Property(Expression.op(Op.EF, Expression.nop(Op.ENABLED, Expression.trans(tid)), null),PropertyType.INVARIANT,"t"+tid));				
				}
				examination = "ReachabilityFireability";
			}
			
			String outform = pwd + "/" + examination + ".sr.xml";
			boolean usesConstants = PropertiesToPNML.transform(spn, outform, doneProps);
			if (usesConstants) {
				// we exported constants to a place with index = current place count
				// to be consistent now add a trivially constant place with initial marking 1
				// token
				System.out.println("Added a place called one to the net.");
				spn.addPlace("one", 1);
			}
			String outsr = pwd + "/model.sr.pnml";
			StructuralToPNML.transform(spn, outsr);
			return null;			
		}
		
		// Now translate and load properties into GAL
		// uses a SAX parser to load to Logic MM, then an M2M to GAL properties.
		reader.loadProperties();
		
		
		if (unfold) {
			SparsePetriNet spn;
			if (nosimplifications) {
				if (reader.getHLPN() != null) {
					SparseHLPetriNet hlpn = reader.getHLPN();
					hlpn.simplifyLogic();
					if (skeleton) {
						spn = hlpn.skeleton();
					} else {
						spn = hlpn.unfold();
					}
				} else {
					spn = reader.getSPN();
				}
			} else {
				reader.createSPN();
				// includes syntactic simplifications
				spn = reader.getSPN();
			}
			if (! applySR) {
				String outform = pwd + "/" + examination + ".sr.xml";
				boolean usesConstants = PropertiesToPNML.transform(spn, outform, doneProps);
				if (usesConstants) {
					// we exported constants to a place with index = current place count
					// to be consistent now add a trivially constant place with initial marking 1
					// token
					System.out.println("Added a place called one to the net.");
					spn.addPlace("one", 1);
				}
				String outsr = pwd + "/model.sr.pnml";
				StructuralToPNML.transform(spn, outsr);
			} else {
				
				for (int propid = 0; propid < spn.getProperties().size() ; propid++) {
					MccTranslator copy = reader.copy();
					fr.lip6.move.gal.structural.Property prop = copy.getSPN().getProperties().get(propid);
					copy.getSPN().getProperties().clear();
					copy.getSPN().getProperties().add(prop);
					
					StructuralReduction sr = new StructuralReduction(copy.getSPN());
					sr.setProtected(copy.getSPN().computeSupport());
					if (examination.startsWith("CTL") || examination.startsWith("LTL")) {
						if (fr.lip6.move.gal.structural.expr.Simplifier.isSyntacticallyStuttering(prop)) {
							ReachabilitySolver.applyReductions(sr, ReductionType.SI_LTL, solverPath, true, true);
						} else {
							ReachabilitySolver.applyReductions(sr, ReductionType.LTL, solverPath, true, true);
						}
					} else {
						ReachabilitySolver.applyReductions(sr, ReductionType.SAFETY, solverPath, true, true);
					}
					copy.getSPN().readFrom(sr);
					
					String outform = pwd + "/" + examination + "." + propid + ".sr.xml";
					boolean usesConstants = PropertiesToPNML.transform(copy.getSPN(), outform, doneProps);
					if (usesConstants) {
						// we exported constants to a place with index = current place count
						// to be consistent now add a trivially constant place with initial marking 1
						// token
						System.out.println("Added a place called one to the net.");
						spn.addPlace("one", 1);
					}
					String outsr = pwd + "/model."+ propid +".sr.pnml";
					StructuralToPNML.transform(copy.getSPN(), outsr);					
				}
				
				
			}
			return IApplication.EXIT_OK;
		}
		
		// are we going for CTL ? only ITSRunner answers this.
		if (examination.startsWith("CTL") || examination.equals("UpperBounds")) {

			if (examination.startsWith("CTL")) {
				LTLPropertySolver logicSolver = new LTLPropertySolver(spotPath, solverPath, pwd, false);
				int solved = logicSolver.preSolveForLogic(reader, doneProps, false);
				if (solved > 0) {
					if (reader.getSPN().getProperties().isEmpty()) {
						return null;
					}
				}
				
				
				for (fr.lip6.move.gal.structural.Property prop : reader.getSPN().getProperties()) {
					// try some property specific reductions
					if (fr.lip6.move.gal.structural.expr.Simplifier.isSyntacticallyStuttering(prop)) {
						MccTranslator reader2 = reader.copy();
						SparsePetriNet spnProp = reader2.getSPN();
						spnProp.getProperties().clear();
						spnProp.getProperties().add(prop.copy());
						StructuralReduction sr = new StructuralReduction (spnProp);
						sr.reduce(ReductionType.SI_LTL);
						spnProp.readFrom(sr);
						spnProp.simplifyLogic();
						ReachabilitySolver.checkInInitial(spnProp, doneProps);
						if (spnProp.getProperties().isEmpty()) {
							continue;
						}
						GALSolver.runGALReductions(reader2, doneProps);
						GALSolver.checkInInitial(reader2.getSpec(), doneProps, reader2.getSPN().isSafe());
						if (reader2.getSpec().getProperties().isEmpty()) {
							continue;
						}
						fr.lip6.move.gal.structural.Property propRed = spnProp.getProperties().get(0);
						if (fr.lip6.move.gal.structural.expr.Simplifier.isAnInvariant(propRed)) {
							// requalify
							propRed.setType(PropertyType.INVARIANT);
							// solve with reachability
							ReachabilitySolver.applyReductions(reader2, doneProps, solverPath);														
							
							if (reader2.getSPN().getProperties().isEmpty()) {
								continue;
							}
							propRed.setType(PropertyType.CTL);
						}
						GlobalPropertySolver.verifyWithSDD(reader2, doneProps, examination, solverPath, 30);
					}										
				}
				reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
				reader.rebuildSpecification(doneProps);
				GALSolver.checkInInitial(reader.getSpec(), doneProps, reader.getSPN().isSafe());
				reader.flattenSpec(false);
				GALSolver.checkInInitial(reader.getSpec(), doneProps, reader.getSPN().isSafe());
				reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
				
				
				if (reader.getSPN().getProperties().isEmpty()) {
					return null;
				}
				// due to + being OR in the CTL syntax, we don't support this type of props
				// TODO: make CTL syntax match the normal predicate syntax in ITS tools
				// reader.removeAdditionProperties();
			}
			if (examination.equals("UpperBounds")) {
				List<Integer> skelBounds = null;
				if (reader.getHLPN() != null) {
					ReachabilitySolver.checkInInitial(reader.getHLPN(), doneProps);
					skelBounds = UpperBoundsSolver.treatSkeleton(reader, doneProps, solverPath);

					reader.getHLPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
				}

				reader.createSPN();
				reader.getSPN().simplifyLogic();
				ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
				reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));

				UpperBoundsSolver.checkInInitial(reader.getSPN(), doneProps);

				UpperBoundsSolver.applyReductions(reader.getSPN(), doneProps, solverPath, skelBounds);

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
						UpperBoundsSolver.checkInInitial(r2.getSPN(), doneProps);
						UpperBoundsSolver.applyReductions(r2.getSPN(), doneProps, solverPath, null);
						System.out.println("Ending property specific reduction for " + p.getName() + " in "
								+ (System.currentTimeMillis() - time) + " ms.");
					}
					reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
				}
				reader.rebuildSpecification(doneProps);

			}

			tryRebuildPNML(pwd, examination, rebuildPNML, reader, doneProps);

			reader = MultiOrderRunner.runMultiITS(pwd, examination, gspnpath, orderHeur, doITS, onlyGal, doHierarchy, useManyOrder,
					reader, doneProps, useLouvain, timeout, wasKilled, startTime, runners, this);
			return 0;
		}

		System.out.println("Working with output stream " + System.out.getClass());
		// LTL, Deadlocks are ok for LTSmin and ITS
		if (examination.startsWith("LTL") || examination.equals("ReachabilityDeadlock")
				|| examination.equals("GlobalProperties")) {

			if (examination.startsWith("LTL")) {
				
				if (spotPath == null) {
					System.out.println("Warning : spot flags not provided. Please use flag : -spotpath $BINDIR/ltlfilt ");
				} else {
					LTLPropertySolver ltlsolve = new LTLPropertySolver(spotPath, solverPath, pwd, exportLTL);
					MccTranslator reader2 = reader.copy();
					MccTranslator reader3 = reader; //final
					ExecutorService pool = Executors.newCachedThreadPool();
					CompletionService<Integer> completionService = 
						       new ExecutorCompletionService<Integer>(pool);

					
					// System.out.println("Before : "+ SerializationUtil.getText(be, true));
					completionService.submit(()->ltlsolve.runStructuralLTLCheck(reader3, doneProps));
					completionService.submit(()->ltlsolve.runSLCLLTLTest(reader2, doneProps));
					
					try {
						Future<Integer> res = completionService.take();
						Integer done = res.get();
						res =  completionService.take();
						done += res.get();
					} catch (Exception e) {
						Logger.getLogger("fr.lip6.move.gal").warning("Invariant computation timed out after "+timeout+" seconds.");
					}
												
				}

				if (reader.getSPN().getProperties().isEmpty()) {
					System.out.println("All properties solved by simple procedures.");
					return null;
				}

				if (exportLTL) {
					SpotRunner.exportLTLProperties(reader.getSPN(), "fin", pwd);
					return null;
				}

			} else if (examination.equals("ReachabilityDeadlock") || examination.equals("GlobalProperties")) {
				if (! DeadlockSolver.checkStructuralDeadlock(pwd, examination, blisspath, solverPath, reader, doneProps).isEmpty()) {
					return null;
				}
			}
			if (doneProps.keySet().containsAll(
					reader.getSPN().getProperties().stream().map(p -> p.getName()).collect(Collectors.toList()))) {
				System.out.println("All properties solved without resorting to exhaustive model-checking.");
				return null;
			} else
				tryRebuildPNML(pwd, examination, rebuildPNML, reader, doneProps);
			if (onlyGal || doLTSmin) {
				// || examination.startsWith("CTL")
				if (!reader.getSpec().getProperties().isEmpty()) {
					System.out.println("Using solver " + solver + " to compute partial order matrices.");
					LTSminRunner ltsRunner = new LTSminRunner(solverPath, solver, doPOR, onlyGal, timeout / reader.getSpec().getProperties().size(),
							reader.getSPN().isSafe());
					ltsRunner.configure(EcoreUtil.copy(reader.getSpec()), doneProps);
					ltsRunner.setNet(reader.getSPN());
					runners.add(ltsRunner);
					ltsRunner.solve(this);
				}
			}
			if (spotmcPath != null) {
				SpotLTLRunner spotRun = new SpotLTLRunner(solverPath, solver, reader.getFolder(), timeout, reader.getSPN().isSafe(),
						spotmcPath);
				spotRun.configure(EcoreUtil.copy(reader.getSpec()), doneProps);
				spotRun.setNet(reader.getSPN());
				runners.add(spotRun);
				spotRun.solve(this);
			}

			if (doITS || onlyGal) {
				reader = MultiOrderRunner.runMultiITS(pwd, examination, gspnpath, orderHeur, doITS, onlyGal, doHierarchy, useManyOrder,
						reader, doneProps, useLouvain, timeout, wasKilled, startTime, runners, this);
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
				if (reader.getHLPN() != null) {
					ReachabilitySolver.checkInInitial(reader.getHLPN(), doneProps);

					SparsePetriNet skel = reader.getHLPN().skeleton();
					skel.getProperties().removeIf(p -> ! fr.lip6.move.gal.structural.expr.Simplifier.allEnablingsAreNegated(p.getBody()));
					
					reader.setSpn(skel,true);
					ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
					new AtomicReducerSR().strongReductions(solverPath, reader, doneProps);
					reader.getSPN().simplifyLogic();
					ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
					reader.rebuildSpecification(doneProps);
					GALSolver.checkInInitial(reader.getSpec(), doneProps, reader.getSPN().isSafe());
					reader.flattenSpec(false);
					GALSolver.checkInInitial(reader.getSpec(), doneProps, reader.getSPN().isSafe());
				}
				reader.createSPN();
				ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
				if (!reader.getSPN().getProperties().isEmpty())
					ReachabilitySolver.applyReductions(reader, doneProps, solverPath);

			} else {

				reader.flattenSpec(false);
				// get rid of trivial properties in spec
				GALSolver.checkInInitial(reader.getSpec(), doneProps, reader.getSPN().isSafe());

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
								 repr, 100, true);
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
					ReachabilitySolver.applyReductions(reader, doneProps, solverPath);

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
				regeneratePNML(reader, doneProps, solverPath);

			if (doneProps.keySet().containsAll(
					reader.getSPN().getProperties().stream().map(p -> p.getName()).collect(Collectors.toList()))) {
				System.out.println("All properties solved without resorting to model-checking.");
				return null;
			} else
				tryRebuildPNML(pwd, examination, rebuildPNML, reader, doneProps);

			if (false) {
				IntMatrixCol sumP = IntMatrixCol.sumProd(-1, reader.getSPN().getFlowPT(), 1,
						reader.getSPN().getFlowTP());
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
				IRunner z3Runner = new SMTRunner(pwd, solverPath, solver, timeout, reader.getSPN().isSafe());
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
					LTSminRunner ltsminRunner = new LTSminRunner(solverPath, solver, doPOR, onlyGal, timeout / reader.getSpec().getProperties().size(),
							reader.getSPN().isSafe());
					ltsminRunner.configure(EcoreUtil.copy(reader.getSpec()), doneProps);
					ltsminRunner.setNet(reader.getSPN());
					runners.add(ltsminRunner);
					ltsminRunner.solve(this);
				}
			}

			reader = MultiOrderRunner.runMultiITS(pwd, examination, gspnpath, orderHeur, doITS, onlyGal, doHierarchy, useManyOrder,
					reader, doneProps, useLouvain, timeout, wasKilled, startTime, runners, this);

		}

		for (IRunner r : runners) {
			if (r != null) {
				r.join();
			}
		}
		return IApplication.EXIT_OK;
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

	private void regeneratePNML(MccTranslator reader, DoneProperties doneProps, String solverPath) {
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
					ReachabilitySolver.applyReductions(copy, doneProps, solverPath);
					System.out.println("For property " + prop.getName() + " final size "
							+ ((GALTypeDeclaration) copy.getSpec().getTypes().get(0)).getVariables().size());
				}
			} catch (GlobalPropertySolvedException e) {
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
