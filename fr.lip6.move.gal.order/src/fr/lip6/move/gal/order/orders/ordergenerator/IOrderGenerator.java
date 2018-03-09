package fr.lip6.move.gal.order.orders.ordergenerator;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.order.orders.order.IOrder;
import fr.lip6.move.gal.semantics.INextBuilder;

public interface IOrderGenerator {
	IOrder compute();
}
