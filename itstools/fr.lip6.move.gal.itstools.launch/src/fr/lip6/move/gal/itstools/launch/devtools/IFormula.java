package fr.lip6.move.gal.itstools.launch.devtools;

import java.util.Collection;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

import fr.lip6.move.gal.itstools.CommandLine;

public interface IFormula {

	public  void setDefaults(ILaunchConfigurationWorkingCopy wc);
	public  void addFlags(CommandLine cl, ILaunchConfiguration configuration);
	public Collection<IOption<?>> getOptions();
}
