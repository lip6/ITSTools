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
package fr.lip6.move.coloane.projects.its.flatten;

import fr.lip6.move.coloane.core.model.factory.FormalismManager;
import fr.lip6.move.coloane.core.model.factory.GraphModelFactory;
import fr.lip6.move.coloane.interfaces.exceptions.ModelException;
import fr.lip6.move.coloane.interfaces.formalism.IElementFormalism;
import fr.lip6.move.coloane.interfaces.formalism.IGraphFormalism;
import fr.lip6.move.coloane.interfaces.formalism.INodeFormalism;
import fr.lip6.move.coloane.interfaces.model.IArc;
import fr.lip6.move.coloane.interfaces.model.IAttribute;
import fr.lip6.move.coloane.interfaces.model.IGraph;
import fr.lip6.move.coloane.interfaces.model.INode;
import fr.lip6.move.coloane.projects.its.CompositeTypeDeclaration;


import java.util.HashMap;
import java.util.Map;

/**
 * An aborted attempt. Create a flat model from a circular tpye def.
 * @author Yann
 *
 */
public final class CircularToComposite {


	/** Build a composite from a Circular composite type Declaration, for a given size n.
	 * 
	 * @param ctd the composite type declaration, which **should be** a circular type def
	 * @param n the size in number of elements/instances of the circular type.
	 * @return a composite type declaration corresponding to the cicrular type.
	 */
	public CompositeTypeDeclaration buildComposite(CompositeTypeDeclaration ctd, int n) {
		IGraph resultGraph = new GraphModelFactory().createGraph(FormalismManager.getInstance().getFormalismByName("ITSComposite"));

		IGraph entry = ctd.getGraph();

		for (IAttribute a : entry.getAttributes()) {
			resultGraph.getAttribute(a.getName()).setValue(a.getValue());
		}
		// ok, so we should have two instances, called "current" and "successor"
		// and a set of synchronizations linking current and succ.

		// id mappings : map an instance index to a node id i the
		// composite we are building
		Map<Integer, Integer> ids = new HashMap<Integer, Integer>();
		try {
			// first build the $n$ instances of "type" in the result
			for (int i = 0; i < n; i++) {
				String instName = "inst_" + i;
				INode node = resultGraph.createNode((INodeFormalism) resultGraph.getFormalism().getRootGraph().getElementFormalism("instance"));

				ids.put(i, node.getId());
				node.getAttribute("name").setValue(instName);
				node.getAttribute("type").setValue("Type");
			}

			// build n instances circularly connecting out n instances.

			// grab the "sync" formalism element id
			IGraphFormalism graph = entry.getFormalism().getRootGraph();
			IElementFormalism sync = graph.getElementFormalism("synchronization");
			for (INode node : entry.getNodes()) {
				// for each synchronization in the original model,
				if (node.getNodeFormalism().equals(sync)) {
					// build n instances circularly connecting the n instances in the result model.

					for (int i = 0; i < n; i++) {
						INode cur = resultGraph.getNode(ids.get(i));
						INode succ = resultGraph.getNode(ids.get((i + 1) % n));

						INode resSync = resultGraph.createNode((INodeFormalism) graph.getElementFormalism("synchronization"));
						// copy name label etc...
						for (IAttribute a : node.getAttributes()) {
							resSync.getAttribute(a.getName()).setValue(a.getValue());
						}
						// copy arcs
						for (IArc a : node.getIncomingArcs()) {
							IArc resArc;
							if ("current".equals(a.getSource().getAttribute("name").getValue())) {
								resArc = resultGraph.createArc(a.getArcFormalism(), cur, resSync);
							} else {
								resArc = resultGraph.createArc(a.getArcFormalism(), succ, resSync);
							}
							// copy name label etc...
							for (IAttribute att : node.getAttributes()) {
								resArc.getAttribute(att.getName()).setValue(att.getValue());
							}
						}
						for (IArc a : node.getOutgoingArcs()) {
							IArc resArc;
							if ("current".equals(a.getTarget().getAttribute("name").getValue())) {
								resArc = resultGraph.createArc(a.getArcFormalism(), cur, resSync);
							} else {
								resArc = resultGraph.createArc(a.getArcFormalism(), succ, resSync);
							}
							// copy name label etc...
							for (IAttribute att : node.getAttributes()) {
								resArc.getAttribute(att.getName()).setValue(att.getValue());
							}
						}
					}
				}
			}
		} catch (ModelException e) {
			e.printStackTrace();
		}
		CompositeTypeDeclaration newCtd = new CompositeTypeDeclaration("ITSCircularComposite", null, resultGraph, ctd.getTypeList());
		return newCtd;
	}
}
