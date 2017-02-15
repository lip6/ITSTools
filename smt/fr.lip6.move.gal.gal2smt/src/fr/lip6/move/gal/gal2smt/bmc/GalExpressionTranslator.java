package fr.lip6.move.gal.gal2smt.bmc;

import org.smtlib.IExpr;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.SMT.Configuration;

import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.semantics.VariableIndexer;

public class GalExpressionTranslator extends ExpressionTranslator {

	public GalExpressionTranslator(Configuration conf) {
		super(conf);
	}


	private VariableIndexer index;
	public void setIndex(VariableIndexer index) {
		this.index = index;
	}

	@Override
	protected IExpr handleReference(Reference ref, IExpr state) {
		/* Reference */
		if (ref instanceof VariableReference) {
			VariableReference vref = (VariableReference) ref;
			ISymbol select = efactory.symbol("select");
			IExpr indexlhs = efactory.numeral(index.getIndex(vref.getRef().getName()));
			if (vref.getIndex() != null) {
				IExpr offset = translate(vref.getIndex(), state);

				indexlhs = efactory.fcn(efactory.symbol("+"), indexlhs, offset);
			}
			return efactory.fcn(select, state, indexlhs);
		}
		throw new UnsupportedOperationException("Cannot handle qualified refs in GAL !");
	}

}
