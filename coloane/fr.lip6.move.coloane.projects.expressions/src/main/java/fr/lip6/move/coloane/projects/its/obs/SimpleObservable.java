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

import java.util.ArrayList;
import java.util.List;

/**
 * A basic implementation of an observer.
 * @author Yann
 *
 */
public class SimpleObservable implements ISimpleObservable {
	private List<ISimpleObserver> obs = new ArrayList<ISimpleObserver>();

	/**
	 * {@inheritDoc}
	 *  (non-Javadoc)
	 * @see fr.lip6.move.coloane.its.ISimpleObservable#addObserver(fr.lip6.move.coloane.its.ISimpleObserver)
	 */
	public final void addObserver(ISimpleObserver o) {
		if (!obs.contains(o)) {
				obs.add(o);
		}
	}

	/**
	 * {@inheritDoc}
	 *  (non-Javadoc)
	 * @see fr.lip6.move.coloane.its.ISimpleObservable#deleteObserver(fr.lip6.move.coloane.its.ISimpleObserver)
	 */
	public final void deleteObserver(ISimpleObserver o) {
		obs.remove(o);
	}

	/**
	 * {@inheritDoc}
	 */
	public final void notifyObservers() {
		for (ISimpleObserver o : obs) {
			o.update();
		}
	}

}
