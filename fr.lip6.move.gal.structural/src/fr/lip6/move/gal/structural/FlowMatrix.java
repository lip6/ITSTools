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
		if (val == 0)
			return;
		addToColumn(flow.getColumn(tindex), vindex, val);
		if (val < 0) {
			addToColumn(flowPT.getColumn(tindex), vindex, -val);
		} else {
			addToColumn(flowTP.getColumn(tindex), vindex, val);
		}
	}

	private void addToColumn(SparseIntArray column, int vindex, int val) {
		int cur = column.get(vindex);				
		cur+=val;
		column.put(vindex, cur);
	}

	public void addReadEffect(int tindex, int vindex, int val) {
		SparseIntArray line = flowPT.getColumn(tindex);
		int cur = line.get(vindex);				
		int max=Math.max(cur,val);
		if (max != cur) {
			line.put(vindex, cur);
			addToColumn(flowTP.getColumn(tindex), vindex, val);
		}
		read.getColumn(tindex).put(vindex, max);
	}
	
	public MatrixCol getIncidenceMatrix() {
		return flow;
	}
	
	public MatrixCol getRead() {
		return read;
	}
	
	public MatrixCol getFlowPT() {
		return flowPT;
	}
	
	public MatrixCol getFlowTP() {
		return flowTP;
	}
	
}
