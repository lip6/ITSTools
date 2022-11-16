package fr.lip6.move.gal.structural.tar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutomataState {
	private List<AutomataEdge> edges=new ArrayList<>();
	private boolean accept = false;
	private List<Integer> simulates = new ArrayList<>();
	private List<Integer> simulators = new ArrayList<>();
	private PlaceRangeVector interpolant;

	public AutomataState(PlaceRangeVector interpolant) {
		this.interpolant = interpolant;
	}

	public boolean isAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}

	public boolean hasEdge (int from, int to) {
		int index = Collections.binarySearch(edges, new AutomataEdge(from));
		if (index < 0) {
			return false;
		} else {
			return edges.get(index).hasTo(to);
		}
	}
	public boolean addEdge (int from, int to) {
		AutomataEdge edge = new AutomataEdge(from);
		int index = Collections.binarySearch(edges, edge);
		if (index < 0) {
			edges.add(-(index+1),edge);
		} else {
			edge = edges.get(index);
		}
		return edge.addTo(to);
	}

	public boolean removeEdge (int from, int to) {
		AutomataEdge edge = new AutomataEdge(from);
		int index = Collections.binarySearch(edges, edge);
		if (index < 0) {
			return false;
		} else {
			return edges.get(index).remove(to);
		}
	}
	
	public boolean removeEdge(int from) {
		AutomataEdge edge = new AutomataEdge(from);
		int index = Collections.binarySearch(edges, edge);
		if (index < 0) {
			return false;
		} else {
			edges.remove(index);
			return true;
		}
	}

	
	public List<AutomataEdge> getEdges() {
		return edges;
	}

	@Override
	public String toString() {
		return "AutomataState [interpolant=" + interpolant + ", simulates=" + simulates + ", simulators=" + simulators
				+ ", edges=" + edges + "]";
	}

	public PlaceRangeVector getInterpolant() {
		return interpolant;
	}

	public List<Integer> getSimulates() {
		return simulates;
	}

	public List<Integer> getSimulators() {
		return simulators;
	}

	public int firstEdgeIndex(int e)
    {
        AutomataEdge edge = new AutomataEdge(e);
        int lb = Collections.binarySearch(edges, edge);
        return lb;
    }

	
}
