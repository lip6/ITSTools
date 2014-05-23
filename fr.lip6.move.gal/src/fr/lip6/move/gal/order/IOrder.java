package fr.lip6.move.gal.order;

import java.util.Set;

public interface IOrder {

	Set<String> getAllVars();
	<T> void accept (IOrderVisitor<T> v);
}
