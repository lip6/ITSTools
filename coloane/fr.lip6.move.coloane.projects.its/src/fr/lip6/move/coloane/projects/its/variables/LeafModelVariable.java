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

import java.util.Collections;
import java.util.Iterator;

/**
 * A terminal node per Composite DP.
 * 
 * @author Yann
 * 
 */
public abstract class LeafModelVariable extends AbstractModelVariable implements
		IModelVariable {

	/**
	 * Any variable has a name.
	 * 
	 * @param name
	 *            the name of this leaf variable.
	 */
	public LeafModelVariable(String name) {
		super(name);
	}
	
	public LeafModelVariable(LeafModelVariable other) {
		super(other);
	}

	/**
	 * A leaf has no children.
	 * 
	 * @return empty list iterator
	 */
	@SuppressWarnings("unchecked")
	public final Iterator<IModelVariable> iterator() {
		return Collections.EMPTY_LIST.iterator();
	}
	
	public abstract IModelVariable clone();

}
