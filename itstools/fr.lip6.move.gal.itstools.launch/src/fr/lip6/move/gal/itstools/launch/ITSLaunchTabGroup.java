package fr.lip6.move.gal.itstools.launch;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

import fr.lip6.move.gal.options.ui.IOption;
import fr.lip6.move.gal.options.ui.MainTab;

public class ITSLaunchTabGroup extends AbstractLaunchConfigurationTabGroup {

	public ITSLaunchTabGroup() {
		
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		
		List<IOption> options = new ArrayList<IOption>();
		OptionsBuilder.addAllCommonOptions(options);
		OptionsTab otab = new OptionsTab("Common", options, LaunchConstants.COMMON_FLAGS);
		
		List<IOption> orderOptions = new ArrayList<IOption>();
		OptionsBuilder.addAllOrderOptions(orderOptions);
		OptionsTab ordtab = new OptionsTab("Order", orderOptions, LaunchConstants.ORDER_FLAGS);

		List<IOption> reachOptions = new ArrayList<IOption>();
		OptionsBuilder.addAllReachOptions(reachOptions);
		OptionsTab rtab = new OptionsTab("Reach", reachOptions, LaunchConstants.REACH_FLAGS);
		
		List<IOption> ctlOptions = new ArrayList<IOption>();
		OptionsBuilder.addAllCTLOptions(ctlOptions);
		OptionsTab ctltab = new OptionsTab("CTL", ctlOptions, LaunchConstants.CTL_FLAGS);

		List<IOption> ltlOptions = new ArrayList<IOption>();
		OptionsBuilder.addAllLTLOptions(ltlOptions);
		OptionsTab ltltab = new OptionsTab("LTL", ltlOptions, LaunchConstants.LTL_FLAGS);

		
		final String [] tools = {"its-reach","its-ctl","its-ltl"};
		ILaunchConfigurationTab[] tabs = {new MainTab(tools,"Choose which tool to run : reachability/safety with its-reach, CTL with its-ctl, LTL with its-ltl."), otab , ordtab, rtab, ctltab , ltltab,  new CommonTab() };
		
		setTabs(tabs);
	}

}
