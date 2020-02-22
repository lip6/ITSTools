package fr.lip6.move.gal.pnml.togal;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import fr.lip6.move.gal.pnml.togal.utils.Utils;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.pnml.ptnet.Arc;
import fr.lip6.move.pnml.ptnet.PTMarking;
import fr.lip6.move.pnml.ptnet.Page;
import fr.lip6.move.pnml.ptnet.PetriNet;
import fr.lip6.move.pnml.ptnet.Place;
import fr.lip6.move.pnml.ptnet.PnObject;
import fr.lip6.move.pnml.ptnet.Transition;

public class PTSRTransformer {

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	public SparsePetriNet transform(PetriNet petriNet) {
		String modelname = Utils.normalizeName(petriNet.getName().getText());
		SparsePetriNet spn = new SparsePetriNet();
		spn.setName(modelname);
		for (Page p : petriNet.getPages()) {
			handlePage(p, spn);
		}

		return spn;
	}

	private void handlePage(Page page, SparsePetriNet spn) {
		Map<Place, Integer> placeMap = new HashMap<Place, Integer>();
		for (PnObject n : page.getObjects()) {
			if (n instanceof Place) {
				Place p = (Place) n;
				int value = interpretMarking(p.getInitialMarking());
				int index = spn.addPlace(Utils.normalizeName(p.getId()), value);
				placeMap.put(p, index);
			}
		}

		getLog().info("Transformed "+ placeMap.size() + " places.");

		for (PnObject pnobj : page.getObjects()) {
			if (pnobj instanceof Transition) {
				Transition t = (Transition) pnobj;
				
				int trind = spn.addTransition(Utils.normalizeName(t.getId()));
				
				for (Arc arc : t.getInArcs()) {
					Place pl = (Place) arc.getSource();
					int pind = placeMap.get(pl);
					int value = 1;
					if (arc.getInscription() != null
							&& arc.getInscription().getText() != null) {
						value = Math.toIntExact(arc.getInscription().getText());
					}
					spn.addPreArc(pind, trind, value);
				}
				for (Arc arc : t.getOutArcs()) {
					Place pl = (Place) arc.getTarget();
					int pind = placeMap.get(pl);
					int value = 1;
					if (arc.getInscription() != null
							&& arc.getInscription().getText() != null) {
						value = Math.toIntExact(arc.getInscription().getText());
					}
					
					spn.addPostArc(pind, trind, value);
				}
			}
		}
		getLog().info("Transformed " + spn.getTransitionCount() + " transitions.");
	}

	private int interpretMarking(PTMarking ptMarking) {
		if (ptMarking == null || ptMarking.getText() == null) {
			return 0;
		}
		return Math.toIntExact(ptMarking.getText());
	}

}
