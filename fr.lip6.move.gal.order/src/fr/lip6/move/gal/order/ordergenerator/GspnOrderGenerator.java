package fr.lip6.move.gal.order.ordergenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.greatspn.order.GreatSPNRunner;
import fr.lip6.move.gal.order.flag.OrderHeuristic;
import fr.lip6.move.gal.order.order.IOrder;
import fr.lip6.move.gal.order.order.OrderFactory;


/**
 * API global cmt s passe le code client ..
 * mesure sur certaines metric 
 * @author thamazgha
 *
 */

public class GspnOrderGenerator implements IOrderGenerator{//utilise gal2PetriNet
	
	
	private GreatSPNRunner runner;
	private List<String> vars;
	String workFolder, modelPath;
	private OrderHeuristic heuri;
	public GspnOrderGenerator(String workFolder, String modelPath, OrderHeuristic o,String binPath, List<String> vars) throws IOException {
		runner= new GreatSPNRunner(workFolder,modelPath,binPath);
		runner.configure(o.toString());
		heuri = o;
		this.vars = vars;
	}
	@Override
	public void configure(String path) {
		if (true)
			return;
		// TODO Auto-generated method stub
		List<String> res = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line,tmp,tmp2;


			while ((line = reader.readLine()) != null) {
				tmp2="";
				if (line.contains("<place")) {
					tmp2="";
					while(!((tmp2+="\n"+reader.readLine()).contains("</name>"))){
					
						
					}
//					System.out.println(line);
				}
				tmp = "";
				BufferedReader r = new BufferedReader(new StringReader(tmp2));
				
				while ((tmp = r.readLine()) != null) {
					if (tmp.contains("<text>")) {
						tmp=tmp.replace("<text>", "");
						tmp=tmp.replace(" ", "");
						tmp=tmp.replace("</text>", "");
						res.add(tmp);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("NOP");
		}
		vars = res;
	}

	@Override
	public IOrder compute() {
		// TODO Auto-generated method stub
		System.out.println(heuri);
		runner.run();
		return  OrderFactory.create(vars,runner.getOrder(),heuri);
		
	}
}
