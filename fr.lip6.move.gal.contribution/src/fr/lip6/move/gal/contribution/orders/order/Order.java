package fr.lip6.move.gal.contribution.orders.order;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class Order implements IOrder {

	protected Map<String, Integer> varpositions;
	protected String[] varnames;
	protected int[] permutation; //TODO i varnames becomes permutation[i] varnames AJOUTER JAVADOC

	public Order(Collection<String> vars) {
		varnames = vars.toArray(new String[vars.size()]);
		varpositions = new HashMap<>();
		
		permutation = new int[vars.size()];
		for (int i = 0; i < vars.size(); i++) {
			permutation[i] = i;
			varpositions.put(varnames[i], i);
		}
	}

	public Order(Collection<String> varsin, Collection<String> varsout) {
		varnames = varsin.toArray(new String[varsin.size()]);
		varpositions = new HashMap<>();
		
		int i = 0;
		Map<String, Integer> initial_pos = new HashMap<>();
		for (String var : varsin) {
			initial_pos.put(var, i);
			varpositions.put(var, i);
			i++;
		}
		i = 0;//ajout
		for (String var : varsout) {
			permutation[i] = initial_pos.get(var);
			i++;// ajout
		}
	}
    //a b c -> 1 2 3,  b c a -> 2 3 1
	

	public Order(Collection<String> varsin, String[] varsout) {
		varnames = varsin.toArray(new String[varsin.size()]);
		varpositions = new HashMap<>();
		
		int i = 0;
		Map<String, Integer> initial_pos = new HashMap<>();
		for (String var : varsin) {
			initial_pos.put(var, i);
			varpositions.put(var, i);
			i++;
		}
		i = 0;//ajout
		for (String var : varsout) {
			permutation[i] = initial_pos.get(var);
			i++;// ajout
		}
	}
	
	public Collection<String> getVariables() {
		return Arrays.asList(varnames);
	}

	
	public Collection<String> getVariablesPermuted() {

		return Arrays.stream(varnames)
				.map(var -> permute(var))
				.collect(Collectors.toList());
	}


	public void printOrder(String path) throws IOException
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

	public String permute(String var) {
		return varnames[ permutation[ varpositions.get(var) ] ];
	}

	public int permute(int index) {
		return permutation[index];
	}

	@Override
	public int[] getPermutation() {
		// TODO Auto-generated method stub
		return permutation;
	}
}
