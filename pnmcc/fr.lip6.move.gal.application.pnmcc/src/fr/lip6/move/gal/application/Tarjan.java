package fr.lip6.move.gal.application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.util.IntMatrixCol;

class Tarjan {

	private static int n, pre, count = 0;
	private static int[] id, low;
	private static boolean[] marked;
	private static boolean[][] adj;
	private static Stack<Integer> stack = new Stack<>();


	private  static void dfs(int u) {
		marked[u] = true;
		low[u] = pre++;
		int min = low[u];
		stack.push(u);
		for (int v = 0; v < n; v++) {
			if (adj[u][v]) {
				if (!marked[v])
					dfs(v);
				if (low[v] < min)
					min = low[v];
			}
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

	public static List<Integer> parsePetriNet(ISparsePetriNet graph) {
		n  = graph.getPnames().size();
		adj = new boolean[n][n];
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
					adj[hPT.keyAt(i)][hTP.keyAt(j)] = true;
				}

			}
		}

		for (int u = 0; u < n; u++)
			if (!marked[u])
				dfs(u);

		System.out.println(Arrays.toString(id));
		System.out.println("Totale scc " + count);
		return Arrays.stream(id).boxed().collect(Collectors.toList());

	}

}