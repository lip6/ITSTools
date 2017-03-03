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

import fr.lip6.move.coloane.interfaces.formalism.IElementFormalism;
import fr.lip6.move.coloane.interfaces.model.IElement;


/**
 * An interface for syntax check rules
 * @author Yann
 *
 */
public interface ISyntaxRule {

	/** Check this rule on the provided element.
	 * 
	 * @param elt the elt to check
	 * @param result the result object to add any syntax errors to.
	 * @return true if rule passes
	 */
	boolean check(IElement elt, Result result);

	/** The name of this rule.
	 * e.g. "name uniqueness"
	 * 
	 * @return the name of this rule.
	 */
	String getName();

	/** The element type this rule applies to.
	 * e.g. "inhibitor Arc"
	 * @return element types this rule applies to.
	 */
	Iterable<IElementFormalism> getRuleTypes();

}
