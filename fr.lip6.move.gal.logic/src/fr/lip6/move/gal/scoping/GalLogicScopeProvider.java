package fr.lip6.move.gal.scoping;


import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.xbase.scoping.XbaseScopeProvider;

import fr.lip6.move.gal.System;
import fr.lip6.move.gal.logic.Properties;

public class GalLogicScopeProvider extends XbaseScopeProvider {
	public static IScope sgetScope (EObject context, EReference reference) {
		String clazz = reference.getEContainingClass().getName() ;
		String prop = reference.getName();
		System s = getSystem(context);
		if (s==null) {
			return null;
		}
		if ("VariableRef".equals(clazz) && "referencedVar".equals(prop)) {
			return Scopes.scopeFor(s.getVariables());
		} else if ("ArrayVarAccess".equals(clazz) && "prefix".equals(prop)) {
			return Scopes.scopeFor(s.getArrays());
		} else if ("Properties".equals(clazz)&& "system".equals(prop) && ! s.eIsProxy()){
			return Scopes.scopeFor(Collections.singletonList(s));
		}
		return null;
	}
	
	public IScope getScope(EObject context, EReference reference) {
		IScope res = sgetScope(context, reference);
		if (res == null) {
			return super.getScope(context, reference);
		} else {
			return res;
		}
	}
	
	private static System getSystem(EObject call) {
		EObject parent = call;
		while (parent != null && !(parent instanceof Properties)) {
			parent = parent.eContainer();
		}
		if (parent != null)
			return ((Properties) parent).getSystem();
		return null;
	}

}
