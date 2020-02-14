package fr.lip6.move.gal.logic.togal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.AF;
import fr.lip6.move.gal.AG;
import fr.lip6.move.gal.AU;
import fr.lip6.move.gal.AX;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.CTLProp;
import fr.lip6.move.gal.EF;
import fr.lip6.move.gal.EG;
import fr.lip6.move.gal.EU;
import fr.lip6.move.gal.EX;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.NamedDeclaration;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.instantiate.Simplifier;
import fr.lip6.move.gal.logic.*;
import fr.lip6.move.gal.support.Support;
import fr.lip6.move.serialization.SerializationUtil;

public class ToGalTransformer {

	Set<Variable> constvars;
	Map<ArrayPrefix, Set<Integer>> constantArrs;
	Set<NamedDeclaration> dontremove;
	Map<String,fr.lip6.move.gal.BooleanExpression> cache = new HashMap<>();
	private ToGalTransformer(Specification spec) {
		if (spec.getMain() instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) spec.getMain();
			constvars = new HashSet<Variable>(gal.getVariables());
			constantArrs = new HashMap<ArrayPrefix, Set<Integer>>();
			dontremove = new HashSet<NamedDeclaration>();
			int totalVars = Simplifier.computeConstants(gal, constvars, constantArrs, dontremove, new Support());
		}
	}

	public static Specification toGal (Properties props) {
		if (! props.getProps().isEmpty()) {
			if (props.getProps().get(0).getName().contains("ReachabilityFireability")) {
				ToLogicNG tlg = new ToLogicNG();
				tlg.simplify(props);
			}
		}
		Specification spec = (Specification) props.getSystem().eContainer();
		ToGalTransformer tg = new ToGalTransformer(spec);
		for (PropertyDesc prop : props.getProps()) {
			spec.getProperties().add(tg.toGal(prop));
		}		
		return spec;
	}

	private fr.lip6.move.gal.Property toGal (PropertyDesc pdesc) {
		fr.lip6.move.gal.Property prop =GalFactory.eINSTANCE.createProperty();

		prop.setName(pdesc.getName());

		// reachability ?
		Property pb = pdesc.getProp();
		if (pb instanceof LogicProp) {
			LogicProp pbody = (LogicProp) pb;
			SafetyProp lprop = null;
			if (isCTL(pbody.getFormula()) && (pdesc.getName().contains("CTL")|| 
					(((LogicProp) pb).getFormula() instanceof Ef) && ((Ef) ((LogicProp) pb).getFormula()).getForm() instanceof Deadlock)) {
				CTLProp ctlprop = GalFactory.eINSTANCE.createCTLProp();
				ctlprop.setPredicate(toGal(pbody.getFormula()));	
				prop.setBody(ctlprop);
			} else if (pdesc.getName().contains("LTL")) {
					fr.lip6.move.gal.LTLProp ltlprop = GalFactory.eINSTANCE.createLTLProp();
					ltlprop.setPredicate(toGal(pbody.getFormula()));	
					prop.setBody(ltlprop);
			} else if (pbody.getFormula() instanceof Ef  && hasNoTemporal(((Ef)pbody.getFormula()).getForm())) {
				Ef ef = (Ef) pbody.getFormula();
				lprop = GalFactory.eINSTANCE.createReachableProp();
				lprop.setPredicate(toGal(ef.getForm()));
			} else if (pbody.getFormula() instanceof Ag  && hasNoTemporal(((Ag)pbody.getFormula()).getForm())) {
				Ag ag = (Ag) pbody.getFormula();
				lprop = GalFactory.eINSTANCE.createInvariantProp();
				lprop.setPredicate(toGal(ag.getForm()));
			} else if (pbody.getFormula() instanceof True ) {
				lprop = GalFactory.eINSTANCE.createReachableProp();
				lprop.setPredicate(GalFactory.eINSTANCE.createTrue());
			} else if (pbody.getFormula() instanceof False ) {
				lprop = GalFactory.eINSTANCE.createReachableProp();
				lprop.setPredicate(GalFactory.eINSTANCE.createFalse());
			} 
			if (lprop != null)  
				prop.setBody(lprop);

		} else if (pb instanceof BoundsProp) {
			BoundsProp bprop = (BoundsProp) pb;
			fr.lip6.move.gal.BoundsProp tbp = GalFactory.eINSTANCE.createBoundsProp();
			tbp.setTarget(toGal(bprop.getTarget()));
			prop.setBody(tbp);
		} 
		
		if (prop.getBody() == null) {
			getLog().warning("Could not translate property :" + pdesc.getName());
			ReachableProp lprop = GalFactory.eINSTANCE.createReachableProp();
			lprop.setPredicate(GalFactory.eINSTANCE.createTrue());
			prop.setBody(lprop );
		}
		return prop;
	}

	private static boolean isCTL(BooleanExpression formula) {		
		return true;
	}

	private static Logger log = Logger.getLogger("fr.lip6.move.gal"); 
	private static Logger getLog() {
		return log;
	}

	private static boolean hasNoTemporal(BooleanExpression form) {
		if (isTemporal(form)) {
			return false;
		}

		for (TreeIterator<EObject> it = form.eAllContents() ; it.hasNext(); ) {
			EObject obj = it.next();
			if (isTemporal(obj)) {
				return false;
			}
			if (obj instanceof Comparison) {
				it.prune();
			}
		}
		return true;
	}

	private static boolean isTemporal(EObject obj) {
		return obj instanceof Ef || obj instanceof Eg 
				|| obj instanceof Af || obj instanceof Ag 
				|| obj instanceof Ax || obj instanceof Ex 
				|| obj instanceof Eu || obj instanceof Au;
	}

	private fr.lip6.move.gal.BooleanExpression toGal(
			BooleanExpression obj) {
		if (obj instanceof And) {
			And and = (And) obj;
			return GF2.and(toGal(and.getLeft()), toGal(and.getRight()));
		} else if (obj instanceof Or) {
			Or or = (Or) obj;
			return GF2.or(toGal(or.getLeft()), toGal(or.getRight()));
		} else if (obj instanceof Not) {
			Not not = (Not) obj;
			return GF2.not(toGal(not.getValue()));
		} else if (obj instanceof Comparison) {
			Comparison cmp = (Comparison) obj;
			return GF2.createComparison(toGal(cmp.getLeft()), toGal(cmp.getOperator()), toGal(cmp.getRight()));
		} else if (obj instanceof True) {
			return GalFactory.eINSTANCE.createTrue();
		} else if (obj instanceof False) {
			return GalFactory.eINSTANCE.createFalse();
		} else if (obj instanceof Enabling) {
			Enabling enab = (Enabling) obj;
			Set<String> seen = new HashSet<>();
			int reduced=0;
			fr.lip6.move.gal.BooleanExpression res = GalFactory.eINSTANCE.createFalse();
			for (Transition tr : enab.getTrans()) {
				if (withAbstractColors) {
					Transition tcopy = EcoreUtil.copy(tr);
					Instantiator.abstractArraystoSingleCell(tcopy);
					res = tcopy.getGuard();
				} else {
					fr.lip6.move.gal.BooleanExpression be = cache.get(tr.getName());
					if (be == null) {
						java.util.List<Transition> inst = Instantiator
								.instantiateParameters(tr,constvars, constantArrs);
						be = GalFactory.eINSTANCE.createFalse();
						Set<String> seen2 = new HashSet<>();
						for (Transition t : inst) {
							fr.lip6.move.gal.BooleanExpression g = t.getGuard();
							String strg = SerializationUtil.getText(g, true);
							if (! seen2.contains(strg)) {								
								be = GF2.or(be,EcoreUtil.copy(g));
								seen2.add(strg);
							} else {
								reduced++;
							}
						}
						cache.put(tr.getName(),be);
					} 
					String strg = SerializationUtil.getText(be, true);
					if (! seen.contains(strg)) {
						res = GF2.or(res,EcoreUtil.copy(be));
						seen.add(strg);
					} else {
						reduced++;
					}					
				}
			}
			if (reduced >0) {
				getLog().info("Removed "+reduced+" identically guarded transitions from multi-enabling predicate. This is usually due to expansion of symmetric bindings from a colored net.");
			}
			List<EObject> totrue = new ArrayList<EObject>();
			for (TreeIterator<EObject> it = res.eAllContents() ; it.hasNext() ; ) {
				EObject elt = it.next();
				if (elt instanceof fr.lip6.move.gal.Comparison) {
					fr.lip6.move.gal.Comparison cmp = (fr.lip6.move.gal.Comparison) elt;
					if ( cmp.getOperator() == fr.lip6.move.gal.ComparisonOperators.GE && cmp.getLeft() instanceof fr.lip6.move.gal.VariableReference && cmp.getRight() instanceof fr.lip6.move.gal.Constant && ((fr.lip6.move.gal.Constant) cmp.getRight()).getValue() == 0) {
						totrue.add(cmp);
						it.prune();
					} else {
						it.prune();
					}
				}
			}
			for (EObject o : totrue) {
				EcoreUtil.replace(o, GalFactory.eINSTANCE.createTrue());
			}
			// just a dirty trick to ensure we can get the result of simplify we need a context.
			fr.lip6.move.gal.Not not = GalFactory.eINSTANCE.createNot();
			not.setValue(res);
			Simplifier.simplify(res);
			res = not.getValue();
			return res ;
		} else if (obj instanceof Au) {
			Au au = (Au) obj;
			AU res = GalFactory.eINSTANCE.createAU();
			res.setLeft(toGal(au.getLeft()));
			res.setRight(toGal(au.getRight()));
			return res;
		} else if (obj instanceof Eu) {
			Eu au = (Eu) obj;
			EU res = GalFactory.eINSTANCE.createEU();
			res.setLeft(toGal(au.getLeft()));
			res.setRight(toGal(au.getRight()));
			return res;
		} else if (obj instanceof Ag) {
			Ag au = (Ag) obj;
			AG res = GalFactory.eINSTANCE.createAG();
			res.setProp(toGal(au.getForm()));
			return res;
		} else if (obj instanceof Af) {
			Af au = (Af) obj;
			AF res = GalFactory.eINSTANCE.createAF();
			res.setProp(toGal(au.getForm()));
			return res;
		} else if (obj instanceof Ax) {
			Ax au = (Ax) obj;
			AX res = GalFactory.eINSTANCE.createAX();
			res.setProp(toGal(au.getForm()));
			return res;
		} else if (obj instanceof Ex) {
			Ex au = (Ex) obj;
			EX res = GalFactory.eINSTANCE.createEX();
			res.setProp(toGal(au.getForm()));
			return res;
		} else if (obj instanceof Eg) {
			Eg au = (Eg) obj;
			EG res = GalFactory.eINSTANCE.createEG();
			res.setProp(toGal(au.getForm()));
			return res;
		} else if (obj instanceof Ef) {
			Ef au = (Ef) obj;
			EF res = GalFactory.eINSTANCE.createEF();
			res.setProp(toGal(au.getForm()));
			return res;
		} else if (obj instanceof LTLFuture) {
			LTLFuture au = (LTLFuture) obj;
			fr.lip6.move.gal.LTLFuture res = GalFactory.eINSTANCE.createLTLFuture();
			res.setProp(toGal(au.getProp()));
			return res;
		} else if (obj instanceof LTLGlobally) {
			LTLGlobally au = (LTLGlobally) obj;
			fr.lip6.move.gal.LTLGlobally res = GalFactory.eINSTANCE.createLTLGlobally();
			res.setProp(toGal(au.getProp()));
			return res;
		} else if (obj instanceof LTLNext) {
			LTLNext au = (LTLNext) obj;
			fr.lip6.move.gal.LTLNext res = GalFactory.eINSTANCE.createLTLNext();
			res.setProp(toGal(au.getProp()));
			return res;
		} else if (obj instanceof LTLUntil) {
			LTLUntil au = (LTLUntil) obj;
			fr.lip6.move.gal.LTLUntil res = GalFactory.eINSTANCE.createLTLUntil();
			res.setLeft(toGal(au.getLeft()));
			res.setRight(toGal(au.getRight()));
			return res;
		} else if (obj instanceof Deadlock) {
			fr.lip6.move.gal.BooleanExpression t = GalFactory.eINSTANCE.createTrue();
			EX EX = GalFactory.eINSTANCE.createEX();
			EX.setProp(t);
			return GF2.not(EX);
		} else {
			getLog().warning("Unknown predicate type in boolean expression "
					+ obj.getClass().getName());
		}
		return GalFactory.eINSTANCE.createTrue();
	}

	private static fr.lip6.move.gal.ComparisonOperators toGal(
			ComparisonOperators operator) {
		switch (operator) {
		case EQ:
			return fr.lip6.move.gal.ComparisonOperators.EQ;
		case NE:
			return fr.lip6.move.gal.ComparisonOperators.NE;
		case GT:
			return fr.lip6.move.gal.ComparisonOperators.GT;
		case GE:
			return fr.lip6.move.gal.ComparisonOperators.GE;
		case LT:
			return fr.lip6.move.gal.ComparisonOperators.LT;
		case LE:
			return fr.lip6.move.gal.ComparisonOperators.LE;
		default:
			getLog().warning("Unknown operator in comparison !");
			return fr.lip6.move.gal.ComparisonOperators.EQ;
		}
	}



	private static fr.lip6.move.gal.IntExpression toGal(IntExpression e) {
		if (e instanceof BinaryIntExpression) {
			BinaryIntExpression bin = (BinaryIntExpression) e;
			return GF2.createBinaryIntExpression(toGal(bin.getLeft()), bin.getOp(), toGal(bin.getRight()));
		} else if (e instanceof VariableRef) {
			VariableRef vr = (VariableRef) e;
			return GF2.createVariableRef(vr.getReferencedVar());
		} else if (e instanceof ArrayVarAccess) {
			ArrayVarAccess ava = (ArrayVarAccess) e;
			return GF2.createArrayVarAccess(ava.getPrefix(), toGal(ava.getIndex()));
		} else if (e instanceof Constant) {
			Constant c = (Constant) e;
			return GF2.constant(c.getValue());
		} else if (e instanceof CardMarking) {
			CardMarking cm = (CardMarking) e;
			EList<VarDecl> places = cm.getPlaces();
			fr.lip6.move.gal.IntExpression sum = toGalPlaceSum(places);
			return sum;			
		} else if (e instanceof BoundsMarking) {
			BoundsMarking cm = (BoundsMarking) e;
			fr.lip6.move.gal.IntExpression sum = toGalPlaceSum(cm.getPlaces());
			return sum;			
		} else {
			getLog().warning("Unknown type in integer toGal for expression "
					+ e.getClass().getName());
		}

		return GF2.constant(0);
	}

	private static fr.lip6.move.gal.IntExpression toGalPlaceSum(List<VarDecl> list) {
		fr.lip6.move.gal.IntExpression sum = null;
		for (VarDecl place : list) {
			fr.lip6.move.gal.IntExpression cur=null;
			if (place instanceof Variable) {
				Variable pl = (Variable) place;
				VariableReference vl = GF2.createVariableRef(pl);
				cur = vl;
			} else if (place instanceof ArrayPrefix) {
				ArrayPrefix ap = (ArrayPrefix) place;
				cur = createSumOfArray(ap);
			}
			if (sum == null) {
				sum = cur;
			} else {
				fr.lip6.move.gal.IntExpression add = GF2.createBinaryIntExpression(sum, "+", cur);
				sum = add;
			}
		}
		return sum;
	}

	private static boolean withAbstractColors = false;
	public static void setWithAbstractColors(boolean withAbstractColors) {
		ToGalTransformer.withAbstractColors = withAbstractColors;
	}
	private static fr.lip6.move.gal.IntExpression createSumOfArray(ArrayPrefix l) {
		if (withAbstractColors) {
			return  GF2.createArrayVarAccess(l, GF2.constant(0));
		} else {
			fr.lip6.move.gal.IntExpression sum = null;
			for (int i = 0; i < ((fr.lip6.move.gal.Constant) l.getSize()).getValue(); i++) {
				VariableReference av = GF2.createArrayVarAccess(l, GF2.constant(i));
				if (sum == null) {
					sum = av;
				} else {
					fr.lip6.move.gal.IntExpression bin = GF2.createBinaryIntExpression(sum, "+", av);
					sum = bin;
				}
			}
			return sum;
		}
	}
}

