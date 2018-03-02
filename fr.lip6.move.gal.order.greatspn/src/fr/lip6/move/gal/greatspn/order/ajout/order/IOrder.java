package fr.lip6.move.gal.greatspn.order.ajout.order;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface IOrder {
	
	public int[] getPermutation();
	public int permute(int i);
	public List<String> getVariables();
	public List<String> getVariablesPermuted();
//	public static String[] fileToList(String nf);
//	public static void listToFile(String nf,List<String> vars);
}
