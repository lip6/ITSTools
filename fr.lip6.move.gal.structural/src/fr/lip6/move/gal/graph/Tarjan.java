package fr.lip6.move.gal.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

import fr.lip6.move.gal.util.IntMatrixCol;

/**
 * Tarjan algorithm based on Low-Link Values, runs in O(|V| + |E|)
 */
public class Tarjan {

	/**
	 * Non recursive version based on Ivan Stoev proposal on SO :
	 * https://stackoverflow.com/questions/46511682/non-recursive-version-of-tarjans-algorithm.
	 *
	 * @param graph
	 * @return the SCCs
	 */
	public static List<List<Integer>> searchForSCC(IntMatrixCol graph) {
		List<List<Integer>> stronglyConnectedComponents = new ArrayList<>();

		int preCount = 0;
		int[] low = new int[graph.getColumnCount()];
		boolean[] visited = new boolean[graph.getColumnCount()];
		Stack<Integer> stack = new Stack<>();

		Stack<Integer> minStack = new Stack<>();
		Stack<Integer> vStack = new Stack<>();
		Stack<Iterator<Integer>> enumeratorStack = new Stack<>();

		Iterator<Integer> enumerator = IntStream.range(0, graph.getColumnCount()).iterator();
		while (true) {
			if (enumerator.hasNext()) {
				int v = enumerator.next();
				if (!visited[v]) {
					low[v] = preCount++;
					visited[v] = true;
					stack.push(v);
					int min = low[v];
					// Level down
					minStack.push(min);
					vStack.push(v);
					enumeratorStack.push(enumerator);
					enumerator = Arrays.stream(graph.getColumn(v).copyKeys()).iterator();
				} else if (minStack.size() > 0) {
					int min = minStack.pop();
					if (low[v] < min) {
						min = low[v];
					}
					minStack.push(min);
				}
			} else {
				// Level up
				if (enumeratorStack.size() == 0) {
					break;
				}

				enumerator = enumeratorStack.pop();
				int v = vStack.pop();
				int min = minStack.pop();

				if (min < low[v]) {
					low[v] = min;
				} else {
					List<Integer> component = new ArrayList<>();

					int w;
					do {
						w = stack.pop();
						component.add(w);
						low[w] = graph.getColumnCount();
					} while (w != v);
					stronglyConnectedComponents.add(component);
				}

				if (minStack.size() > 0) {
					min = minStack.pop();
					if (low[v] < min) {
						min = low[v];
					}
					minStack.push(min);
				}
			}
		}
		return stronglyConnectedComponents;
	}

}