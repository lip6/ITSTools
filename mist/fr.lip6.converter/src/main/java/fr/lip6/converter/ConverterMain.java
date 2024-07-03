package fr.lip6.converter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.lip6.mist.io.lola.LolaImporter;
import fr.lip6.mist.io.lola.LolaTaskImporter;
import fr.lip6.mist.io.pnet.PnetImporter;
import fr.lip6.mist.io.properties.PropertiesImporter;
import fr.lip6.mist.io.properties.PropertiesToPNML;
import fr.lip6.mist.io.selt.SeltTaskImporter;
import fr.lip6.mist.io.spec.SpecImporter;
import fr.lip6.mist.io.tpn.TpnImporter;
import fr.lip6.move.gal.mcc.properties.MCCExporter;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.pnml.PTNetReader;

public class ConverterMain {

	private static final String CONVERT_FLAG = "-convert";
	private static final String FORMULA_FLAG = "-formula";
	private static final String OUT_FOLDER = "-o";
	private static final String DOT_OUT = "-dot";
	private static final String SELT_FLAG = "-selt";

	public static void main(String[] args) {
		// String ff=args[0]; // "benchmark/x0_BUG_REPORT_q1.spec"
		// "benchmark/Model.10om__0_____u__.xml.tpn"
		// "benchmark/h_fairly_terminates.pnet.terminating" "benchmark/terminating.lola"
		//String ff="benchmark/h_fairly_terminates.pnet.terminating.task1";
		// "benchmark/Model.10om__0_____u__.xml.tpn";

		String ff = null;
		String folder = ".";
		boolean doDotOutput=false;
		boolean isFormula = false;
		int firstSelt = -1;
		for (int i=0; i < args.length ; i++) {
			if (CONVERT_FLAG.equals(args[i])) {
				ff = args[++i];
			} else if (FORMULA_FLAG.equals(args[i])) {
				ff = args[++i];
				isFormula = true;
			} else if (OUT_FOLDER.equals(args[i])) {
				folder = args[++i];
			} else if (DOT_OUT.equals(args[i])) {
				doDotOutput= true;
			} else if (SELT_FLAG.equals(args[i])) {
				firstSelt = i+1;
				break;
			}
		}

		// argument validity checks
		{
			if (ff == null) {
				System.err.println("Please provide input file after -convert/-formula option");
				return ;
			}

			File fileff = new File(ff);
			if (! fileff.exists()) {
				System.err.println("Input file "+ff +" does not exist");
				return ;
			}
			
		}
		System.out.println("Transforming source file at : " + ff + " to folder " + folder);

		if (isFormula) {
			
			try {
				PropertiesImporter pi = new PropertiesImporter();
				Map<String, Integer> vars = new HashMap<>();
				List<Property> props = pi.parseFile(ff, vars);
				System.out.println("Parsed properties :" + props);
				PropertiesToPNML.transform(props, vars, folder + "/properties.xml");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
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
			} else if (ff.endsWith(".pnml")) {
				File fff = new File(ff);
				PTNetReader reader = new PTNetReader();
				pn = reader.loadFromXML( fff);
				if (pn == null) {
					System.err.println("PNML file at "+ff+ " contains a Colored net. Please ask us if you'd like support for unfolding in such cases.");
					throw new IOException("Cannot parse colored pnml file "+fff.getAbsolutePath());
					// hlpn = transPN.transformHLPN(fff.toURI());
				} 
			}
			if (firstSelt > 0) {
				List<String> selt = new ArrayList<>();
				for (int i = firstSelt ; i < args.length ; i++) {
					selt.add(args[i]);
				}
				System.out.println("Parsing SELT format properties from :" + selt.toString());
				readSeltFiles(pn,selt);
			}
			
			exportPNML(pn, folder);

			if (doDotOutput)
				FlowPrinter.drawNet(pn, ff);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readSeltFiles(SparsePetriNet pn, List<String> selt) throws IOException {
		for (String pathff : selt) {
			File ff = new File(pathff);
			String path = ff.getCanonicalPath();
			
			SeltTaskImporter lti = new SeltTaskImporter();
			lti.loadSeltTask(path, pn);
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

		if (!pn.getProperties().isEmpty()) {
			// This is a bit too verbose for large models.
			//			System.out.println("Final properties :" + pn.getProperties());
		}

		// currently supposing these are invariants/safety queries
		MCCExporter.exportToMCCFormat(folder + "/model.pnml", folder + "/" + "ReachabilityCardinality" + ".xml", pn);

		System.out.println("Exported model and "+pn.getProperties().size() +" properties to folder :" + folder);
	}

}
