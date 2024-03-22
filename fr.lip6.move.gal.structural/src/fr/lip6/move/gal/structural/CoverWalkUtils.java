package fr.lip6.move.gal.structural;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.IntMatrixCol;

public class CoverWalkUtils {

    // Constants and member variables
    private IntMatrixCol combFlow; // Combined flow matrix representing the net's dynamics.
    private ISparsePetriNet net; // The Petri net instance.
    private IntMatrixCol tFlowPT; // Transposed flow matrix from places to transitions.
    private int[] initialEnabling; // Cache for the initially enabled transitions.

    /**
     * Constructs a CoverWalkUtils instance for a given sparse Petri net.
     * @param sr The sparse Petri net to be used.
     */
    public CoverWalkUtils(ISparsePetriNet sr) {
        this.net = sr;

        // Initialize the combined flow matrix.
        combFlow = new IntMatrixCol(net.getPlaceCount(), 0);
        for (int i = 0; i < net.getFlowPT().getColumnCount(); i++) {
            // Calculate the combined flow for each transition.
            SparseIntArray col = SparseIntArray.sumProd(-1, net.getFlowPT().getColumn(i), 1,
                    net.getFlowTP().getColumn(i));
            combFlow.appendColumn(col);
        }

        // Transpose the flow matrix for efficient access during state updates.
        tFlowPT = net.getFlowPT().transpose();
    }


    /**
     * Computes the enabled transitions for a given state in the Petri net.
     * @param state The current state of the Petri net.
     * @return An array of enabled transitions.
     */
    public int[] computeEnabled(SparseIntArray state) {
        int[] list = new int[net.getTransitionCount() + 1];
        
        int li = 1; // Index for inserting enabled transitions into the list.
        for (int t = 0, e = net.getTransitionCount(); t < e; t++) {
            // Check if the transition is enabled by comparing the current state with the transition's requirements.
            if (SparseIntArray.greaterOrEqual(state, net.getFlowPT().getColumn(t))) {
                list[li++] = t; // Add the transition to the list of enabled transitions.
            }
        }
        list[0] = li - 1; // The first cell records the number of enabled transitions.
        return list;
    }

    /**
     * Checks if the net can stutter, i.e., if there are enabled transitions that don't change the state
     * given the places marked with omega.
     * @param enabled An array of enabled transitions.
     * @param omegas A SparseIntArray indicating places marked with omega.
     * @return True if the net can stutter, false otherwise.
     */
    public boolean canStutter(int[] enabled, SparseIntArray omegas) {
        for (int i = enabled[0]; i >= 1; i--) {
            int transition = enabled[i];
            SparseIntArray transitionEffects = combFlow.getColumn(transition);
            if (SparseIntArray.containsAllKeys(omegas, transitionEffects)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Updates a list of enabled transitions to remove those with effects that are entirely nullified
     * by omega-marked places.
     * @param enabled An array of enabled transitions.
     * @param omegas A SparseIntArray indicating places marked with omega.
     */
    void dropEmpty(int[] enabled, SparseIntArray omegas) {
        for (int i = enabled[0]; i >= 1; i--) {
            int transition = enabled[i];
            SparseIntArray transitionEffects = combFlow.getColumn(transition);
            if (SparseIntArray.containsAllKeys(omegas, transitionEffects)) {
                CoverWalkUtils.dropAt(enabled, i);
            }
        }
    }
    
    
	void dropUnavailable(int[] enabled, SparseIntArray parikh) {
		for (int i = enabled[0]; i >= 1; i--) {
			int t = enabled[i];
			if (parikh.get(t) <= 0) {
				CoverWalkUtils.dropAt(enabled, i);
			}
		}
	}

	public SparseIntArray fire(int t, SparseIntArray state) {
		// NB no enabling check
		return SparseIntArray.sumProdOmega(1, state, 1, combFlow.getColumn(t));
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

		SparseIntArray omegas = extractOmegas(state);
		
		boolean[] seen = new boolean[net.getTransitionCount()];
		for (int i = enabled[0]; i >= 1; i--) {
			int t = enabled[i];
			if (seen[t]) {
				CoverWalkUtils.dropAt(enabled, i);
				continue;
			}

			if (SparseIntArray.greaterOrEqual(state, net.getFlowPT().getColumn(t))) {
				seen[t] = true;
				continue;
			} else {
				CoverWalkUtils.dropAt(enabled, i);
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
					if (seen[t])
						continue;

					if (dropEmptyEffects && SparseIntArray.containsAllKeys(omegas, combFlow.getColumn(t))) {
						continue;
					}

					if (SparseIntArray.greaterOrEqual(state, net.getFlowPT().getColumn(t))) {
						CoverWalkUtils.add(enabled, t);
						seen[t] = true;						
					}
				}
			}
		}
		
	}

	private static final int omega  = Integer.MAX_VALUE;
	
	private SparseIntArray extractOmegas(SparseIntArray state) {
		SparseIntArray omegas = new SparseIntArray();
		
		for (int i=0;i< state.size(); i++) {
			if (state.valueAt(i) == omega) {
				omegas.append(state.keyAt(i), omega);
			}
		}
		
		return omegas;
	}


	public static void add(int[] enabled, int value) {
		enabled[enabled[0] + 1] = value;
		enabled[0]++;
	}
	public static void dropAt(int[] enabled, int index) {
		if (index < enabled[0]) {
			enabled[index] = enabled[enabled[0]];
		}
		enabled[0]--;
	}
	
	public ISparsePetriNet getNet() {
		return net;
	}

	public int[] getInitialEnabling() {
		if (initialEnabling == null) {
			initialEnabling = computeEnabled(getInitial());
		}
		return initialEnabling;
	}

}
