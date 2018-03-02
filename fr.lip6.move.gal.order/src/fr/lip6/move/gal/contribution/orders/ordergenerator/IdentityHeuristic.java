package fr.lip6.move.gal.contribution.orders.ordergenerator;

import fr.lip6.move.gal.contribution.orders.IOrderHeuristic;
import fr.lip6.move.gal.contribution.orders.order.IOrder;
import fr.lip6.move.gal.contribution.orders.order.OrderFactory;
import fr.lip6.move.gal.semantics.INextBuilder;

public class IdentityHeuristic implements IOrderHeuristic {

	@Override
	public IOrder computeReordering(INextBuilder inb) {
		return OrderFactory.identity(inb.getVariableNames());
	}
}
