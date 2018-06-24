package fr.lip6.move.gal.ltsmin.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import fr.lip6.move.gal.ltsmin.preference.LTSminPreferencesActivator;
import fr.lip6.move.gal.ltsmin.preference.PreferenceConstants;
import fr.lip6.move.gal.options.ui.FileChooser;

public class ModelSelectionTab extends AbstractLaunchConfigurationTab implements
		ILaunchConfigurationTab {

	private Text modeltf;
	private Composite comp;
	private FileChooser fc;
	
	
	@Override
	public void createControl(Composite parent) {
		comp = SWTFactory.createComposite(parent, 2, 1, GridData.FILL_HORIZONTAL);
		setControl(comp);
				
		Label l1= new Label(comp, SWT.NONE);
		l1.setText("Model selected");	
		modeltf = new Text(comp, SWT.NONE);
		
		fc = new FileChooser(comp);
		String[] ext = {"gal"};
		fc.setExtensions(ext );
		
		comp.layout();
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(PreferenceConstants.LTSMINSEQ_EXE, LTSminPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.LTSMINSEQ_EXE));
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			modeltf.setText(configuration.getAttribute(PreferenceConstants.LTSMINSEQ_EXE, 
					LTSminPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.LTSMINSEQ_EXE)));
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(PreferenceConstants.LTSMINSEQ_EXE, modeltf.getText());
	}

	@Override
	public String getName() {
		return "Main";
	}

}
