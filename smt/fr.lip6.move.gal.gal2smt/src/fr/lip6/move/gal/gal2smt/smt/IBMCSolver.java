package fr.lip6.move.gal.gal2smt.smt;


import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.gal2smt.Result;

/**
 * An interface for forward exploration bounded depth SMT based solutions.
 * @author ythierry
 *
 */
public interface IBMCSolver extends ISMTSolver {

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
	 * @param prop a property
	 * @return a result with value SAT or UNSAt the caller should interpret 
	 */
	@Override
	public Result verify(Property prop);
	
	/**
	 * Add initial constraints to current solver state
	 * @param spec to read initial values from
	 */
	public void assertInitialState (Specification spec);
}
