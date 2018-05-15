package fr.lip6.move.gal.order.order;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.lip6.move.gal.order.flag.OrderHeuristic;



public class Order implements IOrder {

	protected Map<String, Integer> varpositions;
	protected String[] varnames;
	protected int[] permutation; //TODO i varnames becomes permutation[i] varnames AJOUTER JAVADOC
	private OrderHeuristic heuri;
	private String name;
	
	
	public Order(List<String> vars,OrderHeuristic h, String name) {
		varnames = vars.toArray(new String[vars.size()]);
		varpositions = new HashMap<>();
		heuri=h;
		this.name=name;
		permutation = new int[vars.size()];
		for (int i = 0; i < vars.size(); i++) {
			permutation[i] = i;
			varpositions.put(varnames[i], i);
		}
		
	}

	
	
	
	public Order(List<String> varsin, List<String> varsout,OrderHeuristic h, String name) {
		varnames = varsin.toArray(new String[varsin.size()]);
		varpositions = new HashMap<>();
		permutation = new int[varsin.size()];
		this.name=name;
		heuri = h;
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
	

	
	
	public Order(List<String> varsin, String[] varsout,OrderHeuristic h, String name) {
		varnames = varsin.toArray(new String[varsin.size()]);
		varpositions = new HashMap<>();
		permutation = new int[varsin.size()];
		this.name=name;
		heuri = h;
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
	
	
	
	
	
	public List<String> getVariables() {
		return Arrays.asList(varnames);
	}

	
	
	
	
	public List<String> getVariablesPermuted() {

		return Arrays.stream(varnames)
				.map(var -> permute(var))
				.collect(Collectors.toList());
	}


	
	
	public void printOrder(String path) throws IOException{
		PrintWriter out = new PrintWriter( new BufferedOutputStream(new FileOutputStream(path+"/"+name+"_"+heuri+".ord")));
		out.println("#TYPE "+name.replaceAll("-", "_")+"_flat");
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
	
//	public void listToFile(String nf,List<String> vars) {
//		BufferedWriter bw=null;
//		FileWriter fw=null;
//		try {		
//			fw = new FileWriter(nf+heuri+".ord");
//			bw = new BufferedWriter(fw);
//			bw.write(String.valueOf(vars.size()));
//			bw.newLine();
//			for(String var : vars) {
//				bw.write(var);
//				bw.newLine();
//			}
//		} catch (Exception ex){
//			ex.printStackTrace();
//		}
//		finally {
//			try {
//				if(bw!=null)
//					bw.close();
//				if(fw!=null)
//					fw.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	public String[] fileToList(String nf) {
//		BufferedReader br=null;
//		FileReader fr=null;
//		String[] vars=null;
//		try {		
//			fr = new FileReader(nf);
//			br = new BufferedReader(fr);
//			int size = Integer.valueOf(br.readLine());
//			vars = new String[size];
//			for (int i = 0; i < vars.length; i++) {
//				vars[i] = br.readLine();  
//			}
//		} catch (Exception ex){
//			ex.printStackTrace();
//		}
//		finally {
//			try {
//				if(br!=null)
//					br.close();
//				if(fr!=null)
//					fr.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		return vars;
//	}
}
