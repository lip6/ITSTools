package fr.lip6.move.gal.ltsmin.launch;

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
		OptionsBuilder.addAllGeneralOptions(options);
		OptionsTab otab = new OptionsTab("Common", options, LaunchConstants.COMMON_FLAGS);
		
		List<IOption> seqOptions = new ArrayList<IOption>();
		OptionsBuilder.addAllSeqOptions(seqOptions);
		OptionsTab seqtab = new OptionsTab("SEQ", seqOptions, LaunchConstants.SEQ_FLAGS);
		
		List<IOption> mcOptions = new ArrayList<IOption>();
		OptionsBuilder.addAllSeqOptions(mcOptions);
		OptionsTab mctab = new OptionsTab("MC", mcOptions, LaunchConstants.MC_FLAGS);
		
		List<IOption> symOptions = new ArrayList<IOption>();
		OptionsBuilder.addAllSeqOptions(symOptions);
		OptionsTab symtab = new OptionsTab("SYM", seqOptions, LaunchConstants.SYM_FLAGS);
		
		final String [] tools = {"pins2lts-mc","pins2lts-seq"};
//		,"pins2lts-sym"  or sym (symbolic)
		ILaunchConfigurationTab[] tabs = {new MainTab(tools,"Choose which tool to run : mc (multi-core), seq (sequential)."), otab , seqtab, mctab, symtab,  new CommonTab() };
		
		setTabs(tabs);
	}

}
