package fr.lip6.move.gal.structural;

import java.util.Arrays;
import java.util.List;

public class HLPlace {
	private String name;
	int startIndex;
	private int [] initial;
	private boolean isConstant = false;
	private List<Sort> sort;
	public HLPlace(String name, int start, int [] initial, List<Sort> sort) {
		this.name = name;
		this.startIndex = start;
		this.initial = initial;
		this.sort = sort;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "[name=" + name + "(" + sort + ")" + ", startIndex=" + startIndex + ", initial=" + Arrays.toString(initial) + "]\n";
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
	public void setInitial(int[] initial) {
		this.initial = initial;
	}
	public List<Sort> getSort() {
		return sort;
	}
}