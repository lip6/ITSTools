package fr.lip6.move.gal.application;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.BoundsProp;
import fr.lip6.move.gal.CTLProp;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.LTLProp;
import fr.lip6.move.gal.LogicProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.instantiate.Simplifier;
import fr.lip6.move.gal.mcc.properties.DoneProperties;

public class GALSolver {

	// utility class
	private GALSolver() {}
	/**
	 * Structural analysis and reduction : test in initial state.
	 * @param specWithProps spec which will be modified : trivial properties will be removed
	 * @param doneProps 
	 */
	public static int checkInInitial(Specification specWithProps, DoneProperties doneProps, boolean isSafe) {
		List<Property> props = new ArrayList<Property>(specWithProps.getProperties());
		int done = 0;
		Simplifier.deepEquals = false;
		// iterate down so indexes are consistent
		for (int i = props.size()-1; i >= 0 ; i--) {
			Property propp = props.get(i);
	
			if (doneProps.containsKey(propp.getName())) {
				specWithProps.getProperties().remove(i);
				continue;
			}
			if (isSafe) {
				for (TreeIterator<EObject> ti = propp.getBody().eAllContents() ; ti.hasNext() ; ) {
					EObject obj = ti.next();
					if (obj instanceof Comparison) {
						Comparison cmp = (Comparison) obj;
						
						if (cmp.getLeft() instanceof Reference && cmp.getRight() instanceof Constant) {
							int val = ((Constant) cmp.getRight()).getValue();
							if (   ( val > 1 && ( cmp.getOperator() == ComparisonOperators.LE || cmp.getOperator() == ComparisonOperators.LT)) 
									||
									( val == 1 && cmp.getOperator() == ComparisonOperators.LE )
									) {
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createTrue());
								ti.prune();
							} else if (val > 1 || (val == 1 && cmp.getOperator() == ComparisonOperators.GT) ) {
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createFalse());
								ti.prune();
							}
						} else if (cmp.getRight() instanceof Reference && cmp.getLeft() instanceof Constant) {
							int val = ((Constant) cmp.getLeft()).getValue();
							if (   ( val > 1 && ( cmp.getOperator() == ComparisonOperators.GE || cmp.getOperator() == ComparisonOperators.GT)) 
									||
									( val == 1 && cmp.getOperator() == ComparisonOperators.GE )
									) {
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createTrue());
								ti.prune();
							} else if (val > 1 || (val == 1 && cmp.getOperator() == ComparisonOperators.LT) ) {
								EcoreUtil.replace(cmp, GalFactory.eINSTANCE.createFalse());
								ti.prune();
							}
						}
					}
				}
			}
			LogicProp prop = propp.getBody();
			Simplifier.simplifyAllExpressions(prop);
			Simplifier.simplifyProperties(specWithProps);
			
			String tech = "TOPOLOGICAL INITIAL_STATE";
			boolean solved = false;
			// output verdict
			if (prop instanceof ReachableProp || prop instanceof InvariantProp) {
	
				if (((SafetyProp) prop).getPredicate() instanceof True) {
					// positive forms : EF True , AG True <=>True
					doneProps.put(propp.getName(), true, tech);
					solved = true;
				} else if (((SafetyProp) prop).getPredicate() instanceof False) {
					// positive forms : EF False , AG False <=> False
					doneProps.put(propp.getName(), false, tech);
					solved = true;
				}
			} else if (prop instanceof NeverProp) {
				if (((SafetyProp) prop).getPredicate() instanceof True) {
					// negative form : ! EF P = AG ! P, so ! EF True <=> False
					doneProps.put(propp.getName(), false, tech);
					solved = true;
				} else if (((SafetyProp) prop).getPredicate() instanceof False) {
					// negative form : ! EF P = AG ! P, so ! EF False <=> True
					doneProps.put(propp.getName(), true, tech);
					solved = true;
				}
			} else if (prop instanceof LTLProp) {
				LTLProp ltl = (LTLProp) prop;
				if (ltl.getPredicate() instanceof True) {
					// positive forms : EF True , AG True <=>True
					doneProps.put(propp.getName(), true, tech);
					solved = true;
				} else if (ltl.getPredicate() instanceof False)  {
					// positive forms : EF False , AG False <=> False
					doneProps.put(propp.getName(), false, tech);
					solved = true;
				}
			} else if (prop instanceof CTLProp) {
				CTLProp ltl = (CTLProp) prop;
				if (ltl.getPredicate() instanceof True) {
					// positive forms : EF True , AG True <=>True
					doneProps.put(propp.getName(), true, tech);
					solved = true;
				} else if (ltl.getPredicate() instanceof False)  {
					// positive forms : EF False , AG False <=> False
					doneProps.put(propp.getName(), false, tech);
					solved = true;
				}
			} else if (prop instanceof BoundsProp) {
				BoundsProp bp = (BoundsProp) prop;
				if (bp.getTarget() instanceof Constant) {
					doneProps.put(propp.getName(),((Constant) bp.getTarget()).getValue(),tech);
					solved = true;
				}				
			}
	
			if (solved) {				
				// discard property
				specWithProps.getProperties().remove(i);
				done++;
			}
	
		}
		Simplifier.deepEquals = true;
		return done;
	}

	public static int runGALReductions(MccTranslator reader, DoneProperties doneProps) {
		int solved=0;
		boolean isSafe = reader.getSPN().isSafe();
		Simplifier.deepEquals = false;
		reader.rebuildSpecification(doneProps);
		solved += checkInInitial(reader.getSpec(), doneProps, isSafe);		
		reader.flattenSpec(false);
		Simplifier.simplify(reader.getSpec());
		solved += checkInInitial(reader.getSpec(), doneProps, isSafe);
		Simplifier.deepEquals = true;
		reader.rebuildSPN();
		reader.getSPN().setSafe(isSafe);
		return solved;
	}

}
