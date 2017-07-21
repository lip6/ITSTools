package fr.lip6.move.gal.itstools.launch.devtools;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class OptionEnum extends AbstractOption<String> {
	private Combo combo;	

	public OptionEnum(String name, String tooltiptext, String defaultValue) {
		super(name, tooltiptext, defaultValue);
	}

	private Map<String, String> potentialValuesAndFlags;	
	public Map<String, String> getPotentialValuesAndFlags() {
		return potentialValuesAndFlags;
	}	
	
	public String[] getPotentialValues() {
		return potentialValuesAndFlags.keySet().toArray(new String [potentialValuesAndFlags.size()]);
	}

	public void setCombo(Combo combo) {
		this.combo = combo;
	}

	public void setPotentialValuesAndFlags(Map<String, String> potentialValuesAndFlags) {
		this.potentialValuesAndFlags = potentialValuesAndFlags;
	}

	public Combo getCombo() {
		return combo;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			String currentValue = configuration.getAttribute(getName(), getDefaultValue());
			getCombo().setText(currentValue);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration, String flagID) {
		configuration.setAttribute(getName(), getCombo().getText());
		addFlagsToCommandLine(configuration, flagID);
	}

	@Override
	public void addControl(Composite composite, IWidgetListener listener) {
		Composite label_combo_composite = new Composite(composite, 0);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		label_combo_composite.setLayout(layout);
		Label label = new Label(label_combo_composite, SWT.NONE);
		
		GridData g = new GridData(SWT.RIGHT);
		label.setLayoutData(g);
		GridData g1 = new GridData(SWT.RIGHT);
		label.setText(getName());
		label.setToolTipText(getToolTip());
		combo = new Combo(label_combo_composite, SWT.RIGHT);
		
	//	combo.setLayoutData(g);
		combo.setLayoutData(g1);
		combo.setItems(getPotentialValues());
		combo.addSelectionListener(listener);
	}

	private String flag=null;
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Override
	public void addFlagsToCommandLine(List<String> flags) {
		if (flag != null) {
			flags.add(flag);
		}
		String value = getCombo().getText();
		String flagValue = potentialValuesAndFlags.get(value);
		flags.add(flagValue);
	}
	
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy wc){
		wc.setAttribute(getName(), getDefaultValue());
	}
	
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) { // Detecte uniquement la saisie d'un valeur par défaut n'appartenant pas à la liste des potentialvalues
		for (String s : getPotentialValues())
			if (s.equals(combo.getText()))
				return true;
		return false;
	}

}
