package fr.lip6.move.gal.application.solver.ltl.knowledge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import fr.lip6.ltl.tgba.AcceptedRunFoundException;
import fr.lip6.ltl.tgba.EmptyProductException;
import fr.lip6.ltl.tgba.LTLException;
import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.application.runner.spot.SpotRunner;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class KnowledgeReducer {
	
	
	public static TGBA integrateKnowledge(SparsePetriNet spn, TGBA tgba, List<Expression> knowledge, List<Expression> falseKnowledge, Property propPN,
			SpotRunner spot) throws TimeoutException, EmptyProductException, AcceptedRunFoundException {
		
		long time = System.currentTimeMillis();
		int oriAlphabetSize = tgba.getAPs().size();
		int oriNbStates = tgba.getEdges().size();
		int oriNbEdge = tgba.getEdges().stream().mapToInt(List::size).sum();
		boolean wasStutter = tgba.isStutterInvariant();
		
		TGBA tgbared = tgba;
		try {
			tgbared = knowledgeLoop(tgba, propPN.getBody(), knowledge, falseKnowledge, spot);

			if (tgbared.isEmptyLanguage()) {
				throw new EmptyProductException("KNOWLEDGE");
			} else if (tgbared.isUniversalLanguage()) {
				throw new AcceptedRunFoundException("KNOWLEDGE");
			}
			
			System.out.println("Knowledge based reduction with " + knowledge.size() + " factoid took "
					+ (System.currentTimeMillis() - time) + " ms. Reduced automaton from " + oriNbStates + " states, "
					+ oriNbEdge + " edges and " + oriAlphabetSize + " AP (stutter "+ (wasStutter?"insensitive":"sensitive") +") to " + tgbared.getEdges().size() + " states, "
					+ tgbared.getEdges().stream().mapToInt(List::size).sum() + " edges and " + tgbared.getAPs().size() + " AP (stutter " + (tgbared.isStutterInvariant()?"insensitive":"sensitive")+").");		
		} catch (LTLException e) {
			System.out.println("Knowledge based reduction with " + knowledge.size() + " factoid took "
					+ (System.currentTimeMillis() - time) + " ms. Proved property starting from automaton with " + oriNbStates + " states, "
					+ oriNbEdge + " edges and " + oriAlphabetSize + " AP (stutter "+ (wasStutter?"insensitive":"sensitive"));
			throw e;
		} catch (TimeoutException e) {
			System.out.println("Timeout when exploiting knowledge after " + (System.currentTimeMillis() - time) + " ms. Continuing without knowledge-based reduction.");
		}
		
		return tgbared;
	}
	
    /**
     * Practical knowledge integration following the benchmark-validated decision tree.
     * 
     * This function implements the four-step procedure exactly as decided from measurements:
     * 1. Test emptiness of (phi AND K+_QE) using satisfiability (phi is 
     * 2. Test emptiness of (NOT phi AND K+_QE) using satisfiability.
     * 3. Isolated negative-knowledge satisfiability test (counter example only).
     * 4. Fallback to precise conjunction with AUTO_SI vs AUTO_SMALL.
     * 
     * If any step decides the property, the corresponding exception is thrown.
     * Otherwise the best reduced TGBA is returned.
     * 
     * The precise conjunction is built only when needed (SpotRunner.givenThat accepts lists directly).
     * Filtering and existential quantification are currently a no-op placeholder.
     */
	public static TGBA knowledgeLoop(TGBA tgba, Expression phi, List<Expression> positiveKnowledge, List<Expression> falseKnowledge, SpotRunner spot) 
            throws EmptyProductException, AcceptedRunFoundException, TimeoutException {

        if (positiveKnowledge.isEmpty() && falseKnowledge.isEmpty()) {
            return tgba;
        }
        
		if (tgba.isEmptyLanguage()) {
			throw new EmptyProductException("KNOWLEDGE");
		} else if (tgba.isUniversalLanguage()) {
			throw new AcceptedRunFoundException("KNOWLEDGE");
		}

        // Filter + existential quantification applied individually to each fact.
        // Currently a no-op placeholder (QE/filtering not active).
        // List<Expression> kPlusQEList = filterAndQuantifyExistentially(positiveKnowledge, phi);

        // === Step 1: build conjunction of facts as an expression ===
        Expression kPlus ;
        if (positiveKnowledge.isEmpty()) {
			kPlus = Expression.constant(true);
		} else if (positiveKnowledge.size() == 1) {
			kPlus = positiveKnowledge.get(0);
		} else {
			kPlus = Expression.nop(Op.AND, positiveKnowledge);
		}
        
        // === Step 2 : direct implications of knowledge with respect to phi ===
        try {
        	// a) Test satisfiability of (!phi AND K+) => 
        	// * if UNSAT,  min(!phi) construction is empty. phi is true (implied by K+).
        	if (! spot.isSatisfiable(Expression.op(Op.AND, Expression.not(phi), kPlus))) {
        		System.out.println("Property proved to be true by K+ with min(!phi)");
        		throw new EmptyProductException("KNOWLEDGE");
        	}

        	// b) Test satisfiability of (phi AND K+) =>
        	// * if UNSAT, min(phi) construction is empty. this is equivalent to universality of  max(!phi)
        	// any run of the system is a counter-example to phi, so phi is false.
        	if (! spot.isSatisfiable(Expression.op(Op.AND, phi, kPlus))) {
        		System.out.println("Property proved to be false by K+ with min(phi)");
        		throw new AcceptedRunFoundException("KNOWLEDGE");
        	}
        } catch (IOException e) {
        	System.out.println("IOException or timeout when running Spot to compute min construction : " + e);
        }
        
        // === Step 3 : negative knowledge tests ===
        // Can only prove existence of a counter-example. 
		/* K- is false ; so !K- contains a run of S.*/
		/* This run must also be part of K+, since K+ wraps S.  */
		/* So, consider (!K- AND K+) : it must contain a system run.*/
        /* It also must be non empty since K- false means at least one run, and all runs are in K+. */
		/* So if it is included in !phi, !phi contains a system run, that is a counterexample to phi. */

		/* To avoid complementations and full inclusions, we leverage a "satisfiable" LTL test */
		/* !K- AND K+ AND phi unsatisfiable => all of (!K- AND K+) included in !phi.*/
		/* hence we have a counterexample to phi if  : !K- AND K+ AND phi is UNSAT*/
        tryNegativeKnowledge(phi, kPlus, falseKnowledge, spot);
        
        
        // === Step 4 : Knowledge we have cannot prove or disprove entirely phi: build smaller automata ===
        // We leverage the constructions BM (bounded by Minato) and "Stutter Relax" "Stutter Restrict".
        // See Petri Nets 2025 paper.
        // We build several (its cheap), and pick the best (smallest, stutter-invariant if possible).
        // Currently : Spot has auto-small and auto-si that fixpoint to get small automata.
        TGBA autoSI    = spot.givenThat(tgba, positiveKnowledge, SpotRunner.GivenStrategy.AUTO_SI);   
        TGBA autoSmall = spot.givenThat(tgba, positiveKnowledge, SpotRunner.GivenStrategy.AUTO_SMALL);

        return pickBestAutomaton(autoSI, autoSmall);
    }

	
    /**
     * Isolated negative-knowledge test, callable independently.
     * For each false fact K- we test satisfiability of (NOT K-) AND (phi AND K+_QE).
     * If any test is UNSAT, we have a counter-example to phi.
     * @throws AcceptedRunFoundException 
     */
    public static void tryNegativeKnowledge(Expression phi,
                                             List<Expression> knowledge,
                                             List<Expression> falseKnowledge,
                                             SpotRunner spot) throws AcceptedRunFoundException {
    	tryNegativeKnowledge(phi, Expression.nop(Op.AND,knowledge), falseKnowledge, spot);
    }
	
    /**
     * Isolated negative-knowledge test, callable independently.
     * For each false fact K- we test satisfiability of (NOT K-) AND (phi AND K+_QE).
     * If any test is UNSAT, we have a counter-example to phi.
     */
    public static void tryNegativeKnowledge(Expression phi,
                                             Expression kPlus,
                                             List<Expression> falseKnowledge,
                                             SpotRunner spot)
            throws AcceptedRunFoundException {

        if (falseKnowledge.isEmpty()) {
            return;
        }

        Expression kPlusAndPhi = Expression.op(Op.AND, phi, kPlus);
        
        boolean first = true;

        for (Expression fk : falseKnowledge) {
        	Expression testForm = Expression.op(Op.AND, Expression.not(fk), kPlusAndPhi);
        	try {
        		if (!spot.isSatisfiable(testForm)) {
        			System.out.println("Negative knowledge disproves property using: " + fk);
        			throw new AcceptedRunFoundException("NEGATIVE_KNOWLEDGE");   // counter-example found
        		}
        	} catch (IOException e) {
        		if (first) {
        			System.out.println("IOException or timeout when running Spot to compute negative knowledge test : " + e + "Not reporting more errors.");
        			first = false;
        		}
        	}
        }
    }

    /**
     * Placeholder for support-based filtering + syntactic existential quantification.
     * Applied individually to each positive knowledge fact.
     * Currently returns the input list unchanged (QE/filtering not active).
     * 
     * Later implementation can:
     *   - keep only facts whose support intersects phi's support
     *   - call Simplifier.existentialQuantification on extra APs
     */
    private static List<Expression> filterAndQuantifyExistentially(List<Expression> positiveKnowledge,
                                                                   Expression targetFormula) {
        return new ArrayList<>(positiveKnowledge);   // no-op for now
    }

    private static TGBA pickBestAutomaton(TGBA a, TGBA b) {
        if (a == null) return b;
        if (b == null) return a;

        boolean aIsSI = a.isStutterInvariant();
        boolean bIsSI = b.isStutterInvariant();

        if (aIsSI && !bIsSI) return a;
        if (bIsSI && !aIsSI) return b;
        
        // TODO : refine tie breaker with edges, acceptance...
        return (a.nbStates() <= b.nbStates()) ? a : b;
    }
	
}
