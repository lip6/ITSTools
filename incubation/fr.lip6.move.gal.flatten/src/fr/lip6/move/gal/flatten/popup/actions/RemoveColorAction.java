package fr.lip6.move.gal.flatten.popup.actions;

import fr.lip6.move.gal.System;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.instantiate.Simplifier;


public class RemoveColorAction extends GalAction {


	@Override
	protected String getServiceName() {		
		return "Remove Color";
	}

	@Override
	protected System workWithSystem(System s) throws Exception {
		s = Instantiator.instantiateParametersWithAbstractColors(s);

		s = Simplifier.simplify(s);

		s.setName(s.getName()+"_unc");
		
		return s;
	}

	@Override
	protected String getAdditionalExtension() {		
		return ".unc";
	}

}
