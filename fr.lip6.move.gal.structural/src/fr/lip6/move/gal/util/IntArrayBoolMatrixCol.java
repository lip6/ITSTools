package fr.lip6.move.gal.util;

import android.util.SparseBoolArray;

// this implementation stores an array of SparseBoolArray
public class IntArrayBoolMatrixCol implements IBoolMatrixCol {
	private SparseBoolArray [] cols ;
	
	public IntArrayBoolMatrixCol(int nbcol) {
		cols = new SparseBoolArray [nbcol];
		for (int i=0; i < nbcol ; i++) {
			cols[i] = new SparseBoolArray();
		}
	}

	@Override
	public boolean get(int col, int row) {
		return cols[col].get(row);
	}

	@Override
	public void set(int col, int row, boolean b) {
		cols[col].put(row,b);		
	}

	@Override
	public int[] getColumn(int col) {		
		return cols[col].refKeys();
	}
	

}
