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
package fr.lip6.move.gal.ltsmin;

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
	public static final String PLUGIN_ID = "fr.lip6.move.gal.ltsmin.binaries"; //$NON-NLS-1$

	// The shared instance
	private static BinaryToolsPlugin plugin;

	// variants of the tool : multicore, sequential, symbolic...
	public enum Tool {mc, seq, sym};
	private static URI toolUri [] = new URI [3];
	
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

	
	
	public static URI getProgramURI(Tool tool) throws IOException {
		if (toolUri[tool.ordinal()] == null) {
			String relativePath = "bin/pins2lts-"+ tool.toString() + "-" + getArchOS();
			URL toolff = getDefault().getBundle().getResource(relativePath);
			if (toolff == null) {
				log.severe("unable to find an executable [" + tool + "] in path " + relativePath);
				Enumeration<URL> e = getDefault().getBundle().findEntries("bin/", "*", true);
				log.fine("Lising URL available in bin/");
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
			toolUri[tool.ordinal()] = uri;
			log.fine("Location of the binary : " + toolUri);

			File crocExec = new File(uri);
			if (!crocExec.setExecutable(true)) {
				log.severe("unable to make the command-line tool executable [" + toolUri + "]");
				throw new IOException("unable to make the command-line tool executable");
			}		

		}
		return toolUri[tool.ordinal()];
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
