package fr.lip6.move.gal.contribution.orders;

import java.io.IOException;
import java.util.Collection;

public interface IOrder {
	int[] getPermutation();
	String permute(String var);
	int permute(int index);

	Collection<String> getVariables();
	Collection<String> getVariablesPermuted();

	IOrder compose(IOrder other);
	
	void printOrder (String path) throws IOException;
}
