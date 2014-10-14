package fr.lip6.move.promela.togal.popup.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.serialization.SerializationUtil;

public class PopUpUtils {

	public static boolean isPromelaFile(IFile file) {
		return file.getFileExtension() != null
				&& file.getFileExtension().equals("pml");
	}

	public static String rawName(IFile file) {
		return file.getName().replace(".pml", "");
	}

	public static void saveGalFile(String name, Specification galSpec)
			throws IOException {
		String outPath = name + ".gal";

		FileOutputStream out = new FileOutputStream(new File(outPath));
		out.write(0);
		out.close();

		SerializationUtil.systemToFile(galSpec, outPath);
		java.lang.System.err.println("GAL model written to file: " + outPath);
	}

}
