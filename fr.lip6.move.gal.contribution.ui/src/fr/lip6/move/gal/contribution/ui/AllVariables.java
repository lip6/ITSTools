package fr.lip6.move.gal.contribution.ui;

import java.io.IOException;
import java.util.Arrays;

import fr.lip6.move.gal.contribution.ui.handlers.OrderHandler;
import fr.lip6.move.gal.greatspn.order.GreatSPNRunner;
import fr.lip6.move.gal.pnml.togreatspn.PNMLToGreatSPN;
import fr.lip6.move.pnml.ptnet.PetriNet;

public class AllVariables extends OrderHandler {


	@Override
	protected String getServiceName() {		
		return "Contribution";
	}

	@Override
	public void workOnSpec(PetriNet petriNet, String outpath) throws IOException {
		
		String model =  outpath + "model";
		
		PNMLToGreatSPN ptg = new PNMLToGreatSPN();
		ptg.transform(petriNet,model);
		
		String workFolder = outpath;
		GreatSPNRunner run = new GreatSPNRunner(workFolder, model);
		run.run();
		
		System.out.println(Arrays.toString(run.getOrder()));
	}

}
