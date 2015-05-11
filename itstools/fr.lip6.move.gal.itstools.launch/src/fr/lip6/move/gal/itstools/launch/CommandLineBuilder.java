package fr.lip6.move.gal.itstools.launch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.itstools.CommandLine;
import fr.lip6.move.gal.itstools.preference.GalPreferencesActivator;
import fr.lip6.move.gal.itstools.preference.PreferenceConstants;
import fr.lip6.move.serialization.SerializationUtil;

public class CommandLineBuilder {

	/** Build a command line from a LaunchConfiguration 
	 * @throws CoreException */
	public static CommandLine buildCommand(ILaunchConfiguration configuration) throws CoreException {
		CommandLine cl = new CommandLine();
		
		// Path to source model file
		String oriString = configuration.getAttribute(LaunchConstants.MODEL_FILE, "model.gal");		

		// Path to ITS-reach exe				
		String itsReachPath = configuration.getAttribute(PreferenceConstants.ITSREACH_EXE, GalPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.ITSREACH_EXE));

		cl.addArg(itsReachPath);

		// Produce a GAL file to give to its-tools
		IPath oriPath = Path.fromPortableString(oriString);

		// work folder
		File workingDirectory ;

		
		String cegarProp = configuration.getAttribute(LaunchConstants.CEGAR_PROP, "");
		if (! "".equals(cegarProp)) {
			workingDirectory = new File (oriPath.removeLastSegments(1).toString());
			// Input file options
			cl.addArg("-i") ;
			cl.addArg(oriPath.lastSegment());
			// Model type option
			cl.addArg("-t");
			cl.addArg("CGAL");

			String doTrace = configuration.getAttribute(LaunchConstants.PLAY_TRACE, "");
			if (! "".equals(doTrace)) {
				cl.addArg("-trace");
				cl.addArg(doTrace);
			} else {
				cl.addArg("-reachable");
				cl.addArg(cegarProp);
			}
			// limit verbosity
			cl.addArg("--quiet");
			
		} else {
			// parse it
			Specification spec = SerializationUtil.fileToGalSystem(oriString);

			// copy spec 
			Specification specNoProp = EcoreUtil.copy(spec);

			// clear properties : they will be fed separately
			specNoProp.getProperties().clear();
			// flatten it
			GALRewriter.flatten(specNoProp, true);

			
			workingDirectory = new File (oriPath.removeLastSegments(1).append("/work/").toString());
			
			try {
				workingDirectory.mkdir();
			} catch (SecurityException e) {
				e.printStackTrace();
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to create work folder :"+workingDirectory+". Please check location is open to write in.",e));
			}

			String tmpPath = workingDirectory.getPath() + "/" +oriPath.lastSegment();		
			File modelff = new File(tmpPath);

			try {
				SerializationUtil.systemToFile(specNoProp, tmpPath);
			} catch (IOException e) {
				e.printStackTrace();
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to create working file :"+tmpPath+". Please check location is open to write in.",e));
			}

			// Input file options
			cl.addArg("-i") ;
			cl.addArg(modelff.getName());

			// Model type option
			cl.addArg("-t");
			if (specNoProp.getMain() != null)
				cl.addArg("CGAL");
			else 
				cl.addArg("GAL");

			// test for and handle properties		
			if (! spec.getProperties().isEmpty()) {

				// We will put properties in a file
				String propPath = workingDirectory.getPath() + "/" + oriPath.removeFileExtension().lastSegment() + ".prop";

				try {
					// create file
					SerializationUtil.serializePropertiesForITSTools(modelff.getName(), spec.getProperties(), propPath);

					// property file arguments
					cl.addArg("-reachable-file");
					cl.addArg(new File(propPath).getName());

				} catch (IOException e) {
					e.printStackTrace();
					throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to create file to hold properties :"+tmpPath+". Please check location is open to write in.",e));
				}


			}
		}

		cl.setWorkingDir(workingDirectory);
		
		return cl;
	}
}
