package fr.lip6.move.gal.itstools.launch;

import java.util.List;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import fr.lip6.move.gal.itstools.launch.devtools.IOption;
import fr.lip6.move.gal.itstools.launch.devtools.IWidgetListener;

@SuppressWarnings("restriction")
public class OptionsTab extends AbstractLaunchConfigurationTab {

	/** Make sure we update the configuration state when widgets are touched.
	 * The effect is to compare current and previous settings, and thus compute if "apply, revert, ok" are enabled or not. */
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

	private List<IOption<?>> options;

	public OptionsTab(String name, List<IOption<?>> options) {
		this.options = options;
		this.name = name;
	}

	// LISTENER GENERAL
	private IWidgetListener listener = new WidgetListener();
	private String name;

	public void addOption(IOption<?> option) {
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
		for (IOption<?> opt : options) {
			opt.setDefaults(configuration);
		}
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
	}

	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
		return;
	}

	@Override
	public void deactivated(ILaunchConfigurationWorkingCopy workingCopy) {
		return;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		setErrorMessage(null);
		for (IOption<?> opt : options) {
			if (!opt.isValid(launchConfig)) {
				return false;
			}
		}
		return true;
	}

}
