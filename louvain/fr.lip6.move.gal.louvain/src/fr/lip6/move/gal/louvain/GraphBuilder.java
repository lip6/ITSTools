package fr.lip6.move.gal.louvain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeoutException;

import android.util.SparseIntArray;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.order.OrderFactory;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.gal.structural.StructuralReduction;
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
	
	public static IOrder computeLouvain(StructuralReduction sr) throws IOException, TimeoutException, InterruptedException {
		File ff = File.createTempFile("graph", ".txt");
		
		String folder = "/data/ythierry/louvain/louvain-generic/";
		
		writeGraph(ff.getCanonicalPath(), sr);
		
		CommandLine clConvert = new CommandLine();
		clConvert.addArg(folder+"convert");
		clConvert.addArg("-i");
		clConvert.addArg(ff.getCanonicalPath());
		
		clConvert.addArg("-o");
		String fbin = ff.getCanonicalPath().replace(".txt", ".bin");
		clConvert.addArg(fbin);
		
		clConvert.addArg("-w");
		String fw = ff.getCanonicalPath().replace(".txt", ".weights");
		clConvert.addArg(fw);
		
		int exit = Runner.runTool(10, clConvert );
		System.out.println("Converted graph to binary with : " + clConvert);

		CommandLine cl = new CommandLine();
		cl.addArg(folder+"louvain");
		cl.addArg(fbin);
		
		// -l k    displays the graph of level k rather than the hierachical structure
        // if k=-1 then displays the hierarchical structure rather than the graph at a given level
		cl.addArg("-l");
		cl.addArg("-1");
		
		// -v      verbose mode: gives computation time, information about the hierarchy and quality
		cl.addArg("-v");
		
		// -w file read the graph as a weighted one (weights are set to 1 otherwise)
		cl.addArg("-w");
		cl.addArg(fw);
		
//		-q id   the quality function used to compute partition of the graph (modularity is chosen by default):
//
//	        id = 0   -> the classical Newman-Girvan criterion (also called "Modularity")
//	        id = 1   -> the Zahn-Condorcet criterion
//	        id = 2   -> the Owsinski-Zadrozny criterion (you should specify the value of the parameter with option -c)
//	        id = 3   -> the Goldberg Density criterion
//	        id = 4   -> the A-weighted Condorcet criterion
//	        id = 5   -> the Deviation to Indetermination criterion
//	        id = 6   -> the Deviation to Uniformity criterion
//	        id = 7   -> the Profile Difference criterion
//	        id = 8   -> the Shi-Malik criterion (you should specify the value of kappa_min with option -k)
//	        id = 9   -> the Balanced Modularity criterion
		cl.addArg("-q");
		cl.addArg("0");
		
		String ftree = ff.getCanonicalPath().replace(".txt", ".tree");
		int exit2 = Runner.runTool(10, cl, new File(ftree), false);

		System.out.println("Built communities with : " + cl);

		IOrder ord = OrderFactory.parseLouvain(ftree, sr.getPnames(),false);

		return ord;
	}
}
