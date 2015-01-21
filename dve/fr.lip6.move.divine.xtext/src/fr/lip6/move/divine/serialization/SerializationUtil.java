package fr.lip6.move.divine.serialization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Guice;
import com.google.inject.Injector;

import fr.lip6.move.divine.DivineRuntimeModule;
import fr.lip6.move.divine.divine.DivineSpecification;


/**
 * Utility class for serialization of gal system
 */
public class SerializationUtil  {
	
	
	/**
	 * Create a new file and return a Resource from this file.
	 */
	private static Resource createResource(String filename)
	{
		// GalStandaloneSetup.doSetup() ;
		createInjector();
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

	private static Injector createInjector() {
		return Guice.createInjector(new DivineRuntimeModule());
	}

	
	/**
	 * This method serialize a Gal System in the file {@code filename} 
	 * @param system The root of Gal system
	 * @param filename The output filename.
	 */
	@SuppressWarnings("deprecation")
	public static void systemToFile(DivineSpecification system, String filename) throws IOException
	{
		if(! filename.endsWith(".dve"))
		{
			java.lang.System.err.println("Warning: filename '" + filename + "' should end with .dve extension ");
		}
		
		
		// System.out.print("Serializing...");
		// Creating new resource and binding it to system
		Resource resource = createResource(filename);
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
	 * Returns a Divine from a filename .dve
	 */
	public static DivineSpecification fileToDivineSpecification(String filename)
	{
		if(! filename.endsWith(".dve"))
		{
			java.lang.System.err.println("Warning: filename '" + filename + "' should end with .dve extension ");
		}
		
		Resource res = loadResources(filename); 
		DivineSpecification system = (DivineSpecification) res.getContents().get(0);
		
		return system ;
	}


	
	/**
	 * Load a GAL file and returns a Resources from this file.
	 */
	private static Resource loadResources(String filename) 
	{
		
		Injector inj = createInjector(); 
		
		XtextResourceSet resourceSet = inj.getInstance(XtextResourceSet.class); 
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		URI uri = URI.createFileURI(filename) ; 
		Resource resource = resourceSet.getResource(uri, true);
		return resource ; 
	}
	
	
}