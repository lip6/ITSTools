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
		
		try {
			store.setDefault(PreferenceConstants.LTSMINSEQ_EXE, BinaryToolsPlugin.getProgramURI(Tool.seq).getPath().toString() );
		} catch (IOException e) {
			store.setDefault(PreferenceConstants.LTSMINSEQ_EXE, "Your platform seems to be unsupported." );
		}
		try {
			store.setDefault(PreferenceConstants.LTSMINMC_EXE, BinaryToolsPlugin.getProgramURI(Tool.mc).getPath().toString() );
		} catch (IOException e) {
			store.setDefault(PreferenceConstants.LTSMINMC_EXE, "Your platform seems to be unsupported." );
		}
		
		try {
			store.setDefault(PreferenceConstants.LTSMININC_DIR, BinaryToolsPlugin.getIncludeFolderURI().getPath().toString());
		} catch (IOException e) {
			store.setDefault(PreferenceConstants.LTSMININC_DIR, "Your platform seems to be unsupported." );
		}
	}

}
