package fr.lip6.move.gal.flatten.popup.actions;

import java.util.Arrays;
import java.util.List;

import fr.lip6.move.gal.System;
import fr.lip6.move.gal.instantiate.Instantiator;


public class SeparateParametersAction extends GalAction {


	@Override
	protected String getServiceName() {		
		return "Separate Parameters";
	}

	@Override
	protected System workWithSystem(System s) throws Exception {
//		s = Instantiator.instantiateParametersWithAbstractColors(s);

//		s = Simplifier.simplify(flat);

		Instantiator.separateParameters(s);
		s.setName(s.getName()+"_sep");
		
		return s;
	}

	@Override
	protected String getAdditionalExtension() {		
		return ".sep";
	}
	
	@Override
	protected List<String> getForbiddenExtension() {
		return Arrays.asList("sep","flat","inst","unc");
	}

}
