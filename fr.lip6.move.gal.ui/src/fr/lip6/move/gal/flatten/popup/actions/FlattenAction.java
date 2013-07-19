package fr.lip6.move.gal.flatten.popup.actions;

import java.util.Arrays;
import java.util.List;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.instantiate.Instantiator;


public class FlattenAction extends InstantiateAction {


	@Override
	protected String getServiceName() {		
		return "Flatten Parameters";
	}

	@Override
	protected Specification workWithSystem(Specification spec) throws Exception {
		spec= Instantiator.separateParameters(spec);		
		for (TypeDeclaration td : spec.getTypes()) {
			td.setName(td.getName()+"_sep");
		}
		return super.workWithSystem(spec);
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
