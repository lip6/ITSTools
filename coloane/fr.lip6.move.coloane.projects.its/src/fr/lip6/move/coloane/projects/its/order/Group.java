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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Group or Composite variable in an ordering.
 * 
 * @author Yann
 * 
 */
public final class Group implements Ordering {

	private String name;

	private List<Ordering> children = new ArrayList<Ordering>();

	/**
	 * Sets this group's or composite variable name.
	 * 
	 * @param name name of this variable
	 */
	public Group(String name) {
		this.name = name;
	}

	/**
	 * Composite variables have SDD domain. {@inheritDoc}
	 */
	public Domain getDomain() {
		return Domain.SDD;
	}

	/**
	 * Name accessor.
	 * 
	 * @return this variable's non-qualified name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Composite DP : add a child Ordering object.
	 * 
	 * @param o
	 *            child to be added
	 */
	public void addChild(Ordering o) {
		children.add(o);
	}

	/**
	 * Accessor for children of this Ordering.
	 * 
	 * @return iterator over children.
	 */
	public Iterator<Ordering> iterator() {
		return children.iterator();
	}

	/**
	 * Return index of variable queried for in children table.
	 * {@inheritDoc}
	 * @return 0 if not found, the index otherwise.
	 * 
	 */
	public int getVarIndex(String value) {
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).getName().equals(value)) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * Add a variable at the provided index.
	 * 
	 * @param value
	 *            the variable name
	 * @param index
	 *            the index to be set
	 */
	public void insertVarAtIndex(String value, int index) {
		children.add(index, new Variable(value));
	}

	/**
	 * Sets the name for this Group object.
	 * {@inheritDoc}
	 */
	public void setName(String name) {
		this.name = name;
	}

}
