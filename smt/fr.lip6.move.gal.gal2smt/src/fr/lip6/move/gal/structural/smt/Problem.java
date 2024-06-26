package fr.lip6.move.gal.structural.smt;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.IResponse;
import org.smtlib.SMT;
import org.smtlib.command.C_define_fun;

import android.util.SparseBoolArray;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.expr.Expression;

public class Problem {
	private String name;
	private ISymbol symbol;
	private Expression predicate;
	private VarSet support;
	private ICommand definition;
	
	private ISymbol refinedSymbol = null;
	
	private CandidateSolution solution = new CandidateSolution();
	private boolean isEF;
	private String smtFile;

	public Problem(String name, boolean isEF, Expression pred) {
		this.name = name;
		this.isEF = isEF;
		this.predicate = pred;
		if (!isEF) {
			predicate = Expression.not(predicate);
		}

		SparseBoolArray s = VarSet.computeSupport(predicate);
		this.support = new VarSet();
		support.addVars("s", s);

		IExpr smt = predicate.accept(new ExprTranslator());
		IFactory ef = SMT.instance.smtConfig.exprFactory;
		symbol = ef.symbol(name);
		definition = declareBoolFunc(symbol, smt);
	}

	public Problem(String name, String smtFile, ISymbol symbol, VarSet support) {
		this.name = name;
		this.symbol = symbol;
		this.predicate = null;
		this.support = support;
		this.definition = null;
		this.refinedSymbol = null;
		this.isEF = true;
		this.smtFile = smtFile;
	}

	public static ICommand declareBoolFunc(ISymbol name, IExpr body) {
		IFactory ef = SMT.instance.smtConfig.exprFactory;
		org.smtlib.ISort.IApplication bools = SMT.instance.smtConfig.sortFactory.createSortExpression(ef.symbol("Bool"));		
		C_define_fun def = new org.smtlib.command.C_define_fun(name, // name
				Collections.emptyList(), // parameters
				bools, // return type
				body
		);
		return def;
	}
	
	public void refineDefinition(IExpr additionalConstraint, SolverState solver) {
		if (refinedSymbol != null) {
			throw new IllegalStateException("Refinement already done.");
		}
		IFactory ef = SMT.instance.smtConfig.exprFactory;
		this.refinedSymbol = ef.symbol(name + "_refined");
		IExpr refinedBody = SMTUtils.makeAnd(Arrays.asList(symbol, additionalConstraint));
		ICommand def = declareBoolFunc(refinedSymbol, refinedBody);
		def.execute(solver.getSolver());
	}
	
	public boolean declareProblem(SolverState solver) {
		solver.declareVars(support);
		if (definition == null) {
			if (smtFile != null) {
				try {
					return solver.getSolver().readFile(new File(smtFile)).isOK();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				throw new IllegalStateException("No definition for problem " + name);
			}
		} else {
			IResponse response = definition.execute(solver.getSolver());
			return response.isOK();
		}
		return false;
	}
	
	
	public void updateWitness (SolverState solver, String prefix) {
		solution.updateWitness(solver, prefix);
	}
	
	public void updateStatus(SolverState solver, DoneProperties doneProps) {
		ISymbol sym = refinedSymbol != null ? refinedSymbol : symbol;
		String textReply = SMTUtils.checkSatAssuming(solver.getSolver(), sym);
		
		if ("unsat".equals(textReply)) {
			System.out.println("Problem " + name + " is UNSAT");
			solution.setReply(SMTReply.UNSAT);
			if (isEF) {
				doneProps.put(getName(), false, "SMT_REFINEMENT");
			} else {
				doneProps.put(getName(), true, "SMT_REFINEMENT");				
			}
		} else if ("sat".equals(textReply)) {
			solution.setReply(SMTReply.SAT);
		} else {
			solution.setReply(SMTReply.UNKNOWN);
		}
	}
	
	
	public boolean isSolved() {
		return solution.getReply() == SMTReply.UNSAT || solution.getReply() == SMTReply.REACHABLE;
	}
	
	public CandidateSolution getSolution() {
		return solution;
	}

	@Override
	public String toString() {
		return "(" + symbol + "=" + solution + ")";
	}
	
	public String getName() {
		return name;
	}
	
	public Expression getPredicate() {
		return predicate;
	}
	
	public boolean isEF() {
		return isEF;
	}
	
	public void dropRefinement() {
		refinedSymbol = null;
    }
}