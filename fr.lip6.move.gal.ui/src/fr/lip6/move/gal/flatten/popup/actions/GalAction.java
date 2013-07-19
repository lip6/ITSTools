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

	@Override
	protected void workWithFile(IFile file, StringBuilder log) {

		Specification s = EcoreUtil.copy(SerializationUtil.fileToGalSystem(file.getRawLocationURI().getPath()));

		try {

			s = workWithSystem(s);

			String path = file.getRawLocationURI().getPath().split(getTargetExtension())[0];
			String outpath =  path+ getAdditionalExtension() + getTargetExtension();

			FileOutputStream out = new FileOutputStream(new File(outpath));
			out.write(0);
			out.close();
			SerializationUtil.systemToFile(s,outpath);
			java.lang.System.err.println("GAL model written to file : " +outpath);
			log.append("  " + outpath);
		} catch (Exception e) {
			warn(e);
			return;

		}
	}
	
	@Override
	protected String getTargetExtension() {
		return ".gal";
	}

}


