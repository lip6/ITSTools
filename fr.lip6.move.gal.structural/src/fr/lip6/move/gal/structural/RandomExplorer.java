package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.MatrixCol;

public class RandomExplorer {

	private StructuralReduction sr;
	private MatrixCol combFlow;
	

	public RandomExplorer(StructuralReduction sr) {
		this.sr = sr;
		
		combFlow = new MatrixCol(sr.getPnames().size(),0);
		for (int i = 0 ;  i < sr.getFlowPT().getColumnCount() ; i ++) {
			combFlow.appendColumn(SparseIntArray.sumProd(-1, sr.getFlowPT().getColumn(i), 1, sr.getFlowTP().getColumn(i)));
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
	
	void run (int nbSteps) throws DeadlockFound {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		
		SparseIntArray state = new SparseIntArray(sr.getMarks());
		
		
		for (int  i=0; i < nbSteps ; i++) {
			List<Integer> list = computeEnabled(state);
			if (list.isEmpty()) {
				System.out.println("Deadlock found at step " + i);
				throw new DeadlockFound();
			}
			state = fire ( list.get(rand.nextInt(list.size())), state);			
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
