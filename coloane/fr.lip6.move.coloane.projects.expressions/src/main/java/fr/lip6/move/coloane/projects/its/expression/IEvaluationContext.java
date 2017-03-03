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

import java.util.Collection;
import java.util.Set;

/**
 * An interface to specify a binding of variables to integer values.
 * @author Yann
 *
 */
public interface IEvaluationContext {

	/**
	 * Set a declared variable's value
	 * Side effect : notify observers.
	 * @param var the variable
	 * @param value the value
	 */
	void setVariableValue(IVariable var, Integer value);

	/**
	 * Returns the current value of a variable.
	 * @param var the variable
	 * @return the value or null if not set
	 */
	Integer getVariableValue(IVariable var);

	/** 
	 * The set of declared variables.
	 * @return The set of declared variables.
	 */
	Set<IVariable> getVariables();

	/**
	 * Declare a variable, mandatory before setVarVal
	 * @param var the variable
	 */
	void declareVariable(IVariable var);

	/**
	 * Accessor : builds a set of objects + attach notifications.
	 * @return a set of mappings var to value.
	 */
	Collection<IVariableBinding> getBindings();

	/**
	 * Test whether a variable is declared.
	 * @param var a variable
	 * @return true if the var is declared.
	 */
	boolean containsVariable(IVariable var);
}
