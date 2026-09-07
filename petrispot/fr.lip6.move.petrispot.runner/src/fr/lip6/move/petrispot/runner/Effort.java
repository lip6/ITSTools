package fr.lip6.move.petrispot.runner;

/**
 * How hard one PetriSpot call may try: the contract between the loop that
 * asks and the walker that answers (PetriSpot PORTFOLIO.md, "Three contracts
 * for a walker call"). The binary only knows step and time budgets; the
 * effort decides how the budgets a call site names are read.
 */
public enum Effort {
	/**
	 * Best effort under bounds that are caps, not targets: a short sweep, and
	 * rounds that stop as soon as one solves nothing with every walk ending on
	 * its step budget. For the questions a loop asks beside its real work: the
	 * atoms of an LTL or CTL formula, the knowledge gathered before a model
	 * checking run. The walker never outlasts the engine it serves.
	 */
	GLEAN(3, 10),
	/**
	 * Try hard until the time is spent: a round that solves nothing on its step
	 * budget raises the budget tenfold and walks on. For the questions where
	 * the walker is the engine expected to move: reachability, deadlock and
	 * bounds on the nets the exact engines cannot handle.
	 */
	COMMIT(Integer.MAX_VALUE, Integer.MAX_VALUE);

	private final int sweepCap;
	private final int totalCap;

	private Effort(int sweepCap, int totalCap) {
		this.sweepCap = sweepCap;
		this.totalCap = totalCap;
	}

	/** Whether a round that solved nothing on its step budget buys more steps rather than ending the call. */
	public boolean escalates() {
		return this == COMMIT;
	}

	/** The seconds of sweep this effort grants, out of what the call site would spend. */
	public int sweepSeconds(int asked) {
		return Math.min(asked, sweepCap);
	}

	/** The seconds in total this effort grants, out of what the call site would spend. */
	public int totalSeconds(int asked) {
		return Math.min(asked, totalCap);
	}
}
