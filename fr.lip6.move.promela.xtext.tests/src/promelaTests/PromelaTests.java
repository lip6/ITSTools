package promelaTests;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.eclipse.xtext.junit4.AbstractXtextTests;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import fr.lip6.move.promela.PromelaInjectorProvider;
import fr.lip6.move.promela.promela.PromelaSpecification;


/* Fichier de Test inspiré de son équivalent GAl */


@RunWith(XtextRunner.class)
@InjectWith(PromelaInjectorProvider.class)
public class PromelaTests extends AbstractXtextTests {

	@Inject
	ParseHelper<PromelaSpecification> promelaParser;

	private static int index = -1;

	static {
		// java.lang.System.out.println(java.lang.System.getProperty("user.dir"));
		// TODO Modifier, adapter
		new ReadPromelaFile(new File("./src/promelaSamples"), ".*\\.pml");

	}

	File files[] = ReadPromelaFile.getFiles();

	private static String readFileAsString(File filePath) throws IOException {
		byte[] buffer = new byte[(int) filePath.length()];
		BufferedInputStream f = null;
		try {
			f = new BufferedInputStream(new FileInputStream(filePath));
			f.read(buffer);
		} finally {
			if (f != null)
				try {
					f.close();
				} catch (IOException ignored) {
				}
		}
		return new String(buffer);
	}

	@Before
	public void incrIndex() {
		index++;
	}

	/*
	 * Exemple de Test Spécifique, Gal Mode les fichier étaient reprérés par
	 * leur position dans leur dosier. préférer un système plus flexible se basant sr le nom du fichier! (Path?)
	 * 
	 * @Test public void bakeryGal() throws Exception{
	 * 
	 * System bakery = galParser.parse(readFileAsString(files[index]));
	 * assertEquals(2, bakery.getArrays().size()); assertEquals(9,
	 * bakery.getVariables().size()); assertEquals(24,
	 * bakery.getTransitions().size()); assertEquals(0,
	 * bakery.getLists().size());
	 * 
	 * assertEquals("bakery.4.dve", bakery.getName());
	 * 
	 * for(int i=0; i < 24; i++) assertEquals("t"+i,
	 * bakery.getTransitions().get(i).getName());
	 * 
	 * assertTrue(3 == bakery.getTransitions().get(9).getActions().size()); }
	 */

}
