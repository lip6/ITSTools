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

	@Override
	public int hashCode() {
		final int prime = 9497;
		int result = 18121;
		result = prime * result + ((pnState == null) ? 0 : pnState.hashCode());
		result = prime * result + tgbaState;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductState other = (ProductState) obj;
		if (pnState == null) {
			if (other.pnState != null)
				return false;
		} else if (!pnState.equals(other.pnState))
			return false;
		if (tgbaState != other.tgbaState)
			return false;
		return true;
	}
	
	
}
