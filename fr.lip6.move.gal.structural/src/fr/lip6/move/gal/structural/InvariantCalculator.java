package fr.lip6.move.gal.structural;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.IntMatrixCol;
import uniol.apt.analysis.invariants.InvariantCalculator.InvariantAlgorithm;

/**
 * A front-end for functionality computing invariants. Underlying code is
 * adapted from CvO-Theory group's APT : https://github.com/CvO-Theory/apt 
 * See also uniol.apt package and classes.
 * 
 * @author ythierry
 *
 */
public class InvariantCalculator {
	static final int DEBUG = 0;

	/**
	 * Guaranteed polynomial runtime, returns flows (with positive AND negative
	 * coefficients)
	 * 
	 * @param pn representing the Petri net approximation
	 * @return a set of invariants, i.e. coeffs for each variable such that the sum
	 *         is constant in all markings/states.
	 */
	public static Set<SparseIntArray> computePInvariants(FlowMatrix pn) {
		Set<SparseIntArray> invar;
		long time = System.currentTimeMillis();
		try {
			invar = uniol.apt.analysis.invariants.InvariantCalculator.calcSInvariants(pn, InvariantAlgorithm.PIPE,
					false);
			// InvariantCalculator.printInvariant(invar, sr.getPnames(), sr.getMarks());
			Logger.getLogger("fr.lip6.move.gal").info(
					"Computed " + invar.size() + " place invariants in " + (System.currentTimeMillis() - time) + " ms");
		} catch (ArithmeticException e) {
			invar = new HashSet<>();
			Logger.getLogger("fr.lip6.move.gal")
					.info("Invariants computation overflowed in " + (System.currentTimeMillis() - time) + " ms");
		}
		return invar;
	}

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
					sum = Math.addExact(sum, (Math.multiplyExact((long)v, initial.get(k))));					
				}
			}
		}
		return sum;
	}

	public static Set<SparseIntArray> computePInvariants(IntMatrixCol pn) {
		return computePInvariants(pn, false, 120);
	}

	public static Set<SparseIntArray> computePInvariants(IntMatrixCol pn, boolean onlyPositive, int timeout) {
		ExecutorService pool = Executors.newCachedThreadPool();
		// System.out.println("Before : "+ SerializationUtil.getText(be, true));
		FutureTask<Set<SparseIntArray>> task = new FutureTask<>(() -> computePInvariants(pn, onlyPositive));
		pool.execute(task);
		try {
			return task.get(timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			task.cancel(true);
			Logger.getLogger("fr.lip6.move.gal")
					.warning("Invariant computation timed out after " + timeout + " seconds.");
		}
		return new HashSet<>();
	}

	private static void cache(IntMatrixCol pn, Set<SparseIntArray> inv) {
		synchronized (lock) {
			last = new IntMatrixCol(pn);
			lastInv = new HashSet<>(inv);
		}
	}

	private static Set<SparseIntArray> checkCache(IntMatrixCol pn) {
		synchronized (lock) {
			if (pn.equals(last)) {
				Logger.getLogger("fr.lip6.move.gal").info("Invariant cache hit.");
				return lastInv;
			} else {
				return null;
			}
		}
	}

	public static Set<SparseIntArray> computeTinvariants(ISparsePetriNet sr, IntMatrixCol sumMatrix, List<Integer> repr,
			boolean onlyPositive) {

		Map<Integer, List<Integer>> repSet = computeMap(repr);
		Set<SparseIntArray> invarT = computePInvariants(sumMatrix.transpose(), onlyPositive);

		if (DEBUG >= 1 && invarT != null) {
			List<Integer> empty = new ArrayList<>(sr.getTransitionCount());
			for (int i = 0; i < sr.getTransitionCount(); i++)
				empty.add(0);
			InvariantCalculator.printInvariant(invarT, sr.getTnames(), empty);
		}
		// so we have T invariants, using the reduced flow matrix
		Set<SparseIntArray> reindexT = new HashSet<>();
		// reinterpret over the original indexes of transitions
		for (SparseIntArray inv : invarT) {
			List<SparseIntArray> toadd = new ArrayList<>();
			toadd.add(new SparseIntArray());
			for (int i = 0, ie = inv.size(); i < ie; i++) {
				int t = inv.keyAt(i);
				int val = inv.valueAt(i);
				List<Integer> images = repSet.get(t);
				if (images.size() > 1) {
					for (Integer img : images) {
						List<SparseIntArray> toadd2 = new ArrayList<>();
						for (SparseIntArray b : toadd) {
							SparseIntArray mod = b.clone();
							mod.put(img, val);
							toadd2.add(mod);
						}
						toadd = toadd2;
					}
				} else {
					for (SparseIntArray b : toadd) {
						b.put(images.get(0), val);
					}
				}
			}
			reindexT.addAll(toadd);
		}

		if (DEBUG >= 2 && invarT != null) {
			printInvariant(reindexT, sr.getTnames(), null);
		}

		return invarT;
	}

	public static Set<SparseIntArray> computeTinvariants(ISparsePetriNet sr, IntMatrixCol sumMatrix,
			List<Integer> repr) {
		return computeTinvariants(sr, sumMatrix, repr, true);
	}

	public static Set<SparseIntArray> computePInvariants(IntMatrixCol pn, boolean onlyPositive) {
		Set<SparseIntArray> invar = checkCache(pn);
		if (invar != null) {
			return invar;
		}
		long time = System.currentTimeMillis();
		try {
			invar = uniol.apt.analysis.invariants.InvariantCalculator.calcInvariantsPIPE(pn.transpose(), onlyPositive);
			cache(pn, invar);
			// InvariantCalculator.printInvariant(invar, sr.getPnames(), sr.getMarks());
			Logger.getLogger("fr.lip6.move.gal")
					.info("Computed " + invar.size() + " invariants in " + (System.currentTimeMillis() - time) + " ms");
		} catch (ArithmeticException e) {
			invar = new HashSet<>();
			Logger.getLogger("fr.lip6.move.gal")
					.info("Invariants computation overflowed in " + (System.currentTimeMillis() - time) + " ms");
		}
		return invar;
	}

	/**
	 * Worst case exponential (time and memory), returns semi-flows (with positive
	 * coefficients only) which are reputed easier to interpret.
	 * 
	 * @param pn representing the Petri net approximation
	 * @return a set of invariants, i.e. coeffs for each variable such that the sum
	 *         is constant in all markings/states.
	 */
	public static Set<SparseIntArray> computePSemiFlows(FlowMatrix pn) {
		return uniol.apt.analysis.invariants.InvariantCalculator.calcSInvariants(pn, InvariantAlgorithm.PIPE, true);
	}

	private static IntMatrixCol last = null;
	private static Object lock = new Object();
	private static Set<SparseIntArray> lastInv = null;

	/**
	 * Computes a combined flow matrix, stored with column = transition, while
	 * removing any duplicates (e.g. due to test arcs or plain redundancy). Updates
	 * tnames that is supposed to initially be empty to set the names of the
	 * transitions that were kept. This is so we can reinterpret appropriately the
	 * Parikh vectors f so desired.
	 * 
	 * @param sr             our Petri net
	 * @param representative the mapping from original transition index to their new
	 *                       representative (many to one/surjection)
	 * @return a (reduced, less columns than usual) flow matrix
	 */
	public static IntMatrixCol computeReducedFlow(ISparsePetriNet sr, List<Integer> representative) {
		IntMatrixCol sumMatrix = new IntMatrixCol(sr.getPlaceCount(), 0);
		{
			int discarded = 0;
			int cur = 0;
			Map<SparseIntArray, Integer> seen = new HashMap<>();
			for (int i = 0; i < sr.getFlowPT().getColumnCount(); i++) {
				SparseIntArray combined = SparseIntArray.sumProd(-1, sr.getFlowPT().getColumn(i), 1,
						sr.getFlowTP().getColumn(i));
				Integer repr = seen.putIfAbsent(combined, cur);
				if (repr == null) {
					sumMatrix.appendColumn(combined);
					representative.add(cur);
					cur++;
				} else {
					representative.add(repr);
					discarded++;
				}
			}
			if (discarded > 0) {
				Logger.getLogger("fr.lip6.move.gal").info("Flow matrix only has " + sumMatrix.getColumnCount()
						+ " transitions (discarded " + discarded + " similar events)");
			}
		}
		return sumMatrix;
	}

	public static SparseIntArray transformParikh(SparseIntArray parikhori, Map<Integer, List<Integer>> repr) {
		SparseIntArray parikh = new SparseIntArray();
		for (int i = 0, e = parikhori.size(); i < e; i++) {
			int t = parikhori.keyAt(i);
			int k = parikhori.valueAt(i);
			for (int tr : repr.get(t)) {
				parikh.put(tr, k);
			}
		}
		return parikh;
	}

	public static Map<Integer, List<Integer>> computeMap(List<Integer> repr) {
		Map<Integer, List<Integer>> repSet = new HashMap<>();
		for (int i = 0, e = repr.size(); i < e; i++) {
			final int t = i;
			repSet.compute(repr.get(t), (k, v) -> {
				if (v == null) {
					List<Integer> l = new ArrayList<>();
					l.add(t);
					return l;
				} else {
					v.add(t);
					return v;
				}
			});
		}
		return repSet;
	}

}
