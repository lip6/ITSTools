package fr.lip6.gal.gal2smt.tosmt;

import org.smtlib.IExpr;
import org.smtlib.SMT.Configuration;

import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.semantics.INextBuilder;

public class QualifiedExpressionTranslator extends ExpressionTranslator {

	public QualifiedExpressionTranslator(Configuration conf) {
		super(conf);
	}
	
	private INextBuilder nb;
	public void setNb(INextBuilder nb) {
		this.nb = nb;
	}

	@Override
	protected IExpr handleReference(Reference expr, IExpr state) {
		return efactory.fcn(efactory.symbol("select"), state, efactory.numeral(nb.getIndex(expr)));
	}

}
