package fr.lip6.move.gal.flatten.popup.actions;

import java.io.File;
import java.io.FileOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.serialization.SerializationUtil;


/**
 * A gal action operates over a set of GAL files, transforming them one by one (in order of increasing file size).
 * @author Yann
 *
 */
public abstract class GalAction extends FileAction {

	private String workFolder;
	private String modelName;

	String getWorkFolder() {
		return workFolder;
	}
	
	public String getModelName() {
		return modelName;
	}
	
	@Override
	public void workWithFile(IFile file, StringBuilder log) {

		Specification s = EcoreUtil.copy(SerializationUtil.fileToGalSystem(file.getRawLocationURI().getPath()));

		try {
			String path = file.getRawLocationURI().getPath();
			workFolder = file.getParent().getLocation().toPortableString();
			modelName = file.getName().replace(".gal", "");
			workWithSystem(s);
			if (path.endsWith(getTargetExtension())) {
				path = path.substring(0,path.length()-getTargetExtension().length());
			}
			getLog().info("Running " + getServiceName() + " on target :" + path);
			String outpath =  path+ getAdditionalExtension() + getTargetExtension();
			

			FileOutputStream out = new FileOutputStream(new File(outpath));
			out.write(0);
			out.close();
			SerializationUtil.systemToFile(s,outpath);
			getLog().info("GAL model written to file : " +outpath);
			log.append("  " + outpath);
		} catch (Exception e) {
			warn(e);
			e.printStackTrace();
			return;

		}
	}
	
	@Override
	protected String getTargetExtension() {
		return ".gal";
	}

}


