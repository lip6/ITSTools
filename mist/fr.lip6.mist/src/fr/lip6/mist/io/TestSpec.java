package fr.lip6.mist.io;

import java.io.File;
import java.io.IOException;

import fr.lip6.mist.io.spec.SpecImporter;
import fr.lip6.mist.io.tpn.TpnImporter;
import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.mcc.properties.PropertiesToPNML;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralToPNML;

public class TestSpec {


	public static void main(String[] args) {
		//String ff=args[0];  // "benchmark/x0_BUG_REPORT_q1.spec" "benchmark/Model.10om__0_____u__.xml.tpn"
		String ff="benchmark/Model.10om__0_____u__.xml.tpn";
		String folder = ".";

		System.out.println("Transforming Mist Spec file at : "+ff + " to folder "+folder);

		if (ff.endsWith(".spec")) {
			readSpecFile(ff,folder);			
		} else if (ff.endsWith(".tpn")) {
			readTpnFile(ff,folder);
		}
	}

	private static void readTpnFile(String pathff, String folder) {

		try {
			File ff = new File(pathff);
			String path= ff.getCanonicalPath();
			SparsePetriNet pn = TpnImporter.loadSpec(path);

			System.out.println("Tpn Mist format file parsed successfully !");

			FlowPrinter.drawNet(pn, "Tpn imported from " + path);

			String outsr = folder + "/model.pnml";
			StructuralToPNML.transform(pn, outsr);

			System.out.println("Exported to file :"+outsr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readSpecFile(String pathff, String folder) {

		try {
			File ff = new File(pathff);
			String path= ff.getCanonicalPath();
			SparsePetriNet pn = SpecImporter.loadSpec(path);

			System.out.println("Spec Mist format file parsed successfully !");

			FlowPrinter.drawNet(pn, "Spec imported from " + path);

			System.out.println("Final properties :" + pn.getProperties());

			String outform = folder + "/" + "ReachabilityCardinality" + ".xml";
			boolean usesConstants = PropertiesToPNML.transform(pn, outform, new ConcurrentHashDoneProperties());
			if (usesConstants) {
				// we exported constants to a place with index = current place count
				// to be consistent now add a trivially constant place with initial marking 1
				// token
				System.out.println("Added a place called one to the net.");
				pn.addPlace("one", 1);
			}
			String outsr = folder + "/model.pnml";
			StructuralToPNML.transform(pn, outsr);

			System.out.println("Exported to file :"+outsr);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
