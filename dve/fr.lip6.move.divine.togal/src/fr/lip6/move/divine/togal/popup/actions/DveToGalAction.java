package fr.lip6.move.divine.togal.popup.actions;

import fr.lip6.move.divine.divine.DivineSpecification;
import fr.lip6.move.divine.togal.transform.DveToGALTransformer;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Specification;

public class DveToGalAction extends AbstractDveToGalAction {

	@Override
	public Specification doTransformation(DivineSpecification s, String galName) 
	{
		DveToGALTransformer trans = new DveToGALTransformer();
		GALTypeDeclaration gal = trans.transformToGAL (s,galName);
		Specification spec = GalFactory.eINSTANCE.createSpecification();
		spec.getTypes().add(gal);
		return spec;
	}
	
	@Override
	public String getExtension() {
		return "";
	}
}
