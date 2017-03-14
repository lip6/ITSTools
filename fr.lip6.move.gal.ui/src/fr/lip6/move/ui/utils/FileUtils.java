package fr.lip6.move.ui.utils;

import java.io.File;
import java.util.logging.Logger;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.widgets.Display;

public class FileUtils {
	private static boolean doDisplay = true;


	public static void refreshDisplay (String filename) {
		if (doDisplay) {
			try {
				// force refresh
				Display.getCurrent().asyncExec(new Runnable() {
					@Override
					public void run() {
						try{ 	
							for (IFile file  : ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new File(filename).toURI())) {
								file.refreshLocal(IResource.DEPTH_ZERO, null);
							}
						} catch (Exception e) {
							Logger.getLogger("fr.lip6.move.gal").warning("Error when refreshing explorer view, please refresh manually to ensure new GAL files are visible in eclipse.");
							e.printStackTrace();
						} 
					}
				});
			} catch (Exception e) {
				// getDefault can raise exceptions if no gtk is open
				Logger.getLogger("fr.lip6.move.gal").info("No display to refresh.");
				doDisplay = false;
			}
		}
	}
}