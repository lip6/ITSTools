package fr.lip6.move.gal.structural;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import uniol.apt.analysis.invariants.MatrixCol;

//import org.apache.commons.math3.linear.MatrixUtils;
//import org.apache.commons.math3.linear.RealMatrix;


public class FlowMatrix {
	// To represent the flow matrix, if we can build it. We use a sparse representation.
	// Map variable index -> Transition index -> update to variable (a relative integer)
	private Map<Integer, Map<Integer,Integer>> flow = new TreeMap<>();
	private Map<Integer, Map<Integer,Integer>> read = new TreeMap<>();

	private int nbTrans;
	private int nbVar;

	public FlowMatrix (int nbvar, int nbtrans) {
		this.nbVar = nbvar;
		this.nbTrans = nbtrans;
	}
	
	public void addWriteEffect(int tindex, int vindex, int val) {
		if ( !( tindex < nbTrans && vindex < nbVar) ) throw new IllegalArgumentException();
		Map<Integer, Integer> line = flow.get(vindex);
		if (line == null) {
			line  = new TreeMap<>();
			flow.put(vindex, line);
		}
		Integer cur = line.get(tindex);		
		if (cur==null) {
			cur=0;
		}
		cur+=val;
		line.put(tindex, cur);
	}

	public void addReadEffect(int tindex, int vindex, int val) {
		Map<Integer, Integer> line = read.get(vindex);
		if (line == null) {
			line  = new TreeMap<>();
			read.put(vindex, line);
		}
		Integer cur = line.get(tindex);		
		if (cur==null) {
			cur=0;
		}
		cur= Math.max(cur,val);
		line.put(tindex, cur);
	}
	
	public Set<Entry<Integer, Map<Integer, Integer>>> entrySet() {
		return flow.entrySet();
	}

	public int[][] getIncidenceMatrix() {
		int  [][] mat = new int[nbVar][nbTrans];
		for (Entry<Integer, Map<Integer, Integer>> e : flow.entrySet()) {
			int row = e.getKey();
			for (Entry<Integer, Integer> ee : e.getValue().entrySet()) {
				int col = ee.getKey();
				int val = ee.getValue();
				mat [row][col] =  val;
			}
		}
		
		return mat;
	}
	
	public MatrixCol getSparseIncidenceMatrix() {
		MatrixCol mat = new MatrixCol(nbVar, nbTrans);
		for (Entry<Integer, Map<Integer, Integer>> e : flow.entrySet()) {
			int row = e.getKey();
			for (Entry<Integer, Integer> ee : e.getValue().entrySet()) {
				int col = ee.getKey();
				int val = ee.getValue();
				mat.set(row, col, val);
			}
		}
		return mat;
	}
	
	public int[][] getReadMatrix() {
		int  [][] mat = new int[nbVar][nbTrans];
		for (Entry<Integer, Map<Integer, Integer>> e : read.entrySet()) {
			int row = e.getKey();
			for (Entry<Integer, Integer> ee : e.getValue().entrySet()) {
				int col = ee.getKey();
				int val = ee.getValue();
				mat [row][col] =  val;
			}
		}
		
		return mat;
	}
	
}
