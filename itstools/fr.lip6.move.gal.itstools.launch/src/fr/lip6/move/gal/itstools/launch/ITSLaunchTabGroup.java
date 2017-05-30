package fr.lip6.move.gal.itstools.launch;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

import fr.lip6.move.gal.itstools.launch.devTools.IOption;
import fr.lip6.move.gal.itstools.launch.devTools.Option;

public class ITSLaunchTabGroup extends AbstractLaunchConfigurationTabGroup {

	public ITSLaunchTabGroup() {
		
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		/*Declaration et instanciation de l'option*/
		IOption<String> opt = new Option<String>("Scalar Potential","Set a recursive encoding strategy for scalar sets. \n Depth2 : (depth 2 levels) use 2 level depth for scalar sets. Integer provided defines level 2 block size. [DEFAULT: Depth2, with blocks size 1] \n   -depthRec INT : (depth recursive) use recursive encoding for scalar sets. Integer provided defines number of blocks at highest levels. \n    -DepthShallow INT : (depth shallow recursive) use alternative recursive encoding for scalar sets. Integer provided defines number of blocks at lowest level."
, "DEPTH2", fr.lip6.move.gal.itstools.launch.devTools.OptionType.MULTICHOICE);
		IOption<String> opt2 = new Option<String>("MultiChoices test","I'm a TooltTipText", "B", fr.lip6.move.gal.itstools.launch.devTools.OptionType.MULTICHOICE);
		
		Option<Boolean> opt3 = new Option<Boolean> ("Quiet", "Activate to get less verbose trace.", true, fr.lip6.move.gal.itstools.launch.devTools.OptionType.BOOLEAN);
		/*Declaration et Instantiation de l'onglet d'option*/
		OptionsTab otab = new OptionsTab();
		
		/*Ajout de l'option*/
		otab.addOption(opt);
		otab.addOption(opt2);
		otab.addOption(opt3);
		
		/*Affectation des potentiels valeurs d'options*/
		opt.setPotentialValues(new String[] {"DEPTH2","DEPTHREC","DEPTHSHALLOW"});
		opt2.setPotentialValues(new String[] {"A", "B", "C", "D"});
		
		ILaunchConfigurationTab[] tabs = {new MainTab(), otab , new CommonTab() };
		
		setTabs(tabs);
		
	}

}
