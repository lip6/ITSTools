package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.MatrixCol;

public class RandomExplorer {

	private StructuralReduction sr;
	private MatrixCol combFlow;
	private List<Set<Integer>> conflictSet = new ArrayList<>();
	private List<Set<Integer>> mayEnableSet = new ArrayList<>();

	public RandomExplorer(StructuralReduction sr) {
		this.sr = sr;
		
		combFlow = new MatrixCol(sr.getPnames().size(),0);
		for (int i = 0 ;  i < sr.getFlowPT().getColumnCount() ; i ++) {
			combFlow.appendColumn(SparseIntArray.sumProd(-1, sr.getFlowPT().getColumn(i), 1, sr.getFlowTP().getColumn(i)));
		}
		
		for (int  t=0 ; t < sr.getTnames().size() ; t++) {
			conflictSet.add(new HashSet<>());
			mayEnableSet.add(new HashSet<>());
		}
		
		MatrixCol tFlowPT = sr.getFlowPT().transpose();
		for (int  p = 0 ; p < tFlowPT.getColumnCount() ; p++) {
			SparseIntArray col = tFlowPT.getColumn(p);
			// every transition in this set is in conflict with every other one
			for (int i = 0 ; i < col.size() ; i++) {
				for (int j = i ; j < col.size() ; j++) {
					int ki = col.keyAt(i);
					int kj = col.keyAt(j);
					conflictSet.get(ki).add(kj);
					conflictSet.get(kj).add(ki);
				}	
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
					mayEnableSet.get(kj).add(ki);
				}	
			}
		}
		
	}

	public List<Integer> computeEnabled(SparseIntArray state) {
		List<Integer> list = new ArrayList<>();
		for (int t = 0, e =  sr.getTnames().size(); t < e; t++) {
			if (greaterOrEqual(state, sr.getFlowPT().getColumn(t))) {
				list.add(t);
			}
		}
		return list;
	}
	
	// we just reached "state" by firing tfired
	public List<Integer> updateEnabled (SparseIntArray state, List<Integer> enabled, int tfired) {
		List<Integer> list = new ArrayList<>();
		HashSet<Integer> seen = new HashSet<>();
		for (int t : enabled) {
			if (! conflictSet.get(tfired).contains(t)) {
				list.add(t);
				seen.add(t);
			} else {
				if (greaterOrEqual(state, sr.getFlowPT().getColumn(t))) {
					list.add(t);
				}
				seen.add(t);
			}
		}

		for (int t : mayEnableSet.get(tfired)) {
			if (! seen.contains(t)) {
				if (greaterOrEqual(state, sr.getFlowPT().getColumn(t))) {
					list.add(t);				
				}
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
			state = fire ( tfired, state);
			updateEnabled(state, list, tfired);
		}
		
	}
	
	public SparseIntArray fire (int t, SparseIntArray state) {
		// NB no enabling check
		return SparseIntArray.sumProd(1, state, 1, combFlow.getColumn(t));
	}

	private boolean greaterOrEqual(SparseIntArray s1, SparseIntArray s2) {
		int j = 0;
		for (int i = 0 ; i < s1.size() && j < s2.size() ; ) {
			int sk1 = s1.keyAt(i);
			int sk2 = s2.keyAt(j); 
			
			if (sk1 < sk2) {
				// element in s1 not in s2, ok
				i++;
			} else if (sk1 > sk2) {
				return false;
			} else {
				// equal keys
				if (s1.valueAt(i) < s2.valueAt(j)) {
					return false;
				}
				i++;
				j++;
			}				
		}
		if (j < s2.size()) {
			return false;
		}
		return true;
	}
	
	
}
