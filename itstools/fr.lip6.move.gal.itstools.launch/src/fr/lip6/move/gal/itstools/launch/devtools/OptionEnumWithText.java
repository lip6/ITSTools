package fr.lip6.move.gal.itstools.launch.devtools;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class OptionEnumWithText extends OptionEnum {

	private Text addedText;
	private String textDefault;

	public OptionEnumWithText(String name, String tooltiptext, String defaultValue, String textDefault) {
		super(name, tooltiptext, defaultValue);
		this.textDefault = textDefault;
	}

	@Override
	public void addControl(Composite composite, IWidgetListener listener) {
		Composite label_combo_text_composite = new Composite(composite, 0);
		GridLayout layout = new GridLayout(3, true);
		label_combo_text_composite.setLayout(layout);
		Label label = new Label(label_combo_text_composite, SWT.WRAP);
		label.setText(getName());
		label.setToolTipText(getToolTip());
		setCombo(new Combo(label_combo_text_composite, SWT.NONE));
		getCombo().setItems(getPotentialValues());
		addedText = new Text(label_combo_text_composite, 0);
		addedText.setLayoutData(new GridData());
		getCombo().addSelectionListener(listener);
		getAddedText().addModifyListener(listener);

	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);
		try {
			String currentValue = configuration.getAttribute(getName()+"text", textDefault);
			getAddedText().setText(currentValue);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(getName()+"text", getAddedText().getText());
		super.performApply(configuration);
	}

	public Text getAddedText() {
		return addedText;
	}

	@Override
	public void addFlagsToCommandLine(List<String> flags) {
		super.addFlagsToCommandLine(flags);		
		flags.add(getAddedText().getText());
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy wc) {
		super.setDefaults(wc);
		wc.setAttribute(getName() + "text", textDefault);
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		if (super.isValid(launchConfig)) {
			try {
				Double.parseDouble(getAddedText().getText());
			} catch (NumberFormatException nfe) {
				System.err.println(getAddedText().getText() + " is not a number!");  
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

}
