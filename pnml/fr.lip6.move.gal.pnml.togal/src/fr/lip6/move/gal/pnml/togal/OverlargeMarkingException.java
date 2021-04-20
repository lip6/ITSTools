package fr.lip6.move.gal.pnml.togal;

@SuppressWarnings("serial")
public class OverlargeMarkingException extends RuntimeException {

	public OverlargeMarkingException() {
		super("Annotations (e.g. markings) use too many bits cannot handle transformation accurately.");
	}
}
