package fr.lip6.move.gal.itstools.launch;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

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
	private Button quiet;
	private static final boolean DEFAULT_QUIET = false;

	private Map<String, Boolean> booleanOptions; 
	private Map<String, String> stringOptions; 
	private Map<String, String> EnumOptions;
	

	
	public OptionsTab() {
		super();
		booleanOptions = new LinkedHashMap<String, Boolean>();
		stringOptions = new LinkedHashMap<String, String>();
		EnumOptions = new LinkedHashMap<String, String>();
		booleanOptions.put(LaunchConstants.QUIET, DEFAULT_QUIET);
		//if (!addBooleanOption(LaunchConstants.QUIET, DEFAULT_QUIET))
		//setErrorMessage("E");
	}

	private boolean addBooleanOption(String optionName, boolean defaultValue){
		return booleanOptions.put(optionName, defaultValue);
	}


	@Override
	public void createControl(Composite parent) {
		Composite main = SWTFactory.createComposite(parent, 1, 3, GridData.FILL_BOTH);
//		Label l1= new Label(main, SWT.NONE);
//		l1.setText("Model selected");
		Group boolGroup = SWTFactory.createGroup(main, "Boolean Group", 3, 2, GridData.FILL_BOTH);
		Group stringGroup = SWTFactory.createGroup(main, "String Group", 3, 2, GridData.FILL_BOTH);
		Group enumGroup = SWTFactory.createGroup(main, "Enum Group", 3, 2, GridData.FILL_BOTH);
//		public static Text createText(Composite parent, int style, int hspan, int width, int height, int fill) {

		//Text text = SWTFactory.createText(StringGroup, 0, 2, 5, 3, 2);
		GridLayout gridLayout = new GridLayout(2, true);
		stringGroup.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 2;
		stringGroup.setLayoutData(gridData);
		new Label(stringGroup, SWT.NULL).setText("Label 1:");
		Text label1 = new Text(stringGroup, SWT.SINGLE | SWT.BORDER);
		
		new Label(stringGroup, SWT.NULL).setText("Label 2:");
		Text label2 = new Text(stringGroup, SWT.SINGLE | SWT.BORDER);
		quiet = new Button(boolGroup, SWT.CHECK);
		quiet.setText("quiet");
		quiet.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDirty(true);			
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});	
		quiet.setToolTipText("Activate to get less verbose trace.");
		setControl(main);
	}
//	@Override
//	public void  
	

		

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
		//JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
		configuration.setAttribute(LaunchConstants.QUIET, quiet.getSelection());		
//		configuration.setAttribute(LaunchConstants.MODEL_FILE, modelFileEditor.getStringValue());
		setDirty(false);
	}

	@Override
	public String getName() {
		return "Model Option";
	}

	@Override
	public void modifyText(ModifyEvent e) {
		setDirty(true);
		getLaunchConfigurationDialog().updateButtons();
	}
	
	

}
