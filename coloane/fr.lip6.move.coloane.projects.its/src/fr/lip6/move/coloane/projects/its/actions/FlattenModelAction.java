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
package fr.lip6.move.coloane.projects.its.actions;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import fr.lip6.move.coloane.projects.its.ITypeDeclaration;
import fr.lip6.move.coloane.projects.its.plugin.wizards.FlattenNewModelWizard;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;


/**
 * Class associated with buttons to handle model flattening.
 */
public final class FlattenModelAction extends Action {

	private ITypeDeclaration td;
	private IResource path;

	
	public FlattenModelAction() {
		setDescription("Flatten a model");
		setText("Flatten Model");
	}
	
	/**
	 * Position the TypeDeclaration we are working with.
	 * @param td the TypeDeclaration to set
	 */
	public void setTypeDeclaration(ITypeDeclaration td) {
		this.td = td;		
	}

	/**
	 * @see IActionDelegate#run(IAction) Instantiates the wizard and opens it in
	 *      the wizard container
	 * {@inheritDoc}
	 */
	public void run() {

		// Instantiates and initializes the wizard
		FlattenNewModelWizard wizard = new FlattenNewModelWizard(td);

		SingleSelection<IResource> selectedPath = new SingleSelection<IResource> (path);
		
		wizard.init(PlatformUI.getWorkbench(), selectedPath );

		// Instantiates the wizard container with the wizard and opens it
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), wizard);
		dialog.create();
		dialog.open();
	}

	public void setPath(IResource location) {
		this.path = location;
	}

}


class SingleSelection<T> implements IStructuredSelection {
	
	private T elt;

	public SingleSelection(T path) {
		this.elt = path;
	}

	public boolean isEmpty() {
		return false;
	}
	
	public List<T> toList() {
		return Collections.singletonList(elt);
	}
	
	public Object[] toArray() {
		return toList().toArray();
	}
	
	public int size() {
		return 1;
	}
	
	@SuppressWarnings("rawtypes")
	public Iterator iterator() {
		return toList().iterator();
	}
	
	public Object getFirstElement() {
		return elt;
	}
}
