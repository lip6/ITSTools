package fr.lip6.move.petrispot.runner;

import fr.lip6.move.gal.structural.expr.ArrayVarRef;
import fr.lip6.move.gal.structural.expr.AtomicPropRef;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.BoolConstant;
import fr.lip6.move.gal.structural.expr.Constant;
import fr.lip6.move.gal.structural.expr.ExprVisitor;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;
import fr.lip6.move.gal.structural.expr.ParamRef;
import fr.lip6.move.gal.structural.expr.TransRef;
import fr.lip6.move.gal.structural.expr.VarRef;

/**
 * Prints state predicates in the s-expression syntax read by PetriSpot's
 * {@code --props} option (PetriSpot INTEROP.md section 4), over indices:
 * places are p&lt;i&gt;, transitions t&lt;i&gt;.
 *
 * <p>Supported: boolean constants, and/or/not, comparisons, integer
 * constants, places, sums, differences, products with a constant, and
 * ENABLED over transitions (printed as {@code (fireable t...)}). Anything
 * else (division, modulo, atomic proposition references, parameters) throws
 * {@link UnsupportedOperationException}: the caller then keeps the Java
 * walker.
 */
public class SexprPropertyPrinter implements ExprVisitor<Void> {

	private final StringBuilder sb = new StringBuilder();

	/** {@code (reach NAME predicate)}: a state satisfying predicate answers TRUE. */
	public static String reach(String name, Expression predicate) {
		SexprPropertyPrinter p = new SexprPropertyPrinter();
		p.sb.append("(reach ").append(name).append(' ');
		predicate.accept(p);
		p.sb.append(')');
		return p.sb.toString();
	}

	/**
	 * {@code (bound NAME expr [k])}: maximise expr, a weighted sum of places;
	 * k, when non-negative, is a known upper bound whose value ends the target.
	 */
	public static String bound(String name, Expression expr, int knownBound) {
		SexprPropertyPrinter p = new SexprPropertyPrinter();
		p.sb.append("(bound ").append(name).append(' ');
		expr.accept(p);
		if (knownBound >= 0) p.sb.append(' ').append(knownBound);
		p.sb.append(')');
		return p.sb.toString();
	}

	/** {@code (deadlock NAME)}: a dead marking answers TRUE. */
	public static String deadlock(String name) {
		return "(deadlock " + name + ")";
	}

	private void list(String head, Expression... kids) {
		sb.append('(').append(head);
		for (Expression k : kids) {
			sb.append(' ');
			k.accept(this);
		}
		sb.append(')');
	}

	@Override
	public Void visit(BinOp binOp) {
		switch (binOp.getOp()) {
		case AND: list("and", binOp.left, binOp.right); break;
		case OR: list("or", binOp.left, binOp.right); break;
		case NOT: list("not", binOp.left); break;
		case ADD: list("+", binOp.left, binOp.right); break;
		case MINUS: list("-", binOp.left, binOp.right); break;
		case MULT: list("*", binOp.left, binOp.right); break;
		case EQ: list("==", binOp.left, binOp.right); break;
		case NEQ: list("!=", binOp.left, binOp.right); break;
		case LT: list("<", binOp.left, binOp.right); break;
		case LEQ: list("<=", binOp.left, binOp.right); break;
		case GEQ: list(">=", binOp.left, binOp.right); break;
		case GT: list(">", binOp.left, binOp.right); break;
		default:
			throw new UnsupportedOperationException("Operator not supported by the PetriSpot walker: " + binOp.getOp());
		}
		return null;
	}

	@Override
	public Void visit(NaryOp naryOp) {
		String head;
		switch (naryOp.getOp()) {
		case AND: head = "and"; break;
		case OR: head = "or"; break;
		case ADD: head = "+"; break;
		case ENABLED: head = "fireable"; break;
		case MULT: {
			// the reader takes binary products: fold (* a b c) into (* a (* b c))
			int n = naryOp.nbChildren();
			for (int i = 0; i < n - 1; i++) {
				sb.append("(* ");
				naryOp.childAt(i).accept(this);
				sb.append(' ');
			}
			naryOp.childAt(n - 1).accept(this);
			for (int i = 0; i < n - 1; i++) sb.append(')');
			return null;
		}
		default:
			throw new UnsupportedOperationException("Operator not supported by the PetriSpot walker: " + naryOp.getOp());
		}
		sb.append('(').append(head);
		for (int i = 0, ie = naryOp.nbChildren(); i < ie; i++) {
			sb.append(' ');
			naryOp.childAt(i).accept(this);
		}
		sb.append(')');
		return null;
	}

	@Override
	public Void visit(VarRef varRef) {
		sb.append('p').append(varRef.getValue());
		return null;
	}

	@Override
	public Void visit(TransRef transRef) {
		sb.append('t').append(transRef.getValue());
		return null;
	}

	@Override
	public Void visit(Constant constant) {
		sb.append(constant.getValue());
		return null;
	}

	@Override
	public Void visitBool(BoolConstant boolConstant) {
		sb.append(boolConstant.getValue() != 0 ? "true" : "false");
		return null;
	}

	@Override
	public Void visit(ParamRef paramRef) {
		throw new UnsupportedOperationException("Parameter reference in a state predicate: " + paramRef);
	}

	@Override
	public Void visit(ArrayVarRef arrayVarRef) {
		throw new UnsupportedOperationException("Array reference in a state predicate: " + arrayVarRef);
	}

	@Override
	public Void visit(AtomicPropRef apRef) {
		throw new UnsupportedOperationException("Atomic proposition reference in a state predicate: " + apRef);
	}
}
