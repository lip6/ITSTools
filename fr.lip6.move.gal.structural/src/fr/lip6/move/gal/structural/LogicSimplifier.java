package fr.lip6.move.gal.structural;

import java.util.List;
import java.util.logging.Logger;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class LogicSimplifier {

	public static int simplifyWithInitial(List<Property> properties, SparseIntArray spinit) {

		int simplified = 0;
		for (Property prop : properties) {
			switch (prop.getType()) {
			case LTL:
			case CTL:
			case INVARIANT: {
				Expression body = prop.getBody();
				if (body.getOp() == Op.BOOLCONST) {
					continue;
				}
				// simplify(body);

				int eval = evalInInitial(body, spinit);
				if (eval == 1) {
					prop.setBody(Expression.constant(true));
				} else if (eval == -1) {
					prop.setBody(Expression.constant(false));
				}
				if (eval != 0) {
					simplified++;
				}
			}
			default:
				continue;
			}
		}
		if (simplified != 0) {
			System.out.println("Initial state reduction rules removed " + simplified + " formulas.");
		}
		return simplified;
	}

	/**
	 * This function is an adaptation to LTL/CTL of the Initial states simplification
	 * strategy proposed in Section 3 of the paper : Simplification of CTL Formulae
	 * for Efficient Model Checking of Petri Nets Published at IC PetriNets'2018 By
	 * Bonneland, et al. of Jiri Srba's group working on Tapaal.
	 */
	private static int evalInInitial(Expression predicate, SparseIntArray init) {
		switch (predicate.getOp()) {

		case BOOLCONST: {
			if (predicate.getValue() == 1) {
				return 1;
			} else {
				return -1;
			}
		}
		case LT:case LEQ:case EQ:case NEQ:case GEQ:case GT: {
			if (predicate.eval(init) == 1) {
				return 1;
			} else {
				return -1;
			}
		}
		case NOT:
			return -evalInInitial(predicate.childAt(0), init);
		case AND: {
			boolean found0 = false;

			for (int i = 0; i < predicate.nbChildren(); i++) {
				Expression c = predicate.childAt(i);
				int value = evalInInitial(c, init);

				if (value == -1) {
					// we are also -1
					return -1;
				} else if (value == 0) {
					found0 = true;
				}
			}

			if (found0) {
				return 0;
			} else {
				return 1;
			}
		}
		case OR: {
			boolean found0 = false;

			for (int i = 0; i < predicate.nbChildren(); i++) {
				Expression c = predicate.childAt(i);
				int value = evalInInitial(c, init);

				if (value == 1) {
					// we are also 1
					return 1;
				} else if (value == 0) {
					found0 = true;
				}
			}

			if (found0) {
				return 0;
			} else {
				return -1;
			}
		}
		case X:case EX:case AX:
			return 0;
		case G:case EG:case AG:{
			if (evalInInitial(predicate.childAt(0), init) == -1) {
				return -1;
			} else {
				return 0;
			}
		}
		case F:case EF:case AF:{
			if (evalInInitial(predicate.childAt(0), init) == 1) {
				return 1;
			} else {
				return 0;
			}
		}
		case U:case EU:case AU: {
			int evalr = evalInInitial(predicate.childAt(1), init);
			if (evalr == 1) {
				return 1;
			} else if (evalr == -1) {
				if (evalInInitial(predicate.childAt(0), init) == -1) {
					return -1;
				}
			}
			return 0;			
		}
		default:
			Logger.getLogger("fr.lip6.move.gal").warning("When simplifiying with initial state, unexpected operator in formula :" + predicate.getOp());
			return 0;
		}		
	}

}
