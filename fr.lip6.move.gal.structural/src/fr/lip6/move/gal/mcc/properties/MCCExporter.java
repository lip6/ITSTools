package fr.lip6.move.gal.mcc.properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralToPNML;

public class MCCExporter {

	/**
	 * Build MCC compliant representation of the provided SparsePetriNet and it's
	 * properties.
	 * NB : IOExceptions are not reported to caller.
	 * 
	 * @param pnmlpath path to build the model file in PNML format
	 * @param proppath path to put the formulas in MCC XML format
	 * @param spn      the net which should have properties
	 */
	public static void exportToMCCFormat(String pnmlpath, String proppath, SparsePetriNet spn) {
		try {
			List<Integer> usedConstants = new ArrayList<>();
			if (!spn.getProperties().isEmpty())
				usedConstants = PropertiesToPNML.transform(spn, proppath, new ConcurrentHashDoneProperties());
			if (! usedConstants.isEmpty()) {
				// we exported constants to a place with index = current place count
				// to be consistent now add a trivially constant place with initial marking 1
				// token
				System.out.println("Added places for constants to the net.");
				for (int cst : usedConstants) {
					spn.addPlace("constant"+cst, cst);
				}
			}
			StructuralToPNML.transform(spn, pnmlpath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
