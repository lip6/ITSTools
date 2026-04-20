package fr.lip6.move.gal.application.solver.ltl.knowledge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import fr.lip6.ltl.tgba.LTLException;
import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.application.runner.spot.SpotRunner;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;


public class KnowledgeOrchestrator {


	public static TGBA applyKnowledgeBasedReductions(SparsePetriNet spn, TGBA tgba, SpotRunner spot, Property propPNori) throws LTLException, TimeoutException {

		// To measure overall time.
		long time = System.currentTimeMillis();

		try {
			Property propPN = new Property(tgba.getApm().collectAndRewriteUsingAtoms(propPNori.getBody()), propPNori.getType(), propPNori.getName());
			// cheap knowledge 
			List<Expression> knowledge = new ArrayList<>(); 
			List<Expression> falseKnowledge = new ArrayList<>(); 


			// Step 1 : very cheap structural tests.
			// O(1) : initial state as LTL factoid
			KnowledgeFacts.addInitialStateKnowledge(knowledge, spn, tgba);
			// O(branching degree) or O(BD^2) : formulas of form Xp or XXp
			KnowledgeFacts.addNextStateKnowledge(knowledge, falseKnowledge, spn, tgba);
			// O(places + transition) : absence of SCC in PN implies AF dead 
			KnowledgeFacts.addConvergenceKnowledge(knowledge, spn, tgba);

			// First integration
			tgba = KnowledgeReducer.integrateKnowledge(spn, tgba, knowledge, falseKnowledge, propPN, spot);

			// Step 2 : leverage Reachability engine to compute invariants useful to the formula
			// O(reachability procedures). This is obviously easier than full LTL.
			// In particular due to powerful reductions/decision procedures for invariants.
			// We do not leverage full model checking here typically, more cheap structural or SMT tests and structural reductions.
			KnowledgeFacts.addInvarianceKnowledge(knowledge, falseKnowledge, spn, tgba);

			// Second integration
			propPN = new Property(tgba.getApm().collectAndRewriteUsingAtoms(spn.getProperties().get(0).getBody()), propPNori.getType(), propPNori.getName());
			tgba = KnowledgeReducer.integrateKnowledge(spn, tgba, knowledge, falseKnowledge, propPN, spot);
			
			// Step 3 : more expensive quest for negative knowledge on forced alternance of AP
			List<Expression> ff = KnowledgeFacts.computeEGknowledge(spn, tgba);
			if (!ff.isEmpty()) {
				// no need to resubmit older false knowledge nor rerun positive knowledge.
				KnowledgeReducer.tryNegativeKnowledge(propPN.getBody(), knowledge, ff, spot);
			}
		
		} finally {
			// To measure overall time.
			System.out.println("Knowledge-based reduction took " + (System.currentTimeMillis() - time) + " ms.");
		}
		
		spot.computeInfStutter(tgba);
		spot.runLTLSimplifications(spn);
		
		return tgba;
	}

}
