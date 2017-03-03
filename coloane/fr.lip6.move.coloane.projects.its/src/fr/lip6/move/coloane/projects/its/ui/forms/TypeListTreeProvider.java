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
package fr.lip6.move.coloane.projects.its.ui.forms;

import fr.lip6.move.coloane.projects.its.CompositeTypeDeclaration;
import fr.lip6.move.coloane.projects.its.Concept;
import fr.lip6.move.coloane.projects.its.ITypeDeclaration;
import fr.lip6.move.coloane.projects.its.TypeList;
import fr.lip6.move.coloane.projects.its.expression.IEvaluationContext;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * A provider for objects in the tree view: type declarations, parameters, concepts
 * @author Yann
 *
 */
public class TypeListTreeProvider implements ITreeContentProvider {

	/**
	 * Root elements are the type declarations.
	 * @param inputElement a type list
	 * @return the types in the list
	 */
	public Object[] getElements(Object inputElement) {
		TypeList tl = (TypeList) inputElement;
		List<Object> al = new ArrayList<Object>();
		for (ITypeDeclaration td : tl) {
			al.add(td);
		}
		return al.toArray();
	}

	/**
	 * Children of type = parameters
	 * + concepts for composite type
	 * + effective type for concept
	 * @param element a type declaration or other tree view object
	 * @return children of this tree node
	 */
	public Object[] getChildren(Object element) {
		List<Object> children = new ArrayList<Object>();
		addChildren(element,children);
		return children.toArray();
	}

	protected void addChildren(Object element, List<Object> children) {
		if (element instanceof CompositeTypeDeclaration) {
			CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) element;
			for (Concept concept : ctd.listConcepts()) {
				children.add(concept);
			}
		} else if (element instanceof Concept) {
			Concept concept = (Concept) element;
			if (concept.getEffective() != null) {
				children.add(concept.getEffective());
			}
		}
		if (element instanceof ITypeDeclaration) {
			ITypeDeclaration td = (ITypeDeclaration) element;
			IEvaluationContext params = td.getParameters();
			children.addAll(params.getBindings());
		}
	}

	/**
	 * Implemented where possible per the contract of TreeProvider
	 * {@inheritDoc}
	 */
	public Object getParent(Object element) {
		if (element instanceof Concept) {
			Concept concept = (Concept) element;
			return concept.getParent();
		} else if (element instanceof ITypeDeclaration) {
			ITypeDeclaration td = (ITypeDeclaration) element;
			return td.getTypeList();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
		// NOP
	}

	/**
	 * {@inheritDoc}
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

}
