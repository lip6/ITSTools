//package fr.lip6.move.gal.logic.saxparse.test;
//
//import java.io.File;
//import java.util.List;
//
//import fr.lip6.move.gal.Property;
//import fr.lip6.move.gal.Specification;
//import fr.lip6.move.gal.pnml.togal.PnmlToGalTransformer;
//import fr.lip6.move.gal.pnml.togal.popup.actions.PropReader;
//import fr.lip6.move.serialization.SerializationUtil;
//
//public class TestMain {
//
//	
//	
//	public static void main(String[] args) {
//
//		
//		SerializationUtil.setStandalone(true);
//		
//		File fileProp = new File(
//				"tests/ReachabilityCardinality.xml");
//		
//		PnmlToGalTransformer trans = new PnmlToGalTransformer();
//		spec = trans.transform( new File("tests/philo-5-col.pnml") ) ;
//		order = trans.getOrder();
//		
//		
//		try {
//		Specification fileToGalSystem = SerializationUtil.fileToGalSystem("tests/.gal");
//		} catch (Exception e) {
//			
//			
//			
//		}
//		// File fileProp = new
//		// File("/examples.pnml/p/ReachabilityCardinality.xml");
//		List<Property> propertiesList = PropReader.readXMLProperties(fileProp);
//		for (Property p : propertiesList) {
//
//			System.out.println("Name is: " + p.getName());
//			System.out.println("Body is: " + p.getBody());
//		}
//	}
//}
