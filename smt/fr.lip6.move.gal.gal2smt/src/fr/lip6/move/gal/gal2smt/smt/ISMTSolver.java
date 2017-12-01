package fr.lip6.move.gal.gal2smt.smt;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.semantics.INextBuilder;

public interface ISMTSolver {

	/**
	 * Initialize the solver with : logic/headers, definition of variables S[step] (as arrays indexed by step), definition of transition relation Next(step)
	 * @param spec a specification, we expect "main" instance to be a GAL
	 */
	@Deprecated
	default void init(Specification spec) {
		init(IDeterministicNextBuilder.build(INextBuilder.build(spec)));
	}

	void init(IDeterministicNextBuilder spec);	
	
	/**
	 * Kill the solver cleanly and finish the SMT session.
	 */
	public abstract void exit();

	/**
	 * Call solver in current state
	 * @return SAT or UNSAT; throws RuntimeException in other cases
	 */
	public abstract Result checkSat();

	
	/**
	 * Run check sat for prop on current solver state. Solver state should be left unchanged.
	 * @param prop the property we are studying
	 * @return UNSAT or SAT, should be interpreted by caller
	 */
	public Result verify(Property prop);
	
	public void setShowSatState(boolean shouldShow);

	
}