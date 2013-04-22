package fr.lip6.move.gal.scoping;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.xbase.jvmmodel.JvmGlobalScopeProvider;

public class GalLogicGlobalScopeProvider extends JvmGlobalScopeProvider {

	@Override
	public IScope getScope(Resource resource, EReference reference) {
		return super.getScope(resource, reference);
	}
}
