package fr.lip6.move.gal.itstools.launch;

import java.io.File;

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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class MainTab extends AbstractLaunchConfigurationTab implements ModifyListener {
	
	private static final String DEFAULT_MODEL_FILE = "model.gal";
	private static final String[] LEGAL_EXTENSIONS = {"*.gal"};

	private FieldEditorPreferencePage page;
	private FileFieldEditor modelFileEditor;	
	
	@Override
	public void createControl(Composite parent) {
		Composite main = SWTFactory.createComposite(parent, 2, 1, GridData.FILL_BOTH);
		
		page = new FieldEditorPreferencePage(FieldEditorPreferencePage.GRID) {

			@Override
			public void createControl(Composite parentComposite) {
				noDefaultAndApplyButton();
				super.createControl(parentComposite);
			}
			
			@Override
			protected void createFieldEditors() {
				modelFileEditor = new FileFieldEditor(LaunchConstants.MODEL_FILE, LaunchConstants.MODEL_FILE, getFieldEditorParent());
				modelFileEditor.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile());
				modelFileEditor.getLabelControl(getFieldEditorParent()).setToolTipText("Select a .gal model file.");
				modelFileEditor.setFileExtensions(LEGAL_EXTENSIONS);
				modelFileEditor.setEmptyStringAllowed(false);
				modelFileEditor.getTextControl(getFieldEditorParent()).addModifyListener(MainTab.this);
				
				addField(modelFileEditor);
			}
			
//			public void propertyChange(PropertyChangeEvent event) {
//				updateApplyButton();
//				super.propertyChange(event);
//			}
//						
//			@Override
//			protected void updateApplyButton() {
//				MainTab.this.setDirty(true);
//				super.updateApplyButton();
//			}
			
		};
		
		page.createControl(main);
		Control pageControl = page.getControl();
		pageControl.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		setControl(main);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(LaunchConstants.MODEL_FILE, DEFAULT_MODEL_FILE);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			modelFileEditor.setStringValue(configuration.getAttribute(LaunchConstants.MODEL_FILE, DEFAULT_MODEL_FILE));
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(LaunchConstants.MODEL_FILE, modelFileEditor.getStringValue());
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
