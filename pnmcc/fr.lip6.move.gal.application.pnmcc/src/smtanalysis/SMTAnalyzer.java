package smtanalysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.smtlib.SMT;

import android.util.SparseBoolArray;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.smt.Problem;
import fr.lip6.move.gal.structural.smt.ProblemSet;
import fr.lip6.move.gal.structural.smt.SMTBasedReachabilitySolver;
import fr.lip6.move.gal.structural.smt.VarSet;

public class SMTAnalyzer {

	public void analyze(MccTranslator reader, String workFolder) {
		// step 0 : unfold, rename net places to indexed form
		SparsePetriNet spn = reader.getSPN();
		toIndexedNames(spn);
		
        // step 1 : invoke DD based tool (120 secs timeout)
		DDrunner runner = new DDrunner(reader, workFolder, 120);
		try {
			runner.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String smtFile = runner.getSmtFile();
		
		VarSet support = new VarSet();
		SparseBoolArray sball = new SparseBoolArray(spn.getPlaceCount(), true);
		support.addVars("s", sball );
		Problem p = new Problem("complete",smtFile, SMT.instance.smtConfig.exprFactory.symbol("unreachable"), support);
		
		DoneProperties done = new ConcurrentHashDoneProperties();
		ProblemSet problems = new ProblemSet(done);
		problems.addProblem(p);
		
		List<Integer> repr = new ArrayList<>();
		SMTBasedReachabilitySolver.solveProblems(problems, spn, 300, true, repr);
		
		System.out.println(problems);
	}

	private void toIndexedNames(SparsePetriNet spn) {
		List<String> names = spn.getPnames();
		for (int index =0; index < names.size() ; index++) {
			names.set(index, "s"+index);
		}
		names = spn.getTnames();
		for (int index =0; index < names.size() ; index++) {
			names.set(index, "t"+index);
		}		 
	}

}
