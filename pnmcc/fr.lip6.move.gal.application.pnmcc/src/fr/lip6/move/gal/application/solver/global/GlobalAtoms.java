package fr.lip6.move.gal.application.solver.global;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.hlpn.SparseHLPetriNet;
import fr.lip6.move.gal.util.IntMatrixCol;

/**
 * The atoms of the global examinations: one property per place or per
 * transition, added to the net's property list. A discard mask, when given,
 * skips the objects a caller has already settled. Each builder fixes the
 * name scheme of its atoms; the constants below are the prefixes.
 *
 * Pure builders: no verdict is drawn here, callers roll the atoms up.
 */
public class GlobalAtoms {

	public static final String ONE_SAFE_PREFIX = "osplace_";
	public static final String STABLE_PREFIX = "smplace_";
	public static final String QUASI_LIVE_PREFIX = "qltransition_";
	public static final String LIVE_PREFIX = "ltransition_";
	public static final String REVERSIBLE = "Reversible";

	private static final int DEBUG = 0;

	private GlobalAtoms() {
	}

	/**
	 * One AG(p <= 1) per place, except places a token supply argument already
	 * proves one safe. An initial marking above one settles the whole question
	 * with a single constant false property.
	 */
	public static void oneSafe(PetriNet pn) {
		Set<Integer> osPlaces = new HashSet<>();
		Set<Integer> oneFireTrans = new HashSet<>();

		if (pn instanceof SparsePetriNet) {
			SparsePetriNet spn = (SparsePetriNet) pn;

			IntMatrixCol tflowTP = spn.getFlowTP().transpose();
			IntMatrixCol tflowPT = spn.getFlowPT().transpose();
			List<Integer> marks = spn.getMarks();

			// test initial state first
			for (int mark : marks) {
				if (mark > 1) {
					// not one safe, definitely
					System.out.println("Due to initial marking, net is not one safe.");
					Property oneSafeProperty = new Property(Expression.constant(false), PropertyType.INVARIANT, "OneSafe");
					pn.getProperties().add(oneSafeProperty);
					return;
				}
			}
			// from here on, m0(p) is 0 or 1 for every place p

			boolean changed;
			do {
				changed = false;
				// look for places whose total token supply (initial + producible) is at most one
				// => they are one safe, and their consumers can fire at most once
				for (int pid=0 ; pid < spn.getPlaceCount() ; pid++) {
					if (osPlaces.contains(pid)) {
						continue;
					}
					SparseIntArray feed = tflowTP.getColumn(pid);
					int m0 = marks.get(pid);
					// cannot be fed at all : supply is just the initial marking, which we checked is <= 1
					// or is initially empty and can only be fed exactly once at most, by a single token
					// NB : a place that is both initially marked and feedable can reach 2, it is excluded here
					// NB : the feeding arc weight must be 1, a single firing of weight k puts k tokens in
					if ( (feed.size()==0)
							|| (feed.size()==1 && m0==0 && feed.valueAt(0)==1 && oneFireTrans.contains(feed.keyAt(0))) ) {
						changed = true;
						osPlaces.add(pid);
						// consumers from this place are hence single fireable :
						// they each need at least one token from a supply of at most one
						SparseIntArray cons = tflowPT.getColumn(pid);
						for (int i=0, ie=cons.size() ;i< ie; i++) {
							int tid = cons.keyAt(i);
							oneFireTrans.add(tid);
						}
					}
				}
			} while (changed);

			if (DEBUG==2) FlowPrinter.drawNet(spn, "Finally : Detected single feed",osPlaces,oneFireTrans);
			if (! osPlaces.isEmpty()) {
				System.out.println("Structural unfed/single firing approximation deduced that "+ osPlaces.size()+ "/" + spn.getPlaceCount() + " places are one safe.");
			}
		}

		for (int pid = 0; pid < pn.getPlaceCount(); pid++) {
			// in case colored models
			if (pn instanceof SparseHLPetriNet) {
				SparseHLPetriNet hlpn = (SparseHLPetriNet) pn;
				if (pid >= hlpn.getPlaces().size())
					break;
			}
			if (!osPlaces.contains(pid)) {
				Expression pInfOne = Expression.op(Op.LEQ,
						Expression.nop(Op.CARD, Collections.singletonList(Expression.var(pid))), Expression.constant(1));
				// unary op ignore right
				Expression ag = Expression.op(Op.AG, pInfOne, null);
				Property oneSafeProperty = new Property(ag, PropertyType.INVARIANT, ONE_SAFE_PREFIX + pid);
				pn.getProperties().add(oneSafeProperty);
			}
		}
	}

	/**
	 * One AG(p == m0(p)) per place not in the discard mask. A null mask
	 * discards nothing.
	 */
	public static void stableMarking(PetriNet spn, boolean[] todiscard) {
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
				Property stableMarkingProperty = new Property(ef, PropertyType.INVARIANT, STABLE_PREFIX + pid);
				spn.getProperties().add(stableMarkingProperty);
			}
		}
	}

	/** One EF enabled(t) per transition not in the discard mask. */
	public static void quasiLiveness(PetriNet spn, boolean[] todiscard) {
		for (int tid = 0; tid < spn.getTransitionCount(); tid++) {
			if (todiscard == null || !todiscard[tid]) {
				Expression quasiLive = Expression.nop(Op.ENABLED, Collections.singletonList(Expression.trans(tid)));
				Expression ef = Expression.op(Op.EF, quasiLive, null);
				Property quasiLivenessProperty = new Property(ef, PropertyType.INVARIANT, QUASI_LIVE_PREFIX + tid);
				spn.getProperties().add(quasiLivenessProperty);
			}
		}
	}

	/** One AG EF enabled(t) per transition not in the discard mask, as CTL. */
	public static void liveness(PetriNet spn, boolean[] todiscard) {
		for (int tid = 0; tid < spn.getTransitionCount(); tid++) {
			if (todiscard == null || !todiscard[tid]) {
				Expression live = Expression.nop(Op.ENABLED, Collections.singletonList(Expression.trans(tid)));
				Expression ef = Expression.op(Op.AG, Expression.op(Op.EF, live, null), null);
				Property LivenessProperty = new Property(ef, PropertyType.CTL, LIVE_PREFIX + tid);
				spn.getProperties().add(LivenessProperty);
			}
		}
	}

	/**
	 * A single CTL property, AG EF (m == m0): the initial state is a home
	 * state. On a conservative net the empty places are implied by the marked
	 * ones and left out of the conjunction.
	 */
	public static void reversible(SparsePetriNet spn) {
		boolean conservative = spn.isConservative();
		if (conservative) {
			System.out.println("Net is conservative; using simplified expression for initial state.");
		}
		List<Expression> places = new ArrayList<>();
		for (int p=0; p < spn.getPlaceCount() ; p++) {
			int mark = spn.getMarks().get(p);
			if (!conservative || mark != 0) {
				Expression initialState = Expression.op(Op.EQ, Expression.var(p), Expression.constant(mark));
				places.add(initialState);
			}
		}
		spn.getProperties().add(new Property(Expression.nop(Op.AG, Expression.nop(Op.EF, Expression.nop(Op.AND,places))), PropertyType.CTL,REVERSIBLE));
	}

	/**
	 * Marks transitions whose precondition dominates another's on some shared
	 * place: if t' needs no more than t on every place, t enabled implies t'
	 * enabled, and t' can be left out of a quasi-liveness or liveness cohort
	 * whose verdict is the conjunction. Not a valid discard when every atom
	 * must be answered.
	 */
	public static boolean[] dominatedTransitions(PetriNet pn) {
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
}
