package fr.lip6.move.gal.itstools.launch.devTools;

public interface IOption {

	public String getName();
	public String getDefaultValue();
	public String getCurrentValue();
	public OptionType getType();
	public void setPotentialValues(String [] values); //pour un ordre de priorité au sein des différentes valeurs
	public String [] getPotentialValues();
	public String getToolTip();
	public void changeCurrentValue(String new_value);
	public int getIndex(String value);
}
