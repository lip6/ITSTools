package fr.lip6.move.gal.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import fr.lip6.move.gal.VarDecl;

public class LogicQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {

	
	private String modelName = "none";

	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if (obj instanceof VarDecl) {
			return getConverter().toQualifiedName(((VarDecl)obj).getName());
		}
		return super.getFullyQualifiedName(obj);
	}
}
