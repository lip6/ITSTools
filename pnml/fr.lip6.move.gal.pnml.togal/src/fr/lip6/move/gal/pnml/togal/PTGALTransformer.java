package fr.lip6.move.gal.pnml.togal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.pnml.togal.utils.Utils;
import fr.lip6.move.pnml.ptnet.Arc;
import fr.lip6.move.pnml.ptnet.PTMarking;
import fr.lip6.move.pnml.ptnet.Page;
import fr.lip6.move.pnml.ptnet.PetriNet;
import fr.lip6.move.pnml.ptnet.Place;
import fr.lip6.move.pnml.ptnet.PnObject;
import fr.lip6.move.pnml.ptnet.Transition;

public class PTGALTransformer {

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	private boolean reversible=false;
	
	public GALTypeDeclaration transform(PetriNet petriNet) {

		GalFactory gf = GalFactory.eINSTANCE;

		GALTypeDeclaration gal = gf.createGALTypeDeclaration();
		gal.setName(Utils.normalizeName(petriNet.getName().getText()));

		for (Page p : petriNet.getPages()) {
			handlePage(p, gal, gf);
		}

		return gal;
	}



	private void handlePage(Page page, GALTypeDeclaration gal, GalFactory gf) {
		Map<Place, Variable> placeMap = new HashMap<Place, Variable>();
		for (PnObject n : page.getObjects()) {
			if (n instanceof Place) {
				Place p = (Place) n;

				Variable ap = GalFactory.eINSTANCE.createVariable();
				if (p.getName() != null)
					ap.setComment("/**" + Utils.normalizeName(p.getName().getText()) + "*/");
				
				ap.setName(Utils.normalizeName(p.getId()));
				int value = interpretMarking(p.getInitialMarking());
				ap.setValue(GF2.constant(value));

				gal.getVariables().add(ap);
				placeMap.put(p, ap);
			}
		}

		getLog().info("Transformed "+ placeMap.size() + " places.");

		for (PnObject pnobj : page.getObjects()) {
			if (pnobj instanceof Transition) {
				Transition t = (Transition) pnobj;
				fr.lip6.move.gal.Transition tr = gf.createTransition();
				if (t.getName() != null)
					tr.setComment("/**" + Utils.normalizeName(t.getName().getText()) + "*/");
				tr.setName(Utils.normalizeName(t.getId()));

				BooleanExpression guard = gf.createTrue();

				List<Arc> arcs = new ArrayList<>(t.getInArcs());
				arcs.sort((a,b) -> a.getSource().getId().compareTo(b.getSource().getId()));
				for (Arc arc : arcs) {
					Place pl = (Place) arc.getSource();
					Variable var = placeMap.get(pl);
					int value = 1;
					if (arc.getInscription() != null
							&& arc.getInscription().getText() != null) {
						value = Math.toIntExact(arc.getInscription().getText());
					}
					guard = GF2.and(guard, GF2.createComparison(
							GF2.createVariableRef(var), ComparisonOperators.GE,
							GF2.constant(value)));

					tr.getActions().add(
							GF2.createIncrement(var, -value));
				}

				
				arcs = new ArrayList<>(t.getOutArcs());
				arcs.sort((a,b) -> a.getTarget().getId().compareTo(b.getTarget().getId()));
				for (Arc arc : arcs) {
					Place pl = (Place) arc.getTarget();
					Variable var = placeMap.get(pl);
					int value = 1;
					if (arc.getInscription() != null
							&& arc.getInscription().getText() != null) {
						value = Math.toIntExact(arc.getInscription().getText());
					}
					
					// ensure transition relation is reversible by asserting positive variables
					if (reversible)
						guard = GF2.and(guard, GF2.createComparison(
							GF2.createVariableRef(var), ComparisonOperators.GE,
							GF2.constant(0)));
					
					tr.getActions().add(
							GF2.createIncrement(var, value));
				}
				
				tr.setGuard(guard);
				gal.getTransitions().add(tr);

			}
		}
		getLog().info("Transformed " + gal.getTransitions().size() + " transitions.");

	}

	private int interpretMarking(PTMarking ptMarking) {
		if (ptMarking == null || ptMarking.getText() == null) {
			return 0;
		}
		return Math.toIntExact(ptMarking.getText());
	}

	public void setReversible(boolean reversible) {
		this.reversible = reversible;
	}

}
