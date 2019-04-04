package fr.lip6.move.gal.structural;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.MatrixCol;

public class FlowPrinter {

	public static String drawNet (StructuralReduction sr)  {
		return drawNet(sr.getFlowPT(),sr.getFlowTP(),sr.getMarks(),sr.getPnames(),sr.getTnames());
	}

	public static String drawNet (MatrixCol flowPT, MatrixCol flowTP, List<Integer> marks, List<String> pnames, List<String> tnames) {
		try {
			Path out = Files.createTempFile("petri", ".dot");
			PrintWriter pw = new PrintWriter(out.toFile());

			pw.println("digraph {");
			for (int i=0 ; i < pnames.size() ; i++) {
				pw.println("  p"+i+ " [shape=\"circle\",label=\""+pnames.get(i)+(marks.get(i)!=0?"("+marks.get(i)+")":"") +"\"];");
			}
			for (int i=0 ; i < tnames.size() ; i++) {
				pw.println("  t"+i+ " [shape=\"rectangle\",label=\""+tnames.get(i)+"\"];");
			}
			for (int ti = 0 ; ti < flowPT.getColumnCount() ; ti++) {
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
					pw.println("  t"+ti+" -> p" + col.keyAt(i) );
					if (col.valueAt(i) != 1) {
						pw.print(" [label=\""+col.valueAt(i)+"\"]"); 
					}
					pw.println(";");
				}
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
}
