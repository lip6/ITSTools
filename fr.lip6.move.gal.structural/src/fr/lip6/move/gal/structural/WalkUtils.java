package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.IntMatrixCol;

public class WalkUtils {

	private StructuralReduction sr;
	private IntMatrixCol combFlow;
	private IntMatrixCol tFlowPT;
	private int [] behaviorMap;
	private int behaviorCount;

	public IntMatrixCol getFlowPT() {
		return sr.getFlowPT();
	}


	public IntMatrixCol getFlowTP() {
		return sr.getFlowTP();
	}

	public IntMatrixCol getCombFlow() {
		return combFlow;
	}
	
	public SparseIntArray fire (int t, SparseIntArray state) {
		// NB no enabling check
		return SparseIntArray.sumProd(1, state, 1, combFlow.getColumn(t));
	}

	public WalkUtils(StructuralReduction sr) {
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
				WalkUtils.dropAt(enabled,i);
				continue;
			}
			
			if (SparseIntArray.greaterOrEqual(state, sr.getFlowPT().getColumn(t))) {					
				seen[t] = true;
				seenEffects[behaviorMap[t]] = true;
				continue;
			} else {
				WalkUtils.dropAt(enabled,i);
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
						WalkUtils.add(enabled, t);				
						seen[t] = true;
						seenEffects[behaviorMap[t]] = true;
					}
				}
			}
		}
	}

	int [] computeEnabled(SparseIntArray state) {		
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

	static void dropAt (int [] enabled, int index) {
		if (index < enabled[0]) {
			enabled[index] = enabled[enabled[0]];
		}
		enabled [0] --;
	}

	static void add (int [] enabled, int value) {
		enabled[enabled[0]+1] = value;
		enabled [0] ++;
	}


	public SparseIntArray getInitial() {
		return new SparseIntArray(sr.getMarks());
	}

	
	/** update a list of enabling to remove empty effect transitions*/ 
	void dropEmpty(int [] enabled) {
		for (int i = enabled[0] ; i  >= 1  ; i--) {
			int t = enabled [i];
			if (combFlow.getColumn(t).size() == 0) {
				WalkUtils.dropAt(enabled,i);				
			}
		}
	}

	void dropUnavailable (int [] enabled, SparseIntArray parikh) {
		for (int i = enabled[0] ; i  >= 1  ; i--) {
			int t = enabled [i];
			if (parikh.get(t) <= 0) {
				WalkUtils.dropAt(enabled,i);				
			}
		}
	}
	
}
