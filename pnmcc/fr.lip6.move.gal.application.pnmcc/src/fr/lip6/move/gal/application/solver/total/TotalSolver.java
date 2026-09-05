package fr.lip6.move.gal.application.solver.total;

import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.application.solver.global.GlobalAtoms;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;

/**
 * Sets a total examination up in place of a property file: builds the atoms
 * on the net as parsed, prints the header line, and names the MCC pipeline
 * that solves them. No discard: every object gets an atom, and the pipeline
 * settles the trivial ones (constant places, dead transitions) itself.
 *
 * P/T nets only: a coloured input is refused.
 */
public class TotalSolver {

	private TotalSolver() {
	}

	/**
	 * @return the MCC examination to continue with, or null when the input is refused
	 */
	public static String prepare(TotalExamination exam, MccTranslator reader) {
		if (reader.getHLPN() != null) {
			System.out.println("TOTAL " + exam.name + " unsupported on coloured nets.");
			return null;
		}
		SparsePetriNet spn = reader.getSPN();
		spn.getProperties().clear();
		int count;
		switch (exam) {
		case QUASI_LIVENESS_ALL:
			GlobalAtoms.quasiLiveness(spn, null);
			count = spn.getTransitionCount();
			break;
		case STABLE_MARKING_ALL:
			GlobalAtoms.stableMarking(spn, null);
			count = spn.getPlaceCount();
			break;
		case UPPER_BOUNDS_ALL:
		default:
			for (int pid = 0, pide = spn.getPlaceCount(); pid < pide; pid++) {
				spn.getProperties().add(new Property(Expression.var(pid), PropertyType.BOUNDS, exam.atomPrefix + pid));
			}
			count = spn.getPlaceCount();
			break;
		}
		System.out.println("TOTAL " + exam.name + " " + count);
		return exam.mccExamination;
	}
}
