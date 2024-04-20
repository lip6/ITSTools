package fr.lip6.move.gal.structural.smt;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.smtlib.IExpr;
import org.smtlib.SMT;
import org.smtlib.IExpr.IFactory;
import org.smtlib.command.C_assert;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.util.IntMatrixCol;

public class InvariantRefinerBuilder {

	/**
	 * Computes P invariants then constructs up to two refiners, one for positive invariants, one for negative invariants.
	 * @param effects the effect matrix (Post - Pre with fusion of transitions with the same effect)
	 * @param marks the initial marking
	 * @return a list of refiners, possibly empty
	 */
	public static List<IRefiner> buildInvariantRefiners(IntMatrixCol effects, List<Integer> marks) {
		Set<SparseIntArray> invar = InvariantCalculator.computePInvariants(effects);
		// splitting posneg from pure positive
		IFactory efactory = new SMT().smtConfig.exprFactory;
		StaticRefiner invpos = new StaticRefiner("Positive P Invariants (semi-flows)");
		StaticRefiner invneg = new StaticRefiner("Generalized P Invariants (flows)");
		for (SparseIntArray invariant : invar) {
			boolean hasNeg = false;
			for (int i=0; i < invariant.size() ; i++) {
				if (invariant.valueAt(i) < 0) {
					hasNeg = true;
					break;
				}
			}			
			if (! hasNeg) {
				addInvariant(marks, efactory, invpos, invariant);
			} else {
				addInvariant(marks, efactory, invneg, invariant);
			}
		}
		List<IRefiner> res = new ArrayList<>();
		if (invpos.getConstraints().size() > 0) {
			res.add(invpos);
		}
		if (invneg.getConstraints().size() > 0) {
			res.add(invneg);
		}
		return res;
	}

	/**
	 * Add an invariant to the refiner, as a constraint.
	 * @param marks the initial marking
	 * @param efactory to build the expressions
	 * @param refiner the refiner to add the invariant to
	 * @param invariant the P invariant to add
	 */
	private static void addInvariant(List<Integer> marks, IFactory efactory, StaticRefiner refiner,
			SparseIntArray invariant) {
		long sum = 0;
		// assert : cte = m0 * x0 + ... + m_n*x_n
		// build sum up
		VarSet support = new VarSet();
		List<IExpr> toadd = new ArrayList<>();
		List<IExpr> torem = new ArrayList<>();
		try {
			for (int i = 0 ; i < invariant.size() ; i++) {
				int v = invariant.keyAt(i);
				int val = invariant.valueAt(i);
				IExpr ss = efactory.symbol("s"+v);
				support.addVar("s", v);
				if (val != 1 && val != -1) {
					ss = efactory.fcn(efactory.symbol("*"), efactory.numeral( Math.abs(val)), ss );
				}
				if (val > 0) 
					toadd.add(ss);
				else
					torem.add(ss);
				sum  = Math.addExact(sum, Math.multiplyExact((long)val, marks.get(v))) ; 
			}
		} catch (ArithmeticException e) {
			System.err.println("Invariant declaration overflow for the constant !");
			return;
		}
		if (sum < 0) {
			toadd.add(efactory.numeral(-sum));
		} else if (sum >0) {
			torem.add(efactory.numeral(sum));
		}
		IExpr sumE ;
		if (toadd.isEmpty()) {
			sumE = efactory.numeral(0);
		} else if (toadd.size() == 1) {
			sumE = toadd.get(0);
		} else {
			sumE = efactory.fcn(efactory.symbol("+"), toadd);
		}

		IExpr sumR  ; 
		if (torem.isEmpty()) {
			sumR = efactory.numeral(0);
		} else if (torem.size() == 1) {
			sumR = torem.get(0);
		} else {
			sumR = efactory.fcn(efactory.symbol("+"), torem);
		}
		
		IExpr invarexpr = efactory.fcn(efactory.symbol("="), sumR, sumE);
		refiner.addConstraint(new SMTConstraint(new C_assert(invarexpr),support));
	}


}
