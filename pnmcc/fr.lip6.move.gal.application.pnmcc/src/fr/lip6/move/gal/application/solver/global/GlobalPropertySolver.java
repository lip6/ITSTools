package fr.lip6.move.gal.application.solver.global;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import java.util.Map.Entry;
import java.util.Optional;

import android.util.SparseIntArray;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.application.runner.Ender;
import fr.lip6.move.gal.application.runner.IRunner;
import fr.lip6.move.gal.application.runner.its.ITSRunner;
import fr.lip6.move.gal.application.runner.ltsmin.LTSminRunner;
import fr.lip6.move.gal.application.solver.ParallelWalk;
import fr.lip6.move.gal.application.solver.ReachabilitySolver;
import fr.lip6.move.gal.application.solver.UpperBoundsSolver;
import fr.lip6.move.gal.graph.GraphSuffix;
import fr.lip6.move.gal.structural.graph.PlacesInNonTrivialSCCComputer;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SiphonComputer;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.hlpn.HLPlace;
import fr.lip6.move.gal.structural.smt.DeadlockTester;
import fr.lip6.move.gal.util.IntMatrixCol;
import fr.lip6.move.petrispot.runner.PetriSpotRunner;
import fr.lip6.move.petrispot.runner.PetriSpotRunner.InvariantMode;

public class GlobalPropertySolver {

	private static final String REACHABILITY_DEADLOCK = "ReachabilityDeadlock";

	private static final String LIVENESS = "Liveness";

	private static final String QUASI_LIVENESS = "QuasiLiveness";

	private static final String STABLE_MARKING = "StableMarking";

	private static final String ONE_SAFE = "OneSafe";

	private static final int DEBUG = 0;

	public GlobalPropertySolver() {
	}

	public Optional<Boolean> solveProperty(String examination, MccTranslator reader) {
		// initialize a shared container to detect help detect termination in portfolio
		// case
		DoneProperties doneProps = new GlobalDonePropertyPrinter(examination, true);
		return preStableMarking(examination, reader, doneProps);
	}

	public Optional<Boolean> preStableMarking(String examination, MccTranslator reader, DoneProperties doneProps) {

	
		if (STABLE_MARKING.equals(examination) && reader.getHLPN() != null) {
			
			if(reader.getHLPN().getPlaces().stream().anyMatch(HLPlace::isConstant)) {
				System.out.println("FORMULA " + examination + " TRUE TECHNIQUES TOPOLOGICAL CPN_APPROX");
				return Optional.of(true);
			}

			MccTranslator readercopy = reader.copy();
			SparsePetriNet skel = readercopy.getHLPN().skeleton();

			readercopy.setHLPN(null);
			readercopy.setSpn(skel, false);
			Optional<Boolean> qlResult = solveProperty(STABLE_MARKING, readercopy,
					new GlobalDonePropertyPrinter(STABLE_MARKING, false));

			if (qlResult.isPresent() && qlResult.get()) {
				System.out.println("FORMULA " + examination + " TRUE TECHNIQUES SKELETON_TEST");
				return Optional.of(true);
			}
		}
		
		return preSolveLiveness(examination, reader, doneProps);

	}

	public Optional<Boolean> preSolveLiveness(String examination, MccTranslator reader, DoneProperties doneProps) {

		if (LIVENESS.equals(examination)) {

			boolean isCol = (reader.getHLPN() != null);

			// for COL : testing on skeleton
			if (isCol) {

				{
					ISparsePetriNet skel = reader.getHLPN().skeleton();

					if (!executeSCCLivenessTest(skel, null)) {
						System.out.println("FORMULA Liveness FALSE TECHNIQUES STRUCTURAL SKELETON_TEST");
						return Optional.of(false);
					}
				}
				{

					List<List<Integer>> en = new ArrayList<>(reader.getHLPN().getTransitionCount());
					SparsePetriNet spn = reader.getHLPN().unfold(en,ReductionType.STATESPACE);
					reader.setSpn(spn, false);
					if (!executeSCCLivenessTest(spn, en)) {
						System.out.println("FORMULA Liveness FALSE TECHNIQUES STRUCTURAL SKELETON_TEST");
						return Optional.of(false);
					}

				}
			}

			{
				// call for liveness exhaustive evaluation (using definiton)
				if (reader.getHLPN() != null)
					GlobalAtoms.liveness(reader.getHLPN(), GlobalAtoms.dominatedTransitions(reader.getHLPN()));
				else
					GlobalAtoms.liveness(reader.getSPN(), GlobalAtoms.dominatedTransitions(reader.getSPN()));
			}

			reader.createSPN(false, false);
			{
				if (!isCol && !executeSCCLivenessTest(reader.getSPN(), null)) {
					System.out.println("FORMULA " + examination + " FALSE TECHNIQUES STRUCTURAL SCC_TEST");
					return Optional.of(false);
				}
			}
			if (!isCol) { // what's next : search for siphons .
				Set<Integer> syphon = SiphonComputer.computeEmptySyphon(reader.getSPN().getFlowPT(),
						reader.getSPN().getFlowTP(), reader.getSPN().getMarks());
				if (!syphon.isEmpty()) {
					System.out.println("FORMULA " + examination + " FALSE TECHNIQUES STRUCTURAL SIPHON_TEST");
					return Optional.of(false);
				}
			}
			{
				// test for deadlocks

				Optional<Boolean> deadlock = DeadlockSolver.checkStructuralDeadlock(reader.getFolder(),
						REACHABILITY_DEADLOCK, null, reader.copy(), new GlobalDonePropertyPrinter(REACHABILITY_DEADLOCK, false));
				if (deadlock.isPresent() && deadlock.get()) {
					System.out.println("FORMULA " + examination + " FALSE TECHNIQUES STRUCTURAL DEADLOCK_TEST");
					return Optional.of(false);
				}

			}

			{
				// test for NOT QuasiLiveness ==> NOT Liveness
				MccTranslator readercopy = reader.copy();
				List<Property> hlpnprops = null;
				if (reader.getHLPN() != null) {
					hlpnprops = new ArrayList<>(reader.getHLPN().getProperties());
					reader.getHLPN().getProperties().clear();
				}
				readercopy.getSPN().getProperties().clear();
				
				GlobalDonePropertyPrinter localDone = new GlobalDonePropertyPrinter(QUASI_LIVENESS, false);
				Optional<Boolean> qlResult = solveProperty(QUASI_LIVENESS, readercopy,
						localDone);

				if (qlResult.isPresent()) {
					if (!qlResult.get()) {
						System.out.println("FORMULA " + examination + " FALSE TECHNIQUES QUASILIVENESS_TEST");
						return Optional.of(false);
					} else {
						
						doneProps.put(QUASI_LIVENESS, true, localDone.computeTechniques());
						// Quasi live + reversible => live
						// this is a single property to check (initial state is a home state), might be simpler.
					}
				} else if (reader.getHLPN() != null) {
					reader.getHLPN().getProperties().clear();
					reader.getHLPN().getProperties().addAll(hlpnprops);
				}
			}
		}

		return solveProperty(examination, reader, doneProps);
	}

	public boolean executeSCCLivenessTest(ISparsePetriNet spn, List<List<Integer>> en) {
		// stack based tarjan
		Set<Integer> scc = PlacesInNonTrivialSCCComputer.computePlacesInNonTrivialSCC(spn);
		if (DEBUG > 2)
			FlowPrinter.drawNet(spn, "SCC TARJAN", scc, Collections.emptySet());

		boolean isLive = true;

		if (en == null) {
			// si une place de P \ P_scc a des transitions qui consomment dedans, => NOT
			// Live
			if (scc.size() < spn.getPlaceCount()) {
				IntMatrixCol tFlowPT = spn.getFlowPT().transpose();
				for (int pid = 0; pid < spn.getPlaceCount(); pid++) {
					if (scc.contains(pid))
						continue;
					if (tFlowPT.getColumn(pid).size() > 0) {
						isLive = false;
						break;
					}
				}
			}
		} else {
			// Pour chaque transition (COL ou pas), // Sinon => NOT live
			// si une place de P \ P_scc a des transitions qui consomment dedans, => NOT
			// Live
			if (scc.size() < spn.getPlaceCount()) {
				// Pour chaque transition (COL ou pas),
				for (int tcol = 0, tcole = en.size(); tcol < tcole; tcol++) {
					boolean isOK = false;
					// il existe une instance de la transition (elle même seule instance dans le cas
					// non COL),
					for (int tid : en.get(tcol)) {
						SparseIntArray pt = spn.getFlowPT().getColumn(tid);
						// dont toutes les places précondition sont dans P_scc
						boolean instanceOK = true;
						for (int i = 0; i < pt.size(); i++) {
							int pid = pt.keyAt(i);
							// la transition consomme dans une place hors SCC => cette instance de
							// transition est not live
							if (!scc.contains(pid)) {
								instanceOK = false;
								break;
							}
						}
						if (instanceOK) {
							isOK = true;
							break;
						}
					}
					// sinon not live
					if (!isOK) {
						isLive = false;
						break;
					}
				}
			}
		}
		return isLive;
	}

	private Optional<Boolean> solveProperty(String examination, MccTranslator reader, DoneProperties doneProps) {
		try {
			if (!LIVENESS.equals(examination)) {

				if (reader.getHLPN() != null) {

					buildProperties(examination, reader.getHLPN(), doneProps);

					if (ONE_SAFE.equals(examination)) {
						for (HLPlace place : reader.getHLPN().getPlaces()) {
							int[] initial = place.getInitial();
							int sum = Arrays.stream(initial).sum();
							if (sum > 1) {
								System.out.println("FORMULA " + examination
										+ " FALSE TECHNIQUES STRUCTURAL INITIAL_STATE CPN_APPROX");
								return Optional.of(false);
							}
						}
					}

				}

				
				// load "known" stuff about the model
				if (reader.getSPN() != null && reader.getSPN().isSafe()) {
					// NUPN implies one safe
					if (examination.equals(ONE_SAFE)) {
						System.out.println("FORMULA " + examination + " TRUE TECHNIQUES STRUCTURAL");
						return Optional.of(true);
					}
				}
				if (QUASI_LIVENESS.equals(examination) || STABLE_MARKING.equals(examination)
						|| LIVENESS.equals(examination)) {
					reader.createSPN(false, false);
				} else {
					reader.createSPN();
				}
			}
			// switching examination
			if (reader.getHLPN() == null) {
				reader.getSPN().getProperties().clear();
				if (examination.equals(LIVENESS) || examination.equals(QUASI_LIVENESS)) {
					StructuralReduction sr = new StructuralReduction(reader.getSPN());
					try {
						ReachabilitySolver.applyReductions(sr, ReductionType.LIVENESS, true, true);
						// sr.reduce(ReductionType.LIVENESS);
					} catch (DeadlockFound e) {
						doneProps.put(examination, false, "STRUCTURAL_REDUCTION");
						return Optional.of(false);
					} catch (GlobalPropertySolvedException e) {
						e.printStackTrace();
					}
					reader.getSPN().readFrom(sr);
				}
				buildProperties(examination, reader.getSPN(), doneProps);
			}
			
			if (examination.equals(LIVENESS) && Boolean.valueOf(true).equals(doneProps.getValue(QUASI_LIVENESS))) {
					System.out.println("Net is quasi-live, checking if it is reversible to establish liveness.");

					MccTranslator readercopy = reader.copy();
					readercopy.getSPN().getProperties().clear();
					GlobalAtoms.reversible(readercopy.getSPN());
					
					GlobalDonePropertyPrinter localDone = new GlobalDonePropertyPrinter(LIVENESS, false);
					
					Optional<Boolean> result = applyExhaustiveMethods(examination, readercopy, localDone);
					if (result.isPresent() && result.get()) {
						doneProps.put(GlobalAtoms.REVERSIBLE, true, localDone.computeTechniques());
						doneProps.put(examination, true, localDone.computeTechniques() + " QUASI_LIVE_REVERSIBLE");
						GlobalDonePropertyPrinter gdpp = ((GlobalDonePropertyPrinter) doneProps);
						if (gdpp.shouldTrace()) {
							System.out.println("FORMULA " + examination + " TRUE TECHNIQUES " + gdpp.computeTechniques());
						}
						reader.getSPN().getProperties().clear();
					} else {
						// doneProps.put(REVERSIBLE, false, localDone.computeTechniques());
					}
			}

			

			return applyExhaustiveMethods(examination, reader, doneProps);

		} catch (GlobalPropertySolverException e) {
			return Optional.of(e.verdict);
		}
	}

	public Optional<Boolean> applyExhaustiveMethods(String examination, MccTranslator reader,
			DoneProperties doneProps) {
		SparsePetriNet spn = reader.getSPN();

		spn.simplifyLogic();
		spn.toPredicates();
		if (spn.testInInitial() > 0) {
			ReachabilitySolver.checkInInitial(spn, doneProps);
		}
		spn.removeConstantPlaces();
		spn.removeRedundantTransitions(false);
		spn.removeConstantPlaces();
		ReachabilitySolver.checkInInitial(spn, doneProps);
		spn.simplifyLogic();
		if (spn.isSafe()) {
			spn.assumeOneSafe();
		}
		ReachabilitySolver.checkInInitial(spn, doneProps);

		if (ONE_SAFE.equals(examination) && reader.getHLPN() == null) {
			executeOneSafeOnHLPNTest(doneProps, spn);
		}
		boolean hasCTL = spn.getProperties().stream().anyMatch(p -> p.getType() == PropertyType.CTL);
		// vire les prop triviales, utile ?
		if (!hasCTL)
			applyReachabilitySolver(reader, doneProps);

		spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
		
		int curprops = doneProps.size();
		if (!spn.getProperties().isEmpty() && !doneProps.isFinished()) {
			for (int i=1; i<=1000; i*=10) {
				if (hasCTL) {
					verifyWithSDD(reader, doneProps, "CTLFireability", 3*i);						
				} else {
					verifyWithSDD(reader, doneProps, "ReachabilityFireability", 3*i);
				}
				if (doneProps.isFinished()) {
					return Optional.of(doneProps.getValue(examination));
				}
				if (spn.getProperties().isEmpty()) {
					break;
				}
			}
			if (doneProps.size() < curprops && !hasCTL) {
				curprops = doneProps.size();
				
				// We could do this, but we need lower timeout.
				// applyReachabilitySolver(reader, doneProps);
			}
		}

		if (doneProps.containsKey(examination)) {
			return Optional.of(doneProps.getValue(examination));
		}

		spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName()));

		if (!spn.getProperties().isEmpty()) {
			System.out.println("Unable to solve all queries for examination " + examination + ". Remains :"
					+ spn.getProperties().size() + " assertions to prove.");
			return Optional.empty();
		} else {
			System.out.println(
					"Able to resolve query " + examination + " after proving " + doneProps.size() + " properties.");
			boolean success = isSuccess(doneProps, examination);

			GlobalDonePropertyPrinter gdpp = (GlobalDonePropertyPrinter) doneProps;
			if (gdpp.shouldTrace()) {
				if (success)
					System.out.println("FORMULA " + examination + " TRUE TECHNIQUES " + gdpp.computeTechniques());
				else
					System.out.println("FORMULA " + examination + " FALSE TECHNIQUES " + gdpp.computeTechniques());
			}
			return Optional.of(success);
		}
	}

	public void executeOneSafeOnHLPNTest(DoneProperties doneProps, SparsePetriNet spn) {
		long time = System.currentTimeMillis();
		List<Expression> toCheck = new ArrayList<>(spn.getPlaceCount());
		List<Integer> maxStruct = new ArrayList<>(spn.getPlaceCount());
		List<Integer> maxSeen = new ArrayList<>(spn.getPlaceCount());
		for (int pid = 0, e = spn.getPlaceCount(); pid < e; pid++) {
			toCheck.add(Expression.var(pid));
			maxStruct.add(-1);
			maxSeen.add(1);
		}
		// the invariants themselves
		IntMatrixCol invar = PetriSpotRunner.computeInvariants(spn, InvariantMode.PFLOWS, 60);

		UpperBoundsSolver.approximateStructuralBoundsUsingInvariants(spn, invar, toCheck, maxStruct);

		int d = 0;
		for (int pid = spn.getPlaceCount() - 1; pid >= 0; pid--) {
			if (maxStruct.get(pid) == 1) {
				doneProps.put("osplace_" + pid, true, "STRUCTURAL INVARIANTS");
				maxStruct.remove(pid);
				maxSeen.remove(pid);
				toCheck.remove(pid);
				d++;
			}
		}
		Logger.getLogger("fr.lip6.move.gal")
				.info("Rough structural analysis with invariants proved " + d + " places are one safe in "
						+ (System.currentTimeMillis() - time) + " ms (including invariant computation).");

		DeadlockTester.testOneSafeWithSMT(toCheck, spn, invar, doneProps, 120);

		spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
	}

	/**
	 * Hand the model to the exhaustive engines, with a PetriSpot walk on the
	 * cores they leave idle: both are single threaded, and the walk publishes
	 * into the same DoneProperties, so either may end the attempt.
	 */
	public static void verifyWithSDD(MccTranslator reader, DoneProperties doneProps, String examinationForITS,
			int timeout) {
		ParallelWalk walk = ParallelWalk.start(reader.getSPN(), doneProps, timeout);
		try {
			runExhaustiveEngines(reader, doneProps, examinationForITS, timeout);
		} finally {
			ParallelWalk.stop(walk);
		}
	}

	/** The decision diagrams, then LTSmin, on whatever properties remain open. */
	private static void runExhaustiveEngines(MccTranslator reader, DoneProperties doneProps,
			String examinationForITS, int timeout) {
		long time = System.currentTimeMillis();
		boolean wasInterrupted = false;
		if (reader.isDoITS())
		try {
			for (int i=0; i < 2 ; i++) {
				reader.rebuildSpecification(doneProps);
				reader.getSpec().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
				
				if (i==0) {
					reader.setLouvain(false);
					reader.setOrder(null);				
					reader.flattenSpec(false);
				} else {
					reader.setLouvain(true);
					reader.setOrder(null);
					reader.flattenSpec(true);
				}
				final IRunner itsRunner = new ITSRunner(examinationForITS, reader, true, false, reader.getFolder(), timeout,
						null);
				try {
					// decompose + simplify as needed
					itsRunner.configure(reader.getSpec(), doneProps);
					itsRunner.solve(new Ender() {
						public void killAll() {
							itsRunner.interrupt();
						}
					});
					itsRunner.join();
				} catch (InterruptedException e) {
					System.out.println("ITS runner timed out or was interrupted.");					
					wasInterrupted = true;
				} catch (IOException e) {
					System.out.println("ITS runner failed with exception " + e.getMessage());
					e.printStackTrace();
					wasInterrupted = true;
				} finally {
					if (itsRunner != null) {
						itsRunner.interrupt();
						try {
							itsRunner.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				reader.getSPN().getProperties().removeIf(p->doneProps.containsKey(p.getName()));
				if (reader.getSPN().getProperties().isEmpty() || doneProps.isFinished()) {
					break;
				}
			}
		} catch (OutOfMemoryError e) {
			reader.setSpec(null);
			System.out.println("ITSRunner failed with out of memory error.");
		}
		
		if (doneProps.isFinished() || wasInterrupted || reader.getSPN().getProperties().isEmpty()) {
			return;
		}
		timeout -= (System.currentTimeMillis() - time) / 1000;
		if (timeout <= 0) return;
		//CTL is not for LTSmin
		if (reader.isDoLTSMin())
		if (! reader.getSPN().getProperties().isEmpty() && !examinationForITS.startsWith("CTL")) {
			LTSminRunner ltsminRunner = new LTSminRunner(false, false, timeout, reader.getSPN().isSafe());
			try {
				ltsminRunner.configure(null, doneProps);
				ltsminRunner.setNet(reader.getSPN());
				// ltsminRunner.setShouldRetry(false);
				
				ltsminRunner.solve(new Ender() {
					public void killAll() {
						ltsminRunner.interrupt();
					}
				});
				
				ltsminRunner.join(timeout*1000);
				ltsminRunner.interrupt();
				ltsminRunner.join();
			} catch (IOException | InterruptedException e) {
				System.out.println("LTSmin runner failed with exception " + e.getMessage());
				wasInterrupted = true;								
				
				e.printStackTrace();				
			} finally {
				ltsminRunner.interrupt();
				try {
					ltsminRunner.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}		
		reader.getSPN().getProperties().removeIf(p->doneProps.containsKey(p.getName()));
	}

	private void applyReachabilitySolver(MccTranslator reader, DoneProperties doneProps) {
		if (!reader.getSPN().getProperties().isEmpty()) {
			try {
				ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
				ReachabilitySolver.applyReductions(reader, doneProps, -1);
			} catch (GlobalPropertySolvedException e) {
				e.printStackTrace();
			}
		}
	}

	private void buildProperties(String examination, PetriNet spn, DoneProperties doneProps) {
		switch (examination) {

		case STABLE_MARKING: {
			boolean[] todiscard = null;
			if (spn instanceof SparsePetriNet) {
				SparsePetriNet sspn = (SparsePetriNet) spn;
				int nbp = spn.getPlaceCount();
				sspn.removeConstantPlaces();
				if (sspn.getPlaceCount() < nbp) {
					doneProps.put(STABLE_MARKING, true, "CONSTANT_TEST");
				}
				todiscard = GraphSuffix.computeNonStablePlaces(sspn, doneProps);
			}
			GlobalAtoms.stableMarking(spn, todiscard);
			break;
		}
		case ONE_SAFE:
			GlobalAtoms.oneSafe(spn);
			break;
		case QUASI_LIVENESS:
			GlobalAtoms.quasiLiveness(spn, GlobalAtoms.dominatedTransitions(spn));
			break;
		case LIVENESS:
			GlobalAtoms.liveness(spn, GlobalAtoms.dominatedTransitions(spn));
		}
	}

	public boolean isSuccess(DoneProperties doneProperties, String examination) {
		if (examination.equals(ONE_SAFE) || examination.equals(QUASI_LIVENESS) || examination.equals(LIVENESS)) {
			// at least one false
			for (Entry<String, Boolean> e : doneProperties.entrySet()) {
				if (e.getValue() == false)
					return false;
			}
			return true;

		} else if (examination.equals(STABLE_MARKING)) {
			// at least one true
			for (Entry<String, Boolean> e : doneProperties.entrySet()) {
				if (e.getValue() == true)
					return true;
			}
			return false;
		}

		return false;

	}
}
