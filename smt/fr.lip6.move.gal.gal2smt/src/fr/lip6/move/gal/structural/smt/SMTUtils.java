package fr.lip6.move.gal.structural.smt;

import java.util.List;

import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.SMT;

public class SMTUtils {

	public static IExpr makeAnd(List<IExpr> list) {
		IFactory ef = new SMT().smtConfig.exprFactory;
		list.removeIf(e -> e instanceof ISymbol && "true".equals(((ISymbol) e).value()));
		if (list.isEmpty()) {
			return ef.symbol("true");
		} else if (list.size()==1) {
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
		} else if (list.size()==1) {
			return list.get(0);
		} else {
			return ef.fcn(ef.symbol("or"), list);
		}
	}

}
