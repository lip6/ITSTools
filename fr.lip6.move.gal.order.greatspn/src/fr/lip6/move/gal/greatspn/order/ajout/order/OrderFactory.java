package fr.lip6.move.gal.greatspn.order.ajout.order;


import java.util.List;

public class OrderFactory {

	public static IOrder create(List<String> varsin, List<String> varsout) {
		return new Order( varsin, varsout);
	}
	
	public static IOrder create(List<String> varsin, String[] varsout) {
		return new Order( varsin, varsout);
	}
	
	public static IOrder identity(List<String> varsin) {
		return new Order(varsin);
	}
		
}
