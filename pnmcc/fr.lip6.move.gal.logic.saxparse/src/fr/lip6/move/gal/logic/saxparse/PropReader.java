package fr.lip6.move.gal.logic.saxparse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.logic.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.util.logging.Logger;

import org.xml.sax.SAXException;

public final class PropReader {

	private static final Logger LOGGER = Logger.getLogger("fr.lip6.move.gal"); //$NON-NLS-1$

	public static void readXMLPropertiesIntoProps(File fileProp, Specification spec, Properties props) {


		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {

			InputStream in = new FileInputStream(fileProp);
			SAXParser saxParser = factory.newSAXParser();
			PropHandler handler = new PropHandler(spec, props);
			long debut = System.currentTimeMillis();
			saxParser.parse(in, handler);			
			//LOGGER.info("Load time of PNML: " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		catch (IOException e) {
			LOGGER.warning("IO exception : " + e.getMessage()); //$NON-NLS-1$
		} catch (ParserConfigurationException e) {
			LOGGER.warning("Error at ToolSpecific Xml parser creation. " + e.getMessage()); //$NON-NLS-1$

		} catch (SAXException e) {
			LOGGER.warning("Parse error while parsing toolspecific elements in xml.\n details:" + e.getMessage()); //$NON-NLS-1$
			e.printStackTrace();
		}

		return;
	}

}
