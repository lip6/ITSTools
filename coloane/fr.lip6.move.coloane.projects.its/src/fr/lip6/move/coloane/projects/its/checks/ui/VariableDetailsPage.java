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
import fr.lip6.move.coloane.projects.its.ui.forms.ITSDetailsPage;
import fr.lip6.move.coloane.projects.its.variables.IModelVariable;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * Details about a Variable in a n Order and its name(s).
 * @author Yann
 *
 */
public final class VariableDetailsPage extends ITSDetailsPage<IModelVariable> {

	private static final String VARNAME = "Variable Name";
	private static final String VARDESC = "Description";
	private static final String VARID = "ITS ID";
	private static final String VARQUAL = "Qualified name";

	private ParameterSection params;

	@Override
	protected void update() {
		IModelVariable input = getInput();
		params.setInput(getParameters(input));
	}

	/**
	 * Builds the parameters.
	 * @param input a variable
	 * @return appropriate params.
	 */
	private ParameterList getParameters(IModelVariable input) {
		ParameterList pl = new ParameterList();
		pl.addParameter(VARNAME, input.getName(), "The name of this variable, without its full qualification.");
		pl.addParameter(VARDESC, input.getDescription(), "The type of this variable, might be a marking or a clock or an instance.");
		pl.addParameter(VARQUAL, input.getQualifiedName(), "The fully qualified name of this variable, for use in atomic propositions.");
		pl.addParameter(VARID, input.getId(), "The internal unique identifier of this variable, used internally to discriminate against duplicate names used. This field is mostly for debugging.");
		return pl;
	}

	/**
	 * Just a parameter section.
	 * {@inheritDoc}
	 */
	public void createContents(Composite parent) {
		TableWrapLayout layout = new TableWrapLayout();
		parent.setLayout(layout);

		params = new ParameterSection("Variable Description", getToolkit(),
				parent, false);
	}

}
