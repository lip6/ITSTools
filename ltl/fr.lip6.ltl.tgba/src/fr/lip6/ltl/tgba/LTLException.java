package fr.lip6.ltl.tgba;

public class LTLException extends Exception {
	private String techniques;
	public LTLException(String techniques) {
		this.techniques=techniques;
	}
	public String getTechniques() {
		return techniques;
	}
}
