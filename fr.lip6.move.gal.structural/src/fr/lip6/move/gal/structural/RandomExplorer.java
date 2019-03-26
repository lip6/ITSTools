package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.MatrixCol;

public class RandomExplorer {

	private StructuralReduction sr;
	private MatrixCol combFlow;
	private int [][] conflictSet;
	private int [][] mayEnableSet;

	public RandomExplorer(StructuralReduction sr) {
		this.sr = sr;
		
		combFlow = new MatrixCol(sr.getPnames().size(),0);
		for (int i = 0 ;  i < sr.getFlowPT().getColumnCount() ; i ++) {
			combFlow.appendColumn(SparseIntArray.sumProd(-1, sr.getFlowPT().getColumn(i), 1, sr.getFlowTP().getColumn(i)));
		}
	
		List<Set<Integer>> lconflictSet = new ArrayList<>();
		List<Set<Integer>> lmayEnableSet = new ArrayList<>();
		for (int  t=0 ; t < sr.getTnames().size() ; t++) {
			lconflictSet.add(new TreeSet<>());
			lmayEnableSet.add(new TreeSet<>());
		}
		
		MatrixCol tComb = combFlow.transpose();
		MatrixCol tFlowPT = sr.getFlowPT().transpose();
		for (int  p = 0 ; p < tComb.getColumnCount() ; p++) {
			SparseIntArray col = tComb.getColumn(p);
			SparseIntArray colPT = tFlowPT.getColumn(p);
			// every transition with <0 effect in this set is in conflict with every other one
			for (int i = 0 ; i < col.size() ; i++) {
				int ki = col.keyAt(i);
				int vi = col.valueAt(i);
				
				if (vi < 0) {
					for (int j = 0 ; j < colPT.size() ; j++) {
						int kj = colPT.keyAt(j);
						lconflictSet.get(ki).add(kj);						
					}
				}
			}
		}
		// stored as an array of 0/1 entries
		conflictSet = new int[lconflictSet.size()][lconflictSet.size()];
		for (int i = 0; i < lconflictSet.size() ; i++) {
			for ( Integer tind : lconflictSet.get(i)) {
				conflictSet[i][tind] = 1;
			}
		}
				
		MatrixCol tFlowTP = sr.getFlowTP().transpose();
		for (int  p = 0 ; p < tFlowPT.getColumnCount() ; p++) {
			// the set of transitions taking from this place
			SparseIntArray col = tFlowPT.getColumn(p);
			// the set of transitions feeding this place
			SparseIntArray feed = tFlowTP.getColumn(p);
			for (int i = 0 ; i < col.size() ; i++) {
				for (int j = 0 ; j < feed.size() ; j++) {
					int ki = col.keyAt(i);
					int kj = feed.keyAt(j);
					lmayEnableSet.get(kj).add(ki);
				}	
			}
		}
		mayEnableSet = new int[lmayEnableSet.size()][];
		for (int i = 0; i < lmayEnableSet.size() ; i++) {
			mayEnableSet[i] = new int [lmayEnableSet.get(i).size()];
			int j =0;
			for (Integer tind : lmayEnableSet.get(i)) {
				mayEnableSet[i][j++] = tind;
			}
		}
		
	}

	public List<Integer> computeEnabled(SparseIntArray state) {
		List<Integer> list = new ArrayList<>();
		for (int t = 0, e =  sr.getTnames().size(); t < e; t++) {
			if (greaterOrEqual2(state, sr.getFlowPT().getColumn(t))) {
				list.add(t);
			}
		}
		return list;
	}
	
	// we just reached "state" by firing tfired
	public List<Integer> updateEnabled (SparseIntArray state, List<Integer> enabled, int tfired) {
		if (combFlow.getColumn(tfired).size() == 0) {
			return enabled;
		}
		
		int  [] seen = new int [sr.getTnames().size()];
		List<Integer> list = new ArrayList<>();
		for (int t : enabled) {
			if (seen[t] != 0)
				continue;
			if (conflictSet[tfired][t] == 0) {
				list.add(t);
				seen[t] = 1;
			} else {
				if (greaterOrEqual2(state, sr.getFlowPT().getColumn(t))) {
					list.add(t);
					seen[t] = 1;
				}
			}
		}

		for (int t : mayEnableSet[tfired]) {
			if (seen[t] != 0)
				continue;
			
			if (greaterOrEqual2(state, sr.getFlowPT().getColumn(t))) {
				list.add(t);
				seen[t] = 1;
			}
		}		
		
		return list;
	}
	
	public void run (int nbSteps) throws DeadlockFound {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		
		SparseIntArray state = new SparseIntArray(sr.getMarks());
		List<Integer> list = computeEnabled(state);
		
		for (int  i=0; i < nbSteps ; i++) {			
			if (list.isEmpty()) {
				System.out.println("Deadlock found at step " + i);
				throw new DeadlockFound();
			}
			int tfired = list.get(rand.nextInt(list.size()));
			SparseIntArray newstate = fire ( tfired, state);
			List<Integer> newlist = updateEnabled(newstate, list, tfired);
			/*
			{
				Set<Integer> s1 = new TreeSet<Integer>(newlist);
				Set<Integer> s2 = new TreeSet<Integer>(computeEnabled(newstate));

				if (! s1.equals(s2)) {
					System.err.println("Mismatch " + s1 + " vs. " + s2);
					System.err.println("Enabled as list " + newlist);
				}
			}
			*/
			list = newlist;
			state = newstate;
		}		
	}
	
	public SparseIntArray fire (int t, SparseIntArray state) {
		// NB no enabling check
		return SparseIntArray.sumProd(1, state, 1, combFlow.getColumn(t));
	}

	private boolean greaterOrEqual2(SparseIntArray s1, SparseIntArray s2) {
		if (s1.size() < s2.size()) {
			return false;
		}
		for (int j = 0 ; j < s2.size() ; j++) {
			int sk2 = s2.keyAt(j); 
			int sv1 = s1.get(sk2);
			if (sv1 < s2.valueAt(j)) {
				return false;
			}
		}
		return true;
	}
	
}
