package fr.lip6.move.coloane.emf.xtext.model.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import fr.lip6.move.coloane.emf.Model.DocumentRoot;
import fr.lip6.move.coloane.emf.Model.Tattribute;
import fr.lip6.move.coloane.emf.Model.Tattributes;
import fr.lip6.move.coloane.emf.Model.Tmodel;

public class ITSQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {

	
	private String modelName = "none";

	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if (obj instanceof Tmodel) {
			Tmodel model = (Tmodel) obj;
			obj = model.eContainer();
			if (obj instanceof DocumentRoot) {
				DocumentRoot root = (DocumentRoot) obj;
				String name = root.eResource().getURI().trimFileExtension().lastSegment();
				modelName  = name;
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
							if (! "PUBLIC".equals(iatt.getValue())) {
								return null;
							} else {
								break;
							}
						}
					}
				}
				return getConverter().toQualifiedName(modelName+ "." + att.getValue());
			}
		}
		return null;
//		QualifiedName toret = super.getFullyQualifiedName(obj);
//		return toret;
	}
}
