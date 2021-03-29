package fr.lip6.move.gal.instantiate;

import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.And;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.LTLFuture;
import fr.lip6.move.gal.LTLGlobally;
import fr.lip6.move.gal.LTLNext;
import fr.lip6.move.gal.LTLProp;
import fr.lip6.move.gal.LTLUntil;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.True;

/** Implement rules to simplify LTL formula
 * 
 * Algorithm taken from : David Déharbe, Anamaria Martins Moreira, Christophe Ringeissen:
 * Improving Symbolic Model Checking by Rewriting Temporal Logic Formulae. RTA 2002: 207-221
 * 
 * @author ythierry
 *
 */
public class LTLSimplifier {

	public static void simplifyTemporal(Specification spec) {
		int simplified = 0;
		for (Property prop: spec.getProperties()) {
			if (prop.getBody() instanceof LTLProp) {
				LTLProp body = (LTLProp) prop.getBody();
				if (body.getPredicate() instanceof True || body.getPredicate() instanceof False) {
					continue;
				}
				simplify(body.getPredicate());
				
				int eval = evalInInitial(body.getPredicate());
				if (eval == 1) {
					body.setPredicate(GalFactory.eINSTANCE.createTrue());
				} else if (eval == -1) {
					body.setPredicate(GalFactory.eINSTANCE.createFalse());
				}
				if (eval != 0) {
					simplified++;
				}
			}
		}
		if (simplified != 0) {
			Logger.getLogger("fr.lip6.move.gal").info("Initial state reduction rules for LTL removed "+simplified+ " formulas.");
		}
	}
	
	
	/** 
	 * This function is an adaptation to LTL of the Initial states simplification strategy proposed in Section 3 
	 * of the paper : Simplification of CTL Formulae for Efficient Model Checking of Petri Nets
	 * Published at IC PetriNets'2018
	 * By Bonneland, et al. of Jiri Srba's group working on Tapaal.
	 */
	private static int evalInInitial (BooleanExpression predicate) {
		if (predicate instanceof True) {
			return 1 ;
		} else if (predicate instanceof False) {
			return -1;
		} else if (predicate instanceof Comparison) {
			if (PropertySimplifier.evalInInitialState(predicate)) {
				return 1;
			} else {
				return -1;
			}
		} else if (predicate instanceof LTLNext) {
			return 0;
		} else if (predicate instanceof Not) {
			Not n = (Not) predicate;
			return - evalInInitial(n.getValue());
		} else if (predicate instanceof And) {
			And and = (And) predicate;
			
			int l = evalInInitial(and.getLeft());
			int r = evalInInitial(and.getRight());
			
			if (l==-1 || r==-1) {
				return -1;
			} else if (l==1 && r==1) {
				return 1;
			} else {
				return 0;
			}
		} else if (predicate instanceof Or) {
			Or or = (Or) predicate;
			
			int l = evalInInitial(or.getLeft());
			int r = evalInInitial(or.getRight());
			
			if (l==1 || r==1) {
				return 1;
			} else if (l==-1 && r==-1) {
				return -1;
			} else {
				return 0;
			}
		} else if (predicate instanceof LTLGlobally) {
			LTLGlobally op = (LTLGlobally) predicate;
			if (evalInInitial(op.getProp()) == -1) {
				return -1;
			} else {
				return 0;
			}
		} else if (predicate instanceof LTLFuture) {
			LTLFuture op = (LTLFuture) predicate;
			if (evalInInitial(op.getProp()) == 1) {
				return 1;
			} else {
				return 0;
			}
		} else if (predicate instanceof LTLUntil) {
			LTLUntil op = (LTLUntil) predicate;
			int evalr = evalInInitial(op.getRight());
			if (evalr == 1) {
				return 1;
			} else if (evalr == -1) {
				if (evalInInitial(op.getLeft()) == -1) {
					return -1;
				}
			}
			return 0;			
		}
		Logger.getLogger("fr.lip6.move.gal").warning("Unexpected operator in LTL formula :" + predicate);
		return 0;		
	}

	private static void simplify(BooleanExpression predicate) {
		if (predicate==null) {
			// this should not happen ?
			return;
		} else if (predicate instanceof And) {
			And and = (And) predicate;
			simplify(and.getLeft());
			simplify(and.getRight());
		} else if (predicate instanceof Or) {
			Or or = (Or) predicate;
			simplify(or.getLeft());
			simplify(or.getRight());
		} else if (predicate instanceof LTLGlobally) {
			LTLGlobally ag = (LTLGlobally) predicate;
			simplify(ag.getProp());			
			if (ag.getProp() instanceof False || ag.getProp() instanceof True) {
				// G false = false,
				// G true =  true
				EcoreUtil.replace(predicate, ag.getProp());
			}			
		} else if (predicate instanceof LTLFuture) {
			LTLFuture ef = (LTLFuture) predicate;
			simplify(ef.getProp());
			
			if (ef.getProp() instanceof False || ef.getProp() instanceof True) {
				// F false = false ; F true = true
				EcoreUtil.replace(predicate, ef.getProp());
			} 
			
		} else if (predicate instanceof LTLNext) {
			LTLNext ef = (LTLNext) predicate;
			simplify(ef.getProp());
			
			if (ef.getProp() instanceof False || ef.getProp() instanceof True) {
				// X false = false ; X true = true
				EcoreUtil.replace(predicate, ef.getProp());
			}
		} else if (predicate instanceof LTLUntil) {
			LTLUntil ef = (LTLUntil) predicate;
			simplify(ef.getLeft());			
			simplify(ef.getRight());
			if (ef.getRight() instanceof True || ef.getRight() instanceof False) {
				// a U False = False ; a U True = True
				EcoreUtil.replace(predicate, ef.getRight());
			} else if (ef.getLeft() instanceof True) {
				// True U a = F a
				LTLFuture fut = GalFactory.eINSTANCE.createLTLFuture();
				fut.setProp(ef.getRight());
				EcoreUtil.replace(predicate, fut);
				simplify(fut);
			} else if (ef.getLeft() instanceof False) {
				// False U a = a
				EcoreUtil.replace(predicate, ef.getRight());								
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
