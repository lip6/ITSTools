package fr.lip6.move.gal.order.order;

import java.io.IOException;
import java.util.List;

public interface IOrder {
	int[] getPermutation();
	String permute(String var);
	int permute(int index);
	
	public List<String> getVariables();
	public List<String> getVariablesPermuted();
	public  String[] fileToList(String nf);
	public void listToFile(String nf,List<String> vars);
	
	void printOrder (String path) throws IOException;
}
