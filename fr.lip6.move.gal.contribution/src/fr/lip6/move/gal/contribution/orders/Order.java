package fr.lip6.move.gal.contribution.orders;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order implements IOrder {

	protected String[] varnames;
	protected int[] permutation; //TODO i varnames becomes permutation[i] varnames AJOUTER JAVADOC
	
	public Order(Collection<String> vars) {
		varnames = vars.toArray(new String[vars.size()]);
		
		permutation = new int[vars.size()];
		for (int i = 0; i < vars.size(); i++)
			permutation[i] = i;
	}

	public Order(Collection<String> varsin, Collection<String> varsout) {
		varnames = varsin.toArray(new String[varsin.size()]); //TODO choisir type random access
		
		int i = 0;
		Map<String, Integer> initial_pos = new HashMap<>();
		for (String var : varsin)
			initial_pos.put(var, i++);
		
		for (String var : varsout)
			permutation[i] = initial_pos.get(var);
	}

	@Override
	public int[] getPermutation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getVariables() {
		return Arrays. varnames;
	}

	@Override
	public Collection<String> getVariablesPermuted() {
		
		return null;
	}

	@Override
	public IOrder compose(IOrder other) {
		for (int i = 0; i < permutation.length; i++)
			permutation[i] = other.getPermutation()[ permutation[i] ];
			
		return null;
	}

	void printOrder(String path) throws IOException
	{
		PrintWriter out = new PrintWriter( new BufferedOutputStream(new FileOutputStream(path)));
		out.println("#START");
		for (String var : getVariablesPermuted()) {
			out.println(var);
		}
		out.println("#END");
		out.flush();
		out.close();
	}

	@Override
	public String permute(String var) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int permute(int index) {
		// TODO Auto-generated method stub
		return 0;
	}
}
