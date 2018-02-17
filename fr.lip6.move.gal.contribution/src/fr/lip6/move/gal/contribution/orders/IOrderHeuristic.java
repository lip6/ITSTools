package fr.lip6.move.gal.contribution.orders;

import fr.lip6.move.gal.semantics.INextBuilder;

//TODO ne permet pas de composer les heuristiques
public interface IOrderHeuristic {
	IOrder computeReordering(INextBuilder inb);
}
