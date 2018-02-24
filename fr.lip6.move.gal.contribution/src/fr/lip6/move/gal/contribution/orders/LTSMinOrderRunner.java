package fr.lip6.move.gal.contribution.orders;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
		
		String ltsminpath = "/home/safraou/eclipse-workspace/ITS-Tools-pnmcc-master/lts_install_dir";
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
		
		
		Map<String, Integer> ind = new HashMap<>();
		for (int i = 0; i < order.size(); i++) {
			ind.put(order.get(i),i);
		}
		
		// 2eme boucle
		
		for (int i = 0; i < order.size(); i++) {
			perm.add(ind.get(nb.getVariableNames().get(i)));
		}
		
		// trier ce tableau d'int pour refléter la permutation row order
		// Collections.shuffle(perm);
		/*
		 * ceci estpas forcement fonctionnel
//		 */
//		for(int i =0; i<ori.size(); i++) {
//			String o = order.get(i);
//			int val = Integer.parseInt(o,o.charAt(o.length()-2));//premier o pas sur
//			int index = perm.indexOf(val);
//	//		Collections.swap(perm
//		}
//		
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
