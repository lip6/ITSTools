package fr.lip6.move.gal.application.solver.abstraction;

import java.util.BitSet;
import java.util.List;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;

/**
 * Counterexample-guided refinement over place projections (ABSTRACTION.md):
 * start from the cheapest semi-flows covering the target places, check the
 * property on the projection, refine with the dropped places a spurious
 * abstract witness needed, stop on a sound verdict, a real witness, the whole
 * net or the budget. Independent of the property kind: the checker is
 * supplied by the caller.
 */
public class ProjectionCegar {

	public enum Status {
		PROVED, REFUTED, UNKNOWN
	}

	/** Answer of a checker on one projection; witness is an abstract trace or null. */
	public static class Answer {
		public final Status status;
		public final int[] witness;

		public Answer(Status status, int[] witness) {
			this.status = status;
			this.witness = witness;
		}

		public static final Answer UNKNOWN = new Answer(Status.UNKNOWN, null);
		public static final Answer PROVED = new Answer(Status.PROVED, null);
	}

	public interface AbstractChecker {
		Answer check(PlaceProjection projection, int budgetSeconds);
	}

	/** Outcome of the loop; concreteWitness is set when a real refutation was replayed. */
	public static class Outcome {
		public final Status status;
		public final SparseIntArray concreteWitness;
		public final int refinements;

		Outcome(Status status, SparseIntArray witness, int refinements) {
			this.status = status;
			this.concreteWitness = witness;
			this.refinements = refinements;
		}
	}

	private static final int DEBUG = 1;

	private final ISparsePetriNet net;
	private final SemiflowCover cover;

	public ProjectionCegar(ISparsePetriNet net, SemiflowCover cover) {
		this.net = net;
		this.cover = cover;
	}

	public Outcome run(BitSet targets, AbstractChecker checker, int budgetSeconds) {
		long deadline = System.currentTimeMillis() + 1000L * budgetSeconds;
		if (!cover.covers(targets)) {
			if (DEBUG >= 1) System.out.println("Place projection: a target place is in no semi-flow, giving up.");
			return new Outcome(Status.UNKNOWN, null, 0);
		}
		BitSet kept = new BitSet(net.getPlaceCount());
		for (int i : cover.cheapestCovering(targets, kept)) kept.or(cover.support(i));
		int refinements = 0;
		while (true) {
			PlaceProjection proj = new PlaceProjection(net, kept);
			int left = (int) Math.max(1, (deadline - System.currentTimeMillis()) / 1000);
			if (DEBUG >= 1) System.out.println("Place projection " + refinements + ": " + kept.cardinality() + "/"
					+ net.getPlaceCount() + " places, " + proj.getNet().getTransitionCount() + "/" + net.getTransitionCount()
					+ " transitions, " + left + " s left.");
			Answer a = checker.check(proj, left);
			if (a.status == Status.PROVED) return new Outcome(Status.PROVED, null, refinements);
			if (a.status == Status.UNKNOWN) return new Outcome(Status.UNKNOWN, null, refinements);
			BitSet blocked = new BitSet();
			if (a.witness != null) {
				SparseIntArray reached = proj.replay(a.witness, blocked);
				if (reached != null) return new Outcome(Status.REFUTED, reached, refinements);
			}
			if (System.currentTimeMillis() >= deadline) return new Outcome(Status.UNKNOWN, null, refinements);
			int before = kept.cardinality();
			List<Integer> add = cover.cheapestCovering(blocked, kept);
			for (int i : add) kept.or(cover.support(i));
			if (kept.cardinality() == before) {
				int i = cover.cheapestAdjacent(kept, net);
				if (i < 0) return new Outcome(Status.UNKNOWN, null, refinements);
				kept.or(cover.support(i));
			}
			if (DEBUG >= 1) System.out.println("Place projection refined with " + (kept.cardinality() - before)
					+ " places (" + (a.witness != null ? blocked.cardinality() + " blocking" : "no witness") + ").");
			refinements++;
			if (kept.cardinality() == net.getPlaceCount()) return new Outcome(Status.UNKNOWN, null, refinements);
		}
	}
}
