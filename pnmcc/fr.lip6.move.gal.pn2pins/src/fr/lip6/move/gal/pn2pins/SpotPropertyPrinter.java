package fr.lip6.move.gal.pn2pins;

import java.io.PrintWriter;
import java.util.List;

import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.CExpressionPrinter;
import fr.lip6.move.gal.structural.expr.VarRef;

public class SpotPropertyPrinter extends CExpressionPrinter {

	private List<String> pnames;

	public SpotPropertyPrinter(PrintWriter pw, String prefix, List<String> pnames) {
		super(pw, prefix);
		this.pnames = pnames;
	}

	@Override
	public Void visit(VarRef varRef) {
		pw.append(pnames.get(varRef.getValue())); // are quotes useful "\""
		return null;
	}
	
	@Override
	public Void visit(BinOp binOp) {
			switch (binOp.getOp()) {
			case F:
				pw.print("F(");
				binOp.left.accept(this);
				pw.print(")");
				break;
			case G:
				pw.print("G(");
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
			case EQ:
				quoteInfix(binOp, "==");
				break;
			case NEQ :
			{
				quoteInfix(binOp, "!=");
				break;			
			}
			case LT :
			{
				quoteInfix(binOp, "<");
				break;			
			}
			case LEQ :
			{
				quoteInfix(binOp, "<=");
				break;			
			}
			case GEQ :
			{
				quoteInfix(binOp, ">=");
				break;			
			}
			case GT :
			{
				quoteInfix(binOp, ">");
				break;			
			}
			default:
				super.visit(binOp);
			}
		return null;
	}

	private void quoteInfix(BinOp binOp, String op) {
		pw.append("\"");
		binOp.left.accept(this);
		pw.append(op);
		binOp.right.accept(this);
		pw.append("\"");		
	}

}
