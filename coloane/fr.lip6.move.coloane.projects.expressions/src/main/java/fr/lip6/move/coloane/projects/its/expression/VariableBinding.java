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
 * A class implementing a variable binding built to work with the EvaluationContext implementation.
 * No data is stored, it delegates updates on the context.
 * @author Yann
 *
 */
public final class VariableBinding implements IVariableBinding {

	private EvaluationContext context;
	private IVariable var;

	/**
	 * Create a binding for var, get/set the value in the provided context.
	 * @param var the variable
	 * @param evaluationContext the home context
	 */
	public VariableBinding(IVariable var, EvaluationContext evaluationContext) {
		this.context = evaluationContext;
		this.var = var;
	}


	/**
	 * @return the variable name
	 */
	public String getVariableName() {
		return var.getName();
	}

	/**
	 * @return the variable value or null if not set
	 */
	public Integer getVariableValue() {
		return context.getVariableValue(var);
	}

	/**
	 * Update the context with new value
	 * @param value the new value or null to unset
	 */
	public void setVariableValue(Integer value) {
		context.setVariableValue(var, value);
	}

	/**
	 * {@inheritDoc}
	 */
	public IVariable getVariable() {
		return var;
	}
}
