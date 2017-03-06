package fr.lip6.move.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import fr.lip6.move.gal.InstanceDeclaration;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.VarDecl;

public class ITSQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {

	
	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if (obj instanceof Label) {
			return getConverter().toQualifiedName(((Label) obj).getName());
		}
		if (obj instanceof VarDecl) {
			return getConverter().toQualifiedName(((VarDecl)obj).getName());
		}
		if (obj instanceof Transition) {
			return getConverter().toQualifiedName(((Transition)obj).getName());
		}
		if (obj instanceof InstanceDeclaration) {
			return getConverter().toQualifiedName(((InstanceDeclaration)obj).getName());			
		}
		return super.getFullyQualifiedName(obj);
	}
}
