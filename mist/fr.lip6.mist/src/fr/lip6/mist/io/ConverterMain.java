package fr.lip6.mist.io;

import java.io.File;
import java.io.IOException;

import fr.lip6.mist.io.lola.LolaImporter;
import fr.lip6.mist.io.lola.LolaTaskImporter;
import fr.lip6.mist.io.pnet.PnetImporter;
import fr.lip6.mist.io.spec.SpecImporter;
import fr.lip6.mist.io.tpn.TpnImporter;
import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.mcc.properties.PropertiesToPNML;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralToPNML;

public class ConverterMain {

	private static final String CONVERT_FLAG = "-convert";
	private static final String OUT_FOLDER = "-o";
	private static final String DOT_OUT = "-dot";

	public static void main(String[] args) {
		// String ff=args[0]; // "benchmark/x0_BUG_REPORT_q1.spec"
		// "benchmark/Model.10om__0_____u__.xml.tpn"
		// "benchmark/h_fairly_terminates.pnet.terminating" "benchmark/terminating.lola"
		//String ff="benchmark/h_fairly_terminates.pnet.terminating.task1";
		// "benchmark/Model.10om__0_____u__.xml.tpn";
		
		String ff = null; 
		String folder = ".";
		boolean doDotOutput=false;
		for (int i=0; i < args.length ; i++) {			
			if (CONVERT_FLAG.equals(args[i])) {
				ff = args[++i];
			} else if (OUT_FOLDER.equals(args[i])) {
				folder = args[++i];
			} else if (DOT_OUT.equals(args[i])) {
				doDotOutput= true;
			}
		}
		
		// argument validity checks
		{
			if (ff == null) {
				System.err.println("Please provide input file after -convert option");
				return ;
			}

			File fileff = new File(ff);
			if (! fileff.exists()) {
				System.err.println("Input file "+ff +" does not exist");
				return ;
			}
		}
		System.out.println("Transforming source file at : " + ff + " to folder " + folder);
			
		
		try {
			SparsePetriNet pn = null;
			if (ff.endsWith(".spec")) {
				pn = readSpecFile(ff, folder);
			} else if (ff.endsWith(".tpn")) {
				pn = readTpnFile(ff, folder);
			} else if (ff.endsWith(".pnet")) {
				pn = readPnetFile(ff, folder);
			} else if (ff.endsWith(".lola")) {
				pn = readLolaFile(ff, folder);
			} else if (ff.endsWith(".task1")) {
				pn = readLolaTaskFile(ff, folder);
			}
			exportPNML(pn, folder);

			if (doDotOutput)
				FlowPrinter.drawNet(pn, ff);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static SparsePetriNet readLolaTaskFile(String pathtask, String folder) throws IOException {
		String pathff = pathtask.replace(".task1", "");
		File ff = new File(pathff);
		String path = ff.getCanonicalPath();

		SparsePetriNet pn = LolaImporter.loadLola(path);

		System.out.println("Lola file "+path+" parsed successfully !");

		LolaTaskImporter lti = new LolaTaskImporter();
		lti.loadLolaTask(path + ".task1", pn);

		return pn;
	}

	private static SparsePetriNet readLolaFile(String pathff, String folder) throws IOException {
		File ff = new File(pathff);
		String path = ff.getCanonicalPath();

		SparsePetriNet pn = LolaImporter.loadLola(path);

		System.out.println("Lola file parsed successfully !");

		return pn;
	}
	
	private static SparsePetriNet readPnetFile(String pathff, String folder) throws IOException {
		File ff = new File(pathff);
		String path = ff.getCanonicalPath();

		SparsePetriNet pn = PnetImporter.loadPnet(path);

		System.out.println("PNet file parsed successfully !");

		return pn;
	}

	private static SparsePetriNet readTpnFile(String pathff, String folder) throws IOException {

		File ff = new File(pathff);
		String path = ff.getCanonicalPath();
		SparsePetriNet pn = TpnImporter.loadSpec(path);

		System.out.println("Tpn Mist format file parsed successfully !");

		return pn;
	}

	private static SparsePetriNet readSpecFile(String pathff, String folder) throws IOException {

		File ff = new File(pathff);
		String path = ff.getCanonicalPath();
		SparsePetriNet pn = SpecImporter.loadSpec(path);

		System.out.println("Spec Mist format file parsed successfully !");

		return pn;
	}

	private static void exportPNML(SparsePetriNet pn, String folder) throws IOException {
		if (! pn.getProperties().isEmpty()) {
			System.out.println("Final properties :" + pn.getProperties());

			//  currently supposing these are invariants/safety queries
			String outform = folder + "/" + "ReachabilityCardinality" + ".xml";
			boolean usesConstants = PropertiesToPNML.transform(pn, outform, new ConcurrentHashDoneProperties());
			if (usesConstants) {
				// we exported constants to a place with index = current place count
				// to be consistent now add a trivially constant place with initial marking 1
				// token
				System.out.println("Added a place called one to the net.");
				pn.addPlace("one", 1);
			}
		}
		
		String outsr = folder + "/model.pnml";
		StructuralToPNML.transform(pn, outsr);

		System.out.println("Exported to file :" + outsr);
	}

}
