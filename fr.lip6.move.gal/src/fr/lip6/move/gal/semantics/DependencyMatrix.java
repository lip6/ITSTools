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
	
	public DependencyMatrix(List<List<INext>> transitions) {
		read = new ArrayList<>(transitions.size());
		write = new ArrayList<>(transitions.size());
		
		for (List<INext> t : transitions) {
			
			BitSet lr = new BitSet();
			BitSet lw = new BitSet();

			t.forEach(n -> NextSupportAnalyzer.computeSupport(n, lr, lw));
			read.add(lr);
			write.add(lw);
		}
	}

	public BitSet getRead (int tindex) {
		return read.get(tindex);
	}
	public BitSet getWrite (int tindex) {
		return write.get(tindex);
	}
}
