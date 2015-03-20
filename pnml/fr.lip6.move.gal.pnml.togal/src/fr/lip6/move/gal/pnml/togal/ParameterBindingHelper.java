package fr.lip6.move.gal.pnml.togal;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.pnml.togal.utils.HLUtils;
import fr.lip6.move.pnml.symmetricnet.cyclicEnumerations.Predecessor;
import fr.lip6.move.pnml.symmetricnet.cyclicEnumerations.Successor;
import fr.lip6.move.pnml.symmetricnet.dots.DotConstant;
import fr.lip6.move.pnml.symmetricnet.finiteEnumerations.FEConstant;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.Arc;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.Declaration;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.Page;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.PetriNet;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.Place;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.PnObject;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.Transition;
import fr.lip6.move.pnml.symmetricnet.multisets.Add;
import fr.lip6.move.pnml.symmetricnet.multisets.All;
import fr.lip6.move.pnml.symmetricnet.multisets.NumberOf;
import fr.lip6.move.pnml.symmetricnet.multisets.Subtract;
import fr.lip6.move.pnml.symmetricnet.terms.NamedSort;
import fr.lip6.move.pnml.symmetricnet.terms.Sort;
import fr.lip6.move.pnml.symmetricnet.terms.Term;
import fr.lip6.move.pnml.symmetricnet.terms.TermsDeclaration;
import fr.lip6.move.pnml.symmetricnet.terms.Tuple;
import fr.lip6.move.pnml.symmetricnet.terms.UserOperator;


public class ParameterBindingHelper {

	
	
	/**
	 * Compute a sufficient condition for token conservativeness over the given color sort of the transition t.
	 * @param t
	 * @param sort
	 * @return true if the number of tokens of 'sort' type is constant when firing t
	 */
	public static boolean isConservative (Transition t, Sort sort ) {
		Map<String, Integer> values = new HashMap<String, Integer>();
		
		Map<String, Integer> total = new HashMap<String, Integer>();
		for (Arc arc : t.getInArcs()) {
			Place pl = (Place) arc.getSource();
			
			Map<String, Integer> refPl = buildRefsFromArc(arc.getHlinscription().getStructure(), pl.getType().getStructure(), sort );
			for (Entry<String, Integer> entry : refPl.entrySet()) {
				add(total, entry.getKey(), entry.getValue());
			}
		}
		for (Arc arc : t.getOutArcs()) {
			Place pl = (Place) arc.getTarget();
			
			Map<String, Integer> refPl = buildRefsFromArc(arc.getHlinscription().getStructure(), pl.getType().getStructure(), sort );
			for (Entry<String, Integer> entry : refPl.entrySet()) {
				add(total, entry.getKey(), -entry.getValue());
			}
		}
		if (total.isEmpty()) {
//			getLog().info("for transition :" + t.getName().getText() +" found conservative !");
			return true;
		}
		getLog().info("for transition :" + t.getName().getText() +" found "+ total);
		return false;
	}

	private static Map<String, Integer> buildRefsFromArc(Term term, Sort psort, Sort target) {
		Map<String, Integer> toret = new HashMap<String, Integer>();
		

		if (term instanceof All) {
			// All all = (All) term;
			if (isSameSort(psort, target)) {
				add(toret,"all",1);
			}
		} else if (term instanceof NumberOf) {
			NumberOf no = (NumberOf) term;
			int card = HLUtils.getCardinality(no);

			Map<String, Integer> token = buildRefsFromArc(no.getSubterm().get(1), psort, target);

			for (Entry<String, Integer> it : token.entrySet()) {
				add( toret, it.getKey(), it.getValue()*card);
			}
		} else if (term instanceof UserOperator) {
			// Probably designating a constant of the type
			UserOperator uo = (UserOperator) term;
			int index = HLUtils.getConstantIndex(uo);

			if (isSameSort(psort, target))
				add(toret,Integer.toString(index),1);
		} else if (term instanceof DotConstant) {
			if (isSameSort(psort, target))
				add(toret,Integer.toString(0),1);				
		} else if (term instanceof fr.lip6.move.pnml.symmetricnet.terms.Variable) {
			fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) term;

			if (isSameSort(psort, target))
				add(toret,var.getVariableDecl().getName(),1);
		} else if (term instanceof Tuple) {
			Tuple tuple = (Tuple) term;
			
			for (int i = tuple.getSubterm().size() -1 ; i >= 0 ; i--) {
				Term elem = tuple.getSubterm().get(i);
				if (elem instanceof UserOperator) {
					UserOperator uo = (UserOperator) elem;
					FEConstant fec = (FEConstant) uo.getDeclaration();
					Sort elemSort = fec.getSort();
					if (isSameSort(elemSort,target)) {
						add(toret, Integer.toString(HLUtils.getConstantIndex(uo)), 1);
					}
				} else if (elem instanceof fr.lip6.move.pnml.symmetricnet.terms.Variable) {
					// Probably designating a constant of the type
					fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) elem;
					Sort elemSort = var.getVariableDecl().getSort();
					if (isSameSort(elemSort,target)) {
						add(toret, var.getVariableDecl().getName(), 1);
					}
				} else if (elem instanceof Predecessor) {
					Predecessor pred = (Predecessor) elem;
					// Probably designating a constant of the type
					fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) pred.getSubterm().get(0);
					Sort elemSort = var.getVariableDecl().getSort();

					if (isSameSort(elemSort,target)) {
						add(toret, var.getVariableDecl().getName()+"--", 1);
					}
										
				} else if (elem instanceof Successor) {
					Successor pred = (Successor) elem;
					// Probably designating a constant of the type
					fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) pred.getSubterm().get(0);
					Sort elemSort = var.getVariableDecl().getSort();

					if (isSameSort(elemSort,target)) {
						add(toret, var.getVariableDecl().getName()+"++", 1);
					}
					
				} else {
					getLog().warning("unrecognized term type " + elem.getClass().getName());
					throw new UnsupportedOperationException();
				}

			}
		} else if (term instanceof Add) {
			Add add = (Add) term;
			for (Term t : add.getSubterm()) {
				Map<String, Integer> toadd = buildRefsFromArc(t, psort, target);
				for (Entry<String, Integer> it : toadd.entrySet()) {
					add( toret, it.getKey(), it.getValue());
				}
			}
		} else if (term instanceof Subtract) {
			Subtract add = (Subtract) term;
			int nbterm = 0;
			for (Term t : add.getSubterm()) {
				Map<String, Integer> toadd = buildRefsFromArc(t, psort, target);
				for (Entry<String, Integer> it : toadd.entrySet()) {
					if (nbterm == 0) {
						// the first term minus the next ones
						add( toret, it.getKey(), + it.getValue());
					} else {
						add( toret, it.getKey(), - it.getValue());						
					}
				}
				nbterm++;
			}
		} else if (term instanceof Predecessor) {
			Predecessor pred = (Predecessor) term;
			// Probably designating a constant of the type
			fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) pred.getSubterm().get(0);
			
			if (isSameSort(psort, target)) 
				add(toret, var.getVariableDecl().getName()+"--", 1);
						
		} else if (term instanceof Successor) {
			Successor pred = (Successor) term;
			// Probably designating a constant of the type
			fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) pred.getSubterm().get(0);

			if (isSameSort(psort, target)) 
				add(toret, var.getVariableDecl().getName()+"++", 1);
				
		} else {
			getLog().warning("Encountered unknown term in arc inscription " + term.getClass().getName());
		}


		return toret;
	}

	private static boolean isSameSort(Sort elemSort, Sort target) {
		if (elemSort == target) {
			return true;
		}
		Sort trueSort1 = HLUtils.getTrueSort(elemSort);
		Sort trueSort2 = HLUtils.getTrueSort(target);
		return EcoreUtil.equals(trueSort1, trueSort2);
	}

	private static void add(Map<String, Integer> toret, String va, int i) {
		if (i != 0) {
			Integer old = toret.get(va);
			if (old==null) {
				toret.put(va, i);
			} else {
				int val = i + old;
				if (val != 0) {
					toret.put(va, val);
				} else {
					toret.remove(va);
				}
			}
		}
	}
	
	
	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	public static void analyze(PetriNet petriNet) {
		for (Declaration decl : petriNet.getDeclaration()) {
			for (TermsDeclaration sort : decl.getStructure().getDeclaration()) {
				if (sort instanceof NamedSort) {
					NamedSort nsort = (NamedSort) sort;
					getLog().info("Working on domain : " + nsort.getName() );
					for (Page p : petriNet.getPages()) {
						boolean isOk = true;
						for (PnObject pno : p.getObjects()) {
							if (pno instanceof Transition) {
								Transition t = (Transition) pno;
								if (! isConservative(t,nsort.getSortdef())) {
									getLog().info("Domain :" + nsort.getName() +" is  not conservative due to " + t.getName().getText());							
									isOk = false;
								//	break;
								}
							}
						}
						if (isOk) {
							getLog().info("Domain :" + nsort.getName() +" is conservative.");
						} else {
							getLog().info("Domain :" + nsort.getName() +" is  not conservative.");							
						}
					}
				}
			}
		}
		for (EObject obj : petriNet.eContents()) {
		}
	}
}
