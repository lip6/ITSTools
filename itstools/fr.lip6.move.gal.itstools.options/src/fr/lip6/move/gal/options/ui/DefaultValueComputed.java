package fr.lip6.move.gal.options.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;


public class DefaultValueComputed {
	
	private String extension;

	public DefaultValueComputed(String extension) {
		this.extension = extension;
	}

	public String computeConfigurationDefaultValue(ILaunchConfiguration configuration){
		String oriString = null;
		String defaultValue = null;
		try {
			oriString = configuration.getAttribute(CommonLaunchConstants.MODEL_FILE, "model.gal");
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
