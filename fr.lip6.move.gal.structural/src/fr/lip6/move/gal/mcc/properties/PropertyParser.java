package fr.lip6.move.gal.mcc.properties;

import java.io.File;
import java.io.IOException;

import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;

public class PropertyParser {
	
	public static int  fileToProperties(String path, PetriNet ptnet, PropertyType propertyType) throws IOException {
		PropReader.readXMLPropertiesIntoProps(new File(path), ptnet);
		for (Property prop : ptnet.getProperties()) {
			prop.setType(propertyType);
		}
		return ptnet.getProperties().size();
	}

}
