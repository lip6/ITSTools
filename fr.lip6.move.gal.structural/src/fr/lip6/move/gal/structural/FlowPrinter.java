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
		return drawNet(sr, title, Collections.emptySet(), Collections.emptySet(),200);
	}

	public static String drawNet (StructuralReduction sr, String title, int maxShown)  {
		return drawNet(sr, title, Collections.emptySet(), Collections.emptySet(),maxShown);
	}
	
	public static String drawNet (StructuralReduction sr, String title, Set<Integer> hlPlaces, Set<Integer> hlTrans)  {
		return drawNet(sr, title, hlPlaces, hlTrans, 200);
	}
	public static String drawNet (StructuralReduction sr, String title, Set<Integer> hlPlaces, Set<Integer> hlTrans, int maxShown)  {
		return drawNet(sr.getFlowPT(),sr.getFlowTP(),sr.getMarks(),sr.getPnames(),sr.getTnames(), sr.getUntouchable(), "places: "+sr.getPnames().size() + " trans:"+ sr.getTnames().size()+ " " + title, hlPlaces, hlTrans, maxShown);
	}
	
	public static String drawNet (MatrixCol flowPT, MatrixCol flowTP, List<Integer> marks, List<String> pnames, List<String> tnames, BitSet untouchable, String title, Set<Integer> hlPlaces, Set<Integer> hlTrans, int maxShown) {
		try {
			Path out = Files.createTempFile("petri"+nbWritten++ +"_", ".dot");
			PrintWriter pw = new PrintWriter(out.toFile());

			pw.println("digraph {");
			pw.println("  overlap=\"false\";");			
			pw.println("  labelloc=\"t\";");
			boolean isLarge = false;
			Set<Integer> torep = new HashSet<>();
			Set<Integer> toret = new HashSet<>();

			MatrixCol tflowPT = null;
			MatrixCol tflowTP = null;
			
			if (pnames.size() + tnames.size() > 2*maxShown) {
				isLarge = true;
				title += "(Net is too large representing up to roughly "+maxShown+" objects)";
				{
					Iterator<Integer> it = hlTrans.iterator();
					for (int ite=0; it.hasNext() && ite < maxShown/2 ; ite++) {
						int ti = it.next();
						addNeighborhood(ti, flowPT, flowTP, torep, toret);
					}
				}
				{
					Iterator<Integer> it = hlPlaces.iterator();
					for (int i=0; it.hasNext() && i < maxShown/2 ; i++) {
						torep.add(it.next());
					}
					tflowPT = flowPT.transpose();
					tflowTP = flowTP.transpose();
					for (int pi :torep) {
						addNeighborhood(pi, tflowPT, tflowTP, toret, torep);
					}
					for (int ti:toret) {
						addNeighborhood(ti, flowPT, flowTP, torep, toret);
					}
					if (hlTrans.isEmpty() && hlPlaces.isEmpty()) {
						int pi = untouchable.nextSetBit(0);
						while (torep.size() + toret.size() < ((3*maxShown)/2) && pi >= 0) {
							addNeighborhood(pi, tflowPT, tflowTP, toret, torep);
							if (pi == Integer.MAX_VALUE) {
								break; // or (i+1) would overflow
							}
							pi = untouchable.nextSetBit(pi+1);
						}						
					}
					if (toret.isEmpty() && torep.isEmpty()) {
						torep.add(0);
					}
					
					int sz = 0;
					while (torep.size() + toret.size() < maxShown && torep.size() + toret.size() > sz) {
						sz = torep.size() + toret.size();
						it = torep.iterator();
						while (torep.size() + toret.size() < maxShown && it.hasNext()) {
							addNeighborhood(it.next(), tflowPT, tflowTP, toret, torep);
						}
						it = toret.iterator();
						while (torep.size() + toret.size() < maxShown && it.hasNext()) {
							addNeighborhood(it.next(), flowPT, flowTP, torep, toret);
						}
					}
				}
			}
			pw.println("label=\""+ title +"\";");

			
			for (int ti=0 ; ti < tnames.size() ; ti++) {
				if (isLarge && !toret.contains(ti)) {
					continue;
				}
				String color = "";
				if (hlTrans.contains(ti)) {
					color = ",color=\"blue\""+",peripheries=2";
				}
				boolean incomplete = false;
				SparseIntArray col = flowPT.getColumn(ti);				
				for (int i = 0; i < col.size(); i++) {
					if (!isLarge || torep.contains(col.keyAt(i))) {
						pw.print(" p" + col.keyAt(i)+" -> t"+ti);
						if (col.valueAt(i) != 1) {
							pw.print(" [label=\""+col.valueAt(i)+"\"]"); 
						}
						pw.println(";");
					} else {
						incomplete = true; 
					}
				}
				col = flowTP.getColumn(ti);
				for (int i = 0; i < col.size(); i++) {					
					if (!isLarge || torep.contains(col.keyAt(i))) {
						pw.print("  t"+ti+" -> p" + col.keyAt(i) );
						if (col.valueAt(i) != 1) {
							pw.print(" [label=\""+col.valueAt(i)+"\"]"); 
						}
						pw.println(";");
					} else {
						incomplete = true; 
					}
				}
				if (incomplete) {
					color += ",style=\"dashed\"";
				}
				pw.println("  t"+ti+ " [shape=\"rectangle\",label=\""+tnames.get(ti)+"\"" + color +"];");
			}

			for (int pi=0 ; pi < pnames.size() ; pi++) {
				if (isLarge && !torep.contains(pi)) {
					continue;
				}
				String color = "";
				if (untouchable.get(pi) && hlPlaces.contains(pi)) {
					color = ",color=\"violet\""+",style=\"filled\""+",peripheries=2";
				} else if (untouchable.get(pi)) {
					color = ",color=\"red\""+",style=\"filled\"";
				} else if (hlPlaces.contains(pi)) {
					color = ",color=\"blue\""+",peripheries=2";
				}
				if (isLarge) {
					boolean incomplete = false;
					SparseIntArray col = tflowPT.getColumn(pi);				
					for (int i = 0; i < col.size(); i++) {
						if (! toret.contains(col.keyAt(i))) {
							incomplete = true;
							break;
						}
					}
					if (!incomplete) {
						col = tflowTP.getColumn(pi);
						for (int i = 0; i < col.size(); i++) {					
							if (!toret.contains(col.keyAt(i))) {
								incomplete = true;
								break;
							}
						}
					}
					if (incomplete) {
						color += ",style=\"dashed\"";
					}
				}
				pw.println("  p"+pi+ " [shape=\"oval\",label=\""+pnames.get(pi) +(marks.get(pi)!=0?"("+marks.get(pi)+")":"") + "\"" + color +"];");			
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
