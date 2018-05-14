package fr.lip6.move.gal.structural;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import android.util.SparseIntArray;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.util.MatrixCol;

public class GraphBuilder {

	
	public static Graph buildGraph (MatrixCol flowPT, MatrixCol flowTP, List<String> pnames, List<String> tnames, List<Integer> initial) {
		Graph g = new Graph();

		for (int tindex =0; tindex < tnames.size() ; tindex++) {
			SparseIntArray flow = SparseIntArray.sumProd(1, flowPT.getColumn(tindex), 1, flowTP.getColumn(tindex));
			int nbTouched = flow.size();
			int nbElts = nbTouched * (nbTouched -1) * 2;
			if (nbElts != 0) {
				for (int i = 0 ; i < nbTouched ; i++) {
					for (int j=i+1 ; j < nbTouched ; j++) {
						g.add(new Edge(flow.keyAt(i), flow.keyAt(j), 1.0/nbElts));
					}
				}
			}
		}		
		return g;
	}
	
	public static void writeGraph (String path, StructuralReduction sr) throws FileNotFoundException {
		Graph g = buildGraph(sr.getFlowPT(), sr.getFlowTP(), sr.getPnames(), sr.getTnames(), sr.getMarks());
		
		PrintWriter pw = new PrintWriter(path);
		for (Edge e : g) {
			pw.println(e.getSrc()+" "+e.getDest()+" "+((float)e.getWeight()));
		}
		pw.close();		
	}
	
	public static IOrder computeLouvain(StructuralReduction sr) throws IOException {
		File ff = File.createTempFile("graph", "txt");
		
		writeGraph(ff.getCanonicalPath(), sr);
		
		Runner.
		
		
	}
}
