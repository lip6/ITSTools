package fr.lip6.move.gal.application;

/** 
 * Something you tell when it's time to end it.
 *
 */
public interface Ender {
	/**
	 * It's over, kill them all.
	 * Called by a solver that has solved all problem instances.
	 */
	void killAll();

}
