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

import fr.lip6.move.coloane.projects.its.ITypeListProvider;
import fr.lip6.move.coloane.projects.its.TypeList;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;


/**
 * Hold a Master/Details GUI design pattern Forms based ui.
 * Master is a TypeList tree view, details show all relevant data.
 * @author Yann
 */
public final class MasterDetailsPage extends FormPage implements ITypeListProvider {
	private ScrolledPropertiesBlock block;
	private ITypeListProvider mpe;
	/**
	 * Ctor.
	 * @param editor the parent editor (for getTypes())
	 * @param types the TypeList provider we depende upon.
	 */
	public MasterDetailsPage(FormEditor editor, ITypeListProvider types) {
		super(editor, "treeview", "Types Editor"); //$NON-NLS-1$ //$NON-NLS-2$
		block = new ScrolledPropertiesBlock(this);
		this.mpe = types;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		//FormToolkit toolkit = managedForm.getToolkit();
		form.setText("Models loaded as ITS"); //$NON-NLS-1$
		form.setBackgroundImage(ITSEditorPlugin.getDefault().getImage(ITSEditorPlugin.IMG_FORM_BG));
		block.createContent(managedForm);
	}
	/**
	 * @return the parent editor
	 */
	public ITypeListProvider getMpe() {
		return mpe;
	}
	/**
	 * Refresh display
	 */
	public void refresh() {
		block.refresh();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public TypeList getTypes() {
		return mpe.getTypes();
	}
}

