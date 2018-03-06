package fr.lip6.move.gal.contribution.orders.ordergenerator;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.contribution.orders.PTGALTransformer;
import fr.lip6.move.gal.contribution.orders.order.IOrder;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.pnml.ptnet.PetriNet;

/**
 * API global cmt s passe le code client ..
 * mesure sur certaines metric 
 * @author thamazgha
 *
 */

public class GspnOrderGenerator implements IOrderGenerator{//utilise gal2PetriNet
	
	
	 
	
	
	public INextBuilder configure (PetriNet petriNet, String path) {
		return null;
	}

	@Override
	public void configure(INextBuilder inb, Specification spec) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IOrder compute() {
		// TODO Auto-generated method stub
		return null;
	}
}
