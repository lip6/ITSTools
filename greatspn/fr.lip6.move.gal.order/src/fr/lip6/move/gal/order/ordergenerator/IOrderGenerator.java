package fr.lip6.move.gal.order.ordergenerator;

import fr.lip6.move.gal.order.order.IOrder;

public interface IOrderGenerator {
//	void configure(String path);// spec ??? on la vire ou pas ?
	IOrder compute();
}
