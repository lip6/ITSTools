package fr.lip6.move.gal.pn2pins;

import java.io.PrintWriter;
import java.util.Map;

import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.CExpressionPrinter;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;

public class PropertyPrinter extends CExpressionPrinter {

	private Map<Expression, AtomicProp> atomMap;

	public PropertyPrinter(PrintWriter pw, String prefix, Map<Expression, AtomicProp> atomMap) {
		super(pw, prefix);
		this.atomMap = atomMap;
	}

	private boolean testAtom(Expression e) {
		AtomicProp atom = atomMap.get(e);
		if (atom != null) {
			pw.print("(" + atom.getName() + "==true)");
			return true;
		}
		return false;
	}

	@Override
	public Void visit(BinOp binOp) {
		if (!testAtom(binOp)) {
			switch (binOp.getOp()) {
			case F:
				pw.print("<>(");
				binOp.left.accept(this);
				pw.print(")");
				break;
			case G:
				pw.print("[](");
				binOp.left.accept(this);
				pw.print(")");
				break;
			case X:
				pw.print("X(");
				binOp.left.accept(this);
				pw.print(")");
				break;
			case U:
				infix(binOp, " U ");
				break;
			default:
				super.visit(binOp);
			}
		}
		return null;
	}

	@Override
	public Void visit(NaryOp naryOp) {
		if (!testAtom(naryOp)) {
			super.visit(naryOp);
		}
		return null;
	}

}
