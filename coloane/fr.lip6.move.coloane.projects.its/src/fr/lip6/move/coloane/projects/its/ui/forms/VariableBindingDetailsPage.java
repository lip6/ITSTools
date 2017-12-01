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

import fr.lip6.move.coloane.projects.its.expression.IVariableBinding;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
 * @author Yann
 */
public final class VariableBindingDetailsPage extends ITSDetailsPage<IVariableBinding> {
	private Text varNametf;
	private Text varValuetf;

	/**
	 * {@inheritDoc}
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	public void createContents(Composite parent) {
		TableWrapLayout layout = new TableWrapLayout();
		parent.setLayout(layout);

		FormToolkit toolkit = getToolkit();
		Section s1 = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR);
		s1.marginWidth = 4;
		s1.marginHeight = 4;
		s1.setText("Parameter Binding"); //$NON-NLS-1$
		//		s1.setDescription(Messages.getString("TypeOneDetailsPage.name")); //$NON-NLS-1$
		TableWrapData td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		s1.setLayoutData(td);
		Composite client = toolkit.createComposite(s1);
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = 10;
		glayout.marginHeight = 4;
		glayout.numColumns = 2;
		client.setLayout(glayout);

		GridData gd;
		toolkit.createLabel(client, "Parameter Name"); //$NON-NLS-1$
		varNametf = toolkit.createText(client, "", SWT.SINGLE); //$NON-NLS-1$
		varNametf.setEditable(false);

		gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 10;
		varNametf.setLayoutData(gd);

		toolkit.createLabel(client, "Parameter Value"); //$NON-NLS-1$
		varValuetf = toolkit.createText(client, "", SWT.SINGLE | SWT.BORDER); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING);
		varValuetf.setLayoutData(gd);
		varValuetf.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try {
					if (getInput() != null) {
						String s = varValuetf.getText();
						if ("".equals(s)) {
							getInput().setVariableValue(null);
							return;
						}
						Integer n = Integer.parseInt(s);
						getInput().setVariableValue(n);
					}
				} catch (NumberFormatException ex) {
					varValuetf.setText("");
				}
			}
		});

		toolkit.paintBordersFor(s1);
		toolkit.paintBordersFor(client);
		s1.setClient(client);
	}
	/**
	 * refresh the state
	 */
	protected void update() {
		IVariableBinding input = getInput();
		// CHECKSTYLE OFF
		varNametf.setText(input != null && input.getVariableName() != null ? input.getVariableName() : "");
		Integer n = input != null ? input.getVariableValue() : null;
		varValuetf.setText(n != null ?  n.toString() : ""); //$NON-NLS-1$
		// CHECKSTYLE ON
	}
}
