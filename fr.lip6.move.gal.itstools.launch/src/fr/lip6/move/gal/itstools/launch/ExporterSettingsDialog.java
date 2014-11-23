package fr.lip6.move.gal.itstools.launch;


import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;


/** This very nice wrap of Field based Preference page in a dialog is courtesy of :
 * http://www.subshell.com/en/subshell/blog/article-How-to-use-JFace-field-editors-in-a-custom-dialog100.html
 * 
 * @author Jens Theeﬂ
 *
 */
public class ExporterSettingsDialog extends Dialog {
	private String exporterArgs;
	private File targetDirectory;

	private FieldEditorPreferencePage page;
	private StringFieldEditor exporterArgsEditor;
	private DirectoryFieldEditor targetDirectoryEditor;


	public ExporterSettingsDialog(Shell parentShell, String exporterArgs, File targetDirectory) {
		super(parentShell);
		this.exporterArgs = exporterArgs;
		this.targetDirectory = targetDirectory;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Export Settings");
		newShell.setSize(500, 170);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		page = new FieldEditorPreferencePage(FieldEditorPreferencePage.GRID) {
			@Override
			public void createControl(Composite parentComposite) {
				noDefaultAndApplyButton();
				super.createControl(parentComposite);
			}

			@SuppressWarnings("synthetic-access")
			@Override
			protected void createFieldEditors() {
				exporterArgsEditor = new StringFieldEditor("", "Exporter arguments:", getFieldEditorParent());
				// Hack-Alert: Set a dummy value before correct value, otherwise the validation won't work correctly,
				// if the correct value is an empty string or null
				exporterArgsEditor.setStringValue("XXX-dummy-XXX");
				exporterArgsEditor.setStringValue(exporterArgs);
				addField(exporterArgsEditor);

				targetDirectoryEditor = new DirectoryFieldEditor("", "Target directory:", getFieldEditorParent());
				targetDirectoryEditor.setEmptyStringAllowed(false);
				targetDirectoryEditor.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
				targetDirectoryEditor.setStringValue(targetDirectory.getPath());
				addField(targetDirectoryEditor);
			}
			
			@SuppressWarnings("synthetic-access")
			@Override
			protected void updateApplyButton() {
				updateButtons(isValid());
				super.updateApplyButton();
			}
		};
		
		page.createControl(composite);
		Control pageControl = page.getControl();
		pageControl.setLayoutData(new GridData(GridData.FILL_BOTH));
		return pageControl;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		updateButtons(page.isValid());
	}

	private void updateButtons(boolean isValid) {
		Button okButton = getButton(IDialogConstants.OK_ID);
		if (okButton != null) {
			okButton.setEnabled(isValid);
		}
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			exporterArgs = exporterArgsEditor.getStringValue();
			targetDirectory = new File(targetDirectoryEditor.getStringValue());
		}
		super.buttonPressed(buttonId);
	}

	public String getExporterArgs() {
		return exporterArgs;
	}

	public File getTargetDirectory() {
		return targetDirectory;
	}
}