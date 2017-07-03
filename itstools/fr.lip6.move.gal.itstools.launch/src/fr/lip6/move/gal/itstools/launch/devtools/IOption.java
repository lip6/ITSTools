package fr.lip6.move.gal.itstools.launch.devtools;



import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.widgets.Composite;

import fr.lip6.move.gal.itstools.CommandLine;


/**
 * An Option, that can be set/get to control behavior of a command line tool. 
 * @author ythierry, mamoussou
 *
 */
public interface IOption {

	/** The name of this option (unique).
	 * Typically used to label controls, and to interact with the LaunchConfiguration object.
	 * @return the name
	 */
	public String getName();
	/**
	 * A helpful text describing this option to end users.
	 * @return a readable text
	 */
	public String getToolTip();	
	
	/**
	 * This is called by the framework when displaying the option.
	 * @param configuration a configuration we read the option value from
	 */
	public void initializeFrom(ILaunchConfiguration configuration);

	/**
	 * This is called by the framework every time the user clicks "default".
	 * @param wc a LaunchConfiguration to update by setting the default value of this option.
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy wc);

	
	/**
	 * This is called by the framework when the user want to update a configuration using the option's current value.
	 * @param configuration the configuration to update
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration);
	
	/**
	 * Creates and adds a control to parent, allowing the end user to manipulater it.
	 * @param parent the containing SWT control
	 * @param listener a listener to attach to the control.
	 */
	public void addControl(Composite parent, IWidgetListener listener);
	/**
	 * Update the command line to add flags representing the current state of this option in the LaunchConfiguration. 
	 * @param cl the command line we are building
	 * @param configuration the state of the option is read from here
	 */
	public void addFlagsToCommandLine(CommandLine cl, ILaunchConfiguration configuration);
	
	/**
	 * Make sure that the LaunchConfiguration setting for this option is valid.
	 * @param launchConfig we read the current option state from here
	 * @return true if it looks good.
	 */
	public boolean isValid(ILaunchConfiguration launchConfig);
}
