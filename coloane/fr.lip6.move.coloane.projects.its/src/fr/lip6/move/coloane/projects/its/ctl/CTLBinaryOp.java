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
 * Implement a binary Composite pattern.
 * @author Yann
 *
 */
public abstract class CTLBinaryOp implements CTLFormula {
	
	private CTLFormula left;
	private CTLFormula right;

	/**
	 * a OP b
	 * @param left a
	 * @param right b
	 */
	public CTLBinaryOp(CTLFormula left, CTLFormula right) {
		this.left = left;
		this.right = right;
	}
	
	/**
	 * The left operand.
	 * @return left operand
	 */
	public final CTLFormula getLeft() {
		return left;
	}
	/**
	 * The right operand.
	 * @return right operand
	 */
	public final CTLFormula getRight() {
		return right;
	}
}
