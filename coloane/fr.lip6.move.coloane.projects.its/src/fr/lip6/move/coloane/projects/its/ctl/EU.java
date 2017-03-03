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
 * Exists a Until b.
 * @author Yann
 *
 */
public final class EU extends CTLBinaryOp {

	/**
	 * E a U b.
	 * @param left a
	 * @param right b
	 */
	public EU(CTLFormula left, CTLFormula right) {
		super(left, right);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOperator() {
		return CTLFormula.EU;
	}

	/**
	 * Manual override to produce : E ( (a) U (b) ).
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "E ( (" + getLeft() + ") U (" + getRight() + ") )";
	}

	
}
