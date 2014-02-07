package fr.lip6.move.gal.flatten.popup.actions;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;


public class RemoveColorAction extends GalAction {


	@Override
	protected String getServiceName() {		
		return "Remove Color";
	}

	@Override
	protected void workWithSystem(Specification spec) throws Exception {
		GALRewriter.fuseArrayCells(spec);
	}

	@Override
	protected String getAdditionalExtension() {		
		return ".unc";
	}

}
