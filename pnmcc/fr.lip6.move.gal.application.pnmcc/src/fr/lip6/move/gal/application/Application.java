package fr.lip6.move.gal.application;

import static fr.lip6.move.gal.structural.smt.SMTUtils.computeReducedFlow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
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
import fr.lip6.move.gal.application.solver.ltl.LTLLengthAwareSolver;
import fr.lip6.move.gal.application.solver.ltl.LTLLengthSensitivityAnalyzer;
import fr.lip6.move.gal.application.solver.ltl.LTLPropertySolver;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.instantiate.Simplifier;
import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.logic.saxparse.PropertyParser;
import fr.lip6.move.gal.logic.togal.ToGalTransformer;
import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.mcc.properties.MCCExporter;
import fr.lip6.move.gal.pnml.togal.OverlargeMarkingException;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.struct2gal.ExpressionBuilder;
import fr.lip6.move.gal.struct2gal.SpecBuilder;
import fr.lip6.move.gal.struct2gal.StructuralReductionBuilder;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.StructuralToPNML;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.hlpn.SparseHLPetriNet;
import fr.lip6.move.gal.structural.smt.DeadlockTester;
import fr.lip6.move.gal.util.IntMatrixCol;
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
	private static final String REDUCE="--reduce";
	private static final String REDUCESINGLE="--reduce-single";
	private static final String PFLOW="--Pflows";
	private static final String PSEMIFLOW="--Psemiflows";
	private static final String TFLOW="--Tflows";
	private static final String TSEMIFLOW="--Tsemiflows";

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
		long time = System.currentTimeMillis();
		try {
			return startNoEx(context);
		} catch (Exception e) {
			System.err.println("Application raised an uncaught exception " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			System.out.println("Total runtime "+(System.currentTimeMillis()-time)+ " ms.");
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
		boolean pflows = true;
		boolean psemiflows = false;
		boolean tflows = false;
		boolean tsemiflows = true;

		boolean genDeadTransitions = false;
		boolean genDeadPlaces = false;

		boolean noSLCLtest = false;

		boolean analyzeSensitivity = false;
		int timeout = 3600;
		boolean singleReduction = false;
		ReductionType redForExport = null;

		for (int i = 0; i < args.length; i++) {
			if (PNFOLDER.equals(args[i])) {
				pwd = args[++i];
			} else if (EXAMINATION.equals(args[i])) {
				examination = args[++i];
			} else if (Z3PATH.equals(args[i])) {
				z3path = args[++i];
			} else if (YICES2PATH.equals(args[i])) {
				yices2path = args[++i];
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
				timeout = Integer.parseInt(args[++i]);
			} else if (REDUCE.equals(args[i]) || REDUCESINGLE.equals(args[i])) {
				if (REDUCESINGLE.equals(args[i]))
					singleReduction = true;
				String mode = args[++i];
				try {
					redForExport = ReductionType.valueOf(mode);
				} catch (IllegalArgumentException iae) {
					System.err.println("Sorry this reduction mode '"+ mode +"' is not recognized. Please use one of " + Arrays.toString(ReductionType.values()));
					return IApplication.EXIT_OK;
				}
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
			} else if (PFLOW.equals(args[i])) {
				invariants = true;
				pflows=true;
			} else if (PSEMIFLOW.equals(args[i])) {
				psemiflows = true;
				invariants = true;
			} else if (TFLOW.equals(args[i])) {
				tflows = true;
				invariants = true;
			} else if (TSEMIFLOW.equals(args[i])) {
				invariants = true;
				tsemiflows = true;
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
				noSLCLtest = true;
			} else if (NOKNOWLEDGE.equals(args[i])) {
				LTLPropertySolver.noKnowledgetest = true;
			} else if (NOSTUTTERLTL.equals(args[i])) {
				LTLPropertySolver.noStutterTest = true;
			} else {
				System.err.println("Unrecognized argument :"+args[i]+ " in position "+ i +". Skipping this argument.");
			}
		}

		// use Z3 in preference to Yices if both are available
		Solver solver = Solver.Z3;
		
		// EMF registration
		SerializationUtil.setStandalone(true);

		// setup a "reader" that parses input property files correctly and efficiently
		MccTranslator reader = new MccTranslator(pwd, useLouvain);
		reader.setITS(doITS);
		reader.setLTSMin(doLTSmin);
		
		try {
			if (readGAL == null) {
				// parse the model from PNML to GAL using PNMLFW for COL or fast SAX for PT
				// models
				try {
					reader.transformPNML();
				} catch (OverlargeMarkingException e) {
					if ("OneSafe".equals(examination)) {
						System.out.println("FORMULA OneSafe FALSE TECHNIQUES STRUCTURAL INITIAL_STATE");
						allSolved(pwd,examination);
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
			SpotRunner spot = new SpotRunner(10);
			LTLLengthSensitivityAnalyzer.doSensitivityAnalysis(reader,spot,pwd,examination,"");
			return IApplication.EXIT_OK;
		}

		if (invariants) {
			reader.createSPN();
			List<Integer> tnames = new ArrayList<>();
			List<Integer> repr = new ArrayList<>();
			IntMatrixCol sumMatrix = computeReducedFlow(reader.getSPN(), tnames, repr);
			SparsePetriNet spn = reader.getSPN();
			if (pflows || psemiflows) {
				long time = System.currentTimeMillis();
				Set<SparseIntArray> invar;
				if (pflows) {
					invar = InvariantCalculator.computePInvariants(sumMatrix, reader.getSPN().getPnames());
				} else {
					invar = InvariantCalculator.computePInvariants(sumMatrix, reader.getSPN().getPnames(), true, 120);
				}
				System.out.println("Computed "+invar.size()+" P "+(psemiflows?"semi":"")+" flows in "+(System.currentTimeMillis()-time)+" ms.");
				InvariantCalculator.printInvariant(invar, spn.getPnames(), reader.getSPN().getMarks());
			} 
			
			if (tflows || tsemiflows) {
				long time = System.currentTimeMillis();
				Set<SparseIntArray> invarT;
				if (tflows) {
					invarT= DeadlockTester.computeTinvariants(reader.getSPN(), sumMatrix, tnames,false);
				} else {
					invarT= DeadlockTester.computeTinvariants(reader.getSPN(), sumMatrix, tnames,true);					
				}
				List<Integer> empty = new ArrayList<>(tnames.size());
				for (int i=0 ; i < tnames.size(); i++) empty.add(0);
				List<String> strtnames = repr.stream().map(id -> spn.getTnames().get(id)).collect(Collectors.toList());
				System.out.println("Computed "+invarT.size()+" T "+(psemiflows?"semi":"")+" flows in "+(System.currentTimeMillis()-time)+" ms.");
				InvariantCalculator.printInvariant(invarT, strtnames, empty );				
			}
			return null;
		}

		// for debug and control COL files are small, otherwise 1MB PNML limit (i.e.
		// roughly 200kB GAL max).
		if (pwd.contains("COL") || new File(pwd + "/model.pnml").length() < 1000000) {
			String outpath = pwd + "/model.pnml.img.gal";
			// SerializationUtil.systemToFile(reader.getSpec(), outpath);
		}

		if (redForExport == null)
			if ("StableMarking".equals(examination) || "OneSafe".equals(examination)
					|| "QuasiLiveness".equals(examination) || "Liveness".equals(examination)) {
				GlobalPropertySolver gps = new GlobalPropertySolver();
				Optional<Boolean> b = gps.solveProperty(examination, reader);

				if (b.isPresent() ) {
					allSolved(pwd,examination);
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

		if (redForExport == null)
			if ("StateSpace".equals(examination)) {
				reader.createSPN(false, false);
				
				if (rebuildPNML) {
					tryRebuildPNML(pwd, examination, rebuildPNML, reader, doneProps);
					return 0;
				}
				
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
					ReachabilitySolver.applyReductions(sr,ReductionType.STATESPACE,true,true);

					// Breaks max token per marking metric.
					int curtok = spn.getMarks().stream().mapToInt(i->i).sum();
					int newtok = sr.getMarks().stream().mapToInt(i->i).sum();
					spn.readFrom(sr);
					reader.setMissingTokens( (curtok-newtok) + reader.countMissingTokens());
					
					totaltok = reader.getSPN().removeConstantPlaces();
					if (totaltok > 0) {
						reader.setMissingTokens(totaltok+reader.countMissingTokens());
					}
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
			String outsr = pwd + "/model.sr.pnml";
			MCCExporter.exportToMCCFormat(outsr, outform, spn);
			return null;
		}

		// Now load properties from examination
		reader.loadProperties(examination);

//		if (redForExamination != null) {
//			if ("StateSpace".equals(examination) || "Liveness".equals(examination) || "QuasiLiveness".equals(examination) || "StableMarking".equals(examination))
//				reader.createSPN(false, false);
//			else
//				reader.createSPN();					
//			
//			// basic : test initial conditions
//			SparsePetriNet spn = reader.getSPN();
//			
//			ReachabilitySolver.checkInInitial(spn, doneProps);
//			
//			// apply some further simplifications
//			GALSolver.runGALReductions(reader, doneProps);
//			
//			// apply the appropriate reductions
//			StructuralReduction sr = new StructuralReduction(spn);
//			if (examination.startsWith("CTL") || examination.startsWith("LTL")) {
////				if (fr.lip6.move.gal.structural.expr.Simplifier.isSyntacticallyStuttering(prop)) {
////					ReachabilitySolver.applyReductions(sr, ReductionType.SI_LTL, solverPath, true, true);
////				} else {
//					ReachabilitySolver.applyReductions(sr, ReductionType.LTL, solverPath, true, true);
////				}
//			} else if (examination.contains("Liveness")) {
//				ReachabilitySolver.applyReductions(sr, ReductionType.REACHABILITY, solverPath, true, true);
//			}
//
//			
//			// attempt to disprove with SMT
//			
//			// evaluate and simplify
//			
//			// last round of reductions, including some SMT
//			
//			// walk a bit more
//						
//			// export remaining properties
//			
//			return null;
//		}
		
		if (redForExport != null) {
			// reduce the model for each property
			reader.createSPN(false, false);
			SparsePetriNet spn = reader.getSPN();
			if (spn.getProperties().isEmpty() || singleReduction) {
				// only export one model
				StructuralReduction sr = new StructuralReduction(spn);
				sr.reduce(redForExport);
				spn.readFrom(sr);
				String outsr = pwd + "/model."+redForExport+".pnml";
				if (spn.getProperties().isEmpty()) {
					StructuralToPNML.transform(spn, outsr);	
				} else {
					String outform = pwd + "/" + examination + "." + redForExport+".xml";				
					MCCExporter.exportToMCCFormat(outsr, outform, spn);
				}
			} else {
				// export one model per property, reduced specifically for that properties alphabet.
				List<fr.lip6.move.gal.structural.Property> props = new ArrayList<>(spn.getProperties());
				spn.getProperties().clear();

				for (int index = 0; index < props.size() ; index++) {
					SparsePetriNet spncopy = new SparsePetriNet(spn);
					spncopy.getProperties().add(props.get(index));

					StructuralReduction sr = new StructuralReduction(spncopy);
					sr.reduce(redForExport);
					spncopy.readFrom(sr);

					String outform = pwd + "/" + examination + "." + index + "."+redForExport+".xml";
					String outsr = pwd + "/model."+index+ "."+redForExport+".pnml";

					// output the model
					MCCExporter.exportToMCCFormat(outsr, outform, spncopy);
				}
			}
			return null;
		}

		if (unfold) {
			SparsePetriNet spn;
			if (nosimplifications) {
				if (reader.getHLPN() != null) {
					SparseHLPetriNet hlpn = reader.getHLPN();
					hlpn.simplifyLogic();
					if (skeleton) {
						spn = hlpn.skeleton();
					} else if (hlpn.getProperties().stream().anyMatch(p->p.getType()==PropertyType.BOUNDS) || hlpn.getProperties().isEmpty()){
						spn = hlpn.unfold(ReductionType.STATESPACE);
					} else {
						spn = hlpn.unfold(ReductionType.REACHABILITY);
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
				String outsr = pwd + "/model.sr.pnml";
				MCCExporter.exportToMCCFormat(outsr, outform, spn);
			} else {

				// going for full blown SMT supported reductions.
				for (int propid = 0; propid < spn.getProperties().size() ; propid++) {
					SparsePetriNet copy = new SparsePetriNet(reader.getSPN());
					fr.lip6.move.gal.structural.Property prop = copy.getProperties().get(propid);
					copy.getProperties().clear();
					copy.getProperties().add(prop);

					StructuralReduction sr = new StructuralReduction(copy);
					sr.setProtected(copy.computeSupport());
					if (examination.startsWith("CTL") || examination.startsWith("LTL")) {
						if (fr.lip6.move.gal.structural.expr.Simplifier.isSyntacticallyStuttering(prop)) {
							ReachabilitySolver.applyReductions(sr, ReductionType.SI_LTL, true, true);
						} else {
							ReachabilitySolver.applyReductions(sr, ReductionType.LTL, true, true);
						}
					} else {
						ReachabilitySolver.applyReductions(sr, ReductionType.REACHABILITY, true, true);
					}
					copy.readFrom(sr);

					String outform = pwd + "/" + examination + "." + propid + ".sr.xml";
					String outsr = pwd + "/model."+ propid +".sr.pnml";
					MCCExporter.exportToMCCFormat(outsr, outform, copy);
				}
			}
			return IApplication.EXIT_OK;
		}

		// are we going for CTL ? only ITSRunner answers this.
		if ( (examination!=null && examination.startsWith("CTL")) || "UpperBounds".equals(examination)) {

			if (examination.startsWith("CTL")) {
				LTLPropertySolver logicSolver = new LTLPropertySolver(pwd, false);
				int solved = logicSolver.preSolveForLogic(reader, doneProps, false);
				if (solved > 0) {
					if (reader.getSPN().getProperties().isEmpty()) {
						return null;
					}
				}


				for (fr.lip6.move.gal.structural.Property prop : reader.getSPN().getProperties()) {
					// try some property specific reductions

					reader.setSpec(null);
					MccTranslator reader2 = reader.copy();
					SparsePetriNet spnProp = reader2.getSPN();

					spnProp.getProperties().clear();
					spnProp.getProperties().add(prop.copy());
					StructuralReduction sr = new StructuralReduction(spnProp);

					ReductionType rt = ReductionType.LTL;
					if (fr.lip6.move.gal.structural.expr.Simplifier.isSyntacticallyStuttering(prop)) {
						rt = ReductionType.SI_CTL;
					}
					ReachabilitySolver.applyReductions(sr, rt, doSMT, true);
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
					spnProp = reader2.getSPN();

					fr.lip6.move.gal.structural.Property propRed = spnProp.getProperties().get(0);
					if (fr.lip6.move.gal.structural.expr.Simplifier.isAnInvariant(propRed)) {
						// requalify
						propRed.setType(PropertyType.INVARIANT);
						// solve with reachability
						ReachabilitySolver.applyReductions(reader2, doneProps, -1);

						if (reader2.getSPN().getProperties().isEmpty()) {
							continue;
						}
						propRed.setType(PropertyType.CTL);
					}
//					if (fr.lip6.move.gal.structural.expr.Simplifier.isACTLstar(propRed)) {
//
//					}

					GlobalPropertySolver.verifyWithSDD(reader2, doneProps, examination, 30);
				}
				reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
				reader.rebuildSpecification(doneProps);
				GALSolver.checkInInitial(reader.getSpec(), doneProps, reader.getSPN().isSafe());
				reader.flattenSpec(false);
				GALSolver.checkInInitial(reader.getSpec(), doneProps, reader.getSPN().isSafe());
				reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));


				if (reader.getSPN().getProperties().isEmpty()) {
					allSolved(pwd, examination);
					return null;
				}
				// due to + being OR in the CTL syntax, we don't support this type of props
				// TODO: make CTL syntax match the normal predicate syntax in ITS tools
				// reader.removeAdditionProperties();
			}
			if ("UpperBounds".equals(examination)) {
				List<Integer> skelBounds = null;
				if (reader.getHLPN() != null) {
					ReachabilitySolver.checkInInitial(reader.getHLPN(), doneProps);
					skelBounds = UpperBoundsSolver.treatSkeleton(reader, doneProps);

					reader.getHLPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
				}

				reader.createSPN();
				reader.getSPN().simplifyLogic();
				ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
				reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));

				UpperBoundsSolver.checkInInitial(reader.getSPN(), doneProps);

				UpperBoundsSolver.applyReductions(reader.getSPN(), doneProps, skelBounds);

				reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
				// checkInInitial(reader.getSpec(), doneProps, isSafe);

				if (doneProps.keySet().containsAll(
						reader.getSPN().getProperties().stream().map(p -> p.getName()).collect(Collectors.toList()))) {
					allSolved(pwd,examination);
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
						UpperBoundsSolver.applyReductions(r2.getSPN(), doneProps, null);
						System.out.println("Ending property specific reduction for " + p.getName() + " in "
								+ (System.currentTimeMillis() - time) + " ms.");
					}
					reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
				}
				reader.rebuildSpecification(doneProps);

			}
			
			if (reader.getSPN().getProperties().isEmpty()) {
				allSolved(pwd, examination);				
			} else {
				tryRebuildPNML(pwd, examination, rebuildPNML, reader, doneProps);
				reader = MultiOrderRunner.runMultiITS(pwd, examination, gspnpath, orderHeur, doITS, onlyGal, doHierarchy, useManyOrder,
					reader, doneProps, useLouvain, timeout, wasKilled, startTime, runners, this);
			}
			return 0;
		}

		System.out.println("Working with output stream " + System.out.getClass());
		// LTL, Deadlocks are ok for LTSmin and ITS
		if ((examination!=null && examination.startsWith("LTL")) || "ReachabilityDeadlock".equals(examination)
				|| "GlobalProperties".equals(examination)) {

			if (examination.startsWith("LTL")) {

				LTLPropertySolver ltlsolve = new LTLPropertySolver(pwd, exportLTL);
				
				ltlsolve.runStructuralLTLCheck(reader, doneProps);
				if (! noSLCLtest) {
					new LTLLengthAwareSolver(ltlsolve).runSLCLLTLTest(reader, doneProps);
				}

				if (! reader.getSPN().getProperties().isEmpty()) {
					reader.rebuildSpecification(doneProps);
				}

				if (reader.getSPN().getProperties().isEmpty()) {
					System.out.println("All properties solved by simple procedures.");
					allSolved(pwd, examination);
					return null;
				}

				if (exportLTL) {
					SpotRunner.exportLTLProperties(reader.getSPN(), "fin", pwd);
					return null;
				}

			} else if ("ReachabilityDeadlock".equals(examination) || "GlobalProperties".equals(examination)) {
				if (! DeadlockSolver.checkStructuralDeadlock(pwd, examination, blisspath, reader, doneProps).isEmpty()) {
					allSolved(pwd, examination);
					return null;
				}
			}
			if (doneProps.keySet().containsAll(
					reader.getSPN().getProperties().stream().map(p -> p.getName()).collect(Collectors.toList()))) {
				allSolved(pwd, examination);
				System.out.println("All properties solved without resorting to exhaustive model-checking.");
				return null;
			} else
				tryRebuildPNML(pwd, examination, rebuildPNML, reader, doneProps);
			if (onlyGal || doLTSmin) {
				// || examination.startsWith("CTL")
				if (!reader.getSpec().getProperties().isEmpty()) {
					System.out.println("Using solver " + solver + " to compute partial order matrices.");
					LTSminRunner ltsRunner = new LTSminRunner(solver, doPOR, onlyGal, timeout / reader.getSpec().getProperties().size(), reader.getSPN().isSafe());
					ltsRunner.configure(EcoreUtil.copy(reader.getSpec()), doneProps);
					ltsRunner.setNet(reader.getSPN());
					runners.add(ltsRunner);
					ltsRunner.solve(this);
				}
			}
			if (spotmcPath != null) {
				SpotLTLRunner spotRun = new SpotLTLRunner(solver, reader.getFolder(), timeout, reader.getSPN().isSafe(), spotmcPath);
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
		if ("ReachabilityFireability".equals(examination) || "ReachabilityCardinality".equals(examination)) {

			if (true) {
				if (reader.getHLPN() != null) {
					ReachabilitySolver.checkInInitial(reader.getHLPN(), doneProps);

					SparsePetriNet skel = reader.getHLPN().skeleton();
					skel.getProperties().removeIf(p -> ! fr.lip6.move.gal.structural.expr.Simplifier.allEnablingsAreNegated(p, skel));

					if (! skel.getProperties().isEmpty() ) {
						System.out.println("Remains "+skel.getProperties().size()+ " properties that can be checked using skeleton over-approximation.");
						if (skel.testInInitial()>0) {
							ReachabilitySolver.checkInInitial(skel, doneProps);
						}
						// just in case there are enablings still around; next procedure does not support them.
						skel.toPredicates();
						skel.removeConstantPlaces();
						try {
							if (skel.getFlowPT().getColumns().stream().allMatch(c -> c.size() > 0)) {
								StructuralReduction.findSCCSuffixes(skel, ReductionType.DEADLOCK, new BitSet());
							}
						} catch (DeadlockFound e) {
							// AF dead is true
							if (skel.testInDeadlock()>0) {
								ReachabilitySolver.checkInInitial(skel, doneProps);
							}
						}




						DoneProperties skelProps = new ConcurrentHashDoneProperties();
						reader.setSpn(skel,true);
						ReachabilitySolver.checkInInitial(reader.getSPN(), skelProps);
						List<fr.lip6.move.gal.structural.Property> todel = new ArrayList<>();
						for (fr.lip6.move.gal.structural.Property p : reader.getHLPN().getProperties()) {
							Boolean b = doneProps.getValue(p.getName());
							if (b!=null) {
								if ((p.getBody().getOp()==Op.AG && b)||(p.getBody().getOp()==Op.EF && !b)) {
									doneProps.put(p.getName(), b, "CPN_APPROX");
								} else {
									todel.add(p);
								}
							}
						}
						reader.getHLPN().getProperties().removeAll(todel);

						if (! skel.getProperties().isEmpty()) {
							new AtomicReducerSR().strongReductions(reader.getSPN(), skelProps, null, true);
							reader.getSPN().simplifyLogic();
							ReachabilitySolver.checkInInitial(reader.getSPN(), skelProps);
							reader.rebuildSpecification(skelProps);
							GALSolver.checkInInitial(reader.getSpec(), skelProps, reader.getSPN().isSafe());
							reader.flattenSpec(false);
							GALSolver.checkInInitial(reader.getSpec(), skelProps, reader.getSPN().isSafe());

							for (fr.lip6.move.gal.structural.Property p : reader.getHLPN().getProperties()) {
								Boolean b = skelProps.getValue(p.getName());
								if (b!=null) {
									if ((p.getBody().getOp()==Op.AG && b)||(p.getBody().getOp()==Op.EF && !b)) {
										doneProps.put(p.getName(), b, "CPN_APPROX");
									}
								}
							}
						} else {
							System.out.println("Skeleton over-approximation was not fine enough to positively prove more properties of the HLPN.");
						}
					} else {
						System.out.println("All "+reader.getHLPN().getProperties().size()+ " properties of the HLPN use transition enablings in a way that makes the skeleton too coarse.");

					}
				}
				reader.createSPN();

				ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
				if (!reader.getSPN().getProperties().isEmpty())
					ReachabilitySolver.applyReductions(reader, doneProps, timeout);

//				if (!reader.getSPN().getProperties().isEmpty()) {
//					List<fr.lip6.move.gal.structural.Property> props = new ArrayList<>(reader.getSPN().getProperties());
//					for (fr.lip6.move.gal.structural.Property pp : props) {
//						MccTranslator reader2 = reader.copy();
//						reader2.getSPN().getProperties().removeIf(p->! pp.getName().equals(p.getName()));
//						ReachabilitySolver.applyReductions(reader2, doneProps, solverPath, -1);
//					}
//				}
			} else {

				reader.flattenSpec(false);
				// get rid of trivial properties in spec
				GALSolver.checkInInitial(reader.getSpec(), doneProps, reader.getSPN().isSafe());

				if (specnocol != null) {
					specnocol.getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
					if (pwd.contains("COL") || new File(pwd + "/model.pnml").length() < 1000000) {
						String outpath = pwd + "/model.pnml.unc.gal";
						SerializationUtil.systemToFile(specnocol, outpath, true);
					}
					INextBuilder nb = INextBuilder.build(specnocol);
					IDeterministicNextBuilder idnb = IDeterministicNextBuilder.build(nb);
					StructuralReduction sr = StructuralReductionBuilder.createStructuralReduction(idnb);

					// need to protect some variables
					List<Property> l = reader.getSpec().getProperties();
					List<Expression> tocheck = translateProperties(l, idnb);
						List<Integer> repr = new ArrayList<>();
						List<SparseIntArray> paths = DeadlockTester.testUnreachableWithSMT(tocheck, sr, repr,
								 100, true);
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

				if (!reader.getSpec().getProperties().isEmpty())
					ReachabilitySolver.applyReductions(reader, doneProps, timeout);

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
				regeneratePNML(reader, doneProps);

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
				IRunner z3Runner = new SMTRunner(pwd, solver, timeout, reader.getSPN().isSafe());
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
					LTSminRunner ltsminRunner = new LTSminRunner(solver, doPOR, onlyGal, timeout / reader.getSpec().getProperties().size(), reader.getSPN().isSafe());
					ltsminRunner.configure(null, doneProps);
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

	private void allSolved(String pwd, String examination2) {
		try {
			new File(pwd+"/"+examination2+".solved").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private void tryRebuildPNML(String pwd, String examination, boolean rebuildPNML, MccTranslator reader,
			DoneProperties doneProps) throws IOException {
		if (rebuildPNML) {
			String outform = pwd + "/" + examination + ".sr.xml";
			String outsr = pwd + "/model.sr.pnml";
			MCCExporter.exportToMCCFormat(outsr, outform, reader.getSPN());
		}
	}

	public int rebuildSpecification(MccTranslator reader, StructuralReduction sr) {
		Specification reduced = SpecBuilder.rebuildSpecification(sr);
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

	private void regeneratePNML(MccTranslator reader, DoneProperties doneProps) {
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
					ReachabilitySolver.applyReductions(copy, doneProps, -1);
					System.out.println("For property " + prop.getName() + " final size "
							+ ((GALTypeDeclaration) copy.getSpec().getTypes().get(0)).getVariables().size());
				}
			} catch (GlobalPropertySolvedException e) {
				e.printStackTrace();
			}
		}

	}

	private List<Expression> translateProperties(List<Property> props, IDeterministicNextBuilder idnb) {
		List<Expression> tocheck = new ArrayList<>();
		for (Property prop : props) {
			if (prop.getBody() instanceof NeverProp) {
				NeverProp never = (NeverProp) prop.getBody();
				tocheck.add(ExpressionBuilder.buildExpression(never.getPredicate(), idnb));
			} else if (prop.getBody() instanceof InvariantProp) {
				InvariantProp invar = (InvariantProp) prop.getBody();
				tocheck.add(Expression.not(ExpressionBuilder.buildExpression(invar.getPredicate(), idnb)));
			} else if (prop.getBody() instanceof ReachableProp) {
				ReachableProp reach = (ReachableProp) prop.getBody();
				tocheck.add(ExpressionBuilder.buildExpression(reach.getPredicate(), idnb));
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
