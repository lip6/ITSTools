package fr.lip6.move.gal.itstools.launch.devTools;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class OptionText implements IOption<String> {

	private String defaultValue;
	private String name;
	private String tooltiptext;
	private ITS_Text text;
	
	
	public void setText(ITS_Text text) {
		this.text = text;
	}

	public OptionText( String name, String tooltiptext,String defaultValue) {
		this.defaultValue = defaultValue;
		this.name = name;
		this.setTooltiptext(tooltiptext);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getToolTip() {
		return getTooltiptext();
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

	@Override
	public String getCurrentValue() {
		return text.getText();
	}

	@Override
	public void setControl(Composite composite) {
		Label label = new Label(composite, SWT.NULL);
		label.setText(name);
		label.setToolTipText(getTooltiptext());
		text = new ITS_Text(composite, 0); //style 0 par défaut

	}
	
	public void setControl(Composite composite, int style) {
		Label label = new Label(composite, SWT.NULL);
		label.setText(name);
		label.setToolTipText(getTooltiptext());
		text = new ITS_Text(composite, style);
		
	}

	public ITS_Text getText() {
		return text;
	}

	public String getTooltiptext() {
		return tooltiptext;
	}

	public void setTooltiptext(String tooltiptext) {
		this.tooltiptext = tooltiptext;
	}

}
