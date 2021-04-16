package fr.lip6.move.gal.application;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import android.util.SparseIntArray;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.mcc.properties.PropertiesToPNML;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.NoDeadlockExists;
import fr.lip6.move.gal.structural.RandomExplorer;
import fr.lip6.move.gal.structural.SparseHLPetriNet;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.smt.DeadlockTester;
import fr.lip6.move.gal.structural.StructuralToPNML;

public abstract class DeadlockSolver {

	private static final String REACHABILITY_DEADLOCK = "ReachabilityDeadlock";
	private static final int DEBUG = 0;

	public static Optional<Boolean> checkStructuralDeadlock(String pwd, String examination, String blisspath, String solverPath,
			MccTranslator reader, boolean isSafe, DoneProperties doneProps) {
		{					


			if (reader.getHLPN() != null) {
				SparsePetriNet spn = reader.getHLPN().skeleton();
				spn.toPredicates();			
				spn.testInInitial();

				// this might break the consistency between hlpn and skeleton place indexes, let's avoid it.
				spn.removeConstantPlaces();
				//					spn.removeRedundantTransitions(false);
				//					spn.removeConstantPlaces();
				spn.simplifyLogic();

				if (spn.getFlowPT().getColumns().stream().anyMatch(c -> c.size() == 0)) {
					System.out.println("Skeleton produced trivially non deadlocked net.");
				} else {
					StructuralReduction sr = new StructuralReduction(spn);				
					try {
						Set<String> before = new HashSet<>(sr.getPnames());
						Set<Integer> safeNodes = StructuralReduction.findSCCSuffixes(spn,ReductionType.DEADLOCKS,new BitSet());

						if (safeNodes != null) {
							Set<String> torem = new HashSet<>(before);
							torem.removeAll(sr.getPnames());

							Set<Integer> hlSafeNodes = new HashSet<>();
							SparseHLPetriNet hlpn = reader.getHLPN();
							for (int pid = 0 ; pid < hlpn.getPlaces().size() ; pid++) {
								if (!torem.contains(hlpn.getPlaces().get(pid).getName())) {
									hlSafeNodes.add(pid);
								}
							}
							hlpn.dropAllExcept(hlSafeNodes);
							//	System.out.println(hlpn);
						}


					} catch (DeadlockFound e) {
						doneProps.put(REACHABILITY_DEADLOCK, true, "CPN_APPROX TOPOLOGICAL STRUCTURAL_REDUCTION");
						return Optional.of(true);
					}
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

				System.out.println("Built sparse matrix representations for Structural reductions in "+ (System.currentTimeMillis()-tt) + " ms." + ( (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) / 1000) + "KB memory used");

				if (false && blisspath != null) {
					List<List<List<Integer>>> generators = null;
					BlissRunner br = new BlissRunner(blisspath,pwd,100);
					generators = br.run(sr);
					System.out.println("Obtained generators : " + generators);
					br.computeMatrixForm(generators);
				}
				try {
					if (! ReachabilitySolver.applyReductions(sr, ReductionType.DEADLOCKS, solverPath, isSafe, false,true)) 
						ReachabilitySolver.applyReductions(sr, ReductionType.DEADLOCKS, solverPath, isSafe, true,false);					
				} catch (DeadlockFound d) {
					doneProps.put(REACHABILITY_DEADLOCK, true, "TOPOLOGICAL STRUCTURAL_REDUCTION");
					return Optional.of(true);
				}


				if (false) {
					FlowPrinter.drawNet(sr,"initial");
					String outsr = pwd + "/model.sr.pnml";
					StructuralToPNML.transform(reader.getSPN(), outsr);
					String outform = pwd + "/" + examination + ".sr.xml";
					PropertiesToPNML.transform(spn, outform, doneProps);
				}


				if (blisspath != null) {
					boolean hasConcluded = runBlissSymmetryAnalysis(reader, sr, isSafe, blisspath, pwd, solverPath);
					if (hasConcluded) {
						// ??? this code never invoked
						return Optional.of(true);
					}
				}

				for (int iter=0 ; iter<2 ; iter++) {

					if (iter == 1) {
						ReachabilitySolver.applyReductions(sr, ReductionType.DEADLOCKS, solverPath, isSafe, true,false);
					}
					RandomExplorer re = new RandomExplorer(sr);
					long time = System.currentTimeMillis();					
					// 25 k step					
					int steps = 1250000;
					re.runDeadlockDetection(steps,true,30);						
					if (sr.getTnames().size() < 20000) {
						time = System.currentTimeMillis();
						re.runDeadlockDetection(steps,false,30);
					}

					if (solverPath != null) {
						try {
							List<Integer> repr = new ArrayList<>();
							SparseIntArray por = new SparseIntArray();
							SparseIntArray parikh = DeadlockTester.testDeadlocksWithSMT(sr,solverPath, isSafe,repr, por);
							if (parikh == null) {
								doneProps.put(REACHABILITY_DEADLOCK, false, "TOPOLOGICAL SAT_SMT STRUCTURAL_REDUCTION");
								return Optional.of(false);
							} else {
								int sz = 0;
								for (int i=0 ; i < parikh.size() ; i++) {
									sz += parikh.valueAt(i);
								}
								if (sz != 0) {
									if (DEBUG >= 1) {
										StringBuilder sb = new StringBuilder();
										for (int i=0 ; i < parikh.size() ; i++) {
											sb.append(sr.getTnames().get(parikh.keyAt(i))+"="+ parikh.valueAt(i)+", ");
										}
										System.out.println("SMT solver thinks a deadlock is likely to occur in "+sz +" steps after firing vector : " + sb.toString() );
									}
									// FlowPrinter.drawNet(sr, "Parikh Test :" + sb.toString());
									time = System.currentTimeMillis();			
									// TODO : transmit por info
									re.runGuidedDeadlockDetection(100*sz, parikh,repr,30);
								}
							}
						} catch (ArithmeticException e) {
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
						re.runDeadlockDetection(steps, i%2 == 0,30);	
					}

					re = null;

				}

				reader.rebuildSpecification(doneProps);

			} catch (DeadlockFound e) {
				doneProps.put(REACHABILITY_DEADLOCK, true, "TOPOLOGICAL STRUCTURAL_REDUCTION RANDOM_WALK");
				return Optional.of(true);					
			} catch (NoDeadlockExists e) {
				doneProps.put(REACHABILITY_DEADLOCK, false, "TOPOLOGICAL STRUCTURAL_REDUCTION");
				return Optional.of(false);
			} catch (Exception e) {
				System.out.println("Failed to apply structural reductions, skipping reduction step." );
				e.printStackTrace();
			}
		}
		return Optional.empty();
	}

	private static boolean runBlissSymmetryAnalysis(MccTranslator reader, StructuralReduction sr, boolean isSafe,
			String blisspath, String pwd, String solverPath) throws TimeoutException {
		boolean hasConcluded = false;
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
				hasConcluded=true;
				conti = false;
			}
			if (conti) {
				List<Integer> repr = new ArrayList<>();
				SparseIntArray parikh = DeadlockTester.testDeadlocksWithSMT(sr2,solverPath, isSafe,repr, new SparseIntArray());
				if (parikh == null) {								
					System.out.println( "FORMULA " + reader.getSpec().getProperties().get(0).getName()  + " FALSE TECHNIQUES TOPOLOGICAL SAT_SMT STRUCTURAL_REDUCTION SYMMETRIES");
					hasConcluded = true;
				}
			}
			if (!hasConcluded)
				System.out.println("Symmetry overapproximation was not able to conclude.");							
		}
		return hasConcluded;
	}

}
