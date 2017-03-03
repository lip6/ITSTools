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

import fr.lip6.move.coloane.projects.its.ITypeListProvider;
import fr.lip6.move.coloane.projects.its.TypeList;
import fr.lip6.move.coloane.projects.its.dialogs.AddTypeDialog;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.PlatformUI;


/**
 * An action to add a type to a type list
 * @author Yann
 *
 */
public final class AddTypeAction extends Action {

	private ITypeListProvider editor;
	private AddTypeDialog atd;

	/**
	 * basic ctor
	 * @param editor the parent editor (contains the type list)
	 */
	public AddTypeAction(ITypeListProvider editor) {
		setTypeListProvider(editor);
	}

	/**
	 * update current editor
	 * @param editor parent editor
	 */
	public void setTypeListProvider(ITypeListProvider editor) {
		this.editor = editor;
		atd = new AddTypeDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), editor.getTypes());
	}

	/**
	 * Runs this action
	 */
	@Override
	public void run() {
		TypeList types = editor.getTypes();
		atd.open();
		if (atd.getType() != null) {
			types.addTypeDeclaration(atd.getType());
		}
	}

	/** Used to position a hint = a specific file in the dialog.
	 * 
	 * @param hint a full file path to the candidate for insertion.
	 */
	public void setHint(String hint) {
		atd.setFileField(hint);
	}
}
