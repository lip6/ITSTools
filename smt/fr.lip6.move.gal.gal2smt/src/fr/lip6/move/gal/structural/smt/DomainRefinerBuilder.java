package fr.lip6.move.gal.structural.smt;

import org.smtlib.ICommand;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.SMT;
import org.smtlib.command.C_assert;

public class DomainRefinerBuilder {

	public static StaticRefiner enforceMinBound(String prefix, int nbvars, int min) {
		return enforceBound(prefix, nbvars, min, true);
	}

	public static StaticRefiner enforceMaxBound(String prefix, int nbvars, int max) {
		return enforceBound(prefix, nbvars, max, false);
	}
	
	public static StaticRefiner enforceBound(String prefix, int nbvars, int bound, boolean isMin) {
		IFactory ef =  new SMT().smtConfig.exprFactory;
		StaticRefiner doms = new StaticRefiner("Domain("+prefix+")");
		{			
			for (int i = 0 ; i < nbvars ; i++) {
				// t_i >= 0
				ISymbol si = ef.symbol(prefix + i);
				ISymbol comparator = isMin ? ef.symbol(">=") : ef.symbol("<=");
				ICommand c = new C_assert(ef.fcn(comparator, si, ef.numeral(bound)));
				VarSet vs = new VarSet();
				vs.addVar(prefix , i);
				doms.addConstraint(new SMTConstraint(c, vs ));
			}
		}
		return doms;
	}

	
}
