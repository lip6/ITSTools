package fr.lip6.move.gal.flatten.popup.actions;

import java.util.Arrays;
import java.util.List;

import fr.lip6.move.gal.System;
import fr.lip6.move.gal.instantiate.Instantiator;


public class FlattenAction extends InstantiateAction {


	@Override
	protected String getServiceName() {		
		return "Flatten Parameters";
	}

	@Override
	protected System workWithSystem(System s) throws Exception {
		s= Instantiator.separateParameters(s);		
		s.setName(s.getName()+"_sep");
		return super.workWithSystem(s);
	}

	@Override
	protected String getAdditionalExtension() {		
		return ".sep.flat";
	}
	
	@Override
	protected List<String> getForbiddenExtension() {
		return Arrays.asList("sep","flat","inst","unc");
	}

}
