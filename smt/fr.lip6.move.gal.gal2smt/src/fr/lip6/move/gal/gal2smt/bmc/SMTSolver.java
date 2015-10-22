package fr.lip6.move.gal.gal2smt.bmc;

import java.util.logging.Logger;

import org.smtlib.IExpr;
import org.smtlib.IPrinter;
import org.smtlib.IResponse;
import org.smtlib.ISolver;
import org.smtlib.ISort;
import org.smtlib.IExpr.IFactory;
import org.smtlib.SMT.Configuration;
import org.smtlib.impl.Script;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;

public abstract class SMTSolver implements ISMTSolver {

	protected final Solver engine;
	protected final Configuration conf;
	protected ISolver solver;
	protected final IFactory efactory;
	protected final ISort.IFactory sortfactory ;
	protected final IVariableHandler vh;
	protected final ExpressionTranslator et;

	
	public SMTSolver (Solver engine, Configuration smtConfig, IVariableHandler vh) {
		this.conf = smtConfig;
		this.efactory = smtConfig.exprFactory;
		this.sortfactory = smtConfig.sortFactory;
		this.vh = vh;
		this.et = new ExpressionTranslator(smtConfig, vh);
		this.engine = engine;
	}

	@Override
	public void init(Specification spec) {
		solver = engine.getSolver(conf);
		// start the solver
		IResponse err = solver.start();
		if (err.isError()) {
			throw new RuntimeException("Could not start solver "+ engine+" from path "+ conf.executable);
		}
		
		// declare logic + headers
		Script script = new Script();

		// Logic + options
		script.commands().add(new org.smtlib.command.C_set_option(efactory .keyword(":produce-models"), efactory.symbol("true")));
		script.commands().add(new org.smtlib.command.C_set_logic(efactory.symbol("QF_AUFLIA")));

		if (spec.getMain() instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) spec.getMain();

			vh.declareVariables(script, gal);
		}
		
		err = script.execute(solver);
		if (err.isError()) {
			throw new RuntimeException("Error when declaring system variables to SMT solver.");
		}

	}

	@Override
	public void exit() {
		IResponse res = solver.exit();
		if (res.isError()) {
			Logger.getLogger("fr.lip6.move.gal").info("SMT solver already quit.");
		}
	}
	
	public Result checkSat() {
		IResponse res = solver.check_sat();
		if (res.isError()) {
			throw new RuntimeException("SMT solver raised an exception or timeout.");
		}
		IPrinter printer = conf.defaultPrinter;
	//	System.out.println(printer.toString(script));
		String textReply = printer.toString(res);
		System.out.println(printer.toString(res));
		if ("sat".equals(textReply)) {
			return Result.SAT;
		} else if ("unsat".equals(textReply)) {
			return Result.UNSAT;
		} else {
			throw new RuntimeException("SMT solver raised an error :" + textReply);
		}		
	}

	public Result verifyAssertion(IExpr sprop) {
		solver.push(1);
		solver.assertExpr(sprop);
		Result res = checkSat();
		solver.pop(1);
		return res;
	}


}
