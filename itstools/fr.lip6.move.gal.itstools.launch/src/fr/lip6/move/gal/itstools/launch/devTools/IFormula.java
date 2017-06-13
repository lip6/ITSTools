package fr.lip6.move.gal.itstools.launch.devTools;

import java.util.Collection;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

import fr.lip6.move.gal.itstools.CommandLine;

public interface IFormula {

	public  void setDefaultValue(ILaunchConfigurationWorkingCopy wc);
	public  void addFlags(CommandLine cl, ILaunchConfiguration configuration);
	public Collection<IOption<?>> getOptions();
}
