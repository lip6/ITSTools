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

import java.util.Iterator;

/**
 * A class to represent a model variable, that can be interrogated in the logic.
 * 
 * @author Yann
 * 
 */
public interface IModelVariable extends Iterable<IModelVariable> {

	/**
	 * The pretty name of this variable, may not be unique or set.
	 * 
	 * @return the name or ""
	 */
	String getName();

	/**
	 * The Children of this ModelVariable, in the composite case. {@inheritDoc}
	 */
	Iterator<IModelVariable> iterator();

	/**
	 * The name with "." separators
	 * 
	 * @return the qualified name of this object
	 */
	String getQualifiedName();

	/**
	 * The parent of this model variable.
	 * 
	 * @return the parent or "this" if this is the root.
	 */
	IModelVariable getParent();

	/**
	 * The ITS id of this object, for SDD tools interaction.
	 * 
	 * @return the id
	 */
	String getId();

	/**
	 * Human readable description of what this variable designates.
	 * 
	 * @return a fixed string for each type of variable.
	 */
	String getDescription();

	/**
	 * Define the parent object.
	 * 
	 * @param parent
	 *            new parent.
	 */
	void setParent(IModelVariable parent);

	/** Deep copy this variable */
	IModelVariable clone();
}
