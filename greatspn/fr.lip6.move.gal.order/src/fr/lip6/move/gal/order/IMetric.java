package fr.lip6.move.gal.order;

import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.order.order.IOrder;

public interface IMetric {
	public double compute (INextBuilder inb, IOrder order);
}
