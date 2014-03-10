package fr.lip6.move.gal.instantiate;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;

public class GALRewriter {
	public static boolean autoTagHotbit = false;

	public static void flatten (Specification spec, boolean withSeparation) {
		// remove parameters
		instantiateParameters(spec,withSeparation);
		
		if (autoTagHotbit) {
			Instantiator.tagHotbitVariables(spec);
		}
		
		// rewrite statements to hotbit
		Instantiator.instantiateHotBit(spec);
		
		// adding hotbit creates new parameters
		instantiateParameters(spec, withSeparation);

		// rename type to avoid conflicts
		if (withSeparation)
			rename(spec,"_flat");
		else
			rename(spec,"_inst");
		
		// ranges are not useful anymore
		Instantiator.clearTypedefs(spec);
	}

	private static void rename(Specification spec, String toappend) {
		for (TypeDeclaration td : spec.getTypes()) {
			td.setName(td.getName()+toappend);
		}
	}

	// no simplifications, mostly for debug
	public static void separateParameters(Specification spec) {
		Instantiator.separateParameters(spec);
		rename(spec,"_sep");
	}

	private static void instantiateParameters(Specification spec, boolean withSeparation) {		
		if (withSeparation) {
			// separate what we can
			Instantiator.separateParameters(spec);
		}
		// remove parameters
		Instantiator.instantiateParameters(spec);
		// simplify if we can
		Simplifier.simplify(spec);
	}
	
	public static void fuseArrayCells (Specification spec) {
		Instantiator.instantiateParametersWithAbstractColors(spec);

		Simplifier.simplify(spec);

		Instantiator.fuseIsomorphicEffects(spec);
		
		rename(spec,"_unc");
	}
	
}
