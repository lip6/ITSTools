package fr.lip6.move.gal.gal2java;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.gal2smt.Gal2SMTFrontEnd;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.serialization.SerializationUtil;

public class TestBMC {

	
	public static void main(String[] args) {
		SerializationUtil.setStandalone(true);
		//Specification spec = SerializationUtil.fileToGalSystem("tests/philo-5-col.pnml.flat.gal");
		//Specification spec = SerializationUtil.fileToGalSystem("tests/philo-5-col.pnml.gal");
		
		//Specification spec = SerializationUtil.fileToGalSystem("tests/kanban5.gal");
		//Specification spec = SerializationUtil.fileToGalSystem("tests/csRepeat-2-col.pnml.flat.gal");
		// Specification spec = SerializationUtil.fileToGalSystem("tests/lamport2PT-RC.gal");
		 Specification spec = SerializationUtil.fileToGalSystem("tests/ReachabilityCardinality.gal");
			
		 //Specification spec = SerializationUtil.fileToGalSystem("tests/elevator.3.flat.gal");
		
		 // z3 4.3
		//Gal2SMTFrontEnd smt = new Gal2SMTFrontEnd("/data2/ythierry/download/z3/z3-4.3.2.5e72cf0123f6-x64-ubuntu-12.04/bin/z3", Solver.Z3);
		// z3 4.4
		Gal2SMTFrontEnd smt = new Gal2SMTFrontEnd("/data/ythierry/workspaces/neon/fr.lip6.move.gal.benchmark/z3/bin/z3", Solver.Z3);
		// yices
		// Gal2SMTFrontEnd smt = new Gal2SMTFrontEnd("/data/ythierry/workspaces/neon/fr.lip6.move.gal.benchmark/yices/bin/yices-smt2", Solver.YICES2);
		
		smt.addObserver((prop, res, desc) -> {
			if (res == Result.TRUE || res == Result.FALSE) {
				System.out.println("FORMULA " + prop.getName() + " "+ res +" "+ "TECHNIQUES SAT_SMT "+desc );
			} else {
				// a ambiguous verdict  
				//System.out.println("Obtained  " + prop.getName() + " " + res +" TECHNIQUES SAT_SMT "+desc );						
			}
		});
		
		try {
			smt.checkProperties(spec, "/data/ythierry/workspaces/neon/fr.lip6.move.gal.gal2pins/tests/work");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Check finished ");
	}
	
}