package fr.lip6.move.gal.order;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrderFactory {

	
	public static IOrder parseOrder (String filename, int psize) throws IOException {
		
		Scanner sc;
		// erreur si le fichier n'existe pas
		sc = new Scanner(new File(filename));
		
		IOrder order = null;
		Map<String,IOrder> ordMap= new HashMap<String, IOrder>();
		for (int i = 0; i < psize ; i++) {
			ordMap.put("GENE"+i+"X", new VarOrder(Collections.singletonList("i"+i)));
		}
		String last=null;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] words = line.split("\\s");
			assert(words.length==4);
			ordMap.put(words[0], new CompositeGalOrder(Arrays.asList(new IOrder[] {ordMap.get(words[1]),ordMap.get(words[2])})));
			last = words[0];
		}		
		sc.close();
		
		return ordMap.get(last);
	}
}
