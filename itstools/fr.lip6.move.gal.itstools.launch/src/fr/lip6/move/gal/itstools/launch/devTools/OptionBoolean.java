package fr.lip6.move.gal.itstools.launch.devTools;

import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("restriction")
public class OptionBoolean implements IOption<Boolean> {

	private boolean defaultValue;
	private String name;
	private String tooltiptext;
	private Button button;
	

	public OptionBoolean(String name, String tooltip, boolean defaultValue) {
		this.defaultValue = defaultValue;
		this.name = name;
		tooltiptext = tooltip;
	}

	
	
	public OptionBoolean(String name, String tooltiptext) {
		this.name = name;
		this.tooltiptext = tooltiptext;
		this.defaultValue = true;
	}



	@Override
	public String getName() {
		return name;
	}


	@Override
	public Boolean getDefaultValue() {
		return defaultValue;
	}


	@Override
	public Boolean getCurrentValue() {
		return button.getSelection();
	}


	@Override
	public String getToolTip() {
		return tooltiptext;
	}


	@Override
	public void setControl(Composite composite){
		button = SWTFactory.createCheckButton(composite, name, null, defaultValue, 2);
		GridData layoutData = new GridData();
		layoutData.widthHint = 100;
		button.setLayoutData(layoutData);
		button.setToolTipText(tooltiptext);
	}
	
	public Button getButton() {
		return button;
	}

}
