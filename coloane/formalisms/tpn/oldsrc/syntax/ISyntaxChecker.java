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
package fr.lip6.move.coloane.projects.its.syntax;

import fr.lip6.move.coloane.interfaces.model.IGraph;
import fr.lip6.move.coloane.interfaces.objects.result.IResult;


/** Defines an interface for a formalism specific syntax checker. */
public interface ISyntaxChecker {

	/** The main entry point for syntax checking.
	 * 
	 * @param graph the graph to check
	 * @return an IResult to feed the results view
	 */
	IResult check(IGraph graph);

	/** Return the formalism this syntax checker applies to.
	 * 
	 * @return the formalism this syntax checker is designed to check
	 */
	String getFormalism();

	/** Add a syntax check rule to this Syntax checker
	 * 
	 * @param rule the rule to add.
	 */
	void addRule(ISyntaxRule rule);

}
