package fr.lip6.move.gal.itstools.launch.devTools;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import fr.lip6.move.gal.itstools.CommandLine;

@SuppressWarnings("restriction")
public class OptionBoolean implements IOption<Boolean> {

	private boolean defaultValue;
	private String name;
	private String tooltiptext;
	private Button button;
	private String flag;
	

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
	public void addControl(Composite composite, IWidgetListener listener){
		button = SWTFactory.createCheckButton(composite, name, null, defaultValue, 2);
		GridData layoutData = new GridData();
		layoutData.widthHint = 100;
		button.setLayoutData(layoutData);
		button.setToolTipText(tooltiptext);
		button.addSelectionListener(listener);
	}
	
	public Button getButton() {
		return button;
	}



	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		Boolean currentValue;
		try {
			currentValue = configuration.getAttribute(name, false);
			getButton().setSelection((Boolean)currentValue);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		boolean button_state = getButton().getSelection();
		configuration.setAttribute(getName(), button_state);
		
	}

	public void setFlag(String flag){
		this.flag = flag;
	}

	@Override
	public void addFlagsToCommandLine(CommandLine cl, ILaunchConfiguration configuration) {
		try {
			Boolean value = configuration.getAttribute(name, false);
			if (value){
				cl.addArg(flag);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
