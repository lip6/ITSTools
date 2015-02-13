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
package fr.lip6.move.gal.pnml.togal.popup.actions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.xml.sax.SAXException;

import fr.lip6.move.gal.order.IOrder;


/**
 * Classe regroupant les outils utiles au chargement d'un modèle à partir d'un fichier xml
 */
public final class NupnReader {
	private static final Logger LOGGER = Logger.getLogger("fr.lip6.move.gal"); //$NON-NLS-1$

	/**
	 * Classe ne contenant que des méthode statique.
	 */
	private NupnReader() {	}



	/**
	 * @param stringBuffer URI du fichier XML contenant le modèle à charger
	 * @param formalism formalism read
	 * @return IGraph construit à partir du fichier XML
	 */
	public static IOrder loadFromXML(StringBuffer stringBuffer) {
		NupnHandler modelHandler = new NupnHandler();
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			SAXParser saxParser = factory.newSAXParser();
			long debut = System.currentTimeMillis();
			saxParser.parse(new ByteArrayInputStream(stringBuffer.toString().getBytes(StandardCharsets.UTF_8)), modelHandler);
			LOGGER.info("Temps de chargement : " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (SAXException e) {
			LOGGER.warning("Parse error while analyzing file "+ stringBuffer + ".\n details:"+ e.getMessage()); //$NON-NLS-1$
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.warning("IO exception : " + e.getMessage()); //$NON-NLS-1$
		} catch (ParserConfigurationException e) {
			LOGGER.warning("Error at ITS parser creation. " + e.getMessage()); //$NON-NLS-1$
		}

		return modelHandler.getOrder();
	}

}
