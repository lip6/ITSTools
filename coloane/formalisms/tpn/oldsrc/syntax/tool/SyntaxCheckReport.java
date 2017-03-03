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
package fr.lip6.move.coloane.projects.its.syntax.tool;

import fr.lip6.move.coloane.core.motor.session.SessionManager;
import fr.lip6.move.coloane.core.results.ResultTreeImpl;
import fr.lip6.move.coloane.core.results.reports.IReport;
import fr.lip6.move.coloane.interfaces.model.IArc;
import fr.lip6.move.coloane.interfaces.model.IElement;
import fr.lip6.move.coloane.interfaces.model.INode;
import fr.lip6.move.coloane.interfaces.objects.result.IResult;
import fr.lip6.move.coloane.interfaces.objects.result.ISubResult;
import fr.lip6.move.coloane.projects.its.syntax.Result;

import java.util.HashMap;
import java.util.Map;


public class SyntaxCheckReport implements IReport {

	public ResultTreeImpl build(IResult res) {
		// 1. Build the root of the result tree
		ResultTreeImpl root = new ResultTreeImpl("Syntax Check");
		// 2. Attach the session Manager to the root
		root.setSessionManager(SessionManager.getInstance());			

		Map<Integer,ResultTreeImpl> resPerObject = new HashMap<Integer, ResultTreeImpl>();
		if (res instanceof Result) {
			if (res.getSubResults().isEmpty()) {
				root.addChild(new ResultTreeImpl("Syntax check success!"));
				return root;
			}
			for (ISubResult sub : res.getSubResults()) {
				// Create a node result
				int id = sub.getObjectsOutline().get(0);
				ResultTreeImpl node;
				if (resPerObject.containsKey(id)) {
					node = resPerObject.get(id);
				} else {
					IElement element = root.getSessionManager().getCurrentSession().getGraph().getObject(id);
					if ((element != null) && (element instanceof INode)) {
						INode inode = (INode) element;
						String name;
						if (element.getAttribute("name") != null) {
							name = element.getAttribute("name").getValue();
						} else if (element.getAttribute("label") != null) {
							name = element.getAttribute("label").getValue();
						} else {
							name = "";
						}
					node = new ResultTreeImpl(sub.getObjectsOutline(), inode.getNodeFormalism().getName(), name);
					} else if ((element != null) && (element instanceof IArc)) {
						IArc ia = (IArc) element;
						node = new ResultTreeImpl(sub.getObjectsOutline(),
								ia.getArcFormalism().getName(),
								ia.getSource().getAttribute("name").getValue(),
								"->",
								ia.getTarget().getAttribute("name").getValue());				
					} else {
						node = new ResultTreeImpl(sub.getObjectsOutline());
					}
					resPerObject.put(id,node);
					root.addChild(node);
				}
				String attribute = sub.getAttributesOutline().entrySet().iterator().next().getValue().get(0);
				node.addChild(new ResultTreeImpl(sub.getObjectsOutline(),attribute,sub.getName(),sub.getTextualResults().get(0)));
			}		
		}
		return root;
	}


}
