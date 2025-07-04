package fr.lip6.move.gal.structural.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.util.SparseIntArray;
import fr.lip6.move.gal.graph.Tarjan;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.util.IntMatrixCol;

public class PlacesInNonTrivialSCCComputer {

	public static Set<Integer> computePlacesInNonTrivialSCC(ISparsePetriNet spn) {
		IntMatrixCol graph = computeAdjacency(spn);
		Set<Integer> nonTrivialSCC = new HashSet<>();

		List<List<Integer>> sccs = Tarjan.searchForSCC(graph);
		for (List<Integer> scc : sccs) {
			if (scc.size() > 1 || graph.get(scc.get(0), scc.get(0)) == 1) {
				nonTrivialSCC.addAll(scc);
			}
		}
		return nonTrivialSCC;
	}

	private static IntMatrixCol computeAdjacency(ISparsePetriNet graph) {
		IntMatrixCol adj = new IntMatrixCol(graph.getPlaceCount(), graph.getPlaceCount());
		IntMatrixCol flowPT = graph.getFlowPT();
		IntMatrixCol flowTP = graph.getFlowTP();
		for (int tid = 0; tid < flowPT.getColumnCount(); tid++) {
			SparseIntArray hPT = flowPT.getColumn(tid);
			SparseIntArray hTP = flowTP.getColumn(tid);
			for (int i = 0; i < hPT.size(); i++) {
				for (int j = 0; j < hTP.size(); j++) {
					// (destination, source)
					adj.set(hTP.keyAt(j), hPT.keyAt(i), 1);
				}
			}
		}
		return adj;
	}


}
