package fr.lip6.move.gal.ltsmin.preference;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import fr.lip6.move.gal.ltsmin.preference.LTSminPreferencesActivator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class LTSminPathPreference
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public LTSminPathPreference() {
		super(GRID);
		setPreferenceStore(LTSminPreferencesActivator.getDefault().getPreferenceStore());
		setDescription("LTSmin invocation options.");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {	
				
		FileFieldEditor itsreach = new FileFieldEditor(PreferenceConstants.LTSMINSEQ_EXE, PreferenceConstants.LTSMINSEQ_EXE, true, getFieldEditorParent());
		itsreach.setEmptyStringAllowed(false);
		itsreach.getLabelControl(getFieldEditorParent()).setToolTipText("Point to the version of pins2lts-seq you would like to use. Default settings points to embedded distribution of ltsmin.");
		addField(itsreach);
		
		
		FileFieldEditor itsmreach = new FileFieldEditor(PreferenceConstants.LTSMINMC_EXE, PreferenceConstants.LTSMINMC_EXE, true, getFieldEditorParent());
		itsmreach.setEmptyStringAllowed(false);
		itsmreach.getLabelControl(getFieldEditorParent()).setToolTipText("Point to the version of pins2lts-mc you would like to use. Default settings points to embedded distribution of ltsmin.");
		addField(itsmreach);
				
		PathEditor incfolder = new PathEditor(PreferenceConstants.LTSMININC_DIR, PreferenceConstants.LTSMININC_DIR, "Point to the include folder for LTSmin (it should contain a subfolder ltsmin/ containing *.h header files.)", getFieldEditorParent());
		//incfolder.setEmptyStringAllowed(false);
		incfolder.getLabelControl(getFieldEditorParent()).setToolTipText("Point to the include folder for LTSmin (it should contain a subfolder ltsmin/ containing *.h header files.)");
		addField(incfolder);
		
//		FileFieldEditor itsyreach = new FileFieldEditor(PreferenceConstants.LTSMINSYM_EXE, PreferenceConstants.LTSMINSYM_EXE, true, getFieldEditorParent());
//		itsyreach.setEmptyStringAllowed(false);
//		itsyreach.getLabelControl(getFieldEditorParent()).setToolTipText("Point to the version of pins2lts-sym you would like to use. Default settings points to embedded distribution of ltsmin.");
//		addField(itsyreach);

		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}