package fr.lip6.move.gal.structural;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.MatrixCol;

public class FlowDimacsPrinter {

	public static void drawNet (StructuralReduction sr) {
		drawNet(sr.getFlowPT(),sr.getFlowTP(),sr.getMarks(),sr.getPnames(),sr.getTnames());
	}
	
	public static void drawNet (MatrixCol flowPT, MatrixCol flowTP, List<Integer> marks, List<String> pnames, List<String> tnames) {
		try {
			Path out = Files.createTempFile("petri", ".sym");
			PrintWriter pw = new PrintWriter(out.toFile());
			
			// compute problem size = number of nodes and edges
			
			// each arc with weight > 1 is encoded with an intermediate node colored by weight
			long nontrivialarc = countNonTrivial(flowPT);
			nontrivialarc += countNonTrivial(flowTP);
			// each place with non zero initial marking is associated to a node representing it's tokens.
			long markedPlaces =  marks.stream().filter(i -> i>0).count();
			// each place and transition gives rise to a node
			long nbnode = pnames.size() + tnames.size() + markedPlaces + nontrivialarc;
			
			// count total edges
			
			// each arc gives one edge
			long totalArcs = flowPT.getColumns().stream().map(col -> col.size()).reduce(0, Integer::sum);
			totalArcs += flowPT.getColumns().stream().map(col -> col.size()).reduce(0, Integer::sum);
			
			// besides these "normal" edges, marked places give an extra edge and non trivial arcs cost an extra edge.
			long nbedge = totalArcs + markedPlaces + nontrivialarc ;
			
			// come comments
			pw.println("c from net with "+ pnames.size() + " places and " + tnames.size() + " transitions.");
			
			
			// first line of file = problem definition
			pw.println("p edge "+ nbnode + " " + nbedge);
			
			// places have color 0, transitions have color = 1, special marking/arc annotations have color the value of the function/initial >= 2
			// places have their own index
			// transitions start from tstart = places.size()
			int tstart = pnames.size()+1;
			// marking/function elements start from vstart = places.size() + trans.size()
			int vstart = tstart + tnames.size();
			
			// let places have color 1
			for (int pindex = 0 ; pindex < pnames.size() ; pindex++) {				
				pw.println("n "+ (pindex+1) + " 1");		
			}
			// declare transition nodes as having color 0
			// this is implicitly already the case.
			// so NOP

			int extra = vstart;
			// declare marking nodes for marked places
			// as nodes with color the marking, >= 1
			for (Integer m : marks) {
				if (m >0) {
					pw.println("n "+ extra + " "+ m);
					extra++;
				}
			}
			
			// add values of edges, in transition order.
			for (int t = 0; t < tnames.size() ; t++) {
				SparseIntArray sa = flowPT.getColumn(t);
				for (int i=0;i<sa.size(); i++) {
					if (sa.valueAt(i)>1) {
						pw.println("n "+ extra + " "+ sa.valueAt(i));
						extra++;
					}
				}
				sa = flowTP.getColumn(t);
				for (int i=0;i<sa.size(); i++) {
					if (sa.valueAt(i)>1) {
						pw.println("n "+ extra + " "+ sa.valueAt(i));
						extra++;
					}
				}
			}
			
			// OK, time to export the edges
			// deal with markings
			// any marked place points to a node whose color gives it's marking.
			extra = vstart;
			for (int p=0; p < pnames.size() ; p++) {
				int m = marks.get(p);
				if (m >0) {
					pw.println("e "+ (p+1) + " "+ extra);				
					extra++;
				}				
			}
			// Deal with transitions now
			for (int t = 0; t < tnames.size() ; t++) {
				SparseIntArray sa = flowPT.getColumn(t);
				for (int i=0;i<sa.size(); i++) {
					if (sa.valueAt(i)==1) {
						// simpler case, just put an edge between place and transition
						pw.println("e "+ (sa.keyAt(i)+1) + " "+ (t+tstart));							
					} else {
						// value on arc > 1
						// link to the appropriate "extra" arc node
						pw.println("e "+ (sa.keyAt(i)+1) + " "+ extra);
						pw.println("e "+ extra + " "+ (t+tstart));
						extra++;
					}
				}
				sa = flowTP.getColumn(t);
				for (int i=0;i<sa.size(); i++) {
					if (sa.valueAt(i)==1) {
						// simpler case, just put an edge between place and transition
						pw.println("e "+ (t+tstart) + " "+ (sa.keyAt(i)+1));							
					} else {
						// value on arc > 1
						// link to the appropriate "extra" arc node
						pw.println("e "+ (t+tstart) + " "+extra );
						pw.println("e "+ extra + " "+  (sa.keyAt(i)+1));
						extra++;
					}
				}
			}

			pw.close();
			System.out.println("Successfully produced net in file "+out.toAbsolutePath().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static long countNonTrivial(MatrixCol flowPT) {
		long nontrivialedge = 0;
		for (SparseIntArray sa : flowPT.getColumns()) {
			for (int i=0;i<sa.size(); i++) {
				if (sa.valueAt(i)>1) {
					nontrivialedge++;
				}
			}
		}
		return nontrivialedge;
	}
}
