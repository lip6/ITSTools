package fr.lip6.move.gal.structural.tar;

import java.util.Set;
import java.util.TreeSet;

import fr.lip6.move.gal.structural.ISparsePetriNet;

public class State implements Comparable<State> {
	private int offset = 0;
	private int size = Integer.MAX_VALUE;
	private int edgeCount = 0;
	private Set<Integer> interpolant = new TreeSet<>();

	@Override
	public int compareTo(State other) {
		if (interpolant.size() < other.interpolant.size() && other.interpolant.containsAll(interpolant)) {
			return -1;
		} else if (equals(other)) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((interpolant == null) ? 0 : interpolant.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		State other = (State) obj;
		if (interpolant == null) {
			if (other.interpolant != null)
				return false;
		} else if (!interpolant.equals(other.interpolant))
			return false;
		return true;
	}
	public int getEdgeCount() {
		if (edgeCount == 0) {
			return 0;
		} else {
			return 1 + (((edgeCount-1) + offset) % size);
		}
	}
	public void setEdge(int edge) {
		edgeCount = edge;
		offset = 0;
	}
	public boolean nextEdge(ISparsePetriNet pn) {
		++edgeCount;
		return done(pn);
	}
	public boolean done(ISparsePetriNet pn) {
		return edgeCount > pn.getTransitionCount();
	}

	public Set<Integer> getInterpolant() {
		return interpolant;
	}

}
