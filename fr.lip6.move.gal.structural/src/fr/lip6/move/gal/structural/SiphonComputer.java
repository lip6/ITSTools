package fr.lip6.move.gal.structural;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.IntMatrixCol;

public class SiphonComputer {
	// computes a list of integers corresponding to a subset of places, which form an initially empty syphon.
	// the empty set => there are no initially unmarked syphons
	public static Set<Integer> computeEmptySyphon (IntMatrixCol flowPT, IntMatrixCol flowTP, List<Integer> marks) {
		long time = System.currentTimeMillis();
		Set<Integer> keepP = new HashSet<>();
		Set<Integer> keepT = new HashSet<>();
		for (int i=0,ie=marks.size(); i < ie ; i++) {
			if (marks.get(i) == 0)
				keepP.add(i);
		}
		for (int i=0,ie=flowTP.getColumnCount(); i < ie ; i++) {
			if (flowTP.getColumn(i).size()>0)
					keepT.add(i);
		}
		// iterate reduction of unfeasible parts
		{
			int doneIter =0;
			do {
				doneIter =0;

				for (int tid=flowPT.getColumnCount() ; tid >= 0 ; tid --) {
					if (! keepT.contains(tid)) {
						continue;
					}
					boolean feeds = false;
					{
						SparseIntArray tp = flowTP.getColumn(tid);
						for (int i=0, ie= tp.size() ; i < ie ; i++) {
							if (keepP.contains(tp.keyAt(i))) {
								feeds = true;
								break;
							}
						}
					}
					if (! feeds) {
						// discard this transition, it cannot feed anybody
						keepT.remove(tid);
						doneIter++;
					} else {
						SparseIntArray pt = flowPT.getColumn(tid);
						boolean eats = false;
						for (int i=0, ie= pt.size() ; i < ie ; i++) {
							if (keepP.contains(pt.keyAt(i))) {
								eats = true;
								break;
							}
						}
						if (! eats ) {
							SparseIntArray tp = flowTP.getColumn(tid);
							// discard the transition, but also it's whole post set
							for (int i=0, e = tp.size() ; i < e ; i++) {
								keepP.remove(tp.keyAt(i));							
							}
							doneIter++;
							keepT.remove(tid);
						}
					}
				}
			} while (doneIter >0);
		}
		//Logger.getLogger("fr.lip6.move.gal").info("Computed a system of "+sr.getPnames().size()+"/"+ srori.getPnames().size() + " places and "+sr.getTnames().size()+"/"+ srori.getTnames().size() + " transitions for Syphon test. " + (System.currentTimeMillis()-time) +" ms");
		if (keepP.isEmpty()) {
			// fail
			return new HashSet<>();
		} else {
			// okay so we have a siphon here
			System.out.println("Deduced a syphon composed of "+keepP.size()+" places in "+ (System.currentTimeMillis()-time) +" ms");
			return keepP;
		}
	}

}
