package fr.lip6.move.gal.contribution.orders;

import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.pnml.ptnet.PetriNet;

/**
 * API global cmt s passe le code client ..
 * mesure sur certaines metric 
 * @author thamazgha
 *
 */

public interface GspnOrderGenerator extends IOrderGenerator{//utilise gal2PetriNet
	INextBuilder configure (PetriNet p);
}
