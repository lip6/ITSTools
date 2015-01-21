package fr.lip6.move.promela.serialization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Guice;
import com.google.inject.Injector;

import fr.lip6.move.promela.PromelaRuntimeModule;
import fr.lip6.move.promela.promela.PromelaSpecification;


/**
 * Utility class for serialization of gal system
 */
public class SerializationUtil  {
	
	
	/**
	 * Create a new file and return a Resource from this file.
	 */
	private static Resource createResource(String filename)
	{
		Injector inj = createInjector();
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
		return Guice.createInjector(new PromelaRuntimeModule());
	}
	
	/**
	 * This method serialize a Promela System in the file {@code filename} 
	 * @param system The root of Gal system
	 * @param filename The output filename.
	 */
	@SuppressWarnings("deprecation")
	public static void systemToFile(PromelaSpecification system, String filename) throws IOException
	{
		if(! filename.endsWith(".pml"))
		{
			java.lang.System.err.println("Warning: filename '" + filename + "' should end with .pml extension ");
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
		// force refresh
		try {
			for (IFile file  : ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new java.net.URI("file://" +filename))) {
				file.refreshLocal(IResource.DEPTH_ZERO, null);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Returns a Promela system from a filename .pml
	 */
	public static PromelaSpecification fileToPromelaSpecification(String filename)
	{
		if(! filename.endsWith(".pml"))
		{
			java.lang.System.err.println("Warning: filename '" + filename + "' should end with .pml extension ");
		}
		
		Resource res = loadResources(filename); 
		PromelaSpecification system = (PromelaSpecification) res.getContents().get(0);		

		IResourceValidator validator = createInjector().getInstance(IResourceValidator.class);
		for (Issue issue : validator.validate(res, CheckMode.ALL, CancelIndicator.NullImpl)) {
			if (issue.getSeverity() == Severity.ERROR) {
				throw new IllegalArgumentException("Your model in file "+ filename +"contains errors ! Please correct them before working on file. ");
			}
		}
		
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