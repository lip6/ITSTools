package fr.lip6.ltl.tgba;

import android.util.SparseIntArray;

public class ProductState {
	private int tgbaState;
	private SparseIntArray pnState;
	boolean canStutter = false;
	boolean isDead = false;

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
	
	public void setCanStutter(boolean canStutter) {
		this.canStutter = canStutter;
	}
	
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	public boolean isCanStutter() {
		return canStutter;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	
}
