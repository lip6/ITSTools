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

import fr.lip6.move.coloane.projects.its.checks.CTLCheckService;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class CTLCheckServiceDetailsPage extends ITSCheckServiceDetailsPage {

	@Override
	public CTLCheckService getInput() {
		return (CTLCheckService) super.getInput();
	}

	/**
	 * {@inheritDoc} (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createContents(Composite parent) {
		Button add = getToolkit().createButton(parent, "Add a formula",
				SWT.PUSH);
		add.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				getInput().addFormula("F"+getInput().getFormulae().size(), "TRUE;", "New formula");
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		super.createContents(parent);

		parent.pack();
	}

}
