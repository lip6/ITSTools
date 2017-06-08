package fr.lip6.move.gal.itstools.launch.devTools;



import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.widgets.Composite;

public interface IOption<T> {

	public String getName();
	public String getToolTip();
	public T getDefaultValue();
	public T getCurrentValue();
	
	public void initializeFrom(ILaunchConfiguration configuration);
	public void performApply(ILaunchConfigurationWorkingCopy configuration);
	
	public void addControl(Composite composite, IWidgetListener listener);
	//void setControl(Composite composite, Function<Void, Void> func); YANN
}
