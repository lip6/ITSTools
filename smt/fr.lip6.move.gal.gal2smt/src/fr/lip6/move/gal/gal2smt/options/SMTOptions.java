package fr.lip6.move.gal.gal2smt.options;

import picocli.CommandLine.Option;

public class SMTOptions {
	@Option(names = { "--no-traps" }, description = "Disable trap refinement.")
	private boolean disableTraps;

	private static final SMTOptions instance = new SMTOptions();

	public static SMTOptions getInstance() {
		return instance;
	}
	
	public boolean isDisableTraps() {
		return disableTraps;
	}
		
}
