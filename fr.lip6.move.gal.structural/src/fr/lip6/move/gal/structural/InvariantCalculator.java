package fr.lip6.move.gal.structural;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.MatrixCol;
import uniol.apt.analysis.invariants.InvariantCalculator.InvariantAlgorithm;

/**
 * A front-end for functionality computing invariants.
 * Underlying code is adapted from CvO-Theory group's APT : https://github.com/CvO-Theory/apt
 * See also uniol.apt package and classes.
 * @author ythierry
 *
 */
public class InvariantCalculator {

	
	/**
	 * Guaranteed polynomial runtime, returns flows (with positive AND negative coefficients)
	 * @param pn representing the Petri net approximation
	 * @return a set of invariants, i.e. coeffs for each variable such that the sum is constant in all markings/states.
	 */
	public static Set<SparseIntArray> computePInvariants (FlowMatrix pn, List<String> pnames) {
		Set<SparseIntArray> invar ;
		long time = System.currentTimeMillis();
		try {
			invar = uniol.apt.analysis.invariants.InvariantCalculator.calcSInvariants(pn, InvariantAlgorithm.PIPE, false, pnames);
			//InvariantCalculator.printInvariant(invar, sr.getPnames(), sr.getMarks());
			Logger.getLogger("fr.lip6.move.gal").info("Computed "+invar.size()+" place invariants in "+ (System.currentTimeMillis()-time) +" ms");
		} catch (ArithmeticException e) {
			invar = new HashSet<>();
			Logger.getLogger("fr.lip6.move.gal").info("Invariants computation overflowed in "+ (System.currentTimeMillis()-time) +" ms");
		}
		return invar;		
	}
	
	public static void printInvariant (Collection<SparseIntArray> invariants, List<String> pnames, List<Integer> initial) {
		for (SparseIntArray rv : invariants) {
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			int sum =0;
			for (int i =0; i < rv.size(); i++) {
				int k = rv.keyAt(i);
				int v = rv.valueAt(i); 
				if (v != 0) {					
					if (first) {
						if (v < 0) {
							sb.append("-");
							v = -v;
						}
						first  = false;
					} else {
						if (v < 0) {
							sb.append(" - ");
							v = -v;
						} else {
							sb.append(" + ");
						}
					}
					if (v != 1) {
						sb.append(v + "*"+ pnames.get(k));
					} else {
						sb.append(pnames.get(k));
					}
					sum += rv.get(k) * initial.get(k);
				}
			}
			System.out.println("inv : " + sb.toString() +" = " + sum);
		}
		System.out.println("Total of "+invariants.size() + " invariants.");
	}
	
	public static Set<SparseIntArray> computePInvariants (MatrixCol pn, List<String> pnames) {
		return computePInvariants(pn, pnames,false);
	}
	
	public static Set<SparseIntArray> computePInvariants (MatrixCol pn, List<String> pnames, boolean onlyPositive) {
		Set<SparseIntArray> invar ;
		long time = System.currentTimeMillis();
		try {
			invar = uniol.apt.analysis.invariants.InvariantCalculator.calcInvariantsPIPE(pn.transpose(), onlyPositive, pnames);		
			//InvariantCalculator.printInvariant(invar, sr.getPnames(), sr.getMarks());
			Logger.getLogger("fr.lip6.move.gal").info("Computed "+invar.size()+" place invariants in "+ (System.currentTimeMillis()-time) +" ms");
		} catch (ArithmeticException e) {
			invar = new HashSet<>();
			Logger.getLogger("fr.lip6.move.gal").info("Invariants computation overflowed in "+ (System.currentTimeMillis()-time) +" ms");
		}
		return invar;		
	}
	/**
	 * Worst case exponential (time and memory), returns semi-flows (with positive coefficients only) which are reputed easier to interpret.
	 * @param pn representing the Petri net approximation
	 * @return a set of invariants, i.e. coeffs for each variable such that the sum is constant in all markings/states.
	 */
	public static Set<SparseIntArray> computePSemiFlows (FlowMatrix pn, List<String> pnames) {
		return uniol.apt.analysis.invariants.InvariantCalculator.calcSInvariants(pn, InvariantAlgorithm.PIPE, true, pnames);
	}
	
	
	
}
