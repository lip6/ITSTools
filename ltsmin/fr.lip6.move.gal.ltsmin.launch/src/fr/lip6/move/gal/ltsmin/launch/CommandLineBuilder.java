package fr.lip6.move.gal.ltsmin.launch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.ltsmin.preference.LTSminPreferencesActivator;
import fr.lip6.move.gal.ltsmin.preference.PreferenceConstants;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.serialization.SerializationUtil;
import fr.lip6.move.gal.gal2pins.Gal2PinsTransformerNext;

public class CommandLineBuilder {

	/** Build a command line from a LaunchConfiguration 
	 * @throws CoreException */
	public static List<CommandLine> buildCommand(ILaunchConfiguration configuration) throws CoreException {
		CommandLine cl = new CommandLine();

		// Path to source model file
		String oriString = configuration.getAttribute(LaunchConstants.MODEL_FILE, "model.gal");		
		
		// Produce a GAL file to give to ltsmin
		IPath oriPath = Path.fromPortableString(oriString);

		// work folder
		File workingDirectory ;
		workingDirectory = new File (oriPath.removeLastSegments(1).append("/work/").toString());
		String workDirPath ; 
		try {
			workingDirectory.mkdir();
			workDirPath = workingDirectory.getCanonicalPath();
			
		} catch (SecurityException|IOException e) {
			e.printStackTrace();
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to create work folder :"+workingDirectory+". Please check location is open to write in.",e));
		}
		
		// parse it
		Specification spec = SerializationUtil.fileToGalSystem(oriString);
		
		//System.out.println("SPEC "+spec);
		// flatten it
//		GALRewriter.flatten(spec, true);

//		String tmpPath = workingDirectory.getPath() + "/" +oriPath.lastSegment();	
//		File modelff = new File(tmpPath); 
		
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
		} 
		else if ("pins2lts-mc".equals(tool)) { 
			ltsminExePath = configuration.getAttribute(PreferenceConstants.LTSMINMC_EXE, LTSminPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.LTSMINMC_EXE));			
		} else {
			ltsminExePath = configuration.getAttribute(PreferenceConstants.LTSMINSYM_EXE, LTSminPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.LTSMINSYM_EXE));
		}
		
		cl.setWorkingDir(workingDirectory);

		
		Gal2PinsTransformerNext g2p = new Gal2PinsTransformerNext();
		g2p.transform(spec, workDirPath, false);

		/*
		 * Ceci etait pour itstools
		 * 
		 */
//		// Input file options
//		cl.addArg("-i") ;
		//cl.addArg(modelff.getName());

		// Model type option
//		cl.addArg("-t");
//		if (spec.getMain() != null)
//			cl.addArg("CGAL");
//		else 
//			cl.addArg("GAL");


		// add interpretation of options.		
		for (String flag : configuration.getAttribute(LaunchConstants.COMMON_FLAGS, new ArrayList<>())) {
			cl.addArg(flag);			
		}		
		List<CommandLine> toret = new ArrayList<>();
		
		if ("pins2lts-seq".equals(tool)) {
			// build model and property files + args on commandline
			
			String ltsminpath = new File(ltsminExePath).getParent(); 
			toret.add(compilePINS(workDirPath, ltsminpath) );
			toret.add( linkPINS(workDirPath));
			
			cl.addArg(ltsminExePath);
			cl.addArg("-d");
			cl.addArg("gal.so");
			
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
			// I think github is break or something so this is just for test.
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


		System.out.println("\n"+cl);
		
		
		toret.add(cl);
		return toret;
	}
	
	private static CommandLine compilePINS(String workFolder, String ltsminpath)  {
		// compile
		long time = System.currentTimeMillis();
		CommandLine clgcc = new CommandLine();
		clgcc.setWorkingDir(new File(workFolder));
		clgcc.addArg("gcc");
		clgcc.addArg("-c");
		clgcc.addArg("-I" + ltsminpath + "/include");
		clgcc.addArg("-I.");
		clgcc.addArg("-std=c99");
		clgcc.addArg("-fPIC");
		clgcc.addArg("-O3");
		clgcc.addArg("model.c");

		System.out.println("Built compilation step : " + clgcc);
		return clgcc;
	}

	private static CommandLine linkPINS(String workFolder)  {
		// link
		long time = System.currentTimeMillis();
		CommandLine clgcc = new CommandLine();
		clgcc.setWorkingDir(new File(workFolder));
		clgcc.addArg("gcc");
		clgcc.addArg("-shared");
		clgcc.addArg("-o");
		clgcc.addArg("gal.so");
		clgcc.addArg("model.o");
		System.out.println("Built link step : " + clgcc);
		return clgcc;
	}
	
}
