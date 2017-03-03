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
package fr.lip6.move.coloane.projects.its.flatten;

import fr.lip6.move.coloane.interfaces.model.INode;

/** A class to represent a pair of Transition and containing instance.
 * 
 * @author Yann
 *
 */
public final class ResolvedTrans {


	private String instance;
	private INode transition;

	/**
	 * Ctor
	 * @param instance the prefix to instance e.g: P1.P2.
	 * @param transition the transition to unfold
	 */
	public ResolvedTrans(String instance, INode transition) {
		this.instance = instance;
		this.transition = transition;
	}

	/**
	 * @return the prefix of the instance
	 */
	public String getInstance() {
		return instance;
	}
	/**
	 * @return the transition
	 */
	public INode getTransition() {
		return transition;
	}

	/**
	 * pretty print: obtains labels when unfolding
	 * @return unique name of this object
	 */
	@Override
	public String toString() {
		return instance + "." + transition.getAttribute("label").getValue();
	}
}
