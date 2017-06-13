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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import fr.lip6.move.gal.itstools.launch.devTools.ReachFormula;

public class MainTab extends AbstractLaunchConfigurationTab implements ModifyListener {
	
	private static final String DEFAULT_MODEL_FILE = "model.gal";
	private static final String[] LEGAL_EXTENSIONS = {"*.gal"};

	private FieldEditorPreferencePage page;
	private FileFieldEditor modelFileEditor;	
	
	@Override
	public void createControl(Composite parent) {
		Composite main = SWTFactory.createComposite(parent, 1, 2, GridData.FILL_BOTH);
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
			}
			else {
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
		System.out.println("oraaaaammm\n\nbraaaaaoooommmmm");

		configuration.setAttribute(LaunchConstants.MODEL_FILE, DEFAULT_MODEL_FILE);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			fProjText.setText(configuration.getAttribute(LaunchConstants.PROJECT,ResourcesPlugin.getWorkspace().getRoot().getProjects()[0].getName()));
			modelFileEditor.setStringValue(configuration.getAttribute(LaunchConstants.MODEL_FILE, DEFAULT_MODEL_FILE));
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
		try {
			System.out.println(configuration.getWorkingCopy().getAttributes());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
