package fr.lip6.move.gal.itstools.launch.devTools;



import org.eclipse.swt.widgets.Composite;

public interface IOption<T> {

	public String getName();
	public String getToolTip();
	public T getDefaultValue();
	public T getCurrentValue();
	
	
	public void setControl(Composite composite);
	//void setControl(Composite composite, Function<Void, Void> func); YANN
}
