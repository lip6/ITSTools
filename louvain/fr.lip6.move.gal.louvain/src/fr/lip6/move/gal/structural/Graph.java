package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Graph implements Iterable<Edge>{

	private List<Edge> edges = new ArrayList<>();

	public void forEach(Consumer<? super Edge> action) {
		edges.forEach(action);
	}

	public Iterator<Edge> iterator() {
		return edges.iterator();
	}

	public boolean add(Edge e) {
		return edges.add(e);
	}
	
	
	
}
