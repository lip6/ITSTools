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
package fr.lip6.smt.z3.binaries;

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
	public static final String PLUGIN_ID = "fr.lip6.smt.z3.binaries"; //$NON-NLS-1$

	// The shared instance
	private static BinaryToolsPlugin plugin;

	// variants of the tool : only Z3
	public enum Tool {z3};
	private static URI toolUri [] = new URI [1];
	
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
	private static final String BUNDLE = "fr.lip6.smt.z3.binaries";
	/**
	 * System property naming the product's plugins/ folder when no framework
	 * runs; without it a native image looks beside its own executable.
	 */
	private static final String BINARIES_ROOT = "fr.lip6.binaries.root";

	/**
	 * A resource of this bundle. Through the framework when there is one. Without
	 * a framework (flat classpath, native image) the bundle is a folder of the
	 * product's plugins/: the folder this class was loaded from when the class
	 * path holds the unpacked bundle; else the plugins/ folder named by the
	 * system property, or the one beside the running executable (a native image
	 * has no class folder).
	 */
	private static URL bundleResource(String relativePath) {
		if (getDefault() != null) {
			return getDefault().getBundle().getResource(relativePath);
		}
		try {
			String plugins = System.getProperty(BINARIES_ROOT);
			if (plugins != null) {
				return inPlugins(new File(plugins), relativePath);
			}
			java.security.CodeSource src = BinaryToolsPlugin.class.getProtectionDomain().getCodeSource();
			File root = (src != null && src.getLocation() != null) ? new File(src.getLocation().toURI()) : null;
			if (root == null || (root.isFile() && !root.getName().endsWith(".jar"))) {
				// a native image: the code source is the executable itself, or none; plugins/ sits beside it
				String exe = root != null ? root.getPath() : ProcessHandle.current().info().command().orElse(null);
				if (exe == null) {
					return null;
				}
				return inPlugins(new File(new File(exe).getAbsoluteFile().getParentFile(), "plugins"), relativePath);
			}
			if (root.isFile()) {
				// loaded from a jar: the bundle unpacked in a folder of the jar's name beside it
				root = new File(root.getParentFile(), root.getName().replaceFirst("\\.jar$", ""));
			}
			File f = new File(root, relativePath);
			return f.exists() ? f.toURI().toURL() : null;
		} catch (URISyntaxException | java.net.MalformedURLException e) {
			return null;
		}
	}

	/** The resource in this bundle's folder of a plugins/ folder, or null. */
	private static URL inPlugins(File plugins, String relativePath) throws java.net.MalformedURLException {
		File[] dirs = plugins.listFiles((d, n) -> n.startsWith(BUNDLE + "_") || n.equals(BUNDLE));
		if (dirs == null || dirs.length == 0) {
			return null;
		}
		File f = new File(dirs[0], relativePath);
		return f.exists() ? f.toURI().toURL() : null;
	}

	/** The entries of bin/, for the diagnostic when a tool is missing; none without a framework. */
	private static Enumeration<URL> listBin() {
		if (getDefault() == null) {
			return java.util.Collections.emptyEnumeration();
		}
		return getDefault().getBundle().findEntries("bin/", "*", true);
	}

	public static URI getProgramURI() throws IOException {
		return getProgramURI(Tool.z3);
	}
	
	public static URI getProgramURI(Tool tool) throws IOException {
		if (toolUri[tool.ordinal()] == null) {
			String relativePath=null;		
			relativePath = "bin/"+ tool.toString() + "-" + getArchOS();
			
			URI uri = findRelativeURI(tool.toString(), relativePath);
			toolUri[tool.ordinal()] = uri;
			log.fine("Location of the binary : " + toolUri);

			File executable = new File(uri);
			if (!executable.setExecutable(true)) {
				log.severe("unable to make the command-line tool executable [" + toolUri + "]");
				throw new IOException("unable to make the command-line tool executable");
			}
		}
		return toolUri[tool.ordinal()];
	}

	private static URI findRelativeURI(String tool, String relativePath) throws IOException {
		URL toolff = bundleResource(relativePath);
		if (toolff == null) {
			log.severe("unable to find an executable [" + tool + "] in path " + relativePath);
			Enumeration<URL> e = listBin();
			log.fine("Listing URL available in bin/");
			while (e.hasMoreElements()) {
				log.finer(e.nextElement().toString());
			}
			throw new IOException("unable to find the tool binary");
		}
		URL tmpURL = FileLocator.toFileURL(toolff);

		// use of the multi-argument constructor for URI in order to escape appropriately illegal characters
		URI uri;
		try {
			uri = new URI(tmpURL.getProtocol(), tmpURL.getPath(), null);
		} catch (URISyntaxException e) {
			throw new IOException("Could not create a URI to access the binary tool :", e);
		}
		return uri;
	}
		/**
		 * A method that returns the correct Crocodile executable suffix, depending on the OS and architecture
		 * @return the suffix of the corresponding Crocodile executable
		 * @throws ServiceException if the suffix could not be determined
		 */
		private static String getArchOS() throws IOException {
			String osName = System.getProperty("os.name").toLowerCase();
			String archName = System.getProperty("os.arch").toLowerCase();
			if (osName.contains("mac os x")) {
				return "Darwin";
			}
			String result;
			if (osName.contains("linux")) {				
				result = "linux";
			} else if (osName.contains("windows")) {
				result = "win";
			} else {
				throw new IOException("System architecture not supported : " + osName + " " + archName);				
			}
				
			if (archName.contains("64")) {
				result = result + "64";
			} else if (archName.contains("86")) {
				result = result + "32";
			}
			return result;			
		}
	}
