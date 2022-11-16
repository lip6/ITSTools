package fr.lip6.move.gal.structural.tar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import android.util.SparseIntArray;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.ISparsePetriNet;

public class TARReachabilitySearch {
	private int kbound;
	private int stepno=0;
	private ISparsePetriNet pn;
	private TraceSet traceSet;

	public TARReachabilitySearch(ISparsePetriNet pn, int kbound) {
		this.pn = pn;
		this.kbound = kbound;
	}

	public void check(DoneProperties doneProps) {

	}

	private void handleInvalidTrace(List<State> waiting, int nvalid) {
		assert(waiting.size() >= nvalid);
        waiting = waiting.subList(0, nvalid); // remove invalid part of trace

        for(int i = 1; i < waiting.size(); ++i)
        {
            boolean brk = false;
            for(int j = 0 ; j < i; ++j)
            {
                if(waiting.get(j).compareTo(waiting.get(i)) < 0)
                {
                    waiting = waiting.subList(0, i);
                    brk = true;
                    break;
                }
            }
            if(brk) break;
            if(doStep(waiting.get(i - 1), waiting.get(i).getInterpolant()))
            {
                if(i != 1) waiting = waiting.subList(0, i - 1);
                break;
            }
        }
	}

	private boolean doStep(State state, Set<Integer> nextinter) {
		if(traceSet.follow(state.getInterpolant(), nextinter, state.getEdgeCount()))
        {
            return true;
        }
		if(state.getEdgeCount() == 0)
            return false;

		addNonChanging(state, state.getInterpolant(), nextinter);

		traceSet.maximize(nextinter);

        return false;
	}

	private void addNonChanging(State state, Set<Integer> maximal, Set<Integer> nextinter) {

        List<Integer> changes = new ArrayList<>();
        SparseIntArray pre = pn.getFlowPT().getColumn(state.getEdgeCount()-1);
        SparseIntArray post = pn.getFlowTP().getColumn(state.getEdgeCount()-1);

        for (int i=0;i<pre.size();i++) {
        	changes.add(pre.keyAt(i));
        }
        for (int i=0;i<post.size();i++) {
        	changes.add(post.keyAt(i));
        }
        changes.sort(Comparator.naturalOrder());
        traceSet.copyNonChanged(maximal, changes, nextinter);
    }


}
