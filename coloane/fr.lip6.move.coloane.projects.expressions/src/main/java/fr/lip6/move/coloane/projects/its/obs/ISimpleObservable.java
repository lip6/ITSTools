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
package fr.lip6.move.coloane.projects.its.obs;

/**
 * An observable object, or Subject per the Observer DP.
 * @author Yann
 *
 */
public interface ISimpleObservable {

	/**
	 * Add an observer to this subject.
	 * Double insertion is ignored: the subject uses a SET of observers.
	 * @param o the observer to add
	 */
	void addObserver(ISimpleObserver o);

	/**
	 * Remove an observer from the set.
	 * @param o observer to remove.
	 */
	void deleteObserver(ISimpleObserver o);

}

