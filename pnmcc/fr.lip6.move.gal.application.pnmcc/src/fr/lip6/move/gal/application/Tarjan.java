package fr.lip6.move.gal.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

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
		adj = new IntMatrixCol(n, n);
		marked = new boolean[n];
		id = new int[n];
		low = new int[n];
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

}