package fr.lip6.move.gal.contribution.orders;

import java.util.Collection;

public class OrderFactory {
	
	static IOrder identity(Collection<String> vars) {
		return new Order(vars);
	}

	static IOrder fromValues(Collection<String> varsin, Collection<String> varsout) {
		return new Order(varsin, varsout);
	}
}
