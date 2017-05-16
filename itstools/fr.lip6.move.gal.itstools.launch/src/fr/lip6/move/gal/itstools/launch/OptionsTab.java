package fr.lip6.move.gal.itstools.launch;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class OptionsTab extends AbstractLaunchConfigurationTab implements ModifyListener {
	
	private static final String DEFAULT_MODEL_FILE = "model.gal";
	private static final String[] LEGAL_EXTENSIONS = {"*.gal"};

	@Override
	public void createControl(Composite parent) {
		Composite main = SWTFactory.createComposite(parent, 1, 2, GridData.FILL_BOTH);
		Label l1= new Label(main, SWT.NONE);
		l1.setText("Model selected");
		
		setControl(main);
	}

	
	

		

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// configuration.setAttribute(LaunchConstants.MODEL_FILE, DEFAULT_MODEL_FILE);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
//		try {
//			fProjText.setText(configuration.getAttribute(LaunchConstants.PROJECT,ResourcesPlugin.getWorkspace().getRoot().getProjects()[0].getName()));
//			modelFileEditor.setStringValue(configuration.getAttribute(LaunchConstants.MODEL_FILE, DEFAULT_MODEL_FILE));
//		} catch (CoreException e) {
//			e.printStackTrace();
//		}
		
	}
	

	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
//		configuration.setAttribute(LaunchConstants.PROJECT, fProjText.getText().trim());		
//		configuration.setAttribute(LaunchConstants.MODEL_FILE, modelFileEditor.getStringValue());
		setDirty(false);
	}

	@Override
	public String getName() {
		return "Model selection";
	}

	@Override
	public void modifyText(ModifyEvent e) {
		setDirty(true);
		getLaunchConfigurationDialog().updateButtons();
	}
	

}
