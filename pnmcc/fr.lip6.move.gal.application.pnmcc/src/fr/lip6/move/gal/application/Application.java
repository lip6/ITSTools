package fr.lip6.move.gal.application;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
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
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.CTLProp;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
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
import fr.lip6.move.gal.instantiate.PropertySimplifier;
import fr.lip6.move.gal.instantiate.Simplifier;
import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.logic.saxparse.PropertyParser;
import fr.lip6.move.gal.logic.togal.ToGalTransformer;
import fr.lip6.move.gal.semantics.ExpressionPrinter;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.semantics.NextSupportAnalyzer;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.NoDeadlockExists;
import fr.lip6.move.gal.structural.RandomExplorer;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.StructuralToGreatSPN;
import fr.lip6.move.gal.structural.StructuralToPNML;
import fr.lip6.move.serialization.SerializationUtil;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication, Ender {

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
	              "[%1$tF %1$tT] [%4$-7s] %5$s %n");
		Logger logger = Logger.getLogger("fr.lip6.move.gal");
		logger.setUseParentHandlers(false);
		logger.addHandler(new ConsoleHandler() {
		    {setOutputStream(System.out);}
		});
	}
	
	
	private static final String APPARGS = "application.args";
	
	private static final String PNFOLDER = "-pnfolder";

	private static final String EXAMINATION = "-examination";
	private static final String Z3PATH = "-z3path";
	private static final String YICES2PATH = "-yices2path";
	private static final String SMT = "-smt";
	private static final String ITS = "-its";
	private static final String MANYORDER = "-manyOrder";
	private static final String CEGAR = "-cegar";
	private static final String LTSMINPATH = "-ltsminpath";
	private static final String ONLYGAL = "-onlyGal";
	private static final String disablePOR = "-disablePOR";
	private static final String disableSDD = "-disableSDD";
	private static final String READ_GAL = "-readGAL";
	private static final String USE_LOUVAIN = "-louvain";
	private static final String ORDER_FLAG = "-order";
	private static final String GSPN_PATH = "-greatspnpath";
	private static final String BLISS_PATH = "-blisspath";
	private static final String TIMEOUT = "-timeout";
	
	private IRunner cegarRunner;
	private IRunner z3Runner;
	private IRunner itsRunner;
	private IRunner ltsminRunner;
	
	private static Logger logger = Logger.getLogger("fr.lip6.move.gal"); 
	
	private boolean wasKilled = false;
	
	@Override
	public synchronized void killAll () {
		wasKilled = true;
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
		String blisspath = null;
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
		
		long timeout = 3600;
		
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
			} else if (BLISS_PATH.equals(args[i])) {
				blisspath = args[++i]; 
			} else if (ORDER_FLAG.equals(args[i])) {
				orderHeur = args[++i]; 
			} else if (SMT.equals(args[i])) {
				doSMT = true;
			} else if (LTSMINPATH.equals(args[i])) {
				ltsminpath = args[++i];
				doLTSmin = true;
			} else if (READ_GAL.equals(args[i])) {
				readGAL = args[++i];
			} else if (TIMEOUT.equals(args[i])) {
				timeout = Long.parseLong(args[++i]);
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
		
		boolean isSafe = false;
		// load "known" stuff about the model
		if (reader.isSafeNet()) {
			// NUPN implies one safe
			isSafe = true;
		}
		
		// initialize a shared container to detect help detect termination in portfolio case
		Set<String> doneProps = ConcurrentHashMap.newKeySet();

		// reader now has a spec and maybe a ITS decomposition
		// no properties yet.
		
		// A filename to store the variable ordering, if we compute it with e.g. GreatSPN
		String orderff = null;
		if (orderHeur != null && gspnpath != null) {
			doHierarchy = false;
		}
		
		
		if (examination.equals("StateSpace")) {
			// ITS is the only method we will run.
			reader = runMultiITS(pwd, examination, gspnpath, orderHeur, doITS, onlyGal, doHierarchy, useManyOrder,
					reader, doneProps, useLouvain, timeout);			
			
			return 0;
		}

		
		Specification specnocol = null;
		//		// Abstraction case 
		if (false && pwd.contains("COL") && (examination.equals("ReachabilityFireability") || examination.equals("ReachabilityCardinality"))) {
			ToGalTransformer.setWithAbstractColors(true);
			String pname = pwd + "/"+examination+".xml";
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
				// due to + being OR in the CTL syntax, we don't support this type of props
				// TODO: make CTL syntax match the normal predicate syntax in ITS tools
				//reader.removeAdditionProperties();
			}
			checkInInitial(reader.getSpec(), doneProps, isSafe);
			
			reader = runMultiITS(pwd, examination, gspnpath, orderHeur, doITS, onlyGal, doHierarchy, useManyOrder,
					reader, doneProps, useLouvain, timeout);	
			return 0;
		}
		
		System.out.println("Working with output stream " + System.out.getClass());
		// LTL, Deadlocks are ok for LTSmin and ITS
		if (examination.startsWith("LTL") || examination.equals("ReachabilityDeadlock")|| examination.equals("GlobalProperties")) {
			checkInInitial(reader.getSpec(), doneProps, isSafe);
			
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
					StructuralReduction sr;
					// block for gc of nb/idnb
					{ 
						INextBuilder nb = INextBuilder.build(spec);
						IDeterministicNextBuilder idnb = IDeterministicNextBuilder.build(nb);			
						long tt = System.currentTimeMillis();
						sr = new StructuralReduction(idnb);
						System.out.println("Built sparse matrix representations for Structural reductions in "+ (System.currentTimeMillis()-tt) + " ms." + ( (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) / 1000) + "KB memory used");
					}
					
					if (false && blisspath != null) {
						List<List<List<Integer>>> generators = null;
						BlissRunner br = new BlissRunner(blisspath,pwd,100);
						generators = br.run(sr);
						System.out.println("Obtained generators : " + generators);
						br.computeMatrixForm(generators);
					}
					try {
						if (! applyReductions(sr, reader, ReductionType.DEADLOCKS, solverPath, isSafe,false)) 
							applyReductions(sr, reader, ReductionType.DEADLOCKS, solverPath, isSafe,true);					
					} catch (DeadlockFound d) {
						System.out.println( "FORMULA " + reader.getSpec().getProperties().get(0).getName()  + " TRUE TECHNIQUES TOPOLOGICAL STRUCTURAL_REDUCTION");
						return null;
					}

										
					if (false) {
						FlowPrinter.drawNet(sr,"initial");
						String outsr = pwd + "/model.sr.pnml";
						StructuralToPNML.transform(sr, outsr);
					}
					
			
					if (blisspath != null) {
						List<List<List<Integer>>> generators = null;
						BlissRunner br = new BlissRunner(blisspath,pwd,100);						
						generators = br.run(sr);
						System.out.println("Obtained generators : " + generators);
						List<Set<List<Integer>>> gen = br.computeMatrixForm(generators);
						if (! gen.isEmpty()) {
							StructuralReduction sr2 = sr.clone();
							// attempt fusion
							
							for (Set<List<Integer>> set : gen) {
								if (set.size() >= 2) {
									Iterator<List<Integer>> ite = set.iterator();							
									List<Integer> base = ite.next();									
									while (ite.hasNext()) {
										sr2.fusePlaces(base,ite.next());
									}
								}
							}
							boolean conti = true;
							try { sr2.reduce(ReductionType.DEADLOCKS) ; }
							catch (DeadlockFound df) {
								conti = false;
							}
							catch (NoDeadlockExists ne) {
								System.out.println( "FORMULA " + reader.getSpec().getProperties().get(0).getName()  + " FALSE TECHNIQUES TOPOLOGICAL SAT_SMT STRUCTURAL_REDUCTION SYMMETRIES");
								return null;								
							}
							if (conti) {
								List<Integer> repr = new ArrayList<>();
								SparseIntArray parikh = DeadlockTester.testDeadlocksWithSMT(sr2,solverPath, isSafe,repr);
								if (parikh == null) {								
									System.out.println( "FORMULA " + reader.getSpec().getProperties().get(0).getName()  + " FALSE TECHNIQUES TOPOLOGICAL SAT_SMT STRUCTURAL_REDUCTION SYMMETRIES");
									return null;
								}
							}
							System.out.println("Symmetry overapproximation was not able to conclude.");
						}
					}
					
					RandomExplorer re = new RandomExplorer(sr);
					long time = System.currentTimeMillis();					
					// 25 k step					
					int steps = 1250000;
					re.run(steps,true,30);						
					System.out.println("Random walk for "+(steps/1000)+" k steps run took "+ (System.currentTimeMillis() -time) +" ms. (steps per millisecond=" + (steps/(System.currentTimeMillis() -time +1)) +" )");
					if (sr.getTnames().size() < 20000) {
						time = System.currentTimeMillis();
						re.run(steps,false,30);
						System.out.println("Random directed walk for "+(steps/1000)+" k steps run took "+ (System.currentTimeMillis() -time) +" ms. (steps per millisecond=" + (steps/(System.currentTimeMillis() -time+1)) +" )");
					}
					
					if (solverPath != null) {
						try {
							List<Integer> repr = new ArrayList<>();
							SparseIntArray parikh = DeadlockTester.testDeadlocksWithSMT(sr,solverPath, isSafe,repr);
							if (parikh == null) {
								System.out.println( "FORMULA " + reader.getSpec().getProperties().get(0).getName()  + " FALSE TECHNIQUES TOPOLOGICAL SAT_SMT STRUCTURAL_REDUCTION");
								return null;
							} else {
								int sz = 0;
								for (int i=0 ; i < parikh.size() ; i++) {
									sz += parikh.valueAt(i);
								}
								if (sz != 0) {
									System.out.println("SMT solver thinks a deadlock is likely to occur in "+sz +" steps after firing vector : " );
									//								for (int i=0 ; i < parikh.size() ; i++) {
									//									System.out.print(sr.getTnames().get(parikh.keyAt(i))+"="+ parikh.valueAt(i)+", ");
									//								}
									time = System.currentTimeMillis();		
									re.run(100*sz, parikh,repr,30);
									System.out.println("Random parikh directed walk for "+(100 * sz)+" steps run took "+ (System.currentTimeMillis() -time) +" ms. (steps per millisecond=" + (steps/(System.currentTimeMillis() -time+1)) +" )");
								}
							}
						} catch (Exception e) {
							// in particular java.lang.ArithmeticException
							// at uniol.apt.analysis.invariants.InvariantCalculator.test1b2(InvariantCalculator.java:448)
							// can occur here.
							System.out.println("Failed to apply SMT based deadlock test, skipping this step." );
							e.printStackTrace();
						}
					}
					
					time = System.currentTimeMillis();
					// 75 k steps in 3 traces
					int nbruns = 4;
					steps = 500000;
					for (int  i = 1 ; i <= nbruns ; i++) {
						re.run(steps, i%2 == 0,30);	
						System.out.println("Random "+ (i%2==0?"":"directed ") +"walk for "+i +" * " + (steps/1000) + " k steps run took "+ (System.currentTimeMillis() -time) +" ms. (steps per millisecond="+ (i*steps)/(System.currentTimeMillis() -time) +" )" );
					}
					System.out.println("Random walk for "+nbruns +" * " + (steps/1000) + " k steps run took "+ (System.currentTimeMillis() -time) +" ms. (steps per millisecond="+ ((nbruns*steps)/(System.currentTimeMillis() -time)) +" )" );
					
					re = null;
					Specification reduced = rebuildSpecification(reader, sr);
					
				} catch (DeadlockFound e) {
					System.out.println( "FORMULA " + reader.getSpec().getProperties().get(0).getName()  + " TRUE TECHNIQUES TOPOLOGICAL STRUCTURAL_REDUCTION RANDOM_WALK");
					return null;					
				} catch (NoDeadlockExists e) {
					System.out.println( "FORMULA " + reader.getSpec().getProperties().get(0).getName()  + " FALSE TECHNIQUES TOPOLOGICAL STRUCTURAL_REDUCTION");
					return null;
				} catch (Exception e) {
					System.out.println("Failed to apply structural reductions, skipping reduction step." );
					e.printStackTrace();
				}
				
			}

			if (onlyGal || doLTSmin) {
				// || examination.startsWith("CTL")
				if (! reader.getSpec().getProperties().isEmpty()) {
					System.out.println("Using solver "+solver+" to compute partial order matrices.");
					ltsminRunner = new LTSminRunner(ltsminpath, solverPath, solver, doPOR, onlyGal, reader.getFolder(), timeout / reader.getSpec().getProperties().size() , isSafe );				
					ltsminRunner.configure(EcoreUtil.copy(reader.getSpec()), doneProps);
					ltsminRunner.solve(this);
				}
			}
			if (doITS || onlyGal) {
				reader = runMultiITS(pwd, examination, gspnpath, orderHeur, doITS, onlyGal, doHierarchy, useManyOrder,
						reader, doneProps, useLouvain, timeout);
			}			
			
			if (ltsminRunner != null) 
				ltsminRunner.join();
		
			return 0;
		}
		
		
		// ReachabilityCard and ReachFire are ok for everybody
		if (examination.equals("ReachabilityFireability") || examination.equals("ReachabilityCardinality") ) {
			
			reader.flattenSpec(false);
			// get rid of trivial properties in spec
			checkInInitial(reader.getSpec(), doneProps, isSafe);
			
			new AtomicReducer().strongReductions(solverPath, reader, isSafe, doneProps);
			
			if (specnocol != null) {
				specnocol.getProperties().removeIf(p -> doneProps.contains(p.getName()));
				if (pwd.contains("COL") || new File(pwd + "/model.pnml").length() < 1000000) {
					String outpath = pwd + "/model.pnml.unc.gal";
					SerializationUtil.systemToFile(specnocol, outpath);
				}
				INextBuilder nb = INextBuilder.build(specnocol);
				IDeterministicNextBuilder idnb = IDeterministicNextBuilder.build(nb);			
				StructuralReduction sr = new StructuralReduction(idnb);

				//  need to protect some variables													
				List<Property> l = reader.getSpec().getProperties(); 
				List<Expression> tocheck = translateProperties(l, idnb);
				if (solverPath != null) {
					List<Integer> repr = new ArrayList<>();
					List<SparseIntArray> paths = DeadlockTester.testUnreachableWithSMT(tocheck, sr, solverPath, isSafe, repr);
					int iter = 0;
					for (int v = paths.size()-1 ; v >= 0 ; v--) {
						SparseIntArray parikh = paths.get(v);
						if (parikh == null) {
							Property prop = specnocol.getProperties().get(v);
							if (prop.getBody() instanceof ReachableProp) {
								System.out.println("FORMULA "+prop.getName() + " FALSE TECHNIQUES COLOR_ABSTRACTION STRUCTURAL_REDUCTION TOPOLOGICAL SAT_SMT");
							} else {
								System.out.println("FORMULA "+prop.getName() + " TRUE TECHNIQUES COLOR_ABSTRACTION STRUCTURAL_REDUCTION TOPOLOGICAL SAT_SMT");
							}
							doneProps.add(prop.getName());
							iter++;
						} 
					}
					if (reader.getSpec().getProperties().removeIf(p -> doneProps.contains(p.getName()))) {
						System.out.println("Colored abstraction solved "+iter+" properties.");
					}
				}
			}
			
			
			int iter;
			int iterations =0;
			do {
				iter =0;
				INextBuilder nb = INextBuilder.build(reader.getSpec());
				IDeterministicNextBuilder idnb = IDeterministicNextBuilder.build(nb);			
				StructuralReduction sr = new StructuralReduction(idnb);
		
				//  need to protect some variables													
				List<Property> l = reader.getSpec().getProperties(); 
				List<Expression> tocheck = translateProperties(l, idnb);
				List<Integer> tocheckIndexes = new ArrayList<>();
				for (int j=0; j < l.size(); j++) { tocheckIndexes.add(j);}
				RandomExplorer re = new RandomExplorer(sr);
				int steps = 1000000; // 1 million
				if (iterations == 0) {
					steps = 10000; // be more moderate on first run
				}
				if (iterations >0 && randomCheckReachability(re, tocheck, reader.getSpec(), doneProps,steps) >0)
					iter++;
						
				if (reader.getSpec().getProperties().isEmpty())
					break;
				
				if (solverPath != null && iterations >0) {
					List<Integer> repr = new ArrayList<>();
					List<SparseIntArray> paths = DeadlockTester.testUnreachableWithSMT(tocheck, sr, solverPath, isSafe, repr);
					
					iter += treatVerdicts(reader, doneProps, tocheck, tocheckIndexes, paths);
					
					
					for (int v = paths.size()-1 ; v >= 0 ; v--) {
						SparseIntArray parikh = paths.get(v);
						if (parikh != null) {
							// we have a candidate, try a Parikh satisfaction run. 
							int sz = 0;
							for (int i=0 ; i < parikh.size() ; i++) {
								sz += parikh.valueAt(i);
							}
							if (sz != 0) {
								System.out.println("SMT solver thinks a reachable state is likely to occur in "+sz +" steps.");
//								for (int i=0 ; i < parikh.size() ; i++) {
//									System.out.print(sr.getTnames().get(parikh.keyAt(i))+"="+ parikh.valueAt(i)+", ");
//								}
								long time = System.currentTimeMillis();		
								int[] verdicts = re.run(100*sz, parikh, tocheck,repr,30);
								interpretVerdict(tocheck, reader.getSpec(), doneProps, verdicts, "PARIKH");
								
								System.out.println("Random parikh directed walk for "+(100 * sz)+" steps run took "+ (System.currentTimeMillis() -time) +" ms. (steps per millisecond=" + (100*sz/(System.currentTimeMillis() -time+1)) +" )");
							}
						}
					}
					if (reader.getSpec().getProperties().removeIf(p -> doneProps.contains(p.getName())))
						iter++;
					
				}
				
				if (reader.getSpec().getProperties().removeIf(p -> doneProps.contains(p.getName())))
					iter++;
				if (reader.getSpec().getProperties().isEmpty())
					break;
				
				
				BitSet support = new BitSet();
				for (Property prop : reader.getSpec().getProperties()) {
					if (! doneProps.contains(prop.getName()))
						NextSupportAnalyzer.computeQualifiedSupport(prop, support , idnb);
				}
				System.out.println("Support contains "+support.cardinality() + " out of " + sr.getPnames().size() + " places. Attempting structural reductions.");
				
				sr.setProtected(support);
				if (applyReductions(sr, reader, ReductionType.SAFETY, solverPath, isSafe,false)) {
					iter++;					
				} else if (applyReductions(sr, reader, ReductionType.SAFETY, solverPath, isSafe,true)) {
					iter++;
				}
				Specification reduced = rebuildSpecification(reader, sr); 
				reader.flattenSpec(false);
				checkInInitial(reader.getSpec(), doneProps, isSafe);
				
				if (reader.getSpec().getProperties().removeIf(p -> doneProps.contains(p.getName())))
					iter++;
				
//				if (iterations == 0) {
//					SerializationUtil.systemToFile(reader.getSpec(), "/tmp/before.gal");
//					reader.rewriteSums();
//					SerializationUtil.systemToFile(reader.getSpec(), "/tmp/after.gal");
//				}
				
				iterations++;
			} while ( (iterations==1 || iter > 0) && ! reader.getSpec().getProperties().isEmpty());
				
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
				
			//}
			
			if (doneProps.containsAll(reader.getSpec().getProperties().stream().map(p->p.getName()).collect(Collectors.toList()))) {
				System.out.println("All properties solved without resorting to model-checking.");
				return null;
			}
			
			// SMT does support hierarchy theoretically but does not like it much currently, time to start it, the spec won't get any better
			if ( (z3path != null || yices2path != null) && doSMT ) {
				Specification z3Spec = EcoreUtil.copy(reader.getSpec());
				// run on a fresh copy to avoid any interference with other threads. (1 hour timeout)
				z3Runner = new SMTRunner(pwd, solverPath, solver, timeout, isSafe);
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
					ltsminRunner = new LTSminRunner(ltsminpath, solverPath, solver, doPOR, onlyGal, reader.getFolder(), timeout / reader.getSpec().getProperties().size() ,isSafe );				
					ltsminRunner.configure(EcoreUtil.copy(reader.getSpec()), doneProps);
					ltsminRunner.solve(this);
				}
			}
			
			
			
			reader = runMultiITS(pwd, examination, gspnpath, orderHeur, doITS, onlyGal, doHierarchy, useManyOrder,
					reader, doneProps,useLouvain, timeout);
			
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


	public Specification rebuildSpecification(MccTranslator reader, StructuralReduction sr) {
		Specification reduced = sr.rebuildSpecification();
		reduced.getProperties().addAll(reader.getSpec().getProperties());
		Instantiator.normalizeProperties(reduced);
		Set<String> constants = sr.computeConstants().stream().map(n -> sr.getPnames().get(n)).collect(Collectors.toSet());					
		Map<ArrayPrefix, Set<Integer>> constantArrs = new HashMap<>();
		Set<Variable> constvars = new HashSet<>();
		GALTypeDeclaration gal = (GALTypeDeclaration) reduced.getTypes().get(0);
		for (Variable var : gal.getVariables()) {
			if (constants.contains(var.getName())) {
				constvars.add(var);
			}
		}
		Simplifier.replaceConstants(gal, constvars, constantArrs);
		reader.setSpec(reduced);
		return reduced;
	}

	private int treatVerdicts(MccTranslator reader, Set<String> doneProps, List<Expression> tocheck,
			List<Integer> tocheckIndexes, List<SparseIntArray> paths) {
		int iter = 0;
		for (int v = paths.size()-1 ; v >= 0 ; v--) {
			SparseIntArray parikh = paths.get(v);
			if (parikh == null) {
				Property prop = reader.getSpec().getProperties().get(tocheckIndexes.get(v));
				if (prop.getBody() instanceof ReachableProp) {
					System.out.println("FORMULA "+prop.getName() + " FALSE TECHNIQUES STRUCTURAL_REDUCTION TOPOLOGICAL SAT_SMT");
				} else {
					System.out.println("FORMULA "+prop.getName() + " TRUE TECHNIQUES STRUCTURAL_REDUCTION TOPOLOGICAL SAT_SMT");
				}
				doneProps.add(prop.getName());
				tocheck.remove(v);
				tocheckIndexes.remove(v);
				iter++;
			} 
		}
		if (reader.getSpec().getProperties().removeIf(p -> doneProps.contains(p.getName())))
			iter++;
		return iter;
	}

	private List<Expression> translateProperties(List<Property> props, IDeterministicNextBuilder idnb) {
		List<Expression> tocheck = new ArrayList<Expression> ();
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

	private int randomCheckReachability(RandomExplorer re, List<Expression> tocheck, Specification spec,
			Set<String> doneProps, int steps) {
		long time = System.currentTimeMillis();					
		// 25 k step
		
		int[] verdicts = re.run(steps,tocheck,30);
		int seen = interpretVerdict(tocheck, spec, doneProps, verdicts,"RANDOM");
		System.out.println("Random walk for "+(steps/1000)+" k steps run took "+ (System.currentTimeMillis() -time) +" ms. (steps per millisecond=" + (steps/(System.currentTimeMillis() -time)) +" )");
		return seen;
	}

	private int interpretVerdict(List<Expression> tocheck, Specification spec, Set<String> doneProps,
			int[] verdicts, String walkType) {
		int seen = 0; 
		for (int v = verdicts.length-1 ; v >= 0 ; v--) {
			if (verdicts[v] != 0) {
				Property prop = spec.getProperties().get(v);
				if (prop.getBody() instanceof ReachableProp) {
					System.out.println("FORMULA "+prop.getName() + " TRUE TECHNIQUES TOPOLOGICAL "+walkType+"_WALK");
				} else {
					System.out.println("FORMULA "+prop.getName() + " FALSE TECHNIQUES TOPOLOGICAL "+walkType+"_WALK");
				}
				doneProps.add(prop.getName());
				tocheck.remove(v);
				spec.getProperties().remove(v);
				seen++;
			}
		}
		return seen;
	}

	private boolean applyReductions(StructuralReduction sr, MccTranslator reader, ReductionType rt, String solverPath, boolean isSafe, boolean withSMT)
			throws NoDeadlockExists, DeadlockFound {
		boolean cont = false;
		int it =0;
		int initp = sr.getPnames().size();
		int initt = sr.getTnames().size();
		int total = 0;
		do {
			System.out.println("Starting structural reductions, iteration "+ it + " : " + sr.getPnames().size() +"/" +initp+ " places, " + sr.getTnames().size()+"/"+initt + " transitions.");
			
			int reduced = 0; 
									
			reduced += sr.reduce(rt);
			total+=reduced;
			cont = false;
			if (rt == ReductionType.DEADLOCKS && sr.getTnames().isEmpty()) {
				throw new DeadlockFound();
			}
			
			if (withSMT) {
				boolean useStateEq = false;
				if (reduced > 0 || it ==0) {
					long t = System.currentTimeMillis();
					// 	go for more reductions ?
					
					List<Integer> implicitPlaces = DeadlockTester.testImplicitWithSMT(sr, solverPath, isSafe, false);							
					if (!implicitPlaces.isEmpty()) {
						sr.dropPlaces(implicitPlaces,false,"Implicit Places With SMT (invariants only)");
						cont = true;
						total++;
					} else if (sr.getPnames().size() <= 10000 && sr.getTnames().size() < 10000){
						// limit to 20 k variables for SMT solver with parikh constraints
						useStateEq = true;
						// with state equation can we solve more ?
						implicitPlaces = DeadlockTester.testImplicitWithSMT(sr, solverPath, isSafe, true);
						if (!implicitPlaces.isEmpty()) {
							sr.dropPlaces(implicitPlaces,false,"Implicit Places With SMT (with state equation)");
							reduced += implicitPlaces.size();
							cont = true;
							total++;
						}
					}							
					System.out.println("Implicit Place search using SMT "+ (useStateEq?"with State Equation":"only with invariants") +" took "+ (System.currentTimeMillis() -t) +" ms to find "+implicitPlaces.size()+ " implicit places.");
				}

				if (reduced == 0 || it==0) {
					List<Integer> tokill = DeadlockTester.testImplicitTransitionWithSMT(sr, solverPath);
					if (! tokill.isEmpty()) {
						System.out.println("Found "+tokill.size()+ " redundant transitions using SMT." );
					}
					sr.dropTransitions(tokill,"Redundant Transitions using SMT "+ (useStateEq?"with State Equation":"only with invariants") );
					if (!tokill.isEmpty()) {
						System.out.println("Redundant transitions reduction (with SMT) removed "+tokill.size()+" transitions :"+ tokill);								
						cont = true;
						total++;
					}
				}
			}
			if (!cont && rt == ReductionType.SAFETY && withSMT) {
				cont = sr.ruleFreeAgglo(true) > 0;
			}
			it++;
		} while (cont);
		System.out.println("Finished structural reductions, in "+ it + " iterations. Remains : " + sr.getPnames().size() +"/" +initp+ " places, " + sr.getTnames().size()+"/"+initt + " transitions.");
		return total > 0;
	}

	private MccTranslator runMultiITS(String pwd, String examination, String gspnpath, String orderHeur, boolean doITS,
			boolean onlyGal, boolean doHierarchy, boolean useManyOrder, MccTranslator reader, Set<String> doneProps, boolean useLouvain, long timeout)
			throws IOException, InterruptedException {
		MccTranslator reader2 = null;
		if (useManyOrder) {
			reader2 = reader.copy();
			timeout /= 3;
		} else {
			reader2 = reader;
		}
		if (orderHeur != null && gspnpath != null) {
			reader.flattenSpec(false);		
			String myOrderff = null;
			if (orderHeur != null) {
				myOrderff = computeOrderWithGreatSPN(pwd, gspnpath, orderHeur, reader, myOrderff);
			}

			if (doITS || onlyGal) {				
				// decompose + simplify as needed
				itsRunner = new ITSRunner(examination, reader, doITS, onlyGal, reader.getFolder(),timeout, myOrderff);
				itsRunner.configure(reader.getSpec(), doneProps);
			}			

			if (doITS) {
				itsRunner.solve(this);
				itsRunner.join();				
			}
		}
		
		if (! wasKilled && (doITS || onlyGal) && (!useLouvain || useManyOrder)) {
			if (useManyOrder)
				reader = reader2.copy();
			reader.getSpec().getProperties().removeIf(p->doneProps.contains(p.getName()));
			reader.flattenSpec(true);

			if (doITS || onlyGal) {				
				// decompose + simplify as needed
				itsRunner = new ITSRunner(examination, reader, doITS, onlyGal, reader.getFolder(),timeout, null);
				itsRunner.configure(reader.getSpec(), doneProps);
			}			
					
			if (doITS) {
				itsRunner.solve(this);
				itsRunner.join();				
			}
		}

		if (! wasKilled && (useLouvain || useManyOrder) ) {
			if (useManyOrder)
				reader = reader2;
			reader.getSpec().getProperties().removeIf(p->doneProps.contains(p.getName()));
			reader.setLouvain(true);
			reader.setOrder(null);
			reader.flattenSpec(true);

			if (doITS || onlyGal) {				
				// decompose + simplify as needed
				itsRunner = new ITSRunner(examination, reader, doITS, onlyGal, reader.getFolder(),timeout, null);
				itsRunner.configure(reader.getSpec(), doneProps);
			}			
					
			if (doITS) {
				itsRunner.solve(this);
				itsRunner.join();				
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
				reduced.getProperties().addAll(reader.getSpec().getProperties());
				Instantiator.normalizeProperties(reduced);
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
		}
		return orderff;
	}








	/**
	 * Structural analysis and reduction : test in initial state.
	 * @param specWithProps spec which will be modified : trivial properties will be removed
	 * @param doneProps 
	 */
	private void checkInInitial(Specification specWithProps, Set<String> doneProps, boolean isSafe) {
		List<Property> props = new ArrayList<Property>(specWithProps.getProperties());
				
		// iterate down so indexes are consistent
		for (int i = props.size()-1; i >= 0 ; i--) {
			Property propp = props.get(i);

			if (doneProps.contains(propp.getName())) {
				specWithProps.getProperties().remove(i);
				continue;
			}
			if (isSafe) {
				for (TreeIterator<EObject> ti = propp.getBody().eAllContents() ; ti.hasNext() ; ) {
					EObject obj = ti.next();
					if (obj instanceof Comparison) {
						Comparison cmp = (Comparison) obj;
						
						if (cmp.getLeft() instanceof Reference && cmp.getRight() instanceof Constant) {
							int val = ((Constant) cmp.getRight()).getValue();
							if (   ( val > 1 && ( cmp.getOperator() == ComparisonOperators.LE || cmp.getOperator() == ComparisonOperators.LT)) 
									||
									( val == 1 && cmp.getOperator() == ComparisonOperators.LE )
									) {
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createTrue());
								ti.prune();
							} else if (val > 1 || (val == 1 && cmp.getOperator() == ComparisonOperators.GT) ) {
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createFalse());
								ti.prune();
							}
						} else if (cmp.getRight() instanceof Reference && cmp.getLeft() instanceof Constant) {
							int val = ((Constant) cmp.getLeft()).getValue();
							if (   ( val > 1 && ( cmp.getOperator() == ComparisonOperators.GE || cmp.getOperator() == ComparisonOperators.GT)) 
									||
									( val == 1 && cmp.getOperator() == ComparisonOperators.GE )
									) {
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createTrue());
								ti.prune();
							} else if (val > 1 || (val == 1 && cmp.getOperator() == ComparisonOperators.LT) ) {
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createFalse());
								ti.prune();
							}
						}
					}
				}
			}
			LogicProp prop = propp.getBody();
			Simplifier.simplifyAllExpressions(prop);

			boolean solved = false;
			// output verdict
			if (prop instanceof ReachableProp || prop instanceof InvariantProp) {

				if (((SafetyProp) prop).getPredicate() instanceof True) {
					// positive forms : EF True , AG True <=>True
					System.out.println("FORMULA "+propp.getName() + " TRUE TECHNIQUES TOPOLOGICAL INITIAL_STATE");
					solved = true;
				} else if (((SafetyProp) prop).getPredicate() instanceof False) {
					// positive forms : EF False , AG False <=> False
					System.out.println("FORMULA "+propp.getName() + " FALSE TECHNIQUES TOPOLOGICAL INITIAL_STATE");
					solved = true;
				}
			} else if (prop instanceof NeverProp) {
				if (((SafetyProp) prop).getPredicate() instanceof True) {
					// negative form : ! EF P = AG ! P, so ! EF True <=> False
					System.out.println("FORMULA "+propp.getName() + " FALSE TECHNIQUES TOPOLOGICAL INITIAL_STATE");
					solved = true;
				} else if (((SafetyProp) prop).getPredicate() instanceof False) {
					// negative form : ! EF P = AG ! P, so ! EF False <=> True
					System.out.println("FORMULA "+propp.getName() + " TRUE TECHNIQUES TOPOLOGICAL INITIAL_STATE");
					solved = true;
				}
			} else if (prop instanceof LTLProp) {
				LTLProp ltl = (LTLProp) prop;
				if (ltl.getPredicate() instanceof True) {
					// positive forms : EF True , AG True <=>True
					System.out.println("FORMULA "+propp.getName() + " TRUE TECHNIQUES TOPOLOGICAL INITIAL_STATE");
					solved = true;
				} else if (ltl.getPredicate() instanceof False)  {
					// positive forms : EF False , AG False <=> False
					System.out.println("FORMULA "+propp.getName() + " FALSE TECHNIQUES TOPOLOGICAL INITIAL_STATE");
					solved = true;
				}
			} else if (prop instanceof CTLProp) {
				CTLProp ltl = (CTLProp) prop;
				if (ltl.getPredicate() instanceof True) {
					// positive forms : EF True , AG True <=>True
					System.out.println("FORMULA "+propp.getName() + " TRUE TECHNIQUES TOPOLOGICAL INITIAL_STATE");
					solved = true;
				} else if (ltl.getPredicate() instanceof False)  {
					// positive forms : EF False , AG False <=> False
					System.out.println("FORMULA "+propp.getName() + " FALSE TECHNIQUES TOPOLOGICAL INITIAL_STATE");
					solved = true;
				}
			}

			if (solved) {
				doneProps.add(propp.getName());
				// discard property
				specWithProps.getProperties().remove(i);
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
