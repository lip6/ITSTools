package fr.lip6.move.gal.structural.smt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.smtlib.ICommand;
import org.smtlib.ICommand.Icheck_sat;
import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.IPrinter;
import org.smtlib.IResponse;
import org.smtlib.ISolver;
import org.smtlib.IVisitor;
import org.smtlib.IVisitor.TreeVisitor;
import org.smtlib.IVisitor.VisitorException;
import org.smtlib.SMT;
import org.smtlib.SMT.Configuration;
import org.smtlib.Utils;
import org.smtlib.command.C_assert;
import org.smtlib.command.C_check_sat;
import org.smtlib.command.C_check_sat_assuming;
import org.smtlib.impl.Script;

import android.util.SparseIntArray;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.AtomicPropRef;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.VarRef;
import fr.lip6.smt.z3.binaries.BinaryToolsPlugin;

public class SMTUtils {

	enum POType {
		Plunge, // use Nat or Real
		Forall, // use explicit predicates/constraints
		Partial // partial order defined in Z3
	}

	static final POType useAbstractDataType = POType.Plunge;
	static final Configuration smtConf = new SMT().smtConfig;
	private static final int DEBUG = 0;

	// utility class, don't instantiate
	private SMTUtils() {
	}

	public static Expression rewriteAfterEffect (Expression expr, SparseIntArray t, boolean before) {
		if (expr == null) {
			return null;
		} else if (expr instanceof VarRef) {
			VarRef vref = (VarRef) expr;
			int delta=t.get(vref.getValue());
			if (delta != 0) {
				if (before) {
					delta = -delta;
				}
				return Expression.nop(Op.ADD, expr, Expression.constant(delta));
			} else {
				return expr;
			}
		} else if (expr instanceof AtomicPropRef) {
			AtomicPropRef apr = (AtomicPropRef) expr;
			return rewriteAfterEffect(apr.getAp().getExpression(),t,before) ;			
		} else {
			List<Expression> resc = new ArrayList<>();
			boolean changed = false;
			for (int i=0,ie=expr.nbChildren(); i < ie; i++) {
				Expression child = expr.childAt(i);
				Expression nc = rewriteAfterEffect(child, t,before);
				resc.add(nc);
				if (nc != child) {
					changed = true;
				}
			}
			if (!changed) {
				return expr;
			}
			return Expression.nop(expr.getOp(), resc);			
		} 
	}

	public static Map<Integer, List<Integer>> computeImages(List<Integer> representative) {
		Map<Integer, List<Integer>> images = new HashMap<>();
		for (int i=0; i < representative.size() ; i++) {
			images.computeIfAbsent(representative.get(i), k -> new ArrayList<>()).add(i);
		}
		return images;
	}

	public static IExpr makeAnd(List<IExpr> list) {
		IFactory ef = new SMT().smtConfig.exprFactory;
		list.removeIf(e -> e instanceof ISymbol && "true".equals(((ISymbol) e).value()));
		if (list.isEmpty()) {
			return ef.symbol("true");
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			return ef.fcn(ef.symbol("and"), list);
		}
	}

	public static IExpr makeOr(List<IExpr> list) {
		IFactory ef = new SMT().smtConfig.exprFactory;
		list.removeIf(e -> e instanceof ISymbol && "false".equals(((ISymbol) e).value()));
		if (list.isEmpty()) {
			return ef.symbol("false");
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			return ef.fcn(ef.symbol("or"), list);
		}
	}

	/**
	 * Start an instance of a Z3 solver, with timeout at provided, logic QF_LIA/LRA,
	 * with produce models.
	 * 
	 * @param smt            the smt instance to configure/setup
	 * @param solveWithReals
	 * @return a started solver or throws a RuntimeEx
	 */
	public static ISolver initSolver(org.smtlib.SMT smt, boolean solveWithReals, int timeoutQ, int timeoutT) {
		if (useAbstractDataType == POType.Forall) {
			return initSolver(smt, "AUFLIRA", timeoutQ, timeoutT);
		} else if (useAbstractDataType == POType.Plunge) {
			if (solveWithReals) {
				return initSolver(smt, "QF_LRA", timeoutQ, timeoutT);
			} else {
				return initSolver(smt, "QF_LIA", timeoutQ, timeoutT);
			}
		} else {
			return initSolver(smt, null, timeoutQ, timeoutT);
		}
	}

	public static ISolver initSolver(org.smtlib.SMT smt, String logic, int timeoutQ, int timeoutT) {
		smt.smtConfig.executable = "";

		try {
			smt.smtConfig.executable = BinaryToolsPlugin.getProgramURI().getPath();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		smt.smtConfig.timeout = timeoutQ;
		smt.smtConfig.timeoutTotal = timeoutT;
		Solver engine = Solver.Z3;
		ISolver solver = engine.getSolver(smt.smtConfig);
		// start the solver
		IResponse err = solver.start();
		if (err.isError()) {
			throw new RuntimeException("Could not start solver " + engine + " from path " + smt.smtConfig.executable
					+ " raised error :" + err);
		}
		err = solver.set_option(smt.smtConfig.exprFactory.keyword(Utils.PRODUCE_MODELS),
				smt.smtConfig.exprFactory.symbol("true"));
		if (err.isError()) {
			throw new RuntimeException("Could not set :produce-models option :" + err);
		}
		err = solver.set_logic(logic, null);
		if (err.isError()) {
			throw new RuntimeException("Could not set logic" + err);
		}
	//	System.out.println("Starting Z3 with timeout " + timeoutT + " and query timeout " + timeoutQ);
		return solver;
	}

	public static IExpr buildSum(List<IExpr> torem) {
		IFactory ef = new SMT().smtConfig.exprFactory;
		IExpr lhs;
		if (torem.isEmpty()) {
			lhs = ef.numeral(0);
		} else {
			if (torem.size() == 1) {
				lhs = torem.get(0);
			} else {
				lhs = ef.fcn(ef.symbol("+"), torem);
			}
		}
		return lhs;
	}

	public static String checkSat(ISolver solver) {
		return checkSat(solver, false);
	}

	public static String checkSat(ISolver solver, boolean retry) {
		return checkSat(solver, new C_check_sat(), retry);
	}
	
	public static String checkSatAssuming(ISolver solver, IExpr... assumptions) {
		return checkSat(solver, new C_check_sat_assuming(assumptions), false);
	}
	
	public static String checkSat(ISolver solver, ICommand command, boolean retry) {
		IResponse res = command.execute(solver);
		IPrinter printer = smtConf.defaultPrinter;
		String textReply = printer.toString(res);
		if (DEBUG >= 2) {
			System.out.println(textReply);
		}
		if ("unknown".equals(textReply) && retry) {
			Logger.getLogger("fr.lip6.move.gal").info("SMT solver returned unknown. Retrying;");
			res = solver.check_sat();
			textReply = printer.toString(res);
		}
		if (res.isError()) {
			return "unknown";
		}
		return textReply;
	}

	public static Script declareBoolVariables(int nbvars, String prefix, SMT smt) {
		return declareVariables(nbvars, prefix, false, false, smt, "Bool");
	}

	public static Script declareVariables(int nbvars, String prefix, boolean isSafe, boolean isPositive,
			org.smtlib.SMT smt, boolean solveWithReals) {
		return declareVariables(nbvars, prefix, isSafe, isPositive, smt, solveWithReals ? "Real" : "Int");
	}

	public static Script declareVariables(int nbvars, String prefix, boolean isSafe, boolean isPositive,
			org.smtlib.SMT smt, String type) {
		Script script = new Script();
		IFactory ef = smt.smtConfig.exprFactory;
		org.smtlib.ISort.IApplication ints2 = smt.smtConfig.sortFactory.createSortExpression(ef.symbol(type));

		for (int i = 0; i < nbvars; i++) {
			ISymbol si = ef.symbol(prefix + i);
			script.add(new org.smtlib.command.C_declare_fun(si, Collections.emptyList(), ints2));
			if (isPositive) {
				script.add(new C_assert(ef.fcn(ef.symbol(">="), si, ef.numeral(0))));
			}
			if (isSafe) {
				script.add(new C_assert(ef.fcn(ef.symbol("<="), si, ef.numeral(1))));
			}
		}
		return script;
	}

	/**
	 * Creates and returns a script declaring N natural integer variables, with
	 * names prefix0 to prefixN-1. * If isSafe is true the upper bound is set to 1
	 * (so they are 0 or 1 ~ boolean variables in effect).
	 * 
	 * @param nbvars         the number of variables to add in the script
	 * @param prefix         the prefix used in building variable names
	 * @param isSafe         do we have an upper bound of 1 on these variables
	 *                       (lower bound 0 is always applied)
	 * @param smt            access to the smt factories
	 * @param solveWithReals
	 * @return a script containing declaration + constraints on a set of variables.
	 */
	public static Script declareVariables(int nbvars, String prefix, boolean isSafe, org.smtlib.SMT smt,
			boolean solveWithReals) {
		return declareVariables(nbvars, prefix, isSafe, true, smt, solveWithReals);
	}

	public static void execAndCheckResult(Script script, ISolver solver) {
		if (DEBUG >= 2) {
			for (ICommand a : script.commands()) {
				System.out.println(a);
			}
		}
		long time = System.currentTimeMillis();
		for (ICommand a : script.commands()) {
			if (a instanceof Icheck_sat) {
				String textResp = checkSat(solver);
				// checkpoint
				if ("unsat".equals(textResp) || "unknown".equals(textResp)) {
					return;
				}
			} else {
				IResponse res = a.execute(solver);
				if (res.isError()) {
					String ress = res.toString();
					throw new RuntimeException("SMT solver raised an error when submitting script. Raised "
							+ ress.substring(0, Math.min(70, ress.length())) + "...");
				}
			}
		}
	}
	
	public static SparseIntArray computeSupport(Expression ap) {
		// compute support of e
		SparseIntArray supp;
		{
			BitSet suppbs = new BitSet();
			SparsePetriNet.addSupport(ap,suppbs);
			supp=new SparseIntArray(suppbs);
		}
		return supp;
	}

	private static class VarCollector extends TreeVisitor<Void> implements IVisitor<Void> {
		public VarSet support = new VarSet();
		
		@Override
		public Void visit(ISymbol e) throws VisitorException {
			char c = e.value().charAt(0);
			if (c == 's' || c == 't') {
				int index = Integer.parseInt(e.value().substring(1));
				support.addVar(c+"", index);
			}
			return null;
		}
	}
	
	public static VarSet computeSupport(IExpr pred) {
		VarCollector vc = new VarCollector();
		try {
			pred.accept(vc);
		} catch (VisitorException e) {
			e.printStackTrace();
		}
		return vc.support;
	}

}
