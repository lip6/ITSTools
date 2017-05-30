package fr.lip6.move.gal.itstools.launch;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import fr.lip6.move.gal.itstools.launch.devTools.IOption;
import fr.lip6.move.gal.itstools.launch.devTools.OptionType;

@SuppressWarnings("restriction")
public class OptionsTab extends AbstractLaunchConfigurationTab implements ModifyListener {
	
	private List<IOption> options = new LinkedList<IOption>();
	private List<Control> controls = new LinkedList<Control>();
	private WidgetListener listener = new WidgetListener();
	
	//LISTENER GENERAL
	
	private class WidgetListener implements ModifyListener, SelectionListener {
		
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}
		
		public void widgetDefaultSelected(SelectionEvent e) {/*do nothing*/}
		
		@SuppressWarnings("unchecked")
		public void widgetSelected(SelectionEvent e) {
			int i = findRightControl(e);
			//Rajouter le switch de type pour le bon cast
			String control_type = e.getSource().toString();
			if (control_type.contains("Button")){
				options.get(i).setCurrentValue(((Button)e.getSource()).getSelection());
			}
				
			if (control_type.contains("Combo"))
				options.get(i).setCurrentValue(((Combo)e.getSource()).getText());
		
			
			updateLaunchConfigurationDialog();

		}
	}

	private int findRightControl(SelectionEvent e){
		
		Object match = e.getSource();
		for (int i = 0; i < options.size();i++)
			if(options.get(i).getControl().equals(match))
				return i;
		
		// AJouter peut-être une exception car normalement on ne devrait pas ariiver ici
		return -1; //Pas trop rassurant
	}
	public OptionsTab() {
		
	}

	/*pourquoi pas boolean addOption*/
	public void addOption(IOption option){
		options.add(option);
	}


	
	@Override
	public void createControl(Composite parent) {
		Composite main = SWTFactory.createComposite(parent, 1, 3, GridData.FILL_BOTH);
		
		/* Initialisation des groupes boolean, enum et String*/
		Group boolGroup = SWTFactory.createGroup(main, "Boolean Group ", 1, 2, GridData.FILL_BOTH);
		Group stringGroup = SWTFactory.createGroup(main, "String Group ", 1, 2, GridData.FILL_BOTH);
		Group enumGroup = SWTFactory.createGroup(main, "Enum Group ", 1, 2, GridData.FILL_BOTH);
		
		for (IOption option : options){
			Control option_control;
			switch(option.getType()){
			case MULTICHOICE:
				GridLayout gridLayoutE = new GridLayout(2, true);
				enumGroup.setLayout(gridLayoutE);
				GridData gridDataE = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridDataE.horizontalSpan = 2;
				enumGroup.setLayoutData(gridDataE);
				Label l = new Label(enumGroup, SWT.NULL);
				l.setText(option.getName());
				l.setToolTipText(option.getToolTip());
				option_control = new Combo(enumGroup, SWT.NONE);
				
				((Combo) option_control).addSelectionListener(listener);
				((Combo) option_control).setItems((String[]) option.getPotentialValues()); 
				controls.add(option_control);
				option.setControl(option_control);
				break;
			case BOOLEAN:
				GridLayout gridLayoutB = new GridLayout(2, true);
				boolGroup.setLayout(gridLayoutB);
				GridData gridDataB = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
				gridDataB.horizontalSpan = 2;
				boolGroup.setLayoutData(gridDataB);
				
				org.eclipse.swt.widgets.Button check_button = SWTFactory.createCheckButton(boolGroup, option.getName(), null, (Boolean)option.getDefaultValue(), 2);
				check_button.addSelectionListener(listener);
				controls.add(check_button);
				option.setControl(check_button);
				break;
			case TEXT:
				break;
			}
		}
		setControl(main);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		
	}

	private int getOptionIndexInList(String option_name){
		System.out.println(option_name);
		for(int i = 0; i < options.size(); i++) {
			if (option_name.equals(options.get(i).getName()))
					return i;
		}
		return -1; // Genere une exception de type IndexOutOfBoundException
	}
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			System.out.println(configuration.getAttributes());
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			for (IOption opt : options){
				Object cur = configuration.getAttributes().get(opt.getName());
				opt.setCurrentValue(cur);
			}
			
			for (Entry<String, Object> entry : configuration.getAttributes().entrySet()){
				for (IOption opt : options){
					if (entry.getKey().equals(opt.getName())){
						OptionType type = opt.getType();
						switch(type){
						case MULTICHOICE:
							Combo combo = ((Combo) controls.get(getOptionIndexInList(opt.getName())));
							String config_value = (String)entry.getValue();
							combo.select(opt.getIndex(config_value)); //Ce 24/05
							break;
						case BOOLEAN:
							org.eclipse.swt.widgets.Button button = (Button) ( controls.get(getOptionIndexInList(opt.getName())));
							boolean bool_value = (Boolean)entry.getValue();
							button.setSelection(bool_value);
							break;
						case TEXT:
							break;
						}
					}
						
				}
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	

	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {

			for (IOption option : options){
				OptionType type = option.getType();
				System.out.println(type + option.getName()+option.getCurrentValue());
				switch(type){
				case MULTICHOICE:
					configuration.setAttribute(option.getName(), (String)option.getCurrentValue());
					break;
				case BOOLEAN:
					configuration.setAttribute(option.getName(), (Boolean)option.getCurrentValue());
					break;
				case TEXT:
					break;
				}
				
			}
	}
	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {}
	@Override
	public void deactivated(ILaunchConfigurationWorkingCopy workingCopy) {}
	@Override
	public String getName() {
		return "Model Option";
	}

	@Override
	public void modifyText(ModifyEvent e) {
		
	}
	
	

}
