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
package fr.lip6.move.coloane.core.formalisms.its;

import fr.lip6.move.coloane.core.model.factory.GraphModelFactory;
import fr.lip6.move.coloane.interfaces.exceptions.ModelException;
import fr.lip6.move.coloane.interfaces.exceptions.PluginException;
import fr.lip6.move.coloane.interfaces.extensions.IExample;
import fr.lip6.move.coloane.interfaces.formalism.IFormalism;
import fr.lip6.move.coloane.interfaces.formalism.INodeFormalism;
import fr.lip6.move.coloane.interfaces.model.IGraph;
import fr.lip6.move.coloane.interfaces.model.INode;

import org.eclipse.draw2d.geometry.Point;

public class CircularEmptyModel implements IExample {

	public IGraph buildModel(IFormalism formalism) throws PluginException {
		IGraph graph = new GraphModelFactory().createGraph(formalism);
		try {
			INode node = graph.createNode((INodeFormalism) formalism.getRootGraph().getElementFormalism("instance"));
			node.getGraphicInfo().setLocation(new Point(100, 100));
			node.getAttribute("name").setValue("current");
			node.getAttribute("type").setValue("Type");
			
			node = graph.createNode((INodeFormalism) formalism.getRootGraph().getElementFormalism("instance"));
			node.getGraphicInfo().setLocation(new Point(200, 100));
			node.getAttribute("name").setValue("successor");
			node.getAttribute("type").setValue("Type");
			
		} catch (ModelException e) {
			e.printStackTrace();
		}
		return graph;
	}

}
