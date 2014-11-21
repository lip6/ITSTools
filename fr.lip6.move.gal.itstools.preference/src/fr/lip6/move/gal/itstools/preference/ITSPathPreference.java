package fr.lip6.move.gal.itstools.preference;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import fr.lip6.move.gal.itstools.preference.Activator;

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

public class ITSPathPreference
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public ITSPathPreference() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("ITS tools invocation options.");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {	
				
		FileFieldEditor itsreach = new FileFieldEditor(PreferenceConstants.ITSREACH_EXE, PreferenceConstants.ITSREACH_EXE, true, getFieldEditorParent());
		itsreach.setEmptyStringAllowed(false);
		itsreach.getLabelControl(getFieldEditorParent()).setToolTipText("Point to the version of its-reach you would like to use. Default settings points to embedded distribution of its-reach.");
		addField(itsreach);
		
		FileFieldEditor itsctl = new FileFieldEditor(PreferenceConstants.ITSCTL_EXE, PreferenceConstants.ITSCTL_EXE, true, getFieldEditorParent());
		itsctl.setEmptyStringAllowed(false);
		itsctl.getLabelControl(getFieldEditorParent()).setToolTipText("Point to the version of its-ctl you would like to use. Default settings points to embedded distribution of its-ctl.");
		addField(itsctl);
		
		FileFieldEditor itsltl = new FileFieldEditor(PreferenceConstants.ITSLTL_EXE, PreferenceConstants.ITSLTL_EXE, true, getFieldEditorParent());
		itsltl.setEmptyStringAllowed(false);
		itsltl.getLabelControl(getFieldEditorParent()).setToolTipText("Point to the version of its-ltl you would like to use. Default settings points to embedded distribution of its-ltl.");
		addField(itsltl);
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}