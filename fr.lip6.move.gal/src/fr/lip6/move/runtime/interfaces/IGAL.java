package fr.lip6.move.runtime.interfaces;

import java.util.List;

/**
 * This interface represent a GAL system ; the root by which we can access all other gal elements.
 */
public interface IGAL {
	
	/** Returns the name of the GAL system. */
	String getName();
	
	/**
	 * Returns the initial state of the system.
	 * The initial state contains all variables and their values assigned at initialization.
	 */
	IState getInitState();
	
	/** Returns the list of all transitions of the GAL system */
	List<ITransition> getTransitions();
	
	/**
	 * Returns the boolean value of the TRANSIENT statement
	 * @param entryState : 
	 * 			The state in which variables values will be retrieved. 
	 */
	boolean getTransient(IState entryState);
}
