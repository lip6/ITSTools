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
 * An integer representing the marking of a Time Petri net place.
 * 
 * @author Yann
 * 
 */
public class PlaceMarkingVariable extends LeafModelVariable {

	/**
	 * An integer representing the marking of a Time Petri net place.
	 * 
	 * @param node
	 *            the place node
	 */
	public PlaceMarkingVariable(INode node) {
		super(node.getAttribute("name").getValue());
		setId(getName());
//		setId("P_" + node.getId() + getName());
	}

	private PlaceMarkingVariable(PlaceMarkingVariable other) {
		super(other);
	}

	/**
	 * {@inheritDoc}
	 */
	public final String getDescription() {
		return "An integer representing the marking of a Time Petri net place";
	}

	public PlaceMarkingVariable clone () {
		return new PlaceMarkingVariable(this);
	}
	
}
