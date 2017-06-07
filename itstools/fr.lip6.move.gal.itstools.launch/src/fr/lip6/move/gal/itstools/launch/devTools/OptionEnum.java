package fr.lip6.move.gal.itstools.launch.devTools;

import org.eclipse.swt.SWT;
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

	@Override
	public void setControl(Composite composite) {
		Label label = new Label(composite, SWT.NULL);
		label.setText(name);
		label.setToolTipText(tooltiptext);
		combo = new Combo(composite, SWT.NONE);
		
		combo.setItems(potentialValues);
	}

	public Combo getCombo() {
		// TODO Auto-generated method stub
		return combo;
	}

}
