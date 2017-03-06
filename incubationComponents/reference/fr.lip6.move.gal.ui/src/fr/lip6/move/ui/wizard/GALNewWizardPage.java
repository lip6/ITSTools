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
package fr.lip6.move.ui.wizard;

import org.eclipse.jface.viewers.ISelection;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one
 * (fr.lip6.move.coloane.its).
 */
public final class GALNewWizardPage extends AbstractNewWizardPage {

	/**
	 * Constructor for ITSNewWizardPage.
	 * 
	 * @param selection
	 *            the workspace item currently selected (should be a folder)
	 */
	public GALNewWizardPage(ISelection selection) {
		super(selection);
	}

	@Override
	protected String getWizardDescription() {
		return "This wizard creates a new GAL model file with *.gal extension.";
	}

	@Override
	protected String getWizardFileExtension() {
		return "gal";
	}

	@Override
	protected String getWizardTitle() {
		return "GAL Model";
	}
}
