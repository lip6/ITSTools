package fr.lip6.move.promela.togal.transform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.promela.promela.ProcessDefinition;
import fr.lip6.move.promela.promela.PromelaSpecification;
import fr.lip6.move.promela.togal.transform.environment.Environment;
import fr.lip6.move.promela.togal.transform.environment.GlobalEnvironment;
import fr.lip6.move.promela.togal.transform.representations.ProcessRepresentation;

import static fr.lip6.move.promela.togal.utils.DotConfig.*;

public class PromelaToDotProcessRepresentation {

	List<DotRep> processRep;

	// MAYBE Ou Hash?

	public PromelaToDotProcessRepresentation(PromelaSpecification ospec) {

		processRep = new ArrayList<DotRep>();

		GlobalEnvironment globals = new GlobalEnvironment();
		Environment env = new Environment(globals);
		Converter c = new Converter(env);
		System.err.println("Affiche de la machine à état déduite du processus");
		for (ProcessDefinition p : ospec.getProcesses()) {
			ProcessRepresentation pr = ProcessRepresentation
					.createRepresentation(p, c);
			String dotCode = pr.toDot();
			processRep.add(new DotRep(pr.getName(), dotCode, pr));
		}
	}

	public String getNbProcessDef() {
		return Integer.toString(processRep.size());
	}

	public void saveToFile(String prefix) throws IOException {
		// ARG?
		for (DotRep d : processRep) {
			String fname = prefix + "_" + d.name + ".dot";
			// TODO: improve, use Uti, apu or whatever
			FileOutputStream out = new FileOutputStream(new File(fname));
			PrintWriter pw = new PrintWriter(out);

			pw.write(d.dotCode);
			pw.flush();
			pw.close();

			java.lang.System.err.println("Dot process saved to" + fname);

		}

		java.lang.System.err.println("All dot process saved");
	}

	// MAYBE: move up
	public void convertToDot(String prefix, String format) throws IOException {
		// ARG? changer typage format pour qu'il contienne extension, et options
		// à adapter
		// : png doit nécessiter autres argument pour que le filling marche
		if (isDotLaunchable()) {
			// FIXME: parallel
			for (DotRep d : processRep) {
				String dname = prefix + "/" + d.name + ".dot";
				String iname = prefix + "/" + d.name + "." + format;

				System.out.print("Génération du Graphe" + d.name
						+ " au format " + format);
				
				Process pr = Runtime.getRuntime().exec(
						"dot -o " + iname + " -T" + format + " " + dname);
				try {
					pr.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new RuntimeException();
					//CHECK modify.
				}

			}

			java.lang.System.err.println("All img made process saved");
		} else {
			System.err.println("No Dot program, cannot make them!");
		}
	}

	// FIXME tmp
	public String toString() {
		StringBuffer sb = new StringBuffer("DOTREP\n");
		for (DotRep d : processRep) {
			sb.append("---------------------------------------");
			sb.append(d.dotCode);
		}
		return sb.toString();
	}

	private static class DotRep {

		String name;
		String dotCode;
		// LATER: others?
		//ProcessRepresentation pr; //MAYBE not usefull

		DotRep(String name, String dotCode, ProcessRepresentation pr) {
			super();
			this.name = name;
			this.dotCode = dotCode;
		//	this.pr = pr;
		}

	}

}
