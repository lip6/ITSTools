package fr.lip6.move.gal.instantiate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.VarAccess;
import fr.lip6.move.gal.VariableRef;

public class SupportAnalyzer {

	public static void computeSupport(EObject ie, Support support) {
		if (! computeSupportTerminals(ie, support) ) 
		{
			for (TreeIterator<EObject> it = ie.eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();
				
				if (computeSupportTerminals(obj, support)) {
					it.prune();
				}
			}
		}
	}

	private static boolean computeSupportTerminals(EObject ie,
			Support support) {
		if (ie instanceof VariableRef) {
			VariableRef vref = (VariableRef) ie;
			support.add(vref);
			return true;
		} else if (ie instanceof ArrayVarAccess) {
			ArrayVarAccess ava = (ArrayVarAccess) ie;
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
	
	public static Map<EObject,Set<EObject>> computePrecedence (Iterable<BooleanExpression> guardterms, Iterable<Actions> actions) {
		
		Map<EObject, Set<EObject>> precedes = new HashMap<EObject, Set<EObject>>();
		
		Map<BooleanExpression, Support> guardsupport = new HashMap<BooleanExpression, Support>();
		Map<Actions, Support> readsupport = new HashMap<Actions, Support>();
		Map<Actions, Support> writesupport = new HashMap<Actions, Support>();
		
		for (BooleanExpression be : guardterms) {
			Support support = new Support();
			computeSupport(be, support);
			guardsupport.put(be, support );
		}
		
		for (Actions action : actions) {
			Support read = new Support();
			Support write = new Support();
			computeSupport(action, read, write);
			readsupport.put(action, read);
			writesupport.put(action, write);
		}
		
		List<Actions> seen  = new ArrayList<Actions>();
		for (Actions action : actions) {
			for (BooleanExpression be : guardterms) {
				if (guardsupport.get(be).intersects(writesupport.get(action))) {
					addToPrecedes(be,action,precedes);
				}
			}
			for (Actions before : seen) {
				if (readsupport.get(action).intersects(writesupport.get(seen))) {
					addToPrecedes(before, action, precedes);
				}
			}
			seen.add(action);
		}
		
		return precedes ;
	}

	private static void addToPrecedes(EObject before, EObject after, Map<EObject, Set<EObject>> precedes) {
		Set<EObject> list = precedes.get(before);
		if (list == null) {
			list = new HashSet<EObject>();
			precedes.put(before, list);
		}
		list.add(after);
	}

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
					computeSupport(ass, read, write);
				}
			}
		}
	}
	
}


