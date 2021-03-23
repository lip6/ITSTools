package fr.lip6.move.gal.structural;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.IntMatrixCol;

public class FlowMatrix {
	// To represent the flow matrix, if we can build it. We use a sparse representation.
	// Map variable index -> Transition index -> update to variable (a relative integer)
	private IntMatrixCol flow ;
	private IntMatrixCol read ;
	private IntMatrixCol flowPT ;
	private IntMatrixCol flowTP ;

	public FlowMatrix (int nbvar, int nbtrans) {
		flow = new IntMatrixCol(nbvar, nbtrans);
		flowPT = new IntMatrixCol(nbvar, nbtrans);
		flowTP = new IntMatrixCol(nbvar, nbtrans);
		read = new IntMatrixCol(nbvar, nbtrans);
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
			line.put(vindex, max);
			addToColumn(flowTP.getColumn(tindex), vindex, max - cur);
		}
		read.getColumn(tindex).put(vindex, max);
	}
	
	public IntMatrixCol getIncidenceMatrix() {
		return flow;
	}
	
	public IntMatrixCol getRead() {
		return read;
	}
	
	public IntMatrixCol getFlowPT() {
		return flowPT;
	}
	
	public IntMatrixCol getFlowTP() {
		return flowTP;
	}
	
}
