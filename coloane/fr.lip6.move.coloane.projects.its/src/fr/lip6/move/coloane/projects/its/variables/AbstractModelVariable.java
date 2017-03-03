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

/**
 * Carries common properties of all variables. Most abstract implementation of a
 * IModelVariable.
 *
 * @author Yann
 *
 */
public abstract class AbstractModelVariable implements IModelVariable {

	/**
	 * Variables are named.
	 */
	private String name;
	/**
	 * Parent relationship, per DP.
	 */
	private IModelVariable parent;
	/**
	 * Fully qualified name.
	 */
	private String id;

	/**
	 * Name the variable.
	 * 
	 * @param name its name
	 */
	public AbstractModelVariable(String name) {
		this.name = name;
	}

	public AbstractModelVariable (AbstractModelVariable other) {
		this.name = other.name;
		this.id = other.id;
	}
	
	/**
	 * Position the id. Call during construction.
	 * 
	 * @param id
	 *            the new id.
	 */
	protected final void setId(String id) {
		this.id = id;
	}

	/**
	 * Return this variable's name.
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Parent relationship, per the DP.
	 * 
	 * @return the parent, or "this" at root level.
	 */
	public final IModelVariable getParent() {
		if (parent != null) {
			return parent;
		}
		return this;
	}

	/**
	 * Builds the fully qalified name of this variable.
	 * 
	 * @return a unique hierarchical identifier for this variable.
	 */
	public final String getQualifiedName() {
		String s = "";
		if (getParent() != this) {
			s = getParent().getQualifiedName() + ".";
		}
		return s + name;
	}

	/**
	 * Setter for parent. This operation should only be called internally.
	 * 
	 * @param parent
	 *            the parent in the tree or null to designate this is a root.
	 */
	public final void setParent(IModelVariable parent) {
		this.parent = parent;
	}

	/**
	 * Builds a fully qualified id for this variable. {@inheritDoc}
	 * 
	 * @return the unique hierarchical Id.
	 */
	public final String getId() {
		String s = "";
		if (getParent() != this) {
			s = getParent().getId() + ".";
		}
		return s + id;
	}
	
	public abstract IModelVariable clone ();

}
