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

import fr.lip6.move.coloane.projects.its.obs.SimpleObservable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A basic implem of an {@link IEvaluationContext}. Also implements {@link SimpleObservable}.
 * @author Yann
 *
 */
public final class EvaluationContext extends SimpleObservable implements IEvaluationContext {

	private Map<IVariable, Integer> values;
	private Set<IVariable> variables;
	private List<IVariableBinding> vbs;

	/**
	 * Ctor.
	 */
	public EvaluationContext() {
		variables = new HashSet<IVariable>();
		values = new HashMap<IVariable, Integer>();
	}
	/**
	 * {@inheritDoc}
	 */
	public Integer getVariableValue(IVariable var) {
		return values.get(var);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public void setVariableValue(IVariable var, Integer value) {
		if (!variables.contains(var)) {
			declareVariable(var);
		}
		if (values.get(var) != value) {
			values.put(var, value);
			notifyObservers();
		}
	}
	/**
	 * {@inheritDoc}
	 */
	public void declareVariable(IVariable var) {
		variables.add(var);
		vbs = null;
	}
	/**
	 * {@inheritDoc}
	 */
	public Set<IVariable> getVariables() {
		return variables;
	}
	/**
	 * {@inheritDoc}
	 */
	public Collection<IVariableBinding> getBindings() {
		if (vbs == null) {
			vbs = new ArrayList<IVariableBinding>();

			for (IVariable var : getVariables()) {
				vbs.add(new VariableBinding(var, this));
			}
		}
		return vbs;
	}
	/**
	 * {@inheritDoc}
	 */
	public boolean containsVariable(IVariable var) {
		return variables.contains(var);
	}

	public void clear() {
		values.clear();
		vbs.clear();
		variables.clear();
	}

}
