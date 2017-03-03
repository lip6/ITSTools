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

import fr.lip6.move.coloane.projects.its.ITypeListProvider;
import fr.lip6.move.coloane.projects.its.TypeList;
import fr.lip6.move.coloane.projects.its.checks.CheckList;
import fr.lip6.move.coloane.projects.its.ui.forms.ITSEditorPlugin;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * Hold a Master/Details GUI design pattern Forms based ui. Master is a TypeList
 * tree view, details show all relevant data.
 * 
 * @author Yann
 */
public final class ChecksMasterDetailsPage extends FormPage implements
		ITypeListProvider {
	private ChecksScrolledPropertiesBlock block;
	private ITypeListProvider mpe;
	private CheckList checkList;

	/**
	 * Ctor.
	 * 
	 * @param editor
	 *            the parent editor (for getTypes())
	 * @param cl
	 */
	public ChecksMasterDetailsPage(FormEditor editor, ITypeListProvider types,
			CheckList cl) {
		super(editor, "treeview", cl.getType().getTypeName() + " analysis"); //$NON-NLS-1$ //$NON-NLS-2$
		block = new ChecksScrolledPropertiesBlock(this);
		this.mpe = types;
		this.checkList = cl;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		// FormToolkit toolkit = managedForm.getToolkit();
		form.setText("Analysis available"); //$NON-NLS-1$
		form.setBackgroundImage(ITSEditorPlugin.getDefault().getImage(
				ITSEditorPlugin.IMG_FORM_BG));
		block.createContent(managedForm);
	}

	/**
	 * Refresh display
	 */
	public void refresh() {
		block.refresh();
	}

	public TypeList getTypes() {
		return mpe.getTypes();
	}

	public CheckList getCheckList() {
		return checkList;
	}
}
