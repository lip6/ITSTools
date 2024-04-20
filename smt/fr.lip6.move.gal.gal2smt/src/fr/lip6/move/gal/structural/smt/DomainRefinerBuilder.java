package fr.lip6.move.gal.structural.smt;

import org.smtlib.ICommand;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.command.C_assert;

public class DomainRefinerBuilder {

	public static StaticRefiner enforceMinBound(String prefix, int nbvars, int min, SolverState solver) {
		return enforceBound(prefix, nbvars, min, true, solver);
	}

	public static StaticRefiner enforceMaxBound(String prefix, int nbvars, int max, SolverState solver) {
		return enforceBound(prefix, nbvars, max, false, solver);
	}
	
	public static StaticRefiner enforceBound(String prefix, int nbvars, int bound, boolean isMin, SolverState solver) {
		StaticRefiner doms = new StaticRefiner("Domain");
		{
			IFactory ef = solver.getSMT().smtConfig.exprFactory;

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
