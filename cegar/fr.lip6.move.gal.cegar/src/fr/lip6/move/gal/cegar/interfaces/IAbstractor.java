package fr.lip6.move.gal.cegar.interfaces;

import fr.lip6.move.gal.Specification;

/**
 * Interface for GAL abstractors.
 */
public interface IAbstractor {

	/**
	 * Return the abstraction of the GAL specified when the IAbstraction object
	 * has been instantiated.
	 * 
	 * @return Specification
	 */
	public Specification abstractGal();

	/**
	 * Refine the current abstraction using the specified strategy.
	 * 
	 * @param failedTransition to help choose how to refine
	 */
	public void refine(String trans);
	
}
