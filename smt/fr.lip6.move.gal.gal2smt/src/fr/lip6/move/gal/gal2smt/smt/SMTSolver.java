package fr.lip6.move.gal.gal2smt.smt;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Logger;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IPrinter;
import org.smtlib.IResponse;
import org.smtlib.ISolver;
import org.smtlib.ISort;
import org.smtlib.IVisitor;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IResponse.IValueResponse;
import org.smtlib.IVisitor.VisitorException;
import org.smtlib.SMT.Configuration;
import org.smtlib.Utils;
import org.smtlib.command.C_get_value;
import org.smtlib.impl.Script;
import org.smtlib.sexpr.ISexpr;
import org.smtlib.sexpr.Printer;
import org.smtlib.sexpr.ISexpr.ISeq;

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
	private boolean shouldShow;

	
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
		
		// Logic + options
		err = solver.set_option(efactory.keyword(Utils.PRODUCE_MODELS), efactory.symbol("true"));
		if (err.isError()) {
			throw new RuntimeException("Could not set :produce-models option "+err);
		}
		err = solver.set_logic("QF_AUFLIA", null);
		if (err.isError()) {
			throw new RuntimeException("Could not set logic "+err);
		}
		// declare logic + headers
		Script script = new Script();
		if (spec.getMain() instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) spec.getMain();

			vh.declareVariables(script, gal);
		}
		
		err = script.execute(solver);
		if (err.isError()) {
			throw new RuntimeException("Error when declaring system variables to SMT solver."+conf.defaultPrinter.toString(err));
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
	//	System.out.println(printer.toString(res));
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
		if (res == Result.SAT) {
			onSat(solver);
		}
		solver.pop(1);
		return res;
	}

	protected void onSat(ISolver solver) {
		
		if (shouldShow) {
			ICommand getVals = new C_get_value(listVariablesToShow()); 
			IResponse state = getVals.execute(solver);
			//		if (state.isOK()) {
			StringWriter w = new StringWriter();
			Printer printer = new Printer(w) {
				final IExpr zero = efactory.numeral(0);
				@Override
				public Void visit(ISeq e)
						throws org.smtlib.IVisitor.VisitorException {
					if (e.sexprs().size() == 2 && e.sexprs().get(1).equals(zero)) {
						return null;
					}
					try {
						w.append("(");
						for (ISexpr expr: e.sexprs()) { 
							expr.accept(this); 
						} 
						w.append(" )");
					} catch (IOException ex) { throw new IVisitor.VisitorException(ex); }
					return null;
				}
				@Override
				public Void visit(IValueResponse e)
						throws org.smtlib.IVisitor.VisitorException {
					try {
						w.append("(");
						for (IResponse.IPair<IExpr,IExpr> p : e.values()) {
							if (! p.second().equals(zero)) {
								w.append("(");
								p.first().accept(this);
								w.append(" ");
								p.second().accept(this);
								w.append(")");
							}
						}
						w.append(")");
					} catch (IOException ex) {
						throw new IVisitor.VisitorException(ex);
					}
					return null;
				}
			};
			try {
				state.accept(printer);
			} catch (VisitorException e1) {
				e1.printStackTrace();
			}
			Logger.getLogger("fr.lip6.move.gal").info("SAT in state (no zeros shown ) :" + w.toString() );// TODO Auto-generated method stub
		}		
	}

	public List<IExpr> listVariablesToShow() {
		return vh.getAllAccess();
	}

	public void setShowSatState(boolean shouldShow) {
		this.shouldShow = shouldShow;
	}

}
