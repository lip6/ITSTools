package fr.lip6.move.gal.logic.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.resource.XtextResourceSetProvider;

import com.google.inject.Injector;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.ui.internal.LogicActivator;


/**
 * Utility class for serialization of gal system
 */
public class SerializationUtil  {
	
	
	private static XtextResourceSet resourceSet;

	
	private static final Injector injector = LogicActivator
	        .getInstance().getInjector("fr.lip6.move.gal.Logic");

	
	
	public static XtextResourceSet getResourceSet(IFile file) {
		resourceSet = (XtextResourceSet) injector
                    .getInstance(XtextResourceSetProvider.class)
                    .get(file.getProject());
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
        

		return resourceSet;
	}
	
	/**
	 * This method serialize a Gal System in the file {@code filename} 
	 * @param system The root of Gal system
	 * @param filename The output filename.
	 */
	@SuppressWarnings("deprecation")
	public static void systemToFile(Properties system, IFile file) throws IOException
	{
		if(! file.getFileExtension().contains("prop"))
		{
			java.lang.System.err.println("Warning: filename '" + file + "' should end with .prop extension ");
		}
		
		
		if (! file.exists()) {
			InputStream source = new ByteArrayInputStream(" ".getBytes());
			try {
				file.create(source, true, null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// System.out.print("Serializing...");
		// Creating new resource and binding it to system
		XtextResourceSet resourceSet = getResourceSet(file); 
		
		Resource resource = resourceSet.getResource(URI.createURI(file.getLocationURI().toString()),true);

		EcoreUtil.resolveAll(system);
		resource.getContents().clear();
		resource.getContents().add(system);
		EcoreUtil.resolveAll(resource);
		
		// Options for serializarion. @see XtextResource.OPTION_xxx
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(XtextResource.OPTION_FORMAT, true); 
		
		try 
		{
			
			//FileOutputStream os = new FileOutputStream(filename);
			//system.eResource().save(os, map);
			system.eResource().save( map);
			// os.close();
//			java.lang.System.out.println("Done");
//			java.lang.System.out.println("You can see result in file: " + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Returns a GAL system from a filename .gal
	 */
	public static Properties fileToProperties(IFile file)
	{
		if(! file.getFileExtension().contains("prop"))
		{
			java.lang.System.err.println("Warning: filename '" + file + "' should end with .prop extension ");
		}

		XtextResourceSet resourceSet = getResourceSet(file); 
		
		Resource resource = resourceSet.getResource(URI.createURI(file.getLocationURI().toString()),true);
		
		Properties system = (Properties) resource.getContents().get(0);
		
		GALTypeDeclaration s = system.getSystem();
		if (s.eIsProxy()) {
			EcoreUtil.resolveAll(system);
		}
		
		return system ;
	}

	
}