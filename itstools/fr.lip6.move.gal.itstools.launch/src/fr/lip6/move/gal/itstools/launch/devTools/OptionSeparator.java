package fr.lip6.move.gal.itstools.launch.devTools;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import fr.lip6.move.gal.itstools.CommandLine;

public class OptionSeparator implements IOption<String> {

	private String name;
	
	private String tooltiptext;
	private Label separator;
	public OptionSeparator(String name, String tooltip) {
		
		this.name = name;
		tooltiptext = tooltip;
	}
	@Override
	public String getName() {
		
		return name;
	}

	@Override
	public String getToolTip() {
		// TODO Auto-generated method stub
		return tooltiptext;
	}

	@Override
	public String getDefaultValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrentValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addControl(Composite composite, IWidgetListener listener) {
		Label description = new Label(composite,SWT.NULL);
		description.setText(name);
		Font boldFont = new Font(description.getDisplay(), new FontData("Arial", 12, SWT.BOLD));
		description.setFont(boldFont);
		
		separator= new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		separator.setLayoutData(layoutData );
		//separator.setText(name);
		description.setToolTipText(tooltiptext);

	}

	@Override
	public void addFlagsToCommandLine(CommandLine cl, ILaunchConfiguration configuration) {
		// TODO Auto-generated method stub

	}
	@Override
	public void setDefaultValue(ILaunchConfigurationWorkingCopy wc) {
		// TODO Auto-generated method stub
		
	}

}
