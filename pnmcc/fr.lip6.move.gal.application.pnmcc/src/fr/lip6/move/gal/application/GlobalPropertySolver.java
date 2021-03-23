package fr.lip6.move.gal.application;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map.Entry;

import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.HLPlace;
import fr.lip6.move.gal.structural.NoDeadlockExists;
import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparseHLPetriNet;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class GlobalPropertySolver {

	private static final String LIVENESS = "Liveness";

	private static final String QUASI_LIVENESS = "QuasiLiveness";

	private static final String STABLE_MARKING = "StableMarking";

	private static final String ONE_SAFE = "OneSafe";

	private String solverPath;

	private SparsePetriNet spn;

	public GlobalPropertySolver(String solverPath) {
		this.solverPath = solverPath;
	}


	// ****  solving global properties ****
	
	void buildOneSafeProperty(PetriNet spn) {

		for (int pid = 0; pid < spn.getPlaceCount(); pid++) {

			//in case colored models
			if (spn instanceof SparseHLPetriNet) {
				SparseHLPetriNet hlpn = (SparseHLPetriNet) spn;
				if (pid >= hlpn.getPlaces().size())
					break;
			}

			Expression pInfOne = Expression.op(Op.LEQ,
					Expression.nop(Op.CARD, Collections.singletonList(Expression.var(pid))), Expression.constant(1));
			// unary op ignore right
			Expression ag = Expression.op(Op.AG, pInfOne, null);
			Property oneSafeProperty = new Property(ag, PropertyType.INVARIANT, "place_" + pid);
			spn.getProperties().add(oneSafeProperty);
		}

	}

	void buildStableMarkingProperty(PetriNet spn) {

		for (int pid = 0; pid < spn.getPlaceCount(); pid++) {
			int sum = 0;

			//in case colored models
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
			Property stableMarkingProperty = new Property(ef, PropertyType.INVARIANT, "place_" + pid);
			spn.getProperties().add(stableMarkingProperty);
		}
	}

	void buildQuasiLivenessProperty(PetriNet spn) {
		for (int tid = 0; tid < spn.getTransitionCount(); tid++) {
			Expression quasiLive = Expression.nop(Op.ENABLED, Collections.singletonList(Expression.trans(tid)));
			Expression ef = Expression.op(Op.EF, quasiLive, null);
			Property quasiLivenessProperty = new Property(ef, PropertyType.INVARIANT, "transition_" + tid);
			spn.getProperties().add(quasiLivenessProperty);
		}
	}
	
	void builLivenessProperty(PetriNet spn) {
		for (int tid = 0; tid < spn.getTransitionCount(); tid++) {
			Expression live = Expression.nop(Op.ENABLED, Collections.singletonList(Expression.trans(tid)));
			Expression ef = Expression.op(Op.AG, live, null);
			Property LivenessProperty = new Property(ef, PropertyType.INVARIANT, "transition_" + tid);
			spn.getProperties().add(LivenessProperty);
		}
	}
	
	
	

	public boolean solveProperty(String examination, MccTranslator reader) {

		// initialize a shared container to detect help detect termination in portfolio
		// case
		GlobalDonePropertyPrinter doneProps = new GlobalDonePropertyPrinter(examination);

		if (reader.getHLPN() != null) {
			buildProperties(examination, reader.getHLPN());
			
			if (ONE_SAFE.equals(examination)) {
				for (HLPlace place : reader.getHLPN().getPlaces()) {
					int[] initial = place.getInitial();
					int sum = Arrays.stream(initial).sum();
					if (sum > 1) {
						System.out.println("FORMULA " + examination + " FALSE TECHNIQUES STRUCTURAL INITIAL_STATE CPN_APPROX");			
						return true;
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
				return true;
			}
			isSafe = true;
		}
		if (QUASI_LIVENESS.equals(examination)) {
			reader.createSPN(false, false);
		} else {
			reader.createSPN();
		}
		spn = reader.getSPN();

		// switching examination
		if (reader.getHLPN() == null)
			buildProperties(examination, spn);

		spn.simplifyLogic();
		spn.toPredicates();
		spn.testInInitial();
		spn.removeConstantPlaces();
		spn.removeRedundantTransitions(false);
		spn.removeConstantPlaces();
		spn.simplifyLogic();
		if (isSafe) {
			spn.assumeOneSafe();
		}

		// vire les prop triviales, utile ?
		if (!spn.getProperties().isEmpty()) {
			try {
				ReachabilitySolver.checkInInitial(spn, doneProps);
				ReachabilitySolver.applyReductions(reader, doneProps, solverPath, isSafe);
			} catch (NoDeadlockExists|DeadlockFound e) {
				e.printStackTrace();
				return false;
			} catch (GlobalPropertySolverException e) {
				return true;
			}
		}
		
		spn.getProperties().removeIf(p -> ! doneProps.containsKey(p.getName()));
		
		if (!spn.getProperties().isEmpty()) {
			System.out.println("Unable to solve all queries for examination "+examination + ". Remains :"+ spn.getProperties().size() + " assertions to prove.");
			return false;
		} else {
			System.out.println("Able to resolve query "+examination+ " after proving " + doneProps.size() + " properties.");
		}
		boolean success = isSuccess(doneProps, examination);
		if (success)
			System.out.println("FORMULA " + examination + " TRUE TECHNIQUES " + doneProps.computeTechniques());
		else
			System.out.println("FORMULA " + examination + " FALSE TECHNIQUES " + doneProps.computeTechniques());

		return true;
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
			buildQuasiLivenessProperty(spn);
		}
	}

	public boolean isSuccess(DoneProperties doneProperties, String examination) {
		if (examination.equals(ONE_SAFE) || examination.equals(QUASI_LIVENESS)  || examination.equals(LIVENESS)  ) {
			for (Entry<String, Boolean> e : doneProperties.entrySet()) {
				if (e.getValue() == false)
					return false;
			}
			return true;

		} else if (examination.equals(STABLE_MARKING)) {
			for (Entry<String, Boolean> e : doneProperties.entrySet()) {
				if (e.getValue() == true)
					return true;

			}
			return false;
		}

		return false;

	}
}
