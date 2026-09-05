package fr.lip6.move.gal.application.solver.total;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;

/**
 * Prints one line per atom as its verdict lands, nothing at the end:
 *
 * <pre>
 * QLIVE t17 TRUE TOPOLOGICAL RANDOM_WALK
 * STABLE p3 FALSE RANDOM_WALK
 * BOUND p9 12 SAT_SMT
 * BOUND p11 ? 5 inf
 * </pre>
 *
 * The last shape is an open bound, printed whenever its interval moves; a
 * reader keeps the last line of each object. An atom with no line at all
 * is unanswered.
 */
public class TotalPrinter extends ConcurrentHashDoneProperties implements BoundsProgress {

	private final TotalExamination exam;
	// last printed interval per atom, so an unchanged pair is not repeated
	private final Map<String, Long> lastInterval = new ConcurrentHashMap<>();

	public TotalPrinter(TotalExamination exam) {
		this.exam = exam;
	}

	private static String bound(int v) {
		return (v < 0 || v == Integer.MAX_VALUE) ? "inf" : Integer.toString(v);
	}

	@Override
	public Boolean put(String prop, Boolean value, String techniques) {
		Boolean b = super.put(prop, value, techniques);
		if (b == null) {
			System.out.println(exam.keyword + " " + exam.objectOf(prop) + (value ? " TRUE " : " FALSE ") + techniques);
		} else if (b != value) {
			System.out.println("TestFail conflict detected : techniques " + techniques + " answered differently ("
					+ value + ")on formula " + prop);
		}
		return b;
	}

	@Override
	public Boolean put(String prop, Integer value, String techniques) {
		Boolean b = super.put(prop, value, techniques);
		if (b == null) {
			System.out.println(exam.keyword + " " + exam.objectOf(prop) + " " + bound(value) + " " + techniques);
		}
		return b;
	}

	@Override
	public void interval(String prop, int maxSeen, int maxStruct) {
		if (containsKey(prop)) {
			return;
		}
		long pair = ((long) maxSeen << 32) | (maxStruct & 0xffffffffL);
		Long prev = lastInterval.put(prop, pair);
		if (prev == null || prev.longValue() != pair) {
			System.out.println(exam.keyword + " " + exam.objectOf(prop) + " ? " + bound(maxSeen) + " " + bound(maxStruct));
		}
	}
}
