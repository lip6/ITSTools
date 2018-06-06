package fr.lip6.move.gal.flatten.popup.actions;

import java.util.Arrays;
import java.util.List;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.FusionBuilder;
import fr.lip6.move.gal.instantiate.GALRewriter;


public class FuseAction extends GalAction {


	@Override
	protected String getServiceName() {		
		return "Fuse hierarchy";
	}

	@Override
	public void workWithSystem(Specification spec) throws Exception {				
		GALRewriter.flatten(spec, true);
		FusionBuilder.toSingleGAL(spec);
	}
	
	
	@Override
	protected String getAdditionalExtension() {		
		return ".fuse";
	}

	@Override
	protected List<String> getForbiddenExtension() {
		return Arrays.asList(".fuse");
	}

}
