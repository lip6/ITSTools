package fr.lip6.move.gal.itstools.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate2;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;

public class ITSLaunchDelegate extends LaunchConfigurationDelegate implements
		ILaunchConfigurationDelegate2 {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode,	ILaunch launch, IProgressMonitor monitor) throws CoreException {
		System.out.println("Yay, it runs ! " + configuration.getAttribute(LaunchConstants.MODEL_FILE, "model.gal"));

	}

}
