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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * An abstract class carrying implementations of many required but unused
 * features of IDetailsPage. This class carries the notion of input model object
 * and reflects any changes it is notified of through an observer DP.
 * 
 * @author Yann
 * 
 * @param <T> The model input type.
 */
public abstract class ITSDetailsPage<T> implements IDetailsPage {

	private IManagedForm mform;

	private T input;

	/**
	 * Set the input model object.
	 * @param input new input
	 */
	public void setInput(T input) {
		this.input = input;
	}
	
	/**
	 * Returns the current input model object.
	 * @return the current input.
	 */
	public T getInput() {
		return input;
	}

	/**
	 * {@inheritDoc}
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#initialize(org.eclipse.ui.forms.IManagedForm)
	 */
	public final void initialize(IManagedForm mform) {
		this.mform = mform;
	}

	protected final FormToolkit getToolkit() {
		return mform.getToolkit();
	}

	/**
	 * {@inheritDoc}
	 * (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#inputChanged(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@SuppressWarnings("unchecked")
	public final void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection) selection;
		if (ssel.size() == 1) {
			setInput((T) ssel.getFirstElement());
		} else {
			setInput(null);
		}
		update();
	}

	/**
	 * {@inheritDoc}
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#commit()
	 */
	public final void commit(boolean onSave) {
	}
	/**
	 * {@inheritDoc}
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#setFocus()
	 */
	public final void setFocus() {
	}
	/**
	 * {@inheritDoc}
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#dispose()
	 */
	public final void dispose() {
	}
	/**
	 * {@inheritDoc}
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#isDirty()
	 */
	public final boolean isDirty() {
		return false;
	}
	/**
	 * {@inheritDoc}
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#isStale()
	 */
	public final boolean isStale() {
		return false;
	}
	/**
	 * {@inheritDoc}
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#refresh()
	 */
	public final void refresh() {
		update();
	}
	
	/**
	 * This is the only place the user is supposed to extend this class.
	 */
	protected abstract void update() ;
	/**
	 * {@inheritDoc}
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#setFormInput()
	 */
	public final boolean setFormInput(Object input) {
		return false;
	}


	/**
	 * Create a nice separator between two subsections.
	 * @param toolkit the toolkit
	 * @param parent the parent
	 * @param span the span of the spacer
	 */
	protected final void createSpacer(FormToolkit toolkit, Composite parent, int span) {
		Label spacer = toolkit.createLabel(parent, ""); //$NON-NLS-1$
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		spacer.setLayoutData(gd);
	}
	
	/**
	 * The form that manages this page.
	 * @return the managed form
	 */
	public IManagedForm getMform() {
		return mform;
	}
}
