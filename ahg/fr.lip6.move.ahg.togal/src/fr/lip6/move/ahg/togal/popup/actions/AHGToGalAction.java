package fr.lip6.move.ahg.togal.popup.actions;

import fr.lip6.move.ahg.togal.transform.AHGToGALTransformer;
import fr.lip6.move.gal.Specification;
import lip6.move.ahg.aHG.AttackHyperGraph;

public class AHGToGalAction extends AbstractAHGToGalAction {

	@Override
	public Specification doTransformation(AttackHyperGraph s, String galName) 
	{
		AHGToGALTransformer trans = new AHGToGALTransformer();
		Specification spec = trans.transformToGAL (s,galName);
		return spec;
	}
	
	@Override
	public String getExtension() {
		return "";
	}
}
