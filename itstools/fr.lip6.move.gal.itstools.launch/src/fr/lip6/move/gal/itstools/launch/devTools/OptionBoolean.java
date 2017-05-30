package fr.lip6.move.gal.itstools.launch.devTools;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;

public class OptionBoolean implements IOption<Boolean> {

	private boolean defaultValue, currentValue;
	private String name;
	
	public OptionBoolean(String name, String tooltip, boolean defaultValue, OptionType multichoice) {
		setName(name);
		this.defaultValue = defaultValue;
		currentValue = defaultValue;
		setTooltip(tooltip);
		this.setType(multichoice);
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Boolean getDefaultValue() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Boolean getCurrentValue() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public OptionType getType() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setPotentialValues(Boolean[] values) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Boolean[] getPotentialValues() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getToolTip() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setCurrentValue(Boolean bool_value) {
		button.setSelection(bool_value);
		currentValue = bool_value;
		
	}


	@Override
	public int getIndex(Boolean value) {
		// TODO Auto-generated method stub
		return 0;
	}


	


	@Override
	public Button getControl() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
