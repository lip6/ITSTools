package fr.lip6.move.gal.contribution.orders;
/**
 * 
 * @author thamazgha
 *
 */
public enum OrderHeuristic {

	/**
	 * Read the variable order from the <netname>.place file.
	 */
	F("-F"),
	/**
	 * Derive order using the P-semiflows chaining method.
	 */
	P("-P"),
	/**
	 * Use standard FORCE algorithm.
	 */
	FR("-FR"),
	/**
	 * Use FORCE algorithm with P-semiflows
	 */
	FRP("-FR-P"),
	/**
	 * Use FORCE algorithm with  Nested Units
	 */
	FRNU ("-FR-NU"),
	/**
	 * Use breadth-first search order (poor performances).
	 */
	BFS("-BFS"),
	/**
	 * Use depth-first search order (poor performances)
	 */
	DFS("-DFS"),
	/**
	 * Use Cuthill-McKee method (boost version).
	 */
	CM("CM"),
	/**
	 * Use Cuthill-McKee method (ViennaCL version).
	 */
	CM2("CM2"),
	/**
	 * Use Advanced Cuthill-McKee method.
	 */
	ACM("-ACM"),
	/**
	 * Use Gibbs-Poole-Stockmeyer ordering.
	 */
	GPS("-GPS"),
	/**
	 * Use King ordering method.
	 */
	KING("-KING"),
	/**
	 * Use Sloan ordering (two parametric variations).
	 */
	SLOSLO16("SLO -SLO-16"),
	/**
	 *  Use Noack ordering.
	 */
	NOACK ("-NOACK"),
	/**
	 * Use Tovchigrechko ordering.
	 */
	TOV("-TOV"),
	/**
	 * Use Gradient-P ordering with P-semiflows.
	 */
	GP("-GP"),
	/**
	 * Use Gradient-NU ordering with Nested Units.
	 */
	GNU("-GNU"),
	/**
	 * Use modified FORCE with point spans metric.
	 */
	FORCE("-FORCE"),
	/**
	 * Use modified FORCE with NES metric.
	 */
	FORCENES("-FORCE-NES"),
	/**
	 * Use modified FORCE with WES(1) metric.
	 */
	FORCEWES1("-FORCE-WES1"),
	/**
	 * Use modified FORCE weighted by P-semiflows.
	 */
	FORCEP("-FORCE-P"),
	/**
	 * Use meta-heuristics
	 */
	META("-META"),
	/**
	 * Use SCC post-heuristic variable re-ordering.
	 */
	scc("-scc"),
	/**
	 * <W1> <W2>  Specify integer weights of Sloan function.
	 */
	sloanW("-sloan-W"),
	/**
	 * Refine the selected variable ordering with FORCE iterations.
	 */
	refine("-refine");
	
	private String str;
	OrderHeuristic(String str) { this.str = str; }
	public String toString() {
		return this.str;
	}
}
