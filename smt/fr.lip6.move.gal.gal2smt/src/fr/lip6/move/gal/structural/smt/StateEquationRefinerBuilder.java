package fr.lip6.move.gal.structural.smt;

import java.util.ArrayList;
import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.command.C_assert;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.IntMatrixCol;

public class StateEquationRefinerBuilder {

	public static List<IRefiner> buildStateEquationRefiner(IntMatrixCol effects, List<Integer> marks, SolverState solver) {
		int nbt = effects.getColumnCount();
		String prefix = "t";

		// declare a set of variables for holding Parikh count of the transition		
		solver.addVars(prefix, nbt, VarType.NUMERIC);		

		
		StaticRefiner doms = DomainRefinerBuilder.enforceMinBound(prefix, nbt, 0, solver);
		
		List<IRefiner> refiners = new ArrayList<>();
		refiners.add(doms);

		IFactory ef = solver.getSMT().smtConfig.exprFactory;

		StaticRefiner steq = new StaticRefiner("State Equation");
		
		// we work with one constraint for each place => use transposed
		IntMatrixCol mat = effects.transpose();
		for (int varindex = 0 ; varindex < mat.getColumnCount() ; varindex++) {

			SparseIntArray line = mat.getColumn(varindex);
			VarSet vars = new VarSet();
			
			IExpr constraint = ef.fcn(ef.symbol("+"), ef.numeral(marks.get(varindex)), buildRowConstraint(line, ef, vars));
			
			vars.addVar("s", varindex);
			
			ICommand c = new C_assert(
							ef.fcn(ef.symbol("="), 
									ef.symbol("s"+varindex),
									// = m0.x + X0*C(t0,x) + ...+ XN*C(Tn,x)
									constraint));
			steq.addConstraint(new SMTConstraint(c, vars));
		}
		refiners.add(steq);
		
		return refiners;
	}

	public static IExpr buildRowConstraint(SparseIntArray line, IFactory ef, VarSet vars) {
		// assert : x = X0*C(t0,x) + ...+ XN*C(Tn,x)
		List<IExpr> toadd = new ArrayList<>();
		List<IExpr> torem = new ArrayList<>();
		
		// Ignore initial marking

		//  Xi*C(ti,x)
		for (int i = 0 ; i < line.size() ; i++) {
			int trindex = line.keyAt(i);
			int val = line.valueAt(i);
			IExpr ss = ef.symbol("t"+trindex);
			vars.addVar("t", trindex);
			if (val != 1 && val != -1) {
				ss = ef.fcn(ef.symbol("*"), ef.numeral( Math.abs(val)), ss );
			}
			if (val > 0) 
				toadd.add(ss);
			else
				torem.add(ss);
		}
		
		IExpr sumE ;
		if (toadd.isEmpty()) {
			sumE = ef.numeral(0);
		} else if (toadd.size() == 1) {
			sumE = toadd.get(0);
		} else {
			sumE = ef.fcn(ef.symbol("+"), toadd);
		}

		IExpr sumR;
		if (torem.isEmpty()) {
			sumR = ef.numeral(0);
		} else if (torem.size() == 1) {
			sumR = torem.get(0);
		} else {
			sumR = ef.fcn(ef.symbol("+"), torem);
		}
		
		IExpr constraint = ef.fcn(ef.symbol("-"), sumE, sumR);
		return constraint;
	}
	

}
