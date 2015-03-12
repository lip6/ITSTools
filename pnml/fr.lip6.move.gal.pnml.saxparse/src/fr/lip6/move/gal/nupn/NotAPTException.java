package fr.lip6.move.gal.nupn;

import fr.lip6.move.pnml.ptnet.PNType;

/** tried to parse but was not a PT */
public class NotAPTException extends RuntimeException {
	
	PNType realType;

	public NotAPTException(PNType realType) {
		super();
		this.realType = realType;
	}
	
	public PNType getRealType() {
		return realType;
	}

}
