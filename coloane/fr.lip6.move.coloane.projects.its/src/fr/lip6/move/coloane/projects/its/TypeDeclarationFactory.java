package fr.lip6.move.coloane.projects.its;

import java.io.IOException;
import java.net.URI;

import fr.lip6.move.coloane.interfaces.model.IGraph;

public class TypeDeclarationFactory {

	
	private TypeDeclarationFactory() {}

	/**
	 * Factory operation to build concrete TypeDescriptions
	 * 
	 * @param name
	 *            name of the resulting type
	 * @param file
	 *            the base file containing a coloane model
	 * @param types
	 *            the types to load into
	 * @return an initialized type declaration instance
	 * @throws IOException
	 *             in case of XML read/file open problems
	 */
	public static ITypeDeclaration create(String name, URI file, TypeList types)
			throws IOException {		
		if (file.getPath().endsWith(".model")) {
			IGraph graph = TypeDeclaration.loadGraph(file);
			String form = graph.getFormalism().getName();
			if (form.equals("ITSComposite") || form.equals("Scalar Set Composite")|| form.equals("Circular Set Composite")) {
				return new CompositeTypeDeclaration(name, file, graph, types);
			} else {
				return new TypeDeclaration(name, file, graph, types);
			}
		} else if (file.getPath().endsWith(".gal")) {
			return new GALTypeDeclaration(name, file, types);
		} else {
			throw new IOException("Unknown model extension "+ file.getPath());
		}
	}
	
	
}
