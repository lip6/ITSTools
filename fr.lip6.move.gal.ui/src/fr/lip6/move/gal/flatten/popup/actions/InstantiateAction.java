package fr.lip6.move.gal.flatten.popup.actions;


import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.instantiate.Simplifier;

public class InstantiateAction extends GalAction {

	@Override
	protected Specification workWithSystem(Specification spec) throws Exception {

		//						boolean isPetri = Simplifier.simplifyPetriStyleAssignments(s);
		//						if (isPetri) {
		//							Simplifier.simplifyImplicitVariables(s);
		//						}
//		java.lang.System.err.println("Instantiating model :"+s.getName() + ". Model has "+ s.getTransitions().size() + " transitions.");

		spec = Instantiator.instantiateParameters(spec);

		spec = Simplifier.simplify(spec);

		Instantiator.normalizeCalls(spec);

		for (TypeDeclaration td : spec.getTypes()) {
			td.setName(td.getName()+"_inst");
		}
//		java.lang.System.err.println("Resulting model :"+s.getName() + " has "+ s.getTransitions().size() + " transitions.");

		return spec;
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
