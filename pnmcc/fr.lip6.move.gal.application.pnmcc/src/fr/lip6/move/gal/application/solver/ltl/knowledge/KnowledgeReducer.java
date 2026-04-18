package fr.lip6.move.gal.application.solver.ltl.knowledge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

	public static TGBA applyKnowledgeBasedReductions(SparsePetriNet spn, TGBA tgba, SpotRunner spot, Property propPN) throws LTLException, TimeoutException {
		
		// cheap knowledge 
		List<Expression> knowledge = new ArrayList<>(); 
		List<Expression> falseKnowledge = new ArrayList<>(); 
		
		KnowledgeFacts.addInitialStateKnowledge(knowledge, spn, tgba);
	
		KnowledgeFacts.addNextStateKnowledge(knowledge, falseKnowledge, spn, tgba);
		
		KnowledgeFacts.addConvergenceKnowledge(knowledge, spn, tgba);
		
		System.out.println("Knowledge obtained : " + knowledge);
		System.out.println("False Knowledge obtained : " + falseKnowledge);
		
		// try to reduce the tgba using this knowledge
		if (false)
			tgba = manuallyIntegrateKnowledge(spn, tgba, knowledge, propPN, spot);
		else
			tgba = spotIntegrateKnowledge(spn, tgba, knowledge, falseKnowledge, propPN, spot);
	
		if (tgba.isEmptyLanguage() || tgba.isUniversalLanguage()) {
			return tgba;
		} else {
			KnowledgeFacts.addInvarianceKnowledge(knowledge, falseKnowledge, spn, tgba);
	
			System.out.println("Knowledge obtained : " + knowledge);
			System.out.println("False Knowledge obtained : " + falseKnowledge);
	
			tgba = spotIntegrateKnowledge(spn, tgba, knowledge, falseKnowledge, propPN, spot);						
		}
		
		if (tgba.isEmptyLanguage() || tgba.isUniversalLanguage()) {
			return tgba;
		} else {
			spot.computeInfStutter(tgba);
			List<Expression> ff = KnowledgeFacts.computeEGknowledge(spn, tgba);
			if (!ff.isEmpty()) {
				falseKnowledge.addAll(ff);
				System.out.println("Knowledge obtained : " + knowledge);
				System.out.println("False Knowledge obtained : " + falseKnowledge);
	
				tgba = spotIntegrateKnowledge(spn, tgba, knowledge, falseKnowledge, propPN, spot);						
			}
		}
	
		return tgba;
	}

	public static TGBA manuallyIntegrateKnowledge(SparsePetriNet spn, TGBA tgba, List<Expression> knowledge, Property propPN,
				SpotRunner spot) throws AcceptedRunFoundException, EmptyProductException, TimeoutException {
			boolean needRebuild = true;
			boolean wasAdopted = false;
			for (Expression factoid : knowledge) {
				String ltl = SpotRunner.printLTLProperty(factoid);
	
				try {
					// need to complement tgba				
	
					File comp = Files.createTempFile("comp", ".hoa").toFile();
					if (needRebuild) {
						if (! spot.buildComplement(tgba, comp)) {
							// failure of Spot ?
							continue;
						}				
					}
					// test inclusion : Knowledge dominates the formula
					// i.e. A is a subset of K
					// therefore !K*A = 0
					//				if (sr.isProductEmpty(comp,"!(" +ltl + ")")) {
					//					// property is true, negation is empty
					//					System.out.println("Property (complement) proved to be true thanks to knowledge :" + factoid);
					//					return TGBA.makeFalse(); 
					//				}
	
					// test disjoint : A * K is empty
					// therefore, A does not cover K => does not cover S
					// we have empty product with !A.
					if (spot.isProductEmpty(comp,ltl)) {
						System.out.println("Property (complement) proved to be false thanks to knowledge :" + factoid);
						throw new AcceptedRunFoundException("KNOWLEDGE");
						//return TGBA.makeTrue();
					}
				} catch (IOException e) {
					// skip
					System.out.println("IOexception raised when running Spot : " + e);
				}
	
				TGBA prod = spot.computeProduct(tgba, ltl);
				if (prod.getEdges().get(prod.getInitial()).size() == 0) {
					// this is just false !
					System.out.println("Property proved to be true thanks to knowledge :" + factoid);
					throw new EmptyProductException("KNOWLEDGE");
	//				return TGBA.makeFalse();
				} else if (prod.getProperties().contains("stutter-invariant") && ! tgba.getProperties().contains("stutter-invariant")) {
					System.out.println("Adopting stutter invariant property thanks to knowledge :" + factoid);
					tgba = prod;
					propPN.setBody(Expression.op(Op.OR, propPN.getBody(), Expression.not(Expression.resolveAP(factoid))));
					needRebuild = true;
					wasAdopted = true;
				} else if (prod.getAPs().size() < tgba.getAPs().size()) {
					System.out.println("Adopting property with smaller alphabet thanks to knowledge :" + factoid);
					tgba = prod;
					propPN.setBody(Expression.op(Op.OR, propPN.getBody(), Expression.not(Expression.resolveAP(factoid))));
					needRebuild = true;
					wasAdopted = true;
				}			
			}						
	
			if (wasAdopted) {
				spot.computeInfStutter(tgba);
				spot.runLTLSimplifications(spn);
			}
			return tgba;
		}

	private static TGBA spotIntegrateKnowledge(SparsePetriNet spn, TGBA tgba, List<Expression> knowledge, List<Expression> falseKnowledge, Property propPN,
			SpotRunner spot) throws TimeoutException, EmptyProductException, AcceptedRunFoundException {
		
		long time = System.currentTimeMillis();
		int oriAlphabetSize = tgba.getAPs().size();
		int oriNbStates = tgba.getEdges().size();
		int oriNbEdge = tgba.getEdges().stream().mapToInt(List::size).sum();
		boolean wasStutter = tgba.isStutterInvariant();
		
		tgba = knowledgeLoop(tgba, knowledge, falseKnowledge, spot);
				
		System.out.println("Knowledge based reduction with " + knowledge.size() + " factoid took "
				+ (System.currentTimeMillis() - time) + " ms. Reduced automaton from " + oriNbStates + " states, "
				+ oriNbEdge + " edges and " + oriAlphabetSize + " AP (stutter "+ (wasStutter?"insensitive":"sensitive") +") to " + tgba.getEdges().size() + " states, "
				+ tgba.getEdges().stream().mapToInt(List::size).sum() + " edges and " + tgba.getAPs().size() + " AP (stutter " + (tgba.isStutterInvariant()?"insensitive":"sensitive")+").");		
	
		if (tgba.isEmptyLanguage()) {
			throw new EmptyProductException("KNOWLEDGE");
		} else if (tgba.isUniversalLanguage()) {
			throw new AcceptedRunFoundException("KNOWLEDGE");
		}
				
		spot.computeInfStutter(tgba);
		spot.runLTLSimplifications(spn);
		
		return tgba;
	}

	public static TGBA knowledgeLoop(TGBA tgba, List<Expression> knowledge, List<Expression> falseKnowledge, SpotRunner spot) {
		
		
		
		
		if (true) {
			// Spot 2.11+
			TGBA tgbarelax = tgba;
			TGBA res = null;
			res = spot.givenThat(tgba, knowledge, SpotRunner.GivenStrategy.MINATO);
			if (res != null) tgbarelax = res;
			
			if (tgbarelax.isEmptyLanguage()) {
				System.out.println("Property proved to be true thanks to knowledge (Minato strategy)");
				return tgbarelax;
			} else if (tgbarelax.isUniversalLanguage()) {
				System.out.println("Property proved to be false thanks to knowledge (Minato strategy)");
				return tgbarelax;
			}
			
			// more aggressive : AND the knowledge
			{
				Expression allFacts = Expression.nop(Op.AND, knowledge);
				res = spot.givenThat(tgbarelax, allFacts, SpotRunner.GivenStrategy.MINATO);
				if (res != null) tgbarelax = res;
				res = spot.givenThat(tgbarelax, allFacts, SpotRunner.GivenStrategy.STUTTER_RELAX);
				if (res != null) tgbarelax = res;
				res = spot.givenThat(tgbarelax, allFacts, SpotRunner.GivenStrategy.MINATO);
				if (res != null) tgbarelax = res;
				res = spot.givenThat(tgbarelax, allFacts, SpotRunner.GivenStrategy.STUTTER_RELAX);
				if (res != null) tgbarelax = res;
				res = spot.givenThat(tgbarelax, allFacts, SpotRunner.GivenStrategy.ALL);
				if (res != null) tgbarelax = res;
			}
	
			if (tgbarelax.isEmptyLanguage()) {
				System.out.println("Property proved to be true thanks to conjunction of knowledge (Minato strategy)");
				return tgbarelax;
			} else if (tgbarelax.isUniversalLanguage()) {
				System.out.println("Property proved to be false thanks to conjunction of knowledge (Minato strategy)");
				return tgbarelax;
			}
	
			
			if (! tgba.isStutterInvariant() && tgbarelax.isStutterInvariant()) {
				System.out.println("Knowledge sufficient to adopt a stutter insensitive property.");
			}
	
			
			// test inclusion
			// autfilt --included-in=AnotPhi.hoa Kmoins.hoa
			{
				Expression allFacts = Expression.nop(Op.AND, knowledge);
				for (Expression factoid : falseKnowledge) {				
					Expression negFact = Expression.op(Op.AND,allFacts,factoid);
					if (spot.isIncludedIn(negFact,tgbarelax)) {
						System.out.println("Property proved to be false thanks to negative knowledge :" + factoid);
						return TGBA.makeTrue();				
					}
				}
			}
			return tgbarelax;
		} else {
		
			// OLD STYLE : Spot 2.10.4.dev "manual" knowledge loop
			// counter-examples ?
			{
				TGBA tgbarelax = tgba;
				for (Expression factoid : knowledge) {
					tgbarelax = spot.givenThat(tgbarelax, factoid, SpotRunner.GivenStrategy.RELAX);
					if (tgba.isEmptyLanguage()) {
						System.out.println("Property proved to be true thanks to knowledge :" + factoid);
						return tgbarelax;
					} else if (tgba.isUniversalLanguage()) {
						System.out.println("Property proved to be false thanks to knowledge :" + factoid);
						return tgbarelax;
					}
				}
				// test inclusion
				// autfilt --included-in=AnotPhi.hoa Kmoins.hoa
				for (Expression factoid : falseKnowledge) {
					if (spot.isIncludedIn(factoid,tgbarelax)) {
						System.out.println("Property proved to be false thanks to negative knowledge :" + factoid);
						return TGBA.makeTrue();				
					}
				}
			}
	
	
			for (Expression factoid : knowledge) {
				tgba = spot.givenThat(tgba, factoid, SpotRunner.GivenStrategy.RESTRICT);
				if (tgba.isEmptyLanguage()) {
					System.out.println("Property proved to be true thanks to knowledge :" + factoid);
					return tgba;
				}
			}
	
			if (!tgba.isStutterInvariant()) {
				Expression allFacts = Expression.nop(Op.AND, knowledge);
				tgba = spot.givenThat(tgba, allFacts, SpotRunner.GivenStrategy.STUTTER_RELAX);
				if (tgba.isStutterInvariant()) {
					System.out.println("Knowledge sufficient to adopt a stutter insensitive property.");
				}
			}
	
			for (Expression factoid : knowledge) {
				tgba = spot.givenThat(tgba, factoid, SpotRunner.GivenStrategy.RELAX);
				if (tgba.isEmptyLanguage()) {
					System.out.println("Property proved to be true thanks to knowledge :" + factoid);
					return tgba;
				} else if (tgba.isUniversalLanguage()) {
					System.out.println("Property proved to be false thanks to knowledge :" + factoid);
					return tgba;
				}
			}
		}
		return tgba;
	}

}
