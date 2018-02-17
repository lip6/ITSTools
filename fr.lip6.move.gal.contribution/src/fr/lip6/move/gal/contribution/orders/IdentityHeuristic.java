package fr.lip6.move.gal.contribution.orders;

import fr.lip6.move.gal.semantics.INextBuilder;

public class IdentityHeuristic implements IOrderHeuristic {

	@Override
	public IOrder computeReordering(INextBuilder inb) {
		return OrderFactory.identity(inb.getVariableNames());
	}
}
