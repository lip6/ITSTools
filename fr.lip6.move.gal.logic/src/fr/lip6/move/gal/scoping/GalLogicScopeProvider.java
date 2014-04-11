package fr.lip6.move.gal.scoping;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.xbase.scoping.XbaseScopeProvider;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.logic.Properties;

public class GalLogicScopeProvider extends XbaseScopeProvider {
	public static IScope sgetScope (EObject context, EReference reference) {
		String clazz = reference.getEContainingClass().getName() ;
		String prop = reference.getName();
		GALTypeDeclaration s = getSystem(context);
		if (s==null) {
			return null;
		}
		if ("Properties".equals(clazz) && "system".equals(prop)) {
			List<System> visible = new ArrayList<System>();
			//			
			//			
			//			if (context instanceof Properties) {
			//				
			//				Properties props = (Properties) context;				
			//				if (props.getSystem() != null && ! props.getSystem().eIsProxy()) {
			//					return Scopes.scopeFor(Collections.singletonList(props.getSystem()));
			//				}
			//				
			//			}

		} else if ("VariableRef".equals(clazz) && "referencedVar".equals(prop)) {
			return Scopes.scopeFor(s.getVariables());
		} else if ("ArrayVarAccess".equals(clazz) && "prefix".equals(prop)) {
			return Scopes.scopeFor(s.getArrays());
		} else if ("Properties".equals(clazz)&& "system".equals(prop) && ! s.eIsProxy()){
			return Scopes.scopeFor(Collections.singletonList(s));
		} else if ("MarkingRef".equals(clazz) && "place".equals(prop)) {
			List<VarDecl> list = new ArrayList<VarDecl>();
			list.addAll(s.getVariables());
			list.addAll(s.getArrays());
			return Scopes.scopeFor(list);
		}
		return null;
	}


	private Boolean isRec = false;

	public IScope getScope(EObject context, EReference reference) {
		IScope res = sgetScope(context, reference);

		if (res == null) {
			res = super.getScope(context, reference);
			
			boolean doit = false;
			synchronized (isRec) {
				if (! isRec) {
					// call to getSystem may trigger a pretty stupid stack overflow... 
					isRec = true;
					doit = true;
				} else {
					return res;
				}
			}
			if (doit && context instanceof Properties && ((Properties) context).getSystem() != null) {

				Properties props = (Properties) context;
				for (IEObjectDescription desc : res.getElements(QualifiedName.create(props.getSystem().getName()))) {
					if (desc.getEObjectOrProxy() instanceof GALTypeDeclaration) {
						//							props.setSystem((System) desc.getEObjectOrProxy());
						res =  Scopes.scopeFor(Collections.singletonList(props.getSystem()));
					}
				}
				synchronized (isRec) {
					isRec = false;					
				}
			}

		}
		return res;
	}

	private static GALTypeDeclaration getSystem(EObject call) {
		EObject parent = call;
		while (parent != null && !(parent instanceof Properties)) {
			parent = parent.eContainer();
		}
		if (parent != null)
			return ((Properties) parent).getSystem();
		return null;
	}

}
