package fr.lip6.move.gal.order.orders;

import fr.lip6.move.gal.order.orders.order.IOrder;
import fr.lip6.move.gal.semantics.INextBuilder;

public interface IMetric {
	public double compute (INextBuilder inb, IOrder order);
}
