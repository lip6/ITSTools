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
 * A variable predicate over a system variable.
 * "a = 3, a.P1.tab[2].X < 5 etc..."
 * @author Yann
 *
 */
public final class CTLVariablePredicate implements CTLFormula {

	private String variable;
	private String comparator;
	private String value;

	/**
	 * var comp value.
	 * @param var the variable fully qualified name.
	 * @param comp the comparison used
	 * @param value the value to compare against.
	 */
	public CTLVariablePredicate(String var, String comp, String value) {
		this.variable = var;
		this.comparator = comp;
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOperator() {
		return CTLFormula.PREDICATE;
	}

	/**
	 * var comp value.
	 * {@inheritDoc}
	 */
	public String toString() {
		return variable + " " + comparator + " " + value;
	}
}
