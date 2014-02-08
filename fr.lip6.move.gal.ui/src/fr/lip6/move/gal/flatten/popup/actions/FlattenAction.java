package fr.lip6.move.gal.flatten.popup.actions;

import java.util.Arrays;
import java.util.List;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.instantiate.CompositeBuilder;
import fr.lip6.move.gal.instantiate.GALRewriter;


public class FlattenAction extends GalAction {


	@Override
	protected String getServiceName() {		
		return "Flatten Parameters";
	}

	@Override
	public void workWithSystem(Specification spec) throws Exception {				
		GALRewriter.flatten(spec, true);
		
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				CompositeBuilder.buildComposite(gal);				
			}
		}
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
