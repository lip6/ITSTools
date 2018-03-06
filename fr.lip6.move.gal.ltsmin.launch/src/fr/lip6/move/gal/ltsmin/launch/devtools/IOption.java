package fr.lip6.move.gal.ltsmin.launch.devtools;



import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.widgets.Composite;

import fr.lip6.move.gal.ltsmin.launch.LaunchConstants;


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
	 * Update the FLAGS of the configuration to reflect the state of the controls.
	 * This is called by the framework when the user want to update a configuration using the option's current value.
	 * @param configuration the configuration to update
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration, String flagID);
	
	/**
	 * Creates and adds a control to parent, allowing the end user to manipulater it.
	 * @param parent the containing SWT control
	 * @param listener a listener to attach to the control.
	 */
	public void addControl(Composite parent, IWidgetListener listener);
	
	/** Grab the appropriate configuration setting and pass it to
	 * version taking a list of String.
	 */
	default public void addFlagsToCommandLine(ILaunchConfigurationWorkingCopy configuration, String flagID){
		try {
			List<String> flags = configuration.getAttribute(flagID, new ArrayList<>());
			addFlagsToCommandLine(flags);
			configuration.setAttribute(flagID, flags);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Update the command line list of flags to reflect the current selection state of this option. 
	 * @param flags the command line list of flags we are building
	 */
	public void addFlagsToCommandLine(List<String> flags);
	
	/**
	 * Make sure that the LaunchConfiguration setting for this option is valid.
	 * @param launchConfig we read the current option state from here
	 * @return true if it looks good.
	 */
	public boolean isValid(ILaunchConfiguration launchConfig);
}
