package fr.lip6.move.gal.itstools.launch.devTools;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class OptionEnumWithText extends OptionEnum {

	private ITS_Text addedText;
	private String defaultValueInt;
	public OptionEnumWithText(String name, String tooltiptext, String defaultValue, String defaultValueInt) {
		super(name, tooltiptext, defaultValue);
		this.defaultValueInt = defaultValueInt;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setControl(Composite composite) {
		Label label = new Label(composite, SWT.NULL);
		label.setText(getName());
		label.setToolTipText(getTooltiptext());
		setCombo(new Combo(composite, SWT.NONE));
		
		getCombo().setItems(getPotentialValues());
		addedText = new ITS_Text(composite, 0);
		getCombo().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				addedText.setEnabled(true);
				System.out.println(getCombo().getText() + " " + getDefaultValue());
				if (getCombo().getText().equals(getDefaultValue())) 
					addedText.setText(defaultValueInt);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
	}
	public ITS_Text getAddedText() {
		return addedText;
	}
	

}
