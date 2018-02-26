package fr.lip6.move.gal.contribution.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.contribution.orders.LTSMinOrderRunner;
import fr.lip6.move.gal.contribution.ui.handlers.OrderHandler;
import fr.lip6.move.gal.semantics.INextBuilder;

public class AllVariables extends OrderHandler {


	@Override
	protected String getServiceName() {		
		return "Contribution";
	}

	@Override
	public void workOnSpec(Specification s, String outfolder) throws Exception {
		
		LTSMinOrderRunner runner = new LTSMinOrderRunner();
		
		
		runner.configure(s,outfolder);
		
		// get an order
		INextBuilder nb = INextBuilder.build(s);
		List<String> order = nb.getVariableNames();
		
		// récupérer un vrai ordre
		Collections.shuffle(order);
		
		
		runner.configure(order,nb);
		
		runner.run();
		
		runner.join();
		
		System.out.println("L'ordre est : "+order);
		
		try {
			String path = "/home/osboxes/test.txt"; //à modif en fonction de chacun
			File f = new File(path); 
			FileWriter fw = new FileWriter(f, true);
			fw.write("\nThe order is : "+order.toString());
			System.out.println("Order written in : "+path);
			fw.close();
		}
		catch(IOException ioe) {
			System.out.println("Erreur IO");
			ioe.printStackTrace();
		}
		
		System.out.println("content");
	}

}
