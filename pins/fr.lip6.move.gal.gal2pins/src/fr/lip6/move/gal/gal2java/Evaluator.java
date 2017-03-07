package fr.lip6.move.gal.gal2java;

import java.util.logging.Logger;

import fr.lip6.move.gal.And;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.semantics.VariableIndexer;
import fr.lip6.move.gal.util.GalSwitch;

public class Evaluator extends GalSwitch<Integer> {

	private IState state;

	private VariableIndexer index;
	
	public Evaluator(VariableIndexer index) {
		this.index = index;
	}
	
	public void setState(IState state) {
		this.state = state;
	}
	
	@Override
	public Integer caseBinaryIntExpression(BinaryIntExpression bin) {
		int l = doSwitch(bin.getLeft());
		int r = doSwitch(bin.getRight());
		
		int res = 0;
		if ("+".equals(bin.getOp())) {
			res = l + r;
		} else if ("-".equals(bin.getOp())) {
			res = l - r;
		} else if ("/".equals(bin.getOp())) {
			res = l / r;
		} else if ("*".equals(bin.getOp())) {
			res = l * r;
		} else if ("**".equals(bin.getOp())) {
			res = (int) Math.pow(l , r);
		} else if ("%".equals(bin.getOp())) {
			res = l % r;
		} else if ("<<".equals(bin.getOp())) {
			res = l << r;
		} else if (">>".equals(bin.getOp())) {
			res = l >> r;
		} else if ("|".equals(bin.getOp())) {
			res = l | r;
		} else if ("&".equals(bin.getOp())) {
			res = l & r;
		} else if ("^".equals(bin.getOp())) {
			res = l ^ r;
		} else {
			getLog().warning("Unexpected operator in evaluate procedure:" + bin.getOp());
			throw new RuntimeException("Missing case in switch ");
		}
		
		return res;
	}
	
	@Override
	public Integer caseTrue(True object) {
		return 1;
	}
	
	@Override
	public Integer caseFalse(False object) {
		return 0;
	}
	
	@Override
	public Integer caseNot(Not not) {
		int n = doSwitch(not.getValue());		
		return n==0 ? 1 : 0;
	}
	
	@Override
	public Integer caseOr(Or or) {
		int l = doSwitch(or.getLeft());
		if (l==1) {
			return 1;
		} else {
			return doSwitch(or.getRight());
		}
	}

	@Override
	public Integer caseAnd(And and) {
		int l = doSwitch(and.getLeft());
		if (l==0) {
			return 0;
		} else {
			return doSwitch(and.getRight());
		}
	}
	
	@Override
	public Integer caseComparison(Comparison cmp) {
		int l = doSwitch(cmp.getLeft());
		int r = doSwitch(cmp.getRight());
		switch (cmp.getOperator()) {
		case EQ : return l==r ? 1 : 0;
		case LT : return l<r  ? 1 : 0;
		case GT : return l>r  ? 1 : 0;
		case LE : return l<=r ? 1 : 0;
		case GE : return l>=r ? 1 : 0;
		case NE : return l!=r ? 1 : 0;
		default :
			getLog().warning("Unexpected operator in evaluate procedure:" + cmp.getOperator());
			throw new RuntimeException("Missing case in switch ");
		}
	}

	
	@Override
	public Integer caseConstant(Constant cte) {
		return Instantiator.evalConst(cte);
	}
	
	public int getIndex (VariableReference vref) {
		int vi = index.getIndex(vref.getRef().getName());
		int shift = 0;
		if (vref.getIndex() != null) {
			shift = doSwitch(vref.getIndex());
		}
		return vi + shift ;
	}
	
	@Override
	public Integer caseVariableReference(VariableReference vref) {
		return state.get(getIndex(vref));
	}
	
	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}


}
