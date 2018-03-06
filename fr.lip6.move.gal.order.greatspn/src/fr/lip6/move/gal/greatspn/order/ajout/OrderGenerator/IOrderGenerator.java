package fr.lip6.move.gal.greatspn.order.ajout.OrderGenerator;

import fr.lip6.move.gal.greatspn.order.ajout.order.IOrder;
import fr.lip6.move.gal.semantics.INextBuilder;

public interface IOrderGenerator {
	
	public void configure(INextBuilder nb);
	public IOrder compute();

}
