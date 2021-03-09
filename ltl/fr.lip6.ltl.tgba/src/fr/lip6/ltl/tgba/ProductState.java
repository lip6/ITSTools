package fr.lip6.ltl.tgba;

import android.util.SparseIntArray;

public class ProductState {
	private int tgbaState;
	private SparseIntArray pnState;

	public ProductState(int tgbaState, SparseIntArray pnState) {
		this.tgbaState = tgbaState;
		this.pnState = pnState;
	}

	public SparseIntArray getPNState() {
		return pnState;
	}
	public int getTGBAState() {
		return tgbaState;
	}
	
	
	
}
