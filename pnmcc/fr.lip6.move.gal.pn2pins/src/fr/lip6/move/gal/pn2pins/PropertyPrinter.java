package fr.lip6.move.gal.pn2pins;

import java.io.PrintWriter;

import fr.lip6.move.gal.structural.expr.AtomicPropRef;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.CExpressionPrinter;

public class PropertyPrinter extends CExpressionPrinter {

	private boolean forSpot;

	public PropertyPrinter(PrintWriter pw, String prefix, boolean forSpot) {
		super(pw, prefix);
		this.forSpot = forSpot;
	}

	@Override
	public Void visit(BinOp binOp) {
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
		return null;
	}
	
	@Override
	public Void visit(AtomicPropRef apRef) {
		if (forSpot) {
			pw.print("LTLAP"+apRef.getAp().getName());
		} else {
			pw.print("(LTLAP" + apRef.getAp().getName() + "==true)");
		}
		return null;
	}

}
