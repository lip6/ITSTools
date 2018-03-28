package fr.lip6.move.gal.instantiate;

import java.util.logging.Logger;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.support.Support;
import fr.lip6.move.gal.support.SupportAnalyzer;

public class GALRewriter {
	public static boolean autoTagHotbit = false;

	public static Support flatten (Specification spec, boolean withSeparation) {
		long debut = System.currentTimeMillis();

		// remove parameters
		Support toret = instantiateParameters(spec,withSeparation);
		
		SupportAnalyzer.improveCommutativity(spec);
		
		if (autoTagHotbit) {
			HotBitRewriter.tagHotbitVariables(spec);
		}
		
		// rewrite statements to hotbit
		if (HotBitRewriter.instantiateHotBit(spec)) {
			// adding hotbit creates new parameters
			toret.addAll(instantiateParameters(spec, withSeparation));
		}

		CompositeBuilder.getInstance().rewriteArraysAsVariables(spec);
		// rename type to avoid conflicts
		if (withSeparation)
			rename(spec,"_flat");
		else
			rename(spec,"_inst");
		
		DomainAnalyzer.computeVariableDomains(spec);
		// ranges are not useful anymore
		Instantiator.clearTypedefs(spec);
		getLog().info("Flatten gal took : " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$

		return toret;
	}

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
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
		Support toret = Instantiator.instantiateParameters(spec, withSeparation);
		
//		if (withSeparation) {
//			// separate what we can
//			Instantiator.separateParameters(spec);
//		}
//		// remove parameters
//		Support toret = Instantiator.instantiateParameters(spec);
		// simplify if we can
		toret.addAll(Simplifier.simplify(spec));
		// normalize
		toret.addAll(Instantiator.normalizeCalls(spec));
		
		Simplifier.removeUncalledTransitions (spec);
		
		return toret;
	}
	
	public static void fuseArrayCells (Specification spec) {
		Instantiator.instantiateParametersWithAbstractColors(spec);

		Simplifier.simplify(spec);

		Instantiator.fuseIsomorphicEffects(spec);
		
		rename(spec,"_unc");
	}
	
}
