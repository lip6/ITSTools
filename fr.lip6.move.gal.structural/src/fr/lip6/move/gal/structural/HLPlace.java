package fr.lip6.move.gal.structural;

import java.util.Arrays;

public class HLPlace {
	private String name;
	int startIndex;
	private int [] initial;
	private boolean isConstant = false;
	private String sort;
	public HLPlace(String name, int start, int [] initial, String sname) {
		this.name = name;
		this.startIndex = start;
		this.initial = initial;
		this.sort = sname;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "[name=" + name + ", startIndex=" + startIndex + ", initial=" + Arrays.toString(initial) + "]";
	}
	public void setConstant(boolean b) {
		this.isConstant = b;
	}
	public boolean isConstant() {
		return isConstant;
	}
	public int[] getInitial() {
		return initial;
	}
	public String getSort() {
		return sort;
	}
}