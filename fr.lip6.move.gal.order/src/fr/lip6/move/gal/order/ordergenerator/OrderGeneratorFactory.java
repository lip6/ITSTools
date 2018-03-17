package fr.lip6.move.gal.order.ordergenerator;

import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.order.flag.OrderHeuristic;

//


public class OrderGeneratorFactory {
	public static IOrderGenerator build (OrderHeuristic oh,String workFolder, String modelPath,String binPath) {
		//config interface pour recup choix du runner + heuri
			return new GspnOrderGenerator(workFolder, modelPath,oh,binPath);
		
	}
	public static List<IOrderGenerator> build (List<OrderHeuristic> ohs,String workFolder, String modelPath,String binPath) {
		//config interface pour recup choix du runner + heuri
		List<IOrderGenerator> res = new ArrayList<>();
		for(OrderHeuristic o : ohs)
			res.add(new GspnOrderGenerator(workFolder, modelPath,o,binPath));
		return res;
		
	}
}
