package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IPrinter;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.INumeral;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.IResponse;
import org.smtlib.ISolver;
import org.smtlib.SMT;
import org.smtlib.Utils;
import org.smtlib.command.C_assert;
import org.smtlib.command.C_get_value;
import org.smtlib.ext.C_get_model;
import org.smtlib.impl.Script;
import org.smtlib.sexpr.ISexpr;
import org.smtlib.sexpr.ISexpr.ISeq;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.util.MatrixCol;

public class DeadlockTester {

	/**
	 * Unsat answer means no deadlocks, SAT means nothing, as we are working with an overapprox.
	 * @param sr
	 * @param solverPath
	 * @param isSafe 
	 * @return
	 */
	public static SparseIntArray testDeadlocksWithSMT(StructuralReduction sr, String solverPath, boolean isSafe) {
		List<Integer> tnames = new ArrayList<>();
		MatrixCol sumMatrix = computeReducedFlow(sr, tnames);

		long time = System.currentTimeMillis();
		Set<SparseIntArray> invar = InvariantCalculator.computePInvariants(sumMatrix, sr.getPnames());		
		//InvariantCalculator.printInvariant(invar, sr.getPnames(), sr.getMarks());
		Logger.getLogger("fr.lip6.move.gal").info("Computed "+invar.size()+" place invariants in "+ (System.currentTimeMillis()-time) +" ms");
		
		
		boolean solveWithReals = true;
		SparseIntArray parikh = new SparseIntArray();
		String reply = areDeadlocksPossible(sr, solverPath, isSafe, sumMatrix, tnames, invar, solveWithReals , parikh );
		if ("real".equals(reply)) {
			reply = areDeadlocksPossible(sr, solverPath, isSafe, sumMatrix, tnames, invar, false , parikh);
		}
		
		if (! "unsat".equals(reply)) {
			return parikh;
		} else {
			return null;
		}
	}
	
	public static List<SparseIntArray> testUnreachableWithSMT(List<Expression> tocheck, StructuralReduction sr, String solverPath,
			boolean isSafe) {
		List<SparseIntArray> verdicts = new ArrayList<>();
		
		List<Integer> tnames = new ArrayList<>();
		MatrixCol sumMatrix = computeReducedFlow(sr, tnames);

		long time = System.currentTimeMillis();
		Set<SparseIntArray> invar = InvariantCalculator.computePInvariants(sumMatrix, sr.getPnames());		
		//InvariantCalculator.printInvariant(invar, sr.getPnames(), sr.getMarks());
		Logger.getLogger("fr.lip6.move.gal").info("Computed "+invar.size()+" place invariants in "+ (System.currentTimeMillis()-time) +" ms");
		
		
		for (int i=0, e=tocheck.size() ; i < e ; i++) {
			boolean solveWithReals = true;
			SparseIntArray parikh = new SparseIntArray();
			IExpr smtexpr = tocheck.get(i).accept(new ExprTranslator());
			Script property = new Script();
			property.add(new C_assert(smtexpr));
			String reply = verifyPossible(sr, property, solverPath, isSafe, sumMatrix, tnames, invar, solveWithReals, parikh);
			if ("real".equals(reply)) {
				reply = verifyPossible(sr, property, solverPath, isSafe, sumMatrix, tnames, invar, false, parikh);
			}
		
			if (! "unsat".equals(reply)) {
				verdicts.add(parikh);
			} else {
				verdicts.add(null);
			}
		}
		
		return verdicts ;
	}
	

	private static String areDeadlocksPossible(StructuralReduction sr, String solverPath, boolean isSafe,
			MatrixCol sumMatrix, List<Integer> tnames, Set<SparseIntArray> invar, boolean solveWithReals, SparseIntArray parikh) {
		Script scriptAssertDead = assertNetIsDead(sr);
		return verifyPossible(sr, scriptAssertDead, solverPath, isSafe, sumMatrix, tnames, invar, solveWithReals, parikh);
	}
	
	private static String verifyPossible(StructuralReduction sr, Script tocheck, String solverPath, boolean isSafe,
			MatrixCol sumMatrix, List<Integer> tnames, Set<SparseIntArray> invar, boolean solveWithReals, SparseIntArray parikh) {
		long time;
		org.smtlib.SMT smt = new SMT();
		ISolver solver = initSolver(solverPath, smt,solveWithReals,3000,300);		
		{
			// STEP 1 : declare variables, assert net is dead.
			time = System.currentTimeMillis();
			Script varScript = declareVariables(sr.getPnames().size(), "s", isSafe, smt,solveWithReals);
			execAndCheckResult(varScript, solver);
			// add the script's constraints
			execAndCheckResult(tocheck, solver);
		}

		// STEP 2 : declare and assert invariants 
		String textReply = assertInvariants(invar, sr, solver, smt,true);

		// are we finished ?
		if (textReply.equals("unsat")) {
			solver.exit();
			return textReply;
		}

		// STEP 3 : go heavy, use the state equation to refine our solution
		time = System.currentTimeMillis();
		Logger.getLogger("fr.lip6.move.gal").info("Adding state equation constraints to refine reachable states.");
		Script script = declareStateEquation(sumMatrix, sr, smt,solveWithReals);
		
		execAndCheckResult(script, solver);
		textReply = checkSat(solver, smt, true);
		Logger.getLogger("fr.lip6.move.gal").info("Absence of deadlock check using state equation in "+ (System.currentTimeMillis()-time) +" ms returned " + textReply);

		
		
		if (textReply.equals("sat") && solveWithReals) {			
			IResponse r = new C_get_model().execute(solver);
			if (hasNonIntegerElements(r)) {
				System.out.println("Solution in real domain found non-integer solution.");
				textReply = "real";
			}
//			queryState(ef2, sr, solver);
//			queryParikh(ef2, tnames, solver);
		}
		
		if (textReply.equals("sat")) {
			textReply = refineWithTraps(sr, tnames, solver, smt, solverPath);
		}
		
		if (textReply.equals("sat") && parikh != null) {			
			SparseIntArray state = new SparseIntArray();
			queryVariables(state, parikh, tnames, solver);
			System.out.println("SAT in Deadlock state : ");
			for (int i=0 ; i < state.size() ; i++) {
				System.out.print(sr.getPnames().get(state.keyAt(i))+"="+ state.valueAt(i)+", ");
			}
			System.out.println();
		}
		
		
		solver.exit();
		return textReply;
	}

	private static String refineWithTraps(StructuralReduction sr, List<Integer> tnames, ISolver solver,
			org.smtlib.SMT smt, String solverPath) {
		List<Integer> trap ;
		String textReply = "sat";
		IFactory ef = smt.smtConfig.exprFactory;
		int added =0;
		do {
			SparseIntArray state = new SparseIntArray();
			SparseIntArray pk = new SparseIntArray();
			queryVariables(state, pk, tnames, solver);
			trap = testTrapWithSMT(sr, solverPath, state);
			if (!trap.isEmpty()) {
				added++;
				// add a constraint
				List<IExpr> vars = trap.stream().map(n -> ef.symbol("s"+n)).collect(Collectors.toList());
				IExpr sum = buildSum(ef, vars);
				Script s = new Script();
				s.add(new C_assert(ef.fcn(ef.symbol(">"), sum , ef.numeral(0))));
				execAndCheckResult(s, solver);
				textReply = checkSat(solver, smt, true);
				if (textReply.equals("unsat")) {
					System.out.println("Trap strengthening procedure managed to obtain unsat after adding "+added+ " trap constraints.");
					return textReply;
				}
			}				
		} while (!trap.isEmpty());
		return textReply;
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
	
	private static void queryVariables(SparseIntArray state, SparseIntArray parikh, List<Integer> tnames,
			ISolver solver) {
		IResponse r = new C_get_model().execute(solver);			
		if (r instanceof ISeq) {
			ISeq seq = (ISeq) r;
			for (ISexpr v : seq.sexprs()) {
				if (v instanceof ISeq) {
					ISeq vseq = (ISeq) v;
					if (vseq.sexprs().get(1).toString().startsWith("t")) {
						int tid = Integer.parseInt( vseq.sexprs().get(1).toString().substring(1) );
						int value = (int) Float.parseFloat( vseq.sexprs().get(vseq.sexprs().size()-1).toString() );
						if (value != 0) 
							parikh.put(tnames.get(tid), value);
					} else if (vseq.sexprs().get(1).toString().startsWith("s")) {
						int tid = Integer.parseInt( vseq.sexprs().get(1).toString().substring(1) );
						int value =(int) Float.parseFloat( vseq.sexprs().get(vseq.sexprs().size()-1).toString() );
						if (value != 0) 
							state.put(tid, value);							
					}
				}
			}
		}
	}

	private static void execAndCheckResult(Script script, ISolver solver) {
		IResponse res = script.execute(solver);
		if (res.isError()) {
			throw new RuntimeException("SMT solver raised an error when submitting script. Raised " + res.toString());
		}
	}
	

	public static List<Integer> testImplicitTransitionWithSMT(StructuralReduction sr, String solverPath) {
		long time = System.currentTimeMillis();		
		org.smtlib.SMT smt = new SMT();
		List<Integer> redundantTrans =new ArrayList<>();

		try {
			// using integers currently
			boolean solveWithReals = false;
			ISolver solver = initSolver(solverPath, smt,solveWithReals,25,200);

			time = System.currentTimeMillis();		
			// now for each transition
			for (int tid = 0, ntrans = sr.getTnames().size(); tid < ntrans; tid++) {
				SparseIntArray tiPT = sr.getFlowPT().getColumn(tid);
				SparseIntArray tiTP = sr.getFlowTP().getColumn(tid);
				List<Integer> candidates = new ArrayList<>();
				// for each transition t_j whose support is a subset of t_i's support			
				for (int tjd=0; tjd < ntrans ; tjd++) {
					// i==j case : skip
					if (tid == tjd)
						continue;
					SparseIntArray tjPT = sr.getFlowPT().getColumn(tjd);
					SparseIntArray tjTP = sr.getFlowTP().getColumn(tjd);
					if (SparseIntArray.greaterOrEqual(tiPT, tjPT) && SparseIntArray.greaterOrEqual(tiTP, tjTP)) {
						candidates.add(tjd);
					}
				}

				if (! candidates.isEmpty()) {
					solver.push(1);
					// declare an alpha_j
					Script varScript = declareVariables(candidates.size(), "t", false, smt,solveWithReals);
					execAndCheckResult(varScript, solver);

					Script script = new Script();
					Set<Integer> support = new TreeSet<>();
					for (int i=0; i < tiPT.size() ; i++) {
						support.add(tiPT.keyAt(i));
					}
					for (int i=0; i < tiTP.size() ; i++) {
						support.add(tiTP.keyAt(i));
					}

					//				System.out.println("Testing transition "+sr.getTnames().get(tid) + " against " + candidates.stream().map(n -> sr.getTnames().get(n)).collect(Collectors.toList()));
					//				System.out.println(sr.getTnames().get(tid) + " :" + sr.getFlowPT().getColumn(tid) + " -> " + sr.getFlowTP().getColumn(tid));
					//				System.out.println(" vs . ");
					//				for (int ttid : candidates) {
					//					System.out.println(sr.getTnames().get(ttid) + " :" + sr.getFlowPT().getColumn(ttid) + " -> " + sr.getFlowTP().getColumn(ttid));
					//					
					//				}

					IFactory ef = smt.smtConfig.exprFactory;
					for (int p : support) {
						// assert equality of effects
						// - pre (p,ti) + post(p,ti) = Sum_j alpha_j * ( - pre(p,tj) + post(p,tj) )
						int prei = tiPT.get(p);
						int vali = - prei + tiTP.get(p);
						List<IExpr> toadd = new ArrayList<>();
						List<IExpr> torem = new ArrayList<>();

						List<IExpr> prePT = new ArrayList<>();

						for (int cand =0 ; cand < candidates.size() ; cand++) {
							int tjd = candidates.get(cand);
							SparseIntArray tjPT = sr.getFlowPT().getColumn(tjd);
							SparseIntArray tjTP = sr.getFlowTP().getColumn(tjd);
							int prej = tjPT.get(p);
							int valj = - prej + tjTP.get(p);
							if (valj != 0) {
								IExpr ss = ef.symbol("t"+cand);
								if (valj != 1 && valj != -1) {
									ss = ef.fcn(ef.symbol("*"), ef.numeral( Math.abs(valj)), ss );
								} 
								if (valj > 0) 
									toadd.add(ss);
								else
									torem.add(ss);
							}
							if (prej != 0) {
								IExpr ss = ef.symbol("t"+cand);
								if (prej != 1) {
									ss = ef.fcn(ef.symbol("*"), ef.numeral( Math.abs(prej)), ss );
								} 
								prePT.add(ss);
							}
						}
						// effect of ti = effect of ponderated sum of tj
						// vali + torem = toadd
						if (vali < 0) {
							toadd.add(ef.numeral(-vali));
						} else {
							torem.add(ef.numeral(vali));
						}
						IExpr lhs = buildSum(ef, torem);
						IExpr rhs = buildSum(ef, toadd);

						script.add(new C_assert(ef.fcn(ef.symbol("="), lhs, rhs)));


						script.add(new C_assert(ef.fcn(ef.symbol(">="), ef.numeral(prei), buildSum(ef, prePT))));
					}
					execAndCheckResult(script, solver);
					String textReply = checkSat(solver, smt, false);


					// are we finished ?
					if (textReply.equals("sat")) {
						Logger.getLogger("fr.lip6.move.gal").fine("Transition "+sr.getTnames().get(tid) + " with index "+tid+ " is redundant.");
						redundantTrans.add(tid);
					}

					solver.pop(1);
					Logger.getLogger("fr.lip6.move.gal").fine("Trans "+sr.getTnames().get(tid) + " with index "+tid+ " gave us " + textReply + " in " + (System.currentTimeMillis()-time) +" ms");
				}
			}

			Logger.getLogger("fr.lip6.move.gal").info("Redundant transitions in "+ (System.currentTimeMillis()-time) +" ms returned " + redundantTrans);

		} catch (Exception e) {
			Logger.getLogger("fr.lip6.move.gal").warning("SMT solver raised an exception "+ e.getMessage() + " returning currently detected " + redundantTrans.size() + " redundant transitions ");			
		}
		return redundantTrans;
	}

	private static IExpr buildSum(IFactory ef, List<IExpr> torem) {
		IExpr lhs;
		if (torem.isEmpty()) {
			lhs = ef.numeral(0);
		} else {
			if (torem.size() == 1) {
				lhs = torem.get(0);
			} else {
				lhs = ef.fcn(ef.symbol("+"), torem);
			}
		}
		return lhs;
	}
	// computes a list of integers corresponding to a subset of places, of which at least one should be marked, and that contradicts the solution provided
	// the empty set => traps cannot contradict the solution.
	public static List<Integer> testTrapWithSMT(StructuralReduction srori, String solverPath, SparseIntArray solution) {
		long time = System.currentTimeMillis();
		StructuralReduction sr = srori.clone();
		// step 1 : reduce net by removing marked places entirely from the picture
		{
			List<Integer> todrop = new ArrayList<>(solution.size());

			for (int i=solution.size()-1 ; i >= 0 ; i --) {
				todrop.add(solution.keyAt(i));
			}
			sr.dropPlaces(todrop, false, false);
		}
		// iterate reduction of unfeasible parts
		{
			int doneIter =0;
			do {
				doneIter =0;
				Set<Integer> todropP = new TreeSet<>();
				Set<Integer> todropT = new TreeSet<>();

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
					sr.dropTransitions(new ArrayList<>(todropT), false);
				}
				if (!todropP.isEmpty()) {
					sr.dropPlaces(new ArrayList<>(todropP), false, false);
				}
			} while (doneIter >0);
		}
		if (sr.getPnames().isEmpty()) {
			// fail
			return new ArrayList<>();
		}
		Logger.getLogger("fr.lip6.move.gal").info("Computed a system of "+sr.getPnames().size()+"/"+ srori.getPnames().size() + " places and "+sr.getTnames().size()+"/"+ srori.getTnames().size() + " transitions for Trap test. " + (System.currentTimeMillis()-time) +" ms");
		
		if (! sr.getPnames().isEmpty()) {
			// okay so we have some candidate places that could form a trap here
			
			// init a solver
			SMT smt = new SMT();
			IFactory ef = smt.smtConfig.exprFactory;
			ISolver solver = initSolver(solverPath, smt, "QF_UF", 50, 120);
			Script script = declareBoolVariables(sr.getPnames().size(), "s", smt);
			execAndCheckResult(script, solver);
			
			// now feed constraints in
			
			// solution should be a non empty set, containing at least one initially marked place
			{
				List<IExpr> oring = new ArrayList<>();
				for (int i=0; i < sr.getPnames().size() ; i++) {
					if (sr.getMarks().get(i) >0)
						oring.add(ef.symbol("s"+i));
				}
				IExpr or;
				if (oring.isEmpty()) {
					// failed
					return new ArrayList<>();
				} else if (oring.size() > 1) {
					or = ef.fcn(ef.symbol("or"), oring);
				} else {
					or = oring.get(0);
				}
				Script s = new Script();
				s.add(new C_assert(or));
				execAndCheckResult(s, solver);
			}
			
			// transition constraints now
			MatrixCol tflowPT = sr.getFlowPT().transpose();
			// for each place p
			for (int  pid = 0 ; pid < sr.getPnames().size() ; pid++)  {
				//   for each transition t feeding from p
				SparseIntArray tpt = tflowPT.getColumn(pid);
				List<IExpr> toass = new ArrayList<>();
				
				for (int i=0, e=tpt.size(); i < e ; i++ ) {
					//        one place fed by t is in the set
					int tid = tpt.keyAt(i);
					SparseIntArray outs = sr.getFlowTP().getColumn(tid);
					if (outs.get(pid) > 0) {
						// transition feeds back into p, this constraint is trivially satisfied, just remove it
						continue;
					} else {
						for (int j=0, ee=outs.size() ; j < ee ; j++) {
							// one of these places must be in the trap
							int ppid = outs.keyAt(j);
							toass.add(ef.symbol("s"+ ppid));						
						}
					}
				}
				IExpr or = null;
				// or the places :  one of them at least must be true
				if (toass.isEmpty()) {
					throw new RuntimeException("expected non empty output set");
				} else if (toass.size() == 1) {
					or = toass.get(0);
				} else {
					or = ef.fcn(ef.symbol("or"), toass);
				}
				// assert the constraint for this transition
				IExpr constraint = ef.fcn(ef.symbol("=>"), ef.symbol("s"+pid), or);
				Script sc = new Script();
				sc.add(new C_assert(constraint));
				execAndCheckResult(sc, solver);
				String res = checkSat(solver, smt);
				if ("unsat".equals(res)) {
					// meh, we (already) cannot build a trap
					solver.exit();
					return new ArrayList<>();
				}
			}
			// looks real good, we have not obtained UNSAT yet
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
			Logger.getLogger("fr.lip6.move.gal").info("Deduced a trap composed of "+tsz+" places in "+ (System.currentTimeMillis()-time) +" ms");
			return res;
		}
		
		return new ArrayList<>();
	}
		
	public static List<Integer> testImplicitWithSMT(StructuralReduction sr, String solverPath, boolean isSafe, boolean withStateEquation) {
		List<Integer> implicitPlaces =new ArrayList<>();
		List<Integer> tnames = new ArrayList<>();
		MatrixCol tFlowPT = null;
		long time = System.currentTimeMillis();
		long orioritime = time;
		try {
			MatrixCol sumMatrix = computeReducedFlow(sr, tnames);
			Set<SparseIntArray> invar ;

			try {
				invar = InvariantCalculator.computePInvariants(sumMatrix, sr.getPnames());		
				//InvariantCalculator.printInvariant(invar, sr.getPnames(), sr.getMarks());
				Logger.getLogger("fr.lip6.move.gal").info("Computed "+invar.size()+" place invariants in "+ (System.currentTimeMillis()-time) +" ms");
			} catch (ArithmeticException e) {
				invar = new HashSet<>();
				Logger.getLogger("fr.lip6.move.gal").info("Invariants computation overflowed in "+ (System.currentTimeMillis()-time) +" ms");
			}
			org.smtlib.SMT smt = new SMT();

			// using reals currently
			boolean solveWithReals = true;
			ISolver solver = initSolver(solverPath, smt,solveWithReals,30,60);
			{
				// STEP 1 : declare variables
				time = System.currentTimeMillis();
				Script varScript = declareVariables(sr.getPnames().size(), "s", isSafe, smt,solveWithReals);
				execAndCheckResult(varScript, solver);			
			}

			// STEP 2 : declare and assert invariants 
			String textReply = assertInvariants(invar, sr, solver, smt,false);

			// are we finished ?
			if ("sat".equals(textReply) && withStateEquation) {
				// STEP 3 : go heavy, use the state equation to refine our solution
				time = System.currentTimeMillis();
				Script script = declareStateEquation(sumMatrix, sr, smt,solveWithReals);

				execAndCheckResult(script, solver);
				textReply = checkSat(solver, smt, false);
				//Logger.getLogger("fr.lip6.move.gal").info("Implicit Places using state equation in "+ (System.currentTimeMillis()-time) +" ms returned " + textReply);
			}

			time = System.currentTimeMillis();
			long oritime = time;
			tFlowPT = sr.getFlowPT().transpose();

			for (int placeid = 0, sz = sr.getPnames().size(); placeid < sz; placeid++) {
				if (sr.getUntouchable().get(placeid)) {
					continue;
				}
				
				// assert implicit
				Script pimplicit = assertPimplict (placeid,tFlowPT,sr,smt);
				if (pimplicit.commands().isEmpty()) {
					continue;
				}
				solver.push(1);
				execAndCheckResult(pimplicit, solver);

				textReply = checkSat(solver, smt, false);

				// are we finished ?
				if (textReply.equals("unsat")) {
					Logger.getLogger("fr.lip6.move.gal").fine("Place "+sr.getPnames().get(placeid) + " with index "+placeid+ " is implicit.");
					implicitPlaces.add(placeid);
				}

				solver.pop(1);
				Logger.getLogger("fr.lip6.move.gal").fine("Place "+sr.getPnames().get(placeid) + " with index "+placeid+ " gave us " + textReply + " in " + (System.currentTimeMillis()-time) +" ms");
				long deltat = System.currentTimeMillis() - time;
				if (deltat >= 30000) {
					time = System.currentTimeMillis();
					Logger.getLogger("fr.lip6.move.gal").info("Performed "+placeid +"/"+ sz + " implicitness test of which " + implicitPlaces.size() + " returned IMPLICIT in " + (time -oritime)/1000 + " seconds." );				
					if (time - oritime > 120000 && implicitPlaces.isEmpty()) {
						Logger.getLogger("fr.lip6.move.gal").info("Timeout of Implicit test with SMT after "+ (time -oritime)/1000 + " seconds.");
						break;
					}
				}
			}
			Logger.getLogger("fr.lip6.move.gal").info("Implicit Places using invariants "+ (withStateEquation?"and state equation ":"")+ "in "+ (System.currentTimeMillis()-orioritime) +" ms returned " + implicitPlaces);

			solver.exit();
		} catch (Exception e) {
			Logger.getLogger("fr.lip6.move.gal").info("Implicit Places with SMT raised an exception" + e.getMessage() + " after "+ (System.currentTimeMillis()-orioritime) +" ms ");			
		}
		
		if (tFlowPT == null) {
			tFlowPT = sr.getFlowPT().transpose();
		}
		
		List<Integer> realImplicit = new ArrayList<Integer>();
		//Collections.sort(implicitPlaces, (a,b) -> -sr.getPnames().get(a).compareTo(sr.getPnames().get(b)));
		// so that this variable is effectively final for lambda capture
		MatrixCol tfPT = tFlowPT;
		Collections.sort(implicitPlaces, (a,b) -> -Integer.compare(tfPT.getColumn(a).size(),tfPT.getColumn(b).size()));
		for (int i=0; i < implicitPlaces.size() ; i++) {
			int pi = implicitPlaces.get(i);
			SparseIntArray piPT = tFlowPT.getColumn(pi);
			boolean isOk = true;
			// make sure that the outputs of this place will still have at least one condition
			for (int j=0; j < piPT.size() ; j++) {
				int tid = piPT.keyAt(j);
				SparseIntArray pret = sr.getFlowPT().getColumn(tid);
				boolean otherImplicit = false;
				for (int k=0; k < pret.size() ; k++) {
					int pp = pret.keyAt(k);
					if (realImplicit.contains(pp)) {
						otherImplicit = true;
						break;
					}
				}
				if (otherImplicit) {
					isOk = false;
					break;
				}
			}
			if (isOk) {
				realImplicit.add(pi);
			}
		}
		if (realImplicit.size() < implicitPlaces.size()) {
			Logger.getLogger("fr.lip6.move.gal").info("Actually due to overlaps returned " + realImplicit);
		}
		Collections.sort(realImplicit);
		return realImplicit;
	}

	private static Script assertPimplict(int placeid, MatrixCol tFlowPT, StructuralReduction sr, SMT smt) {
		
		IFactory ef = smt.smtConfig.exprFactory;
		// for each transition that takes from P				
		SparseIntArray eatP = tFlowPT.getColumn(placeid);
		List<IExpr> orConds = new ArrayList<>();
		for (int i=0; i < eatP.size() ; i++) {
			int tid = eatP.keyAt(i);
			int value = eatP.valueAt(i);
			
			// assert that "t is enabled, disregarding the fact it needs P marked with >= value"
			SparseIntArray preT = sr.getFlowPT().getColumn(tid);
			List<IExpr> conds = new ArrayList<>();
			for (int j=0; j < preT.size() ; j++) {
				int pfrom = preT.keyAt(j);
				int pval = preT.valueAt(j);
				if (pfrom == placeid) {
					continue;
				}
				// M(pfrom) >= pval
				conds.add(
						ef.fcn(ef.symbol(">="), 
								ef.symbol("s"+pfrom),
								// >= pval
								ef.numeral(pval)));
			}
			
			if (conds.isEmpty()) {
				// p controls this output fully, it is *not* implicit
				return new Script();
			}
			// P is not marked enough to enable T
			IExpr notMarked = ef.fcn(ef.symbol("<"), 
					ef.symbol("s"+placeid),
					// < value
					ef.numeral(value));
			
			conds.add(notMarked);
			// build up the full AND of constraints
			IExpr tenab = ef.fcn(ef.symbol("and"), conds);
			if (conds.size() == 1) {
				tenab = conds.get(0);
			} else if (conds.isEmpty()) {
				tenab = ef.symbol("true");
			}
			orConds.add(tenab);
		}
		
		Script script = new Script();
		IExpr res;
		if (orConds.isEmpty()) {
			return script;
		} else if (orConds.size() == 1) {
			res = orConds.get(0);
		} else {
			res = ef.fcn(ef.symbol("or"), orConds);
		}
		// t is enabled without P => P lacks tokens
		// If this assertion is sat, P is not implicit
		// if we get unsat, P is implicit w.r.t. this transition, it passes one implicitness test.
		script.add(new C_assert(res));

		return script;
	}



	/** Create a script that constrains state variables to satisfy the Petri net state equation.
	 * 
	 * In practice we do not use all transitions, only significant representatives. 
	 * We add a variable for each transition giving it's firing count.
	 * We add an assertion for each place forcing it to satisfy the state equation from M0.
	 * 
	 * @param sumMatrix reduced combined flow matrix as computed in computeReducedFlow()
	 * @param sr the Petri net (to grab initial marking from)
	 * @param smt for solver factories
	 * @return a Script that contains appropriate declarations and assertions implementing the state equation.
	 */
	private static Script declareStateEquation(MatrixCol sumMatrix, StructuralReduction sr, org.smtlib.SMT smt, boolean solveWithReals) {
		
		
		
		// declare a set of variables for holding Parikh count of the transition
		Script script = declareVariables(sumMatrix.getColumnCount(), "t", false, smt,solveWithReals);

		IFactory ef = smt.smtConfig.exprFactory;
		// we work with one constraint for each place => use transposed
		MatrixCol mat = sumMatrix.transpose();
		for (int varindex = 0 ; varindex < mat.getColumnCount() ; varindex++) {

			SparseIntArray line = mat.getColumn(varindex);
			// assert : x = m0.x + X0*C(t0,x) + ...+ XN*C(Tn,x)
			List<IExpr> exprs = new ArrayList<IExpr>();

			// m0.x
			exprs.add(ef.numeral(sr.getMarks().get(varindex)));

			//  Xi*C(ti,x)
			for (int i = 0 ; i < line.size() ; i++) {
				int val = line.valueAt(i);
				int trindex = line.keyAt(i);
				IExpr nbtok ;
				if (val > 0) 
					nbtok = ef.numeral(val);
				else if (val < 0)
					nbtok = ef.fcn(ef.symbol("-"), ef.numeral(-val));
				else 
					continue;
				exprs.add(ef.fcn(ef.symbol("*"), 
						ef.symbol("t"+trindex),
						nbtok));
			}

			script.add(
					new C_assert(
							ef.fcn(ef.symbol("="), 
									ef.symbol("s"+varindex),
									// = m0.x + X0*C(t0,x) + ...+ XN*C(Tn,x)
									ef.fcn(ef.symbol("+"), exprs))));
		}
		return script;
	}


	/**
	 * Feeds the invariants to the solver as a set of constraints over the state variables.
	 * The feeding is done in two steps, first only semi flows, then the full generalized flows.
	 * 
	 * The goal is to get "unsat" result, so more constraints means more likely to be unsat, 
	 * but it's not clear that we need all constraints to be unsat (the contradiction could come early).
	 * We declare invariants in parts, if we get "unsat" return it immediately, otherwise add more invariants,
	 * check sat again and return the solver's response whether "sat" or "unsat". 
	 * 
	 * @param invar the invariants to declare, as obtained from the InvariantCalculator module.
	 * @param sr the Petri net
	 * @param solver we expect the solver to already know about variables
	 * @param smt access to smt factories
	 * @return "unsat" is what we hope for, could also return "sat" and maybe "unknown". 
	 */
	private static String assertInvariants(Set<SparseIntArray> invar, StructuralReduction sr, ISolver solver,
			org.smtlib.SMT smt, boolean verbose) {

		long time = System.currentTimeMillis();
		Script invpos = new Script();
		Script invneg = new Script();
		declareInvariants(invar,sr,invpos,invneg,smt);

		String textReply = "sat";
		// add the positive only for now
		if (!invpos.commands().isEmpty()) {
			execAndCheckResult(invpos, solver);		
			textReply = checkSat(solver, smt, true);
			if (verbose) Logger.getLogger("fr.lip6.move.gal").info("Absence of deadlock check using  "+invpos.commands().size()+" positive place invariants in "+ (System.currentTimeMillis()-time) +" ms returned " + textReply);
		}

		if (textReply.equals("sat") && ! invneg.commands().isEmpty()) {
			time = System.currentTimeMillis();
			if (verbose) Logger.getLogger("fr.lip6.move.gal").fine("Adding "+invneg.commands().size()+" place invariants with negative coefficients.");
			execAndCheckResult(invneg, solver);
			textReply = checkSat(solver, smt, true);
			if (verbose)  Logger.getLogger("fr.lip6.move.gal").info("Absence of deadlock check using  "+invpos.commands().size()+" positive and " + invneg.commands().size() +" generalized place invariants in "+ (System.currentTimeMillis()-time) +" ms returned " + textReply);
		}
		return textReply;
	}

	private static String checkSat(ISolver solver, org.smtlib.SMT smt) {
		return checkSat(solver, smt, false);
	}
	
	private static String checkSat(ISolver solver, org.smtlib.SMT smt, boolean retry) {
		IResponse res = solver.check_sat();
		IPrinter printer = smt.smtConfig.defaultPrinter;
		String textReply = printer.toString(res);
		if ("unknown".equals(textReply) && retry) {
			Logger.getLogger("fr.lip6.move.gal").info("SMT solver returned unknown. Retrying;");
			res = solver.check_sat();
			textReply = printer.toString(res);
		}
		return textReply;
	}


	/**
	 * Declares the invariants represented by invar, in two scripts according to whether they are pure positive 
	 * (semi flows) or general flows.
	 * @param invar the invariants we need to build constraints for
	 * @param sr the Petri net
	 * @param invpos positive invariants asserted here
	 * @param invneg general invariants asserted here
	 * @param smt solver access
	 */
	private static void declareInvariants(Set<SparseIntArray> invar, StructuralReduction sr, Script invpos,
			Script invneg, SMT smt) {
		// splitting posneg from pure positive
		IFactory efactory = smt.smtConfig.exprFactory;
		for (SparseIntArray invariant : invar) {
			boolean hasNeg = false;
			for (int i=0; i < invariant.size() ; i++) {
				if (invariant.valueAt(i) < 0) {
					hasNeg = true;
					break;
				}
			}			
			if (! hasNeg) {
				addInvariant(sr, efactory, invpos, invariant);
			} else {
				addInvariant(sr, efactory, invneg, invariant);
			}
		}
	}


	/**
	 * Builds a script that tests for deadlocks.
	 * i.e. that no transition is enabled.
	 * Algorithm consists in 
	 * AND over all transitions t 
	 *   OR of any input place not being marked enough to fire t.
	 *   
	 * We avoid having duplicate conditions asserted, but there is no implication test currently.
	 * @param sr the net we work with
	 * @param smt solver access
	 * @return a script which asserts that the system is deadlocked.
	 */
	private static Script assertNetIsDead(StructuralReduction sr) {
		Script scriptAssertDead = new Script();
		// deliberate block to help gc.
		{			
			IFactory ef = new SMT().smtConfig.exprFactory;
			Set<SparseIntArray> preconds = new HashSet<>();
			for (int i = 0; i < sr.getFlowPT().getColumnCount() ; i++)
				preconds.add(sr.getFlowPT().getColumn(i));
			for (SparseIntArray arr : preconds) {
				List<IExpr> conds = new ArrayList<>();
				// one of the preconditions of the transition is not marked enough to fire
				for (int  i=0; i < arr.size() ; i++) {
					conds.add( ef.fcn(ef.symbol("<"), ef.symbol("s"+arr.keyAt(i)), ef.numeral(arr.valueAt(i))));
				}
				// any of these is true => t is not fireable								
				IExpr res;
				if (conds.size() == 1) {
					res = conds.get(0);
				} else {
					res = ef.fcn(ef.symbol("or"), conds);
				}
				// add that t is not fireable
				scriptAssertDead.add(new C_assert(res));
			}			
		}
		return scriptAssertDead;
	}


	/**
	 * Computes a combined flow matrix, stored with column = transition, while removing any duplicates (e.g. due to test arcs or plain redundancy).
	 * Updates tnames that is supposed to initially be empty to set the names of the transitions that were kept.
	 * This is so we can reinterpret appropriately the Parikh vectors f so desired.
	 * @param sr our Petri net
	 * @param tnames empty list that will contain the transition names after call.
	 * @return a (reduced, less columns than usual) flow matrix
	 */
	private static MatrixCol computeReducedFlow(StructuralReduction sr, List<Integer> tnames) {
		MatrixCol sumMatrix = new MatrixCol(sr.getPnames().size(), 0);
		{
			int discarded=0;
			Set<SparseIntArray> seen = new HashSet<>();
			for (int i=0 ; i < sr.getFlowPT().getColumnCount() ; i++) {
				SparseIntArray combined = SparseIntArray.sumProd(-1, sr.getFlowPT().getColumn(i), 1, sr.getFlowTP().getColumn(i));
				if (seen.add(combined)) {
					sumMatrix.appendColumn(combined);
					tnames.add(i);
				} else
					discarded++;
			}
			if (discarded >0) {
				Logger.getLogger("fr.lip6.move.gal").info("Flow matrix only has "+sumMatrix.getColumnCount() +" transitions (discarded "+ discarded +" similar events)");
			}
		}
		return sumMatrix;
	}


	/**
	 * Creates and returns a script declaring N natural integer variables, with names prefix0 to prefixN-1. *
	 * If isSafe is true the upper bound is set to 1 (so they are 0 or 1 ~ boolean variables in effect).
	 * @param nbvars the number of variables to add  in the script
	 * @param prefix the prefix used in building variable names
	 * @param isSafe do we have an upper bound of 1 on these variables (lower bound 0 is always applied)
	 * @param smt access to the smt factories
	 * @param solveWithReals 
	 * @return a script containing declaration + constraints on a set of variables.
	 */
	private static Script declareVariables(int nbvars, String prefix, boolean isSafe, org.smtlib.SMT smt, boolean solveWithReals) {
		Script script = new Script();
		IFactory ef = smt.smtConfig.exprFactory;
		org.smtlib.ISort.IApplication ints2 ;
		if (solveWithReals)
			ints2 = smt.smtConfig.sortFactory.createSortExpression(ef.symbol("Real"));
		else
			// For integer LIA
			ints2 = smt.smtConfig.sortFactory.createSortExpression(ef.symbol("Int"));
		
		for (int i=0 ; i < nbvars ; i++) {
			ISymbol si = ef.symbol(prefix+i);
			script.add(new org.smtlib.command.C_declare_fun(
					si,
					Collections.emptyList(),
					ints2								
					));
			script.add(new C_assert(ef.fcn(ef.symbol(">="), si, ef.numeral(0))));
			if (isSafe) {
				script.add(new C_assert(ef.fcn(ef.symbol("<="), si, ef.numeral(1))));
			}			
		}
		return script;
	}

	
	private static Script declareBoolVariables(int nbvars, String prefix, SMT smt) {
		Script script = new Script();
		IFactory ef = smt.smtConfig.exprFactory;
		org.smtlib.ISort.IApplication ints2 = smt.smtConfig.sortFactory.createSortExpression(ef.symbol("Bool"));
		
		for (int i=0 ; i < nbvars ; i++) {
			ISymbol si = ef.symbol(prefix+i);
			script.add(new org.smtlib.command.C_declare_fun(
					si,
					Collections.emptyList(),
					ints2								
					));
		}
		return script;
	}


	/**
	 * Start an instance of a Z3 solver, with timeout at provided, logic QF_LIA/LRA, with produce models.
	 * @param solverPath path to Z3 exe
	 * @param smt the smt instance to configure/setup
	 * @param solveWithReals 
	 * @return a started solver or throws a RuntimeEx
	 */
	private static ISolver initSolver(String solverPath, org.smtlib.SMT smt, boolean solveWithReals, int timeoutQ, int timeoutT) {
		if (solveWithReals) {
			return initSolver(solverPath, smt, "QF_LRA", timeoutQ, timeoutT);
		} else {
			return initSolver(solverPath, smt, "QF_LIA", timeoutQ, timeoutT);
		}
	}
	private static ISolver initSolver(String solverPath, org.smtlib.SMT smt, String logic, int timeoutQ, int timeoutT) {
		smt.smtConfig.executable = solverPath;
		smt.smtConfig.timeout = timeoutQ;
		smt.smtConfig.timeoutTotal = timeoutT;
		Solver engine = Solver.Z3;
		ISolver solver = engine.getSolver(smt.smtConfig);		
		// start the solver
		IResponse err = solver.start();
		if (err.isError()) {
			throw new RuntimeException("Could not start solver "+ engine+" from path "+ solverPath + " raised error :"+err);
		}
		err = solver.set_option(smt.smtConfig.exprFactory.keyword(Utils.PRODUCE_MODELS), smt.smtConfig.exprFactory.symbol("true"));
		if (err.isError()) {
			throw new RuntimeException("Could not set :produce-models option :" + err);
		}
		err = solver.set_logic(logic, null);
		if (err.isError()) {
			throw new RuntimeException("Could not set logic" + err);
		}
		return solver;
	}



	private static void addInvariant(StructuralReduction sr, IFactory efactory, Script script,
			SparseIntArray invariant) {
		int sum = 0;
		// assert : cte = m0 * x0 + ... + m_n*x_n
		// build sum up
		List<IExpr> toadd = new ArrayList<>();
		List<IExpr> torem = new ArrayList<>();
		for (int i = 0 ; i < invariant.size() ; i++) {
			int v = invariant.keyAt(i);
			int val = invariant.valueAt(i);
			if (val != 0) {
				IExpr ss = efactory.symbol("s"+v);
				if (val != 1 && val != -1) {
					ss = efactory.fcn(efactory.symbol("*"), efactory.numeral( Math.abs(val)), ss );
				}
				if (val > 0) 
					toadd.add(ss);
				else
					torem.add(ss);
				sum += sr.getMarks().get(v) * val;
			}
		}
		IExpr sumE ;
		if (toadd.isEmpty()) {
			sumE = efactory.numeral(0);
		} else if (toadd.size() == 1) {
			sumE = toadd.get(0);
		} else {
			sumE = efactory.fcn(efactory.symbol("+"), toadd);
		}

		IExpr sumR  = efactory.numeral(sum);
		if (! torem.isEmpty()) {
			torem.add(sumR);
			sumR = efactory.fcn(efactory.symbol("+"), torem);
		}
		IExpr invarexpr = efactory.fcn(efactory.symbol("="), sumR, sumE);
		script.add(new C_assert(invarexpr));
	}

	private static boolean hasNonIntegerElements (IResponse s) {
		
		if (s instanceof ISeq) {
			ISeq seq = (ISeq) s;
			if (((ISeq) s).sexprs().isEmpty()) {
				return false;
			}
			if (seq.sexprs().get(0).toString().equals("/")) {
				return true;
			}
			for (ISexpr c : seq.sexprs()) {
				if (hasNonIntegerElements(c)) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	private static SparseIntArray extractState(IResponse state) {
		SparseIntArray res= new SparseIntArray();
		if (state instanceof ISeq) {
			ISeq seq = (ISeq) state;

			for (ISexpr sexpr : seq.sexprs()) {
				if (sexpr instanceof ISeq) {
					ISeq pair = (ISeq) sexpr;
					if (pair.sexprs().size() == 2) {
						if (pair.sexprs().get(0) instanceof ISymbol && pair.sexprs().get(1) instanceof INumeral) {
							int varindex = Integer.parseInt( ((ISymbol) pair.sexprs().get(0)).value().substring(1) );
							int varvalue = ((INumeral) pair.sexprs().get(1)).intValue();
							res.append(varindex, varvalue);
						}
					}
				}
			}
		}
		return res;
	}

	private static void queryState(IFactory efactory, StructuralReduction sr, ISolver solver) {
		List<IExpr> places = new ArrayList<>();
		for (int i =0 ; i < sr.getPnames().size() ; i++) {
			ISymbol si = efactory.symbol("s"+i);
			places.add(si);
		}
		ICommand getVals = new C_get_value(places);
		IResponse state = getVals.execute(solver);		
		SparseIntArray s = extractState(state);
		Logger.getLogger("fr.lip6.move.gal").info("Deadlock seems possible (SAT) in state :" + s);
	}

	private static void queryParikh(IFactory efactory, List<String> pnames, ISolver solver) {
		List<IExpr> places = new ArrayList<>();
		for (int i =0 ; i < pnames.size() ; i++) {
			ISymbol si = efactory.symbol("t"+i);
			places.add(si);
		}
		ICommand getVals = new C_get_value(places);
		IResponse state = getVals.execute(solver);		
		SparseIntArray s = extractState(state);
		StringBuilder sb = new StringBuilder();
		for (int  i=0 ; i < s.size() ; i++) {
			int ti = s.keyAt(i);
			int vi = s.valueAt(i);
			if (i >0) {
				sb.append(", ");
			}
			sb.append(pnames.get(ti)).append("=").append(vi);
		}
		Logger.getLogger("fr.lip6.move.gal").info("Deadlock seems possible with Parikh count :" + sb.toString());
	}

}
