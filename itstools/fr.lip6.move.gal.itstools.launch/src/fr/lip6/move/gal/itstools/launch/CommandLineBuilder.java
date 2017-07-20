package fr.lip6.move.gal.itstools.launch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.BoundsProp;
import fr.lip6.move.gal.CTLProp;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.LTLProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.itstools.CommandLine;
import fr.lip6.move.gal.itstools.launch.devtools.IOption;
import fr.lip6.move.gal.itstools.preference.GalPreferencesActivator;
import fr.lip6.move.gal.itstools.preference.PreferenceConstants;
import fr.lip6.move.serialization.BasicGalSerializer;
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

		List<Property> props = new ArrayList<Property>(spec.getProperties());
		spec.getProperties().clear();
		try {
			SerializationUtil.systemToFile(spec, tmpPath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to create working file :"+tmpPath+". Please check location is open to write in.",e));
		}
				
		boolean hasCTL = false;
		boolean hasLTL = false;

		for (Property p : props) {
			if (p.getBody() instanceof CTLProp) {
				hasCTL = true;
				break;
			} else if (p.getBody() instanceof LTLProp) {
				hasLTL = true;
				break;
			}
		}

		// Path to ITS-reach exe				
		String itsExePath;
		if (!hasCTL && !hasLTL) {
			itsExePath = configuration.getAttribute(PreferenceConstants.ITSREACH_EXE, GalPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.ITSREACH_EXE));
		} else if (hasCTL) {
			itsExePath = configuration.getAttribute(PreferenceConstants.ITSCTL_EXE, GalPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.ITSCTL_EXE));
		} else {
			itsExePath = configuration.getAttribute(PreferenceConstants.ITSLTL_EXE, GalPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.ITSLTL_EXE));
		}

		cl.addArg(itsExePath);

		// Input file options
		cl.addArg("-i") ;
		cl.addArg(modelff.getName());

		// Model type option
		cl.addArg("-t");
		if (spec.getMain() != null)
			cl.addArg("CGAL");
		else 
			cl.addArg("GAL");



		// test for and handle properties		
		if (! props.isEmpty()) {

			Set<String> boundvars = new LinkedHashSet<String>();
			List<Property> safeProps = new ArrayList<Property>(); 
			List<Property> ctlProps = new ArrayList<Property>(); 
			List<Property> ltlProps = new ArrayList<Property>(); 
			for (Property prop : props) {
				BasicGalSerializer bgs = new BasicGalSerializer(true);
				if (prop.getBody() instanceof BoundsProp) {
					BoundsProp bp = (BoundsProp) prop.getBody();
					Property target = prop;
					int toadd=0;
					for (TreeIterator<EObject> it = target.eAllContents() ; it.hasNext() ; ) {
						EObject obj = it.next();
						if (obj instanceof Constant) {
							Constant cte = (Constant) obj;
							toadd += cte.getValue();
						} else if (obj instanceof Reference) {
							it.prune();
						}
					}
					if (toadd != 0) {
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						bgs.serialize(bp.getTarget(), bos);
						String targetVar = bos.toString();

						Logger.getLogger("fr.lip6.move.gal").warning("For property "+target.getName() + " will report bounds of "+targetVar+ " without constants. Add "+toadd+" to the result in the trace.");;
					}
					safeProps.add(prop);
				} else if (prop.getBody() instanceof SafetyProp) {
					safeProps.add(prop);
				} else if (prop.getBody() instanceof CTLProp) {
					ctlProps.add(prop);
				} else if (prop.getBody() instanceof LTLProp) {
					ltlProps.add(prop);
				} 
			}
			if (! safeProps.isEmpty()) {
				// We will put properties in a file
				String propPath = workingDirectory.getPath() + "/" + oriPath.removeFileExtension().lastSegment() + ".prop";

				try {
					// create file
					SerializationUtil.serializePropertiesForITSTools(modelff.getName(), safeProps, propPath);

					// property file arguments
					cl.addArg("-reachable-file");
					cl.addArg(new File(propPath).getName());

				} catch (IOException e) {
					e.printStackTrace();
					throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to create file to hold properties :"+tmpPath+". Please check location is open to write in.",e));
				}
			}
			if (! ctlProps.isEmpty()) {
				// We will put properties in a file
				String propPath = workingDirectory.getPath() + "/" + oriPath.removeFileExtension().lastSegment() + ".ctl";

				try {
					// create file
					SerializationUtil.serializePropertiesForITSCTLTools(modelff.getName(), ctlProps, propPath);

					// property file arguments
					cl.addArg("-ctl");
					cl.addArg(new File(propPath).getName());

					cl.addArg("--fair-time");
				} catch (IOException e) {
					e.printStackTrace();
					throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to create file to hold properties :"+tmpPath+". Please check location is open to write in.",e));
				}
			}
			if (! ltlProps.isEmpty()) {
				// We will put properties in a file
				String propPath = workingDirectory.getPath() + "/" + oriPath.removeFileExtension().lastSegment() + ".ltl";

				try {
					// create file
					SerializationUtil.serializePropertiesForITSLTLTools(modelff.getName(), ltlProps, propPath);

					// property file arguments
					cl.addArg("-LTL");
					cl.addArg(new File(propPath).getName());
					cl.addArg("-c");
				} catch (IOException e) {
					e.printStackTrace();
					throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to create file to hold properties :"+tmpPath+". Please check location is open to write in.",e));
				}
			}

			// limit verbosity
			if (!hasLTL)
				cl.addArg("--quiet");

		}



		cl.setWorkingDir(workingDirectory);

		// add interpretation of options.		
		for (String flag : configuration.getAttribute(LaunchConstants.FLAGS, new ArrayList<>())) {
			cl.addArg(flag);
		}

		//System.out.println("\n"+cl);
		return cl;
	}
}
