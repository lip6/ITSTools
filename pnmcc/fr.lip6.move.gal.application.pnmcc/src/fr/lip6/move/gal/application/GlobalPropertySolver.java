package fr.lip6.move.gal.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import java.util.Map.Entry;
import java.util.Optional;

import android.util.SparseIntArray;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.HLPlace;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SiphonComputer;
import fr.lip6.move.gal.structural.SparseHLPetriNet;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.smt.DeadlockTester;
import fr.lip6.move.gal.util.IntMatrixCol;

public class GlobalPropertySolver {

	private static final String REACHABILITY_DEADLOCK = "ReachabilityDeadlock";

	private static final String LIVENESS = "Liveness";

	private static final String QUASI_LIVENESS = "QuasiLiveness";

	private static final String STABLE_MARKING = "StableMarking";

	private static final String ONE_SAFE = "OneSafe";

	private static final int DEBUG = 0;

	private final String solverPath;

	public GlobalPropertySolver(String solverPath) {
		this.solverPath = solverPath;
	}

	// **** solving global properties ****

	void buildOneSafeProperty(PetriNet spn) {

		for (int pid = 0; pid < spn.getPlaceCount(); pid++) {

			// in case colored models
			if (spn instanceof SparseHLPetriNet) {
				SparseHLPetriNet hlpn = (SparseHLPetriNet) spn;
				if (pid >= hlpn.getPlaces().size())
					break;
			}

			Expression pInfOne = Expression.op(Op.LEQ,
					Expression.nop(Op.CARD, Collections.singletonList(Expression.var(pid))), Expression.constant(1));
			// unary op ignore right
			Expression ag = Expression.op(Op.AG, pInfOne, null);
			Property oneSafeProperty = new Property(ag, PropertyType.INVARIANT, "osplace_" + pid);
			spn.getProperties().add(oneSafeProperty);
		}

	}

	void buildStableMarkingProperty(PetriNet spn, DoneProperties doneProps) {
		boolean[] todiscard = null;
		if (spn instanceof SparsePetriNet) {
			SparsePetriNet sspn = (SparsePetriNet) spn;
			//todiscard = computeNonStablePlaces(sspn, doneProps);
			todiscard = GraphSuffix.computeNonStablePlaces(sspn, doneProps);

		}

		for (int pid = 0; pid < spn.getPlaceCount(); pid++) {
			int sum = 0;
			// in case colored models
			if (spn instanceof SparseHLPetriNet) {
				SparseHLPetriNet hlpn = (SparseHLPetriNet) spn;
				if (pid >= hlpn.getPlaces().size())
					break;
				sum = Arrays.stream(hlpn.getPlaces().get(pid).getInitial()).sum();
			} else if (spn instanceof SparsePetriNet) {
				ISparsePetriNet sparse = (ISparsePetriNet) spn;
				sum = sparse.getMarks().get(pid);
			}

			if (todiscard != null && todiscard[pid]) {
				continue;
			} else {
				Expression stable = Expression.op(Op.EQ,
						Expression.nop(Op.CARD, Collections.singletonList(Expression.var(pid))),
						Expression.constant(sum));
				Expression ef = Expression.op(Op.AG, stable, null);
				Property stableMarkingProperty = new Property(ef, PropertyType.INVARIANT, "smplace_" + pid);
				spn.getProperties().add(stableMarkingProperty);
			}
		}
	}

	private boolean[] computeNonStablePlaces(SparsePetriNet spn, DoneProperties doneProps) {
		boolean [] nonstable = new boolean[spn.getPlaceCount()];
		long time = System.currentTimeMillis();
		// extract simple transitions to a PxP matrix
		int nbP = spn.getPlaceCount();
		IntMatrixCol graph = new IntMatrixCol(nbP,nbP);
		
		IntMatrixCol flowPT = spn.getFlowPT();
		IntMatrixCol flowTP = spn.getFlowTP();
		
		for (int tid = 0; tid < flowPT.getColumnCount() ; tid++) {
			SparseIntArray hPT = flowPT.getColumn(tid);
			SparseIntArray hTP = flowTP.getColumn(tid);
			if (hPT.size() == 1 && hTP.size() == 1 && hPT.valueAt(0)==1 && hTP.valueAt(0)==1) {				
				graph.set(hTP.keyAt(0), hPT.keyAt(0), 1);
			}						
		}
		
		List<List<Integer>> sccs = Tarjan.searchForSCC(graph);
		sccs.removeIf(s -> s.size() == 1);
				
		int reduced = 0;
		// so we have SCC, any SCC with tokens initially in it  => all places in the SCC are NON STABLE
		for (List<Integer> scc : sccs) {
			boolean isMarked = false;
			for (int pid: scc) {
				if (spn.getMarks().get(pid) > 0) {
					isMarked=true;
					break;
				}
			}
			if (isMarked) {
				for (int pid: scc) {
					nonstable [pid] = true;
					reduced ++;
				}
			}
		}
		
		if (reduced >0) {
			System.out.println("SCC test allowed to assert that "+reduced+" places are NOT stable.");
			doneProps.put("SccTest", false, "TRIVIAL_MARKED_SCC_TEST");
		}
		return nonstable;
	}

	void buildQuasiLivenessProperty(PetriNet spn) {
		boolean[] todiscard = computeDominatedTransitions(spn);
		for (int tid = 0; tid < spn.getTransitionCount(); tid++) {
			if (!todiscard[tid]) {
				Expression quasiLive = Expression.nop(Op.ENABLED, Collections.singletonList(Expression.trans(tid)));
				Expression ef = Expression.op(Op.EF, quasiLive, null);
				Property quasiLivenessProperty = new Property(ef, PropertyType.INVARIANT, "qltransition_" + tid);
				spn.getProperties().add(quasiLivenessProperty);
			}
		}
	}

	private boolean[] computeDominatedTransitions(PetriNet pn) {
		boolean[] todiscard = new boolean[pn.getTransitionCount()];
		int discards = 0;
		if (pn instanceof ISparsePetriNet) {
			ISparsePetriNet spn = (ISparsePetriNet) pn;
			IntMatrixCol tflowPT = spn.getFlowPT().transpose();

			for (int pid = 0, pide = spn.getPlaceCount(); pid < pide; pid++) {
				SparseIntArray tpt = tflowPT.getColumn(pid);
				List<Integer> consumers = Arrays.stream(tpt.copyKeys()).boxed().collect(Collectors.toList());
				consumers.sort((i, j) -> -Integer.compare(spn.getFlowPT().getColumn(i).size(),
						spn.getFlowPT().getColumn(j).size()));
				for (int i = 0; i < consumers.size(); i++) {
					if (todiscard[consumers.get(i)])
						continue;
					for (int j = i + 1; j < consumers.size(); j++) {
						if (todiscard[consumers.get(j)])
							continue;
						if (SparseIntArray.greaterOrEqual(spn.getFlowPT().getColumn(consumers.get(i)),
								spn.getFlowPT().getColumn(consumers.get(j)))) {
							todiscard[consumers.get(j)] = true;
							discards++;
						} else if (SparseIntArray.greaterOrEqual(spn.getFlowPT().getColumn(consumers.get(j)),
								spn.getFlowPT().getColumn(consumers.get(i)))) {
							todiscard[consumers.get(i)] = true;
							discards++;
							break;
						}
					}
				}
			}
		}
		if (discards > 0) {
			System.out.println("Discarding " + discards + " transitions out of " + todiscard.length + ". Remains "
					+ (todiscard.length - discards));
		}
		return todiscard;
	}

	void buildLivenessProperty(PetriNet spn) {
		boolean[] todiscard = computeDominatedTransitions(spn);
		for (int tid = 0; tid < spn.getTransitionCount(); tid++) {
			if (!todiscard[tid]) {
				Expression live = Expression.nop(Op.ENABLED, Collections.singletonList(Expression.trans(tid)));
				Expression ef = Expression.op(Op.AG, Expression.op(Op.EF, live, null), null);
				Property LivenessProperty = new Property(ef, PropertyType.CTL, "ltransition_" + tid);
				spn.getProperties().add(LivenessProperty);
			}
		}
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
					SparsePetriNet spn = reader.getHLPN().unfold(en);
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
					buildLivenessProperty(reader.getHLPN());
				else
					buildLivenessProperty(reader.getSPN());
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
						REACHABILITY_DEADLOCK, null, solverPath, reader.copy(),
						new GlobalDonePropertyPrinter(REACHABILITY_DEADLOCK, false));
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
				Optional<Boolean> qlResult = solveProperty(QUASI_LIVENESS, readercopy,
						new GlobalDonePropertyPrinter(QUASI_LIVENESS, false));

				if (qlResult.isPresent() && !qlResult.get()) {
					System.out.println("FORMULA " + examination + " FALSE TECHNIQUES QUASILIVENESS_TEST");
					return Optional.of(false);
				} else if (reader.getHLPN() != null) {
					reader.getHLPN().getProperties().clear();
					reader.getHLPN().getProperties().addAll(hlpnprops);
				}
			}
		}

		return solveProperty(examination, reader, doneProps);
	}

	public boolean executeSCCLivenessTest(ISparsePetriNet spn, List<List<Integer>> en) {
		// recursive tarjan
		// new Tarjan().parsePetriNet(spn);
		// stack based tarjan
		Set<Integer> scc = Tarjan.computePlacesInNonTrivialSCC(spn);
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
				if (reader.getSPN().isSafe()) {
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
						ReachabilitySolver.applyReductions(sr, ReductionType.LIVENESS, solverPath, true, true);
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

			// vire les prop triviales, utile ?
			if (!LIVENESS.equals(examination))
				applyReachabilitySolver(reader, doneProps);

			spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName()));

			if (!spn.getProperties().isEmpty() && !doneProps.isFinished()) {
				for (int i=1; i<=1000; i*=10) {
					if (LIVENESS.equals(examination)) {
						verifyWithSDD(reader, doneProps, "CTLFireability", solverPath, 3*i);						
					} else {
						verifyWithSDD(reader, doneProps, "ReachabilityFireability", solverPath, 3*i);
					}
					if (doneProps.isFinished()) {
						return Optional.of(doneProps.getValue(examination));
					}
					if (spn.getProperties().isEmpty()) {
						break;
					}
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

		} catch (GlobalPropertySolverException e) {
			return Optional.of(e.verdict);
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
		Set<SparseIntArray> invar;
		{
			// effect matrix
			IntMatrixCol sumMatrix = IntMatrixCol.sumProd(-1, spn.getFlowPT(), 1, spn.getFlowTP());
			invar = InvariantCalculator.computePInvariants(sumMatrix, spn.getPnames(), false, 60);
		}

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

		DeadlockTester.testOneSafeWithSMT(toCheck, spn, invar, doneProps, solverPath, 10);

		spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
	}

	public static void verifyWithSDD(MccTranslator reader, DoneProperties doneProps, String examinationForITS,
			String solverPath, int timeout) {
		
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
			try {
				// decompose + simplify as needed
				IRunner itsRunner = new ITSRunner(examinationForITS, reader, true, false, reader.getFolder(), timeout,
						null);
				itsRunner.configure(reader.getSpec(), doneProps);
				itsRunner.solve(new Ender() {
					public void killAll() {
						itsRunner.interrupt();
					}
				});
				itsRunner.join();
			} catch (IOException | InterruptedException e) {
				System.out.println("ITS runner failed with exception " + e.getMessage());
				e.printStackTrace();
			}
			reader.getSPN().getProperties().removeIf(p->doneProps.containsKey(p.getName()));
			if (reader.getSPN().getProperties().isEmpty() || doneProps.isFinished()) {
				break;
			}
		}		
		if (doneProps.isFinished()) {
			return;
		}
		//CTL is not for LTSmin
		if (! reader.getSPN().getProperties().isEmpty() && !examinationForITS.startsWith("CTL")) {
			LTSminRunner ltsminRunner = new LTSminRunner(solverPath, Solver.Z3, false, false, timeout,
					reader.getSPN().isSafe());
			try {
				ltsminRunner.configure(reader.getSpec(), doneProps);
				ltsminRunner.setNet(reader.getSPN());
				
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
				e.printStackTrace();
			}
		}
		reader.getSPN().getProperties().removeIf(p->doneProps.containsKey(p.getName()));
	}

	private void applyReachabilitySolver(MccTranslator reader, DoneProperties doneProps) {
		if (!reader.getSPN().getProperties().isEmpty()) {
			try {
				ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
				ReachabilitySolver.applyReductions(reader, doneProps, solverPath);
			} catch (GlobalPropertySolvedException e) {
				e.printStackTrace();
			}
		}
	}

	private void buildProperties(String examination, PetriNet spn, DoneProperties doneProps) {
		switch (examination) {

		case STABLE_MARKING:
			buildStableMarkingProperty(spn,doneProps);
			break;
		case ONE_SAFE:
			buildOneSafeProperty(spn);
			break;
		case QUASI_LIVENESS:
			buildQuasiLivenessProperty(spn);
			break;
		case LIVENESS:
			buildLivenessProperty(spn);
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
