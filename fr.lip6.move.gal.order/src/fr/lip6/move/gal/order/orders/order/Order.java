package fr.lip6.move.gal.contribution.orders.order;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Order implements IOrder {

	protected Map<String, Integer> varpositions;
	protected String[] varnames;
	protected int[] permutation; // i -> permutation[i]

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
		
		for (String var : varsout)
			permutation[i] = initial_pos.get(var);
	}

	@Override
	public int[] getPermutation() {
		return permutation;
	}

	@Override
	public List<String> getVariables() {
		return Arrays.stream(varnames)
				.collect(Collectors.toList());
	}

	@Override
	public List<String> getVariablesPermuted() {

		return Arrays.stream(varnames)
				.map(var -> permute(var))
				.collect(Collectors.toList());
	}

	@Override
	public String permute(String var) {
		return varnames[ permutation[ varpositions.get(var) ] ];
	}

	@Override
	public int permute(int index) {
		return permutation[index];
	}
}
