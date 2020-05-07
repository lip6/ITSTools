package fr.lip6.move.gal.mcc.properties;

import java.io.File;
import java.io.IOException;

import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class PropertyParser {
	
	public static int  fileToProperties(String path, PetriNet ptnet, PropertyType propertyType) throws IOException {
		File ff = new File(path);
		if (ff.exists()) {
			PropReader.readXMLPropertiesIntoProps(ff, ptnet, propertyType == PropertyType.LTL);
			for (Property prop : ptnet.getProperties()) {
				prop.setType(propertyType);
			}
		} else {
			if (propertyType == PropertyType.DEADLOCK) {
				fr.lip6.move.gal.structural.Property deadlockProp = 
						new fr.lip6.move.gal.structural.Property(Expression.op(Op.EF,Expression.op(Op.DEAD, null, null),null), propertyType ,"ReachabilityDeadlock");
				ptnet.getProperties().add(deadlockProp);
			} else {
				System.out.println("DO_NOT_COMPETE");
			}
		}
		return ptnet.getProperties().size();
	}

}
