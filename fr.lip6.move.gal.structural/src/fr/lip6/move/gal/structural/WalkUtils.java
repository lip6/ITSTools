package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.IntMatrixCol;

public class WalkUtils {

	private int behaviorCount;
	private int[] behaviorMap;
	private int[][] behaviors;
	private IntMatrixCol combFlow;
	private ISparsePetriNet net;
	private IntMatrixCol tFlowPT;
	private int emptyEffect = -1;

	public WalkUtils(ISparsePetriNet sr) {
		this.net = sr;

		LinkedHashMap<SparseIntArray, List<Integer>> effects = new LinkedHashMap<>();
		combFlow = new IntMatrixCol(net.getPlaceCount(), 0);
		for (int i = 0; i < net.getFlowPT().getColumnCount(); i++) {
			SparseIntArray col = SparseIntArray.sumProd(-1, net.getFlowPT().getColumn(i), 1,
					net.getFlowTP().getColumn(i));
			combFlow.appendColumn(col);
			effects.computeIfAbsent(col, k -> new ArrayList<>()).add(i);
		}
		behaviorMap = new int[net.getTransitionCount()];
		behaviors = new int [effects.size()][];
		int i = 0;
		for (Entry<SparseIntArray, List<Integer>> ent : effects.entrySet()) {
			behaviors[i] = new int [ent.getValue().size()];
			int j=0;
			for (Integer t : ent.getValue()) {
				behaviorMap[t] = i;
				behaviors[i][j++] = t;
			}
			if (ent.getKey().size() == 0) {
				// empty effect set
				emptyEffect = i;
			}
			i++;
		}
		behaviorCount = effects.size();
		tFlowPT = net.getFlowPT().transpose();
	}

	public int[] computeEnabled(SparseIntArray state) {
		int[] list = new int[net.getTransitionCount() + 1];
		// to clear any similar effects
		boolean[] seenEffects = new boolean[behaviorCount];
		
		int li = 1;
		for (int t = 0, e = net.getTransitionCount(); t < e; t++) {
			if (seenEffects[behaviorMap[t]]) {
				continue;
			}
			if (SparseIntArray.greaterOrEqual(state, net.getFlowPT().getColumn(t))) {
				list[li++] = t;
				seenEffects[behaviorMap[t]] = true;
			}
		}
		list[0] = li - 1;					
		return list;
	}
	
	public boolean canStutter (int [] enabled) {
		for (int i = enabled[0]; i >= 1; i--) {
			int t = enabled[i];
			if (behaviorMap[t] == emptyEffect) {
				return true;
			}
		}
		return false;
	}

	/** update a list of enabling to remove empty effect transitions */
	void dropEmpty(int[] enabled) {
		for (int i = enabled[0]; i >= 1; i--) {
			int t = enabled[i];
			if (combFlow.getColumn(t).size() == 0) {
				WalkUtils.dropAt(enabled, i);
			}
		}
	}

	void dropUnavailable(int[] enabled, SparseIntArray parikh) {
		for (int i = enabled[0]; i >= 1; i--) {
			int t = enabled[i];
			if (parikh.get(t) <= 0) {
				WalkUtils.dropAt(enabled, i);
			}
		}
	}

	public SparseIntArray fire(int t, SparseIntArray state) {
		// NB no enabling check
		return SparseIntArray.sumProd(1, state, 1, combFlow.getColumn(t));
	}

	public IntMatrixCol getCombFlow() {
		return combFlow;
	}

	public IntMatrixCol getFlowPT() {
		return net.getFlowPT();
	}

	public IntMatrixCol getFlowTP() {
		return net.getFlowTP();
	}

	public SparseIntArray getInitial() {
		return new SparseIntArray(net.getMarks());
	}

	public void updateEnabled(SparseIntArray state, int[] enabled, int tfired) {
		updateEnabled(state, enabled, tfired, true);
	}
	
	// we just reached "state" by firing tfired
	public void updateEnabled(SparseIntArray state, int[] enabled, int tfired, boolean dropEmptyEffects) {
		if (combFlow.getColumn(tfired).size() == 0) {
			return;
		}

		boolean[] seen = new boolean[net.getTransitionCount()];
		boolean[] seenEffects = new boolean[behaviorCount];
		SparseIntArray disabledEffects = new SparseIntArray();
		for (int i = enabled[0]; i >= 1; i--) {
			int t = enabled[i];
			if (seen[t] || seenEffects[behaviorMap[t]]) {
				WalkUtils.dropAt(enabled, i);
				continue;
			}

			if (SparseIntArray.greaterOrEqual(state, net.getFlowPT().getColumn(t))) {
				seen[t] = true;
				seenEffects[behaviorMap[t]] = true;
				continue;
			} else {
				if (!seenEffects[behaviorMap[t]]) {
					disabledEffects.put(behaviorMap[t],1);
				}
				WalkUtils.dropAt(enabled, i);
			}
		}

		// the places fed by this transition
		SparseIntArray tp = combFlow.getColumn(tfired);
		for (int pi = 0, pie = tp.size(); pi < pie; pi++) {
			int p = tp.keyAt(pi);
			if (tp.valueAt(pi) > 0) {
				// the set of transitions taking from this place
				SparseIntArray col = tFlowPT.getColumn(p);
				for (int i = 0; i < col.size(); i++) {
					int t = col.keyAt(i);
					if (seen[t] || seenEffects[behaviorMap[t]])
						continue;

					if (dropEmptyEffects && combFlow.getColumn(t).size() == 0) {
						continue;
					}

					if (SparseIntArray.greaterOrEqual(state, net.getFlowPT().getColumn(t))) {
						WalkUtils.add(enabled, t);
						seen[t] = true;
						seenEffects[behaviorMap[t]] = true;
					}
				}
			}
		}
		
		// make sure there are no illegitimately disabled effects
		for (int i=0,ie=disabledEffects.size() ; i < ie ; i++) {
			int effect = disabledEffects.keyAt(i);
			if (seenEffects[effect]) {
				continue;
			} else {
				//recompute enablings of transitions with this effect
				for (int t : behaviors[effect]) {
					if (seen[t]) {
						continue;
					} else {
						if (SparseIntArray.greaterOrEqual(state, net.getFlowPT().getColumn(t))) {
							WalkUtils.add(enabled, t);
							break;
						}
					}
				}
			}
		}
	}

	private static void add(int[] enabled, int value) {
		enabled[enabled[0] + 1] = value;
		enabled[0]++;
	}
	private static void dropAt(int[] enabled, int index) {
		if (index < enabled[0]) {
			enabled[index] = enabled[enabled[0]];
		}
		enabled[0]--;
	}
	
	public ISparsePetriNet getNet() {
		return net;
	}

}
