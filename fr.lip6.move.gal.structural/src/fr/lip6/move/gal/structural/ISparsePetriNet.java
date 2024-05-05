package fr.lip6.move.gal.structural;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.util.IntMatrixCol;

public interface ISparsePetriNet {

	List<Integer> getMarks();

	IntMatrixCol getFlowTP();

	IntMatrixCol getFlowPT();

	int getPlaceCount();

	int getTransitionCount();

	List<String> getTnames();

	List<String> getPnames();

	int getMaxArcValue();

	BitSet computeSupport();

	boolean isSafe();

	void setSafe(boolean isSafe);

	default void toPredicates(List<Property> properties) {
		for (Property prop : properties) {
			prop.setBody(replacePredicates(prop.getBody()));
		}
	}
	
	default Expression replacePredicates(Expression expr) {
		if (expr == null) {
			return expr;
		} else if (expr instanceof BinOp) {
			BinOp bin = (BinOp) expr;
			if (bin.getOp() == Op.DEAD) {
				return Expression.not(Expression.op(Op.EX, Expression.constant(true), null));
			}
			Expression l = replacePredicates(bin.left);
			Expression r = replacePredicates(bin.right);
			if (l == bin.left && r == bin.right) {
				return expr;
			}
			return Expression.op(bin.op, l, r);
		} else if (expr instanceof NaryOp) {
			NaryOp nop = (NaryOp) expr;
			List<Expression> resc = new ArrayList<>();
			if (expr.getOp() == Op.CARD || expr.getOp() == Op.BOUND) {
				for (Expression child : nop.getChildren()) {
					if (child.getOp() == Op.PLACEREF) {
						resc.add(Expression.var(child.getValue()));
					} else {
						resc.add(child);
					}
				}
				return Expression.nop(Op.ADD, resc);
			} else if (expr.getOp() == Op.ENABLED) {
				Set<SparseIntArray> pres = new HashSet<>();
				int skipped = 0;
				for (Expression child : nop.getChildren()) {
					if (child.getOp() == Op.TRANSREF) {
						int tid = child.getValue();
						if (!pres.add(getFlowPT().getColumn(tid))) {
							skipped++;
						}
					}					
				}
				if (skipped > 0) {
					Logger.getLogger("fr.lip6.move.gal").info("Reduced "+skipped+" identical enabling conditions.");
				}
				for (SparseIntArray pre : pres) {
					List<Expression> conds = new ArrayList<>();
					for (int i=0,ie=pre.size();i<ie;i++) {
						conds.add(Expression.op(Op.GEQ, Expression.var(pre.keyAt(i)), Expression.constant(pre.valueAt(i))));
					}
					resc.add(Expression.nop(Op.AND, conds));
				}
				return Expression.nop(Op.OR, resc);				
			} else {
				boolean changed = false;
				for (Expression child : nop.getChildren()) {
					Expression nc = replacePredicates(child);
					resc.add(nc);
					if (nc != child) {
						changed = true;
					}
				}
				if (!changed) {
					return expr;
				}
				return Expression.nop(nop.getOp(), resc);
			}
		} 
		return expr;
	}
}
