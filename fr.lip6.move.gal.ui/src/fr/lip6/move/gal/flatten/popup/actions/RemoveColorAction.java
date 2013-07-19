package fr.lip6.move.gal.flatten.popup.actions;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.instantiate.Simplifier;


public class RemoveColorAction extends GalAction {


	@Override
	protected String getServiceName() {		
		return "Remove Color";
	}

	@Override
	protected Specification workWithSystem(Specification spec) throws Exception {

		spec = Instantiator.instantiateParametersWithAbstractColors(spec);

		spec = Simplifier.simplify(spec);

		spec = Instantiator.fuseIsomorphicEffects(spec);
		for (TypeDeclaration td : spec.getTypes()) {
			td.setName(td.getName()+"_unc");
		}
		return spec;
	}

	@Override
	protected String getAdditionalExtension() {		
		return ".unc";
	}

}
