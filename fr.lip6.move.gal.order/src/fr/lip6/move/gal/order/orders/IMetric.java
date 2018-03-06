package fr.lip6.move.gal.contribution.orders;

import fr.lip6.move.gal.contribution.orders.order.IOrder;
import fr.lip6.move.gal.semantics.INextBuilder;

public interface IMetric {
	public double compute (INextBuilder inb, IOrder order);
}
