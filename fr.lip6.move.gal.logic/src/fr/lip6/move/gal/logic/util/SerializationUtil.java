package fr.lip6.move.gal.logic.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Inject;
import com.google.inject.Injector;

import fr.lip6.move.GalStandaloneSetup;
import fr.lip6.move.gal.LogicStandaloneSetup;
import fr.lip6.move.gal.logic.Properties;


/**
 * Utility class for serialization of gal system
 */
public class SerializationUtil  {
	
	
	@Inject
	private static XtextResourceSet resourceSet;
	
	/**
	 * Create a new file and return a Resource from this file.
	 */
	private static Resource createResource(String filename)
	{
		LogicStandaloneSetup.doSetup() ; 
		ResourceSet resourceSet = new ResourceSetImpl();
		try {
			// Will void the file, or create it if not exists.  
			new FileWriter(new File(filename)).close() ; 
			
			URI uri = URI.createFileURI(filename) ; 
			Resource resource = resourceSet.getResource(uri, true);
			return resource ; 
		
		} catch (IOException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	
	/**
	 * This method serialize a Gal System in the file {@code filename} 
	 * @param system The root of Gal system
	 * @param filename The output filename.
	 */
	@SuppressWarnings("deprecation")
	public static void systemToFile(Properties system, String filename) throws IOException
	{
		if(! filename.endsWith(".prop"))
		{
			java.lang.System.err.println("Warning: filename '" + filename + "' should end with .prop extension ");
		}
		
		
		// System.out.print("Serializing...");
		// Creating new resource and binding it to system
		Injector inj = new GalStandaloneSetup().createInjectorAndDoEMFRegistration(); 
		
		XtextResourceSet resourceSet = inj.getInstance(XtextResourceSet.class); 
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		String galfile = filename.replace(".prop", ".gal");
		
		URI uri2 = URI.createPlatformPluginURI(galfile, true); 
		Resource resource = resourceSet.getResource(uri2, true);

		resource.getContents().add(system);
		
		// Options for serializarion. @see XtextResource.OPTION_xxx
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(XtextResource.OPTION_FORMAT, true); 
		
		try 
		{
			
			FileOutputStream os = new FileOutputStream(filename);
			system.eResource().save(os, map);
			os.close();
//			java.lang.System.out.println("Done");
//			java.lang.System.out.println("You can see result in file: " + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Returns a GAL system from a filename .gal
	 */
	public static Properties fileToProperties(String filename)
	{
		if(! filename.endsWith(".prop"))
		{
			java.lang.System.err.println("Warning: filename '" + filename + "' should end with .prop extension ");
		}
		
		Resource res = loadResources(filename); 
		Properties system = (Properties) res.getContents().get(0);
		
		fr.lip6.move.gal.System s = system.getSystem();
		if (s.eIsProxy()) {
			EcoreUtil.resolveAll(system);
		}
		
		return system ;
	}


	
	/**
	 * Load a GAL file and returns a Resources from this file.
	 */
	private static Resource loadResources(String filename) 
	{
		
//		Injector inj = new GalStandaloneSetup().createInjectorAndDoEMFRegistration(); 
//		
//		XtextResourceSet resourceSet = inj.getInstance(XtextResourceSet.class); 
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		String galfile = filename.replace(".prop", ".gal");
		
		URI uri2 = URI.createFileURI(galfile) ; 
		Resource resource = resourceSet.getResource(uri2, true);
		
		URI uri = URI.createFileURI(filename) ; 		
		Resource resource2 = resourceSet.getResource(uri, true);
		EcoreUtil.resolveAll(resource2);
		return resource2 ; 
	}
	
	
}