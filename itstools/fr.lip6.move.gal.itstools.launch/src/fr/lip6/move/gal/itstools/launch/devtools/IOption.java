package fr.lip6.move.gal.itstools.launch.devtools;



import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.widgets.Composite;

import fr.lip6.move.gal.itstools.CommandLine;

public interface IOption<T> {

	public String getName();
	public String getToolTip();
	public T getDefaultValue();
	
	
	public void initializeFrom(ILaunchConfiguration configuration);
	public void performApply(ILaunchConfigurationWorkingCopy configuration);
	
	public void addControl(Composite composite, IWidgetListener listener);
	public void addFlagsToCommandLine(CommandLine cl, ILaunchConfiguration configuration);
	public void setDefaults(ILaunchConfigurationWorkingCopy wc);
	public boolean isValid(ILaunchConfiguration launchConfig);
}
