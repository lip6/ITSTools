package fr.lip6.move.gal.itstools.launch;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.Launch;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class OptionsTabs extends AbstractLaunchConfigurationTab implements ModifyListener {
	
	private static final String DEFAULT_MODEL_FILE = "model.gal";
	private static final String[] LEGAL_EXTENSIONS = {"*.gal"};
	private Button quiet;
	private static final boolean DEFAULT_QUIET = false;

	private Map<String, Boolean> booleanOptions; 
	private Map<String, String> stringOptions; 
	private Map<String, String> EnumOptions;
	private Text label2;
	private Text label1;
	private Combo scalarPotential;
	private Button forward;
	private Button witness;
	private Button fairTime;
	private Text time_duration;
	private Button dddsdd;
	private Button help;
	private Text block_size;
	

	
	public OptionsTabs() {
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
		GridLayout gridLayoutG = new GridLayout(1, true);
		main.setLayout(gridLayoutG);
		/* Initialisation des groupes boolean, enum et String*/
		Group boolGroup = SWTFactory.createGroup(main, "Boolean Group ", 1, 2, GridData.FILL_BOTH);
		Group stringGroup = SWTFactory.createGroup(main, "String Group ", 1, 2, GridData.FILL_BOTH);
		Group enumGroup = SWTFactory.createGroup(main, "Enum Group ", 1, 2, GridData.FILL_BOTH);

		/*Mis-en-forme du groupe string*/
		GridLayout gridLayout = new GridLayout(2, true);
		stringGroup.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 2;
		stringGroup.setLayoutData(gridData);
		Label t_duration = new Label(stringGroup, SWT.NULL);
		t_duration.setText("Time Duration:");
		t_duration.setToolTipText("Maximum execution time");
		time_duration = new Text(stringGroup, SWT.SINGLE | SWT.BORDER);
		Label b_size = new Label(stringGroup, SWT.NULL);
		b_size.setText("Block Size");
		b_size.setToolTipText("Block size in Scalar encoding");

		block_size = new Text(stringGroup, SWT.SINGLE | SWT.BORDER);
		
		GridLayout gridLayoutB = new GridLayout(2, true);
		boolGroup.setLayout(gridLayoutB);
		
		forward = new Button(boolGroup, SWT.CHECK);
		forward.setText("Forward");
		forward.setToolTipText("Use Forward CTL model-checking (faster)");
		forward.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		witness= new Button(boolGroup, SWT.CHECK);
		witness.setText("witness");
		witness.setToolTipText("Produce a witness/counter-example");
		witness.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		fairTime= new Button(boolGroup, SWT.CHECK);
		fairTime.setText("fairTime");
		fairTime.setToolTipText("Force fair time elapse");
		fairTime.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		quiet = new Button(boolGroup, SWT.CHECK);
		
		quiet.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDirty(true);			
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});	
		
		dddsdd = new Button(boolGroup, SWT.CHECK);
		dddsdd.setText("dddsdd");
		dddsdd.setToolTipText("Privilege SDD encoding");
		dddsdd.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDirty(true);			
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});	
		
		help = new Button(boolGroup, SWT.CHECK);
		help.setText("help");
		help.setToolTipText("For Help (à modifier)");
		help.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDirty(true);			
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});	
//		private static final String SCALAR_PARAMETER = "Use recursive encodings for scalar";
//		private static final String DEPTH2 = "Depth2";
//		private static final String DEPTHREC = "DepthRec";
//		private static final String DEPTHSHALLOW = "DepthShallow";
//		private static final String GCTHRESHOLD = "GC Threshold, in MB";
		/*Mis-en-forme du groupe enum*/
		GridLayout gridLayoutE = new GridLayout(2, true);
		enumGroup.setLayout(gridLayoutE);
		GridData gridDataE = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridDataE.horizontalSpan = 2;
		enumGroup.setLayoutData(gridDataE);
		new Label(enumGroup, SWT.NULL).setText("Scalar Potential");
		scalarPotential = new Combo(enumGroup, SWT.NULL);
		scalarPotential.setItems(new String[] {"--","DEPTH2","DEPTHREC","DEPTHSHALLOW"});
		setControl(main);
	}
//	@Override
//	public void  
	

	private void quiet_defaults(ILaunchConfiguration configuration){
		quiet.setSelection(true);
		try {
			configuration.getWorkingCopy().setAttribute(LaunchConstants.QUIET, true);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//setDirty(true);
	}

	private void enum_defaults(ILaunchConfiguration configuration){
		scalarPotential.setItems("--");
		//Possiblement retirer la configuration associeé à l'enum
		try {
			configuration.getWorkingCopy().removeAttribute("");
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//setDirty(true);
	}
	private void string_defaults(ILaunchConfiguration configuration){
		time_duration.setText("60"); //fr.lip6.move.coloane.projects.its.checks.AbstractCheckService
		label2.setText("0");
		//configuration.setAttribute(LaunchConstants.QUIET, true);
		//setDirty(true);
	}
	
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		quiet_defaults(configuration);
		enum_defaults(configuration);
		string_defaults(configuration);
		setDirty(true);
	}

	
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		boolean quiet_state;
		try {
			quiet_state = configuration.getAttribute(LaunchConstants.QUIET, true);
			quiet.setSelection(quiet_state);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
