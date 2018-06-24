package fr.lip6.move.gal.itstools.launch;

import org.eclipse.debug.ui.ILaunchShortcut;
import fr.lip6.move.gal.options.ui.GALFileLaunchShortcut;


public class ITSLaunchShortcut extends GALFileLaunchShortcut implements ILaunchShortcut {

	public ITSLaunchShortcut() {
		super("fr.lip6.move.gal.itstools.launch.launchConfigurationType", "its-reach", new OptionsBuilder());
	}

}
