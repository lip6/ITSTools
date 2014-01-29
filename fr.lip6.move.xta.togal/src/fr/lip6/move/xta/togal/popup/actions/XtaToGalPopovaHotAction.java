package fr.lip6.move.xta.togal.popup.actions;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.timedAutomata.XTA;
import fr.lip6.move.xta.togal.transform.XtaToGALTransformer;

public class XtaToGalPopovaHotAction extends XtaToGalAction {

	@Override
	public GALTypeDeclaration doTransformation(XTA s, String galName) 
	{
		XtaToGALTransformer trans = new XtaToGALTransformer();
		GALTypeDeclaration gal = trans.transformToGAL (s,galName,true,true);
		return gal;
	}

	@Override
	public String getExtension() {
		return ".pop.hot";
	}
}
