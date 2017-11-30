package fr.lip6.gal.gal2smt.tosmt;

import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.ISymbol;

import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.semantics.Assign;
import fr.lip6.move.gal.semantics.LeafNextVisitor;
import fr.lip6.move.gal.semantics.Predicate;
import fr.lip6.move.gal.semantics.VariableIndexer;

public class NextTranslator implements LeafNextVisitor<IExpr> {

	private IExpr state;
	private GalExpressionTranslator et;
	
	public NextTranslator(IExpr cur, GalExpressionTranslator et) {
		state = cur;
		this.et = et;
	}
	
	
	public IExpr getState() {
		return state;
	}

	@Override
	public IExpr visit(Assign ass) {
		// use current statement indexer to resolve variables
		VariableIndexer index = ass.getIndexer();
		et.setIndex(index);
		
		IFactory efactory = et.getEfactory();
		
		// find the index of lhs
		VariableReference vref = ass.getAssignment().getLeft();
		IExpr indexlhs = efactory.numeral(index.getIndex(vref.getRef().getName())); 
		if (vref.getIndex() != null) {
			IExpr offset = et.translate(vref.getIndex(), state);
			
			indexlhs = efactory.fcn(efactory.symbol("+"), indexlhs, offset);
		}
		
		// find the value of rhs
		IExpr rhs = et.translate(ass.getAssignment().getRight(), state);
		
		switch (ass.getAssignment().getType()) {
		case INCR :
			rhs = efactory.fcn( efactory.symbol("+"), et.translate(ass.getAssignment().getLeft(), state), rhs);
			break;
		case DECR :
			rhs = efactory.fcn( efactory.symbol("-"), et.translate(ass.getAssignment().getLeft(), state), rhs);
			break;
		default :
			break;
		}
		
		// update "state" attribute for next statements
		// state = store(state,  getIndex(lhs), rhs)
		final ISymbol store = efactory.symbol("store");
		IExpr updstate = efactory.fcn(store, state, indexlhs, rhs);
		state = updstate;
		
		// no new conditions
		return null;
	}

	@Override
	public IExpr visit(Predicate pred) {
		// use current statement indexer to resolve variables
		VariableIndexer index = pred.getIndexer();
		et.setIndex(index);
		
		// create a new condition over current state and return it
		return et.translateBool(pred.getGuard(), state);
	}

}
