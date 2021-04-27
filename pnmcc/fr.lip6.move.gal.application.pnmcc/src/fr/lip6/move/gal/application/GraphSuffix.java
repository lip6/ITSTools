package fr.lip6.move.gal.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import android.util.SparseIntArray;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.util.IntMatrixCol;

public class GraphSuffix {

	public static boolean[] computeNonStablePlaces(SparsePetriNet spn, DoneProperties doneProps) {
		boolean[] nonstable = new boolean[spn.getPlaceCount()];
		long time = System.currentTimeMillis();
		// extract simple transitions to a PxP matrix
		int nbP = spn.getPlaceCount();
		IntMatrixCol graph = new IntMatrixCol(nbP, nbP);

		IntMatrixCol flowPT = spn.getFlowPT();
		IntMatrixCol flowTP = spn.getFlowTP();

		// 1. graphe v2
		for (int tid = 0; tid < flowPT.getColumnCount(); tid++) {
			SparseIntArray hPT = flowPT.getColumn(tid);
			SparseIntArray hTP = flowTP.getColumn(tid); // flowPT(tid).keyAt(0)
			if (hPT.size() == 1 && hPT.valueAt(0) == 1 && hTP.get(hPT.keyAt(0)) == 0) {
				for (int pid = 0; pid < hTP.size(); pid++) {
					// source - dest
					graph.set(hPT.keyAt(0), hTP.keyAt(pid), 1);
				}
			}
		}
		Set<Integer> safeNodes = new HashSet<>();

		for (int pid = 0; pid < spn.getPlaceCount(); pid++) {

			// 2. initialement marquée && au moins un successeur
			if (spn.getMarks().get(pid) > 0 && graph.getColumn(pid).size() > 0) {
				safeNodes.add(pid);
			}
		}
		// 3 & 4 invocation du calcul des suffixes des safeNodes, toutes les places de safeNodes sont sont instables
		collectPrefix(safeNodes, graph, false);

		// 5. On cherche des places pas encore instable, et non marquées, sans prédecesseur.

		IntMatrixCol tgraph = graph.transpose();
		Set<Integer> unStable = new HashSet<>();

		for (int pid = 0; pid < tgraph.getColumnCount(); pid++) {
			if (tgraph.getColumn(pid).size() == 0 && spn.getMarks().get(pid) == 0 && !safeNodes.contains(pid))
				unStable.add(pid);
		}
		
		//6. Calcul des suffixes des têtes
		
		Set<Integer> unStableHead = new HashSet<>(unStable);
		
		collectPrefix(unStableHead, tgraph, false);
		
		
		//7-a Retirer au suffixe les places de têtes
		Set<Integer> result = unStableHead.stream().filter(pid->!(unStable.contains(pid))).collect(Collectors.toSet());
		
		//7-b 
		unStable.addAll(result);

		
		
		//******************************    what's next ? **************************************
		
		List<List<Integer>> sccs = Tarjan.searchForSCC(graph);
		sccs.removeIf(s -> s.size() == 1);

		int reduced = 0;
		// so we have SCC, any SCC with tokens initially in it => all places in the SCC
		// are NON STABLE
		for (List<Integer> scc : sccs) {
			boolean isMarked = false;
			for (int pid : scc) {
				if (spn.getMarks().get(pid) > 0) {
					isMarked = true;
					break;
				}
			}
			if (isMarked) {
				for (int pid : scc) {
					nonstable[pid] = true;
					reduced++;
				}
			}
		}

		if (reduced > 0) {
			System.out.println("SCC test allowed to assert that " + reduced + " places are NOT stable.");
			doneProps.put("SccTest", false, "TRIVIAL_MARKED_SCC_TEST");
		}
		return nonstable;
	}

	// méthode calcul des suffixes des safeNodes
	private static void collectPrefix(Set<Integer> safeNodes, IntMatrixCol graph, boolean transpose) {
		if (safeNodes.size() == graph.getColumnCount()) {
			return;
		}
		// work with predecessor relationship
		IntMatrixCol tgraph = graph;

		if (transpose)
			tgraph = graph.transpose();

		Set<Integer> seen = new HashSet<>();
		List<Integer> todo = new ArrayList<>(safeNodes);
		while (!todo.isEmpty()) {
			List<Integer> next = new ArrayList<>();
			seen.addAll(todo);
			for (int n : todo) {
				SparseIntArray pred = tgraph.getColumn(n);
				for (int i = 0; i < pred.size(); i++) {
					int pre = pred.keyAt(i);
					if (seen.add(pre)) {
						next.add(pre);
					}
				}
			}
			todo = next;
		}
		safeNodes.addAll(seen);
	}
}
