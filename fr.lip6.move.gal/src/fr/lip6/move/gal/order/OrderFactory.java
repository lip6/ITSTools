package fr.lip6.move.gal.order;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.support.Support;

public class OrderFactory {

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	
	public static IOrder readOrder (Specification spec) throws IOException {
		
		TypeDeclaration tmain = spec.getMain();
		
		if (tmain == null) {
			tmain = spec.getTypes().get(0);
		}
		return readOrder (tmain, "main");
	}
	
	public static IOrder readOrder(TypeDeclaration tmain, String name) {
		if (tmain instanceof CompositeTypeDeclaration) {
			CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) tmain;
			return readCTDOrder(ctd);
		} else if (tmain instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) tmain;
			return readGalOrder(gal, name);
		}
		getLog().warning("Could not handle type in readOrder.");
		return null;
	}

	private static IOrder readGalOrder(GALTypeDeclaration gal, String name) {
		Support supp = new Support();
		for (Variable var : gal.getVariables()) {
			supp.add(var);
		}
		for (ArrayPrefix ap : gal.getArrays()) {
			supp.addAll(GF2.createArrayVarAccess(ap, GF2.constant(0)));
		}
		return new VarOrder(supp , name);
	}


	private static IOrder readCTDOrder(CompositeTypeDeclaration ctd) {
		
		
		return null;
	}


	public static IOrder parseOrder (String filename, int psize) throws IOException {
		
		Scanner sc;
		// erreur si le fichier n'existe pas
		sc = new Scanner(new File(filename));
		
		Map<String,IOrder> ordMap= new HashMap<String, IOrder>();
		for (int i = 0; i < psize ; i++) {
			ordMap.put("GENE"+i+"X", new VarOrder(Collections.singletonList("i"+i), "GENE"+i+"X"));
		}
		String last=null;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] words = line.split("\\s");
			assert(words.length==4);
			ordMap.put(words[0], new CompositeGalOrder(Arrays.asList(new IOrder[] {ordMap.get(words[1]),ordMap.get(words[2])}),words[0]));
			last = words[0];
		}		
		sc.close();
		
		return ordMap.get(last);
	}
	
	public static IOrder parseLouvain (String filename, List<String> pnames, boolean rec) throws IOException {
		
		Scanner sc;
		// erreur si le fichier n'existe pas
		sc = new Scanner(new File(filename));
		
		List<List<String>> elements = new ArrayList<>();
		
		int i=0;
		while (sc.hasNextLine() && i < pnames.size()) {
			String line = sc.nextLine();
			String[] words = line.split("\\s");
			assert(words.length==2);
			int var = Integer.parseInt(words[0]);
			int part = Integer.parseInt(words[1]);
			while (part >= elements.size()) {
				elements.add(new ArrayList<>());
			}
			List<String> target = elements.get(part);
			target.add(pnames.get(var));
			
			i++;
		}		
		List<IOrder> subs = new ArrayList<>();
		for (i=0 ; i < elements.size() ; i++) {
			subs.add(new VarOrder(elements.get(i), "u"+i));
		}
		if (rec) {
			i=0;
			while (sc.hasNextLine())
				i=parseRec(sc, subs,i);						
		}		
		sc.close();
		
		return new CompositeGalOrder(subs, "glob");
	}


	private static int parseRec(Scanner sc, List<IOrder> subs, int last) {
		int i;
		List<List<IOrder>> newsubs = new ArrayList<>();
		i=0;
		while (sc.hasNextLine() && i < subs.size()) {
			String line = sc.nextLine();
			String[] words = line.split("\\s");
			assert(words.length==2);
			int var = Integer.parseInt(words[0]);
			int part = Integer.parseInt(words[1]);
			while (part >= newsubs.size()) {
				newsubs.add(new ArrayList<>());
			}
			List<IOrder> target = newsubs.get(part);
			target.add(subs.get(var));
			
			i++;
		}
		subs.clear();
		i=last;
		for (List<IOrder> list : newsubs) {				
			subs.add(new CompositeGalOrder(list, "c"+i));
			i++;
		}
		return i;
	}
}
