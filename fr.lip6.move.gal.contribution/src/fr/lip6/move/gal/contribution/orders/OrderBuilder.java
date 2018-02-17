package fr.lip6.move.gal.contribution.orders;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.semantics.INextBuilder;

//TODO les heuristiques ont besoin de Specification ou bien INextBuilder
public class OrderBuilder {

	private INextBuilder inb;
	
	public OrderBuilder(Specification spec) {
		inb = INextBuilder.build(spec);
	}
		
	public INextBuilder buildOrder () {
		return inb;
	}	
}
