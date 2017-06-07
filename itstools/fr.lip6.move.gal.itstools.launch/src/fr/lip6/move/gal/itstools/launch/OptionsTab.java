package fr.lip6.move.gal.itstools.launch;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationDialog;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import fr.lip6.move.gal.itstools.launch.devTools.IOption;
import fr.lip6.move.gal.itstools.launch.devTools.OptionBoolean;
import fr.lip6.move.gal.itstools.launch.devTools.OptionEnum;
import fr.lip6.move.gal.itstools.launch.devTools.OptionEnumWithText;
import fr.lip6.move.gal.itstools.launch.devTools.OptionText;
import fr.lip6.move.gal.itstools.launch.devTools.OptionText2;

@SuppressWarnings("restriction")
public class OptionsTab extends AbstractLaunchConfigurationTab implements ModifyListener {
	
	private List<OptionBoolean> boolean_options = new LinkedList<OptionBoolean>();
	private List<OptionEnum> enum_options = new LinkedList<OptionEnum>();
	private List<OptionText> text_options = new LinkedList<OptionText>();
	private List<OptionEnumWithText> enum_optionsW = new LinkedList<OptionEnumWithText>();
	private List<IOption<?>> options = new LinkedList<>();

	private WidgetListener listener = new WidgetListener();
	
	//LISTENER GENERAL
	
	private class WidgetListener implements ModifyListener, SelectionListener {
		
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}
		
		public void widgetDefaultSelected(SelectionEvent e) {/*do nothing*/}
		
		
		public void widgetSelected(SelectionEvent e) {
			updateLaunchConfigurationDialog();
		}
	}

	
	/*Ou plutôt Instanceof OptionBoolean*/
	
	public void addOption(OptionText option){
		text_options.add(option);
		options.add(option);
	}
	public void addOption(OptionBoolean option){
		boolean_options.add(option);
	}
	public void addOption(OptionEnum option){
		enum_options.add(option);
	}

	public void addOption(OptionEnumWithText option){
		enum_optionsW.add(option);
	}

	
	@Override
	public void createControl(Composite parent) {
		Composite main = SWTFactory.createComposite(parent, 1, 3, GridData.FILL_BOTH);
		
		/* Initialisation des groupes boolean, enum, enumText et String*/
		Group boolGroup = SWTFactory.createGroup(main, "Boolean Group ", 1, 2, GridData.FILL_BOTH);
		Group textGroup = SWTFactory.createGroup(main, "String Group ", 1, 2, GridData.FILL_BOTH);
		Group enumGroup = SWTFactory.createGroup(main, "Enum Group ", 1, 2, GridData.FILL_BOTH);
		Group enumGroupW = SWTFactory.createGroup(main, "Enum-Text Group", 1, 2, GridData.FILL_BOTH);

	
		GridLayout gridLayoutB = new GridLayout(6, true);
		gridLayoutB.horizontalSpacing = 10;
		GridLayout gridLayoutE =  new GridLayout(2, true);
		GridLayout gridLayoutT = new GridLayout(4, true);
		GridLayout gridLayoutET =  new GridLayout(3, true);
		boolGroup.setLayout(gridLayoutB);
		enumGroup.setLayout(gridLayoutE);
		textGroup.setLayout(gridLayoutT);
		enumGroupW.setLayout(gridLayoutET);
		
		
		GridData gridDataB = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		GridData gridDataE = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		GridData gridDataT = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		GridData gridDataET = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		boolGroup.setLayoutData(gridDataB);
		enumGroup.setLayoutData(gridDataE);
		enumGroup.setLayoutData(gridDataT);
		enumGroupW.setLayoutData(gridDataET);
		
//		for (IOption<?> opt : options) {
//			opt.setControl(composite);         YANN
//		}
		for (OptionBoolean option : boolean_options){
			//	option.setControl(boolGroup, listener ); YANN
			option.setControl(boolGroup);	
			option.getButton().addSelectionListener(listener);
		}
		
		for (OptionEnum option : enum_options){
			option.setControl(enumGroup);
			option.getCombo().addSelectionListener(listener);
		}
		
		
		for (OptionText option : text_options){
			option.setControl(textGroup);
			option.getText().addModifyListener(listener);
			if (option instanceof OptionText2) {
				((OptionText2)option).getCheck().addSelectionListener(listener);
			}
		}
		
		for (OptionEnumWithText option : enum_optionsW){
			option.setControl(enumGroupW);
			option.getCombo().addSelectionListener(listener);
			option.getAddedText().addModifyListener(listener);
		}
		
		setControl(main);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		
	}


	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		
		try {
			for (OptionBoolean option : boolean_options){
				Object currentValue;

				currentValue = configuration.getAttributes().get(option.getName());
				if (currentValue.equals("true"))
					currentValue = true;
				if(currentValue.equals("false"))
					currentValue = false;
				if(currentValue != null)
					option.getButton().setSelection((Boolean)currentValue);
			}

			for (OptionEnum option : enum_options){
				Object currentValue = configuration.getAttributes().get(option.getName());
				if(currentValue != null)
					option.getCombo().setText((String)currentValue);
			}


			for (OptionText option : text_options){
				Object currentValue = configuration.getAttributes().get(option.getName());
				if(currentValue != null){
					String[] s = ((String)currentValue).split(" "); 
					if (s.length == 2)
						option.getText().setText(s[1]);
				}
			}
			
			for (OptionEnumWithText option : enum_optionsW){
				Object currentValue = configuration.getAttributes().get(option.getName());
				String[] value_int;
				if(currentValue != null) {
					 value_int = ((String)currentValue).split(" ");
					 option.getCombo().setText(value_int[0]);
					 option.getAddedText().setText(value_int[1]);
				}
				
			}
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
	
		for (OptionBoolean option : boolean_options){
			boolean button_state = option.getButton().getSelection();
			configuration.setAttribute(option.getName(), new Boolean(button_state).toString());
		}

		for (OptionEnum option : enum_options){
			String combo_state = option.getCombo().getText();
			configuration.setAttribute(option.getName(), combo_state);
		}


		for (OptionText option : text_options){
			if (option instanceof OptionText2 && !option.getText().isEnabled()){
					configuration.removeAttribute(option.getName());
					continue;
			}
			String text_state = option.getText().getText();
			configuration.setAttribute(option.getName(), "true " +text_state);
		}
		
		for (OptionEnumWithText option : enum_optionsW){
			String combo_text_state = option.getCombo().getText() + " " + option.getAddedText().getText();
			configuration.setAttribute(option.getName(), combo_text_state);
		}
		
		//A RETIRER JUSTE POUR LE DEBUG
			try {
				System.out.println(configuration.getAttributes());
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {}
	@Override
	public void deactivated(ILaunchConfigurationWorkingCopy workingCopy) {}
	@Override
	public String getName() {
		return "Reachable Formula";
	}

	@Override
	public void modifyText(ModifyEvent e) {
		
	}
	
	

}
