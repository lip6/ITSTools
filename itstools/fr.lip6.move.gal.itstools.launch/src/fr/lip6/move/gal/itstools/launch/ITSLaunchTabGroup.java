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
		OptionsBuilder.addAllOptions(options);
		OptionsTab otab = new OptionsTab("Common", options, LaunchConstants.COMMON_FLAGS);
		
		List<IOption> reachOptions = new ArrayList<IOption>();
		OptionsBuilder.addAllReachOptions(reachOptions);
		OptionsTab rtab = new OptionsTab("Reach", reachOptions, LaunchConstants.REACH_FLAGS);
		
		List<IOption> ctlOptions = new ArrayList<IOption>();
		OptionsBuilder.addAllCTLOptions(ctlOptions);
		OptionsTab ctltab = new OptionsTab("CTL", ctlOptions, LaunchConstants.CTL_FLAGS);

		ILaunchConfigurationTab[] tabs = {new MainTab(), otab , rtab, ctltab , new CommonTab() };
		
		setTabs(tabs);
	}

}
