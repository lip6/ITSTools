package fr.lip6.move.gal.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.VarAccess;
import fr.lip6.move.gal.VariableRef;

/**
 * A utility class (static only) with methods to analyze support of GAL statements.
 * @author Yann
 *
 */
public class SupportAnalyzer {

	/**
	 * Adds all support variables of target EObject (and its descendants) to the provided support.
	 * @param target the object whose support we are interested in
	 * @param support the support we add supporting variables to
	 */
	public static void computeSupport(EObject target, Support support) {
		// test for terminal case
		if (! computeSupportTerminals(target, support) ) 
		{
			// iterate over descendants : this way of doing it avoids a costly recursion
			for (TreeIterator<EObject> it = target.eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();
				
				if (computeSupportTerminals(obj, support)) {
					// prevent analysis of descendants of this node; next will skip to siblings of this node
					// the issue is tab[tab[i]] where we are not interested in child expression tab[i] 
					// we already have deduced by computeSupportTerminals that tab[*] is in support
					it.prune();
				}
			}
		}
	}

	/**
	 * If the current target is a terminal (ref to var or array cell) return true and and concerned var to support.
	 * For a plain variable ref this is just a SupportVariable. For an array cell reference, we test if
	 *  the index is a constant, e.g. tab[3] add tab[3] to suport, if we have a nested expression, we pessimistically
	 *  add all cells of tab to support. Computing domain of subexpression seems a bit too much.
	 * Otherwise (target is not a VarAccess) return false and do nothing.
	 * @param target the (possibly) terminal statement
	 * @param support the support to add to if we get a hit
	 * @return true if the node was terminal
	 */
	private static boolean computeSupportTerminals(EObject target,
			Support support) {
		if (target instanceof VariableRef) {
			VariableRef vref = (VariableRef) target;
			support.add(vref);
			return true;
		} else if (target instanceof ArrayVarAccess) {
			ArrayVarAccess ava = (ArrayVarAccess) target;
			if (ava.getIndex() instanceof Constant) {
				support.add(ava.getPrefix(), ((Constant) ava.getIndex()).getValue());
				return true;
			} else {
				support.addAll(ava); 
				return false;
			}
		}
		return false;
	}

	/**
	 * This function computes a partial order over its arguments, based on which supports intersect or not in the arguments.
	 * We consider the guard is of the form g1&&g2&&g3 ... a conjunction of "guardterms". It should be evaluated first.
	 * Then we have a sequential list of actions, a1;a2;a3 that should be executed in order.
	 * Given that guard terms are effect free (read only).
	 * Given that for statements (Actions) a we can compute both read(a) and write(a) the set of support variables that are read or written by a.
	 * The question is, without messing up the semantics, what necessarily must precede what ?
	 * Basically, we have initially a partial order : (evaluate all guard terms in any order) THEN a1 THEN a2 ...
	 * Now a1 really precedes a2 iff. read(a2) intersects write(a1). Otherwise, we can relax the partial order to
	 *  (all gi) THEN (a1 a2) THEN a3 ....
	 *  
	 * @param guardterms the terms of the guard of the form g1&&g2&&g3 ...
	 * @param actions the actions that follow guard evaluation, in evaluation order
	 * @return A partial order, mapping every object in both arguments to the set of other arguments it must really precede.
	 */
	public static Map<EObject,Set<EObject>> computePrecedence (Iterable<BooleanExpression> guardterms, Iterable<Actions> actions) {

		Map<EObject, Set<EObject>> precedes = new HashMap<EObject, Set<EObject>>();
		// cache/convenience access to support of the actions
		Map<Actions, Support> readsupport = new HashMap<Actions, Support>();
		Map<Actions, Support> writesupport = new HashMap<Actions, Support>();
		// cache convenience access to support of guard terms
		Map<BooleanExpression, Support> guardsupport = new HashMap<BooleanExpression, Support>();

		// compute support of guard terms and map them to it 
		for (BooleanExpression be : guardterms) {
			Support support = new Support();
			computeSupport(be, support);
			guardsupport.put(be, support );
		}

		// compute the support of each Action and map it to its read and write support
		loadSupport(actions, readsupport, writesupport);

		// precedence of guard on actions
		for (Actions action : actions) {
			for (BooleanExpression be : guardterms) {
				// if the action writes to a variable tested in the guard, we have to test *before* doing the action.
				if (guardsupport.get(be).intersects(writesupport.get(action))) {
					addToPrecedes(be,action,precedes);
				}
			}
		}
		// precedence of actions on one another
		computeActionPrecedence(actions, precedes, readsupport, writesupport);

		return precedes ;
	}

	/**
	 * Compute read and write support of each action in the argument list.
	 * @param actions the list of actions to index
	 * @param readsupport the resulting cache with read support of each action
	 * @param writesupport the resulting cache  with write support of each action
	 */
	private static void loadSupport(Iterable<Actions> actions,
			Map<Actions, Support> readsupport,
			Map<Actions, Support> writesupport) {
		for (Actions action : actions) {
			Support read = new Support();
			Support write = new Support();
			computeSupport(action, read, write);
			readsupport.put(action, read);
			writesupport.put(action, write);
		}
	}

	
	/** 
	 * Iterate through the argument actions, and determine precedence. 
	 * ai precedes aj, noted ai < aj iff. 
	 * ** i < j, ai is before aj in the initial order
	 * ** and ( read(a) inter write(b) or read(b) inter write(a) or write(a) inter write(b) are non empty ). 
	 * @param actions The actions in their initial order
	 * @param precedes Will be filled in this function. a -> {b,c} means action a < b and a < c.
	 * @param readsupport (readonly) caches for support of actions, should be loaded before call.
	 * @param writesupport (readonly) caches for support of actions, should be loaded before call.
	 */
	private static void computeActionPrecedence(Iterable<Actions> actions,
			Map<EObject, Set<EObject>> precedes,
			Map<Actions, Support> readsupport,
			Map<Actions, Support> writesupport) {

		List<Actions> seen  = new ArrayList<Actions>();
		for (Actions action : actions) {
			for (Actions before : seen) {
				Support r1 = readsupport.get(action);
				Support w1 = writesupport.get(action);
				Support w2 = writesupport.get(before);
				Support r2 = readsupport.get(before);
				if (r1.intersects(w2)
						|| w1.intersects(r2)
						|| w1.intersects(w2)) {

					addToPrecedes(before, action, precedes);
				}
			}
			seen.add(action);
		}
	}

	/**
	 * Utility helper to add "before < after" to a Partial order under construction. 
	 * @param before 
	 * @param after
	 * @param precedes
	 */
	private static void addToPrecedes(EObject before, EObject after, Map<EObject, Set<EObject>> precedes) {
		Set<EObject> list = precedes.get(before);
		if (list == null) {
			list = new HashSet<EObject>();
			precedes.put(before, list);
		}
		list.add(after);
	}

	/**
	 * Compute the read and write supports of an action.
	 * For tab[i] = e ; this means tab[*] is write support, support(i) union support(e) are read support.
	 * For tab[3] = e ; this means tab[3] is write support, support(e) is read support.
	 * For a = e; this means {a} is write support and support(e) is write support.
	 * For control structures, scan for boolean expressions (conditions) and descend into body recursively to collect full support of the action.
	 * @param action an action to analyze
	 * @param read the read support of the action, that is added to.
	 * @param write the write support, added to.
	 */
	private static void computeSupport(Actions action, Support read, Support write) {
		if (action instanceof Assignment) {
			Assignment ass = (Assignment) action;
			VarAccess lhs = ass.getLeft();
			if (lhs instanceof VariableRef) {
				VariableRef vref = (VariableRef) lhs;
				write.add(vref);
			} else if (lhs instanceof ArrayVarAccess) {
				ArrayVarAccess ava = (ArrayVarAccess) lhs;
				if (ava.getIndex() instanceof Constant) {
					write.add(ava.getPrefix(), ((Constant) ava.getIndex()).getValue());
				} else {
					write.addAll(ava); 
					computeSupport(ava.getIndex(), read);
				}
			}
			computeSupport(ass.getRight(), read);			
		} else {
			for (TreeIterator<EObject> it = action.eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();
				if (obj instanceof Assignment) {
					Assignment ass = (Assignment) obj;
					// this recursion should descend correctly
					computeSupport(ass, read, write);
					// no need to descend more
					it.prune();
				} else if (obj instanceof BooleanExpression) {
					// condition of if statement for instance
					// add to read
					computeSupport(obj, read);
					it.prune();
				}
			}
		}
	}

	/**
	 * This function attempts to imporve commutativity of statements (assignments).
	 * The rationale is thus :
	 * s1 :a = x;
	 * s2 :b = a;
	 * We have s1 < s2, since write(s1) inter read(s2) non empty. But, if a is only assigned a value once, we could equivalently write
	 * s1 :a = x;
	 * s2 :b = x;
	 * and have s1 <> s2 (commutative). The trick is that between s1 and s2, no variable read in x can written to.
	 * x is an arbitrary expression.
	 * This function works pretty well on some very specific patterns, but is a bit weak overall.
	 * TODO : Not sure it handles "if" statements gracefully. 
	 * @param spec the spec whose action we are going to "improve" in place
	 */
	public static void improveCommutativity(Specification spec) {

		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				for (Transition t : gal.getTransitions()) {
					// for every transition of every GAL

					// build partial order. We instantiate per transition so there is no confusion.
					Map<EObject, Set<EObject>> precedes = new HashMap<EObject, Set<EObject>>();
					Map<Actions, Support> readsupport = new HashMap<Actions, Support>();
					Map<Actions, Support> writesupport = new HashMap<Actions, Support>();

					loadSupport(t.getActions(), readsupport, writesupport);

					computeActionPrecedence(t.getActions(), precedes, readsupport, writesupport);

					// make sure there is no swap behavior where variables are assigned twice
					boolean breakout = false;
					for (int i = 0 ; i < t.getActions().size() ; i++) {
						for (int j = i+1; j < t.getActions().size() ; j++) {
							if (writesupport.get(t.getActions().get(i)).intersects(writesupport.get(t.getActions().get(j)))) {
								breakout = true;
								i = t.getActions().size();
								break;
							}
						}
					}
					if (breakout) {
						// danger, we are not protected against this behavior in the sequel
						continue;
					}

					for (int i = 0 ; i < t.getActions().size() ; i++) {
						Actions a = t.getActions().get(i);
						if (a instanceof Assignment) {
							Assignment ass = (Assignment) a;
							if (ass.getLeft() instanceof VariableRef) {
								VariableRef vref = (VariableRef) ass.getLeft();

								// Assignment of the form : vref = rhs ; 
								
								Support vsupp = new Support();
								computeSupport(vref, vsupp);
								Support rsupp = new Support();
								computeSupport(ass.getRight(), rsupp);

								for (int j = i+1; j < t.getActions().size() ; j++) {
									Actions a2 = t.getActions().get(j);

									if (rsupp.intersects(writesupport.get(a2))) {
										// stop if something modifies rhs
										break;
									}
									if (readsupport.get(a2).intersects(vsupp)) {
										// filter only relevant and store into refs
										List<EObject> refs = new ArrayList<EObject>();
										for (TreeIterator<EObject> it = a2.eAllContents() ; it.hasNext() ; ) {
											EObject obj = it.next();
											if (obj instanceof VariableRef) {
												VariableRef vvref = (VariableRef) obj;
												if (vvref.getReferencedVar() == vref.getReferencedVar()) {
													refs.add(vvref);
												}
											}
										}
										// use EMF to heavy weight change the stuff in place
										// done after scan loop to avoid messing up the iterator.
										for (EObject obj : refs) {
											EcoreUtil.replace(obj, EcoreUtil.copy(ass.getRight()));
										}
									}
								}

							}
						}
					}
				}
			}
		}
	}
}

