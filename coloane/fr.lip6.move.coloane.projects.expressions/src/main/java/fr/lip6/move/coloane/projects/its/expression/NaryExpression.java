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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Base abstract class for N-ary expressions
 * @author Yann
 *
 */
public abstract class NaryExpression implements IntegerExpression {

	private List<IntegerExpression> children = new ArrayList<IntegerExpression>();

	/**
	 * {@inheritDoc}
	 */
	public final List<IntegerExpression> getChildren() {
		return children;
	}

	/**
	 * {@inheritDoc}
	 */
	public final Set<IVariable> supportingVariables() {
		Set<IVariable> set = new HashSet<IVariable>();
		for (IntegerExpression expr : getChildren()) {
			set.addAll(expr.supportingVariables());
		}
		return set;
	}
}
