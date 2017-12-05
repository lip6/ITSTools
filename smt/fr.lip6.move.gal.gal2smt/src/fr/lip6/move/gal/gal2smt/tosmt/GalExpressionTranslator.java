package fr.lip6.move.gal.gal2smt.tosmt;

import org.smtlib.IExpr;
import org.smtlib.IExpr.IFcnExpr;
import org.smtlib.IExpr.INumeral;
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
			int indi = -1;
			if (indexlhs instanceof INumeral) {
				indi = ((INumeral) indexlhs).intValue();
			}
			if (vref.getIndex() != null) {
				IExpr offset = translate(vref.getIndex(), state);
				if (offset instanceof INumeral) {
					indi += ((INumeral) offset).intValue();
				} else {
					indi = -1;
				}
				indexlhs = efactory.fcn(efactory.symbol("+"), indexlhs, offset);
			}
			if (indi != -1) {
				// looking for :
				// (select (store state indj rhs) indi); and indi/indj are ints, and indi!=indj
//				(select (store state indj rhs) indi) 
//				with indi, indj integers and indi != inj
//				->
//				(select state indi)

				if (state instanceof IFcnExpr) {
					IFcnExpr sfcn = (IFcnExpr) state;
					if (sfcn.head().headSymbol().toString().equals("store") 
							&& sfcn.args().get(1) instanceof INumeral) {
						if (((INumeral) sfcn.args().get(1)).intValue() != indi) {
							// skip one level
							// recurse on nested array
							return handleReference(ref,sfcn.args().get(0));
						}
					}
				}
			}
			
			return efactory.fcn(select, state, indexlhs);
		}
		throw new UnsupportedOperationException("Cannot handle qualified refs in GAL !");
	}

}
