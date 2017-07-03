package fr.lip6.move.gal.itstools.launch;

import java.util.logging.Logger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate2;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;

import fr.lip6.move.gal.flatten.popup.actions.ConsoleAdder;
import fr.lip6.move.gal.itstools.CommandLine;

public class ITSLaunchDelegate extends LaunchConfigurationDelegate implements
ILaunchConfigurationDelegate2 {


	@Override
	public void launch(ILaunchConfiguration configuration, String mode,	ILaunch launch, IProgressMonitor monitor) throws CoreException {
		ConsoleAdder.startConsole();

		CommandLine cl = CommandLineBuilder.buildCommand(configuration);

		// Bring it all together for the invocation
		// full argument list		
		Logger.getLogger("fr.lip6.move.gal").fine("Running command line " + cl);

		// Define the process
		Process p = DebugPlugin.exec(cl.getArgs(), cl.getWorkingDir().getAbsoluteFile() );

		// Let the DebugPlugin manage running the process
		IProcess proc = DebugPlugin.newProcess(launch, p, "ITS runner");
		// System.out.println("done!");
	}

}
