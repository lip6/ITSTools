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

import fr.lip6.move.coloane.projects.its.checks.AbstractCheckService;
import fr.lip6.move.coloane.projects.its.ui.forms.ITSDetailsPage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
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
public class CheckServiceDetailsPage extends
		ITSDetailsPage<AbstractCheckService> {
	private Text serviceNametf;
	private Text foldertf;
	private ParameterSection params;

	/**
	 * {@inheritDoc} (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	public void createContents(Composite parent) {
		TableWrapLayout layout = new TableWrapLayout();
		parent.setLayout(layout);

		FormToolkit toolkit = getToolkit();
		Section s1 = toolkit.createSection(parent,
				ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE
		 		| ExpandableComposite.COMPACT);
//						| ExpandableComposite.EXPANDED);
		s1.marginWidth = 4;
		s1.marginHeight = 4;
		s1.setText("Check Service Description"); //$NON-NLS-1$
		//		s1.setDescription(Messages.getString("TypeOneDetailsPage.name")); //$NON-NLS-1$
		TableWrapData td = new TableWrapData(TableWrapData.FILL,
				TableWrapData.TOP);
		td.grabHorizontal = true;
		s1.setLayoutData(td);
		Composite client = toolkit.createComposite(s1);
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = 10;
		glayout.marginHeight = 5;
		glayout.numColumns = 2;
		client.setLayout(glayout);

		GridData gd;
		toolkit.createLabel(client, "Service Name"); //$NON-NLS-1$
		serviceNametf = toolkit.createText(client, "", SWT.SINGLE); //$NON-NLS-1$
		serviceNametf.setEditable(false);
		gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 10;
		serviceNametf.setLayoutData(gd);

		toolkit.createLabel(client, "Work folder"); //$NON-NLS-1$
		Composite folderzone = toolkit.createComposite(client, SWT.BORDER);

		glayout = new GridLayout();
		glayout.numColumns = 1;
		folderzone.setLayout(glayout);
		gd = new GridData( GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 400;
		folderzone.setLayoutData(gd);

		foldertf = toolkit
				.createText(folderzone, "", SWT.SINGLE | SWT.WRAP | SWT.H_SCROLL); //$NON-NLS-1$		


	    gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING);
		foldertf.setLayoutData(gd);
		foldertf.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (getInput() != null) {
					String s = foldertf.getText();
					getInput().setWorkdir(s);
				}
			}
		});

		Button browseb = toolkit
				.createButton(folderzone, "Browse...", SWT.PUSH);
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING
				| GridData.HORIZONTAL_ALIGN_BEGINNING);
		browseb.setLayoutData(gd);
		browseb.addSelectionListener(new SelectionAdapter() {
			DirectoryDialog dialog = new DirectoryDialog(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow().getShell());

			@Override
			public void widgetSelected(SelectionEvent event) {
				dialog.setText("Choose a work folder");
				dialog.setMessage("Choose a work folder for service "
						+ getInput().getName()
						+ ". This folder will hold intermediate files and run results.");
				String directory = dialog.open();
				foldertf.setText(directory);
			}
		});



		toolkit.paintBordersFor(s1);
		toolkit.paintBordersFor(client);
		s1.setClient(client);

		// /// ADD THE PARAMS HERE
		params = new ParameterSection("Tool Settings", getToolkit(), parent,
				true);
	
		updateFolder();
	}


	private void updateFolder() {
		AbstractCheckService input = getInput();
		foldertf.setText(input != null && input.getWorkDir() != null ? input
				.getWorkDir() : "");
	}


	
	/**
	 * Adds a button to run the tool if necessary.
	 * @param parent the container to add the button to.
	 */
	protected void createRunButton(Composite parent) {
		Button runb = getToolkit().createButton(parent, "Run service", SWT.PUSH);

		runb.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				getInput().run();
			}
		});

	}

	/**
	 * refresh the state
	 */
	@Override
	protected void update() {
		AbstractCheckService input = getInput();
		params.setInput(input.getParameters());
		// CHECKSTYLE OFF
		serviceNametf.setText(input != null && input.getName() != null ? input
				.getName() : "");
		updateFolder();
		// CHECKSTYLE ON
	}

}
