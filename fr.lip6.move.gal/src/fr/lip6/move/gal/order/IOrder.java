package fr.lip6.move.gal.order;

import java.util.Set;

public interface IOrder {
	
	String getName();
	Set<String> getAllVars();
	<T> T accept (IOrderVisitor<T> v);
	IOrder clone();
}
