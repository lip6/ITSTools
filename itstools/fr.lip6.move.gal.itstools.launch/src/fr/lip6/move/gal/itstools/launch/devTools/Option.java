package fr.lip6.move.gal.itstools.launch.devTools;

public class Option implements IOption {

	
	private String name;
	private String defaultValue;
	private String tooltip;
	private String [] potentialValues;
	
	/* POUR extension*/
	public enum Type {BOOLEAN, TEXT, MULTICHOICE}
	private OptionType type;
	private String currentValue;
	


	public Option(String name, String tooltip, String defaultValue,OptionType multichoice) {
		super();
		this.name = name;
		this.defaultValue = defaultValue;
		currentValue = defaultValue;
		this.tooltip = tooltip;
		this.setType(multichoice);
	}

	public String getDefaultValue() {
		return defaultValue;
	}



	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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

	@Override
	public void setPotentialValues(String[] values) {
		potentialValues = values;
	}

	public String[] getPotentialValues() {
		return potentialValues;
	}

	public OptionType getType() {
		return type;
	}

	public void setType(OptionType multichoice) {
		this.type = multichoice;
	}

	public int getIndex(String value) {
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

	@Override
	public void changeCurrentValue(String new_value) {
		currentValue = new_value;
		
	}

	@Override
	public String getCurrentValue() {
		// TODO Auto-generated method stub
		return currentValue;
	}

}
