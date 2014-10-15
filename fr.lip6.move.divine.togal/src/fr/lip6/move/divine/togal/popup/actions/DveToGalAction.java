package fr.lip6.move.divine.togal.popup.actions;

import fr.lip6.move.divine.divine.DivineSpecification;
import fr.lip6.move.divine.togal.transform.DveToGALTransformer;
import fr.lip6.move.gal.GALTypeDeclaration;

public class DveToGalAction extends AbstractDveToGalAction {

	@Override
	public GALTypeDeclaration doTransformation(DivineSpecification s, String galName) 
	{
		DveToGALTransformer trans = new DveToGALTransformer();
		GALTypeDeclaration gal = trans.transformToGAL (s,galName);
		return gal;
	}
	
	@Override
	public String getExtension() {
		return "";
	}
}
