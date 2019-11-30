package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.util.MatrixCol;

public class RandomExplorer {

	private static final int DEBUG = 0;
	private StructuralReduction sr;
	private MatrixCol combFlow;
	private MatrixCol tFlowPT;

	public RandomExplorer(StructuralReduction sr) {
		this.sr = sr;
		
		combFlow = new MatrixCol(sr.getPnames().size(),0);
		for (int i = 0 ;  i < sr.getFlowPT().getColumnCount() ; i ++) {
			combFlow.appendColumn(SparseIntArray.sumProd(-1, sr.getFlowPT().getColumn(i), 1, sr.getFlowTP().getColumn(i)));
		}
	
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
		for (int i = enabled[0] ; i  >= 1  ; i--) {
			int t = enabled [i];
			if (seen[t]) {
				dropAt(enabled,i);
				continue;
			}
			
			if (SparseIntArray.greaterOrEqual(state, sr.getFlowPT().getColumn(t))) {					
				seen[t] = true;
				continue;
			} else {
				dropAt(enabled,i);
			}

		}		
		
		// the places fed by this transition
		SparseIntArray tp = sr.getFlowTP().getColumn(tfired);
		for (int  pi = 0 ; pi < tp.size() ; pi++) {
			int p = tp.keyAt(pi);
			// the set of transitions taking from this place
			SparseIntArray col = tFlowPT.getColumn(p);
			for (int i = 0 ; i < col.size() ; i++) {
				int t = col.keyAt(i);
				if (seen[t])
					continue;
			
				if (combFlow.getColumn(t).size()==0) {
					continue;
				}
				
				if (SparseIntArray.greaterOrEqual(state, sr.getFlowPT().getColumn(t))) {
					add(enabled, t);				
					seen[t] = true;
				}
			}
		}
	}
	public int[] run (long nbSteps, SparseIntArray parikhori, List<Expression> exprs, List<Integer> repr, int timeout) {
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
			
			if (! updateVerdicts(exprs, state, verdicts)) {
				System.out.println("Finished Parikh walk after "+ i + "  steps, including "+nbresets+ " resets, run visited all " +exprs.size()+ "properties "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ (DEBUG >=1 ? (" reached state " + state):"") );				
				return verdicts;
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
				continue;
			}
			
			int r = rand.nextInt(list[0])+1;
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
			if (rand.nextDouble() < 1.0 - (nbresets*0.001)) {
				dropUnavailable(list, parikh);
			}
			for (int tr : repSet.get(repr.get(tfired))) {
				parikh.put(tr, parikh.get(tr)-1);
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
	
	public int[] run (long nbSteps, List<Expression> exprs, int timeout) {
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
				System.out.println("Interrupted random walk after "+ i + "  steps, including "+nbresets+ " resets, run timeout after "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ " properties seen :" + Arrays.toString(verdicts) +(DEBUG >=1 ? (" reached state " + state):"") );
				return verdicts;
			}
			if (! updateVerdicts(exprs, state, verdicts)) {
				System.out.println("Finished random walk after "+ i + "  steps, including "+nbresets+ " resets, run visited all " +exprs.size()+ "properties "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ (DEBUG >=1 ? (" reached state " + state):"") );				
				return verdicts;
			}
			
			int r = rand.nextInt(list[0])+1;
			int tfired = list[r];			
			
			if (last != -1 && rand.nextDouble() < 0.98 && SparseIntArray.greaterOrEqual(state, sr.getFlowPT().getColumn(last))) {
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
			if (list[0] == 0){
				//System.out.println("Dead end with self loop(s) found at step " + i);
				nbresets ++;
				last = -1;
				state = new SparseIntArray(sr.getMarks());
				list = computeEnabled(state);
				dropEmpty(list);
				continue;
			}
			
		}
		long dur = System.currentTimeMillis() - time + 1; 
		System.out.println("Incomplete random walk after "+ i + "  steps, including "+nbresets+ " resets, run finished after "+ dur +" ms. (steps per millisecond="+ (i/dur) +" )"+ " properties seen :" + Arrays.toString(verdicts) +(DEBUG >=1 ? (" reached state " + state):"") );

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

	public void run (long nbSteps, SparseIntArray parikhori, List<Integer> repr, int timeout) throws DeadlockFound {
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
			
			if (rand.nextDouble() < 1.0 - (nbresets*0.001)) {
				dropUnavailable(list, parikh);
			}
			for (int tr : repSet.get(repr.get(tfired))) {
				parikh.put(tr, parikh.get(tr)-1);
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
	
	public void run (long nbSteps, boolean fullRand, int timeout) throws DeadlockFound {
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
			
			if (last != -1 && rand.nextDouble() < 0.98 && SparseIntArray.greaterOrEqual(state, sr.getFlowPT().getColumn(last))) {
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
