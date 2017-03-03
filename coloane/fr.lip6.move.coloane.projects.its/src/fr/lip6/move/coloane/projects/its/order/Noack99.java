/**
 * Copyright (c) 2006-2010 MoVe - Laboratoire d'Informatique de Paris 6 (LIP6).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jean-Baptiste VORON (LIP6) - Project Head / Initial contributor
 *   Clément DÉMOULINS (LIP6) - Project Manager
 *   Yann THIERRY-MIEG (LIP6)
 *
 * Official contacts:
 *   coloane@lip6.fr
 *   http://coloane.lip6.fr
 */
package fr.lip6.move.coloane.projects.its.order;

import fr.lip6.move.coloane.interfaces.formalism.IElementFormalism;
import fr.lip6.move.coloane.interfaces.model.IArc;
import fr.lip6.move.coloane.interfaces.model.IGraph;
import fr.lip6.move.coloane.interfaces.model.INode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Implementation of the ordering heuristic proposed in Noack'99,
 * as described in
 * A. Tovchigrechko: Efficient symbolic analysis of bounded Petri nets using
 *  interval decision disgrams (submitted version), BTU Cottbus, Ph. D. Thesis, October 2008
 * 
 * Essentially the algorithm consists in defining a weight for each place:
 * 
 * w(p) =  (sum_(t touches p)  card(places touching t in S) / card(places touching t)) / card(transitions touching p)
 * 
 * @author Yann
 *
 */
public final class Noack99 implements IOrderingHeuristic {

	/**
	 * {@inheritDoc}
	 */
	public List<Integer> computeOrder(IGraph graph) {
		List<Integer> order = new ArrayList<Integer>();

		// grab the set of places = variables in the input model
		List<INode> places = new ArrayList<INode>();

		IElementFormalism place = graph.getFormalism().getRootGraph().getElementFormalism("place");
		for (INode node : graph.getNodes()) {
			if (node.getNodeFormalism().equals(place)) {
				places.add(node);
			}
		}

		// now, while there are places to add to the order
		// The set S of nodes already put in the order
		Set<INode> treatedNodes = new HashSet<INode>();

		// find the heaviest place in the set
		while (!places.isEmpty()) {
			double max = -1;
			int imax = -1;
			for (int i = 0; i < places.size(); i++) {
				INode pl = places.get(i);
				double w = getWeight(pl, treatedNodes);
				if (w > max) {
					imax = i;
					max = w;
				}
			}
			INode maxP = places.get(imax);
			// add it to the treated set
			treatedNodes.add(maxP);
			// add it to the order
			order.add(maxP.getId());
			// remove it from "places"
			places.remove(imax);
		}

		return order;
	}

	/**
	 * The weight of a place, knowing that the nodes in s are already placed.
	 * @param place the place to test
	 * @param s the nodes already placed
	 * @return the weight metric
	 */
	private double getWeight(INode place, Set<INode> s) {
		double result = 0;
		int cardTouchP = 0;
		// foreach t in pre(p)
		for (IArc arc : place.getIncomingArcs()) {
			INode t = arc.getSource();
			double cardT = 0;
			double cardNotInS = 0;
			cardTouchP++;

			// foreach p' in pre(t)
			for (IArc preArc : t.getIncomingArcs()) {
				INode preT = preArc.getSource();
				cardT++;
				if (!s.contains(preT)) {
					cardNotInS++;
				}
			}
			if (cardT != 0) {
				result += cardNotInS / cardT;
			}
		}

		// foreach t in post(p)
		for (IArc arc : place.getOutgoingArcs()) {
			INode t = arc.getTarget();
			double cardT = 0;
			double cardNotInS = 0;
			cardTouchP++;

			// foreach p' in pre(t)
			for (IArc postArc : t.getOutgoingArcs()) {
				INode postT = postArc.getTarget();
				cardT++;
				if (!s.contains(postT)) {
					cardNotInS++;
				}
			}
			if (cardT != 0) {
				result += cardNotInS / cardT;
			}
		}
		if (cardTouchP != 0) {
			result /= cardTouchP;
		}
		return result;
	}
}

