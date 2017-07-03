package fr.lip6.move.gal.itstools.launch;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

import fr.lip6.move.gal.itstools.launch.devtools.IOption;

public class ITSLaunchTabGroup extends AbstractLaunchConfigurationTabGroup {

	public ITSLaunchTabGroup() {
		
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		
		List<IOption> options = new ArrayList<IOption>();
		ReachabilityOptionsBuilder.addAllOptions(options);
		OptionsTab otab = new OptionsTab("General", options);

		ILaunchConfigurationTab[] tabs = {new MainTab(), otab , new CommonTab() };
		
		setTabs(tabs);
	}

}
