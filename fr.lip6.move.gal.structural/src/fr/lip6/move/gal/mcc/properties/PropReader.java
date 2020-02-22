package fr.lip6.move.gal.mcc.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import fr.lip6.move.gal.structural.PetriNet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.util.logging.Logger;

import org.xml.sax.SAXException;

public final class PropReader {

	private static final Logger LOGGER = Logger.getLogger("fr.lip6.move.gal"); //$NON-NLS-1$

	public static void readXMLPropertiesIntoProps(File fileProp, PetriNet ptnet) throws IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();

		Exception error = null;
		try {

			InputStream in = new FileInputStream(fileProp);
			SAXParser saxParser = factory.newSAXParser();
			PropHandler handler = new PropHandler(ptnet);
			long debut = System.currentTimeMillis();
			saxParser.parse(in, handler);			
			//LOGGER.info("Load time of PNML: " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		catch (IOException e) {
			LOGGER.warning("IO exception : " + e.getMessage()); //$NON-NLS-1$
			error =e;
		} catch (ParserConfigurationException e) {
			LOGGER.warning("Error at ToolSpecific Xml parser creation. " + e.getMessage()); //$NON-NLS-1$
			error =e;
		} catch (SAXException e) {
			LOGGER.warning("Parse error while parsing toolspecific elements in xml.\n details:" + e.getMessage()); //$NON-NLS-1$
			e.printStackTrace();
			error =e;
		}
		if (error != null) {
			throw new IOException("Parse error while treating translation of formula, possibly this examination is not supported yet.", error);
		}
	}

}
