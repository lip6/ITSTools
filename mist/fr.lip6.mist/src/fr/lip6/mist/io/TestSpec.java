package fr.lip6.mist.io;

import java.io.File;
import java.io.IOException;

import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.mcc.properties.PropertiesToPNML;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralToPNML;

public class TestSpec {

	
	public static void main(String[] args) {
		System.out.println("Transforming Mist Spec file at : "+args[0]);
		test2(args[0]);
	}

	private static void test2(String pathff) {
		
		try {
			File ff = new File(pathff);
//			File ff = new File("benchmark/x0_BUG_REPORT_q1.spec");
//			File ff = new File("benchmark/terminating.lola");
			String path= ff.getCanonicalPath();
			SparsePetriNet pn = SpecImporter.loadSpec(path);

			System.out.println("Spec Mist format file parsed successfully !");

			FlowPrinter.drawNet(pn, "Spec imported from " + path);

			System.out.println("Final properties :" + pn.getProperties());

			String pwd = ".";
			
			String outform = pwd + "/" + "ReachabilityCardinality" + ".xml";
			boolean usesConstants = PropertiesToPNML.transform(pn, outform, new ConcurrentHashDoneProperties());
			if (usesConstants) {
				// we exported constants to a place with index = current place count
				// to be consistent now add a trivially constant place with initial marking 1
				// token
				System.out.println("Added a place called one to the net.");
				pn.addPlace("one", 1);
			}
			String outsr = pwd + "/model.pnml";
			StructuralToPNML.transform(pn, outsr);
			
			System.out.println("Exported to file :"+outsr);
			
			FlowPrinter.drawNet(pn, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
