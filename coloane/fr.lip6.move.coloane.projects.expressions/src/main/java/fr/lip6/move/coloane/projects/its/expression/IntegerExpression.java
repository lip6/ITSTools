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
package fr.lip6.move.coloane.projects.its.expression;

import java.util.List;
import java.util.Set;

/**
 * A basic Composite + Interpret DP implem for integer arithmetic expressions involving variables.
 * @author Yann
 *
 */
public interface IntegerExpression {

	/**
	 * 
	 * @return children of this expression (can be empty)
	 */
	List<IntegerExpression> getChildren();

	/**
	 * Evaluate in the given context, which should provide values for every variable in the expression.
	 * @param context the context
	 * @return the integer value in this context
	 * throws NullPointerException bad stuff if the context is not OK: variables not set/not found
	 */
	int evaluate(IEvaluationContext context);

	/**
	 * Accessor to test if a context is OK.
	 * @return the set of variables in this expression.
	 */
	Set<IVariable> supportingVariables();
}
