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
		
		
		FileFieldEditor itsyreach = new FileFieldEditor(PreferenceConstants.LTSMINSYM_EXE, PreferenceConstants.LTSMINSYM_EXE, true, getFieldEditorParent());
		itsyreach.setEmptyStringAllowed(false);
		itsyreach.getLabelControl(getFieldEditorParent()).setToolTipText("Point to the version of pins2lts-sym you would like to use. Default settings points to embedded distribution of ltsmin.");
		addField(itsyreach);
		
//		FileFieldEditor itsctl = new FileFieldEditor(PreferenceConstants.ITSCTL_EXE, PreferenceConstants.ITSCTL_EXE, true, getFieldEditorParent());
//		itsctl.setEmptyStringAllowed(false);
//		itsctl.getLabelControl(getFieldEditorParent()).setToolTipText("Point to the version of its-ctl you would like to use. Default settings points to embedded distribution of its-ctl.");
//		addField(itsctl);
//		
//		FileFieldEditor itsltl = new FileFieldEditor(PreferenceConstants.ITSLTL_EXE, PreferenceConstants.ITSLTL_EXE, true, getFieldEditorParent());
//		itsltl.setEmptyStringAllowed(false);
//		itsltl.getLabelControl(getFieldEditorParent()).setToolTipText("Point to the version of its-ltl you would like to use. Default settings points to embedded distribution of its-ltl.");
//		addField(itsltl);
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}