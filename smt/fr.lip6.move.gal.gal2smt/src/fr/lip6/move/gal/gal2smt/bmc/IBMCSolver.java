package fr.lip6.move.gal.gal2smt.bmc;


import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.gal2smt.Result;

/**
 * An interface for forward exploration bounded depth SMT based solutions.
 * @author ythierry
 *
 */
public interface IBMCSolver {

	/**
	 * Initialize the solver with : logic/headers, definition of variables S[step] (as arrays indexed by step), definition of transition relation Next(step)
	 * Definition of properties as boolean predicates with one parameter (step).
	 * @param spec a specification, we expect "main" instance to be a GAL
	 * @param withInitialState if true, also specify initial state constraint as read from GAL initialization
	 */
	public void init (Specification spec, boolean withInitialState);
	
	/** 
	 * The current depth of this BMC exploration, starts at 0
	 * @return the depth of BMC
	 */
	public int getDepth();

	/**
	 * Increment the current depth : assert constraints linking  S[depth] and S[depth+1]
	 */
	public void incrementDepth();
	
	/**
	 * Try to verify whether S[depth] |= prop.
	 * Does not modify the current set of assertions.
	 * @param prop a boolean function with one integer parameter (the depth)
	 * @return a result with value SAT if property is reachable at current depth, UNSAT else, or UNKNWOWN in case of timeout
	 */
	public Result checkProperty(Property prop);
	
	/**
	 * Kill the solver cleanly and finish the SMT session.
	 */
	public void exit();
	
	/**
	 * Add initial constraints to current solver state
	 * @param spec to read initial values from
	 */
	public void assertInitial (Specification spec);
	
}
