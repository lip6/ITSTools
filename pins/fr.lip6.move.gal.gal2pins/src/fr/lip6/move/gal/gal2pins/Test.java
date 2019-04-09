package fr.lip6.move.gal.gal2pins;


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
		
		Gal2SMTFrontEnd gsf = new Gal2SMTFrontEnd("/data2/ythierry/download/z3/z3-4.3.2.5e72cf0123f6-x64-ubuntu-12.04/bin/z3", Solver.Z3, 300000);
		g2p.setSmtConfig(gsf);
		g2p.transform(spec, "tests/", true, false);
	}
}
