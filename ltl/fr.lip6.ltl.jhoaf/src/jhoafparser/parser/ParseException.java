package jhoafparser.parser;

import java.io.IOException;

@SuppressWarnings("serial")
public abstract class ParseException extends IOException {

	public ParseException(String initialise) {
		super(initialise);
	}

	public ParseException() {
	}

}
