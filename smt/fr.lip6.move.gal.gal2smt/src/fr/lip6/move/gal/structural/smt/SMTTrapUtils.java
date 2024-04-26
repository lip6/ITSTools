package fr.lip6.move.gal.structural.smt;

import static fr.lip6.move.gal.structural.smt.SMTUtils.buildSum;
import static fr.lip6.move.gal.structural.smt.SMTUtils.checkSat;
import static fr.lip6.move.gal.structural.smt.SMTUtils.declareBoolVariables;
import static fr.lip6.move.gal.structural.smt.SMTUtils.execAndCheckResult;
import static fr.lip6.move.gal.structural.smt.SMTUtils.initSolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IResponse;
import org.smtlib.IExpr.IFactory;
import org.smtlib.ISolver;
import org.smtlib.SMT;
import org.smtlib.command.C_assert;
import org.smtlib.ext.C_get_model;
import org.smtlib.impl.Script;
import org.smtlib.sexpr.ISexpr;
import org.smtlib.sexpr.ISexpr.ISeq;

import android.util.SparseBoolArray;
import android.util.SparseIntArray;
import fr.lip6.move.gal.gal2smt.options.SMTOptions;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.util.IntMatrixCol;

public class SMTTrapUtils {

	private static final int DEBUG = 0;

	static boolean refineResultsWithTraps(ISparsePetriNet sr, SMT smt,
			List<Script> properties, boolean[] done, List<SparseIntArray> parikhs, List<SparseIntArray> pors,
			List<String> verdicts, ISolver solver, boolean withWitness) {
		
		
		if (SMTOptions.getInstance().isDisableTraps()) {
			return false;
		}
		
		int nbdone = 0;
		
		boolean hasTraps = true;
		for (int iter=0; hasTraps; iter++) {
			hasTraps = false;
			System.out.println("TRAPS : Iteration " + iter);
		for (int pid = 0, pide = properties.size() ; pid < pide ; pid++) {
			if (done[pid]) {
				nbdone++;
				continue;
			} else {
				
				IResponse res = solver.push(1);
				if (res.isError()) {
					break;
				}
				Script traps = new Script();
				
				Script tocheck = properties.get(pid);				
				execAndCheckResult(tocheck, solver);
				String textReply = checkSat(solver);
	
				// are we finished ?
				if (textReply.equals("unsat")||textReply.equals("unknown")) {
					done[pid] = true;
					nbdone++;
					verdicts.set(pid, textReply);
					if (textReply.equals("unsat"))
						parikhs.set(pid, null);
				}
	
				if (textReply.equals("sat")) {
					
					textReply = refineWithTraps(sr, solver, smt, traps);
					if (textReply.equals("unsat")||textReply.equals("unknown")) {
						done[pid] = true;
						nbdone++;
						verdicts.set(pid, textReply);
						if (textReply.equals("unsat"))
							parikhs.set(pid, null);
					} else {
					
						SparseIntArray state = new SparseIntArray();
						SparseIntArray parikh = new SparseIntArray();
						SparseIntArray por = new SparseIntArray();
						boolean hasreals = DeadlockTester.queryVariables(state, parikh, por, solver);
						if (hasreals)
						{
							// Logger.getLogger("fr.lip6.move.gal").info("Solution in real domain found non-integer solution.");
							textReply = "real";
							done[pid] = true;
							nbdone++;
							verdicts.set(pid, textReply);
						} else {
							parikhs.set(pid, parikh);
							pors.set(pid, por);
						}
					}
				}
	
				res = solver.pop(1);
				if (res.isError()) {
					break;
				}
				
				if (! traps.commands().isEmpty()) {
					execAndCheckResult(traps, solver);
					traps = new Script();
					hasTraps = true;
				}
				
			}
		}
		}
	
		// true if no more props to check
		return nbdone == properties.size();
	}


	public static String refineWithTraps(ISparsePetriNet sr, ISolver solver,
			org.smtlib.SMT smt, Script traps) {
		
		if (SMTOptions.getInstance().isDisableTraps()) {
			return "sat";
		}
		
		long time = System.currentTimeMillis();		
		List<Integer> trap ;
		String textReply = "none";
		IFactory ef = smt.smtConfig.exprFactory;
		int added =0;
		int tested =0;
		
		int lasttrapsize = -1;
		int repeatTraps = 0;
		do {
			SparseIntArray state = new SparseIntArray();
			SparseIntArray pk = new SparseIntArray();
			if (DeadlockTester.queryVariables(state, pk, new SparseIntArray(),solver)) {
				return "sat";
			}
			trap = testTrapWithSMT(sr, state, null);
			if (DEBUG >=1)
				confirmTrap(sr,trap, state);
			tested++;
			if (!trap.isEmpty()) {
				
				
				if (trap.size() == lasttrapsize) {
					repeatTraps++;
				} else {
					lasttrapsize = trap.size();
					repeatTraps = 0;
				}
				if (repeatTraps >= 10) {
					// this is not looking good, might have a combinatorial number of traps
					Logger.getLogger("fr.lip6.move.gal").info("Trap strengthening procedure interrupted after too many repetitions " + (System.currentTimeMillis() -time) + " ms");
					break;
				}
				
				added++;
				// add a constraint
				List<IExpr> vars = trap.stream().map(n -> ef.symbol("s"+n)).collect(Collectors.toList());
				IExpr sum = buildSum(vars);
				Script s = new Script();
				ICommand constraint = new C_assert(ef.fcn(ef.symbol(">"), sum , ef.numeral(0)));
				s.add(constraint);
				if (traps != null) {
					traps.add(constraint);
				}
				execAndCheckResult(s, solver);
				textReply = checkSat(solver,  false);
				if (textReply.equals("unsat")) {
					Logger.getLogger("fr.lip6.move.gal").info("Trap strengthening procedure managed to obtain unsat after adding "+added+ " trap constraints in " + (System.currentTimeMillis() -time) + " ms");
					return textReply;
				} else if (textReply.equals("unknown")) {
					break;
				}
			}				
		} while (!trap.isEmpty());
		if (tested > 1 || added > 0) {
			Logger.getLogger("fr.lip6.move.gal").info("Trap strengthening (SAT) tested/added "+tested +"/" + added+ " trap constraints in " + (System.currentTimeMillis() -time) + " ms");
		}
		return textReply;
	}


	// computes a list of integers corresponding to a subset of places, of which at least one should be marked, and that contradicts the solution provided
	// the empty set => traps cannot contradict the solution.
	public static List<Integer> testTrapWithSMT(ISparsePetriNet srori, SparseIntArray solution) {
		return testTrapWithSMT(srori, solution, null);
	}


	// computes a list of integers corresponding to a subset of places, of which at least one should be marked, and that contradicts the solution provided
	// the empty set => traps cannot contradict the solution.
	public static List<Integer> testTrapWithSMT(ISparsePetriNet srori, SparseIntArray solution, SparseBoolArray declaredVars) {
		long time = System.currentTimeMillis();
		// step 0 : make sure there are finally empty places that were initially marked
		boolean feasible = false;
		for (int p=0, e= srori.getPnames().size(); p < e ; p++) {
			if (isDeclared(p,declaredVars) && srori.getMarks().get(p) > 0 && solution.get(p) == 0) {
				feasible = true;
				break;
			}
		}
		if (!feasible) {
			return new ArrayList<>();
		}

		StructuralReduction sr = new StructuralReduction(srori);			

		// step 1 : reduce net by removing finally marked places entirely from the picture
		{
			List<Integer> todrop = new ArrayList<>(solution.size());

			for (int i=solution.size()-1 ; i >= 0 ; i --) {
				todrop.add(solution.keyAt(i));
			}
			if (declaredVars != null) {
				int pid = 0;
				for (int i = 0, ie = declaredVars.size(); i < ie; i++) {
					int p = declaredVars.keyAt(i);
					for (; pid < p; pid++) {
						todrop.add(pid);
					}
					pid = p+1;
				}
				if (pid < sr.getPlaceCount()) {
					for (; pid < sr.getPlaceCount(); pid++) {
						todrop.add(pid);
					}
				}
			}
			todrop = new ArrayList<>(new HashSet<>(todrop));
			sr.dropPlaces(todrop, false, false,"");
		}
		// iterate reduction of unfeasible parts
		{
			int doneIter =0;
			Set<Integer> todropP = new HashSet<>();
			Set<Integer> todropT = new HashSet<>();
			do {
				doneIter =0;
				
				todropP.clear();
				todropT.clear();

				for (int tid=sr.getTnames().size()-1 ; tid >= 0 ; tid --) {
					if (sr.getFlowPT().getColumn(tid).size()==0) {
						// discard this transition, it cannot induce any additional constraints
						todropT.add(tid);
						doneIter++;
					} else if (sr.getFlowTP().getColumn(tid).size()==0) {
						SparseIntArray pt = sr.getFlowPT().getColumn(tid);
						// discard the transition, but also it's whole pre set
						for (int i=0, e = pt.size() ; i < e ; i++) {
							todropP.add(pt.keyAt(i));							
						}
						doneIter++;
						todropT.add(tid);
					}
				}
				if (!todropT.isEmpty()) {
					sr.dropTransitions(new ArrayList<>(todropT), false,"");
				}
				if (!todropP.isEmpty()) {
					sr.dropPlaces(new ArrayList<>(todropP), false, false,"");
				}
			} while (doneIter >0);
		}

		if (sr.getPnames().isEmpty()) {
			// fail
			return new ArrayList<>();
		}
		{
			boolean ok = false;
			for (int i=0, ie = sr.getPnames().size() ; i < ie ; i++) {
				if (sr.getMarks().get(i)>0 && solution.get(i)==0) {
					ok=true;
					break;
				}
			}
			if (!ok)
				return new ArrayList<>();
		}
		if (DEBUG >=1)
			Logger.getLogger("fr.lip6.move.gal").info("Computed a system of "+sr.getPnames().size()+"/"+ srori.getPnames().size() + " places and "+sr.getTnames().size()+"/"+ srori.getTnames().size() + " transitions for Trap test. " + (System.currentTimeMillis()-time) +" ms");

		if (! sr.getPnames().isEmpty()) {
			// okay so we have some candidate places that could form a trap here

			// init a solver
			SMT smt = new SMT();
			IFactory ef = smt.smtConfig.exprFactory;
			ISolver solver = initSolver(smt, "QF_UF", 50, 120);
			Script script = declareBoolVariables(sr.getPnames().size(), "s", smt);
			execAndCheckResult(script, solver);

			// now feed constraints in

			// solution should be a non empty set, containing at least one initially marked place that is now empty
			{
				List<IExpr> oring = new ArrayList<>();
				for (int i=0; i < sr.getPnames().size() ; i++) {
					if (sr.getMarks().get(i) >0 && solution.get(i)==0)
						oring.add(ef.symbol("s"+i));
				}

				if (oring.isEmpty()) {
					// failed
					solver.exit();
					return new ArrayList<>();
				}
				IExpr or = SMTUtils.makeOr(oring);
				Script s = new Script();
				s.add(new C_assert(or));
				execAndCheckResult(s, solver);
			}

			// transition constraints now
			IntMatrixCol tflowPT = sr.getFlowPT().transpose();
			// for each place p
			for (int  pid = 0 ; pid < sr.getPnames().size() ; pid++)  {


				//   for each transition t feeding from p
				SparseIntArray tpt = tflowPT.getColumn(pid);

				Script sc = new Script();

				for (int i=0, e=tpt.size(); i < e ; i++ ) {
					//        one place fed by t is in the set
					int tid = tpt.keyAt(i);
					SparseIntArray outs = sr.getFlowTP().getColumn(tid);
					List<IExpr> toass = new ArrayList<>();

					if (outs.size() == 0) {
						// this is bad : this place cannot be in any trap
						// just break out
						sc.commands().clear();
						IExpr constraint = ef.fcn(ef.symbol("not"), ef.symbol("s"+pid));
						sc.add(new C_assert(constraint));
						break;
					} else if (outs.get(pid) > 0) {
						// transition feeds back into p, this constraint is trivially satisfied, just remove it
						continue;
					} else {
						for (int j=0, ee=outs.size() ; j < ee ; j++) {
							// one of these places must be in the trap
							int ppid = outs.keyAt(j);
							toass.add(ef.symbol("s"+ ppid));						
						}
					}
					IExpr or = SMTUtils.makeOr(toass); 

					// assert the constraint for this transition
					IExpr constraint = ef.fcn(ef.symbol("=>"), ef.symbol("s"+pid), or);
					//					if (toass.isEmpty()) {
					//						// this can happen if only transitions take and put in the place
					//						constraint = ef.fcn(ef.symbol("not"), ef.symbol("s"+pid));
					//					}
					sc.add(new C_assert(constraint));
				}

				execAndCheckResult(sc, solver);
				String res = checkSat(solver);
				if ("unsat".equals(res)) {
					// meh, we (already) cannot build a trap
					solver.exit();
					return new ArrayList<>();
				}
			}
			// looks real good, we have not obtained UNSAT yet

			// try to minimize the trap
			long ttime = System.currentTimeMillis();
			List<IExpr> tosum = new ArrayList<>(sr.getPnames().size());
			for (int i=0, e=sr.getPnames().size(); i < e; i++ ) {
				IExpr ss = ef.symbol("s"+i);
				tosum.add(ss);
			}
			solver.minimize(ef.fcn(ef.symbol("+"), tosum));
			checkSat(solver,  false);
			long minitime = (System.currentTimeMillis() - ttime);	

			List<Boolean> trap = new ArrayList<>(sr.getPnames().size());
			for (int i=0, e=sr.getPnames().size(); i < e; i++ ) {
				trap.add(false);
			}
			queryBoolVariables(trap,solver);
			List<Integer> res = new ArrayList<>();
			int tsz = 0;
			for (int i=0 ; i < trap.size() ; i++) {
				if (trap.get(i)) {
					res.add(srori.getPnames().indexOf(sr.getPnames().get(i)));
					tsz++;
				}
			}
			solver.exit();
			Logger.getLogger("fr.lip6.move.gal").info("Deduced a trap "+ (DeadlockTester.DEBUG>=1 ? res : "")+"composed of "+tsz+" places in "+ (System.currentTimeMillis()-time) +" ms of which "+minitime+" ms to minimize.");
			return res;
		}

		return new ArrayList<>();
	}

	
	private static boolean isDeclared(int p, SparseBoolArray declaredVars) {
		return declaredVars == null || declaredVars.get(p);
	}


	private static void confirmTrap(ISparsePetriNet sr, List<Integer> trap, SparseIntArray state) {
		if (trap.isEmpty())
			return;
		Set<Integer> targets = new HashSet<>(trap);
		for (int t = 0, e=sr.getTnames().size() ; t < e ;  t++) {
			SparseIntArray colpt = sr.getFlowPT().getColumn(t);
			boolean eats = false;
			for (int i=0 ; i < colpt.size() ; i++) {
				if (targets.contains(colpt.keyAt(i))) {
					eats = true;
					break;
				}
			}
			if (eats) {
				SparseIntArray coltp = sr.getFlowTP().getColumn(t);
				boolean feeds = false;
				for (int i=0 ; i < coltp.size() ; i++) {
					if (targets.contains(coltp.keyAt(i))) {
						feeds = true;
						break;
					}
				}
				if (!feeds) {
					System.err.println("This is NOT a trap !");
					return;
				}
			}
		}
		// make sure the trap is empty in target state
		for (int i : trap) {
			if (state.get(i) > 0) {
				System.err.println("This trap is already marked !");
				return;
			}
		}
		System.err.println("Trap OK");
	}

	// note the List should be large enough
	private static void queryBoolVariables (List<Boolean> res, ISolver solver) {
		IResponse r = new C_get_model().execute(solver);
		if (r instanceof ISeq) {
			ISeq seq = (ISeq) r;
			for (ISexpr v : seq.sexprs()) {
				if (v instanceof ISeq) {
					ISeq vseq = (ISeq) v;
					if (vseq.sexprs().get(1).toString().startsWith("s")) {
						int tid = Integer.parseInt( vseq.sexprs().get(1).toString().substring(1) );
						boolean value = Boolean.parseBoolean( vseq.sexprs().get(vseq.sexprs().size()-1).toString());

						if (value) {							
							res.set(tid, value);					
						}
					}
				}
			}
		}
	}
}
