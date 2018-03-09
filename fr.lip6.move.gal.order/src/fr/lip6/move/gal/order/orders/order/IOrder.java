package fr.lip6.move.gal.order.orders.order;

import java.util.List;

public interface IOrder {
	int[] getPermutation();

	String permute(String var);
	int permute(int index);

	List<String> getVariables();
	List<String> getVariablesPermuted();
}