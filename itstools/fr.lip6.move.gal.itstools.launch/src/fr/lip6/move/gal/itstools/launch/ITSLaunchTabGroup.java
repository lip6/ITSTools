package fr.lip6.move.gal.itstools.launch;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

import fr.lip6.move.gal.itstools.launch.devtools.ReachableFormula;

public class ITSLaunchTabGroup extends AbstractLaunchConfigurationTabGroup {

	public ITSLaunchTabGroup() {
		
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		
		ReachableFormula r = ReachableFormula.getInstance();
		OptionsTab otab = new OptionsTab(r);

		ILaunchConfigurationTab[] tabs = {new MainTab(), otab , new CommonTab() };
		
		setTabs(tabs);
		
	}

}
