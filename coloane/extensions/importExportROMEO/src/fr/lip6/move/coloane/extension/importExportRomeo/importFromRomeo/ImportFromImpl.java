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
package fr.lip6.move.coloane.extension.importExportRomeo.importFromRomeo;

import fr.lip6.move.coloane.interfaces.exceptions.ExtensionException;
import fr.lip6.move.coloane.interfaces.extensions.IImportFrom;
import fr.lip6.move.coloane.interfaces.formalism.IFormalism;
import fr.lip6.move.coloane.interfaces.model.IGraph;

import java.util.logging.Logger;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;


/**
 * Import a Romeo model into a graph model.
 *
 * @author Yann TM
 */
public class ImportFromImpl implements IImportFrom {
	/** The logger */
	private static final Logger LOGGER = Logger.getLogger("fr.lip6.move.coloane.core"); //$NON-NLS-1$

	/**
	 * Import a Romeo State class graph or Zone based graph file into a Graph object
	 * @param filePath The location of the file to be imported
	 * @param formalism The formalism (since CAMI file does not define the model formalism)
	 * @param monitor A monitor to follow the operation progress
	 * @return The resulting model {@link IGraph}
	 * @throws ExtensionException Something went wrong
	 */
	public final IGraph importFrom(String filePath, IFormalism formalism, IProgressMonitor monitor) throws ExtensionException {
		IGraph model = null;

		LOGGER.finer("Creation du fichier..."); ////$NON-NLS-1$
		IPath path = new Path(filePath);
		IFileStore file = EFS.getLocalFileSystem().getStore(path);
		model = ModelLoader.loadFromXML(file.toURI(), formalism);

		return model;
	}


}
