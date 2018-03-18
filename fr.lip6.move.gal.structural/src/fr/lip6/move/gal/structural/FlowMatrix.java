package fr.lip6.move.gal.structural;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.MatrixCol;

public class FlowMatrix {
	// To represent the flow matrix, if we can build it. We use a sparse representation.
	// Map variable index -> Transition index -> update to variable (a relative integer)
	private MatrixCol flow ;
	private MatrixCol read ;
	private MatrixCol flowPT ;
	private MatrixCol flowTP ;

	public FlowMatrix (int nbvar, int nbtrans) {
		flow = new MatrixCol(nbvar, nbtrans);
		flowPT = new MatrixCol(nbvar, nbtrans);
		flowTP = new MatrixCol(nbvar, nbtrans);
		read = new MatrixCol(nbvar, nbtrans);
	}
	
	public void addWriteEffect(int tindex, int vindex, int val) {
		SparseIntArray line = flow.getColumn(tindex);
		int cur = line.get(vindex);				
		cur+=val;
		line.put(vindex, cur);
	}

	public void addReadEffect(int tindex, int vindex, int val) {
		SparseIntArray line = flow.getColumn(tindex);
		int cur = line.get(vindex);				
		cur=Math.max(cur,val);
		line.put(vindex, cur);
	}
	
	public MatrixCol entrySet() {
		return flow;
	}

	public int[][] getIncidenceMatrix() {
		return flow.explicit();
	}
	
	public MatrixCol getSparseIncidenceMatrix() {
		return new MatrixCol(flow);
	}
	
	public MatrixCol getRead() {
		return read;
	}
	
}
