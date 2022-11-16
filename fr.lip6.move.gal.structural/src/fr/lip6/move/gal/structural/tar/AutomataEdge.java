package fr.lip6.move.gal.structural.tar;

import java.util.HashSet;
import java.util.Set;


public class AutomataEdge implements Comparable<AutomataEdge> {
	private int edge;
	private Set<Integer> to = new HashSet<>();

	public AutomataEdge(int edge) {
		this.edge = edge;
	}

	@Override
	public int compareTo(AutomataEdge o) {
		return Integer.compare(edge, o.edge);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		AutomataEdge other = (AutomataEdge) obj;
		if (edge != other.edge)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + edge;
		return result;
	}

	public boolean hasTo(int target) {
		return to.contains(target);
	}

	public boolean remove(int target) {
		return to.remove(target);
	}

	public boolean addTo(int target) {
		return to.add(target);
	}

	@Override
	public String toString() {
		return edge + "==>" + to;
	}

	public int getEdge() {
		return edge;
	}

	public Set<Integer> getTo() {
		return to;
	}

}
