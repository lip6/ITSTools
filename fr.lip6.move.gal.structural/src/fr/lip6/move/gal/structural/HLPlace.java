package fr.lip6.move.gal.structural;

import java.util.Arrays;
import java.util.List;

import fr.lip6.move.gal.structural.expr.Expression;

public class HLPlace {
	private String name;
	int startIndex;
	private int [] initial;
	private boolean isConstant = false;
	private List<Sort> sort;
	
	private int [] multipliers=null;
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
	/**
	 * Assume r is a constant tuple, e.g. <3,2,4>
	 * @param r
	 * @return the index in flattened array for this cell
	 */
	public int resolve(List<Expression> r) {
		if (multipliers ==null) {
			multipliers = new int[sort.size()];
			multipliers[sort.size()-1]=1;						
			for (int i=sort.size()-2 ; i >= 0 ; i--) {
				multipliers[i] = multipliers[i+1]*sort.get(i+1).size();
			}			
		}	
		int sum=0;
		for (int i=0; i < r.size(); i++) {
			sum += multipliers[i]*r.get(i).eval(null);
		}
		return sum;
	}
}