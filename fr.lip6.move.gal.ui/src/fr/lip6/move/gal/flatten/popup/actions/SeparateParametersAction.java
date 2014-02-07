package fr.lip6.move.gal.flatten.popup.actions;

import java.util.Arrays;
import java.util.List;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;


public class SeparateParametersAction extends GalAction {


	@Override
	protected String getServiceName() {		
		return "Separate Parameters";
	}

	@Override
	protected void workWithSystem(Specification spec) throws Exception {
		GALRewriter.separateParameters(spec);
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
