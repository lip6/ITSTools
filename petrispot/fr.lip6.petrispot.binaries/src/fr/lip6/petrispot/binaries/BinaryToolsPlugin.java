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
package fr.lip6.petrispot.binaries;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Logger;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class BinaryToolsPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "fr.lip6.petrispot.binaries"; //$NON-NLS-1$

	// The shared instance
	private static BinaryToolsPlugin plugin;

	private static URI petriUri = null;

	/**
	 * The constructor
	 */
	public BinaryToolsPlugin() {
	}

	/** {@inheritDoc} */
	public final void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/** {@inheritDoc} */
	public final void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static BinaryToolsPlugin getDefault() {
		return plugin;
	}

	private static final Logger log = Logger.getLogger("fr.lip6.move.gal"); //$NON-NLS-1$

	/** This bundle's symbolic name, the prefix of its folder in the product's plugins/. */
	private static final String BUNDLE = "fr.lip6.petrispot.binaries";
	/** System property naming the product's plugins/ folder when no framework runs and the class folder is not the bundle (a native image). */
	private static final String BINARIES_ROOT = "fr.lip6.binaries.root";

	/**
	 * A resource of this bundle: through the framework when there is one; on a
	 * flat classpath (no framework, getDefault() is null) the bundle is the
	 * unpacked folder this class was loaded from, plugins/<id>_<version>/, where
	 * bin/ sits beside the classes. Loaded from a jar instead, the binaries are
	 * expected unpacked in a folder of the jar's name beside it.
	 */
	private static URL bundleResource(String relativePath) {
		if (getDefault() != null) {
			return getDefault().getBundle().getResource(relativePath);
		}
		try {
			// an explicit plugins folder (a native image has no class folder to start from)
			String plugins = System.getProperty(BINARIES_ROOT);
			if (plugins != null) {
				File[] dirs = new File(plugins).listFiles((d, n) -> n.startsWith(BUNDLE + "_") || n.equals(BUNDLE));
				if (dirs == null || dirs.length == 0) {
					return null;
				}
				File f = new File(dirs[0], relativePath);
				return f.exists() ? f.toURI().toURL() : null;
			}
			File root = new File(BinaryToolsPlugin.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			if (root.isFile()) {
				root = new File(root.getParentFile(), root.getName().replaceFirst("\\.jar$", ""));
			}
			File f = new File(root, relativePath);
			return f.exists() ? f.toURI().toURL() : null;
		} catch (URISyntaxException | java.net.MalformedURLException e) {
			return null;
		}
	}

	/** The entries of bin/, for the diagnostic when a tool is missing; none without a framework. */
	private static Enumeration<URL> listBin() {
		if (getDefault() == null) {
			return java.util.Collections.emptyEnumeration();
		}
		return getDefault().getBundle().findEntries("bin/", "*", true);
	}

	/**
	 * Returns the URI of the petri64 binary for the current platform.
	 */
	public static URI getPetriURI() throws IOException {
		if (petriUri == null) {
			String relativePath = "bin/" + getPetriExecutableName();
			URL resource = bundleResource(relativePath);
			if (resource == null) {
				log.severe("unable to find PetriSpot binary in path " + relativePath);
				Enumeration<URL> e = listBin();
				log.fine("Listing URLs available in bin/");
				while (e.hasMoreElements()) {
					log.finer(e.nextElement().toString());
				}
				throw new IOException("unable to find the PetriSpot binary");
			}
			URL fileUrl = FileLocator.toFileURL(resource);
			try {
				petriUri = new URI(fileUrl.getProtocol(), fileUrl.getPath(), null);
			} catch (URISyntaxException e) {
				throw new IOException("Could not create a URI to access the PetriSpot binary:", e);
			}
			log.fine("Location of PetriSpot binary: " + petriUri);

			File executable = new File(petriUri);
			if (!executable.setExecutable(true)) {
				log.severe("unable to make PetriSpot executable [" + petriUri + "]");
				throw new IOException("unable to make PetriSpot executable");
			}
		}
		return petriUri;
	}

	/**
	 * Returns the platform-specific executable name for PetriSpot.
	 */
	private static String getPetriExecutableName() throws IOException {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("windows")) {
			return "petri64.exe";
		} else if (osName.contains("linux")) {
			return "petri64";
		} else if (osName.contains("mac os x") || osName.contains("darwin")) {
			return "petri64-mac";
		} else {
			throw new IOException("System platform not supported by PetriSpot: " + osName);
		}
	}
}
