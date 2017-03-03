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
 * Represents a pair variable/value, possibly unset as reflected by null values.
 * @author Yann
 *
 */
public interface IVariableBinding {
	/**
	 * The variable name
	 * @return the name
	 */
	String getVariableName();
	/**
	 * The integer value of the variable or null if not set.
	 * @return the value
	 */
	Integer getVariableValue();
	/**
	 * Update variable value (and possibly notify listeners)
	 * @param value new value or null to unset
	 */
	void setVariableValue(Integer value);
	/**
	 * Return an IVariable rather than a String
	 * @return the variable
	 */
	IVariable getVariable();
}
