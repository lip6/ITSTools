package fr.lip6.move.gal.options.ui;

import java.util.List;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class OptionSeparator extends AbstractOption<String> {
	private Label separator;
	
	public OptionSeparator(String name, String tooltip) {
		super(name,tooltip,null);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration, String f) {
	}

	@Override
	public void addControl(Composite composite, IWidgetListener listener) {
		Label description = new Label(composite,SWT.NULL);
		description.setText(getName());
		Font boldFont = new Font(description.getDisplay(), new FontData("Arial", 11, SWT.BOLD|SWT.ITALIC));
		description.setFont(boldFont);
		
		separator= new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		separator.setLayoutData(layoutData );
		description.setToolTipText(getToolTip());
	}

	@Override
	public void addFlagsToCommandLine(List<String> flags) {
	}
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy wc) {
	}
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		return true;
	}

}
