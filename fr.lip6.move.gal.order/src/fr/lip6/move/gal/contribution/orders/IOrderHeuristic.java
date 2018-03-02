package fr.lip6.move.gal.contribution.orders;

import fr.lip6.move.gal.semantics.INextBuilder;

//TODO RANDOM ORDER GENERATOR
//TODO REVERSE ORDER GENERATOR
//TODO Order Generator builder

//TODO ne permet pas de composer les heuristiques
public interface IOrderHeuristic {
	IOrder computeReordering(INextBuilder inb);
}
