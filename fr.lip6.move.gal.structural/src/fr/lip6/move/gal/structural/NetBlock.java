package fr.lip6.move.gal.structural;

/**
 * The optional data blocks a net may carry beside its structure: what a
 * transformation recorded about the net it produced, so a consumer can count
 * objects of the *original* net rather than of this one.
 *
 * They are the named blocks of the PNET container (PetriSpot `KERS.md`,
 * `INTEROP.md` section 3, `HSC_PLAN.md` sections 10 to 13): an 8-byte name
 * and a KERS payload, hence {@link #blockName()} is at most 8 characters. An
 * enum rather than a string keeps presence tests O(1) and typos impossible,
 * and fixes the order blocks are written in.
 *
 * The contract for a modifier of the net: maintain the blocks it can and drop
 * the ones it cannot. A stale block makes a consumer return a wrong number,
 * where a missing one only leaves a value unanswered.
 */
public enum NetBlock {
	/**
	 * One column of T rows: how many transitions of the producer's own input
	 * each transition here stands for, minus one (so an absent entry means 1).
	 * A consumer counting arcs of the reachability graph multiplies by it.
	 */
	TMULT,
	/**
	 * One column of P rows: how many places each place stands for, minus one.
	 * For a free strongly connected component whose tokens travel freely, a
	 * marking of m in a place standing for K represents C(m+K-1, K-1) markings,
	 * so a consumer counting states folds that weight in.
	 */
	PCOEF,
	/**
	 * One sparse column: the constant marking of each place that was removed
	 * for holding a fixed number of tokens. Additive in token sums, and a
	 * candidate in a per-place maximum.
	 */
	PDROP,
	/**
	 * A P by G matrix: the pre-arcs of transitions that were dropped but still
	 * contribute arcs to the graph being counted (their enabling condition is
	 * all a consumer needs to count them).
	 */
	GHOSTPT,
	/** One column of G rows: the multiplicities of {@link #GHOSTPT}, minus one. */
	GMULT;

	/** The 8-byte PNET block name, this constant's name. */
	public String blockName() {
		return name();
	}
}
