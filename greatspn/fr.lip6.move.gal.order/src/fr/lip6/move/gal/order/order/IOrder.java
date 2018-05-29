package fr.lip6.move.gal.order.order;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IOrder {
//	public int[] getPermutation();
//	public String permute(String var);
//	public int permute(int index);
	public List<String> getVariables();
	public List<String> getVariablesPermuted();
//	public  String[] fileToList(String nf);
//	public void listToFile(String nf,List<String> vars);
	public void printOrder (String path) throws IOException;
	void filter(Set<String> st);
	
}
