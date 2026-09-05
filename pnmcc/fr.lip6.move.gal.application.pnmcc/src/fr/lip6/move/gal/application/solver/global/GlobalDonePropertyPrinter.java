package fr.lip6.move.gal.application.solver.global;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;

public class GlobalDonePropertyPrinter extends ConcurrentHashDoneProperties {

	private String examination;
	private final Aggregation aggregation;
	// Solver threads accumulate into this concurrently, and computeTechniques
	// iterates it while they do.
	private Set<String> tech = ConcurrentHashMap.newKeySet();
	private boolean makeTrace = true;
	// The examination has one verdict and one FORMULA line. Several threads can
	// reach a verdict at the same instant on different sub properties; this
	// elects the one that prints.
	private final AtomicBoolean verdictPrinted = new AtomicBoolean(false);

	public GlobalDonePropertyPrinter(String examination, boolean makeTrace) {
		super();
		this.examination = examination;
		this.makeTrace = makeTrace;
		this.aggregation = Aggregation.of(examination);
	}

	public String computeTechniques() {

		StringBuilder str = new StringBuilder();

		for (String t : tech) {
			str.append(t).append(" ");
		}

		return str.toString();

	}

	public boolean shouldTrace() {
		return makeTrace;
	}

	/**
	 * Prints the single FORMULA line carrying the verdict of the examination.
	 * The first caller wins; any later one, on any thread, is dropped.
	 * @return true if this call is the one that printed
	 */
	private boolean printVerdict(boolean value, String techniques) {
		if (!makeTrace || !verdictPrinted.compareAndSet(false, true)) {
			return false;
		}
		System.out.println("FORMULA " + examination + (value ? " TRUE" : " FALSE") + " TECHNIQUES " + techniques);
		return true;
	}
	
	@Override
	public Boolean put(String prop, Boolean value, String techniques) {
		// System.out.println("FORMULA "+prop+(value?" TRUE":" FALSE")+ " TECHNIQUES
		// "+techniques);

		for (String t : techniques.split(" "))
			tech.add(t);

		Boolean deciding = aggregation.decidingValue();
		if (deciding != null && deciding.equals(value)) {
			// One reading of the techniques, so the line printed and the value
			// recorded describe the same thing.
			String techs = computeTechniques();
			super.put(examination, deciding, techs);
			printVerdict(deciding, techs);
			// Thrown by every thread that gets here, printer or not: each one
			// has to unwind its own work.
			throw new GlobalPropertySolverException(examination + (deciding ? " TRUE" : " FALSE"), deciding);
		}

		return super.put(prop, value, techniques);
	}

	
	@Override
	public boolean isFinished() {
		return containsKey(examination);
	}
}
