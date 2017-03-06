package fr.lip6.move.gal.flatten.popup.actions;


import fr.lip6.move.gal.System;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.instantiate.Simplifier;

public class InstantiateAction extends GalAction {

	@Override
	protected System workWithSystem(System s) throws Exception {
		//						boolean isPetri = Simplifier.simplifyPetriStyleAssignments(s);
		//						if (isPetri) {
		//							Simplifier.simplifyImplicitVariables(s);
		//						}
		java.lang.System.err.println("Instantiating model :"+s.getName() + ". Model has "+ s.getTransitions().size() + " transitions.");

		s = Instantiator.instantiateParameters(s);

		s = Simplifier.simplify(s);

		Instantiator.normalizeCalls(s);
		
		s.setName(s.getName()+"_inst");
		java.lang.System.err.println("Resulting model :"+s.getName() + " has "+ s.getTransitions().size() + " transitions.");
		
		return s;
	}

	@Override
	protected String getAdditionalExtension() {
		return ".inst";
	}

	@Override
	protected String getServiceName() {
		return "Instantiate Parameters";
	}
	
}
