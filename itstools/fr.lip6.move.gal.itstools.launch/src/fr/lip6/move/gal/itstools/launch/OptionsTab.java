package fr.lip6.move.gal.itstools.launch;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import fr.lip6.move.gal.itstools.launch.devtools.IFormula;
import fr.lip6.move.gal.itstools.launch.devtools.IOption;
import fr.lip6.move.gal.itstools.launch.devtools.IWidgetListener;
import fr.lip6.move.gal.itstools.launch.devtools.ReachableFormula;

@SuppressWarnings("restriction")
public class OptionsTab extends AbstractLaunchConfigurationTab {

	/** Make sure we update the config state when widgets are touched */
	private class WidgetListener implements IWidgetListener {
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			/* do nothing */}

		public void widgetSelected(SelectionEvent e) {
			updateLaunchConfigurationDialog();
		}
	}
	
	
	private IFormula formula ;
	public OptionsTab(IFormula formula) {
		super();
		this.formula = formula;
	}
	
	// LISTENER GENERAL
	private IWidgetListener listener = new WidgetListener();

	public void addOption(IOption<?> option) {
		formula.getOptions().add(option);
	}

	@Override
	public void createControl(Composite parent) {
		Composite main = SWTFactory.createComposite(parent, 1, 3, GridData.FILL_BOTH);

		for (IOption<?> opt : formula.getOptions()) {
			opt.addControl(main, listener);
		}

		setControl(main);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		ReachableFormula.getInstance().setDefaults(configuration);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {

		for (IOption<?> opt : formula.getOptions()) {
			opt.initializeFrom(configuration);
		}

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {

		for (IOption<?> opt : formula.getOptions()) {
			opt.performApply(configuration);
		}

		// A RETIRER JUSTE POUR LE DEBUG
//		try {
//			System.out.println(configuration.getAttributes());
//		} catch (CoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {return;
	}

	@Override
	public void deactivated(ILaunchConfigurationWorkingCopy workingCopy) {return;
	}

	@Override
	public String getName() {
		return "General Options";
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		setErrorMessage(null);
		for (IOption<?> opt : formula.getOptions()) {
			if (!opt.isValid(launchConfig)) {
				return false;
			}
		}
		return true;
	}

	

}
