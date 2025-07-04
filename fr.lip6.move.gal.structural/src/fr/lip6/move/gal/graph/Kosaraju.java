package fr.lip6.move.gal.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.IntMatrixCol;

public class Kosaraju {

	public static List<List<Integer>> searchForSCC(IntMatrixCol graph) {
		// This part implements Kosaraju to find SCC
		// not the best time complexity algo for that, but enough for us.
		Stack<Integer> stack = new Stack<>();
		Set<Integer> visited = new HashSet<>();

		// derecursed version uses a todo stack
		Stack<Integer> todo = new Stack<>();
		for (int p = 0; p < graph.getColumnCount(); p++) {
			todo.add(p);
		}
		while (!todo.isEmpty()) {
			int p = todo.pop();
			if (p == -1) {
				stack.push(todo.pop());
				continue;
			}
			SparseIntArray col = graph.getColumn(p);
			if (col.size() > 0) {
				if (!visited.add(p)) {
					continue;
				}
				todo.push(p);
				todo.push(-1);
				for (int i = 0; i < col.size(); i++) {
					todo.push(col.keyAt(i));
				}
			}
		}

		List<List<Integer>> sccs = new ArrayList<>();
		List<Integer> curScc = new ArrayList<>();
		visited.clear();
		graph = graph.transpose();
		while (!stack.isEmpty()) {
			int cur = stack.pop();
			visitNode(graph, curScc, cur, visited);
			if (!curScc.isEmpty()) {
				sccs.add(curScc);
				curScc = new ArrayList<>();
			}
		}
		return sccs;
	}

	private static void visitNode(IntMatrixCol graph, List<Integer> curScc, int cur, Set<Integer> visited) {
		if (visited.add(cur)) {
			curScc.add(cur);
			SparseIntArray col = graph.getColumn(cur);
			for (int i = 0; i < col.size(); i++) {
				visitNode(graph, curScc, col.keyAt(i), visited);
			}
		}
	}
	
}
