package fr.lip6.move.gal.ltsmin.launch.devtools;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class OptionText extends  AbstractOption<String> {

	private Text text;
	
	private Button check;
	private DefaultValueComputed computer;
	private String flag;

	public void setFlag(String flag){
		this.flag = flag;
	}
	public void setPathExtension(String extension) {
		computer = new DefaultValueComputed(extension);
	}

	public void setText(Text text) {
		this.text = text;
	}

	public OptionText( String name, String tooltiptext,String defaultValue) {
		super(name,tooltiptext,defaultValue);
	}

	public Text getText() {
		return text;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {			
			getText().setText(configuration.getAttribute(getName()+"text",getDefaultValue()));

			boolean currentValue = configuration.getAttribute(getName(),false);
			if (currentValue) {
				check.setSelection(true);
			} else {
				getText().setEnabled(false);				
				check.setSelection(false);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration, String flagID) {
		configuration.setAttribute(getName(), check.getSelection());
		configuration.setAttribute(getName()+"text", getText().getText());
		addFlagsToCommandLine(configuration, flagID);
	}

	@Override
	public void addControl(Composite composite, IWidgetListener listener) {
		
		Composite check_text_composite = new Composite(composite, SWT.FILL);
		GridLayout layout = new GridLayout(2, true); 

		check_text_composite.setLayout(layout);
		check = new Button(check_text_composite, SWT.CHECK);

		check.setText(getName());
		check.setToolTipText(getToolTip());
		check.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				getText().setEnabled(check.getSelection());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	
		text = new Text(check_text_composite, 0);
		text.setLayoutData(new GridData());

		check.addSelectionListener(listener);
		text.addModifyListener(listener);
	}

	@Override
	public void addFlagsToCommandLine(List<String> flags) {
		if (check.getSelection()){
			flags.add(flag);
			flags.add(text.getText());
		}
	}
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy wc){
		// default is to be disabled
		wc.setAttribute(getName(), false);
		wc.setAttribute(getName()+"text", getDefaultValue());
	}
	
	
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		//Supposons que les champs doivent remplies par des entiers à l'exception des option possédant un computer
		if (computer == null){
			 try {
				 if (check.getSelection())
					 Double.parseDouble(getText().getText());
			    } catch(NumberFormatException nfe) {
			    	System.err.println(getText().getText() + " is not a number!");
			        return false;
			    }
		}
		return true;
	}

}
