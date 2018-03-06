package fr.lip6.move.gal.ltsmin.launch.devtools;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;

import fr.lip6.move.gal.ltsmin.launch.LaunchConstants;

public class DefaultValueComputed {
	
	private String extension;

	public DefaultValueComputed(String extension) {
		this.extension = extension;
	}

	public String computeConfigurationDefaultValue(ILaunchConfiguration configuration){
		String oriString = null;
		String defaultValue = null;
		try {
			oriString = configuration.getAttribute(LaunchConstants.MODEL_FILE, "model.gal");
		} catch (CoreException e) {
			e.printStackTrace();
		}
		IPath oriPath;
		if (oriString != null){
			oriPath= Path.fromPortableString(oriString);
			defaultValue = oriPath.removeFileExtension().lastSegment() + extension;
		}
		return defaultValue;
	}

}
