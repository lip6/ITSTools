package fr.lip6.move.ahg.togal.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.LogicProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.Variable;
import lip6.move.ahg.aHG.Attack;
import lip6.move.ahg.aHG.AttackHyperGraph;
import lip6.move.ahg.aHG.Edge;
import lip6.move.ahg.aHG.Node;



public class AHGToGALTransformer {

	public Specification transformToGAL(AttackHyperGraph ahg, String name) {

		GalFactory GF = GalFactory.eINSTANCE;
		GALTypeDeclaration gal = GF.createGALTypeDeclaration();

		gal.setName(name);

		Map<Node,Variable> nodeMap = new HashMap<>();

		for (Node n : ahg.getNodes()) {
			Variable v = GF.createVariable();
			v.setName("State_"+n.getName());
			v.setValue(GF2.constant(0));
			gal.getVariables().add(v);
			nodeMap.put(n, v);
		}

		Attack attack = ahg.getAttack();
		for (Node n : attack.getInit()) {
			nodeMap.get(n).setValue(GF2.constant(1));
		}
		BooleanExpression goalNotReached = GF.createTrue();
		{
			for (Node n : attack.getGoal()) {
				goalNotReached = GF2.or(
						goalNotReached, 	
						GF2.createComparison(
								GF2.createVariableRef(nodeMap.get(n)),
								ComparisonOperators.EQ, 
								GF2.constant(0)));
			}
		}

		for (Edge e : ahg.getEdges()) {
			Variable v = GF.createVariable();
			v.setName("Edge_"+e.getName());
			v.setValue(GF2.constant(0));
			gal.getVariables().add(v);


			Transition t = GF.createTransition();
			t.setName(e.getName());
			gal.getTransitions().add(t);
			BooleanExpression guard = EcoreUtil.copy(goalNotReached);

			// attack vector unused
			guard = GF2.and(guard,
					GF2.createComparison(GF2.createVariableRef(v), 
							ComparisonOperators.EQ, 
							GF2.constant(0)));
			
			// pre are satisfied
			for (Node n : e.getPre()) {
				// pre must be 1 for all pre
				guard = GF2.and(guard,  
							GF2.createComparison(
								GF2.createVariableRef(nodeMap.get(n)),
								ComparisonOperators.EQ, 
								GF2.constant(1)));
			}

			// at least one post is 0
			BooleanExpression postZero = GF.createFalse();
			for (Node n : e.getPost()) {
				postZero = GF2.or(postZero, GF2.createComparison(GF2.createVariableRef(nodeMap.get(n)),
						ComparisonOperators.EQ, GF2.constant(0)));
			}
			// add to guard
			guard = GF2.and(guard, postZero);

			t.setGuard(guard);
			for (Node n : e.getPost()) {
				Assignment setOne = GF.createAssignment();
				setOne.setLeft(GF2.createVariableRef(nodeMap.get(n)));
				setOne.setRight(GF2.constant(1));
				t.getActions().add(setOne );
			}
			t.getActions().add(GF2.createAssignment(GF2.createVariableRef(v), GF2.constant(1)));
		}

		// add the property
		Property prop = GF.createProperty();
		prop.setName("goal");
		
		ReachableProp p = GF.createReachableProp();
		BooleanExpression goalReached = GF.createTrue();
		for (Node n : attack.getGoal()) {
			goalReached = GF2.and(goalReached, GF2.createComparison(GF2.createVariableRef(nodeMap.get(n)),
					ComparisonOperators.EQ, GF2.constant(1)));
		}
		p.setPredicate(goalReached);
		prop.setBody(p);
		
		
		Specification spec = GF.createSpecification();
		spec.getTypes().add(gal);
		spec.getProperties().add(prop);
		return spec;
	}

}


