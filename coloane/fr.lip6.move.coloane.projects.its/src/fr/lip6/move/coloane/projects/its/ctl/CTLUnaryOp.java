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
package fr.lip6.move.coloane.projects.its.ctl;

/**
 * A Composite for unary operators (e.g. NOT).
 * @author Yann
 *
 */
public abstract class CTLUnaryOp implements CTLFormula {
	
	private CTLFormula operand;

	/**
	 * OP a.
	 * @param operand a
	 */
	public CTLUnaryOp(CTLFormula operand) {
		this.operand = operand;
	}
	
	/**
	 * Returns the operand of this operator.
	 * @return the operand
	 */
	public final CTLFormula getOperand() {
		return operand;
	}
	
	/**
	 * Formats : OP (operand).
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		return getOperator() + " (" + getOperand() + ")";
	}

}
