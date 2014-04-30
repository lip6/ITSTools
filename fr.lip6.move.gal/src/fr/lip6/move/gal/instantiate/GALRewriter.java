package fr.lip6.move.gal.instantiate;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;

public class GALRewriter {
	public static boolean autoTagHotbit = false;

	public static Support flatten (Specification spec, boolean withSeparation) {
		// remove parameters
		Support toret = instantiateParameters(spec,withSeparation);
		
		SupportAnalyzer.improveCommutativity(spec);
		
		if (autoTagHotbit) {
			HotBitRewriter.tagHotbitVariables(spec);
		}
		
		// rewrite statements to hotbit
		HotBitRewriter.instantiateHotBit(spec);
		
		// adding hotbit creates new parameters
		toret.addAll(instantiateParameters(spec, withSeparation));

		// rename type to avoid conflicts
		if (withSeparation)
			rename(spec,"_flat");
		else
			rename(spec,"_inst");
		
		DomainAnalyzer.computeVariableDomains(spec);
		// ranges are not useful anymore
		Instantiator.clearTypedefs(spec);
		return toret;
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

	private static Support instantiateParameters(Specification spec, boolean withSeparation) {		
		if (withSeparation) {
			// separate what we can
			Instantiator.separateParameters(spec);
		}
		// remove parameters
		Instantiator.instantiateParameters(spec);
		// simplify if we can
		Support toret = Simplifier.simplify(spec);
		// normalize
		toret.addAll(Instantiator.normalizeCalls(spec));
		
		return toret;
	}
	
	public static void fuseArrayCells (Specification spec) {
		Instantiator.instantiateParametersWithAbstractColors(spec);

		Simplifier.simplify(spec);

		Instantiator.fuseIsomorphicEffects(spec);
		
		rename(spec,"_unc");
	}
	
}
