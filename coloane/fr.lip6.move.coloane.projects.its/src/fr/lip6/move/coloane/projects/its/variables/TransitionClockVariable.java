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
package fr.lip6.move.coloane.projects.its.variables;

import fr.lip6.move.coloane.interfaces.model.INode;

/**
 * The integer variable representing a TPN transition clock.
 * 
 * @author Yann
 * 
 */
public class TransitionClockVariable extends LeafModelVariable {

	private String clock;

	/**
	 * The integer variable representing a TPN transition clock.
	 * 
	 * @param transition
	 *            the owner transition.
	 */
	public TransitionClockVariable(INode transition) {
		super(transition.getAttribute("label").getValue());
		setId("__clock_T_" + transition.getId() + getName());
		clock = "[ " + transition.getAttribute("earliestFiringTime").getValue()
				+ ", " + transition.getAttribute("latestFiringTime").getValue()
				+ " ]";
	}
	
	

	public TransitionClockVariable(TransitionClockVariable other) {
		super(other);
		this.clock = other.clock;
	}



	/**
	 * {@inheritDoc}
	 */
	public final String getDescription() {
		return "An integer representing a transition clock value " + clock
				+ ".";
	}
	
	public TransitionClockVariable clone () {
		return new TransitionClockVariable(this);
	}
}
