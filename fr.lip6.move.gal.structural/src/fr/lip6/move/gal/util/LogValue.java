package fr.lip6.move.gal.util;

/**
 * Rendering of values destined for a log line.
 *
 * Collections of places, variables or arc constants run to hundreds of
 * thousands of characters on the large models. Concatenated into a log
 * statement they produce a single line that no one can read and that dwarfs
 * the run it describes, so they are cut here.
 */
public class LogValue {

	/** Above this, a value rendered on one log line is cut. */
	private static final int LIMIT = 120;

	public static String abbreviate(Object value) {
		return abbreviate(value, LIMIT);
	}

	/** The full length is reported, so a cut value never reads as a complete one. */
	public static String abbreviate(Object value, int limit) {
		String s = String.valueOf(value);
		if (s.length() <= limit) {
			return s;
		}
		return s.substring(0, limit) + "... [" + s.length() + " chars]";
	}
}
