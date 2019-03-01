package fr.lip6.move.gal.instantiate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.AF;
import fr.lip6.move.gal.AG;
import fr.lip6.move.gal.AU;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.CTLProp;
import fr.lip6.move.gal.EF;
import fr.lip6.move.gal.EG;
import fr.lip6.move.gal.EU;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.True;

/** Implement rules to reduce number of temporal operators in formula
 * 
 * Algorithm taken from : David Déharbe, Anamaria Martins Moreira, Christophe Ringeissen:
 * Improving Symbolic Model Checking by Rewriting Temporal Logic Formulae. RTA 2002: 207-221
 * 
 * @author ythierry
 *
 */
public class CTLSimplifier {

	public static void simplifyTemporal(Specification spec) {
		for (Property prop: spec.getProperties()) {
			if (prop.getBody() instanceof CTLProp) {
				CTLProp body = (CTLProp) prop.getBody();
				simplify(body.getPredicate());
			}
		}
	}

	private static void simplify(BooleanExpression predicate) {
		if (predicate instanceof And) {
			And and = (And) predicate;
			simplify(and.getLeft());
			simplify(and.getRight());
			
			// AG f * AG g -> AG f* g
			if (and.getLeft() instanceof AG && and.getRight() instanceof AG) {
				AG ag = GalFactory.eINSTANCE.createAG();
				ag.setProp(GF2.and(((AG) and.getLeft()).getProp(), ((AG) and.getRight()).getProp()));
				EcoreUtil.replace(predicate, ag);
			}

		} else if (predicate instanceof Or) {
			Or or = (Or) predicate;
			simplify(or.getLeft());
			simplify(or.getRight());

			// EF f + EF g -> EF f + g
			if (or.getLeft() instanceof EF && or.getRight() instanceof EF) {
				EF ef = GalFactory.eINSTANCE.createEF();
				ef.setProp(GF2.or(((EF) or.getLeft()).getProp(), ((EF) or.getRight()).getProp()));
				EcoreUtil.replace(predicate, ef);
			}
		} else if (predicate instanceof AG) {
			AG ag = (AG) predicate;
			simplify(ag.getProp());
			
			if (ag.getProp() instanceof False) {
				// AG false = false
				EcoreUtil.replace(predicate, ag.getProp());
			} else if (ag.getProp() instanceof AG) {
				// AG AG f -> AG f
				ag.setProp(((AG) ag.getProp()).getProp());		
			} else if (ag.getProp() instanceof EG) {
				// AG EG f -> AG f
				ag.setProp(((EG) ag.getProp()).getProp());		
			} else if (ag.getProp() instanceof AF) {
				if (((AF) ag.getProp()).getProp() instanceof AG) {
					// AG AF AG f -> AF AG f
					EcoreUtil.replace(predicate, ag.getProp());
				} else if (((AF) ag.getProp()).getProp() instanceof EF) {
					EF ef = (EF) ((AF) ag.getProp()).getProp();
					// AG AF EF AG EF f -> AF EF AG EF f
					if (ef.getProp() instanceof AG && ((AG) ef.getProp()).getProp() instanceof EF) {
						EcoreUtil.replace(predicate, ag.getProp());
					}
				}
			} else if (ag.getProp() instanceof EF && ((EF) ag.getProp()).getProp() instanceof AG && ((AG) ((EF) ag.getProp()).getProp()).getProp() instanceof EF) {
				// AG EF AG EF f -> EF AG EF f 
				EcoreUtil.replace(predicate, ag.getProp());
			}
			
		} else if (predicate instanceof EF) {
			EF ef = (EF) predicate;
			simplify(ef.getProp());
			
			if (ef.getProp() instanceof False || ef.getProp() instanceof True) {
				// EF false = false ; EF true = true
				EcoreUtil.replace(predicate, ef.getProp());
			} else if (ef.getProp() instanceof EF) {
				// EF EF f -> EF f
				ef.setProp(((EF) ef.getProp()).getProp());		
			} else if (ef.getProp() instanceof AF) {
				// EF AF f -> EF f
				ef.setProp(((AF) ef.getProp()).getProp());
			} else if (ef.getProp() instanceof EU) {
				// EF E f U g -> EF g 
				EcoreUtil.replace(predicate, ((EU)ef.getProp()).getRight());
			} else if (ef.getProp() instanceof AU) {
				// EF A f U g -> EF g 
				EcoreUtil.replace(predicate, ((AU)ef.getProp()).getRight());
			} else if (ef.getProp() instanceof EG) {
				if (((EG) ef.getProp()).getProp() instanceof EF) {
					// EF EG EF f -> EG EF f
					EcoreUtil.replace(predicate, ef.getProp());								
				} else if (((EG) ef.getProp()).getProp() instanceof AG) {
					AG ag = (AG) ((EG) ef.getProp()).getProp();
					// EF EG AG EF AG f -> EG AG EF AG f
					if (ag.getProp() instanceof EF && ((EF) ag.getProp()).getProp() instanceof AG) {
						EcoreUtil.replace(predicate, ef.getProp());
					}
				}
			} else if (ef.getProp() instanceof AG && ((AG) ef.getProp()).getProp() instanceof EF && ((EF) ((AG) ef.getProp()).getProp()).getProp() instanceof AG) {
				// EF AG EF AG f -> AG EF AG f 
				EcoreUtil.replace(predicate, ef.getProp());
			}	
		} else if (predicate instanceof EG) {
			EG eg = (EG) predicate;
			simplify(eg.getProp());
			
			if (eg.getProp() instanceof False) {
				// EG false = false
				EcoreUtil.replace(predicate, eg.getProp());
			} else if (eg.getProp() instanceof EG) {
				// EG EG f -> EG f
				eg.setProp(((EG) eg.getProp()).getProp());		
			} else if (eg.getProp() instanceof AF && ((AF) eg.getProp()).getProp() instanceof EG) {
				// EG AF EG f -> AF EG f
				EcoreUtil.replace(predicate, eg.getProp());
			} else if (eg.getProp() instanceof EF && ((EF) eg.getProp()).getProp() instanceof EG) {
				// EG EF EG f -> EF EG f
				EcoreUtil.replace(predicate, eg.getProp());			
			} else if (eg.getProp() instanceof AF && ((AF) eg.getProp()).getProp() instanceof EF && ((EF) ((AF) eg.getProp()).getProp()).getProp() instanceof EG) {
				// EG AF EF EG f -> AF EF EG f 
				EcoreUtil.replace(predicate, eg.getProp());
			}	
		} else if (predicate instanceof AF) {
			AF af = (AF) predicate;
			simplify(af.getProp());
			
			if (af.getProp() instanceof False || af.getProp() instanceof True) {
				// AF false = false ; AF true = true
				EcoreUtil.replace(predicate, af.getProp());
			} else if (af.getProp() instanceof AF) {
				// AF AF f -> AF f
				af.setProp(((AF) af.getProp()).getProp());		
			} else if (af.getProp() instanceof EF) {
				// AF EF f -> EF f
				EcoreUtil.replace(predicate, af.getProp());
			} else if (af.getProp() instanceof AU) {
				// AF A f U g  -> AF g				
				af.setProp(((AU) af.getProp()).getRight());						
			} else if (af.getProp() instanceof EG && ((EG) af.getProp()).getProp() instanceof AF) {
				// AF EG AF f -> EG AF f
				EcoreUtil.replace(predicate, af.getProp());
			} else if (af.getProp() instanceof EF && ((EF) af.getProp()).getProp() instanceof EG) {
				// AF AG AF f -> AG AF f
				EcoreUtil.replace(predicate, af.getProp());			
			} else if (af.getProp() instanceof EG && ((EG) af.getProp()).getProp() instanceof AG && ((AG) ((EG) af.getProp()).getProp()).getProp() instanceof AF) {
				// AF EG AG AF f -> EG AG AF f 
				EcoreUtil.replace(predicate, af.getProp());
			}
		} else {
			for (EObject o : predicate.eContents()) {
				if (o instanceof BooleanExpression) {
					simplify((BooleanExpression) o);
				}
			}
		}
	}

	
	
}
