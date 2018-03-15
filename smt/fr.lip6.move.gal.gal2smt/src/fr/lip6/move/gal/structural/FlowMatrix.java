package fr.lip6.move.gal.structural;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

//import org.apache.commons.math3.linear.MatrixUtils;
//import org.apache.commons.math3.linear.RealMatrix;


public class FlowMatrix {
	// To represent the flow matrix, if we can build it. We use a sparse representation.
	// Map variable index -> Transition index -> update to variable (a relative integer)
	private Map<Integer, Map<Integer,Integer>> flow = new TreeMap<>();
	private Map<Integer, Map<Integer,Integer>> read = new TreeMap<>();

	private int maxT=0, maxV=0;
	
	public void addWriteEffect(int tindex, int vindex, int val) {
		maxT = Math.max(maxT, tindex);
		maxV = Math.max(maxV, vindex);
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
	
	
//	public RealMatrix getIncidenceMatrix () {
//		RealMatrix mat = MatrixUtils.createRealMatrix(maxV+1, maxT+1);
//		for (Entry<Integer, Map<Integer, Integer>> e : flow.entrySet()) {
//			int row = e.getKey();
//			for (Entry<Integer, Integer> ee : e.getValue().entrySet()) {
//				int col = ee.getKey();
//				int val = ee.getValue();
//				mat.setEntry(row, col, val);
//			}
//		}
//		
//		return mat;
//	}

	public int[][] getIncidenceMatrix2() {
		int  [][] mat = new int[maxV+1][maxT+1];
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
	public int[][] getReadMatrix() {
		int  [][] mat = new int[maxV+1][maxT+1];
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
