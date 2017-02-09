package fr.lip6.move.gal.semantics;

import java.io.ByteArrayOutputStream;

import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.serialization.BasicGalSerializer;

/**
 * Utility class for printing Gal elements (Assignments, Bool and Int Expression mostly) using a VariableIndexer.
 * Mostly for debug purposes.
 * @author ythierry
 *
 */
public class ExpressionPrinter {

	/**
	 * Return a string representing the provided object (Assignment, Bool or Int expression), resolving variables using the indexer (i.e. a single big array for all the state variables).
	 * @param ass a GAL element to print such as an assignment
	 * @param stateName a string the is used to denote the current state.
	 * @param index resolve ariables to indexes in the state vector
	 * @return a pretty string
	 */
	public static String print(EObject ass, String stateName, VariableIndexer index) {
		BasicGalSerializer bgs = new BasicGalSerializer() {

			@Override
			public Boolean caseVariableReference(VariableReference vref) {
				String vname = vref.getRef().getName();
				if (vref.getIndex() != null) {
					pw.print(stateName + "[" + index.getIndex(vname));
					pw.print("+");
					doSwitch(vref.getIndex());
					pw.print("]");
				} else {
					pw.print(stateName + "[" + index.getIndex(vname) + "]");
				}
				return true;
			}

		};
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bgs.setStream(baos, 2);
		bgs.doSwitch(ass);
		bgs.close();
		return baos.toString();
	}
}
