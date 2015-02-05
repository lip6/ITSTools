package fr.lip6.move.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import fr.lip6.move.coloane.emf.Model.DocumentRoot;
import fr.lip6.move.coloane.emf.Model.Tattribute;
import fr.lip6.move.coloane.emf.Model.Tattributes;
import fr.lip6.move.coloane.emf.Model.Tmodel;
import fr.lip6.move.gal.InstanceDeclaration;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.VarDecl;

public class ITSQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {

	
	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if (obj instanceof Tmodel) {
			Tmodel model = (Tmodel) obj;
			obj = model.eContainer();
			if (obj instanceof DocumentRoot) {
				DocumentRoot root = (DocumentRoot) obj;
				String name = root.eResource().getURI().trimFileExtension().lastSegment();
				return getConverter().toQualifiedName(name);
			}
		}
		if (obj instanceof Tattribute) {
			Tattribute att = (Tattribute) obj;
			if (att.getName().equals("label")) {
				EObject oatts = att.eContainer();
				if (oatts instanceof Tattributes) {
					Tattributes atts = (Tattributes) oatts;
					for (Tattribute iatt :atts.getAttribute()) {
						if (iatt.getName().equals("visibility")) {
							if (! "PUBLIC".equalsIgnoreCase(iatt.getValue())) {
								return null;
							} else {
								break;
							}
						}
					}
				}
				return getConverter().toQualifiedName(att.getValue());
			}
			else {
				return null;
			}
		}
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
