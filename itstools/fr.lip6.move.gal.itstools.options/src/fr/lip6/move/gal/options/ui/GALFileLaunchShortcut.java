package fr.lip6.move.gal.options.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import fr.lip6.move.gal.options.ui.CommonLaunchConstants;
import fr.lip6.move.gal.options.ui.IOption;

import org.eclipse.jface.dialogs.MessageDialog;


public class GALFileLaunchShortcut implements ILaunchShortcut {

	private final String ID_LAUNCH_TYPE;
	private final String defaultTool;
	private final IOptionsBuilder optionsBuilder;

	public GALFileLaunchShortcut(String launchID, String defaultTool, IOptionsBuilder optionsBuilder) {
		this.ID_LAUNCH_TYPE = launchID;
		this.defaultTool = defaultTool;
		this.optionsBuilder = optionsBuilder;
	}
	
	@Override
	public void launch(ISelection selection, String mode) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection iss = (IStructuredSelection) selection;
			for (Object resource : iss.toArray()) {
				if (resource instanceof IFile) {
					IFile file = (IFile) resource;
					if (file.getFileExtension()!=null && "gal".equals(file.getFileExtension()) ) {
						String fname = file.getRawLocation().toString();
						IProject fproj = file.getProject();
						launch(fname, fproj);
					}
				}
			}
		}
	}

	@Override
	public void launch(IEditorPart editor, String mode) {

	}

	public ILaunchConfigurationType getConfigurationType() {
		ILaunchManager lm= DebugPlugin.getDefault().getLaunchManager();
		return lm.getLaunchConfigurationType(ID_LAUNCH_TYPE);		
	}
	
	private void launch (String modelff,IProject curProj) {
		List<ILaunchConfiguration> configs = getCandidates(modelff, curProj, getConfigurationType());
		if (configs != null) {
			ILaunchConfiguration config = null;
			int count = configs.size();
			if (count == 1) {
				config = configs.get(0);
			} else if (count > 1) {
				config = chooseConfiguration(configs);
				if (config == null) {
					return;
				}
			}
			if (config == null) {
				config = createConfiguration(modelff, curProj);
			}
			if (config != null) {
				DebugUITools.launch(config, "run");
			}
		}
	}
	
	
	/**
	 * Returns a configuration from the given collection of configurations that should be launched,
	 * or <code>null</code> to cancel. Default implementation opens a selection dialog that allows
	 * the user to choose one of the specified launch configurations.  Returns the chosen configuration,
	 * or <code>null</code> if the user cancels.
	 * 
	 * @param configList list of configurations to choose from
	 * @return configuration to launch or <code>null</code> to cancel
	 */
	protected ILaunchConfiguration chooseConfiguration(List<ILaunchConfiguration> configList) {
		IDebugModelPresentation labelProvider = DebugUITools.newDebugModelPresentation();
		ElementListSelectionDialog dialog= new ElementListSelectionDialog(getShell(), labelProvider);
		dialog.setElements(configList.toArray());
		dialog.setTitle("Choose an existing run configuration");  
		dialog.setMessage("Please choose one of these compatible run configurations.");
		dialog.setMultipleSelection(false);
		int result = dialog.open();
		labelProvider.dispose();
		if (result == Window.OK) {
			return (ILaunchConfiguration) dialog.getFirstResult();
		}
		return null;		
	}
		
	private List<ILaunchConfiguration> getCandidates(String type, IProject curProj, ILaunchConfigurationType cType) {
		List<ILaunchConfiguration> candidateConfigs = Collections.emptyList();
		try {
			ILaunchConfiguration[] configs = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations(cType);
			candidateConfigs = new ArrayList<ILaunchConfiguration>(configs.length);
			for (int i = 0; i < configs.length; i++) {
				ILaunchConfiguration config = configs[i];
				if (config.getAttribute(CommonLaunchConstants.MODEL_FILE,"").equals(type)
						&& config.getAttribute(CommonLaunchConstants.PROJECT, "").equals(curProj.getName()) 
						) { //$NON-NLS-1$
					candidateConfigs.add(config);
				}
			}
		} catch (CoreException e) {
			Activator.log(e);
		}
		return candidateConfigs;
	}

	private ILaunchConfiguration createConfiguration (String modelff, IProject curProj) {
		ILaunchConfiguration config = null;
		try {
			ILaunchConfigurationType configType = getConfigurationType();
			String modelname = new File
					(modelff).getName();
			ILaunchConfigurationWorkingCopy wc = configType.newInstance(null, DebugPlugin.getDefault().getLaunchManager().generateLaunchConfigurationName(modelname)); 
			
			// set default values for anew LaunchConfiguration 
			wc.setAttribute(CommonLaunchConstants.PROJECT, curProj.getName());
			wc.setAttribute(CommonLaunchConstants.MODEL_FILE,modelff);
			wc.setAttribute(CommonLaunchConstants.TOOL, defaultTool);
			List<IOption> options = new ArrayList<>();
			optionsBuilder.addAllOptions(options);
			for (IOption opt : options) {
				opt.setDefaults(wc);
			}

			config = wc.doSave();
		} catch (CoreException ce) {
			MessageDialog.openError(getShell(), "Error creating launch configuration from shortcut.", ce.getStatus().getMessage());
		}
		return config;
	}

	private Shell getShell() {
		IWorkbench a1 = Activator.getDefault().getWorkbench();
		if (a1!=null) {
			IWorkbenchWindow a2 = a1.getActiveWorkbenchWindow();
			if (a2 !=null) {
				return a2.getShell();
			}
		}
		return null;
	}

}
