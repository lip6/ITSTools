package fr.lip6.move.gal.ltsmin.launch;

import org.eclipse.debug.ui.ILaunchShortcut;

import fr.lip6.move.gal.options.ui.GALFileLaunchShortcut;

public class LTSMinLaunchShortcut extends GALFileLaunchShortcut implements ILaunchShortcut {

	public LTSMinLaunchShortcut() {
		super("fr.lip6.move.gal.ltsmin.launch.launchConfigurationType", "its-reach", new OptionsBuilder());
	}

}
