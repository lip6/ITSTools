package fr.lip6.move.gal.pnml.togal.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper;

import fr.lip6.move.pnml.symmetricnet.terms.Variable;
import fr.lip6.move.pnml.symmetricnet.terms.VariableDecl;

public class EqualityHelperUpToPerm extends EqualityHelper {
	
	private static final long serialVersionUID = 1L;
	private VariableDecl var1;
	private VariableDecl var2;

	public EqualityHelperUpToPerm(VariableDecl var1, VariableDecl var2) {
		this.var1 = var1;
		this.var2 = var2;
	}

	@Override
	public boolean equals(EObject p1t, EObject p2t) {
		
		if (p1t != null && p1t instanceof Variable
			&&	p2t != null && p2t instanceof Variable) {
			Variable v1 = (Variable) p1t;
			Variable v2 = (Variable) p2t;
			
			if ( (v1.getVariableDecl()==var1 && v2.getVariableDecl()==var2)
					|| (v1.getVariableDecl()==var2 && v2.getVariableDecl()==var1) ) {
				return true;
			} else if (v1.getVariableDecl()==var1 || v2.getVariableDecl()==var2
					|| v1.getVariableDecl()==var2 || v2.getVariableDecl()==var1 ) {
						return false;
			}
		}
		return super.equals(p1t, p2t);
	}
}
