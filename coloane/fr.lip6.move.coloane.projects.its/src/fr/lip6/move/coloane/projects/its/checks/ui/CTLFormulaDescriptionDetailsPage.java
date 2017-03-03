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

import fr.lip6.move.coloane.projects.its.checks.CTLFormulaDescription;
import fr.lip6.move.coloane.projects.its.ui.forms.ITSDetailsPage;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class CTLFormulaDescriptionDetailsPage extends
		ITSDetailsPage<CTLFormulaDescription> {

	private CTLSection ctl;

	@Override
	protected void update() {
		ctl.setInput(getInput());
	}

	public void createContents(Composite parent) {
		TableWrapLayout layout = new TableWrapLayout();
		parent.setLayout(layout);

		ctl = new CTLSection(getToolkit(), parent);
		ctl.setInput(getInput());
	}

}
