package fr.lip6.move.gal.contribution.orders;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.smtlib.plugin.Preferences;

import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.application.Ender;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.semantics.INextBuilder;

public class LTSMinOrderRunner {
	
	
	private LTSminRunner runner;
	private Set<String> doneProps = new HashSet<>();

	public List<String> buildOrder (Specification spec) {
		INextBuilder inb = INextBuilder.build(spec);
		
		return inb.getVariableNames();		
	}
	
	public void printOrder (String path, List<String> order) throws IOException {
		PrintWriter out = new PrintWriter( new BufferedOutputStream(new FileOutputStream(path)));
		out.println("#START");
		for (String var : order) {
			out.println(var);
		}
		out.println("#END");
		out.flush();
		out.close();
	}

	public void configure(Specification s, String outfolder) throws Exception {
		
		String ltsminpath = "/home/ythierry/git/ITS-Tools-pnmcc/lts_install_dir/";
		String solverPath = Preferences.getExec("z3_4_3");
		Solver solver = Solver.Z3;
		boolean doPOR = false;
		runner = new LTSminRunner(ltsminpath, solverPath, solver, doPOR, false, outfolder, 300);
		runner.configure(s, doneProps );
		
		Property prop = GalFactory.eINSTANCE.createProperty();
		prop.setName("Deadlock");
		ReachableProp lpro = GalFactory.eINSTANCE.createReachableProp();
		lpro.setPredicate(GalFactory.eINSTANCE.createTrue());
		prop.setBody(lpro );
		// add deadlock property
		s.getProperties().add(prop);
	}

	public void configure(List<String> order, INextBuilder nb) {
		// compute permutation
		List<String> ori = nb.getVariableNames();
		
		List<Integer> perm = new ArrayList<>(ori.size());
		
		for (int i = 0 ; i < ori.size() ; i++) {
			perm.add(i);
		}
		
		// trier ce tableau d'int pour refléter la permutation row order
		Collections.shuffle(perm);
		
		runner.addOrder (perm);
	}

	public void run() {
		runner.solve(new Ender() {
			@Override
			public void killAll() {
			}
		});
	}

	public void join() throws InterruptedException {
		runner.join();
	}
}
