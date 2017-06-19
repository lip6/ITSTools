package fr.lip6.move.gal.itstools.launch.devTools;

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

import fr.lip6.move.gal.itstools.CommandLine;

public class OptionEnumWithText extends OptionEnum {

	private Text addedText;
	private String textValue;
	private String text_state;

	public OptionEnumWithText(String name, String tooltiptext, String defaultValue, String textValue) {
		super(name, tooltiptext, defaultValue);
		this.textValue = textValue;
		text_state = textValue;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addControl(Composite composite, IWidgetListener listener) {
		Composite label_combo_text_composite = new Composite(composite, 0);
		GridLayout layout = new GridLayout(3, true);
		label_combo_text_composite.setLayout(layout);
		Label label = new Label(label_combo_text_composite, SWT.WRAP);
		label.setText(getName());
		label.setToolTipText(getTooltiptext());
		setCombo(new Combo(label_combo_text_composite, SWT.NONE));
		getCombo().setItems(getPotentialValues());
		addedText = new Text(label_combo_text_composite, 0);
		addedText.setLayoutData(new GridData());
		getCombo().addSelectionListener(listener);
		getAddedText().addModifyListener(listener);

	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		Object currentValue, currentValueText;
		try {
			currentValue = configuration.getAttributes().get(getName());
			currentValueText = configuration.getAttributes().get(getName() + "Value");
			if (currentValue != null) {
				getCombo().setText((String) currentValue);
			} else {
				getCombo().setText(getDefaultValue());
			}
			// En général ça aurait pu être une liste mais il aurait fallu fixer
			// la place de la valeur-combo et celle du texte
			if (currentValueText != null) {
				addedText.setText((String) currentValueText);
			} else {
				getAddedText().setText(textValue);
			}

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		String combo_text_state = getCombo().getText();
		text_state = getAddedText().getText();
		configuration.setAttribute(getName(), combo_text_state);
		configuration.setAttribute(getName() + "Value", text_state);
	}

	public Text getAddedText() {
		return addedText;
	}

	@Override
	public void addFlagsToCommandLine(CommandLine cl, ILaunchConfiguration configuration) {

		String value;
		try {
			value = configuration.getAttribute(getName(), "");

			if (value.length() > 0) {
				String flagValue = getPotentialValuesAndFlags().get(value);
				boolean flagAdded = false;
				if (flagValue != null) {
					cl.addArg(flagValue);
					flagAdded = true;
				}

				// super.addFlagsToCommandLine(cl, configuration);
				if (flagAdded && text_state != null)
					cl.addArg(text_state);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy wc) {
		super.setDefaults(wc);
		wc.setAttribute(getName() + "Value", text_state);
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		boolean validEnumChoice = false;
		for (String s : getPotentialValues())
			if (s.equals(getCombo().getText())) {
				validEnumChoice = true;
				break;
			}
		if (validEnumChoice) {
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
