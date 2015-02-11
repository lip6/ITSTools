package fr.lip6.move.gal.flatten.popup.actions;


import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;

public class InstantiateAction extends GalAction {

	@Override
	protected void workWithSystem(Specification spec) throws Exception {

//		java.lang.System.err.println("Instantiating model :"+s.getName() + ". Model has "+ s.getTransitions().size() + " transitions.");
		GALRewriter.flatten(spec, false);
		
//		java.lang.System.err.println("Resulting model :"+s.getName() + " has "+ s.getTransitions().size() + " transitions.");

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
