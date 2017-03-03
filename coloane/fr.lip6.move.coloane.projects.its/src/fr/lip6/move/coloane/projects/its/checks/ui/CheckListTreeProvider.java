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
package fr.lip6.move.coloane.projects.its.checks.ui;

import fr.lip6.move.coloane.projects.its.ITypeDeclaration;
import fr.lip6.move.coloane.projects.its.checks.AbstractCheckService;
import fr.lip6.move.coloane.projects.its.checks.CTLCheckService;
import fr.lip6.move.coloane.projects.its.checks.CTLFormulaDescription;
import fr.lip6.move.coloane.projects.its.checks.CheckList;
import fr.lip6.move.coloane.projects.its.checks.IServiceResultProvider;
import fr.lip6.move.coloane.projects.its.checks.OrderingService;
import fr.lip6.move.coloane.projects.its.checks.ServiceResult;
import fr.lip6.move.coloane.projects.its.order.Ordering;
import fr.lip6.move.coloane.projects.its.order.Orders;
import fr.lip6.move.coloane.projects.its.variables.IModelVariable;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class CheckListTreeProvider implements ITreeContentProvider {

	/**
	 * Root elements are the type declarations.
	 * 
	 * @param inputElement
	 *            a type list
	 * @return the types in the list
	 * 
	 */
	public Object[] getElements(Object inputElement) {
		CheckList cl = (CheckList) inputElement;
		List<Object> al = new ArrayList<Object>();
		al.add(cl.getType());
		for (IServiceResultProvider cs : cl) {
			al.add(cs);
		}
		return al.toArray();
	}

	/**
	 * Children of type = parameters + concepts for composite type + effective
	 * type for concept
	 * 
	 * @param element
	 *            a type declaration or other tree view object
	 * @return children of this tree node
	 */
	public Object[] getChildren(Object element) {
		List<Object> children = new ArrayList<Object>();
		addChildren(element, children);
		return children.toArray();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	protected void addChildren(Object element, List<Object> children) {
		if (element instanceof OrderingService) {
			OrderingService ords = (OrderingService) element;
			children.add(ords.getOrders());
		}
		if (element instanceof CTLCheckService) {
			CTLCheckService ccs = (CTLCheckService) element;
			for (CTLFormulaDescription form : ccs.getFormulae()) {
				children.add(form);
			}
		} else if (element instanceof AbstractCheckService) {
			IServiceResultProvider cs = (IServiceResultProvider) element;
			for (ServiceResult sr : cs) {
				children.add(sr);
			}
		} else if (element instanceof CTLFormulaDescription) {
			CTLFormulaDescription form = (CTLFormulaDescription) element;
			for (ServiceResult sr : form) {
				children.add(sr);
			}
		} else if (element instanceof Orders) {
			Orders os = (Orders) element;
			for (Ordering o : os) {
				children.add(o);
			}
		} else if (element instanceof Ordering) {
			Ordering o = (Ordering) element;
			for (Ordering o2 : o) {
				children.add(o2);
			}
		} else if (element instanceof ITypeDeclaration) {
			ITypeDeclaration td = (ITypeDeclaration) element;
			for (IModelVariable v : td.getVariables()) {
				children.add(v);
			}
		} else if (element instanceof IModelVariable) {
			IModelVariable var = (IModelVariable) element;
			for (IModelVariable v2 : var) {
				children.add(v2);
			}
		}
	}

	/**
	 * Implemented where possible per the contract of TreeProvider {@inheritDoc}
	 */
	public Object getParent(Object element) {
		if (element instanceof ServiceResult) {
			ServiceResult sr = (ServiceResult) element;
			return sr.getParent();
		} else if (element instanceof CTLFormulaDescription) {
			CTLFormulaDescription form = (CTLFormulaDescription) element;
			return form.getParent();
		} else if (element instanceof IModelVariable) {
			IModelVariable var = (IModelVariable) element;
			if (var.getParent() != var) {
				return var.getParent();
			}
		}
		return null;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
