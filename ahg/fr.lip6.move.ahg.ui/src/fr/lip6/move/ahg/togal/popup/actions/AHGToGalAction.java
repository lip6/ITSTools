package fr.lip6.move.ahg.togal.popup.actions;

import fr.lip6.move.ahg.AttackHyperGraph;
import fr.lip6.move.ahg.togal.transform.AHGToGALTransformer;
import fr.lip6.move.gal.Specification;

public class AHGToGalAction extends AbstractAHGToGalAction {

	@Override
	public Specification doTransformation(AttackHyperGraph s, String galName) 
	{
		Specification spec = AHGToGALTransformer.transformToGAL (s,galName);
		return spec;
	}
	
	@Override
	public String getExtension() {
		return "";
	}
}
