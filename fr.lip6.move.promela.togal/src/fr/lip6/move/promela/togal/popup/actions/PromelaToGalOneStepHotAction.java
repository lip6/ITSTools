package fr.lip6.move.promela.togal.popup.actions;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.promela.promela.PromelaSpecification;
import fr.lip6.move.promela.togal.transform.PromelaToGALTransformer;

public class PromelaToGalOneStepHotAction extends PromelaToGalAction {

	public Specification doTransformation(PromelaSpecification s, String galName) {
		PromelaToGALTransformer trans = new PromelaToGALTransformer();
		Specification gal = trans.transformToGAL(s, galName);
		return gal;
	}

	public String getExtension() {
		return "";
		// Why?
	}
}
