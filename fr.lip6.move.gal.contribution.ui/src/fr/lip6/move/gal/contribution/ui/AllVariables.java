package fr.lip6.move.gal.contribution.ui;

import java.io.IOException;

import fr.lip6.move.gal.contribution.ui.handlers.OrderHandler;
import fr.lip6.move.gal.pnml.togreatspn.PNMLToGreatSPN;
import fr.lip6.move.pnml.ptnet.PetriNet;

public class AllVariables extends OrderHandler {


	@Override
	protected String getServiceName() {		
		return "Contribution";
	}

	@Override
	public void workOnSpec(PetriNet petriNet, String outpath) throws IOException {
		
		PNMLToGreatSPN ptg = new PNMLToGreatSPN();
		ptg.transform(petriNet, outpath);
	}

}
