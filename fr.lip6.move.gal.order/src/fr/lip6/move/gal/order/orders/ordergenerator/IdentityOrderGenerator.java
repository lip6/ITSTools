package fr.lip6.move.gal.order.orders.ordergenerator;

import fr.lip6.move.gal.order.orders.order.IOrder;
import fr.lip6.move.gal.order.orders.order.OrderFactory;
import fr.lip6.move.gal.semantics.INextBuilder;

public class IdentityOrderGenerator implements IOrderGenerator {
	private INextBuilder inb;

	public IdentityOrderGenerator(INextBuilder inb) {
		this.inb = inb;
	}
	
	
	@Override
	public IOrder compute() {
		return OrderFactory.identity(inb.getVariableNames());
	}
}
