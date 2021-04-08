package fr.lip6.move.gal.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.IntStream;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.util.IntMatrixCol;

class Tarjan {

	/**
	 * Tarjan algorithm based on Low-Link Values, runs in O(|V| + |E|)
	 */

	private static int n, pre, count = 0;
	private static int[] id, low;
	private static boolean[] marked;
	// change to a spare data structure
	private static IntMatrixCol adj;

	private static Stack<Integer> stack = new Stack<>();

	private static void dfs(int u) {
		marked[u] = true;
		low[u] = pre++;
		int min = low[u];
		stack.push(u);
		for (int i = 0; i < adj.getColumn(u).size(); i++) {
			int v = adj.getColumn(u).keyAt(i);

			if (!marked[v])
				dfs(v);
			if (low[v] < min)
				min = low[v];
		}
		if (min < low[u]) {
			low[u] = min;
			return;
		}
		int v;
		do {
			v = stack.pop();
			id[v] = count;
			low[v] = n;
		} while (v != u);
		count++;
	}

	// Returns the id array with the strongly connected components.
	// If id[i] == id[j] then nodes i and j are part of the same strongly connected
	// component.
	public int[] getStronglyConnectedComponents() {
		return id.clone();
	}

	// Returns the number of strongly connected components in this graph
	public int countStronglyConnectedComponents() {
		return count;
	}

	public static Set<Integer> parsePetriNet(ISparsePetriNet graph) {
		n = graph.getPnames().size();
		marked = new boolean[n];
		id = new int[n];
		low = new int[n];
		adj = computeAdjacency(graph);

		for (int u = 0; u < n; u++)
			if (!marked[u])
				dfs(u);
		
		Map<Integer, List<Integer>> sccs = new HashMap<>();
		for(int pid = 0; pid < id.length; pid++) {
			sccs.computeIfAbsent(id[pid], k -> new ArrayList<>()).add(pid); 
			
		}
		Set<Integer> nonTrivialSCC = new HashSet<>();
		
		for(List<Integer> scc : sccs.values()) {
			if(scc.size() > 1 || adj.get(scc.get(0), scc.get(0)) == 1) {
				nonTrivialSCC.addAll(scc);
			}
		}
		return nonTrivialSCC;

	}

	public static Set<Integer> computePlacesInNonTrivialSCC (ISparsePetriNet spn) {
		IntMatrixCol graph = computeAdjacency(spn);
		Set<Integer> nonTrivialSCC = new HashSet<>();
		
		List<List<Integer>> sccs = searchForSCC(graph);
		for(List<Integer> scc : sccs) {
			if(scc.size() > 1 || adj.get(scc.get(0), scc.get(0)) == 1) {
				nonTrivialSCC.addAll(scc);
			}
		}
		return nonTrivialSCC;
	}
	
	private static IntMatrixCol computeAdjacency(ISparsePetriNet graph) {
		IntMatrixCol adj = new IntMatrixCol(n, n);
		IntMatrixCol flowPT = graph.getFlowPT();
		IntMatrixCol flowTP = graph.getFlowTP();
		for (int tid = 0; tid < flowPT.getColumnCount(); tid++) {
			SparseIntArray hPT = flowPT.getColumn(tid);
			SparseIntArray hTP = flowTP.getColumn(tid);
			for (int i = 0; i < hPT.size(); i++) {
				for (int j = 0; j < hTP.size(); j++) {
					// (destination, source)
					adj.set(hTP.keyAt(j), hPT.keyAt(i), 1);
				}
			}
		}
		return adj;
	}
	
	
	
	
	/**
	 * Non recursive version based on Ivan Stoev proposal on SO :
	 * https://stackoverflow.com/questions/46511682/non-recursive-version-of-tarjans-algorithm.
	 * 
	 * @param graph
	 * @return the SCCs
	 */
	public static List<List<Integer>> searchForSCC(IntMatrixCol graph)
	{
		List<List<Integer>> stronglyConnectedComponents = new ArrayList<>();

	    int preCount = 0;
	    var low = new int[graph.getColumnCount()];
	    var visited = new boolean[graph.getColumnCount()];
	    var stack = new Stack<Integer>();

	    var minStack = new Stack<Integer>();
	    var enumeratorStack = new Stack<Iterator<Integer>>();
	    
	    Iterator<Integer> enumerator = IntStream.range(0, graph.getColumnCount()).iterator();
	    while (true)
	    {
	        if (enumerator.hasNext())
	        {
	            int v = enumerator.next();
	            if (!visited[v])
	            {
	                low[v] = preCount++;
	                visited[v] = true;
	                stack.push(v);
	                int min = low[v];
	                // Level down
	                minStack.push(min);
	                enumeratorStack.push(enumerator);
	                enumerator = Arrays.stream(graph.getColumn(v).copyKeys()).iterator(); 
	            }
	            else if (minStack.size() > 0)
	            {
	                int min = minStack.pop();
	                if (low[v] < min) min = low[v];
	                minStack.push(min);
	            }
	        }
	        else
	        {
	            // Level up
	            if (enumeratorStack.size() == 0) break;

	            enumerator = enumeratorStack.pop();
	            int v = enumerator.next();
	            int min = minStack.pop();

	            if (min < low[v])
	            {
	                low[v] = min;
	            }
	            else
	            {
	                List<Integer> component = new ArrayList<>();

	                int w;
	                do
	                {
	                    w = stack.pop();
	                    component.add(w);
	                    low[w] = graph.getColumnCount();
	                } while (w != v);
	                stronglyConnectedComponents.add(component);
	            }

	            if (minStack.size() > 0)
	            {
	                min = minStack.pop();
	                if (low[v] < min) min = low[v];
	                minStack.push(min);
	            }
	        }
	    }
	    return stronglyConnectedComponents;
	}

}