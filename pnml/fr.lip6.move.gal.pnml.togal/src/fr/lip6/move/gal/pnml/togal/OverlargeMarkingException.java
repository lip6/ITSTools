package fr.lip6.move.gal.pnml.togal;

import java.io.IOException;

@SuppressWarnings("serial")
public class OverlargeMarkingException extends IOException {

	public OverlargeMarkingException() {
		super("Annotations (e.g. markings) use too many bits cannot handle transformation accurately.");
	}
}
