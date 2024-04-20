package fr.lip6.move.gal.structural.smt;

import java.util.Collections;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.IResponse;
import org.smtlib.SMT;

import android.util.SparseBoolArray;
import fr.lip6.move.gal.structural.expr.Expression;

public class Problem {
	private String name;
	private ISymbol symbol;
	private int id;
	private Expression predicate;
	private VarSet support;
	private ICommand definition;
	
	private CandidateSolution solution = new CandidateSolution();

	public Problem(String name, int id, Expression predicate) {
		this.name = name;
		this.id = id;
		this.predicate = predicate;

		SparseBoolArray s = VarSet.computeSupport(predicate);
		this.support = new VarSet();
		support.addVars("s", s);

		IExpr smt = predicate.accept(new ExprTranslator());
		SMT fac = new SMT();
		IFactory ef = fac.smtConfig.exprFactory;
		org.smtlib.ISort.IApplication bools = fac.smtConfig.sortFactory.createSortExpression(ef.symbol("Bool"));
		this.symbol = ef.symbol(name);
		definition = new org.smtlib.command.C_define_fun(symbol, // name
				Collections.emptyList(), // parameters
				bools, // return type
				smt // body
		);
	}
	
	public boolean declareProblem(SolverState solver) {
		solver.declareVars(support);
		IResponse response = definition.execute(solver.getSolver());
		return response.isOK();
	}
	
	public void updateStatus(SolverState solver, boolean withWitness) {
		String textReply = SMTUtils.checkSatAssuming(solver.getSolver(), symbol);
		
		if ("unsat".equals(textReply)) {
			System.out.println("Problem " + name + " is UNSAT");
			solution.setReply(SMTReply.UNSAT);
		} else if ("sat".equals(textReply)) {
			solution.setReply(SMTReply.SAT);
			// have to do this to check for reals
			if (withWitness || solver.getNumericType() == SolutionType.Real) {
				solution.updateWitness(solver.getSolver());
			}
		} else {
			solution.setReply(SMTReply.UNKNOWN);
		}
	}
	
	
	public boolean isSolved() {
		return solution.getReply() == SMTReply.UNSAT;
	}
	
	public CandidateSolution getSolution() {
		return solution;
	}

	@Override
	public String toString() {
		return "(" + symbol + "=" + solution + ")";
	}
	
	
}