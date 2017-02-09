package fr.lip6.move.gal.semantics;

import java.util.List;

import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Specification;

public interface INextBuilder {

	List<INext> getNextForLabel(String lab);

	List<Integer> getInitial();

	int size();
	
	static INextBuilder build (Specification spec) {
		if (spec.getMain() instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) spec.getMain();
			return new GalNextBuilder(gal,0);
		} else if (spec.getMain() instanceof CompositeTypeDeclaration) {
			CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) spec.getMain();
			return new CompositeNextBuilder(ctd, 0);
		}
		throw new UnsupportedOperationException("Unrecognized type for main instance.");
	}

}