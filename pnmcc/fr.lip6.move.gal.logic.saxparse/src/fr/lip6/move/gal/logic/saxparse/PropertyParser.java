package fr.lip6.move.gal.logic.saxparse;

import java.io.File;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.logic.LogicFactory;
import fr.lip6.move.gal.logic.Properties;

public class PropertyParser {

	
	
	public static Properties  fileToProperties(String path, Specification spec) {
				
		Properties props = LogicFactory.eINSTANCE.createProperties();
		props.setSystem((GALTypeDeclaration) spec.getTypes().get(0));
		
		PropReader.readXMLPropertiesIntoProps(new File(path), spec, props);
				
		return props;
	}
}
