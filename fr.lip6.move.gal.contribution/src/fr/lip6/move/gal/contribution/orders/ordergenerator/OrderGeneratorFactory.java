package fr.lip6.move.gal.contribution.orders.ordergenerator;

import fr.lip6.move.gal.contribution.orders.flag.OrderHeuristic;

public class OrderGeneratorFactory {
	public static IOrderGenerator build (OrderHeuristic oh,String workFolder, String modelPath) {
		//config interface pour recup choix du runner + heuri
		switch (oh) {
		case F:
			return new GreatSPNOrderGen(workFolder, modelPath);
		case FR:
			new GreatSPNOrderGen(workFolder, modelPath, oh);
		default:
			return new GreatSPNOrderGen(workFolder, modelPath);
		}
	}
}
