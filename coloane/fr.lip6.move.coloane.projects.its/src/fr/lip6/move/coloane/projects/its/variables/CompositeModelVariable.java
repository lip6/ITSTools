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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A SDD variable representing a composite per the DP.
 * 
 * @author Yann
 * 
 */
public abstract class CompositeModelVariable extends AbstractModelVariable
		implements IModelVariable {

	/**
	 * children per Composite DP.
	 */
	private List<IModelVariable> children = new ArrayList<IModelVariable>();

	/**
	 * Variables are named.
	 * 
	 * @param name the name
	 */
	public CompositeModelVariable(String name) {
		super(name);
	}


	/**
	 * Iterable interface for children per DP.
	 * {@inheritDoc}
	 */
	public final Iterator<IModelVariable> iterator() {
		return children.iterator();
	}

	/**
	 * Write accessor to add children.
	 * 
	 * @param var
	 *            a subvariable child.
	 */
	public final void addChild(IModelVariable var) {
		children.add(var);
		var.setParent(this);
	}

	public CompositeModelVariable (CompositeModelVariable other) {
		super(other);
		for (IModelVariable v: other) {
			this.addChild(v.clone());
		}
	}
	
	public abstract IModelVariable clone();

}
