package fr.lip6.move.gal.structural.tar;

import java.util.BitSet;
import java.util.Stack;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.Property;

public class Solver {

	private BitSet support;

	public Solver(ISparsePetriNet pn, SparseIntArray state, Property property, BitSet support) {

		this.support = support;
	}

	public BitSet getSupport() {
		return support;
	}

	public boolean check(Stack<State> waiting, TraceSet traceSet) {
		// TODO Auto-generated method stub
		return false;
	}


}
