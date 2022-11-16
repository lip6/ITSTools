package fr.lip6.move.gal.structural.tar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.tar.Range.RangeComparison;

public class TraceSet {
	private ISparsePetriNet pn;
	private Map<PlaceRangeVector, Integer> indexMap;
	private List<AutomataState> states;
	private Set<Integer> initial;




	public TraceSet(ISparsePetriNet pn) {
		this.pn = pn;
		init();
	}

	public void init() {
		PlaceRangeVector trueRange = new PlaceRangeVector();
		PlaceRangeVector falseRange = new PlaceRangeVector();
		for (int pid=0,pide=pn.getPlaceCount(); pid<pide; ++pid) {
			falseRange.findOrAdd(pid).getRange().restrictTo(0);
		}
		states.add(new AutomataState(falseRange));
		states.add(new AutomataState(trueRange));
		computeSimulation(0);


	}

	private void computeSimulation(int index) {
		AutomataState state = states.get(index);
        assert(index == states.size() - 1 || index == 0);
        for (int i = 0; i < states.size(); ++i) {
            if (i == index) continue;
            AutomataState other = states.get(i);

            RangeComparison res = other.getInterpolant().compare(state.getInterpolant());
            assert(!res.includes || !res.included);
            if (res.includes) {
                state.getSimulates().add(i);
                int pos = Collections.binarySearch(other.getSimulators(),index);
                if (pos < 0) {
                	other.getSimulators().add(-(pos+1), index);
                }
            }
            if (res.included) {
                state.getSimulators().add(i);
                int pos = Collections.binarySearch(other.getSimulates(),index);
                if (pos < 0) {
                	other.getSimulates().add(-(pos+1), index);
                }
            }
        }
	}

	public boolean follow(Set<Integer> from, Set<Integer> nextinter, int symbol) {
        nextinter.add(1);
        for (Integer ii : from) {
        	int i=ii;
            if (i == 0) {
                assert(false);
                continue;
            }
            AutomataState as = states.get(i);
            if (as.isAccept()) {
                assert(false);
                break;
            }

            int it = as.firstEdgeIndex(symbol);

            while (it > 0 && it < as.getEdges().size()) {
                AutomataEdge ae = as.getEdges().get(it);
            	if (ae.getEdge() != symbol) {
                    break;
                }
                ++it;
                assert(ae.getTo().size() > 0);
                if (ae.getTo().contains(0))
                    return true;
                nextinter.addAll(ae.getTo());
            }
        }
        return false;
	}

	public void copyNonChanged(Set<Integer> from, List<Integer> modifiers, Set<Integer> to) {
		for (Integer p : from)
            if (!states.get(p).getInterpolant().restricts(modifiers))
                to.add(p);
	}

	public void maximize(Set<Integer> org) {
		for (Integer i : new ArrayList<>(org)) {
			org.addAll(states.get(i).getSimulates());
		}
		org.add(1);
	}

}
