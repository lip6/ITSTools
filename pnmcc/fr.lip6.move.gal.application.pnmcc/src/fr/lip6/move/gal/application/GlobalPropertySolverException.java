package fr.lip6.move.gal.application;

public class GlobalPropertySolverException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;
	public boolean verdict;

	public GlobalPropertySolverException(String message, boolean b) {
		super(message);
		this.verdict = b;
	}
}
