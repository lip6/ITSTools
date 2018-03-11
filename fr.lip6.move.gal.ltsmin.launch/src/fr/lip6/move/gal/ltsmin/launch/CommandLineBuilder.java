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
import fr.lip6.move.gal.itstools.CommandLine;
import fr.lip6.move.gal.ltsmin.preference.LTSminPreferencesActivator;
import fr.lip6.move.gal.ltsmin.preference.PreferenceConstants;
import fr.lip6.move.serialization.SerializationUtil;

public class CommandLineBuilder {

	/** Build a command line from a LaunchConfiguration 
	 * @throws CoreException */
	public static CommandLine buildCommand(ILaunchConfiguration configuration) throws CoreException {
		CommandLine cl = new CommandLine();

		// Path to source model file
		String oriString = configuration.getAttribute(LaunchConstants.MODEL_FILE, "model.gal");		

		// Produce a GAL file to give to its-tools
		IPath oriPath = Path.fromPortableString(oriString);

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

		// flatten it
		GALRewriter.flatten(spec, true);
				
		String tmpPath = workingDirectory.getPath() + "/" +oriPath.lastSegment();		
		File modelff = new File(tmpPath);

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
		
		// Path to ITS-reach exe				
		String itsExePath=null;
		String tool = configuration.getAttribute(LaunchConstants.TOOL, "pins2lts-seq");
		if ("pins2lts-seq".equals(tool)) {
			itsExePath = configuration.getAttribute(PreferenceConstants.LTSMINSEQ_EXE, LTSminPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.LTSMINSEQ_EXE));
		} 
		else if ("pins2lts-mc".equals(tool)) { 
			itsExePath = configuration.getAttribute(PreferenceConstants.LTSMINMC_EXE, LTSminPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.LTSMINMC_EXE));
//		} else {
//			itsExePath = configuration.getAttribute(PreferenceConstants.ITSLTL_EXE, GalPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.ITSLTL_EXE));
//		}
		
		cl.addArg(itsExePath);

		cl.setWorkingDir(workingDirectory);

		// Input file options
		cl.addArg("-i") ;
		cl.addArg(modelff.getName());

		// Model type option
		cl.addArg("-t");
		if (spec.getMain() != null)
			cl.addArg("CGAL");
		else 
			cl.addArg("GAL");

		// add interpretation of options.		
		for (String flag : configuration.getAttribute(LaunchConstants.COMMON_FLAGS, new ArrayList<>())) {
			cl.addArg(flag);
		}		

		if ("pins2lts-seq".equals(tool)) {
			// build model and property files + args on commandline
//			
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
			for (String flag : configuration.getAttribute(LaunchConstants.SEQ_FLAGS, new ArrayList<>())) {
				cl.addArg(flag);
			}
		} else if ("pins2lts-sym".equals(tool)) {
			for (String flag : configuration.getAttribute(LaunchConstants.SEQ_FLAGS, new ArrayList<>())) {
				cl.addArg(flag);
			}
		}


		//System.out.println("\n"+cl);
		return cl;
	}
}
