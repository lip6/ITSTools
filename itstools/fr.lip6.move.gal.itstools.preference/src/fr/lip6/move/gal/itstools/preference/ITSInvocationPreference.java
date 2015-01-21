package fr.lip6.move.gal.itstools.preference;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import fr.lip6.move.gal.itstools.preference.GalPreferencesActivator;

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

public class ITSInvocationPreference
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public ITSInvocationPreference() {
		super(GRID);
		setPreferenceStore(GalPreferencesActivator.getDefault().getPreferenceStore());
		setDescription("ITS tools invocation options.");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {	
				
		IntegerFieldEditor time = new IntegerFieldEditor(PreferenceConstants.TIMEOUT_DURATION,PreferenceConstants.TIMEOUT_DURATION, getFieldEditorParent());
		time.getLabelControl(getFieldEditorParent()).setToolTipText("Maximum time allowed for process execution before the \"Failed due to timeout\" message is produced.");
		addField(time);
		

		BooleanFieldEditor verb = new BooleanFieldEditor(
				PreferenceConstants.QUIET_PARAMETER,
				PreferenceConstants.QUIET_PARAMETER,
				getFieldEditorParent());
		verb.getDescriptionControl(getFieldEditorParent()).setToolTipText("Lower the verbosity of output. On verbose mode, the tool will print the model's internal representation as well as more traces on what is going on.");
		addField(verb);

		IntegerFieldEditor gc = new IntegerFieldEditor(PreferenceConstants.GCTHRESHOLD,PreferenceConstants.GCTHRESHOLD, getFieldEditorParent());
		gc.getLabelControl(getFieldEditorParent()).setToolTipText("Soft memory limit before starting to invoke GC, should be set to 50-80% of system memory.Default is 1300000KB=1.3GB. Lower values decrease memory footprint but increase runtime.");
		addField(gc);

		
		
		
		BooleanFieldEditor dddsdd = new BooleanFieldEditor(
				PreferenceConstants.DDDSDD_PARAMETER,
				PreferenceConstants.DDDSDD_PARAMETER,
				getFieldEditorParent());
		dddsdd.getDescriptionControl(getFieldEditorParent()).setToolTipText("Primarily use SDD to encode variables. Sometimes (but rarely overall) less efficient than DDD (i.e. leave unchecked) for models with large marking values.");
		addField(dddsdd);

		
		ComboFieldEditor drstrat = new ComboFieldEditor(
				PreferenceConstants.SCALAR_PARAMETER,
				PreferenceConstants.SCALAR_PARAMETER,				
				new String[][] { { PreferenceConstants.DEPTH2, PreferenceConstants.DEPTH2 }, 
						{ PreferenceConstants.DEPTHREC, PreferenceConstants.DEPTHREC },
						{ PreferenceConstants.DEPTHSHALLOW, PreferenceConstants.DEPTHSHALLOW }
								},
				getFieldEditorParent());
		drstrat.getLabelControl(getFieldEditorParent()).setToolTipText("Set a recursive encoding strategy for scalar sets. \n Depth2 : (depth 2 levels) use 2 level depth for scalar sets. Integer provided defines level 2 block size. [DEFAULT: Depth2, with blocks size 1] \n   -depthRec INT : (depth recursive) use recursive encoding for scalar sets. Integer provided defines number of blocks at highest levels. \n    -DepthShallow INT : (depth shallow recursive) use alternative recursive encoding for scalar sets. Integer provided defines number of blocks at lowest level.");
		addField(drstrat);
		
		IntegerFieldEditor block = new IntegerFieldEditor(PreferenceConstants.BLOCK_SIZE_PARAMETER,PreferenceConstants.BLOCK_SIZE_PARAMETER, getFieldEditorParent());
		block.getLabelControl(getFieldEditorParent()).setToolTipText("Sets the block size used as additional setting for encoding of Scalar set.");
		addField(block);
		

		
//		addField(
//				new IntegerFieldEditor(PreferenceConstants., "Maximum time allowed for process execution before the \"Failed due to timeout\" message is produced.", getFieldEditorParent()));
//		
//		
//		addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH, 
//				"&Directory preference:", getFieldEditorParent()));
//		addField(
//			new BooleanFieldEditor(
//				PreferenceConstants.P_BOOLEAN,
//				"&An example of a boolean preference",
//				getFieldEditorParent()));
//
//		addField(new RadioGroupFieldEditor(
//				PreferenceConstants.P_CHOICE,
//			"An example of a multiple-choice preference",
//			1,
//			new String[][] { { "&Choice 1", "choice1" }, {
//				"C&hoice 2", "choice2" }
//		}, getFieldEditorParent()));
//		addField(
//			new StringFieldEditor(PreferenceConstants.P_STRING, "A &text preference:", getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}