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

/**
 * unary minus of an expression as in -(3)
 * @author Yann
 *
 */
public final class UnaryMinus extends NaryExpression {

	/**
	 * Ctor.
	 * @param expr child expression
	 */
	public UnaryMinus(IntegerExpression expr) {
		getChildren().add(expr);
	}

	/**
	 * {@inheritDoc}
	 */
	public int evaluate(IEvaluationContext context) {
		assert getChildren().size() == 1;
		return -getChildren().iterator().next().evaluate(context);
	}

}
