package fr.lip6.move.gal.itstools.launch;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import fr.lip6.move.gal.itstools.launch.devTools.IOption;
import fr.lip6.move.gal.itstools.launch.devTools.IWidgetListener;

@SuppressWarnings("restriction")
public class OptionsTab extends AbstractLaunchConfigurationTab implements ModifyListener{
	

	private List<IOption<?>> options = new LinkedList<>();

	public List<IOption<?>> getOptions() {
		return options;
	}

	private IWidgetListener listener = new WidgetListener();
	
	//LISTENER GENERAL

	

	private class WidgetListener implements IWidgetListener{
		
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}
		
		public void widgetDefaultSelected(SelectionEvent e) {/*do nothing*/}
		
		
		public void widgetSelected(SelectionEvent e) {
			updateLaunchConfigurationDialog();
		}
	}

	public void addOption(IOption<?> option){
		options.add(option);
	}
	

	
	@Override
	public void createControl(Composite parent) {
		Composite main = SWTFactory.createComposite(parent, 1, 3, GridData.FILL_BOTH);
		
	
		
		for (IOption<?> opt : options) {
			opt.addControl(main, listener);		
		}

		
		setControl(main);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		
	}


	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		
		for (IOption<?> opt : options) {
			opt.initializeFrom(configuration);
		}

	}
	

	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
	
		for (IOption<?> opt : options) {
			opt.performApply(configuration);
		}

		//A RETIRER JUSTE POUR LE DEBUG
			try {
				System.out.println(configuration.getAttributes());
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {}
	@Override
	public void deactivated(ILaunchConfigurationWorkingCopy workingCopy) {}
	@Override
	public String getName() {
		return "Reachable Formula";
	}

	@Override
	public void modifyText(ModifyEvent e) {
		
	}
	
	

}
