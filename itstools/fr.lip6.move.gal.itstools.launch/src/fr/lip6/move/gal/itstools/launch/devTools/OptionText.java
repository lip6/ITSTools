package fr.lip6.move.gal.itstools.launch.devTools;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import fr.lip6.move.gal.itstools.CommandLine;

public class OptionText implements IOption<String> {

	private String defaultValue;
	private String name;
	private String tooltiptext;
	private ITS_Text text;
	
	private Button check;
	private DefaultValueComputer computer;
	private String flag;
	private String text_state;
	
	public void setFlag(String flag){
		this.flag = flag;
	}
	public void setPathExtension(String extension) {
		computer = new DefaultValueComputer(extension);
	}

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


	public ITS_Text getText() {
		return text;
	}

	public String getTooltiptext() {
		return tooltiptext;
	}

	public void setTooltiptext(String tooltiptext) {
		this.tooltiptext = tooltiptext;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		Object currentValue;
		try {
			
			currentValue = configuration.getAttributes().get(name);
			if(currentValue != null) {
				getText().setText((String)currentValue);
				check.setSelection(true);
			}				
			else{
				if (computer != null){
					getText().setText(computer.computeConfigurationDefaultValue(configuration));
					return;
				}
				check.setSelection(false);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (!getText().isEnabled()){ // or !check.getSelection()
			configuration.removeAttribute(getName());
			return;
	}
	//String text_state = getText().getText();
	text_state = getText().getText();

	configuration.setAttribute(getName(), text_state);
		
	}

	@Override
	public void addControl(Composite composite, IWidgetListener listener) {
		
		Composite check_text_composite = new Composite(composite, SWT.FILL);
		GridLayout layout = new GridLayout(2, true);
		check_text_composite.setLayout(layout);
		check = new Button(check_text_composite, SWT.CHECK);
		check.setSelection(true);
		//setText(new ITS_Text(check_text_composite, 0)); //style 0 par défaut
		check.setText(getName());
		check.setToolTipText(getTooltiptext());
		check.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				getText().setEnabled(check.getSelection());
				
					
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
	
		text = new ITS_Text(check_text_composite, 0);
		check.addSelectionListener(listener);
		text.addModifyListener(listener);
	}

	@Override
	public void addFlagsToCommandLine(CommandLine cl, ILaunchConfiguration configuration) {
		try {
			String value = configuration.getAttribute(name, "");
			if (value.length() > 0){
				cl.addArg(flag);
				//cl.addArg(text.getText());
				cl.addArg(text_state);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

}
