package fr.lip6.move.gal.order.orders.ordergeneratorbuilder;

import fr.lip6.move.gal.order.orders.IOrderHeuristic;
import fr.lip6.move.gal.order.orders.flag.OrderHeuristic;
import fr.lip6.move.gal.order.orders.ordergenerator.GspnOrderGenerator;
import fr.lip6.move.gal.order.orders.ordergenerator.IOrderGenerator;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.pnml.ptnet.PetriNet;

public class GspnRunner {
	
	private PetriNet p;
	private INextBuilder inb;

	public GspnRunner (INextBuilder inb) {
		//utilise gal2PetriNet
		//PetriNet p		
	}

	IOrderGenerator createGenerator (OrderHeuristic h) {
		//TODO vérifier que l'option h est bien un gspn
		//return new GspnOrderGenerator(inb, this, h);		
		return null;
	}	
}
