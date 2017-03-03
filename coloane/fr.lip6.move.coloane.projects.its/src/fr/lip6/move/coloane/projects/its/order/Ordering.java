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
package fr.lip6.move.coloane.projects.its.order;

/**
 * An abstraction to represent Ordering objects in memory. Iterator interface
 * allows to access children.
 * 
 * @author Yann
 * 
 */
public interface Ordering extends Iterable<Ordering> {

	/**
	 * Gives a logical name for this SDD variable.
	 * 
	 * @return a name, could be generated.
	 */
	String getName();

	/**
	 * Variables are either SDD type (hierarchical) or Integer type.
	 */
	enum Domain {
		SDD, Integer
	};

	/**
	 * Return this variable's domain.
	 * 
	 * @return SDD or Integer
	 */
	Domain getDomain();

	/**
	 * The index of a variable of this order.
	 * 
	 * @param value
	 *            the variable to look for
	 * @return the index or 0 if not found
	 */
	int getVarIndex(String value);

	/**
	 * Insert a variable in the order, so its resulting index is the one
	 * provided.
	 * 
	 * @param value
	 *            the variable to insert
	 * @param index the index to insert at
	 */
	void insertVarAtIndex(String value, int index);

	/**
	 * Set a name for this variable.
	 * @param string the name.
	 */
	void setName(String string);
}
