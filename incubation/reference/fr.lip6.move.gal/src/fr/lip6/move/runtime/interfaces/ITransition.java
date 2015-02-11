package fr.lip6.move.runtime.interfaces;


/**
 * This interface represent a transition in a GAL system
 */
public interface ITransition {
	
	/** Returns the name of the transition */
	String getName();
	
	/** Returns the boolean value of a transition guard.
	 * 
	 * @param entryState : The state in which variables values will be retrieved.
	 */
	boolean guard(final IState entryState);
	
	/**
	 * Evaluate the body of the transition, and returns a state in which variables have been
	 * modified.
	 * @param entryState : the state in which variables values will be retrived.
	 * @return : a state in which variables has been modified.
	 */
	IState successor(final IState entryState);
} 
