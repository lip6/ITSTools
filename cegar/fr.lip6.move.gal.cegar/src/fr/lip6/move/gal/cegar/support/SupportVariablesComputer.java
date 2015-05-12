package fr.lip6.move.gal.cegar.support;

import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.support.ISupportVariable;
import fr.lip6.move.gal.support.Support;

/**
 * A utility class (static only) for GAL abstractions with a method to extract the Variables of the specified Support.
 * @author Anonymous
 *
 */
public class SupportVariablesComputer {
	
	public static List<Variable> getVariablesFromSupport(Support support) {
		
		List<Variable> varList = new ArrayList<Variable>();
		
		for (ISupportVariable sv : support) {
			VarDecl vd = sv.getVar();
			if (vd instanceof Variable)
				varList.add((Variable) vd);
		}
		
		return varList;
	}
}
