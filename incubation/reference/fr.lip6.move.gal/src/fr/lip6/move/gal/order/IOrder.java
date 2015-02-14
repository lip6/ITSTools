package fr.lip6.move.gal.order;

import java.util.Set;

public interface IOrder {

	Set<String> getAllVars();
	<T> T accept (IOrderVisitor<T> v);
}
