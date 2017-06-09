package fr.lip6.move.gal.itstools.launch.devTools;

import java.util.HashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import fr.lip6.move.gal.itstools.CommandLine;

public class OptionEnum implements IOption<String> {
	
	private String defaultValue;
	private String name;
	private String tooltiptext;
	
	private Combo combo;
	private HashMap<String, String> potentialValuesAndFlags;
	private String flag;
	
	public void setFlag(String flag){
		this.flag = flag;
	}
	public String getTooltiptext() {
		return tooltiptext;
	}

	public void setTooltiptext(String tooltiptext) {
		this.tooltiptext = tooltiptext;
	}

	public String[] getPotentialValues() {
		return potentialValuesAndFlags.keySet().toArray(new String [potentialValuesAndFlags.size()]);
	}

	public void setCombo(Combo combo) {
		this.combo = combo;
	}

	
	
	
	public OptionEnum(String name, String tooltiptext, String defaultValue ) {
		this.defaultValue = defaultValue;
		this.name = name;
		this.tooltiptext = tooltiptext;
	}

	public void setPotentialValuesAndFlags(HashMap<String, String> potentialValuesAndFlags) {
		this.potentialValuesAndFlags = potentialValuesAndFlags;
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
		
		combo.setItems(getPotentialValues());
		combo.addSelectionListener(listener);
	}

	@Override
	public void addFlagsToCommandLine(CommandLine cl, ILaunchConfiguration configuration) {
		try {
			String value = configuration.getAttribute(name, "");
			if (value.length() > 0){
				if (flag != null) // utile dans OptionEnumWithText dont la method addFlag to Command appelle la méthode ici
					cl.addArg(flag);
				cl.addArg(potentialValuesAndFlags.get(value));
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

}
