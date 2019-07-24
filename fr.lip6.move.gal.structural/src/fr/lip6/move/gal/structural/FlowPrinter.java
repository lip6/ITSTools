package fr.lip6.move.gal.structural;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.MatrixCol;

public class FlowPrinter {

	static int nbWritten = 1000;
	
	public static String drawNet (StructuralReduction sr, String title)  {
		return drawNet(sr, title, Collections.emptySet(), Collections.emptySet());
	}
	
	public static String drawNet (StructuralReduction sr, String title, Set<Integer> hlPlaces, Set<Integer> hlTrans)  {
		return drawNet(sr.getFlowPT(),sr.getFlowTP(),sr.getMarks(),sr.getPnames(),sr.getTnames(), sr.getUntouchable(), "places: "+sr.getPnames().size() + " trans:"+ sr.getTnames().size()+ " " + title, hlPlaces, hlTrans);
	}
	
	public static String drawNet (MatrixCol flowPT, MatrixCol flowTP, List<Integer> marks, List<String> pnames, List<String> tnames, BitSet untouchable, String title, Set<Integer> hlPlaces, Set<Integer> hlTrans) {
		try {
			Path out = Files.createTempFile("petri"+nbWritten++ +"_", ".dot");
			PrintWriter pw = new PrintWriter(out.toFile());

			pw.println("digraph {");
			pw.println("  labelloc=\"t\";");
			boolean isLarge = false;
			Set<Integer> torep = new HashSet<>();
			Set<Integer> toret = new HashSet<>();

			if (pnames.size() + tnames.size() > 400) {
				isLarge = true;
				title += "(Net is too large representing up to 300 objects)";
				{
					Iterator<Integer> it = hlTrans.iterator();
					for (int ite=0; it.hasNext() && ite < 100 ; ite++) {
						int ti = it.next();
						addNeighborhood(ti, flowPT, flowTP, torep, toret);
					}
				}
				{
					Iterator<Integer> it = hlPlaces.iterator();
					for (int i=0; it.hasNext() && i < 100 ; i++) {
						torep.add(it.next());
					}
					MatrixCol tflowPT = flowPT.transpose();
					MatrixCol tflowTP = flowTP.transpose();
					for (int pi :torep) {
						addNeighborhood(pi, tflowPT, tflowTP, toret, torep);
					}
					int pi = untouchable.nextSetBit(0);
					while (torep.size() + toret.size() < 220 && pi >= 0) {
						addNeighborhood(pi, tflowPT, tflowTP, toret, torep);
						if (pi == Integer.MAX_VALUE) {
							break; // or (i+1) would overflow
						}
						pi = untouchable.nextSetBit(pi+1);
					}
					pi=0;
					while (torep.size() + toret.size() < 220 && pi < pnames.size()) {
						addNeighborhood(pi++, tflowPT, tflowTP, toret, torep);
					}
				}
			}
			pw.println("label=\""+ title +"\";");

			
			for (int i=0 ; i < tnames.size() ; i++) {
				if (isLarge && !toret.contains(i)) {
					continue;
				}
				String col = "";
				if (hlTrans.contains(i)) {
					col = ",color=\"blue\""+",peripheries=2";
				}
				pw.println("  t"+i+ " [shape=\"rectangle\",label=\""+tnames.get(i)+"\"" + col +"];");
			}
			for (int ti = 0 ; ti < flowPT.getColumnCount() ; ti++) {
				if (isLarge && !toret.contains(ti)) {
					continue;
				}
				SparseIntArray col = flowPT.getColumn(ti);				
				for (int i = 0; i < col.size(); i++) {					
					pw.print("  p"+col.keyAt(i)+" -> t" + ti);
					if (col.valueAt(i) != 1) {
						pw.print(" [label=\""+col.valueAt(i)+"\"]"); 
					}
					pw.println(";");
				}
				col = flowTP.getColumn(ti);
				for (int i = 0; i < col.size(); i++) {					
					pw.print("  t"+ti+" -> p" + col.keyAt(i) );
					if (col.valueAt(i) != 1) {
						pw.print(" [label=\""+col.valueAt(i)+"\"]"); 
					}
					pw.println(";");
				}
			}

			for (int i=0 ; i < pnames.size() ; i++) {
				if (isLarge && !torep.contains(i)) {
					continue;
				}
				String col = "";
				if (untouchable.get(i) && hlPlaces.contains(i)) {
					col = ",color=\"violet\""+",style=\"filled\""+",peripheries=2";
				} else if (untouchable.get(i)) {
					col = ",color=\"red\""+",style=\"filled\"";
				} else if (hlPlaces.contains(i)) {
					col = ",color=\"blue\""+",peripheries=2";
				}
				pw.println("  p"+i+ " [shape=\"oval\",label=\""+pnames.get(i) +(marks.get(i)!=0?"("+marks.get(i)+")":"") + "\"" + col +"];");			
			}

			pw.println("}");
			pw.close();
			System.out.println("Successfully produced net in file "+out.toAbsolutePath().toString());
			return out.toAbsolutePath().toString();
		} catch (IOException e) {
			System.err.println("Unable to produce dot representation in file :" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private static void addNeighborhood(int ti, MatrixCol flowPT, MatrixCol flowTP, Set<Integer> torep,
			Set<Integer> toret) {
		toret.add(ti);
		SparseIntArray col = flowPT.getColumn(ti);				
		for (int i = 0; i < col.size(); i++) {
			torep.add(col.keyAt(i));						
		}
		col = flowTP.getColumn(ti);
		for (int i = 0; i < col.size(); i++) {
			torep.add(col.keyAt(i));						
		}
	}
}
