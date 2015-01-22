package fr.lip6.move.gal.itstools.launch;

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
import org.eclipse.jface.dialogs.MessageDialog;


public class ITSLaunchShortcut implements ILaunchShortcut {

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

	private ILaunchConfigurationType getConfigurationType() {
		ILaunchManager lm= DebugPlugin.getDefault().getLaunchManager();
		return lm.getLaunchConfigurationType(LaunchConstants.ID_LAUNCH_TYPE);		
	}
	
	private void launch (String modelff,IProject curProj) {
		List<ILaunchConfiguration> configs = getCandidates(modelff, curProj, getConfigurationType());
		if(configs != null) {
			ILaunchConfiguration config = null;
			int count = configs.size();
			if(count == 1) {
				config = configs.get(0);
			}
			else if(count > 1) {
				config = chooseConfiguration(configs);
				if(config == null) {
					return;
				}
			}
			if (config == null) {
				config = createConfiguration(modelff,curProj);
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
		List<ILaunchConfiguration> candidateConfigs = Collections.EMPTY_LIST;
		try {
			ILaunchConfiguration[] configs = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations(cType);
			candidateConfigs = new ArrayList<ILaunchConfiguration>(configs.length);
			for (int i = 0; i < configs.length; i++) {
				ILaunchConfiguration config = configs[i];
				if (config.getAttribute(LaunchConstants.MODEL_FILE,"").equals(type)
						&& config.getAttribute(LaunchConstants.PROJECT, "").equals(curProj.getName()) 
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
			String modelname = new File(modelff).getName();
			ILaunchConfigurationWorkingCopy wc = configType.newInstance(null, DebugPlugin.getDefault().getLaunchManager().generateLaunchConfigurationName(modelname)); 
			
			wc.setAttribute(LaunchConstants.PROJECT, curProj.getName());
			wc.setAttribute(LaunchConstants.MODEL_FILE,modelff);
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
