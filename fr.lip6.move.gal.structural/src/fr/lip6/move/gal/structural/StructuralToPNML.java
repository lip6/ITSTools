package fr.lip6.move.gal.structural;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import android.util.SparseIntArray;

public class StructuralToPNML {

	private static Logger log = Logger.getLogger("fr.lip6.move.gal");
	
	private static Logger getLog() {
		return log ;
	}

	
	public static void transform(StructuralReduction sr, String path) throws IOException {
		long time = System.currentTimeMillis();
		PrintWriter pw = new PrintWriter(new File(path));
		pw.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		pw.append("<pnml xmlns=\"http://www.pnml.org/version-2009/grammar/pnml\">");
		pw.append("<net id=\"ITS Tools generated\" type=\"http://www.pnml.org/version-2009/grammar/ptnet\">");
		pw.append("<page id=\"page0\"><name><text>DefaultPage</text></name>");
		
		for (int p = 0 ; p < sr.getPnames().size() ; p++) {
			pw.append("<place id=\"p"+p+"\">");
			pw.append("<name><text>"+sr.getPnames().get(p)+"</text></name>");
			pw.append("<initialMarking><text>" + sr.getMarks().get(p) + "</text></initialMarking>");
			pw.append("</place>");
		}
		for (int t = 0 ; t < sr.getTnames().size() ; t++) {
			pw.append("<transition id=\"t"+t+"\">");
			pw.append("<name><text>"+sr.getTnames().get(t)+"</text></name>");
			pw.append("</transition>");
		}
		int arcid = 0;
		for (int t = 0; t < sr.getTnames().size() ; t++) {
			SparseIntArray pt = sr.getFlowPT().getColumn(t);
			for (int i=0 ; i < pt.size() ; i++) {
				int p = pt.keyAt(i);
				int val = pt.valueAt(i);
				pw.append("<arc id=\"arc"+(arcid++)+"\" source=\"p"+ p + "\" target=\"t"+ t +"\">");
				pw.append("<inscription><text>"+val+"</text></inscription>");
				pw.append("</arc>");
			}
		}
		for (int t = 0; t < sr.getTnames().size() ; t++) {
			SparseIntArray tp = sr.getFlowTP().getColumn(t);
			for (int i=0 ; i < tp.size() ; i++) {
				int p = tp.keyAt(i);
				int val = tp.valueAt(i);
				pw.append("<arc id=\"arc"+(arcid++)+"\" source=\"t"+ t + "\" target=\"p"+ p +"\">");
				pw.append("<inscription><text>"+val+"</text></inscription>");
				pw.append("</arc>");
			}
		}
		pw.append("</page>");
		pw.append("<name><text>ITS Tools generated</text></name></net>");
		pw.append("</pnml>");
		pw.close();
		getLog().info("Export to PNML in file "+path +" took "+ (System.currentTimeMillis()-time) + " ms.");
	}

}