package fr.lip6.move.gal.application.solver.global;

/**
 * How the atoms of a global examination roll up into its verdict, and hence
 * which atom verdict settles the examination at once.
 */
public enum Aggregation {
	/** Every atom must hold: one FALSE atom decides FALSE. OneSafe, QuasiLiveness, Liveness. */
	ALL_TRUE,
	/** One atom suffices: one TRUE atom decides TRUE. StableMarking. */
	ANY_TRUE,
	/** No roll-up: atoms are collected, nothing is decided from one of them. */
	NONE;

	public static Aggregation of(String examination) {
		switch (examination) {
		case "StableMarking":
			return ANY_TRUE;
		case "OneSafe":
		case "Liveness":
		case "QuasiLiveness":
			return ALL_TRUE;
		default:
			return NONE;
		}
	}

	/** The atom verdict that decides the examination, or null if none does. */
	public Boolean decidingValue() {
		switch (this) {
		case ALL_TRUE:
			return Boolean.FALSE;
		case ANY_TRUE:
			return Boolean.TRUE;
		default:
			return null;
		}
	}
}
