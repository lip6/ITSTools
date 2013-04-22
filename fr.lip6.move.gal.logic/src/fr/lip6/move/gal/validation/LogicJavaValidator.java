package fr.lip6.move.gal.validation;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.validation.Check;

import fr.lip6.move.gal.logic.Properties;
 

public class LogicJavaValidator extends AbstractLogicJavaValidator {

	
	@Check
	public void checkSimplifier (Properties p) {
		Resource root = EcoreUtil.getRootContainer(p).eResource();
//		try {
//			Properties p2 = EcoreUtil.copy(p);
//			LogicSimplifier.simplify(p2);
//
//			synchronized(this) {
//
//			String path = root.getURI().toPlatformString(true).split(".prop")[0];
//			String outpath =  path+".flat.prop";
//			
//			URI uri = URI.createPlatformResourceURI(path, true);
//			//URI uri2 = uri.resolve(URI.createURI("/"));
//			//String outpath =  file.getRawLocationURI().getPath()+".flat.gal";
//
//			FileOutputStream out = new FileOutputStream(new File(outpath));
//			out.write(0);
//			out.close();
//			SerializationUtil.systemToFile(p2,outpath);
//			}
//			java.lang.System.err.println("On a passe la serialization");
//
//		} catch (Exception e) {
////			MessageDialog.openWarning(
////					shell,
////					"Flatten",
//			System.err.println("Flatten GAL operation raised an exception " + e.getMessage());
//			e.printStackTrace();
//			return;

//		}

		
	}
	
//	@Check
//	public void checkGreetingStartsWithCapital(Greeting greeting) {
//		if (!Character.isUpperCase(greeting.getName().charAt(0))) {
//			warning("Name should start with a capital", MyDslPackage.Literals.GREETING__NAME);
//		}
//	}

}
