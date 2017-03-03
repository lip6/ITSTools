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
 * TRUE or FALSE.
 * @author Yann
 *
 */
public final class CTLConstant implements CTLFormula {
	
	private int value;

	public static final int FALSE = 0;	
	public static final int TRUE = 1;
	public static final int DEAD = 2;
	
	/**
	 * TRUE or FALSE.
	 * @param b value
	 */
	CTLConstant(int b) {
		this.value = b;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOperator() {
		return CTLFormula.CONSTANT;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		switch (value) {
		case TRUE:
			return "TRUE";			
		case FALSE:
			return "FALSE";			
		case DEAD:
			return "DEADLOCK";
		default:
			return "??Constant??";
		}
	}
}
