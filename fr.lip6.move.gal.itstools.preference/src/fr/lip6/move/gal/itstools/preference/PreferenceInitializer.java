package fr.lip6.move.gal.itstools.preference;

import java.io.IOException;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import fr.lip6.move.coloane.tools.its.BinaryToolsPlugin;
import fr.lip6.move.coloane.tools.its.BinaryToolsPlugin.Tool;
import fr.lip6.move.gal.itstools.preference.GalPreferencesActivator;

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
		IPreferenceStore store = GalPreferencesActivator.getDefault().getPreferenceStore();
		
		store.setDefault(PreferenceConstants.TIMEOUT_DURATION, PreferenceConstants.DEFAULT_TIMEOUT);
		store.setDefault(PreferenceConstants.QUIET_PARAMETER, true);
		store.setDefault(PreferenceConstants.DDDSDD_PARAMETER, true);
		store.setDefault(PreferenceConstants.SCALAR_PARAMETER, PreferenceConstants.DEPTH2);
		store.setDefault(PreferenceConstants.BLOCK_SIZE_PARAMETER, 1);
		store.setDefault(PreferenceConstants.GCTHRESHOLD, 1300000);
		try {
			store.setDefault(PreferenceConstants.ITSREACH_EXE, BinaryToolsPlugin.getProgramURI(Tool.reach).getPath().toString() );
			store.setDefault(PreferenceConstants.ITSCTL_EXE, BinaryToolsPlugin.getProgramURI(Tool.ctl).getPath().toString() );
			store.setDefault(PreferenceConstants.ITSLTL_EXE, BinaryToolsPlugin.getProgramURI(Tool.ltl).getPath().toString() );
		} catch (IOException e) {
			store.setDefault(PreferenceConstants.ITSREACH_EXE, "Your platform seems to be unsupported." );
		}

	}

}
