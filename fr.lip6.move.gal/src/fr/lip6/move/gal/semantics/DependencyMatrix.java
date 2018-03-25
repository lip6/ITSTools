package fr.lip6.move.gal.semantics;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import fr.lip6.move.gal.semantics.INext;
import fr.lip6.move.gal.semantics.NextSupportAnalyzer;

/**
 * Computes and stores the support for a set of linear Next constructions (e.g. as obtained through Determinize
 * @author ythierry
 *
 */
public class DependencyMatrix {

	private List<BitSet> read;
	private List<BitSet> write;
	private List<BitSet> control;
	private int rows;
	
	public DependencyMatrix(List<List<INext>> transitions, int nbRows) {
		read = new ArrayList<>(transitions.size());
		write = new ArrayList<>(transitions.size());
		
		for (List<INext> t : transitions) {
			
			BitSet lr = new BitSet();
			BitSet lw = new BitSet();
			BitSet lc = new BitSet();
			t.forEach(n -> NextSupportAnalyzer.computeSupport(n, lr, lw, lc));
			read.add(lr);
			write.add(lw);
			control.add(lc);
		}
		this.rows = nbRows;
	}

	public BitSet getRead (int tindex) {
		return read.get(tindex);
	}
	public BitSet getWrite (int tindex) {
		return write.get(tindex);
	}
	public BitSet getControl (int tindex) {
		return control.get(tindex);
	}
	
	
	public int nbCols() {
		return read.size();
	}
	public int nbRows() {
		return rows;
	}
}
