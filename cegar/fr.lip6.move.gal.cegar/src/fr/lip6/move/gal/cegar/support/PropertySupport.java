package fr.lip6.move.gal.cegar.support;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.cegar.interfaces.IPropertySupport;
import fr.lip6.move.gal.support.ISupportVariable;
import fr.lip6.move.gal.support.Support;

public class PropertySupport implements IPropertySupport {
	private final Support support;
	private final SupportManager parent;
	private int depth;
	
	public PropertySupport(SupportManager parent, Support initial, int initialDepth) {
		this.parent = parent;
		this.support = initial;
		this.depth = initialDepth;
	}
		
	public Support adaptTo(Specification spec) {
		if (parent.getSpecification().equals(spec))
			return support;
		
		Support adapted = new Support();
		
		for (TypeDeclaration typeDeclaration : spec.getTypes()) {

			if (typeDeclaration instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration)typeDeclaration;
				
				for (Variable var : gal.getVariables()) {
					for (ISupportVariable v : support)
						if (var.getName().equals(v.getVar().getName()))
							adapted.add(var);
				}				
			}
		}
		return adapted;
	}
	
	public void refine(Support toAdd) {
		this.support.addAll(toAdd);
	}
	
	public int getIncreasedDepth() {
		return ++depth;
	}
}
