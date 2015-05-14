package fr.lip6.move.serialization;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Guice;
import com.google.inject.Injector;

import fr.lip6.move.GalRuntimeModule;
import fr.lip6.move.GalStandaloneSetup;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;


/**
 * Utility class for serialization of gal system
 */
public class SerializationUtil  {
	
	private static boolean isStandalone = false;
	
	private static Logger getLog() { return Logger.getLogger("fr.lip6.move.gal"); }
	
	public static void setStandalone(boolean isStandalone) {
		SerializationUtil.isStandalone = isStandalone;
	}
	
	/**
	 * Create a new file and return a Resource from this file.
	 */
	private static Resource createResource(String filename)
	{
		// GalStandaloneSetup.doSetup() ;
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
		if (isStandalone) {
			GalStandaloneSetup gs = new GalStandaloneSetup();
			return gs.createInjectorAndDoEMFRegistration();
		} else { 
			return Guice.createInjector(new GalRuntimeModule());
		}
	}

	public static void systemToFile(TypeDeclaration system, String filename) throws IOException
	{
		Specification spec = GalFactory.eINSTANCE.createSpecification();
		spec.getTypes().add(system);
		systemToFile(spec, filename);
	}
	
	/**
	 * This method serialize a Gal System in the file {@code filename} 
	 * @param system The root of Gal system
	 * @param filename The output filename.
	 */
	@SuppressWarnings("deprecation")
	public static void systemToFile(Specification system, final String filename) throws IOException
	{
		long debut = System.currentTimeMillis();

		if(! filename.endsWith(".gal"))
		{
			getLog().warning("Warning: filename '" + filename + "' should end with .gal extension ");
		}
		
		
		// System.out.print("Serializing...");
		// Creating new resource and binding it to system
//		Resource resource = createResource(filename);
//		resource.getContents().add(system);
//		
//		// Options for serializarion. @see XtextResource.OPTION_xxx
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put(XtextResource.OPTION_FORMAT, true); 
		
		try 
		{
			
			FileOutputStream os = new FileOutputStream(filename);
			BasicGalSerializer bser = new BasicGalSerializer();
			bser.serialize(system, new BufferedOutputStream(os));
//			system.eResource().save(os, map);
			os.close();
			
//			java.lang.System.out.println("Done");
//			java.lang.System.out.println("You can see result in file: " + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		getLog().info("Time to serialize gal into " + filename + " : " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$
		
		
		// force refresh
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				try{ 	
					for (IFile file  : ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new File(filename).toURI())) {
						file.refreshLocal(IResource.DEPTH_ZERO, null);
					}
				} catch (Exception e) {
					getLog().warning("Error when refreshing explorer view, please refresh manually to ensure new GAL files are visible in eclipse.");
					e.printStackTrace();
				} 
			}
		});
	}
	
	
	/**
	 * Returns a GAL system from a filename .gal
	 */
	public static Specification fileToGalSystem(String filename)
	{
		if(! filename.endsWith(".gal"))
		{
			getLog().warning("Warning: filename '" + filename + "' should end with .gal extension ");
		}
		
		Resource res = loadResources(filename); 
		Specification system = (Specification) res.getContents().get(0);
		
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
	
	public static void serializePropertiesForITSTools(String outpath,
			List<Property> properties, String propPath)
			throws IOException {
		long debut = System.currentTimeMillis();
		OutputStream out = new FileOutputStream(propPath);
		// first line is removed anyway : reference source model
		out.write(("import  \"" + outpath + "\";\n").getBytes());

		// STRICT mode
		BasicGalSerializer bsg = new BasicGalSerializer(true);
		bsg.setStream(out);
		// Add one line per property
		for (Property prop : properties) {
			bsg.doSwitch(prop);
//			out.write(ToStringUtils.getTextString(prop) + "\n") ;
		}
		bsg.close();
		// 
		out.flush();
		out.close();

		getLog().info("Time to serialize properties into " + propPath + " : " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$
	
	}

	public static String getText (EObject modelElement, boolean isStrict) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		BasicGalSerializer bgs = new BasicGalSerializer(isStrict);
		bgs.serialize(modelElement, bos);
		return bos.toString();
	}
	
}