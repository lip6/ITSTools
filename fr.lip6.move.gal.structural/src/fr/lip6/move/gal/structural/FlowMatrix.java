package fr.lip6.move.gal.structural;

import java.util.Map;
import java.util.Map.Entry;

import android.util.SparseIntArray;

import java.util.Set;
import java.util.TreeMap;

import uniol.apt.analysis.invariants.MatrixCol;

//import org.apache.commons.math3.linear.MatrixUtils;
//import org.apache.commons.math3.linear.RealMatrix;


public class FlowMatrix {
	// To represent the flow matrix, if we can build it. We use a sparse representation.
	// Map variable index -> Transition index -> update to variable (a relative integer)
	private Map<Integer, SparseIntArray> flow = new TreeMap<>();
	private Map<Integer, SparseIntArray> read = new TreeMap<>();

	private int nbTrans;
	private int nbVar;

	public FlowMatrix (int nbvar, int nbtrans) {
		this.nbVar = nbvar;
		this.nbTrans = nbtrans;
	}
	
	public void addWriteEffect(int tindex, int vindex, int val) {
		if ( !( tindex < nbTrans && vindex < nbVar) ) throw new IllegalArgumentException();
		SparseIntArray line = flow.get(vindex);
		if (line == null) {
			line  = new SparseIntArray();
			flow.put(vindex, line);
		}
		int cur = line.get(tindex);				
		cur+=val;
		line.put(tindex, cur);
	}

	public void addReadEffect(int tindex, int vindex, int val) {
		SparseIntArray line = read.get(vindex);
		if (line == null) {
			line  = new SparseIntArray();
			read.put(vindex, line);
		}
		int cur = line.get(tindex);		
		cur= Math.max(cur,val);
		line.put(tindex, cur);
	}
	
	public Set<Entry<Integer, SparseIntArray>> entrySet() {
		return flow.entrySet();
	}

	public int[][] getIncidenceMatrix() {
		return getSparseIncidenceMatrix().explicit();
	}
	
	public MatrixCol getSparseIncidenceMatrix() {
		MatrixCol mat = new MatrixCol(nbVar, nbTrans);
		for (Entry<Integer, SparseIntArray> e : flow.entrySet()) {
			int row = e.getKey();
			SparseIntArray arr = e.getValue();
			for (int i = 0 ; i < arr.size() ; i++) {
				int col = arr.keyAt(i);
				int val = arr.valueAt(i);
				mat.set(row, col, val);
			}
		}
		return mat;
	}
	
	
}
