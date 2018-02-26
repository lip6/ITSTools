package fr.lip6.move.gal.contribution.orders;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.semantics.INextBuilder;

public interface IOrderGenerator {
	void configure(INextBuilder inb, Specification spec);// spec ??? on la vire ou pas ?
	IOrder compute();
}
