package fr.lip6.move.gal.itstools.launch.devTools;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class Option<T> implements IOption<T> {

	
	private String name;
	public void setName(String name) {
		this.name = name;
	}

	private T defaultValue;
	private String tooltip;
	private T [] potentialValues;
	private Control control;
	
	private OptionType type;
	private T currentValue;
	


	public Option(){}
	public Option(String name, String tooltip, T defaultValue,OptionType multichoice) {
		super();
		this.name = name;
		this.defaultValue = defaultValue;
		currentValue = defaultValue;
		this.tooltip = tooltip;
		this.setType(multichoice);
	}

	public T getDefaultValue() {
		return defaultValue;
	}



	public String getTooltip() {
		return tooltip;
	}



	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}


	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

//	@Override
//	public void setPotentialValues(T[] values) {
//		potentialValues = values;
//	}

	public T[] getPotentialValues() {
		return potentialValues;
	}

	public OptionType getType() {
		return type;
	}

	public void setType(OptionType multichoice) {
		this.type = multichoice;
	}

	public int getIndex(T value) {
		for (int i = 1; i < potentialValues.length; i++)
			if (value.equals(potentialValues[i]))
				return i;
		return 0;
	}
	@Override
	public String getToolTip() {
		// TODO Auto-generated method stub
		return tooltip;
	}

	//@Override
//	public void setCurrentValue(T new_value) {
//		currentValue = new_value;
//		
//	}

	@Override
	public T getCurrentValue() {
		// TODO Auto-generated method stub
		return currentValue;
	}
	public Control getControl() {
		return control;
	}
	public void setControl(Control control) {
		this.control = control;
	}
	@Override
	public void setControl(Composite composite) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
