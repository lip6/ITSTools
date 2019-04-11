package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

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
import org.smtlib.impl.Script;
import org.smtlib.sexpr.ISexpr;
import org.smtlib.sexpr.ISexpr.ISeq;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.util.MatrixCol;

public class DeadlockTester {

	/**
	 * Unsat answer means no deadlocks, SAT means nothing, as we are working with an overapprox.
	 * @param sr
	 * @param solverPath
	 * @param isSafe 
	 * @return
	 */
	public static String testDeadlocksWithSMT(StructuralReduction sr, String solverPath, boolean isSafe) {
		org.smtlib.SMT smt = new SMT();
		
		
		
		ISolver solver = initSolver(solverPath, smt);
		
		int nbvars = sr.getPnames().size();
		String prefix = "s";
		Script script = declareVariables(nbvars, prefix, isSafe, smt);
		
		IFactory efactory = smt.smtConfig.exprFactory;
		org.smtlib.ISort.IApplication ints = smt.smtConfig.sortFactory.createSortExpression(efactory.symbol("Int"));
		
		MatrixCol sumMatrix = new MatrixCol(sr.getPnames().size(), 0);
		List<String> tnames = new ArrayList<>();
		{
			int discarded=0;
			Set<SparseIntArray> seen = new HashSet<>();
			for (int i=0 ; i < sr.getFlowPT().getColumnCount() ; i++) {
				SparseIntArray combined = SparseIntArray.sumProd(-1, sr.getFlowPT().getColumn(i), 1, sr.getFlowTP().getColumn(i));
				if (seen.add(combined)) {
					sumMatrix.appendColumn(combined);
					tnames.add(sr.getTnames().get(i));
				} else
					discarded++;
			}
			if (discarded >0) {
				Logger.getLogger("fr.lip6.move.gal").info("Flow matrix only has "+sumMatrix.getColumnCount() +" transitions (discarded "+ discarded +" similar events)");
			}
		}

		long timestamp2 = System.currentTimeMillis();
		Set<List<Integer>> invar = InvariantCalculator.computePInvariants(sumMatrix, sr.getPnames());		
		InvariantCalculator.printInvariant(invar, sr.getPnames(), sr.getMarks());
		Logger.getLogger("fr.lip6.move.gal").info("Computed "+invar.size()+" place invariants in "+ (System.currentTimeMillis()-timestamp2) +" ms");
		
		
		// splitting posneg from pure positive
		Script scriptMore = new Script();
		int normal = 0;
		int additional = 0;
		for (List<Integer> invariant : invar) {
			if (invariant.stream().allMatch(v -> v>=0)) {
				addInvariant(sr, efactory, script, invariant);
				normal++;
			} else {
				addInvariant(sr, efactory, scriptMore, invariant);
				additional ++;
			}
		}
		Script scriptAssertDead = new Script();
		// deliberate block to help gc.
		{
			Set<SparseIntArray> preconds = new HashSet<>();
			for (int i = 0; i < sr.getFlowPT().getColumnCount() ; i++)
				preconds.add(sr.getFlowPT().getColumn(i));
			for (SparseIntArray arr : preconds) {
				List<IExpr> conds = new ArrayList<>();
				// one of the preconditions of the transition is not marked enough to fire
				for (int  i=0; i < arr.size() ; i++) {
					conds.add( efactory.fcn(efactory.symbol("<"), efactory.symbol("s"+arr.keyAt(i)), efactory.numeral(arr.valueAt(i))));
				}
				// any of these is true => t is not fireable
				IExpr res;
				if (conds.size() == 1) {
					res = conds.get(0);
				} else {
					res = efactory.fcn(efactory.symbol("or"), conds);
				}
				// add that t is not fireable
				scriptAssertDead.add(new C_assert(res));
			}			
		}
		timestamp2 = System.currentTimeMillis();
		IResponse res = script.execute(solver);		
		res = scriptAssertDead.execute(solver);
		// free up memory
		script = null;
		scriptAssertDead = null;
		
		IResponse res2 = solver.check_sat();
		IPrinter printer = smt.smtConfig.defaultPrinter;
		String textReply = printer.toString(res2);
		Logger.getLogger("fr.lip6.move.gal").info("Absence of deadlock check using  "+normal+" positive place invariants in "+ (System.currentTimeMillis()-timestamp2) +" ms returned " + textReply);

		
		if (textReply.equals("sat") && ! scriptMore.commands().isEmpty()) {
			timestamp2 = System.currentTimeMillis();
			Logger.getLogger("fr.lip6.move.gal").info("Adding "+additional+" place invariants with negative coefficients.");
			res = scriptMore.execute(solver);
			res2 = solver.check_sat();
			textReply = printer.toString(res2);
			Logger.getLogger("fr.lip6.move.gal").info("Absence of deadlock check using  "+normal+" positive and " + additional +" generalized place invariants in "+ (System.currentTimeMillis()-timestamp2) +" ms returned " + textReply);
			// help gc
			scriptMore = null;
		}
		
		if (textReply.equals("sat")) {
			timestamp2 = System.currentTimeMillis();
			Logger.getLogger("fr.lip6.move.gal").info("Adding state equation constraints to refine reachable states.");
			script = new Script();
			
			// declare a set of variables for holding Parikh count of the transition
			declareVariables(sumMatrix.getColumnCount(), "t", false, smt);

			// we work with one constraint for each place => use transposed
			MatrixCol mat = sumMatrix.transpose();
			for (int varindex = 0 ; varindex < mat.getColumnCount() ; varindex++) {
				
				SparseIntArray line = mat.getColumn(varindex);
				// assert : x = m0.x + X0*C(t0,x) + ...+ XN*C(Tn,x)
				List<IExpr> exprs = new ArrayList<IExpr>();

				// m0.x
				exprs.add(efactory.numeral(sr.getMarks().get(varindex)));

				//  Xi*C(ti,x)
				for (int i = 0 ; i < line.size() ; i++) {
					int val = line.valueAt(i);
					int trindex = line.keyAt(i);
					IExpr nbtok ;
					if (val > 0) 
						nbtok = efactory.numeral(val);
					else if (val < 0)
						nbtok = efactory.fcn(efactory.symbol("-"), efactory.numeral(-val));
					else 
						continue;
					exprs.add(efactory.fcn(efactory.symbol("*"), 
							efactory.symbol("t"+trindex),
							nbtok));
				}
				
				script.add(
						new C_assert(
						efactory.fcn(efactory.symbol("="), 
						efactory.symbol("s"+varindex),
						// = m0.x + X0*C(t0,x) + ...+ XN*C(Tn,x)
						efactory.fcn(efactory.symbol("+"), exprs))));
			}
			res = script.execute(solver);
			res2 = solver.check_sat();
			textReply = printer.toString(res2);
			Logger.getLogger("fr.lip6.move.gal").info("Absence of deadlock check using state equation in "+ (System.currentTimeMillis()-timestamp2) +" ms returned " + textReply);
		}
		
		
		if (textReply.equals("sat")) {			
			queryState(efactory, sr, solver);
			queryParikh(efactory, tnames, solver);
		}
		
		solver.exit();
		return textReply;
	}


	/**
	 * Creates and returns a script declaring N natural integer variables, with names prefix0 to prefixN-1. *
	 * If isSafe is true the upper bound is set to 1 (so they are 0 or 1 ~ boolean variables in effect).
	 * @param nbvars the number of variables to add  in the script
	 * @param prefix the prefix used in building variable names
	 * @param isSafe do we have an upper bound of 1 on these variables (lower bound 0 is always applied)
	 * @param smt access to the smt factories
	 * @return a script containing declaration + constraints on a set of variables.
	 */
	private static Script declareVariables(int nbvars, String prefix, boolean isSafe, org.smtlib.SMT smt) {
		Script script = new Script();
		IFactory ef = smt.smtConfig.exprFactory;
		org.smtlib.ISort.IApplication ints2 = smt.smtConfig.sortFactory.createSortExpression(ef.symbol("Int"));		
		for (int i =0 ; i < nbvars ; i++) {
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


	/**
	 * Start an instance of a Z3 solver, with timeout at 3000, logic QF_LIA, with produce models.
	 * @param solverPath path to Z3 exe
	 * @param smt the smt instance to configure/setup
	 * @return a started solver or throws a RuntimeEx
	 */
	private static ISolver initSolver(String solverPath, org.smtlib.SMT smt) {
		smt.smtConfig.executable = solverPath;
		smt.smtConfig.timeout = 3000;
		Solver engine = Solver.Z3;
		ISolver solver = engine .getSolver(smt.smtConfig);		
		// start the solver
		IResponse err = solver.start();
		if (err.isError()) {
			throw new RuntimeException("Could not start solver "+ engine+" from path "+ solverPath + " raised error :"+err);
		}
		err = solver.set_option(smt.smtConfig.exprFactory.keyword(Utils.PRODUCE_MODELS), smt.smtConfig.exprFactory.symbol("true"));
		if (err.isError()) {
			throw new RuntimeException("Could not set :produce-models option :" + err);
		}
		err = solver.set_logic("QF_LIA", null);
		if (err.isError()) {
			throw new RuntimeException("Could not set logic to QF_LIA" + err);
		}
		return solver;
	}
	
	

	private static void addInvariant(StructuralReduction sr, IFactory efactory, Script script,
			List<Integer> invariant) {
		int sum = 0;
		// assert : cte = m0 * x0 + ... + m_n*x_n
		// build sum up
		List<IExpr> toadd = new ArrayList<>();
		List<IExpr> torem = new ArrayList<>();
		for (int v = 0 ; v < invariant.size() ; v++) {
			if (invariant.get(v) != 0) {
				IExpr ss = efactory.symbol("s"+v);
				if (invariant.get(v) != 1 && invariant.get(v) != -1) {
					ss = efactory.fcn(efactory.symbol("*"), efactory.numeral( Math.abs(invariant.get(v))), ss );
				}
				if (invariant.get(v) > 0) 
					toadd.add(ss);
				else
					torem.add(ss);
				sum += sr.getMarks().get(v) * invariant.get(v);
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
