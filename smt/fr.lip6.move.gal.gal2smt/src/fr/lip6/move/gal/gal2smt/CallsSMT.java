package fr.lip6.move.gal.gal2smt;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.smtlib.IExpr;
import org.smtlib.IExpr.ISymbol;

import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.Transition;

public class CallsSMT {
	private static IExpr.IFactory efactory = GalToSMT.getSMT().smtConfig.exprFactory;	
	private static HashMap<String, ArrayList<ISymbol>> transitions = new HashMap<String, ArrayList<ISymbol>>();
	
	public static Map<String, List<Transition>> fillTransitionMap(EList<Transition> transitionsList){
		Map<String, List<Transition>> res = new HashMap<String, List<Transition>>();
		
		for (Transition trans : transitionsList) {
			String label = "";
			if(trans.getLabel() != null) {
				label = trans.getLabel().getName();
				List<Transition> list = res.get(label);
				if (list == null) {
					list = new ArrayList<Transition>();
					res.put(label, list);
				}
				list.add(trans);
			}
			addTransition(label, trans);
		}
		return res ;
	}
	
	public static IExpr generateSelfCalls(Label label, IExpr indexNow){
		String lbl = "";
		List<IExpr> exprs = new ArrayList<IExpr>();
		// do not add this id to the call !
		//exprs.add(efactory.symbol("true"));
		
		if(label != null) 
			lbl = label.getName();
		for(ISymbol transitExpr : transitions.get(lbl)){
			exprs.add(efactory.fcn(transitExpr, indexNow));
		}
		return efactory.fcn(efactory.symbol("or"),exprs);
	}
	
	public static void addTransition(String label, Transition transition){
		if(transitions.containsKey(label)){
			transitions.get(label).add(efactory.symbol(transition.getName()));
		}else{
			ArrayList<ISymbol> trans = new ArrayList<ISymbol>();
			trans.add(efactory.symbol(transition.getName()));
			transitions.put(label, trans);
		}
	}
}
