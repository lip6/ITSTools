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

import fr.lip6.move.coloane.projects.its.checks.ParameterList;
import fr.lip6.move.coloane.projects.its.order.Ordering;
import fr.lip6.move.coloane.projects.its.ui.forms.ITSDetailsPage;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class OrderingDetailsPage extends ITSDetailsPage<Ordering> {

	private static final String VARNAME = "Variable Name";
	private static final String VARTYPE = "Variable Domain";
	private ParameterSection params;

	@Override
	protected void update() {
		Ordering input = getInput();
		params.setInput(getParameters(input));

	}

	private ParameterList getParameters(Ordering input) {
		ParameterList pl = new ParameterList();
		pl.addParameter(VARNAME, input.getName(), "The name of this variable, to use in atomic propositions.");
		pl.addParameter(VARTYPE, input.getDomain().toString(), "The domain of this variable, might be a nested type or an integer range.");
		return pl;
	}

	public void createContents(Composite parent) {
		TableWrapLayout layout = new TableWrapLayout();
		parent.setLayout(layout);

		params = new ParameterSection("Variable Details", getToolkit(), parent,
				false);

	}

}
