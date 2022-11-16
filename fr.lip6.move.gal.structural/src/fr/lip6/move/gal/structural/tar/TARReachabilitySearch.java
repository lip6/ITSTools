package fr.lip6.move.gal.structural.tar;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import android.util.SparseIntArray;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.Property;

public class TARReachabilitySearch {
	private static final int DEBUG = 0;
	private int kbound;
	private int stepno = 0;
	private ISparsePetriNet pn;
	private TraceSet traceSet;

	public TARReachabilitySearch(ISparsePetriNet pn, int kbound) {
		this.pn = pn;
		this.kbound = kbound;
	}

	public void check(DoneProperties doneProps) {

	}

	private void handleInvalidTrace(List<State> waiting, int nvalid) {
		assert (waiting.size() >= nvalid);
		waiting = waiting.subList(0, nvalid); // remove invalid part of trace

		for (int i = 1; i < waiting.size(); ++i) {
			boolean brk = false;
			for (int j = 0; j < i; ++j) {
				if (waiting.get(j).compareTo(waiting.get(i)) < 0) {
					waiting = waiting.subList(0, i);
					brk = true;
					break;
				}
			}
			if (brk)
				break;
			if (doStep(waiting.get(i - 1), waiting.get(i).getInterpolant())) {
				if (i != 1)
					waiting = waiting.subList(0, i - 1);
				break;
			}
		}
	}

	private boolean doStep(State state, Set<Integer> nextinter) {
		if (traceSet.follow(state.getInterpolant(), nextinter, state.getEdgeCount())) {
			return true;
		}
		if (state.getEdgeCount() == 0)
			return false;

		addNonChanging(state, state.getInterpolant(), nextinter);

		traceSet.maximize(nextinter);

		return false;
	}

	private void addNonChanging(State state, Set<Integer> maximal, Set<Integer> nextinter) {

		List<Integer> changes = new ArrayList<>();
		SparseIntArray pre = pn.getFlowPT().getColumn(state.getEdgeCount() - 1);
		SparseIntArray post = pn.getFlowTP().getColumn(state.getEdgeCount() - 1);

		for (int i = 0; i < pre.size(); i++) {
			changes.add(pre.keyAt(i));
		}
		for (int i = 0; i < post.size(); i++) {
			changes.add(post.keyAt(i));
		}
		changes.sort(Comparator.naturalOrder());
		traceSet.copyNonChanged(maximal, changes, nextinter);
	}

	
	
	

	public void reachable(List<Property> queries,
			List<Optional<Boolean>> results,
			boolean printstats, boolean printtrace)
	{
		// inhibitors are not supported yet


		// set up working area
		SparseIntArray state = new SparseIntArray(pn.getMarks());

		// check initial marking
		// already done ahead of this procedure

		// Search!
		for(int i = 0; i < queries.size(); ++i)
		{
			traceSet.clear();

			if(results.get(i).isEmpty())
			{
				BitSet support = new BitSet();
				PetriNet.addSupport(queries.get(i).getBody(), support);

				Solver solver = new Solver(pn, state, queries.get(i), support);
				boolean res = tryReach(printtrace, solver);
				results.set(i, Optional.of(res));
				
				//auto ret = _printer.handle(i, queries[i].get(), results[i]);
				
				// results[i] = ret.first;
				// if(res && ret.second)
				//	return;
			}
		}

		if(printstats)
			printStats();
	}

	private void printStats() {
		// TODO Auto-generated method stub
		
	}
	
	private boolean update_use (boolean [] use_trans, SparseIntArray use_place, boolean any) {
		SparseIntArray np = use_place.clone();
		boolean update = false;
		for(int t = 0; t < pn.getTransitionCount(); ++t)
		{
			SparseIntArray pre = pn.getFlowPT().getColumn(t);
			SparseIntArray post = pn.getFlowTP().getColumn(t);
			
			if(use_trans[t+1]) continue;
			{
				if (SparseIntArray.keysIntersect(pre, np) || SparseIntArray.keysIntersect(post, np)) {
					use_trans[t+1] = true;					
				}				
			}
			if(use_trans[t+1])
			{
				update = true;
				for (int i=pre.size()-1; i>=0 ; i--) {
					np.put(i, 1);
				}
				if(any)
				{
					use_place.move(np);
					return true;
				}
			}
		}
		use_place.move(np);
		return update;
	}
	
	private static class TARResult {
		public boolean finished;
		public boolean satisfied;
		public TARResult(boolean finished, boolean satisfied) {
			this.finished = finished;
			this.satisfied = satisfied;
		}
	}

	private boolean tryReach(boolean printtrace, Solver solver) {
		traceSet.removeEdges(0);
		boolean[] use_trans = new boolean[pn.getTransitionCount()];
		BitSet support = solver.getSupport();
		SparseIntArray use_place = new SparseIntArray();
		for (int i = support.nextSetBit(0); i >= 0; i = support.nextSetBit(i + 1)) {
			use_place.append(i, 1);
			if (i == Integer.MAX_VALUE) {
				break; // or (i+1) would overflow
			}
		}
		use_trans[0] = true;
		update_use(use_trans, use_place, false);

		do {

			TARResult res = runTAR(printtrace, solver, use_trans);
			if (res.finished) {
				if (!res.satisfied) {
					if (update_use(use_trans, use_place, false))
						continue;

					if (printtrace)
						System.err.println(traceSet.toString());
				}

				return res.satisfied;
			}
		} while (true);
	}

	private TARResult runTAR(boolean printtrace, Solver solver, boolean[] use_trans) {
        long tt = System.currentTimeMillis();
        AntiChain checked = new AntiChain();
        // waiting-list with levels
        boolean all_covered = true;
        Stack<State> waiting = new Stack<>();
        // initialize
        {
            State state = new State();
            state.resetEdges(pn);
            HashSet<Integer> init = new HashSet<>(traceSet.getInitial());
            traceSet.maximize(init);
            state.setInterpolant(init);
            waiting.add(state);
        }
        
        while (!waiting.empty())
        {
            if(popDone(waiting))
                continue;  // we have reached the end of the edge-iterator for this part of the trace

            ++stepno;

            assert(waiting.size() > 0 );
            State state = waiting.peek();
            Set<Integer> nextinter = new HashSet<>();
            if(!use_trans[state.getEdgeCount()])
            {
                state.nextEdge(pn);
                continue;
            }
            if(doStep(state, nextinter)) // Check if the next state makes the interpolant automata accept.
            {
                state.nextEdge(pn);
                continue;
            }

            if(waiting.peek().getEdgeCount() == 0) // check if proposition is satisfied
            {
                boolean satisfied = solver.check(waiting, traceSet);
                
                if(satisfied)
                {
                    if(printtrace)
                        printTrace(waiting);
                    return new TARResult(true, true);
                }
                else
                {
                    return new TARResult(false, false);
                }
            }
            else
            {
            	if (DEBUG >=1)
            		printStats();
                nextEdge(checked, state, waiting, nextinter);
            }
        }
        return new TARResult(all_covered, false);
	}

	private void nextEdge(AntiChain checked, State state, Stack<State> waiting, Set<Integer> nextinter) {
		
	}

	private void printTrace(Stack<State> waiting) {
		// TODO Auto-generated method stub
		
	}

	private boolean popDone(Stack<State> waiting) {
        boolean popped = false;
        while(waiting.get(waiting.size()-1).done(pn)) // we have tried all transitions for this state-pair!
        {
            assert(waiting.size() > 0);            
            waiting.pop();
            popped = true;
            if(waiting.size() > 0)
            {
                // count up parents edge counter!
                waiting.peek().nextEdge(pn);
            }
            if(waiting.size() == 0) break;
        }
        return popped;
	}
}