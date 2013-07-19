package fr.lip6.move.gal.flatten.popup.actions;

import java.util.Arrays;
import java.util.List;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.instantiate.Instantiator;


public class SeparateParametersAction extends GalAction {


	@Override
	protected String getServiceName() {		
		return "Separate Parameters";
	}

	@Override
	protected Specification workWithSystem(Specification spec) throws Exception {
		//		s = Instantiator.instantiateParametersWithAbstractColors(s);

		//		s = Simplifier.simplify(flat);
		Instantiator.separateParameters(spec);
		for (TypeDeclaration td : spec.getTypes()) {
				td.setName(td.getName()+"_sep");
		}
		return spec;
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
