/**
 * A fast SAX parser for PNML files that builds a Sparse Petri Net.
 */
package fr.lip6.move.gal.structural.pnml;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import fr.lip6.move.gal.structural.SparsePetriNet;

/**
 * Classe regroupant les outils utiles au chargement d'un modèle à partir d'un fichier xml
 */
public final class PTNetReader {
	private static final Logger LOGGER = Logger.getLogger("fr.lip6.move.gal"); //$NON-NLS-1$
	private SparsePetriNet net = null;

	/**
	 * @param stringBuffer URI du fichier XML contenant le modèle à charger
	 * @param formalism formalism read
	 * @return IGraph construit à partir du fichier XML
	 */
	public SparsePetriNet loadFromXML(InputStream in) throws IllegalArgumentException {
		PTNetHandler modelHandler = new PTNetHandler();
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			SAXParser saxParser = factory.newSAXParser();
			long debut = System.currentTimeMillis();
			saxParser.parse(in, modelHandler);
			LOGGER.info("Load time of PNML (sax parser for PT used): " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (SAXException e) {
			LOGGER.warning("Parse error while parsing toolspecific elements in pnml.\n details:"+ e.getMessage()); //$NON-NLS-1$
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.warning("IO exception : " + e.getMessage()); //$NON-NLS-1$
		} catch (ParserConfigurationException e) {
			LOGGER.warning("Error in parser configuration. " + e.getMessage()); //$NON-NLS-1$
			e.printStackTrace();
		} 
		net = modelHandler.getParseResult();
		return modelHandler.getParseResult();
	}
	
	public SparsePetriNet getNet() {
		return net;
	}

}
