package fr.lip6.move.gal.order.order;

import java.util.Collection;
import java.util.List;

import fr.lip6.move.gal.order.flag.OrderHeuristic;




public class OrderFactory {

//	public static IOrder create(Collection<String> varsin, Collection<String> varsout) {
//		return new Order( varsin, varsout);
//	}
//	
//	public static IOrder create(Collection<String> varsin, String[] varsout) {
//		return new Order( varsin, varsout);
//	}
		
	public static IOrder create(List<String> varsin, List<String> varsout,OrderHeuristic h) {
		return new Order( varsin, varsout,h);
	}
	
	public static IOrder create(List<String> varsin, String[] varsout,OrderHeuristic h) {
		return new Order( varsin, varsout,h);
	}
	
	public static IOrder identity(List<String> varsin,OrderHeuristic h) {
		return new Order(varsin,h);
	}
}
