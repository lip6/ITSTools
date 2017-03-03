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
package fr.lip6.move.coloane.projects.its;

import fr.lip6.move.coloane.projects.its.obs.SimpleObservable;

import java.util.ArrayList;
import java.util.List;

/**
 * A concept represents instances in a composite.
 * For a type name given in the coloane composite model in an instance, the concept maps
 * to a type declaration in the TypeList.
 * @author Yann
 *
 */
public final class Concept extends SimpleObservable {
	private String name;
	/** The list of labels required in this composite for a given type name */
	private List<String> labels;
	/** The resolved map from Type name in the composite, to effective Type in the Model*/
	private ITypeDeclaration effective;
	private CompositeTypeDeclaration parent;

	/**
	 * Ctor.
	 * @param name of the type in the Composite graph object.
	 * @param parent the parent declaration
	 */
	public Concept(String name, CompositeTypeDeclaration parent) {
		this.name = name;
		labels = new ArrayList<String>();
		effective = null;
		this.parent = parent;
	}

	/**
	 * accessor
	 * @return the name of the type in the Composite graph object.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Based on the graph, the labels required from this type.
	 * @return the required labels for this concept
	 */
	public List<String> getLabels() {
		return labels;
	}
	/**
	 * 
	 * @return the effective type assigned to this concept or null if not set
	 */
	public ITypeDeclaration getEffective() {
		return effective;
	}
	/**
	 * Update value and notify observers
	 * @param effective new effective type
	 */
	public void setEffective(ITypeDeclaration effective) {
		if (this.effective != effective) {
			this.effective = effective;
			notifyObservers();
		}
	}
	
	/**
	 * @return parent of this concept
	 */
	public CompositeTypeDeclaration getParent() {
		return parent;
	}
}
