package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.github.lovasoa.bloomfilter.BloomFilter;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.util.IntMatrixCol;

public class RandomExplorer {

	private static final int DEBUG = 0;
	private StructuralReduction sr;
	private IntMatrixCol combFlow;
	private IntMatrixCol tFlowPT;
	private int [] behaviorMap;
	private int behaviorCount;
	
	public RandomExplorer(StructuralReduction sr) {
		this.sr = sr;
		
		LinkedHashMap<SparseIntArray, List<Integer>> effects = new LinkedHashMap<>();
		combFlow = new IntMatrixCol(sr.getPnames().size(),0);
		for (int i = 0 ;  i < sr.getFlowPT().getColumnCount() ; i ++) {
			SparseIntArray col = SparseIntArray.sumProd(-1, sr.getFlowPT().getColumn(i), 1, sr.getFlowTP().getColumn(i));
			combFlow.appendColumn(col);
			effects.computeIfAbsent(col, k -> new ArrayList<>()).add(i);
		}
		behaviorMap = new int [sr.getTnames().size()];
		int i=0;
		for (Entry<SparseIntArray, List<Integer>> ent : effects.entrySet()) {
			for (Integer t : ent.getValue()) {
				behaviorMap[t]=i;
			}
			i++;
		}
		behaviorCount = effects.size();
		tFlowPT = sr.getFlowPT().transpose();
	}

	private int [] computeEnabled(SparseIntArray state) {		
		int [] list  = new int [sr.getTnames().size()+1];
		int li = 1;
		for (int t = 0, e =  sr.getTnames().size(); t < e; t++) {
			if (SparseIntArray.greaterOrEqual(state, sr.getFlowPT().getColumn(t))) {
				list[li++] = t;
			}
		}
		list[0] = li -1 ;
		return list;
	}
	
	private void dropAt (int [] enabled, int index) {
		if (index < enabled[0]) {
			enabled[index] = enabled[enabled[0]];
		}
		enabled [0] --;
	}
	private void add (int [] enabled, int value) {
		enabled[enabled[0]+1] = value;
		enabled [0] ++;
	}

	
	// we just reached "state" by firing tfired
	public void updateEnabled (SparseIntArray state, int [] enabled, int tfired) {
		if (combFlow.getColumn(tfired).size() == 0) {
			return ;
		}
		
		boolean [] seen = new boolean [sr.getTnames().size()];
		boolean [] seenEffects = new boolean [behaviorCount];
		for (int i = enabled[0] ; i  >= 1  ; i--) {
			int t = enabled [i];
			if (seen[t] || seenEffects[behaviorMap[t]]) {
				dropAt(enabled,i);
				continue;
			}
			
			if (SparseIntArray.greaterOrEqual(state, sr.getFlowPT().getColumn(t))) {					
				seen[t] = true;
				seenEffects[behaviorMap[t]] = true;
				continue;
			} else {
				dropAt(enabled,i);
			}

		}		
		
		// the places fed by this transition
		SparseIntArray tp = combFlow.getColumn(tfired);
		for (int  pi = 0, pie=tp.size() ; pi < pie ; pi++) {
			int p = tp.keyAt(pi);
			if (tp.valueAt(pi) > 0) {
				// the set of transitions taking from this place
				SparseIntArray col = tFlowPT.getColumn(p);
				for (int i = 0 ; i < col.size() ; i++) {
					int t = col.keyAt(i);
					if (seen[t] || seenEffects[behaviorMap[t]])
						continue;

					if (combFlow.getColumn(t).size()==0) {
						continue;
					}

					if (SparseIntArray.greaterOrEqual(state, sr.getFlowPT().getColumn(t))) {
						add(enabled, t);				
						seen[t] = true;
						seenEffects[behaviorMap[t]] = true;
					}
				}
			}
		}
	}
	public int[] runGuidedReachabilityDetection (long nbSteps, SparseIntArray parikhori, List<Expression> exprs, List<Integer> repr, int timeout, boolean max) {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		
		Map<Integer, List<Integer>> repSet = computeMap(repr);		
		SparseIntArray parikh = transformParikh(parikhori, repr, repSet);
		parikhori = parikh.clone();

		long time = System.currentTimeMillis();
		SparseIntArray state = new SparseIntArray(sr.getMarks());
		int [] list = computeEnabled(state);
		dropEmpty(list);		
		dropUnavailable(list, parikh);
		
		long nbresets = 0;
		
		int [] verdicts = new int [exprs.size()];
		int i=0;
		
		// mode : 0 = RAND, 1 = MAX, 2 = MIN
		int mode = 0 ;
		
		if (list[0] == 0) {
			System.out.println("This parikh vector is obviously unfeasible : no match in initial state.");
			return verdicts;
		}
		
		for (; i < nbSteps ; i++) {			
			long dur = System.currentTimeMillis() - time + 1; 
			if (dur > 1000 * timeout) {
				System.out.println("Interrupted Parikh walk after "+ i + "  steps, including "+nbresets+ " resets, run timeout after "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ " properties seen :" + Arrays.toString(verdicts) +(DEBUG >=1 ? (" reached state " + state):"") );
				return verdicts;
			}
			if (!max) {
				if (! updateVerdicts(exprs, state, verdicts)) {
					System.out.println("Finished Parikh walk after "+ i + "  steps, including "+nbresets+ " resets, run visited all " +exprs.size()+ " properties in "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ (DEBUG >=1 ? (" reached state " + state):"") );				
					return verdicts;
				}
			} else {
				updateMaxVerdicts(exprs, state, verdicts);
			}
			
			if (list[0] == 0){
				//System.out.println("Dead end with self loop(s) found at step " + i);
				nbresets ++;
				state = new SparseIntArray(sr.getMarks());
				list = computeEnabled(state);
				parikh = parikhori.clone(); 
				dropEmpty(list);
				// each reset weakens the policy
				if (rand.nextDouble() < 1.0 - (nbresets*0.001)) {
					dropUnavailable(list, parikh);
				}
				mode = (mode + 1)% 3; 
				continue;
			}
			
			int r ;
			if (mode == 0) {
				// RAND
				r = rand.nextInt(list[0])+1;
			} else if (mode == 1) {
				// MAX
				r = list[0];
			} else {
				// MIN
				r = 1;
			}
			int tfired = list[r];
			SparseIntArray newstate = fire ( tfired, state);

			// code to draw the net state
			//			List<Integer> init = sr.getMarks();
			//			sr.setMarks(newstate.toList(init.size()));
			//			Set<Integer> pl = new HashSet<>();
			//			for (int ii=0;ii < newstate.size() ; ii++) {
			//				pl.add(newstate.keyAt(ii));
			//			}
			//FlowPrinter.drawNet(sr, "After "+tfired + " remains to fire " + parikh, pl, Collections.singleton(tfired) );			
			// NB : discards empty events
			updateEnabled(newstate, list, tfired);
			for (int tr : repSet.get(repr.get(tfired))) {
				parikh.put(tr, parikh.get(tr)-1);
			}
			if (rand.nextDouble() < 1.0 - (nbresets*0.001)) {
				dropUnavailable(list, parikh);
			}
			// undoing the modifications to sr 
			//FlowPrinter.drawNet(sr, "After "+tfired + " updated to fire " + parikh, pl, Collections.singleton(tfired) );
			//sr.setMarks(init);
			state = newstate;									
		}
		
		long dur = System.currentTimeMillis() - time + 1; 
		System.out.println("Incomplete Parikh walk after "+ i + " steps, including "+nbresets+ " resets, run finished after "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ " properties seen :" + Arrays.toString(verdicts) + " could not realise parikh vector " + (DEBUG >=1 ? parikhori : "")+ (DEBUG >=1 ? (" reached state " + state):"") );
		return verdicts;
	}

	public static class WasExhaustive {
		public boolean wasExhaustive = false;
	}
	public int[] runProbabilisticReachabilityDetection (long nbSteps, List<Expression> exprs, int timeout, int bestFirst, boolean exhaustive, WasExhaustive wex) {
		long time = System.currentTimeMillis();
		SparseIntArray istate = new SparseIntArray(sr.getMarks());

		int [] verdicts = new int [exprs.size()];
		int  i=0;
		long explored = 0;
		long seen = 1; // initial state

		try {
			List<SparseIntArray> todo = new ArrayList<>();
			todo.add(istate);
			BloomFilter bloom = null;
			Set<SparseIntArray> real = null; 
			if (! exhaustive) {
				bloom = new BloomFilter(10000000, 16*1024*1024*8);
				bloom.add(istate);
			} else {
				real = new HashSet<>();
				real.add(istate);
			}
			if (! updateVerdicts(exprs, istate, verdicts)) {
				System.out.println("Finished probabilistic random walk after "+ i + "  steps, run visited all " +exprs.size()+ " properties in 0 ms. (steps per millisecond=0 )"+ (DEBUG >=1 ? (" reached state " + istate):"") );				
				return verdicts;
			}
			int shuffled =0;
			for (; i < nbSteps && ! todo.isEmpty() && todo.size() < nbSteps ; i++) {
				SparseIntArray state = todo.remove(todo.size()-1);

				long dur = System.currentTimeMillis() - time + 1; 
				if (dur > 1000 * timeout) {
					System.out.println("Interrupted probabilistic random walk after "+ i + "  steps, run timeout after "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ " properties seen :" + Arrays.toString(verdicts) +(DEBUG >=1 ? (" reached state " + state):"") );
					break;
				}

				int [] list = computeEnabled(state);
				explored++;

				if (list[0] == 0){
					//System.out.println("Dead end with self loop(s) found at step " + i);				
					continue;
				}

				boolean dobreak = false;
				for (int ti = 1 ; ti-1 < list[0] && i < nbSteps && todo.size() < nbSteps; ti++, i++) {
					SparseIntArray succ = fire(list[ti],state);
					if (!exhaustive) {
						if (! bloom.contains(succ)) {
							todo.add(succ);
							bloom.add(succ);
							seen++;
							if (! updateVerdicts(exprs, succ, verdicts)) {
								System.out.println("Finished probabilistic random walk after "+ i + "  steps, run visited all " +exprs.size()+ " properties in "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ (DEBUG >=1 ? (" reached state " + state):"") );				
								dobreak = true;
								break;
							}
						}
					} else {
						if (!real.contains(succ)) {
							todo.add(succ);
							real.add(succ);
							seen++;
							if (! updateVerdicts(exprs, succ, verdicts)) {
								System.out.println("Finished exhaustive random walk after "+ i + "  steps, run visited all " +exprs.size()+ " properties in "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ (DEBUG >=1 ? (" reached state " + state):"") );				
								dobreak = true;
								break;
							}
							if (DEBUG >=2) {
								if (! bloom.contains(succ)) {
									bloom.add(succ);
								} else {
									System.out.println("collision " + succ);
								}
							}
						}
					}

					if (list[0] > 1000 && ti%1000 == 0) {
						dur = System.currentTimeMillis() - time + 1; 
						if (dur > 1000 * timeout) {
							dobreak = true;
							break;
						}
					}
				}
				if (dobreak) 
					break;
				if (i/10000 > shuffled) {
					shuffled = i / 1000;
					//Collections.shuffle(todo);
					Collections.reverse(todo);
				}
			}
			if (todo.isEmpty()) {
				wex.wasExhaustive = true;
				if (! exhaustive) {
					System.out.println("Probably explored full state space saw : "+ seen + "  states, properties seen :" + Arrays.toString(verdicts) );
				} else {
					System.out.println("Explored full state space saw : "+ seen + "  states, properties seen :" + Arrays.toString(verdicts) );
				}
			}
			long dur = System.currentTimeMillis() - time + 1; 
			if (! exhaustive) {
				System.out.println("Probabilistic random walk after "+ i + "  steps, saw "+seen+" distinct states, run finished after "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ " properties seen :" + Arrays.toString(verdicts)  );
			} else {
				System.out.println("Exhaustive walk after "+ i + "  steps, saw "+seen+" distinct states, run finished after "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ " properties seen :" + Arrays.toString(verdicts)  );
			}
		} catch (OutOfMemoryError e) {
			long dur = System.currentTimeMillis() - time + 1; 
			System.out.println("Probabilistic random walk exhausted memory after "+ i + "  steps, saw "+seen+" distinct states, run finished after "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ " properties seen :" + Arrays.toString(verdicts)  );
		}
		return verdicts;
	}

	public int[] runRandomReachabilityDetection (long nbSteps, List<Expression> exprs, int timeout, int bestFirst) {
		return runRandomReachabilityDetection(nbSteps,exprs,timeout,bestFirst,false);
	}
	
	/**
	 * A pseudo-random memoryless walk of the state space.
	 * @param nbSteps The maximum number of steps/transition firings
	 * @param exprs The list of expressions we are trying to find witnesses to
	 * @param timeout maximum time for this check, in seconds
	 * @param bestFirst -1 for random run or the index of the property we want to target with the Best-first heuristic
	 * @param max if true, we are trying to maximize the expressions (bounds) rather than test their truth value
	 * @return a set of answers, one per expression in exprs. For "normal" case these are 1 if we found acounter example or 0 otherwise. For bounds these are the max value of the expression.
	 */
	public int[] runRandomReachabilityDetection (long nbSteps, List<Expression> exprs, int timeout, int bestFirst, boolean max) {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		long time = System.currentTimeMillis();
		SparseIntArray state = new SparseIntArray(sr.getMarks());
		int [] list = computeEnabled(state);
		dropEmpty(list);		
		
		int last = -1;
		long nbresets = 0;
		
		int [] verdicts = new int [exprs.size()];
		
		int  i=0;
		for (; i < nbSteps ; i++) {
			long dur = System.currentTimeMillis() - time + 1; 
			if (dur > 1000 * timeout) {
				System.out.println("Interrupted "+(bestFirst>=0?"Best-First ":"")+"random walk after "+ i + "  steps, including "+nbresets+ " resets, run timeout after "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ " properties seen :" + Arrays.toString(verdicts) +(DEBUG >=1 ? (" reached state " + state):"") );
				return verdicts;
			}
			if (!max) {
				if (! updateVerdicts(exprs, state, verdicts)) {
					System.out.println("Finished "+(bestFirst>=0?"Best-First ":"")+"random walk after "+ i + "  steps, including "+nbresets+ " resets, run visited all " +exprs.size()+ " properties in "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ (DEBUG >=1 ? (" reached state " + state):"") );				
					return verdicts;
				}
			} else {
				updateMaxVerdicts(exprs, state, verdicts);
			}
			if (list[0] == 0){
				//System.out.println("Dead end with self loop(s) found at step " + i);
				nbresets ++;
				last = -1;
				state = new SparseIntArray(sr.getMarks());
				list = computeEnabled(state);
				dropEmpty(list);
				continue;
			}
			
			if (bestFirst==-1 || list[0]<=1) {
				int r = rand.nextInt(list[0])+1;
				int tfired = list[r];			

				boolean repeat = shouldRepeatLast(last, state, rand); 
				if (repeat) {
					tfired = last;				
					// iterate firing
					do {
						state = fire ( tfired, state);
						i++;
					} while (SparseIntArray.greaterOrEqual(state, sr.getFlowPT().getColumn(tfired)));
					updateEnabled(state, list, tfired);
					last = -1;
				} else {
					SparseIntArray newstate = fire ( tfired, state);				
					// NB : discards empty events
					updateEnabled(newstate, list, tfired);

					last = tfired;				
					state = newstate;
				}
			} else {
				// heuristically follow a successor with "Best-first search"
				int minDist = max ? 0 :Integer.MAX_VALUE;
				List<Integer>  mini = new ArrayList<>();
				List<SparseIntArray> bestSucc = new ArrayList<>();
				for (int ti = 1 ; ti-1 < list[0] && i < nbSteps; ti++) {
					SparseIntArray succ = fire(list[ti],state);
					int distance = exprs.get(bestFirst).evalDistance(succ, false);					
					if ( (!max && distance < minDist) || (max && distance > minDist)) {
						mini.clear();
						bestSucc.clear();
						minDist = distance;
					}
					if ((!max && distance <= minDist) || (max && distance >= minDist)) {
						mini.add(list[ti]);
						bestSucc.add(succ);
					}
					i++;
					if (list[0] > 1000 && ti%1000 == 0) {
						dur = System.currentTimeMillis() - time + 1; 
						if (dur > 1000 * timeout) {
							break;
						}
					}
				}
				int chosen = rand.nextInt(bestSucc.size());
				state = bestSucc.get(chosen);
				last = mini.get(chosen);
				updateEnabled(state, list, last);
			}
		}
		long dur = System.currentTimeMillis() - time + 1; 
		System.out.println("Incomplete "+(bestFirst>=0?"Best-First ":"")+"random walk after "+ i + "  steps, including "+nbresets+ " resets, run finished after "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ " properties seen :" + Arrays.toString(verdicts) +(DEBUG >=1 ? (" reached state " + state):"") );

		return verdicts;
	}

	private boolean updateVerdicts(List<Expression> exprs, SparseIntArray state, int[] verdicts) {
		boolean remains = false;
		for (int v = 0; v < verdicts.length; v++) {
			if (verdicts[v] == 0) {
				remains = true;
				if (exprs.get(v).eval(state) == 1) {
					verdicts[v] = 1;
				}
			}
		}
		return remains;
	}

	
	private void updateMaxVerdicts(List<Expression> exprs, SparseIntArray state, int[] verdicts) {
		for (int v = 0; v < verdicts.length; v++) {
			verdicts[v] = Math.max(exprs.get(v).eval(state),verdicts[v]);
		}
	}
	
	public void runGuidedDeadlockDetection (long nbSteps, SparseIntArray parikhori, List<Integer> repr, int timeout) throws DeadlockFound {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		long time = System.currentTimeMillis();
		Map<Integer, List<Integer>> repSet = computeMap(repr);		
		SparseIntArray parikh = transformParikh(parikhori, repr, repSet);
		parikhori = parikh.clone();
		SparseIntArray state = new SparseIntArray(sr.getMarks());
		int [] list = computeEnabled(state);
		dropEmpty(list);		
		dropUnavailable(list, parikh);		
		
		long nbresets = 0;
		int i=0;
		for (; i < nbSteps ; i++) {
			long dur = System.currentTimeMillis() - time + 1; 
			if (dur > 1000 * timeout) {
				System.out.println("Interrupted Parikh directed walk after "+ i + "  steps, including "+nbresets+ " resets, run timeout after "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ (DEBUG >=1 ? (" reached state " + state):"") );
				break;
			}
			if (list[0] == 0) {
				// includes empty effects 
				list = computeEnabled(state);
				if (list[0] == 0) {
					System.out.println("Interrupted Parikh directed walk after "+ i + "  steps, including "+nbresets+ " resets, run found a deadlock after "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ (DEBUG >=1 ? (" reached state " + state):"") );
					throw new DeadlockFound();
				} else {
					//System.out.println("Dead end with self loop(s) found at step " + i);
					nbresets ++;
					state = new SparseIntArray(sr.getMarks());
					list = computeEnabled(state);
					parikh = parikhori.clone(); 
					dropEmpty(list);
					dropUnavailable(list, parikh);
					continue;
				}
			}
			int r = rand.nextInt(list[0])+1;
			int tfired = list[r];
			SparseIntArray newstate = fire ( tfired, state);			
			// NB : discards empty events
			updateEnabled(newstate, list, tfired);
			
			for (int tr : repSet.get(repr.get(tfired))) {
				parikh.put(tr, parikh.get(tr)-1);
			}
			if (rand.nextDouble() < 1.0 - (nbresets*0.001)) {
				dropUnavailable(list, parikh);
			}
			state = newstate;			
		}
		long dur = System.currentTimeMillis() -time + 1; // avoid zero divide
		System.out.println("Parikh directed walk for "+ i + "  steps, including "+nbresets+ " resets, run took "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ (DEBUG >=1 ? (" reached state " + state):"") );
	}

	private SparseIntArray transformParikh(SparseIntArray parikhori, List<Integer> repr, Map<Integer, List<Integer>> repSet) {
		SparseIntArray parikh = new SparseIntArray();
		for (int i=0, e=parikhori.size() ; i < e ; i++) {
			int t = repr.get(parikhori.keyAt(i));
			int k = parikhori.valueAt(i);
			for (int tr : repSet.get(t)) {
				parikh.put(tr, k);
			}
		}
		return parikh;
	}

	private Map<Integer, List<Integer>> computeMap(List<Integer> repr) {
		Map<Integer,List<Integer>> repSet = new HashMap<>();
		for (int i=0, e=repr.size(); i < e ; i++) {
			final int t = i;
			repSet.compute(repr.get(t), (k, v) ->  {
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
	
	public void runDeadlockDetection (long nbSteps, boolean fullRand, int timeout) throws DeadlockFound {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		
		long time = System.currentTimeMillis();
		SparseIntArray state = new SparseIntArray(sr.getMarks());
		int [] list = computeEnabled(state);
		dropEmpty(list);		
		
		int last = -1;
		
		long nbresets = 0;
		int i=0;
		for ( ; i < nbSteps ; i++) {	
			long dur = System.currentTimeMillis() - time + 1; 
			if (dur > 1000 * timeout) {
				System.out.println("Interrupted Parikh directed walk after "+ i + "  steps, including "+nbresets+ " resets, run timeout after "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ (DEBUG >=1 ? (" reached state " + state):"") );
				return;
			}
			if (list[0] == 0) {
				// includes empty effects 
				list = computeEnabled(state);
				if (list[0] == 0) {
					System.out.println("Finished Parikh directed walk after "+ i + "  steps, including "+nbresets+ " resets, run found a deadlock after "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ (DEBUG >=1 ? (" reached state " + state):"") );
					throw new DeadlockFound();
				} else {
					//System.out.println("Dead end with self loop(s) found at step " + i);
					nbresets ++;
					last = -1;
					state = new SparseIntArray(sr.getMarks());
					list = computeEnabled(state);
					dropEmpty(list);
					continue;
				}
			}						
			int r = rand.nextInt(list[0])+1;
			int tfired = list[r];			
			
			boolean repeat = shouldRepeatLast(last, state, rand);
			if (repeat) {
				tfired = last;				
				// iterate firing
				do {
					state = fire ( tfired, state);
					i++;
				} while (SparseIntArray.greaterOrEqual(state, sr.getFlowPT().getColumn(tfired)));
				updateEnabled(state, list, tfired);
				last = -1;
				continue;
			}
			
			if (list[0]==1 || fullRand || rand.nextDouble() >= 0.6) {
				SparseIntArray newstate = fire ( tfired, state);			
				// NB : discards empty events
				updateEnabled(newstate, list, tfired);													
				last = tfired;
				state = newstate;
			} else {
				// heuristically follow a successor with less outgoing edges
				
				SparseIntArray [] succ = new SparseIntArray[list[0]];
				for (int ti = 1 ; ti-1 < list[0] ; ti++) {
					succ[ti-1] = fire(list[ti],state);
					i++;
				}
				int minSucc = sr.getTnames().size()+1;
				int mini = -1;
				int [] minList = null;
				for (int ti = 0 ; ti < succ.length ; ti++) {
					int[] listC = Arrays.copyOf(list, list.length);
					updateEnabled(succ[ti], listC, list[ti+1]);
					if (listC[0] < minSucc   || (listC[0] == minSucc && rand.nextDouble() >= 0.5)) {
						minSucc = listC[0];
						mini = ti;
						minList = listC;
					}
				}
				state = succ[mini];
				last = list[mini+1];
				list = minList;				
			}
		}
		long dur = System.currentTimeMillis() -time + 1; // avoid zero divide
		System.out.println("Random "+ (fullRand?"":"directed ") +"walk for "+ i + "  steps, including "+nbresets+ " resets, run took "+ dur +" ms (no deadlock found). (steps per millisecond="+ (i/dur) +" )"+ (DEBUG >=1 ? (" reached state " + state):"") );
	}

	public boolean shouldRepeatLast(int last, SparseIntArray state, ThreadLocalRandom rand) {
		boolean repeat = false;
		if (last != -1 && sr.getFlowPT().getColumn(last).size() > 0 &&  rand.nextDouble() < 0.98 && SparseIntArray.greaterOrEqual(state, sr.getFlowPT().getColumn(last))) {
			// make sure there is no divergent behavior here
			SparseIntArray combb = combFlow.getColumn(last);
			for (int j=0, je = combb.size() ; j < je ; j++) {
				if (combb.valueAt(j) < 0) {
					repeat = true;
					break;
				}
			}
		}
		return repeat;
	}
	
	/** update a list of enabling to remove empty effect transitions*/ 
	private void dropEmpty(int [] enabled) {
		for (int i = enabled[0] ; i  >= 1  ; i--) {
			int t = enabled [i];
			if (combFlow.getColumn(t).size() == 0) {
				dropAt(enabled,i);				
			}
		}
	}

	private void dropUnavailable (int [] enabled, SparseIntArray parikh) {
		for (int i = enabled[0] ; i  >= 1  ; i--) {
			int t = enabled [i];
			if (parikh.get(t) <= 0) {
				dropAt(enabled,i);				
			}
		}
	}
	
	public SparseIntArray fire (int t, SparseIntArray state) {
		// NB no enabling check
		return SparseIntArray.sumProd(1, state, 1, combFlow.getColumn(t));
	}
	
}
