package fr.lip6.move.gal.itstools.launch.devTools;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import fr.lip6.move.gal.itstools.CommandLine;

public class OptionEnumWithText extends OptionEnum {

	private ITS_Text addedText;
	private String defaultValueInt;
	private String text_state;
	public OptionEnumWithText(String name, String tooltiptext, String defaultValue, String defaultValueInt) {
		super(name, tooltiptext, defaultValue);
		this.defaultValueInt = defaultValueInt;
		text_state = defaultValueInt;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void addControl(Composite composite, IWidgetListener listener) {
		Composite label_combo_text_composite = new Composite(composite, 0);
		GridLayout layout = new GridLayout(3, true);
		label_combo_text_composite.setLayout(layout);
		Label label = new Label(label_combo_text_composite, SWT.NULL);
		label.setText(getName());
		label.setToolTipText(getTooltiptext());
		setCombo(new Combo(label_combo_text_composite, SWT.NONE));
		
		getCombo().setItems(getPotentialValues());
		addedText = new ITS_Text(label_combo_text_composite, 0);
		
		
		getCombo().addSelectionListener(listener);
		getAddedText().addModifyListener(listener);
		
	}
	
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		Object currentValue, currentValueText;
		try {
			currentValue = configuration.getAttributes().get(getName());
			currentValueText = configuration.getAttributes().get(getName()+"Value");
			if(currentValue != null) {
				getCombo().setText((String)currentValue);
			}else{
				getCombo().setText(getDefaultValue());
			}
			//En général ça aurait pu être une liste
			if (currentValueText != null){
				addedText.setText((String)currentValueText);
			}else{
				getAddedText().setText(defaultValueInt);
			}
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		String combo_text_state = getCombo().getText();
		text_state = getAddedText().getText();
		configuration.setAttribute(getName(), combo_text_state);
		configuration.setAttribute(getName()+"Value", text_state);
	}
	public ITS_Text getAddedText() {
		return addedText;
	}
	@Override
	public void addFlagsToCommandLine(CommandLine cl, ILaunchConfiguration configuration) {
		super.addFlagsToCommandLine(cl, configuration);
		//cl.addArg(getAddedText().getText());
		cl.addArg(text_state);
	}
	
	public void setDefaultValue(ILaunchConfigurationWorkingCopy wc){
		super.setDefaultValue(wc);
		//addedText.setText(defaultValueInt);
	}

}
