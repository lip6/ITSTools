package fr.lip6.move.gal.itstools.launch;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class MainTab implements ILaunchConfigurationTab {

	@Override
	public void createControl(Composite parent) {
//		Composite composite = (Composite) super.createDialogArea(parent);
//
//		page = new FieldEditorPreferencePage(FieldEditorPreferencePage.GRID) {
//			@Override
//			public void createControl(Composite parentComposite) {
//				noDefaultAndApplyButton();
//				super.createControl(parentComposite);
//			}
//
//			@SuppressWarnings("synthetic-access")
//			@Override
//			protected void createFieldEditors() {
//				exporterArgsEditor = new StringFieldEditor("", "Exporter arguments:", getFieldEditorParent());
//				// Hack-Alert: Set a dummy value before correct value, otherwise the validation won't work correctly,
//				// if the correct value is an empty string or null
//				exporterArgsEditor.setStringValue("XXX-dummy-XXX");
//				exporterArgsEditor.setStringValue(exporterArgs);
//				addField(exporterArgsEditor);
//
//				targetDirectoryEditor = new DirectoryFieldEditor("", "Target directory:", getFieldEditorParent());
//				targetDirectoryEditor.setEmptyStringAllowed(false);
//				targetDirectoryEditor.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
//				targetDirectoryEditor.setStringValue(targetDirectory.getPath());
//				addField(targetDirectoryEditor);
//			}
//			
//			@SuppressWarnings("synthetic-access")
//			@Override
//			protected void updateApplyButton() {
//				updateButtons(isValid());
//				super.updateApplyButton();
//			}
//		};
//		
//		page.createControl(composite);
//		Control pageControl = page.getControl();
//		pageControl.setLayoutData(new GridData(GridData.FILL_BOTH));
//		return pageControl;

	}

	@Override
	public Control getControl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSave() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setLaunchConfigurationDialog(ILaunchConfigurationDialog dialog) {
		// TODO Auto-generated method stub

	}

	@Override
	public void launched(ILaunch launch) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivated(ILaunchConfigurationWorkingCopy workingCopy) {
		// TODO Auto-generated method stub

	}

}
