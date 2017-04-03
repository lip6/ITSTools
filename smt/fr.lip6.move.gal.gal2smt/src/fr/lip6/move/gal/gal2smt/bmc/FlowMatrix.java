package fr.lip6.move.gal.gal2smt.bmc;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;


public class FlowMatrix {
	// To represent the flow matrix, if we can build it. We use a sparse representation.
	// Map variable index -> Transition index -> update to variable (a relative integer)
	private Map<Integer, Map<Integer,Integer>> flow = new TreeMap<>();

	public void addEffect(int tindex, int vindex, int val) {
		Map<Integer, Integer> line = flow.get(vindex);
		if (line == null) {
			line  = new TreeMap<>();
			flow.put(vindex, line);
		}
		Integer cur = line.get(tindex);		
		if (cur==null) {
			cur=0;
		}
		cur+=val;
		line.put(tindex, cur);
	}

	public Set<Entry<Integer, Map<Integer, Integer>>> entrySet() {
		return flow.entrySet();
	}
	
}
