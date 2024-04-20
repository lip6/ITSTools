package fr.lip6.move.gal.structural.smt;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import android.util.SparseBoolArray;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;

public class VarSet implements Cloneable{
	private Map<String, SparseBoolArray> vars = new HashMap<>();
	
	public void addVars(String prefix, SparseBoolArray values) {
		vars.compute(prefix, (k, v) -> (v == null) ? values.clone() : SparseBoolArray.or(v,values));
	}
	
	public static VarSet removeAll(VarSet a, VarSet b) {
		VarSet res = new VarSet();
        for (Map.Entry<String, SparseBoolArray> entry : a.vars.entrySet()) {
            SparseBoolArray bvals = b.vars.get(entry.getKey());
            if (bvals != null) {
                res.addVars(entry.getKey(), SparseBoolArray.removeAll(entry.getValue(), bvals));
            } else {
                res.addVars(entry.getKey(), entry.getValue());
            }
        }
        return res;
    }
	
	public VarSet clone() {
		VarSet res = new VarSet();
		for (Map.Entry<String, SparseBoolArray> entry : vars.entrySet()) {
			res.addVars(entry.getKey(), entry.getValue());
		}
		return res;
	}
	
	public static boolean intersects(VarSet a, VarSet b) {
		for (Map.Entry<String, SparseBoolArray> entry : a.vars.entrySet()) {
            SparseBoolArray bvals = b.vars.get(entry.getKey());
            if (bvals != null) {
				if (SparseBoolArray.intersects(entry.getValue(),bvals)) {
					return true;
				}
            }
        }
		return false;
	}
	
	public boolean containsAll(VarSet b) {
		for (Map.Entry<String, SparseBoolArray> entry : b.vars.entrySet()) {
			SparseBoolArray avals = vars.get(entry.getKey()); 
			SparseBoolArray bvals = entry.getValue();
			if (avals == null) {
				return false;
			}
			if (! SparseBoolArray.containsAll(avals, bvals)) {
				return false;
			}
		}
		return true;
	}

	public void addAll(VarSet b) {
		for (Map.Entry<String, SparseBoolArray> entry : b.vars.entrySet()) {
			vars.compute(entry.getKey(), (k, v) -> (v == null) ? entry.getValue().clone() : SparseBoolArray.or(v,entry.getValue()));
		}
	}
	
	public void addVar(String prefix, int index) {
		vars.compute(prefix, (k, v) -> {
			if (v == null) {

				SparseBoolArray s = new SparseBoolArray();
				s.put(index, true);
				return s;
			} else {
				v.put(index, true);
				return v;
			}
		});
	}

	public static SparseBoolArray computeSupport(Expression pred) {
		BitSet bs = new BitSet();
		SparsePetriNet.addSupport(pred, bs);
		return new SparseBoolArray(bs);
	}
	
	public Map<String, SparseBoolArray> getVars() {
		return vars;
	}

	@Override
	public String toString() {
		return vars.toString();
	}
	
	public int size() {
		int total = 0;
		for (SparseBoolArray s : vars.values()) {
			total += s.size();
		}
		return total;
	}
	
}
