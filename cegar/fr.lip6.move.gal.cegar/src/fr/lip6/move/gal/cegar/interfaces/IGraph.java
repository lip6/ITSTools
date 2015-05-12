package fr.lip6.move.gal.cegar.interfaces;

import java.util.List;
import java.util.Set;

public interface IGraph<T> {
	public void addEdge(T v1, T v2);

	public Set<T> getConnectedComponent(T v, int depth);
	
	public void setEdges(List<List<T>> edges);
}
