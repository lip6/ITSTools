package fr.lip6.move.gal.contribution.orders.order;

import java.io.IOException;
import java.util.Collection;

import java.util.List;

public interface IOrder {
	int[] getPermutation();

	String permute(String var);
	int permute(int index);

	List<String> getVariables();
	List<String> getVariablesPermuted();
}