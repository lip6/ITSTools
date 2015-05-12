package fr.lip6.move.gal.cegar.abstractor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import fr.lip6.move.gal.And;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.VariableReference;

public class BoundApplier {
	/**
	 * Applies the specified bound to the GAL, preventing a potentially
	 * infinite execution.
	 * @param spec the GAL to bind
	 * @param bound value that triggers an execution abortion when surpassed by
	 * at least one GAL variable  
	 */
	public static void apply(Specification spec, int bound) {

		List<TypeDeclaration> typeDeclarations = spec.getTypes();

		for (TypeDeclaration declaration : typeDeclarations) {
			if (declaration instanceof GALTypeDeclaration) {
				GALTypeDeclaration gtd = (GALTypeDeclaration) declaration;
				
				for(Transition transition : gtd.getTransitions())
					boundTransition(transition, bound);
			}
		}
	}
	/**
	 * Bound the specified Transition with the specified bound. The Transtion is bound with the Variables on wich she write.
	 * 
	 * @param transition
	 * @param bound
	 */
	private static void boundTransition(Transition transition, int bound) {
		Set<VariableReference> references = getReferencesFromTransition(transition);
		
		if(references.size() > 0) {
			BooleanExpression guard = transition.getGuard();
			
			And and = GalFactory.eINSTANCE.createAnd();
			and.setLeft(guard);
			
			transition.setGuard(createBooleanExpressionFromReferences(and, references, bound));
		}
	}
	
	/**
	 * Function to create the BooleanExpression in order to bind the transition from wich we've collected the Set references.
	 * 
	 * @param expression
	 * @param references
	 * @param bound
	 * @return
	 */
	private static BooleanExpression createBooleanExpressionFromReferences(And expression, Set<VariableReference> references, int bound) {
		if(references.size() == 1) {
			Comparison comparison = GalFactory.eINSTANCE.createComparison();
			Constant boundConstant = GalFactory.eINSTANCE.createConstant();
			Iterator<VariableReference> iterator = references.iterator();
			
			boundConstant.setValue(bound);
			comparison.setLeft(iterator.next());
			comparison.setRight(boundConstant);
			comparison.setOperator(ComparisonOperators.LE);
			
			expression.setRight(comparison);
			
			return expression;
		}
		else {
			Comparison comparison = GalFactory.eINSTANCE.createComparison();
			Constant boundConstant = GalFactory.eINSTANCE.createConstant();
			And and = GalFactory.eINSTANCE.createAnd();
			Iterator<VariableReference> iterator = references.iterator();
			
			boundConstant.setValue(bound);
			
			comparison.setLeft(iterator.next());
			comparison.setRight(boundConstant);
			comparison.setOperator(ComparisonOperators.LE);
			
			iterator.remove();
			
			expression.setRight(comparison);
			and.setLeft(expression);
			
			expression = and;
			
			return createBooleanExpressionFromReferences(expression, references, bound);
		}
	}
	
	/**
	 * A utility function to get all VariableReferences on which the specified Transition do a Assignement.
	 * 
	 * @param transition
	 * @return
	 */
	private static Set<VariableReference> getReferencesFromTransition(Transition transition) {
		Set<VariableReference> references = new HashSet<VariableReference>();
		
		Set<String> referencesName = new HashSet<String>();
		Logger log = Logger.getLogger("fr.lip6.move.gal");
		
		for(Statement action : transition.getActions()) {
			if(action instanceof Assignment) {
				Assignment assignement = (Assignment) action;
				
				VariableReference reference = GalFactory.eINSTANCE.createVariableReference();
				reference.setRef(assignement.getLeft().getRef());
				
				references.add(reference);
				referencesName.add(reference.getRef().getName());
			}
		}
		
		log.fine("Bounding transition " + transition.getName() + " with " + referencesName.size() + " variable(s) "  + referencesName);
		
		return references;
	}

}
