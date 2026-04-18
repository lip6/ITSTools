package fr.lip6.move.petrispot.runner;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

import android.util.SparseIntArray;

public class InvariantPrinter {

	public static void printInvariant(Collection<SparseIntArray> invariants, List<String> pnames, List<Integer> initial,
			PrintStream out) {
		for (SparseIntArray rv : invariants) {
			StringBuilder sb = new StringBuilder();
			try {
				long sum = printEquation(rv, initial, pnames, sb);
				out.println("inv : " + sb.toString() + " = " + sum);
			} catch (ArithmeticException e) {
				System.err.println("Overflow of 'long' when computing constant for invariant.");
			}
		}
		out.println("Total of " + invariants.size() + " invariants.");
	}

	public static void printInvariant(Collection<SparseIntArray> invariants, List<String> pnames,
			List<Integer> initial) {
		printInvariant(invariants, pnames, initial, System.out);
	}

	public static long printEquation(SparseIntArray inv, List<Integer> initial, List<String> pnames, StringBuilder sb) {
		boolean first = true;
		long sum = 0;
		for (int i = 0; i < inv.size(); i++) {
			int k = inv.keyAt(i);
			int v = inv.valueAt(i);
			if (v != 0) {
				if (first) {
					if (v < 0) {
						sb.append("-");
						v = -v;
					}
					first = false;
				} else {
					if (v < 0) {
						sb.append(" - ");
						v = -v;
					} else {
						sb.append(" + ");
					}
				}
				if (v != 1) {
					sb.append(v + "*" + pnames.get(k));
				} else {
					sb.append(pnames.get(k));
				}
				if (initial != null) {
					sum = Math.addExact(sum, (Math.multiplyExact((long) v, initial.get(k))));
				}
			}
		}
		return sum;
	}

}
