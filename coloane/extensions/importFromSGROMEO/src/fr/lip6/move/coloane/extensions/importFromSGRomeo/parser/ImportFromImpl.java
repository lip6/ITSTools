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
package fr.lip6.move.coloane.extensions.importFromSGRomeo.parser;

import fr.lip6.move.coloane.interfaces.exceptions.ExtensionException;
import fr.lip6.move.coloane.interfaces.extensions.IImportFrom;
import fr.lip6.move.coloane.interfaces.formalism.IFormalism;
import fr.lip6.move.coloane.interfaces.model.IGraph;

import java.io.IOException;
import java.util.logging.Logger;

import main.antlr3.fr.lip6.move.coloane.extensions.importFromSGRomeo.parser.SGRomeoLexer;
import main.antlr3.fr.lip6.move.coloane.extensions.importFromSGRomeo.parser.SGRomeoParser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * A class to do import of romeo SG files
 * @author Yann
 *
 */
public class ImportFromImpl implements IImportFrom {
	/** Le logger pour la classe */
	private static final Logger LOGGER = Logger.getLogger("fr.lip6.move.coloane.core"); //$NON-NLS-1$


	/**
	 * Import a PROD format model
	 * @param filePath nom de fchier a importer
	 * @param formalism the formalism of this file
	 * @param monitor the progress bar
	 * @return le model adapte correspondant
	 * @throws ExtensionException si le fichier n'est pas valide
	 */
	public final IGraph importFrom(String filePath, IFormalism formalism, IProgressMonitor monitor) throws ExtensionException {
		LOGGER.finer("Creation du fichier..."); ////$NON-NLS-1$

		SGRomeoLexer lexer;
		try {
			lexer = new SGRomeoLexer(new ANTLRFileStream(filePath));
		} catch (IOException e) {
			throw new ExtensionException("Problem opening file " + e.getMessage());
		}

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		SGRomeoParser parser = new SGRomeoParser(tokens);
		parser.setFormalism(formalism);
		IGraph graph;
		try {
			graph = parser.romeoSGModel();
		} catch (RecognitionException e) {
			throw new ExtensionException("Error parsing prod file " + e.getMessage());
		}
		return graph;

	}

}

