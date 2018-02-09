package fr.lip6.move.gal.contribution.ui;

import java.io.IOException;
import java.util.List;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.contribution.orders.OrderBuilder;
import fr.lip6.move.gal.contribution.orders.PTGALTransformer;
import fr.lip6.move.gal.contribution.ui.handlers.OrderHandler;
import fr.lip6.move.pnml.ptnet.PetriNet;

public class AllVariables extends OrderHandler {


	@Override
	protected String getServiceName() {		
		return "Contribution";
	}

	@Override
	public void workOnSpec(PetriNet petriNet, String outpath) throws IOException {
		
		PTGALTransformer ptg = new PTGALTransformer();
		ptg.transform(petriNet, outpath);
	}

}
