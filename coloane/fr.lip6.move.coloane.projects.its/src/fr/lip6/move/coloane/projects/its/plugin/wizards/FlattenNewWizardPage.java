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
package fr.lip6.move.coloane.projects.its.plugin.wizards;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one
 * (fr.lip6.move.coloane.its).
 */
public final class FlattenNewWizardPage extends AbstractNewWizardPage {

	private boolean shouldInstantiate = false;

	/**
	 * Constructor for ITSNewWizardPage.
	 * 
	 * @param selection
	 *            the workspace item currently selected (should be a folder)
	 */
	public FlattenNewWizardPage(ISelection selection) {
		super(selection);
	}

	/**
	 * Sets whether the variables in integer expressions should be resolved to
	 * their full values or not.
	 * 
	 * @return
	 */
	public boolean shouldInstantiate() {
		return shouldInstantiate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getWizardDescription() {
		return "This wizard creates a new Coloane model by flattening the hierarchical ITS description.";
	}

	/**
	 * Extension is "model". {@inheritDoc}
	 */
	@Override
	protected String getWizardFileExtension() {
		return "model";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getWizardTitle() {
		return "Flatten an ITS Model";
	}

	@Override
	protected void addContent(Composite container) {
		Button b = new Button(container, SWT.CHECK);
		b.setText("Instantiate Variables");
		b.setToolTipText("Instantiate parameters of the model, i.e. $Variable in attributes replaced by their effective value.");
		b.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				boolean b = ((Button) e.getSource()).getSelection();
				shouldInstantiate = b;
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}
}
