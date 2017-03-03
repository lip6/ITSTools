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
package fr.lip6.move.coloane.projects.its.plugin;

import fr.lip6.move.coloane.projects.its.dialogs.FileBrowserField;
import fr.lip6.move.coloane.projects.its.obs.ISimpleObserver;
import fr.lip6.move.coloane.projects.its.ui.forms.ITSEditorPlugin;

import java.io.File;
import java.net.URI;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The preferences page contributed by the plugin.
 * 
 * @author Yann Thierry-Mieg
 */
public final class ITSPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private FileBrowserField reachField;
	private FileBrowserField ctlField;
	private FileBrowserField orderField;
	private FileBrowserField pythonField;

	/**
	 * Creates the composite which will contain all the preference controls for
	 * this page.
	 * 
	 * @param parent
	 *            the parent composite
	 * @return the composite for this page
	 */
	private Composite createComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
				| GridData.HORIZONTAL_ALIGN_FILL));
		return composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite composite = createComposite(parent);
		createOpenModeGroup(composite);
		applyDialogFont(composite);
		return composite;
	}

	/**
	 * Build the area with dot detection options.
	 * 
	 * @param composite
	 *            the parent in which we add stuff.
	 */
	private void createOpenModeGroup(Composite composite) {
		{
			Group buttonComposite = new Group(composite, SWT.LEFT);
			GridLayout layout = new GridLayout();
			buttonComposite.setLayout(layout);
			GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
					| GridData.GRAB_HORIZONTAL);
			buttonComposite.setLayoutData(data);
			buttonComposite.setText("its-reach executable to use");
			reachField = new FileBrowserField(buttonComposite);
			URI path = ITSEditorPlugin.getDefault().getITSReachPath();
			if (path != null) {
				reachField.setText(path.getPath());
			}
			reachField.setToolTipText("Select its-reach executable.");
			reachField.setLayoutData(data);

			reachField.addObserver(new ISimpleObserver() {
				public void update() {
					browserChanged(reachField.getText(),
							ITSEditorPlugin.ITS_REACH_NAME);
				}
			});
		}

		{
			Group buttonComposite = new Group(composite, SWT.LEFT);
			GridLayout layout = new GridLayout();
			buttonComposite.setLayout(layout);
			GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
					| GridData.GRAB_HORIZONTAL);
			buttonComposite.setLayoutData(data);
			data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
					| GridData.GRAB_HORIZONTAL);
			buttonComposite.setLayoutData(data);
			buttonComposite.setText("its-ctl executable to use");

			ctlField = new FileBrowserField(buttonComposite);
			ctlField.setToolTipText("Select its-reach executable.");
			URI path = ITSEditorPlugin.getDefault().getITSCTLPath();
			if (path != null) {
				ctlField.setText(path.getPath());
			}
			ctlField.setLayoutData(data);

			ctlField.addObserver(new ISimpleObserver() {
				public void update() {
					browserChanged(ctlField.getText(),
							ITSEditorPlugin.ITS_CTL_NAME);
				}
			});
		}

		{
			Group buttonComposite = new Group(composite, SWT.LEFT);
			GridLayout layout = new GridLayout();
			buttonComposite.setLayout(layout);
			GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
					| GridData.GRAB_HORIZONTAL);
			buttonComposite.setLayoutData(data);
			data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
					| GridData.GRAB_HORIZONTAL);
			buttonComposite.setLayoutData(data);
			buttonComposite.setText("IGenerateOrder.py executable to use");

			orderField = new FileBrowserField(buttonComposite);
			URI path = ITSEditorPlugin.getDefault().getOrderingPath();
			if (path != null) {
				orderField.setText(path.getPath());
			}
			orderField.setToolTipText("Select IGenerateOrder.py executable.");
			orderField.setLayoutData(data);

			orderField.addObserver(new ISimpleObserver() {
				public void update() {
					browserChanged(orderField.getText(),
							ITSEditorPlugin.ORDERING_NAME);
				}
			});
		}
		{
			Group buttonComposite = new Group(composite, SWT.LEFT);
			GridLayout layout = new GridLayout();
			buttonComposite.setLayout(layout);
			GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
					| GridData.GRAB_HORIZONTAL);
			buttonComposite.setLayoutData(data);
			data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
					| GridData.GRAB_HORIZONTAL);
			buttonComposite.setLayoutData(data);
			buttonComposite.setText("python executable to use");

			pythonField = new FileBrowserField(buttonComposite);
			URI path = ITSEditorPlugin.getDefault().getPythonPath();
			if (path != null) {
				pythonField.setText(path.getPath());
			}
			pythonField.setToolTipText("Select python executable.");
			pythonField.setLayoutData(data);

			pythonField.addObserver(new ISimpleObserver() {
				public void update() {
					browserChanged(pythonField.getText(),
							ITSEditorPlugin.PYTHON_PATH);
				}
			});
		}

	}

	/**
	 * Called when user has done something. Grab the path text.
	 * 
	 * @param newText
	 *            the new text that is supposed to refer to a file with
	 *            compatible name
	 * @param exeName
	 *            expected name for executable we seek.
	 */
	public final void browserChanged(String newText, String exeName) {
		setErrorMessage(null);
		setMessage(null);
		if (newText.length() == 0) {
			setErrorMessage("Please enter a path.");
			setValid(false);
			return;
		}
		File exeFile = new File(newText);
		String fileName = exeFile.getName();
		int extensionPos;
		while ((extensionPos = fileName.lastIndexOf('.')) > 0) {
			fileName = fileName.substring(0, extensionPos);
		}
		if (!exeFile.exists()) {
			setErrorMessage(newText + " doesn't exist");
			setValid(false);
		} else if (exeFile.isDirectory()) {
			setErrorMessage(newText + " is a directory");
			setValid(false);
		} else if (!ITSEditorPlugin.isExecutable(exeFile)) {
			setMessage(newText + " is not executable!",
					IMessageProvider.WARNING);
			setValid(false);
		} else if (!exeName.equalsIgnoreCase(fileName)) {
			setMessage("The file name should be " + exeName,
					IMessageProvider.WARNING);
			setValid(false);
		} else {
			setValid(true);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see IWorkbenchPreferencePage
	 */
	public void init(IWorkbench workbench) {
	}

	/**
	 * The user has pressed OK or Apply. Store this page's values.
	 * 
	 * @return true
	 */
	@Override
	public final boolean performOk() {
		ITSEditorPlugin itsplugin = ITSEditorPlugin.getDefault();
		itsplugin.setITSReachPath(reachField.getText());
		itsplugin.setITSCTLPath(ctlField.getText());
		itsplugin.setOrderingPath(orderField.getText());
		itsplugin.setPythonPath(pythonField.getText());
		return true;
	}

}
