package fr.lip6.move.gal.cegar.support;

public class InexistantTransitionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 353879989288338759L;

	public InexistantTransitionException(String transition) {
		super(transition);
	}
}
