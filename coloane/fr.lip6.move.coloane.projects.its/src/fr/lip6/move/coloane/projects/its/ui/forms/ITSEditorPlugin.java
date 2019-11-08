/**
 * Copyright (c) 2006-2010 MoVe - Laboratoire d'Informatique de Paris 6 (LIP6).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jean-Baptiste VORON (LIP6) - Project Head / Initial contributor
 *   Clément DÉMOULINS (LIP6) - Project Manager
 *   Yann THIERRY-MIEG (LIP6)
 *
 * Official contacts:
 *   coloane@lip6.fr
 *   http://coloane.lip6.fr
 */
package fr.lip6.move.coloane.projects.its.ui.forms;

import fr.lip6.move.coloane.core.main.Coloane;
import fr.lip6.move.coloane.core.model.factory.FormalismManager;
import fr.lip6.move.coloane.interfaces.formalism.IFormalism;
import fr.lip6.move.coloane.projects.its.TypeList;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;


/**
 * The main plugin class to be used in the desktop.
 */
public final class ITSEditorPlugin extends AbstractUIPlugin {
	public static final String IMG_FORM_BG = "formBg"; //$NON-NLS-1$
	public static final String IMG_LARGE = "large"; //$NON-NLS-1$
	public static final String IMG_HORIZONTAL = "horizontal"; //$NON-NLS-1$
	public static final String IMG_VERTICAL = "vertical"; //$NON-NLS-1$
	public static final String IMG_SAMPLE = "sample"; //$NON-NLS-1$
	public static final String IMG_WIZBAN = "wizban"; //$NON-NLS-1$
	public static final String IMG_LINKTO_HELP = "linkto_help"; //$NON-NLS-1$
	public static final String IMG_HELP_TOPIC = "help_topic"; //$NON-NLS-1$
	public static final String IMG_CLOSE = "close"; //$NON-NLS-1$
	public static final String IMG_VARIABLE = "variable";
	public static final String IMG_REFRESH = "refresh";
	public static final String IMG_USETVAR = "variable_unset";
	public static final String IMG_SETVAR = "variable_set";
	public static final String IMG_RESULTOK = "result_ok";
	public static final String IMG_RESULTNOK = "result_nok";
	public static final String IMG_RESULTFAIL = "result_fail";
	public static final String IMG_REACH_SERVICE = "reach_service";
	public static final String IMG_COMPOSITE = "composite_formalism";
	public static final String IMG_TPNFORM = "tpn_formalism";
	public static final String IMG_INSTANCE = "instance";
	public static final String IMG_TRANSITION = "transition";
	public static final String IMG_PLACE = "place";
	public static final String IMG_GAL = "gal";

	public static final String ITS_REACH_NAME = "its-reach";
	public static final String ITS_CTL_NAME = "its-ctl";
	public static final String ORDERING_NAME = "IGenerateOrder";
	public static final String PYTHON_PATH = "python";

	private static String ID = ITSEditorPlugin.class.getPackage().getName();

	// The shared instance.
	private static ITSEditorPlugin plugin;
	// Resource bundle.
	private ResourceBundle resourceBundle;
	private FormColors formColors;

	/**
	 * The constructor.
	 */
	public ITSEditorPlugin() {
		plugin = this;
		System.out.println("<<<< ITS >>>>");
		try {
			resourceBundle = ResourceBundle
					.getBundle("org.eclipse.ui.forms.examples.internal.ExamplesPluginResources"); //$NON-NLS-1$
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	/**
	 * @return the ID
	 */
	public static String getID() {
		return ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initializeImageRegistry(ImageRegistry registry) {
		registerImage(registry, IMG_FORM_BG, "form_banner.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_LARGE, "large_image.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_HORIZONTAL, "th_horizontal.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_VERTICAL, "th_vertical.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_SAMPLE, "sample.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_WIZBAN, "newprj_wiz.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_LINKTO_HELP, "linkto_help.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_HELP_TOPIC, "help_topic.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_CLOSE, "close_view.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_VARIABLE, "variable_tab.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_REFRESH, "refresh.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_SETVAR, "set_variable.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_USETVAR, "unset_variable.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_RESULTOK, "success_check.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_RESULTNOK, "error_check.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_RESULTFAIL, "problem_check.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_GAL, "set_variable.gif"); //$NON-NLS-1$
		registerImage(registry, IMG_REACH_SERVICE, "progress_rem.png"); //$NON-NLS-1$
				
		// images grabbed from coloane formalisms
		IFormalism f = FormalismManager.getInstance().getFormalismById(
				"ITS Composite");
		registry.put(IMG_COMPOSITE,
				ImageDescriptor.createFromFile(Coloane.class, f.getImageName()));

		String img = f.getRootGraph().getElementFormalism("instance")
				.getGraphicalDescription().getIcon24px();
		registry.put(IMG_INSTANCE,
				ImageDescriptor.createFromFile(Coloane.class, img));

		f = FormalismManager.getInstance().getFormalismById("Time Petri Net");
		registry.put(IMG_TPNFORM,
				ImageDescriptor.createFromFile(Coloane.class, f.getImageName()));

		img = f.getRootGraph().getElementFormalism("transition")
				.getGraphicalDescription().getIcon24px();
		registry.put(IMG_TRANSITION,
				ImageDescriptor.createFromFile(Coloane.class, img));

		img = f.getRootGraph().getElementFormalism("place")
				.getGraphicalDescription().getIcon24px();
		registry.put(IMG_PLACE,
				ImageDescriptor.createFromFile(Coloane.class, img));
	}

	/**
	 * Add an image to registry
	 * 
	 * @param registry
	 *            the referential
	 * @param key
	 *            the image id
	 * @param fileName
	 *            the image file path
	 */
	private void registerImage(ImageRegistry registry, String key,
			String fileName) {
		try {
			IPath path = new Path("resources/icons/" + fileName); //$NON-NLS-1$
			URL url = FileLocator.find(getBundle(), path, null);
			if (url != null) {
				ImageDescriptor desc = ImageDescriptor.createFromURL(url);
				registry.put(key, desc);
			}
		} catch (Exception e) {
			warning("An error occured while loading image for plugin:"
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * returns form colors of a display (singleton factory)
	 * 
	 * @param display
	 *            the display
	 * @return the new formcolors or existing one
	 */
	public FormColors getFormColors(Display display) {
		if (formColors == null) {
			formColors = new FormColors(display);
			formColors.markShared();
		}
		return formColors;
	}

	/**
	 * Returns the shared instance.
	 * 
	 * @return sole instance
	 */
	public static ITSEditorPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the workspace instance.
	 * 
	 * @return sole instance
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not
	 * found.
	 * 
	 * @param key
	 *            to the bundle
	 * @return the id of a bundle
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = ITSEditorPlugin.getDefault()
				.getResourceBundle();
		try {
			// CHECKSTYLE OFF
			return (bundle != null ? bundle.getString(key) : key);
			// CHECKSTYLE ON
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle
	 * 
	 * @return my own resource bundle
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		try {
			if (formColors != null) {
				formColors.dispose();
				formColors = null;
			}
		} finally {
			super.stop(context);
		}
	}

	/**
	 * Great user API to get icons
	 * 
	 * @param key
	 *            uri of the image
	 * @return an image
	 */
	public Image getImage(String key) {
		return getImageRegistry().get(key);
	}

	/**
	 * Great user API to get icons
	 * 
	 * @param key
	 *            uri of the image
	 * @return an image
	 */
	public ImageDescriptor getImageDescriptor(String key) {
		return getImageRegistry().getDescriptor(key);
	}

	/**
	 * Returns whether the given file is executable. Depending on the platform
	 * we might not get this right.
	 * 
	 * @param file
	 *            the file to test
	 * @return true if executable is detected with certitude
	 */
	public static boolean isExecutable(File file) {
		if (!file.isFile()) {
			return false;
		}
		if (Platform.getOS().equals(Platform.OS_WIN32)) {
			// executable attribute is a *ix thing, on Windows all files are
			// executable
			return true;
		}
		IFileStore store = EFS.getLocalFileSystem().fromLocalFile(file);
		if (store == null) {
			return false;
		}
		return store.fetchInfo().getAttribute(EFS.ATTRIBUTE_EXECUTABLE);
	}
	/**
	 * Position the path.
	 * @param text new path.
	 */
	public void setITSReachPath(String text) {
		setPreference(ITS_REACH_NAME, text);
	}
	/**
	 * Position the path.
	 * @param text new path.
	 */
	public void setITSCTLPath(String text) {
		setPreference(ITS_CTL_NAME, text);
	}

	/**
	 * Position the path.
	 * @param text new path.
	 */
	public void setOrderingPath(String text) {
		setPreference(ORDERING_NAME, text);
	}

	/**
	 * Position the path.
	 * @param text new path.
	 */
	public void setPythonPath(String text) {
		setPreference(PYTHON_PATH, text);
	}

	/**
	 * Grab the its-reach path from prefs.
	 * 
	 * @return the path
	 */
	public URI getITSReachPath() {
		return new File(getPreference(ITS_REACH_NAME)).toURI();
	}

	/**
	 * Returns a path built from preference value.
	 * @param preference path name
	 * @return Path object
	 */
	private URI makePath(String preference) {
		if (preference == null) {
			return null;
		} else {
			return new File(preference).toURI();
		}
	}

	/**
	 * Return currently set path in preferences.
	 * @return current its-ctl designated path.
	 */
	public URI getITSCTLPath() {
		return makePath(getPreference(ITS_CTL_NAME));
	}
	/**
	 * Return currently set path for Ordering.py script in preferences.
	 * @return path to Silien Hong NEOPPOD Order script.
	 */
	public URI getOrderingPath() {
		return makePath(getPreference(ORDERING_NAME));
	}
	
	/**
	 * Path to python executable.
	 * @return yep it's necessary on windows.
	 */
	public URI getPythonPath() {
		return makePath(getPreference(PYTHON_PATH));
	}

	/**
	 * Returns the preference with the given name
	 * 
	 * @param preferenceName
	 *            the pref
	 * @return the value
	 */
	public String getPreference(String preferenceName) {
		Preferences node = Platform.getPreferencesService().getRootNode()
				.node(InstanceScope.SCOPE).node(ITSEditorPlugin.ID);
		return node.get(preferenceName, null);
	}

	/**
	 * Sets the given preference to the given value.
	 * 
	 * @param preferenceName
	 *            the preference
	 * @param value
	 *            the value to set
	 */
	private void setPreference(String preferenceName, String value) {
		IEclipsePreferences root = Platform.getPreferencesService()
				.getRootNode();
		Preferences node = root.node(InstanceScope.SCOPE).node(ID);
		node.put(preferenceName, value);
		try {
			node.flush();
		} catch (BackingStoreException e) {
			warning("Error updating preferences." + e);
		}
	}

	/**
	 * Emit a warning with the appropriate logger.
	 * @param e Warning message
	 */
	public static void warning(String e) {
		Logger.getLogger(ID).warning(e);
	}
	

	private TypeList currentModel;

	/**
	 * Returns the last manipulated ITS model. Used to resolve double-click in GUI.
	 * @return null if unset or the current model otherwise.
	 */
	public TypeList getCurrentModel() {
		return currentModel;
	}
	
	/**
	 * Update current model according to currently selected model.
	 * @param currentModel new current model
	 */
	public void setCurrentModel(TypeList currentModel) {
		this.currentModel = currentModel;
	}

}
