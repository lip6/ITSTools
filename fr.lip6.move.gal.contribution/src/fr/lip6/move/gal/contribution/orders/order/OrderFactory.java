package fr.lip6.move.gal.contribution.orders.order;

import java.util.Collection;



public class OrderFactory {

//	public static IOrder create(Collection<String> varsin, Collection<String> varsout) {
//		return new Order( varsin, varsout);
//	}
//	
//	public static IOrder create(Collection<String> varsin, String[] varsout) {
//		return new Order( varsin, varsout);
//	}
		
	static IOrder identity(Collection<String> vars) {
		return new Order(vars);
	}

	static IOrder fromValues(Collection<String> varsin, Collection<String> varsout) {
		return new Order(varsin, varsout);
	}
	
	public static IOrder fromValues(Collection<String> varsin, String[] varsout) {
		return new Order( varsin, varsout);
	}
}
