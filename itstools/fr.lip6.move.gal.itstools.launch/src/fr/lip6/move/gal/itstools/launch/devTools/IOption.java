package fr.lip6.move.gal.itstools.launch.devTools;

import org.eclipse.swt.widgets.Control;

public interface IOption<T> {

	public String getName();
	public String getToolTip();
	public T getDefaultValue();
	public T getCurrentValue();
	public void setCurrentValue(T new_value);
	
	public OptionType getType();
	
	public void setPotentialValues(T [] values); //pour un ordre de priorité au sein des différentes valeurs
	public T [] getPotentialValues();
	public int getIndex(T value);
	
	//public void setControl(Control c);
	public Control getControl();
}
