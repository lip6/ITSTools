package fr.lip6.move.gal.mcc.properties;

import java.io.File;
import java.io.IOException;

import fr.lip6.move.gal.structural.PetriNet;

public class PropertyParser {
	
	public static int  fileToProperties(String path, PetriNet ptnet) throws IOException {
		PropReader.readXMLPropertiesIntoProps(new File(path), ptnet);
				
		return ptnet.getProperties().size();
	}

}
