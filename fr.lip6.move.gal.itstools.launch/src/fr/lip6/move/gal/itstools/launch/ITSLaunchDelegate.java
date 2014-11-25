package fr.lip6.move.gal.itstools.launch;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate2;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;

import fr.lip6.move.gal.itstools.preference.GalPreferencesActivator;
import fr.lip6.move.gal.itstools.preference.PreferenceConstants;

public class ITSLaunchDelegate extends LaunchConfigurationDelegate implements
		ILaunchConfigurationDelegate2 {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode,	ILaunch launch, IProgressMonitor monitor) throws CoreException {
		System.out.println("Yay, it runs ! " + configuration.getAttribute(LaunchConstants.MODEL_FILE, "model.gal"));
		
		
		File modelff = new File(configuration.getAttribute(LaunchConstants.MODEL_FILE, "model.gal"));
		
		String[] cmdLine = { configuration.getAttribute(PreferenceConstants.ITSREACH_EXE, GalPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.ITSREACH_EXE)),
				"-i",
				modelff.getName(),
				"-t",
				"GAL"
		};
		File workingDirectory = modelff.getParentFile();
		Process p = DebugPlugin.exec(cmdLine, workingDirectory.getAbsoluteFile() );
		
		IProcess proc = DebugPlugin.newProcess(launch, p, "ITS runner");
		
		System.out.println("done!");
		
	}

}
