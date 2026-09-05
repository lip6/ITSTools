package fr.lip6.move.gal.application.solver.total;

import fr.lip6.move.gal.application.solver.global.GlobalAtoms;

/**
 * The total examinations: one atom per place or per transition of the net,
 * every atom answered on its own. Each rides an MCC examination pipeline
 * and names its atoms with a prefix that the printer turns back into the
 * object index, p<i> or t<i> in definition order.
 */
public enum TotalExamination {
	QUASI_LIVENESS_ALL("QuasiLivenessAll", "QLIVE", "ReachabilityFireability", GlobalAtoms.QUASI_LIVE_PREFIX, "t"),
	STABLE_MARKING_ALL("StableMarkingAll", "STABLE", "ReachabilityCardinality", GlobalAtoms.STABLE_PREFIX, "p"),
	UPPER_BOUNDS_ALL("UpperBoundsAll", "BOUND", "UpperBounds", "ubplace_", "p");

	public final String name;
	/** First word of every output line of this examination. */
	public final String keyword;
	/** The MCC examination whose pipeline solves the atoms. */
	public final String mccExamination;
	/** Atom names are this prefix followed by the object index. */
	public final String atomPrefix;
	/** Printed object names are this letter followed by the object index. */
	public final String objectPrefix;

	private TotalExamination(String name, String keyword, String mccExamination, String atomPrefix, String objectPrefix) {
		this.name = name;
		this.keyword = keyword;
		this.mccExamination = mccExamination;
		this.atomPrefix = atomPrefix;
		this.objectPrefix = objectPrefix;
	}

	/** The total examination named by an -examination argument, or null. */
	public static TotalExamination of(String examination) {
		for (TotalExamination te : values()) {
			if (te.name.equals(examination)) {
				return te;
			}
		}
		return null;
	}

	/** Atom name to printed object: the prefix swapped for the object letter, other names unchanged. */
	public String objectOf(String atomName) {
		if (atomName.startsWith(atomPrefix)) {
			return objectPrefix + atomName.substring(atomPrefix.length());
		}
		return atomName;
	}
}
