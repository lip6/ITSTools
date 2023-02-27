package fr.lip6.move.gal.mcc.properties;

import java.io.PrintWriter;

import fr.lip6.move.gal.structural.expr.AtomicPropRef;
import fr.lip6.move.gal.structural.expr.TransRef;
import fr.lip6.move.gal.structural.expr.VarRef;

public class LTSMinPropertyPrinter extends PropertyPrinter {

	public LTSMinPropertyPrinter(PrintWriter pw) {
		super(pw, "p", false);
	}
	
	@Override
	public Void visit(AtomicPropRef apRef) {
		return apRef.getAp().getExpression().accept(this);		
	}
	

	@Override
	public Void visit(TransRef transRef) {
		pw.append("t"+transRef.getValue());
		return null;
	}

	@Override
	public Void visit(VarRef varRef) {
		pw.append("p").append(Integer.toString(varRef.getValue()));
		return null;
	}
	
}
