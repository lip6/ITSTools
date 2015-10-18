package fr.lip6.move.gal.gal2smt.cover;

import java.util.HashMap;
import java.util.Map;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.VariableReference;

public class FlowMatrix {

	private Map<String, Map<Transition,Integer>> flow = new HashMap<String, Map<Transition,Integer>>();
	
	void addEffect(Transition tr, VariableReference vref, int val) {
		String symb = getSymbolName(vref);
		Map<Transition, Integer> line = getLine(symb );
		Integer cur = line.get(tr);
		if (cur==null) {
			cur=0;
		}
		cur+=val;
		line.put(tr, cur);
	}

	public Map<Transition, Integer> getLine(String symbol) {
		Map<Transition, Integer> res = flow.get(symbol);
		if (res==null) {
			res = new HashMap<Transition, Integer>();
			flow.put(symbol, res);
		}
		return res ;
	}

	
	String getTransParikhName(Transition tr) {
		return tr.getName()+".x";
	}

	public String getSymbolName(VariableReference vref) {
		if (vref.getIndex()==null) {
			return vref.getRef().getName();
		} else {
			return getArrName((ArrayPrefix) vref.getRef(), ((Constant) vref.getIndex()).getValue());
		}
	}
	String getArrName(ArrayPrefix arr, int i) {
		return "|"+arr.getName()+"["+i+"]|";
	}

}
