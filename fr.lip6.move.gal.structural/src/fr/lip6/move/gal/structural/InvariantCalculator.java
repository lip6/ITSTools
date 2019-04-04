package fr.lip6.move.gal.structural;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
	public static Set<List<Integer>> computePInvariants (FlowMatrix pn, List<String> pnames) {
		return uniol.apt.analysis.invariants.InvariantCalculator.calcSInvariants(pn, InvariantAlgorithm.PIPE, false, pnames);
	}
	
	public static void printInvariant (Collection<List<Integer>> invariants, List<String> pnames, List<Integer> initial) {
		for (List<Integer> rv : invariants) {
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			int sum =0;
			for (int i =0; i < rv.size(); i++) {
				int v = rv.get(i); 
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
						sb.append(v + "*"+ pnames.get(i));
					} else {
						sb.append(pnames.get(i));
					}
					sum += rv.get(i) * initial.get(i);
				}
			}
			System.out.println("inv : " + sb.toString() +" = " + sum);
		}
		System.out.println("Total of "+invariants.size() + " invariants.");
	}
	
	public static Set<List<Integer>> computePInvariants (MatrixCol pn, List<String> pnames) {
		return uniol.apt.analysis.invariants.InvariantCalculator.calcInvariantsPIPE(pn.transpose(), false, pnames);
	}
	/**
	 * Worst case exponential (time and memory), returns semi-flows (with positive coefficients only) which are reputed easier to interpret.
	 * @param pn representing the Petri net approximation
	 * @return a set of invariants, i.e. coeffs for each variable such that the sum is constant in all markings/states.
	 */
	public static Set<List<Integer>> computePSemiFlows (FlowMatrix pn, List<String> pnames) {
		return uniol.apt.analysis.invariants.InvariantCalculator.calcSInvariants(pn, InvariantAlgorithm.PIPE, true, pnames);
	}
	
	
	
}
