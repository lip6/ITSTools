package fr.lip6.move.gal.gal2smt;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.emf.common.util.EList;
import org.smtlib.IExpr;
import org.smtlib.IExpr.ISymbol;

import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.Transition;

public class CallsSMT {
	private static IExpr.IFactory efactory = GalToSMT.getSMT().smtConfig.exprFactory;	
	private static HashMap<String, ArrayList<ISymbol>> transitions = new HashMap<String, ArrayList<ISymbol>>();
	
	public static void fillTransitionMap(EList<Transition> transitionsList){
		for (Transition transit : transitionsList) {
			String label = "";
			if(transit.getLabel() != null) label = transit.getLabel().getName();
			addTransition(label, transit);
		}
	}
	
	public static IExpr generateSelfCalls(Label label, IExpr indexNow){
		String lbl = "";
		List<IExpr> exprs = new ArrayList<IExpr>();
		exprs.add(efactory.symbol("true"));
		
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
