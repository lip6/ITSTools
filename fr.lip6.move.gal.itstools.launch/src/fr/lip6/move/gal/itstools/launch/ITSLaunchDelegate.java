package fr.lip6.move.gal.itstools.launch;

import java.io.File;
import java.io.IOException;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
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
		
		// Path to ITS-reach exe				
		String itsReachPath = configuration.getAttribute(PreferenceConstants.ITSREACH_EXE, GalPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.ITSREACH_EXE));
		// Path to source model file
		String oriPath = configuration.getAttribute(LaunchConstants.MODEL_FILE, "model.gal");		

		
		// just a trace
		System.out.println("Yay, it runs ! " + oriPath );
		
		// parse it
		Specification spec = SerializationUtil.fileToGalSystem(oriPath);
		
		Specification specNoProp = EcoreUtil.copy(spec);
		specNoProp.getProperties().clear();
		
		String tmpPath = oriPath + "tmp.gal";
		try {
			SerializationUtil.systemToFile(specNoProp, tmpPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		File modelff = new File(tmpPath);
		
		// test for and handle properties		
		if (! spec.getProperties().isEmpty()) {
			for (Property prop : spec.getProperties()) {
				
				String formff =  ToStringUtils.getTextString(prop.getBody().getPredicate()) ;		
				
				
				
				
				String[] cmdLine = { itsReachPath ,
						"-i",
						modelff.getName(),
						"-t",
						"GAL",
						"-reachable",
						formff						
				};
				File workingDirectory = modelff.getParentFile();
				
				
				Process p = DebugPlugin.exec(cmdLine, workingDirectory.getAbsoluteFile() );
				
				IProcess proc = DebugPlugin.newProcess(launch, p, "ITS runner");
				
				System.out.println("done!");

				
			}
			
		} else {
			String[] cmdLine = { configuration.getAttribute(PreferenceConstants.ITSREACH_EXE, GalPreferencesActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.ITSREACH_EXE)),
					"-i",
					modelff.getName(),
					"-t",
					"GAL"
			};
			File workingDirectory = modelff.getParentFile();
			
			
			Process p = DebugPlugin.exec(cmdLine, workingDirectory.getAbsoluteFile() );
			
			IProcess proc = DebugPlugin.newProcess(launch, p, "ITS runner");
			
			System.out.println("done!");
			
			
		}
		
		
		
	}

}
