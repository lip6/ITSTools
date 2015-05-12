package fr.lip6.move.gal.cegar.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.lip6.move.gal.cegar.interfaces.IGraph;

/**
 * A class to represent connexity Graph.
 * 
 * @author Anonymous
 *
 */
public class Graph<T> implements IGraph<T> {

	private final HashMap<T, Set<T>> graph;

	public Graph() {
		this.graph = new HashMap<T, Set<T>>();
	}

	@Override
	public void addEdge(T v1, T v2) {
		if (!this.graph.containsKey(v1))
			this.graph.put(v1, new HashSet<T>());
		if (!this.graph.containsKey(v2))
			this.graph.put(v2, new HashSet<T>());

		this.graph.get(v1).add(v2);
		this.graph.get(v2).add(v1);
	}

	@Override
	public Set<T> getConnectedComponent(T vertex, int depth) {
		Set<T> neightbors = new HashSet<T>();
		
		if(depth == 0)
			neightbors.add(vertex);
		
		else {
			neightbors.add(vertex);
			for(T neighbor : this.graph.get(vertex))
				neightbors.addAll(getConnectedComponent(neighbor, depth - 1));
		}
		
		return neightbors;
	}

	@Override
	public void setEdges(List<List<T>> edges) {
		for (List<T> edge : edges) {
			for (T vertex : edge) {

				Set<T> neighbors = new HashSet<T>(edge);
				neighbors.remove(vertex);

				if (!this.graph.containsKey(vertex))
					this.graph.put(vertex, neighbors);
				else
					this.graph.get(vertex).addAll(neighbors);
			}
		}
	}
}
