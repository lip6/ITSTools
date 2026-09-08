package fr.lip6.move.gal.structural;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.IntMatrixCol;

/**
 * Maintenance of the optional {@link NetBlock} data blocks across the
 * transformations of a net.
 *
 * The point of this file is that net code stays about nets: a transformation
 * says in one call what it did to its objects, and every decision about what
 * that means for a counting record is taken here. A transformation that
 * carries no record calls nothing, since the presence test is O(1) and made
 * once, before the work.
 */
public class NetBlocks {

	/** What a net must offer for its blocks to be maintained here. */
	public interface Holder {
		boolean hasAnyBlock();

		IntMatrixCol getBlock(NetBlock block);

		void putBlock(NetBlock block, IntMatrixCol matrix);

		void clearBlocks(String why);
	}

	private NetBlocks() {
	}

	/**
	 * Duplicate transitions were fused: the survivor stands for the dropped
	 * ones too, so it takes their multiplicities. The one drop a counting
	 * record can follow exactly.
	 *
	 * @param count      the transition count before the deletions
	 * @param dropped    the transitions about to be deleted
	 * @param survivorOf for each dropped transition, the one it duplicates
	 */
	public static void transitionsFused(Holder net, int count, List<Integer> dropped,
			Map<Integer, Integer> survivorOf) {
		if (!net.hasAnyBlock() || dropped.isEmpty()) {
			return;
		}
		IntMatrixCol next = fuse(net.getBlock(NetBlock.TMULT), count, dropped, survivorOf);
		if (next == null) {
			net.clearBlocks("a fusion of " + dropped.size() + " duplicate transitions it could not follow");
		} else {
			net.putBlock(NetBlock.TMULT, next);
		}
	}

	/**
	 * Transitions were removed and no surviving transition stands for them, so
	 * the arcs they contributed to the graph a record counts are gone with
	 * them: the record cannot be maintained. Keeping their guards and weights
	 * instead (the ghost contributors of HSC_PLAN.md section 11) is what would
	 * let it survive.
	 */
	public static void transitionsDropped(Holder net, int howMany, String why) {
		if (!net.hasAnyBlock() || howMany == 0) {
			return;
		}
		net.clearBlocks(howMany + " transitions removed (" + why + ") whose arcs it cannot account for");
	}

	/**
	 * May a rule remove the transitions that have no effect?
	 *
	 * Only when nothing is tracking counts. Their arcs are self-loops of the
	 * graph a record counts and no survivor carries them, so a net that tracks
	 * keeps them: they cost a consumer nothing, since a transition that cannot
	 * change the marking adds nothing to a fixpoint and its guard is still
	 * there to be counted.
	 */
	public static boolean mayDropNoEffect(Holder net) {
		if (!net.hasAnyBlock()) {
			return true;
		}
		System.out.println("Keeping the transitions with no effect: a counting record needs their arcs.");
		return false;
	}

	/** TMULT after a fusion, or null when the fusion cannot be followed. */
	private static IntMatrixCol fuse(IntMatrixCol tmult, int count, List<Integer> dropped,
			Map<Integer, Integer> survivorOf) {
		long[] weight = new long[count];
		Arrays.fill(weight, 1L);
		if (tmult != null && tmult.getColumnCount() > 0) {
			SparseIntArray col = tmult.getColumn(0);
			for (int i = 0, ie = col.size(); i < ie; i++) {
				weight[col.keyAt(i)] = 1L + col.valueAt(i);
			}
		}
		Set<Integer> dead = new HashSet<>(dropped);
		for (int td : dropped) {
			// three or more identical transitions form a chain of survivors
			int root = td;
			while (dead.contains(root)) {
				Integer next = survivorOf.get(root);
				if (next == null || next.intValue() == root) {
					return null;
				}
				root = next.intValue();
			}
			weight[root] += weight[td];
			if (weight[root] > Integer.MAX_VALUE) {
				return null;
			}
		}
		// a survivor moves down by the number of transitions dropped below it
		int[] shift = new int[count];
		int seen = 0;
		for (int t = 0; t < count; t++) {
			if (dead.contains(t)) {
				seen++;
				shift[t] = -1;
			} else {
				shift[t] = t - seen;
			}
		}
		IntMatrixCol next = new IntMatrixCol(count - dropped.size(), 0);
		SparseIntArray kept = new SparseIntArray();
		for (int t = 0; t < count; t++) {
			if (shift[t] >= 0 && weight[t] != 1L) {
				kept.append(shift[t], (int) (weight[t] - 1L));
			}
		}
		next.appendColumn(kept);
		return next;
	}
}
