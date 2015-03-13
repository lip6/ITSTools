package fr.lip6.move.gal.nupn;


/** tried to parse but was not a PT */
public class NotAPTException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	String realType;

	public NotAPTException(String type) {
		super();
		this.realType = type;
	}
	
	public String getRealType() {
		return realType;
	}

}
