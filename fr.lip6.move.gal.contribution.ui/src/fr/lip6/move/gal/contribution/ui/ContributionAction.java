package fr.lip6.move.gal.contribution.ui;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.flatten.popup.actions.FileAction;
import fr.lip6.move.serialization.SerializationUtil;
import fr.lip6.move.ui.utils.FileUtils;


/**
 * A gal action operates over a set of GAL files, transforming them one by one (in order of increasing file size).
 * @author Yann
 *
 */
public abstract class ContributionAction extends FileAction {

	private String workFolder;
	private String modelName;
	private IProject project;
	private IFile sourceFile;

	public String getWorkFolder() {
		return workFolder;
	}
	
	public String getModelName() {
		return modelName;
	}
	
	public IProject getProject() {
		return project;
	}
	
	public IFile getSourceFile() {
		return sourceFile;
	}
	
	@Override
	public void workWithFile(IFile file, StringBuilder log) {
		project = file.getProject();
		sourceFile = file;

		Specification s = SerializationUtil.fileToGalSystem(file.getRawLocationURI().getPath());
		System.out.println("coucou");
		try {
			String path = file.getRawLocationURI().getPath();
			workFolder = file.getParent().getLocation().toPortableString();
			modelName = file.getName().replace(".gal", "");
			
			getLog().info("Running " + getServiceName() + " on target :" + path);
			String outpath =  path+ ".ord";
			
			workOnSpec(s,outpath);
			
			FileUtils.refreshDisplay(outpath);
			getLog().info("GAL model written to file : " +outpath);
			log.append("  " + outpath);
		} catch (Exception e) {
			warn(e);
			e.printStackTrace();
			return;

		}
	}
	
	public abstract void workOnSpec(Specification s, String outpath) throws IOException ;

	@Override
	protected String getTargetExtension() {
		return ".gal";
	}

}


