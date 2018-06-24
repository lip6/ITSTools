package fr.lip6.move.gal.ltsmin.preference;

import java.io.IOException;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.lip6.move.gal.ltsmin.BinaryToolsPlugin;
import fr.lip6.move.gal.ltsmin.BinaryToolsPlugin.Tool;
import fr.lip6.move.gal.ltsmin.preference.LTSminPreferencesActivator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = LTSminPreferencesActivator.getDefault().getPreferenceStore();
		
//		store.setDefault(PreferenceConstants.GCTHRESHOLD, 1300000);
		
		try {
			store.setDefault(PreferenceConstants.LTSMINSEQ_EXE, BinaryToolsPlugin.getProgramURI(Tool.seq).getPath().toString() );
		} catch (IOException e) {
			store.setDefault(PreferenceConstants.LTSMINSEQ_EXE, "Your platform seems to be unsupported." );
		}

	}

}
