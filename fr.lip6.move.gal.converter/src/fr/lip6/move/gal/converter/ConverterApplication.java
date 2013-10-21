package fr.lip6.move.gal.converter;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import fr.lip6.move.coloane.core.model.factory.FormalismManager;
import fr.lip6.move.coloane.core.ui.files.ModelLoader;
import fr.lip6.move.coloane.extension.exportGAL.exportToGAL.ExportToGAL;
import fr.lip6.move.coloane.extensions.importExportTINA.importFromTINA.ImportFromImpl;
import fr.lip6.move.coloane.interfaces.exceptions.ExtensionException;
import fr.lip6.move.coloane.interfaces.formalism.IFormalism;
import fr.lip6.move.coloane.interfaces.model.IGraph;

public class ConverterApplication implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		
		String[] vargs = (String[])context.getArguments().get("application.args");
		Map<String,String> args = new HashMap<String, String>();
		
		for (int i= 0; i < vargs.length -1; i+=2) {
			args.put(vargs[i], vargs[i+1]);
		}
		
		String sourcePath = args.get("-sourcePath");
		String sourceType = args.get("-sourceType");
		IGraph gsource = null;
		if (sourceType.equals("TINA_NET")) {
			gsource = loadTina(sourcePath);
		}
		
		
//		Specification s = GalFactory.eINSTANCE.createSpecification();
//		GALTypeDeclaration gal = GalFactory.eINSTANCE.createGALTypeDeclaration();
//		gal.setName("yo");
//		s.getTypes().add(gal);
		String targetPath = args.get("-targetPath");
		
		
		System.out.println("Building target file :" + targetPath);
		ExportToGAL exp = new ExportToGAL();
		exp.export(gsource, targetPath, new NullProgressMonitor());
		
		return IApplication.EXIT_OK;
	}

	

	static IGraph loadTina(String path) throws IOException, ExtensionException {
		ImportFromImpl imp = new ImportFromImpl();
		IFormalism formalism = FormalismManager.getInstance().getFormalismByName("Time Petri Net");		
		return imp.importFrom(path, formalism , new NullProgressMonitor());
	}
	
	/**
	 * Load a IGraph from a file
	 * 
	 * @param typePath
	 *            the file to load from
	 * @return the coloane graph model
	 * @throws IOException
	 *             if any problems during parse or file load.
	 */
	static IGraph loadGraph(URI typePath) throws IOException {
		// Construction d'un modele en memoire a partir de se representation en
		// XML
		IGraph graph = ModelLoader.loadGraphFromXML(typePath);

		// Si le chargement a échoué, on annule l'ouverture de l'éditeur
		if (graph == null) {
			throw new IOException("Load from XML file failed !");
		}
		return graph;
	}
	
	@Override
	public void stop() {
		

	}

}
