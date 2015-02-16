/**
 * Copyright (c) 2006-2015 MoVe - Laboratoire d'Informatique de Paris 6 (LIP6).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *   Yann THIERRY-MIEG (LIP6)
 * Contributors:
 *   Jean-Baptiste VORON (LIP6) 
 *   Clément DÉMOULINS (LIP6) 
 */
package fr.lip6.move.gal.nupn;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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


	public static IOrder loadFromXML(StringBuffer stringBuffer) {
		System.out.println("Sawtoolspecific :"+ stringBuffer);
		//patch pnmlFW bug: opening tag not in buffer
		return loadFromXML(new ByteArrayInputStream( stringBuffer.toString().getBytes(StandardCharsets.UTF_8)));
	}

	/**
	 * @param stringBuffer URI du fichier XML contenant le modèle à charger
	 * @param formalism formalism read
	 * @return IGraph construit à partir du fichier XML
	 */
	public static IOrder loadFromXML(InputStream in) {
		NupnHandler modelHandler = new NupnHandler();
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			SAXParser saxParser = factory.newSAXParser();
			long debut = System.currentTimeMillis();
			saxParser.parse(in, modelHandler);
			LOGGER.info("Load time : " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (SAXException e) {
			LOGGER.warning("Parse error while parsing toolspecific elements in pnml.\n details:"+ e.getMessage()); //$NON-NLS-1$
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.warning("IO exception : " + e.getMessage()); //$NON-NLS-1$
		} catch (ParserConfigurationException e) {
			LOGGER.warning("Error at ToolSpecific Nupn parser creation. " + e.getMessage()); //$NON-NLS-1$
		}

		return modelHandler.getOrder();
	}

}
