package fr.lip6.move.gal.itstools.launch.devtools;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import fr.lip6.move.gal.itstools.CommandLine;

@SuppressWarnings("restriction")
public class OptionBoolean extends AbstractOption<Boolean> {
	
	private Button button;
	private String flag;

	public OptionBoolean(String name, String tooltip, boolean defaultValue) {
		super(name,tooltip,defaultValue);
	}


	@Override
	public void addControl(Composite composite, IWidgetListener listener) {
		Composite check_composite = new Composite(composite, SWT.FILL);
		GridLayout layout = new GridLayout(1, true);
		check_composite.setLayout(layout);
		button = SWTFactory.createCheckButton(check_composite, getName(), null, getDefaultValue(), 2);
		GridData layoutData = new GridData();
		button.setLayoutData(layoutData);
		button.setToolTipText(getToolTip());
		button.addSelectionListener(listener);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		//System.out.println("Configuration " + configuration);           
		try {
			Boolean currentValue = configuration.getAttribute(getName(), false);
			button.setSelection(currentValue);
		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		boolean button_state = button.getSelection();
		configuration.setAttribute(getName(), button_state);
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public void addFlagsToCommandLine(CommandLine cl, ILaunchConfiguration configuration) {
		try {
			Boolean value = configuration.getAttribute(getName(), false);
			if (value != getDefaultValue()) {
				cl.addArg(flag);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy wc) {
		wc.setAttribute(getName(), getDefaultValue());
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		return true;
	}

}
