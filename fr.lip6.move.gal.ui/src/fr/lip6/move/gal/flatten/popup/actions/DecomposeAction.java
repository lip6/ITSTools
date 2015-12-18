package fr.lip6.move.gal.flatten.popup.actions;

import java.util.Arrays;
import java.util.List;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.instantiate.CompositeBuilder;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.instantiate.Instantiator;


public class DecomposeAction extends GalAction {


	@Override
	protected String getServiceName() {		
		return "Decompose (BETA)";
	}

	@Override
	public void workWithSystem(Specification spec) throws Exception {				
		GALRewriter.flatten(spec, true);
		
		
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				CompositeBuilder.getInstance().buildComposite(gal,getWorkFolder()+"/"+getModelName()+".txt");
//				spec.getTypes().remove(td);
//				spec.getTypes().addAll(newspec.getTypes());
//				spec.setMain(spec.getTypes().get(spec.getTypes().size()-1));
				Instantiator.normalizeCalls(spec);
				return;
			}
		}
	}
	
	
	@Override
	protected String getAdditionalExtension() {		
		return ".mod";
	}

	@Override
	protected List<String> getForbiddenExtension() {
		return Arrays.asList(".mod");
	}

}
