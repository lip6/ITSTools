package fr.lip6.move.gal.ltsmin.launch;

import java.io.File;
import java.util.ArrayList;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.ltsmin.preference.LTSminPreferencesActivator;
import fr.lip6.move.gal.ltsmin.preference.PreferenceConstants;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.serialization.SerializationUtil;
import fr.lip6.move.gal.gal2pins.Gal2PinsTransformerNext;

public class CommandLineBuilder {

	/** Build a command line from a LaunchConfiguration 
	 * @throws CoreException */
	public static CommandLine buildCommand(ILaunchConfiguration configuration) throws CoreException {
		CommandLine cl = new CommandLine();

		// Path to source model file
		String oriString = configuration.getAttribute(LaunchConstants.MODEL_FILE, "model.gal");		

		// Produce a GAL file to give to ltsmin
		IPath oriPath = Path.fromPortableString(oriString);
		//System.out.println("PATH "+oriPath);
//		Gal2PinsTransformerNext model2pins = new Gal2PinsTransformerNext();

		// work folder
		File workingDirectory ;
		workingDirectory = new File (oriPath.removeLastSegments(1).append("/work/").toString());

		try {
			workingDirectory.mkdir();
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to create work folder :"+workingDirectory+". Please check location is open to write in.",e));
		}
		
		// parse it
		Specification spec = SerializationUtil.fileToGalSystem(oriString);
		
		//System.out.println("SPEC "+spec);
		// flatten it
		//GALRewriter.flatten(spec, true);
		
		//transform gal to pins
		
//		model2pins.transform(spec, workingDirectory.getAbsolutePath(), false);
		//System.out.println("MODEL "+model2pins);
		
		 
				
		//String tmpPath = workingDirectory.getPath() + "/" +oriPath.lastSegment();	
		String tmpPath = null; //try get a .so
		//File modelff = new File(tmpPath); 
		
		// export to LTSmin format

		// deal with properties as well
//		List<Property> props = new ArrayList<Property>(spec.getProperties());
//		spec.getProperties().clear();
//		try {
//			SerializationUtil.systemToFile(spec, tmpPath);
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to create working file :"+tmpPath+". Please check location is open to write in.",e));
//		}
		
		// Path to LTSmin exe				
		String ltsminExePath=null;
		String tool = configuration.getAttribute(LaunchConstants.TOOL, "pins2lts-seq");

		if ("pins2lts-seq".equals(tool)) {
			ltsminExePath = configuration.getAttribute(PreferenceConstants.LTSMINSEQ_EXE, LTSminPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.LTSMINSEQ_EXE));
			//System.out.println("IFFFFFFFFFFFFFF");
		} 
		else if ("pins2lts-mc".equals(tool)) { 
			ltsminExePath = configuration.getAttribute(PreferenceConstants.LTSMINMC_EXE, LTSminPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.LTSMINMC_EXE));
			//System.out.println("ELSEEEEEEEE IFFFFF");
		} else {
			ltsminExePath = configuration.getAttribute(PreferenceConstants.LTSMINSYM_EXE, LTSminPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.LTSMINSYM_EXE));
			//System.out.println("ELSEEEEEEEEEEe");
		}
		
		//System.out.println("!!! "+ltsminExePath);
		//System.out.println(" DIR "+workingDirectory);
		//System.out.println(" ABS PATH "+workingDirectory.getAbsolutePath());
		
		//cl.addArg(ltsminExePath);

		cl.setWorkingDir(workingDirectory);
		//System.out.println("AFTERRRRRRR");
		
		// Input file options
		//cl.addArg("-i") ;
		

		// Model type option
//		cl.addArg("-t");
//		if (spec.getMain() != null)
//			cl.addArg("CGAL");
//		else 
//			cl.addArg("GAL");
		//System.out.println("BEFORE FLAG");
		//System.out.println("CONFIG "+configuration);
		// add interpretation of options.		
		for (String flag : configuration.getAttribute(LaunchConstants.COMMON_FLAGS, new ArrayList<>())) {
			cl.addArg(flag);
			//System.out.println("FLAGGGG "+flag+"\n");
		}		

		if ("pins2lts-seq".equals(tool)) {
			// build model and property files + args on commandline
			
			//cl.addArg("gcc -c "+workingDirectory+" -I. -std=c99 -fPIC -O3 model.c"); 
			//cl.addArg(" && gcc -shared -o gal.so model.o");
			cl.addArg(tool);
			
			cl.addArg("model.so");//replace by an actual .so
			//cl.addArg(modelff.getName()+" ");
			
			cl.addArg("-r");
			//cl.addArg("d");
			cl.addArg("bg ");
			//cl.addArg("-rsc");
			//cl.addArg(",bk");
			
			
			
			//cl.addArg("--regroup-exit ");
			//cl.addArg(" --roworder");
			//cl.addArg("--graph-metrics ");
			
//			List<Property> safeProps = new ArrayList<Property>();
//			BasicGalSerializer bgs = new BasicGalSerializer(true);
//			for (Property prop : props) {				
//				if (prop.getBody() instanceof BoundsProp) {
//					BoundsProp bp = (BoundsProp) prop.getBody();
//					Property target = prop;
//					int toadd=0;
//					for (TreeIterator<EObject> it = target.eAllContents() ; it.hasNext() ; ) {
//						EObject obj = it.next();
//						if (obj instanceof Constant) {
//							Constant cte = (Constant) obj;
//							toadd += cte.getValue();
//						} else if (obj instanceof Reference) {
//							it.prune();
//						}
//					}
//					if (toadd != 0) {
//						ByteArrayOutputStream bos = new ByteArrayOutputStream();
//						bgs.serialize(bp.getTarget(), bos);
//						String targetVar = bos.toString();
//
//						Logger.getLogger("fr.lip6.move.gal").warning("For property "+target.getName() + " will report bounds of "+targetVar+ " without constants. Add "+toadd+" to the result in the trace.");;
//					}
//					safeProps.add(prop);
//				} else if (prop.getBody() instanceof SafetyProp) {
//					safeProps.add(prop);
//				}
//			}
//			if (! safeProps.isEmpty()) {
//				// We will put properties in a file
//				String propPath = workingDirectory.getPath() + "/" + oriPath.removeFileExtension().lastSegment() + ".prop";
//
//				try {
//					// create file
//					SerializationUtil.serializePropertiesForITSTools(modelff.getName(), safeProps, propPath);
//
//					// property file arguments
//					cl.addArg("-reachable-file");
//					cl.addArg(new File(propPath).getName());
//
//				} catch (IOException e) {
//					e.printStackTrace();
//					throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to create file to hold properties :"+tmpPath+". Please check location is open to write in.",e));
//				}
//			}
			for (String flag : configuration.getAttribute(LaunchConstants.SEQ_FLAGS, new ArrayList<>())) {
				cl.addArg(flag);
			}
		} else if ("pins2lts-mc".equals(tool)) { 
			for (String flag : configuration.getAttribute(LaunchConstants.MC_FLAGS, new ArrayList<>())) {
				cl.addArg(flag);
			}
		} else if ("pins2lts-sym".equals(tool)) {
			for (String flag : configuration.getAttribute(LaunchConstants.SYM_FLAGS, new ArrayList<>())) {
				cl.addArg(flag);
			}
		}


		//System.out.println("\n"+cl);
		return cl;
	}
}
