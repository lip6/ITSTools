package fr.lip6.move.gal.itstools.launch.devTools;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class OptionText2 extends OptionText {

	
	
	private Button check;


	public Button getCheck() {
		return check;
	}


	public OptionText2( String name, String tooltiptext,String defaultValue) {
		super(name, tooltiptext, defaultValue);
	}

	
	@Override
	public void setControl(Composite composite) {
		
		check = new Button(composite, SWT.CHECK);
		check.setSelection(true);
		setText(new ITS_Text(composite, 0)); //style 0 par défaut
		check.setText(getName());
		check.setToolTipText(getTooltiptext());
		check.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				getText().setEnabled(check.getSelection());
				if (!check.getSelection())
					getText().setText(getDefaultValue());
					
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
	
	}
	
	

	

}
