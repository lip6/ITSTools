package fr.lip6.move.gal.semantics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.instantiate.Instantiator;

/**
 * Maps GAL variables to integer describing their position in the global state vector (a big array with one cell per variable or array cell of the original GAL).
 * Let the INextBuilder classes handle building this correctly, there are offsets to deal with multiple instances of a type. 
 * @author ythierry
 *
 */
public class VariableIndexer {

	private Map<String, Integer> varIndexes;
	private List<String> varNames;
	private int size = 0;
	private int offset;
	private List<Integer> initial;

	public VariableIndexer(GALTypeDeclaration gal, int offset) {
		this.offset = offset;
		varIndexes = new HashMap<>();
		varNames = new ArrayList<>();
		initial = new ArrayList<>();
		int i = offset;
		for (Variable var : gal.getVariables()) {
			varIndexes.put(var.getName(), i++);
			varNames.add(var.getName());
			initial.add(Instantiator.evalConst(var.getValue()));
			size++;
		}
		for (ArrayPrefix arr : gal.getArrays()) {
			String aname = arr.getName();
			int max = Instantiator.evalConst(arr.getSize());
			size += max;
			varIndexes.put(aname, i);
			for (int j = 0; j < max; j++) {
				String vn = aname + "[" + j + "]";
				varIndexes.put(vn, i++);
				varNames.add(vn);
				initial.add(Instantiator.evalConst(arr.getValues().get(j)));
			}
		}
	}

	public int getIndex(String vname) {
		return varIndexes.get(vname);
	}

	public int getSize() {
		return size;
	}

	public int getOffset() {
		return offset;
	}

	public List<Integer> getInitial() {
		return initial;
	}
}
