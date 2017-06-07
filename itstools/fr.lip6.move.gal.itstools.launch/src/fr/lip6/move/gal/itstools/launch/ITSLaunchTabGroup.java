package fr.lip6.move.gal.itstools.launch;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

import fr.lip6.move.gal.itstools.launch.devTools.OptionBoolean;
import fr.lip6.move.gal.itstools.launch.devTools.OptionEnum;
import fr.lip6.move.gal.itstools.launch.devTools.OptionEnumWithText;
import fr.lip6.move.gal.itstools.launch.devTools.OptionText;
import fr.lip6.move.gal.itstools.launch.devTools.OptionText2;
import fr.lip6.move.gal.itstools.launch.devTools.ReachFormula;

public class ITSLaunchTabGroup extends AbstractLaunchConfigurationTabGroup {

	public ITSLaunchTabGroup() {
		
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		
		OptionsTab otab = new OptionsTab();
		
		ReachFormula.instanciate(otab);

		ILaunchConfigurationTab[] tabs = {new MainTab(), otab , new CommonTab() };
		
		setTabs(tabs);
		
	}

}
