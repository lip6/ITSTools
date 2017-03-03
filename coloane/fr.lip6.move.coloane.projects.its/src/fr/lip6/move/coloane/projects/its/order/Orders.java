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

import fr.lip6.move.coloane.projects.its.obs.SimpleObservable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A hierarchical variable order, carries Ordering objects.
 * @author Yann
 *
 */
public final class Orders extends SimpleObservable implements Iterable<Ordering> {

	private List<Ordering> orders = new ArrayList<Ordering>();
	
	/**
	 * Iterator over children.
	 * @return children iterator
	 */
	public Iterator<Ordering> iterator() {
		return orders.iterator();
	}

	/**
	 * Adds a child Ordering object.
	 * @param name name for this order
	 * @param order order to be added
	 */
	public void addOrder(String name, Ordering order) {
		Group parent = new Group(name);
		parent.addChild(order);
		orders.add(parent);
		notifyObservers();
	}

}
