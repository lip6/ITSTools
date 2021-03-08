package jhoafparser.parser;

@SuppressWarnings("serial")
public abstract class ParseException extends Exception {

	public ParseException(String initialise) {
		super(initialise);
	}

	public ParseException() {
	}

}
