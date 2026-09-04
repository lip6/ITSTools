package fr.lip6.move.gal.application.solver.abstraction;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.util.IntMatrixCol;

/**
 * The P-semi-flows of a net as candidate building blocks of a projection
 * (ABSTRACTION.md): a kept set that is a union of semi-flow supports is
 * structurally bounded. Each semi-flow carries its support, its constant
 * (value on the initial marking) and a price, the number of markings it
 * admits, C(constant + k - 1, k - 1) for k places.
 */
public class SemiflowCover {

	private final List<BitSet> supports = new ArrayList<>();
	private final List<Long> constants = new ArrayList<>();
	private final List<Double> prices = new ArrayList<>();
	private final int placeCount;

	public SemiflowCover(ISparsePetriNet net, IntMatrixCol semiflows) {
		placeCount = net.getPlaceCount();
		for (int c = 0; c < semiflows.getColumnCount(); c++) {
			SparseIntArray col = semiflows.getColumn(c);
			if (col.size() == 0) continue;
			BitSet supp = new BitSet();
			long constant = 0;
			for (int i = 0; i < col.size(); i++) {
				supp.set(col.keyAt(i));
				constant += (long) col.valueAt(i) * net.getMarks().get(col.keyAt(i));
			}
			supports.add(supp);
			constants.add(constant);
			prices.add(price(constant, supp.cardinality()));
		}
	}

	/** Markings of k places summing to at most c: C(c + k, k), capped. */
	private static double price(long c, int k) {
		double r = 1;
		for (int i = 1; i <= k; i++) {
			r = r * (c + i) / i;
			if (r > 1e18) return 1e18;
		}
		return r;
	}

	public int size() {
		return supports.size();
	}

	public BitSet support(int i) {
		return supports.get(i);
	}

	public long constant(int i) {
		return constants.get(i);
	}

	public double price(int i) {
		return prices.get(i);
	}

	/** Is every place of the set covered by some semi-flow? */
	public boolean covers(BitSet places) {
		BitSet covered = new BitSet(placeCount);
		for (BitSet s : supports) covered.or(s);
		BitSet missing = (BitSet) places.clone();
		missing.andNot(covered);
		return missing.isEmpty();
	}

	/**
	 * Indices of the cheapest semi-flows, one per uncovered place of the set,
	 * whose supports are not already inside kept. Greedy: for each place not
	 * yet covered by a chosen or kept semi-flow, the cheapest one containing it.
	 */
	public List<Integer> cheapestCovering(BitSet places, BitSet kept) {
		List<Integer> chosen = new ArrayList<>();
		BitSet covered = (BitSet) kept.clone();
		for (int p = places.nextSetBit(0); p >= 0; p = places.nextSetBit(p + 1)) {
			if (covered.get(p)) continue;
			int best = -1;
			for (int i = 0; i < supports.size(); i++) {
				if (supports.get(i).get(p) && (best < 0 || prices.get(i) < prices.get(best))) best = i;
			}
			if (best < 0) continue;
			chosen.add(best);
			covered.or(supports.get(best));
		}
		return chosen;
	}

	/**
	 * The cheapest semi-flow not inside kept whose support touches a transition
	 * of the kept set (shares a pre- or post-place with a transition that has
	 * an arc to a kept place), or -1.
	 */
	public int cheapestAdjacent(BitSet kept, ISparsePetriNet net) {
		BitSet frontier = new BitSet(placeCount);
		IntMatrixCol pt = net.getFlowPT();
		IntMatrixCol tp = net.getFlowTP();
		for (int t = 0; t < net.getTransitionCount(); t++) {
			boolean touches = false;
			for (SparseIntArray col : new SparseIntArray[] { pt.getColumn(t), tp.getColumn(t) }) {
				for (int i = 0; i < col.size() && !touches; i++) touches = kept.get(col.keyAt(i));
			}
			if (!touches) continue;
			for (SparseIntArray col : new SparseIntArray[] { pt.getColumn(t), tp.getColumn(t) }) {
				for (int i = 0; i < col.size(); i++) frontier.set(col.keyAt(i));
			}
		}
		frontier.andNot(kept);
		int best = -1;
		for (int i = 0; i < supports.size(); i++) {
			if (!supports.get(i).intersects(frontier)) continue;
			BitSet outside = (BitSet) supports.get(i).clone();
			outside.andNot(kept);
			if (outside.isEmpty()) continue;
			if (best < 0 || prices.get(i) < prices.get(best)) best = i;
		}
		return best;
	}
}
