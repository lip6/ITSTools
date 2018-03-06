package fr.lip6.move.gal.ltsmin.launch;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
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

@SuppressWarnings("restriction")
public class MainTab extends AbstractLaunchConfigurationTab implements ModifyListener {
	
	private static final String DEFAULT_MODEL_FILE = "model.gal";
	private static final String[] LEGAL_EXTENSIONS = {"*.gal"};

	private FieldEditorPreferencePage page;
	private FileFieldEditor modelFileEditor;
	private Combo combo;	
	
	@Override
	public void createControl(Composite parent) {
		Composite main = SWTFactory.createComposite(parent, 1, 3, GridData.FILL_BOTH);
		addToolControl(main);

		createProjectEditor(main);
		
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
		};
		
		page.createControl(main);
				
		Control pageControl = page.getControl();
		pageControl.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		setControl(main);
	}

	public void addToolControl(Composite composite) {
		Composite label_combo_composite = new Composite(composite, 0);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		label_combo_composite.setLayout(layout);
		Label label = new Label(label_combo_composite, SWT.NONE);
		
		GridData g = new GridData(SWT.RIGHT);
		label.setLayoutData(g);
		GridData g1 = new GridData(SWT.RIGHT);
		label.setText("Tool to run :");
		label.setToolTipText("Choose which tool to run : reachability/safety with its-reach, CTL with its-ctl, LTL with its-ltl.");
		combo = new Combo(label_combo_composite, SWT.RIGHT);
		
	//	combo.setLayoutData(g);
		combo.setLayoutData(g1);
		combo.setItems("its-reach","its-ctl","its-ltl");
		combo.addSelectionListener(fListener);
	}
	
	/**
	 * A listener which handles widget change events for the controls
	 * in this tab.
	 */
	private class WidgetListener implements ModifyListener, SelectionListener {
		
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}
		
		public void widgetDefaultSelected(SelectionEvent e) {/*do nothing*/}
		
		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			if (source == fProjButton) {
				IProject project = chooseProject();
				if (project == null) {
					return;
				}
				String projectName = project.getName();
				fProjText.setText(projectName);	
			} else {
				updateLaunchConfigurationDialog();
			}
		}
	}
		
		protected static final String EMPTY_STRING = ""; //$NON-NLS-1$
		//Project UI widgets
		protected Text fProjText;

		private Button fProjButton;
		
		private WidgetListener fListener = new WidgetListener();
	/**
	 * Creates the widgets for specifying a main type.
	 * Inspired from org.eclipse.jdt.internal.debug.ui.launcher.AbstractJavaMainTab
	 * @param parent the parent composite
	 */
	protected void createProjectEditor(Composite parent) {
		Font font= parent.getFont();
		Group group= new Group(parent, SWT.NONE);
		group.setText("Project :"); 
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);
		group.setFont(font);
		fProjText = new Text(group, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fProjText.setLayoutData(gd);
		fProjText.setFont(font);
		fProjText.addModifyListener(fListener);
		fProjButton = createPushButton(group, "Browse...", null); 
		fProjButton.addSelectionListener(fListener);
	}	
	
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(LaunchConstants.MODEL_FILE, DEFAULT_MODEL_FILE);
		configuration.setAttribute(LaunchConstants.TOOL, "its-reach");
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			fProjText.setText(configuration.getAttribute(LaunchConstants.PROJECT,ResourcesPlugin.getWorkspace().getRoot().getProjects()[0].getName()));
			modelFileEditor.setStringValue(configuration.getAttribute(LaunchConstants.MODEL_FILE, DEFAULT_MODEL_FILE));
			combo.setText(configuration.getAttribute(LaunchConstants.TOOL, "its-reach"));
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	private IProject chooseProject() {
		ILabelProvider labelProvider= new LabelProvider() {
			public String getText(Object element) {
			if (element instanceof IProject) {
				IProject proj = (IProject) element;
				return proj.getName();
			}
			return super.getText(element);
		} };
		ElementListSelectionDialog dialog= new ElementListSelectionDialog(getShell(), labelProvider);
		dialog.setTitle("Project selection"); 
		dialog.setMessage("Please choose a project"); 
		dialog.setElements(ResourcesPlugin.getWorkspace().getRoot().getProjects(0));
		IProject curProject= getProject();
		if (curProject != null) {
			dialog.setInitialSelections(new Object[] { curProject });
		}
		if (dialog.open() == Window.OK) {			
			return (IProject) dialog.getFirstResult();
		}		
		return null;		
	}
	
	
	private IProject getProject() {
		String projName = fProjText.getText().trim();
		if (projName.length() < 1) {
			return null;
		}
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
		
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(LaunchConstants.PROJECT, fProjText.getText().trim());		
		configuration.setAttribute(LaunchConstants.MODEL_FILE, modelFileEditor.getStringValue());
		configuration.setAttribute(LaunchConstants.TOOL, combo.getText());
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
