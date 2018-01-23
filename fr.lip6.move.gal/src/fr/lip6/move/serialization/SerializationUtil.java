package fr.lip6.move.serialization;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Guice;
import com.google.inject.Injector;

import fr.lip6.move.GalRuntimeModule;
import fr.lip6.move.GalStandaloneSetup;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
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
		OutputStream out = new BufferedOutputStream(new FileOutputStream(propPath));
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

	public static void serializePropertiesForITSCTLTools(String outpath, List<Property> ctlProps, String propPath) 
			throws IOException {
		long debut = System.currentTimeMillis();
		OutputStream out = new FileOutputStream(propPath);
		// first line is removed anyway : reference source model
		out.write(("# import  \"" + outpath + "\";\n").getBytes());

		// STRICT mode
		// we need to identify atoms
		Set<EObject> atoms = collectAtoms(ctlProps);
				
		// STRICT mode
		BasicGalSerializer bsg = new AtomSerializer(atoms); 
		bsg.setCTL(true);
		bsg.setStream(out);
		// Add one line per property
		for (Property prop : ctlProps) {
			bsg.doSwitch(prop);
			//		out.write(ToStringUtils.getTextString(prop) + "\n") ;
		}
		bsg.close();
		// 
		out.flush();
		out.close();

		getLog().info("Time to serialize properties into " + propPath + " : " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$

	}
	
	private static boolean isPureBool(EObject obj) {
		if (obj instanceof And || obj instanceof Or || obj instanceof Not ) {
			for (EObject child:  obj.eContents()) {
				if (! isPureBool(child)) {
					return false;
				}
			}
			return true;
		} else if (obj instanceof Comparison) {
			return true;
		}				
		return false;
	}

	
	public static void serializePropertiesForITSLTLTools(String outpath, List<Property> props, String propPath) 
			throws IOException {
		long debut = System.currentTimeMillis();
		OutputStream out = new FileOutputStream(propPath);
		// first line is removed anyway : reference source model
		out.write(("# import  \"" + outpath + "\";\n").getBytes());

		// we need to identify atoms
		Set<EObject> atoms = collectAtoms(props);
		
		// STRICT mode
		BasicGalSerializer bsg = new AtomSerializer(atoms); 
		bsg.setLTL(true);
		bsg.setStream(out);
		// Add one line per property
		for (Property prop : props) {
			bsg.doSwitch(prop);
			//		out.write(ToStringUtils.getTextString(prop) + "\n") ;
		}
		bsg.close();
		// 
		out.flush();
		out.close();

		getLog().info("Time to serialize properties into " + propPath + " : " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$

	}

	private static Set<EObject> collectAtoms(List<Property> props) {
		Set<EObject> atoms = new HashSet<>();
		for (Property p : props) {
			for (TreeIterator<EObject> it = p.getBody().eAllContents() ; it.hasNext() ;  ) {
				EObject obj = it.next();
				if (isPureBool(obj)) {
					// helps to recognize that  !AP is the negation of AP
					// Can reduce number of AP as well as help simplifications
					if (obj instanceof Not) {
						obj = ((Not) obj).getValue();
					}					
					atoms.add(obj);
					it.prune();
				}
			}
		}
		return atoms;
	}
}


class AtomSerializer extends BasicGalSerializer {
	
	private Set<EObject> atoms;

	public AtomSerializer(Set<EObject> atoms) {
		super(true);
		this.atoms = atoms;
	}
	@Override
	public Boolean doSwitch(EObject eObject) {
		if (atoms.contains(eObject)) {
			pw.print("\"(");
			Boolean ret = super.doSwitch(eObject);
			pw.print(")\"");
			return ret;
		} else {
			return super.doSwitch(eObject);
		}
	}

	@Override
	public Boolean caseComparison(Comparison comp) {
		if (comp.getLeft() instanceof Constant) {
			doSwitch(comp.getRight());
			pw.print(reverse(comp.getOperator()));
			doSwitch(comp.getLeft());
		} else {
			doSwitch(comp.getLeft());
			pw.print(comp.getOperator().getLiteral());
			doSwitch(comp.getRight());
		}
		return true;
	}
}






