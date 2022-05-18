package fr.lip6.mist.io;

import java.io.File;
import java.io.IOException;

import fr.lip6.mist.io.lola.LolaImporter;
import fr.lip6.mist.io.lola.LolaTaskImporter;
import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.mcc.properties.PropertiesToPNML;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralToPNML;

public class TestLola {

	
	public static void main(String[] args) {
		
		
		test2();
	}

	private static void test2() {
		
		try {
			File ff = new File("benchmark/h_fairly_terminates.pnet.terminating");
//			File ff = new File("benchmark/terminating.lola");
			String path= ff.getCanonicalPath();
			String task=path + ".task1";
			SparsePetriNet pn = loadLolaTask(path, task);
			String pwd = ff.getParentFile().getCanonicalPath();
			
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
			
			FlowPrinter.drawNet(pn, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static SparsePetriNet loadLolaTask(String path, String task) throws IOException {
		SparsePetriNet pn = LolaImporter.loadLola(path);

		System.out.println("Lola file parsed successfully !");

		FlowPrinter.drawNet(pn, "Lola imported from " + path);

		LolaTaskImporter lti = new LolaTaskImporter();
		lti.loadLolaTask(task, pn);

		System.out.println("Final properties :" + pn.getProperties());
		return pn;
	}
}
