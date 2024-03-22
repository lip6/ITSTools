package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class CoverWalker {

	
	private static final int DEBUG = 0;
	private CoverWalkUtils wu;

	public CoverWalker(ISparsePetriNet sr) {
		this.wu = new CoverWalkUtils(sr);
	}

	private static final int omega  = Integer.MAX_VALUE;
	
	private void updateMaxVerdicts(List<Expression> exprs, SparseIntArray state, int[] verdicts) {
		for (int v = 0; v < verdicts.length; v++) {
			if (verdicts[v] == omega) {
				continue;
			}
			if (exprs.get(v).getOp() == Op.ADD) {
				Expression dad = exprs.get(v);
				int sum = 0;
				for (int cid = 0 ; cid < dad.nbChildren() ; cid++) {
					Expression c = dad.childAt(cid);
					if (c.getOp() == Op.CONST) {
						sum += c.getValue();
					} else if (c.getOp() == Op.PLACEREF) {
						int val = state.get(c.getValue());
						if (val == omega) {
							sum = omega;
							break;
						} else {
							sum += val;
						}
					} else {
						throw new RuntimeException("Unexpected child in bounds query.");
					}
				}
				verdicts[v] = Math.max(sum, verdicts[v]);
			} else {
				verdicts[v] = Math.max(exprs.get(v).eval(state), verdicts[v]);
			}
		}
	}
	
	
	public int[] runRandomReachabilityDetection(long nbSteps, List<Expression> exprs, int timeout, int bestFirst,
			boolean max, SparseIntArray maxReached) {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		long time = System.currentTimeMillis();
		SparseIntArray state = wu.getInitial();
		int[] list = wu.getInitialEnabling().clone();
		wu.dropEmpty(list, new SparseIntArray());

		SparseIntArray initstate = state.clone();
		int[] initlist = list.clone();

		int last = -1;
		long nbresets = 0;

		int[] verdicts = new int[exprs.size()];
		
		int STACKLIMIT = 20;
		int initialTotalTokens = initstate.sumValues();
		int totalTokens = initialTotalTokens;
		int totalOmega = 0;
		LinkedHashSet<SparseIntArray> stack = new LinkedHashSet<>();
		
		int i = 0;
		for (; i < nbSteps; i++) {
			long dur = System.currentTimeMillis() - time + 1;
			if (dur > 1000 * timeout) {
				// we might not have tested the current state, which is a shame.
				// worst case is double evaluating when timeout is reached, who cares on small
				// problems.
				updateMaxVerdicts(exprs, state, verdicts);

				System.out.println("Interrupted " + (bestFirst >= 0 ? "Best-First " : "") + "random walk after " + i
						+ "  steps, including " + nbresets + " resets, run timeout after " + dur
						+ " ms. (steps per millisecond=" + (i / dur) + " )" + " properties seen " + Arrays.toString(verdicts)
						+ (DEBUG >= 1 ? new SparseIntArray(verdicts) + (" reached state " + state) : ""));
				return verdicts;
			}
			boolean skipVerdict = false;
			
			int newTotalTokens = state.sumValuesOmega();
			if (newTotalTokens > totalTokens) {
				int addedOmega = addOmega(state, stack);
				if (addedOmega >0) {
					totalTokens = state.sumValuesOmega();
					stack.clear();
					totalOmega += addedOmega;
					maxReached.move(state.clone());
				} else {
					totalTokens = newTotalTokens;
				}
			}
			if (stack.size() == STACKLIMIT) {
				Iterator<SparseIntArray> it = stack.iterator();
				it.next();
				it.remove();
			}
			stack.add(state);

			updateMaxVerdicts(exprs, state, verdicts);

			if (list[0] == 0 || (i >= nbSteps / 3 && nbresets == 0) || (i >= 2 * nbSteps / 3 && nbresets <= 1)) {
				// System.out.println("Dead end with self loop(s) found at step " + i);
				nbresets++;
				last = -1;
				state = initstate.clone();
				list = initlist.clone();
				totalOmega = 0;
				totalTokens = initialTotalTokens;
				stack.clear();
				continue;
			}

			if (bestFirst == -1 || list[0] <= 1) {
				int r = rand.nextInt(list[0]) + 1;
				int tfired = list[r];

				SparseIntArray newstate = wu.fire(tfired, state);
				// NB : discards empty events
				wu.updateEnabled(newstate, list, tfired);
				
				last = tfired;
				state = newstate;
			} else {
				// heuristically follow a successor with "Best-first search"
				int minDist = max ? 0 : Integer.MAX_VALUE;
				List<Integer> mini = new ArrayList<>();
				List<SparseIntArray> bestSucc = new ArrayList<>();
				for (int ti = 1; ti - 1 < list[0] && i < nbSteps; ti++) {
					SparseIntArray succ = wu.fire(list[ti], state);
					int distance = exprs.get(bestFirst).evalDistance(succ, false);
					if ((!max && distance < minDist) || (max && distance > minDist)) {
						mini.clear();
						bestSucc.clear();
						minDist = distance;
					}
					if ((!max && distance <= minDist) || (max && distance >= minDist)) {
						mini.add(list[ti]);
						bestSucc.add(succ);
					}
					i++;
					if (list[0] > 1000 && ti % 1000 == 0) {
						dur = System.currentTimeMillis() - time + 1;
						if (dur > 1000 * timeout) {
							break;
						}
					}
				}
				int chosen = rand.nextInt(bestSucc.size());
				state = bestSucc.get(chosen);
				last = mini.get(chosen);
				wu.updateEnabled(state, list, last);
			}
		}
		long dur = System.currentTimeMillis() - time + 1;
		if (nbSteps > 50) {
			System.out.println("Incomplete " + (bestFirst >= 0 ? "Best-First " : "") + "random walk after " + i
					+ "  steps, including " + nbresets + " resets, run finished after " + dur
					+ " ms. (steps per millisecond=" + (i / dur) + " )" + " properties (out of " + exprs.size()
					+ ") seen :" + Arrays.toString(verdicts) + (DEBUG >= 1 ? (" reached state " + state) : ""));
		}

		return verdicts;
	}


	private int addOmega(SparseIntArray state, LinkedHashSet<SparseIntArray> stack) {
		int added = 0;
		// implement A. Valmari's multi scan to add more omega in one go.
		boolean repeat = false;
		do {
			repeat = false;
			for (Iterator<SparseIntArray> it = stack.iterator() ; it.hasNext()  ; ) {
				SparseIntArray old = it.next();
				if (SparseIntArray.coversStrictly(state, old)) {
					int localAdded = 0;
					for (int i=0,ie=state.size(); i < ie ; i++) {
						if (state.valueAt(i) != omega && old.get(state.keyAt(i)) < state.valueAt(i)) {
							state.setValueAt(i, omega);
							added ++;
							localAdded ++;							
							repeat = true;
						}
					}
					
					if (localAdded > 0) {
						it.remove();
						break;
					}
					
				}
			}
		} while (repeat);
		return added;
	}
	
	
	// another implementation
    public static boolean compareAndUpdateIfDominates(SparseIntArray cur, SparseIntArray past) {
        // Determine the sizes outside the loop since they are fixed.
        int ss1 = cur.size();
        int ss2 = past.size();

        // Step 1: Check if cur strictly covers past.
        if (!SparseIntArray.coversStrictly(cur, past)) {
            return false;
        }

        // Step 2: Iterate a second time to update dominant entries.
        int i = 0, j = 0;
        for ( ; i < ss1 && j < ss2; ) {
            int sk1 = cur.keyAt(i);
            int sk2 = past.keyAt(j);

            if (sk1 == sk2) {
                // Update to omega if cur's value is strictly greater.
                if (cur.valueAt(i) > past.valueAt(j)) {
                    cur.setValueAt(i, omega);
                }
                i++;
                j++;
            } else if (sk1 > sk2) {
                // If there's a key in past not present in cur, move forward in past.
                throw new RuntimeException("current should dominate past !");
            } else {
                // If cur has a key not in past, update to omega and move forward in cur.
                cur.setValueAt(i, omega);
                i++;
            }
       
        }
        if (i < ss1 && j >= ss2) {
        	for (;i<ss1;i++) {
        		cur.setValueAt(i, omega);
        	}
        }

        return true;
    }
    
	

	
	
}
