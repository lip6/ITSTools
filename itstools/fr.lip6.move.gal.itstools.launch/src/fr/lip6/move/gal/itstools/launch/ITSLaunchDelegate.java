package fr.lip6.move.gal.itstools.launch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate2;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.itstools.preference.GalPreferencesActivator;
import fr.lip6.move.gal.itstools.preference.PreferenceConstants;
import fr.lip6.move.serialization.SerializationUtil;
import fr.lip6.move.ui.labeling.ToStringUtils;

public class ITSLaunchDelegate extends LaunchConfigurationDelegate implements
ILaunchConfigurationDelegate2 {


	@Override
	public void launch(ILaunchConfiguration configuration, String mode,	ILaunch launch, IProgressMonitor monitor) throws CoreException {


		List<String> args = new ArrayList<String>();

		// Path to ITS-reach exe				
		String itsReachPath = configuration.getAttribute(PreferenceConstants.ITSREACH_EXE, GalPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.ITSREACH_EXE));

		args.add(itsReachPath);

		// Path to source model file
		String oriPath = configuration.getAttribute(LaunchConstants.MODEL_FILE, "model.gal");		

		// parse it
		Specification spec = SerializationUtil.fileToGalSystem(oriPath);

		// copy spec and clear properties : they will be fed separately
		Specification specNoProp = EcoreUtil.copy(spec);
		specNoProp.getProperties().clear();

		// Produce a GAL file to give to its-tools
		String tmpPath = oriPath + "tmp.gal";
		File modelff = new File(tmpPath);
		try {
			SerializationUtil.systemToFile(specNoProp, tmpPath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to create working file :"+tmpPath+". Please check location is open to write in.",e));
		}

		// Input file options
		args.add("-i") ;
		args.add(modelff.getName());

		// Model type option
		args.add("-t");
		args.add("CGAL");


		// test for and handle properties		
		if (! spec.getProperties().isEmpty()) {

			// We will put properties in a file
			String propPath = oriPath + ".prop";

			try {
				// create file
				File propFile = new File(propPath);
				PrintWriter out = new PrintWriter(propFile);

				// first line is removed anyway : reference source model
				out.write("import  \"" + modelff.getName() + "\";\n");

				// Add one line per property
				for (Property prop : spec.getProperties()) {
					out.write(ToStringUtils.getTextString(prop) + "\n") ;
				}
				// 
				out.flush();
				out.close();

				// property file arguments
				args.add("-reachable-file");
				args.add(propFile.getName());

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to create file to hold properties :"+tmpPath+". Please check location is open to write in.",e));
			}


		} 


		// Bring it all together for the invocation
		// full argument list
		String [] cmdLine = args.toArray(new String[args.size()]);		
		// work folder
		File workingDirectory = modelff.getParentFile();

		// Define the process
		Process p = DebugPlugin.exec(cmdLine, workingDirectory.getAbsoluteFile() );

		// Let the DebugPlugin manage running the process
		IProcess proc = DebugPlugin.newProcess(launch, p, "ITS runner");

		// System.out.println("done!");





	}

}
