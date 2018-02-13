package fr.lip6.move.gal.contribution.orders;

import java.util.List;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.contribution.orders.OrderBuilder.OrderHeuristique;

public class MetaOrderBuilder {

	public List<String> findBestOrder(Specification spec) {
		// Retrieve order from spec
		OrderBuilder ob = new OrderBuilder();

		// Find best score order
		List<String> bestOrder = ob.buildOrder(spec);
		double bestScore = Scoring.score(spec, bestOrder);

		for (OrderHeuristique f : OrderHeuristique.values()) {
			List<String> order = ob.applyHeuristic(spec, bestOrder, f);
			double score = Scoring.score(spec, order);
			
			if (score > bestScore) {
				bestOrder = order;
				bestScore = score;
			}
		}
		
		return bestOrder;
	}
}
