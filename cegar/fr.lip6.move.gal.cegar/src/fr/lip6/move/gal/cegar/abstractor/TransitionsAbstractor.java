package fr.lip6.move.gal.cegar.abstractor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;

import fr.lip6.move.gal.And;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.support.Support;
import fr.lip6.move.gal.support.SupportAnalyzer;

/**
 * A utility class (static only) for GAL abstractions with a method to abstract the Specification's Transitions using the specified Support.
 * @author Anonymous
 *
 */
public class TransitionsAbstractor {

	/**
	 * Do this work:<br>
	 * For each Transition of the specified Specification, if the Transition's Support don't intersect with the specified Support, the Transition is removed.
	 * Otherwise, the Transition's Guard and Statements are abstract if it's possible.
	 * 
	 * @param specification
	 * @param toKeep
	 */
	public static void abstractUsingSupport(Specification specification, Support toKeep) {

		Logger log = Logger.getLogger("fr.lip6.move.gal");
		List<String> removedTransitions = new ArrayList<String>();
		List<String> abstractedTransitions = new ArrayList<String>();
		
		for(TypeDeclaration typeDeclaration : specification.getTypes()) {

			if(typeDeclaration instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration)typeDeclaration;
				
				List<Transition> transitionsToDelete = new ArrayList<Transition>();
				
				for(Transition transition : gal.getTransitions()) {
					Support transitionSupport = new Support();
					
					SupportAnalyzer.computeSupport(transition, transitionSupport);
					
					if( ! toKeep.intersects(transitionSupport)) {
						transitionsToDelete.add(transition);
						
						removedTransitions.add(transition.getName());
					}
					else {
						abstractGuard(transition, toKeep, abstractedTransitions);
						abstractStatements(transition, toKeep);
					}
				}
				
				gal.getTransitions().removeAll(transitionsToDelete);
				
				log.finer("Abstracting GAL, abstracted " + abstractedTransitions.size() + " transitions' Guard " + abstractedTransitions.toString());
				log.finer("Abstracting GAL, removed " + removedTransitions.size() + " transition(s) " + removedTransitions.toString());
			}
		}
	}

	/**
	 * Abstract the specified Transition's Guard using the specified Support. If the Guard's Support do not intersect with toKeep, the Guard becomes a [TRUE] Guard.
	 * 
	 * @param transition
	 * @param toKeep
	 */
	private static void abstractGuard(Transition transition, Support toKeep, List<String> abstractedTransitions) {
		
		if(noIntersectionGuardSupport(transition.getGuard(), toKeep)) {
			transition.setGuard(GalFactory.eINSTANCE.createTrue());
			abstractedTransitions.add(transition.getName());
		}

	}

	/**
	 * Check if the specified Guard do NOT intersect with the specified Support. Try also to abstract the different Guard's conjunctions.
	 * 
	 * @param guard
	 * @param toKeep
	 * @return
	 */
	private static boolean noIntersectionGuardSupport(BooleanExpression guard, Support toKeep) {
		
		if (guard instanceof And) {
			And and = (And) guard;
			
			if (noIntersectionGuardSupport(and.getLeft(), toKeep))
				and.setLeft(GalFactory.eINSTANCE.createTrue());
			
			if (noIntersectionGuardSupport(and.getRight(), toKeep))
				and.setRight(GalFactory.eINSTANCE.createTrue());

			return false;
		}
		else if (guard instanceof Comparison) {
			Support support = new Support();
			SupportAnalyzer.computeSupport(guard, support);
			return !toKeep.intersects(support);
		}
		return false;
	}
	
	/**
	 * Abstract the Statements by removing those whose Support do not intersect with the specified Support toKeep.
	 * 
	 * @param actions
	 * @param toKeep
	 */
	private static void abstractStatements(Transition transition, Support toKeep) {
		EList<Statement> statements = transition.getActions();
		List<Statement> statementsToDelete = new ArrayList<Statement>();
		
		Logger log = Logger.getLogger("fr.lip6.move.gal");
		
		for (Statement statement : statements) {
			Support support = new Support();
			SupportAnalyzer.computeSupport(statement, support);
			if (!toKeep.intersects(support)) {
				statementsToDelete.add(statement);
			}
		
		}
		
		statements.removeAll(statementsToDelete);
		
		log.fine("Abstracting GAL, removed " + statementsToDelete.size() + " statement(s) " + "from transition " + transition.getName());
	}
}
