package fr.lip6.move.gal.gal2pins;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.gal2smt.Gal2SMTFrontEnd;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.serialization.SerializationUtil;

public class Test {

	
	public static void main(String[] args) {
		SerializationUtil.setStandalone(true);
	//	Specification spec = SerializationUtil.fileToGalSystem("tests/kanban5.gal");
	//	Specification spec = SerializationUtil.fileToGalSystem("tests/csRepeat-2-col.pnml.flat.gal");
		Specification spec = SerializationUtil.fileToGalSystem("tests/Lotos-Garavel.gal");
		//Specification spec = SerializationUtil.fileToGalSystem("tests/gear.1.flat.gal");
		//Specification spec = SerializationUtil.fileToGalSystem("tests/elevator.3.flat.gal");
		Gal2PinsTransformerNext g2p = new Gal2PinsTransformerNext();
		
		Gal2SMTFrontEnd gsf = new Gal2SMTFrontEnd(Solver.Z3, 300000);
		g2p.setSmtConfig(gsf);
		List<File> produced = new ArrayList<>();
		g2p.transform(spec, "tests/", true, false, produced);
		for (File f : produced) {
			try {
				System.out.println("Produced file: " + f.getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
