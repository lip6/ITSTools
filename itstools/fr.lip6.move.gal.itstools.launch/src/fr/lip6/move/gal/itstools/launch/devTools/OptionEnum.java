package fr.lip6.move.gal.itstools.launch.devTools;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class OptionEnum implements IOption<String> {
	
	private String defaultValue;
	private String name;
	private String tooltiptext;
	public String getTooltiptext() {
		return tooltiptext;
	}

	public void setTooltiptext(String tooltiptext) {
		this.tooltiptext = tooltiptext;
	}

	public String[] getPotentialValues() {
		return potentialValues;
	}

	public void setCombo(Combo combo) {
		this.combo = combo;
	}

	private Combo combo;
	private String[] potentialValues;
	
	
	public OptionEnum(String name, String tooltiptext, String defaultValue ) {
		this.defaultValue = defaultValue;
		this.name = name;
		this.tooltiptext = tooltiptext;
	}

	public void setPotentialValues(String[] values) {
		potentialValues = values;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getToolTip() {
		return tooltiptext;
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

	@Override
	public String getCurrentValue() {
		if (combo == null) return null;
		return combo.getText();
	}

	

	public Combo getCombo() {
		// TODO Auto-generated method stub
		return combo;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		Object currentValue;
		try {
			currentValue = configuration.getAttributes().get(name);
			if(currentValue != null)
				getCombo().setText((String)currentValue);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		String combo_state = getCombo().getText();
		configuration.setAttribute(getName(), combo_state);
		
	}

	@Override
	public void addControl(Composite composite, IWidgetListener listener) {
		Composite label_combo_composite = new Composite(composite, 0);
		GridLayout layout = new GridLayout(2, true);
		label_combo_composite.setLayout(layout);
		Label label = new Label(label_combo_composite, SWT.NULL);
		label.setText(name);
		label.setToolTipText(tooltiptext);
		combo = new Combo(label_combo_composite, SWT.NONE);
		
		combo.setItems(potentialValues);
		combo.addSelectionListener(listener);
	}

}
