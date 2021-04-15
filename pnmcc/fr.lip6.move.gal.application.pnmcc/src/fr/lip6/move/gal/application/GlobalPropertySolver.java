package fr.lip6.move.gal.application;

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
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.HLPlace;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.NoDeadlockExists;
import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SiphonComputer;
import fr.lip6.move.gal.structural.SparseHLPetriNet;
import fr.lip6.move.gal.structural.SparsePetriNet;
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

	void buildStableMarkingProperty(PetriNet spn) {

		for (int pid = 0; pid < spn.getPlaceCount(); pid++) {
			int sum = 0;

			// in case colored models
			if (spn instanceof SparseHLPetriNet) {
				SparseHLPetriNet hlpn = (SparseHLPetriNet) spn;
				if (pid >= hlpn.getPlaces().size())
					break;
				sum = Arrays.stream(hlpn.getPlaces().get(pid).getInitial()).sum();
			} else if (spn instanceof SparsePetriNet) {
				SparsePetriNet sparse = (SparsePetriNet) spn;
				sum = sparse.getMarks().get(pid);
			}

			Expression stable = Expression.op(Op.EQ,
					Expression.nop(Op.CARD, Collections.singletonList(Expression.var(pid))), Expression.constant(sum));
			Expression ef = Expression.op(Op.AG, stable, null);
			Property stableMarkingProperty = new Property(ef, PropertyType.INVARIANT, "smplace_" + pid);
			spn.getProperties().add(stableMarkingProperty);
		}
	}

	void buildQuasiLivenessProperty(PetriNet spn) {
		for (int tid = 0; tid < spn.getTransitionCount(); tid++) {
			Expression quasiLive = Expression.nop(Op.ENABLED, Collections.singletonList(Expression.trans(tid)));
			Expression ef = Expression.op(Op.EF, quasiLive, null);
			Property quasiLivenessProperty = new Property(ef, PropertyType.INVARIANT, "qltransition_" + tid);
			spn.getProperties().add(quasiLivenessProperty);
		}
	}

	void buildLivenessProperty(PetriNet spn) {
		for (int tid = 0; tid < spn.getTransitionCount(); tid++) {
			Expression live = Expression.nop(Op.ENABLED, Collections.singletonList(Expression.trans(tid)));
			Expression ef = Expression.op(Op.AG, Expression.op(Op.EF, live, null), null);
			Property LivenessProperty = new Property(ef, PropertyType.CTL, "ltransition_" + tid);
			spn.getProperties().add(LivenessProperty);
		}
	}

	public Optional<Boolean> solveProperty(String examination, MccTranslator reader) {
		// initialize a shared container to detect help detect termination in portfolio
		// case
		GlobalDonePropertyPrinter doneProps = new GlobalDonePropertyPrinter(examination, true);
		return solveProperty(examination, reader, doneProps);
	}
	
	public Optional<Boolean> solveProperty(String examination, MccTranslator reader, DoneProperties doneProps) {

		if (LIVENESS.equals(examination)) {

			boolean isCol = (reader.getHLPN() != null);

			{ // for COL : testing on skeleton
				if (isCol) {
					
					{
						SparsePetriNet skel = reader.getHLPN().skeleton();

						if (!executeSCCLivenessTest(skel,null)) {
							System.out.println("FORMULA Liveness FALSE TECHNIQUES STRUCTURAL SKELETON_TEST");
							return Optional.of(false);
						}
					}
					{
						
						List<List<Integer>> en = new ArrayList<>(reader.getHLPN().getTransitionCount());
						SparsePetriNet spn = reader.getHLPN().unfold(en);
						reader.setSpn(spn, false);
						if (!executeSCCLivenessTest(spn,en)) {
							System.out.println("FORMULA Liveness FALSE TECHNIQUES STRUCTURAL SKELETON_TEST");
							return Optional.of(false);
						}
						
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
				if (!isCol && !executeSCCLivenessTest(reader.getSPN(),null)) {
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
						REACHABILITY_DEADLOCK, null, solverPath, reader.copy(), reader.isSafeNet(),
						new GlobalDonePropertyPrinter(REACHABILITY_DEADLOCK, false));
				if (deadlock.isPresent() && deadlock.get()) {
					System.out.println("FORMULA " + examination + " FALSE TECHNIQUES STRUCTURAL DEADLOCK_TEST");
					return Optional.of(false);
				}

			}

			{
				// test for NOT QuasiLiveness ==> NOT Liveness
				MccTranslator readercopy = reader.copy();
				Optional<Boolean> qlResult = solveProperty(QUASI_LIVENESS, readercopy, new GlobalDonePropertyPrinter(QUASI_LIVENESS, false));
				
				if (qlResult.isPresent() && ! qlResult.get()) {
					System.out.println("FORMULA " + examination + " FALSE TECHNIQUES QUASILIVENESS_TEST");
					return Optional.of(false);
				}
			}
		}


		return solveProperty(examination, reader, doneProps);
	}

	public boolean executeSCCLivenessTest(SparsePetriNet spn, List<List<Integer>> en) {
		// recursive tarjan
		// new Tarjan().parsePetriNet(spn);
		// stack based tarjan
		Set<Integer> scc = Tarjan.computePlacesInNonTrivialSCC(spn);
		if (DEBUG > 2)
			FlowPrinter.drawNet(spn, "SCC TARJAN", scc, Collections.emptySet());

		boolean isLive = true;

		if (en == null) {
			// si une place de P \ P_scc a des transitions qui consomment dedans, => NOT Live
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
			// Pour chaque transition (COL ou pas),			// Sinon => NOT live
			// si une place de P \ P_scc a des transitions qui consomment dedans, => NOT Live
			if (scc.size() < spn.getPlaceCount()) {
				// Pour chaque transition (COL ou pas),	
				for (int tcol = 0, tcole = en.size() ; tcol < tcole ; tcol++) {
					boolean isOK = false;
					// il existe une instance de la transition (elle même seule instance dans le cas non COL), 					
					for (int tid : en.get(tcol)) {
						SparseIntArray pt = spn.getFlowPT().getColumn(tid);
						// dont toutes les places précondition sont dans P_scc
						boolean instanceOK = true;
						for (int i=0;i < pt.size() ; i++) {
							int pid = pt.keyAt(i);
							// la transition consomme dans une place hors SCC => cette instance de transition est not live
							if (! scc.contains(pid)) {
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

	private Optional<Boolean> solveProperty(String examination, MccTranslator reader,
			GlobalDonePropertyPrinter doneProps) {
		System.out.println("HLPN NULL == " + reader.getHLPN() == null);

		if (reader.getHLPN() != null) {

			buildProperties(examination, reader.getHLPN());

			if (ONE_SAFE.equals(examination)) {
				for (HLPlace place : reader.getHLPN().getPlaces()) {
					int[] initial = place.getInitial();
					int sum = Arrays.stream(initial).sum();
					if (sum > 1) {
						System.out.println(
								"FORMULA " + examination + " FALSE TECHNIQUES STRUCTURAL INITIAL_STATE CPN_APPROX");
						return Optional.of(false);
					}
				}
			}

		}

		boolean isSafe = false;
		// load "known" stuff about the model
		if (reader.isSafeNet()) {
			// NUPN implies one safe
			if (examination.equals(ONE_SAFE)) {
				System.out.println("FORMULA " + examination + " TRUE TECHNIQUES STRUCTURAL");
				return Optional.of(true);
			}
			isSafe = true;
		}
		if (QUASI_LIVENESS.equals(examination) || STABLE_MARKING.equals(examination)) {
			reader.createSPN(false, false);
		} else {
			reader.createSPN();
		}
		SparsePetriNet spn = reader.getSPN();

		// switching examination
		if (reader.getHLPN() == null)
			buildProperties(examination, spn);

		try {
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
			if (isSafe) {
				spn.assumeOneSafe();
			}
			ReachabilitySolver.checkInInitial(spn, doneProps);


			if (ONE_SAFE.equals(examination) && reader.getHLPN() == null) {
				executeOneSafeOnHLPNTest(doneProps, isSafe,spn);
			}

			// vire les prop triviales, utile ?
			if (! LIVENESS.equals(examination))
				applyReachabilitySolver(reader, doneProps, isSafe);

			spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName()));

		} catch (GlobalPropertySolverException e) {
			return Optional.of(e.verdict);
		}
		
		if (! spn.getProperties().isEmpty()) {
			if (LIVENESS.equals(examination)) {
				verifyWithCTL(reader, doneProps, "CTLFireability");							
			} else {
				verifyWithCTL(reader, doneProps, "ReachabilityFireability");
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
			if (success)
				System.out.println("FORMULA " + examination + " TRUE TECHNIQUES " + doneProps.computeTechniques());
			else
				System.out.println("FORMULA " + examination + " FALSE TECHNIQUES " + doneProps.computeTechniques());

			return Optional.of(success);
		}
	}

	public void executeOneSafeOnHLPNTest(GlobalDonePropertyPrinter doneProps, boolean isSafe, SparsePetriNet spn) {
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
			invar = InvariantCalculator.computePInvariants(sumMatrix, spn.getPnames());
		}

		long time = System.currentTimeMillis();
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
		Logger.getLogger("fr.lip6.move.gal").info("Rough structural analysis with invriants proved " + d
				+ " places are one safe in " + (System.currentTimeMillis() - time) + " ms.");

		DeadlockTester.testOneSafeWithSMT(toCheck, spn, invar, doneProps, solverPath, isSafe, 10);

		spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
	}

	private void verifyWithCTL(MccTranslator reader, DoneProperties doneProps, String examinationForITS) {
		reader.rebuildSpecification(doneProps);
		reader.getSpec().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
		reader.setLouvain(true);
		reader.setOrder(null);
		reader.flattenSpec(true);

		// timeout 1000 secs ?
		int timeout = 1000;
		
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
			System.out.println("ITS runner failed with exception "+ e.getMessage());
			e.printStackTrace();
		}
	}


	private void applyReachabilitySolver(MccTranslator reader, GlobalDonePropertyPrinter doneProps, boolean isSafe) {
		reader.createSPN();
		if (!reader.getSPN().getProperties().isEmpty()) {
			try {
				ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
				ReachabilitySolver.applyReductions(reader, doneProps, solverPath, isSafe);
			} catch (NoDeadlockExists | DeadlockFound e) {
				e.printStackTrace();
			} 
		}
	}

	private void buildProperties(String examination, PetriNet spn) {
		switch (examination) {

		case STABLE_MARKING:
			buildStableMarkingProperty(spn);
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
