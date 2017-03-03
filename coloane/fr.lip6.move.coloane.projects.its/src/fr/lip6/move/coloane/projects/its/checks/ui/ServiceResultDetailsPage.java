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

import fr.lip6.move.coloane.projects.its.checks.ServiceResult;
import fr.lip6.move.coloane.projects.its.ui.forms.ITSDetailsPage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * A details page for a variable binding.
 * 
 * @author Yann
 */
public final class ServiceResultDetailsPage extends
		ITSDetailsPage<ServiceResult> {
	private Text isOktf;
	private Text datetf;
	private Text reporttf;
	private ParameterSection params;
	private ParameterSection results;
	private Section resSection;

	/**
	 * {@inheritDoc} (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	public void createContents(Composite parent) {
		TableWrapLayout layout = new TableWrapLayout();
		parent.setLayout(layout);

		results = new ParameterSection("Results of this run",
				getToolkit(), parent, false);
		results.setExpanded(true);
		
		params = new ParameterSection("Tool settings used in this run",
				getToolkit(), parent, false);
		params.setExpanded(false);

		FormToolkit toolkit = getToolkit();
		resSection = toolkit.createSection(parent,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE
				| ExpandableComposite.COMPACT);
		resSection.marginWidth = 4;
		resSection.marginHeight = 4;
		resSection.setText("Check Result Description"); //$NON-NLS-1$
		//		s1.setDescription(Messages.getString("TypeOneDetailsPage.name")); //$NON-NLS-1$
		TableWrapData td = new TableWrapData(TableWrapData.FILL,
				TableWrapData.TOP);
		td.grabHorizontal = true;
		resSection.setLayoutData(td);
		Composite client = toolkit.createComposite(resSection);
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = 10;
		glayout.marginHeight = 5;
		glayout.numColumns = 2;
		client.setLayout(glayout);

		GridData gd;
		toolkit.createLabel(client, "Result"); //$NON-NLS-1$
		isOktf = toolkit.createText(client, "", SWT.SINGLE); //$NON-NLS-1$
		isOktf.setEditable(false);
		gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 10;
		isOktf.setLayoutData(gd);

		toolkit.createLabel(client, "Date"); //$NON-NLS-1$

		datetf = toolkit.createText(client, "", SWT.SINGLE); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		datetf.setLayoutData(gd);
		datetf.setEditable(false);

		createSpacer(toolkit, client, 2);

		toolkit.createLabel(client, "Raw Output"); //$NON-NLS-1$
		reporttf = toolkit.createText(client,
				"", SWT.MULTI | SWT.WRAP | SWT.V_SCROLL); //$NON-NLS-1$
		reporttf.setEditable(false);
		gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 10;
		gd.heightHint = 250;
		reporttf.setLayoutData(gd);
		
		toolkit.paintBordersFor(resSection);
		toolkit.paintBordersFor(client);
		resSection.setClient(client);
	}

	/**
	 * refresh the state
	 */
	@Override
	protected void update() {
		ServiceResult input = getInput();
		if (input.getResults().getParameters().size()==0) {
			resSection.setExpanded(true);
		}
		params.setInput(input.getParameters());
		results.setInput(input.getResults());
		
		// CHECKSTYLE OFF
		isOktf.setText(input != null ? input.getSuccess().toString() : "");
		datetf.setText(input != null && input.getDate() != null ? input
				.getDate() : "");
		reporttf.setText(input != null && input.getReport() != null ? input
				.getReport() : "");
		// CHECKSTYLE ON
	}
}
