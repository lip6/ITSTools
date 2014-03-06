package fr.lip6.move.gal.flatten.popup.actions;

import java.util.Arrays;
import java.util.List;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;


public class FlattenAction extends GalAction {


	@Override
	protected String getServiceName() {		
		return "Flatten Parameters";
	}

	@Override
	public void workWithSystem(Specification spec) throws Exception {				
		GALRewriter.flatten(spec, true);	
	}
	
	
	@Override
	protected String getAdditionalExtension() {		
		return ".flat";
	}

	@Override
	protected List<String> getForbiddenExtension() {
		return Arrays.asList("sep","flat","inst","unc");
	}

}
