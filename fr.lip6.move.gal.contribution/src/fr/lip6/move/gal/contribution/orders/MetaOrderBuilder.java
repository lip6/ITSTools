package fr.lip6.move.gal.contribution.orders;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.semantics.INextBuilder;

public class MetaOrderBuilder {
	
	public IOrder findBestOrder(Specification spec) {
		// Retrieve order from spec
		OrderBuilder ob = new OrderBuilder(spec);		
		INextBuilder inb = ob.buildOrder();

		// Compute scores of orders
		//TODO remplir cette liste
		List<IOrderHeuristic> heuristics = new LinkedList<>();
		heuristics.add(new IdentityHeuristic());

		TreeMap<IOrder, Double> orderToMetric = new TreeMap<>();
		
		Metric metric = new Metric();

		for (IOrderHeuristic heuristic : heuristics) {
			IOrder o = heuristic.computeReordering(inb);
			double m = metric.compute(inb, o);
			
			orderToMetric.put(o, m);
		}
		
		//TODO compositions HERE
		
		return orderToMetric.lastKey();
	}
	
	//TODO est ce qu'il y a un meilleur endroit pour le mettre
	Specification applyReordering(Specification spec, IOrder order) {
		return spec;
		//TODO reconstruire la specification en permutant l'ordre des variables
		//TODO est ce nécessaire, ou bien peut on se contenter de l'ordre ?
	}
}
