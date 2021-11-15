package fr.lip6.move.gal.graph;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

import android.util.SparseIntArray;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.util.IntMatrixCol;

public class GraphSuffix {

	public static boolean[] computeNonStablePlaces(SparsePetriNet spn, DoneProperties doneProps) {

		int reduced = 0;
		long time = System.currentTimeMillis();

		// Etape 1 : réduction scc triviales non marquées
		reduced = reduceTrivialScc(spn, doneProps);

		boolean[] nonstable = new boolean[spn.getPlaceCount()];

		// extract simple transitions to a PxP matrix
		IntMatrixCol graph = buildFreeTokenGraph(spn);

		reduced += reduceMarkedSuffix(spn, graph, nonstable, doneProps);

		// On cherche des places pas encore instable, et non marquées, sans
		// prédecesseur.
		reduced += reduceUnmarkedSuffix(spn, graph, nonstable, doneProps);
		if (reduced > 0) {
			System.out.println("Structural test allowed to assert that " + reduced + " places are NOT stable. Took "+(System.currentTimeMillis() - time)+" ms.");
		}

		return nonstable;
	}

	private static int reduceUnmarkedSuffix(SparsePetriNet spn, IntMatrixCol graph, boolean[] nonstable,
			DoneProperties doneProps) {
		int reduced = 0;
		IntMatrixCol tgraph = graph.transpose();
		Set<Integer> heads = new HashSet<>();
		for (int pid = 0; pid < tgraph.getColumnCount(); pid++) {
			if (tgraph.getColumn(pid).size() == 0 && spn.getMarks().get(pid) == 0 && !nonstable[pid])
				heads.add(pid);
		}

		// 6. Calcul des suffixes des têtes
		Set<Integer> unStableSuffix = new HashSet<>(heads);

		StructuralReduction.collectPrefix(unStableSuffix, tgraph, false);

		// 7-a Retirer au suffixe les places de têtes
		unStableSuffix.removeAll(heads);

		// 7-b
		for (int i : unStableSuffix) {
			nonstable[i] = true;
			reduced++;
		}
		if (!unStableSuffix.isEmpty())
			doneProps.put("unMarkedSuffixTest", false, "UNMARKED_SUFFIX_TEST");
		return reduced;
	}

	private static int reduceMarkedSuffix(SparsePetriNet spn, IntMatrixCol graph, boolean[] nonstable,
			DoneProperties doneProps) {
		int reduced = 0;
		Set<Integer> safeNodes = new HashSet<>();
		for (int pid = 0; pid < spn.getPlaceCount(); pid++) {

			// 2. initialement marquée && au moins un successeur
			if (spn.getMarks().get(pid) > 0 && graph.getColumn(pid).size() > 0) {
				safeNodes.add(pid);
			}
		}
		// 3 & 4 invocation du calcul des suffixes des safeNodes, toutes les places de
		// safeNodes sont sont instables
		StructuralReduction.collectPrefix(safeNodes, graph, false);

		// every node in safeNodes is unstable
		for (int i : safeNodes) {
			nonstable[i] = true;
			reduced++;
		}
		if (!safeNodes.isEmpty())
			doneProps.put("markedSuffixTest", false, "MARKED_SUFFIX_TEST");
		return reduced;
	}

	private static IntMatrixCol buildFreeTokenGraph(SparsePetriNet spn) {
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
					// dest - source
					graph.set(hTP.keyAt(pid), hPT.keyAt(0), 1);
				}
			}
		}
		return graph;
	}

	private static int reduceTrivialScc(SparsePetriNet spn, DoneProperties doneProps) {

		int reduced = 0;
		StructuralReduction sr = new StructuralReduction(spn);
		BitSet bs = new BitSet();

		for (int pid = 0; pid < spn.getPlaceCount(); pid++) {
			if (spn.getMarks().get(pid) > 0)
				bs.set(pid);
		}
		sr.setProtected(bs);

		if (sr.findFreeSCC(ReductionType.SAFETY)) {
			doneProps.put("unMarkedSccTest", false, "TRIVIAL_UNMARKED_SCC_TEST");
			reduced += spn.getPlaceCount() - sr.getPlaceCount();
			spn.readFrom(sr);
		}

		return reduced;
	}

}
