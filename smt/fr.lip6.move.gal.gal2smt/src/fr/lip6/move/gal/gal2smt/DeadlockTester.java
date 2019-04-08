package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.smtlib.IExpr;
import org.smtlib.IPrinter;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.IResponse;
import org.smtlib.ISolver;
import org.smtlib.SMT;
import org.smtlib.command.C_assert;
import org.smtlib.impl.Script;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.util.MatrixCol;

public class DeadlockTester {

	/**
	 * Unsat answer means no deadlocks, SAT means nothing, as we are working with an overapprox.
	 * @param sr
	 * @param solverPath
	 * @return
	 */
	public static String testDeadlocksWithSMT(StructuralReduction sr, String solverPath) {
		org.smtlib.SMT smt = new SMT();
		smt.smtConfig.executable = solverPath;
		smt.smtConfig.timeout = 3000;
		Solver engine = Solver.Z3;
		ISolver solver = engine .getSolver(smt.smtConfig);
		IFactory efactory = smt.smtConfig.exprFactory;
		org.smtlib.ISort.IFactory sortfactory = smt.smtConfig.sortFactory;
		// start the solver
		IResponse err = solver.start();
		solver.set_logic("QF_LIA", null);
		if (err.isError()) {
			throw new RuntimeException("Could not start solver "+ engine+" from path "+ solverPath + " raised error :"+err);
		}
		Script script = new Script();
		org.smtlib.ISort.IApplication ints = sortfactory.createSortExpression(efactory.symbol("Int"));
		
		for (int i =0 ; i < sr.getPnames().size() ; i++) {
			ISymbol si = efactory.symbol("s"+i);
			script.add(new org.smtlib.command.C_declare_fun(
					si,
					Collections.emptyList(),
					ints								
					));
			script.add(new C_assert(efactory.fcn(efactory.symbol(">="), si, efactory.numeral(0))));
		}
		MatrixCol sumMatrix = new MatrixCol(sr.getPnames().size(), 0);
		for (int i=0 ; i < sr.getFlowPT().getColumnCount() ; i++) {
			sumMatrix.appendColumn(SparseIntArray.sumProd(-1, sr.getFlowPT().getColumn(i), 1, sr.getFlowTP().getColumn(i)));
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
		Set<SparseIntArray> preconds = new HashSet<>();
		for (int i = 0; i < sr.getFlowPT().getColumnCount() ; i++)
			preconds.add(sr.getFlowPT().getColumn(i));
		for (SparseIntArray arr : preconds) {
			List<IExpr> conds = new ArrayList<>();
			for (int  i=0; i < arr.size() ; i++) {
				conds.add( efactory.fcn(efactory.symbol("<"), efactory.symbol("s"+arr.keyAt(i)), efactory.numeral(arr.valueAt(i))));
			}
			IExpr res;
			if (conds.size() == 1) {
				res = conds.get(0);
			} else {
				res = efactory.fcn(efactory.symbol("or"), conds);
			}
			script.add(new C_assert(res));
		}		
		timestamp2 = System.currentTimeMillis();
		IResponse res = script.execute(solver);		
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
		} 
		
		return textReply;
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

}
