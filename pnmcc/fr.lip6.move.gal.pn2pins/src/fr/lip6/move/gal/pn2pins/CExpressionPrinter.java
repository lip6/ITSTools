package fr.lip6.move.gal.pn2pins;

import java.io.PrintWriter;

import fr.lip6.move.gal.structural.expr.ArrayVarRef;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.BoolConstant;
import fr.lip6.move.gal.structural.expr.Constant;
import fr.lip6.move.gal.structural.expr.ExprVisitor;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;
import fr.lip6.move.gal.structural.expr.ParamRef;
import fr.lip6.move.gal.structural.expr.TransRef;
import fr.lip6.move.gal.structural.expr.VarRef;

public class CExpressionPrinter implements ExprVisitor<Void> {

	protected PrintWriter pw;
	private String prefix;
	
	public CExpressionPrinter(PrintWriter pw, String prefix) {
		this.pw = pw;
		this.prefix = prefix;
	}

	public void close() {
		pw.close();
	}
	
	@Override
	public Void visit(VarRef varRef) {
		pw.append(prefix).append("[").append(Integer.toString(varRef.getValue())).append("]");
		return null;
	}

	@Override
	public Void visit(Constant constant) {
		pw.append(Integer.toString(constant.getValue()));
		return null;
	}

	@Override
	public Void visit(BinOp binOp) {
		switch (binOp.getOp()) {
		case AND : 
		{
			infix(binOp, " && ");
			break;
		}
		case OR :
		{
			infix(binOp, " || ");
			break;
		}
		case NOT :
		{
			pw.append("!");
			binOp.left.accept(this);
			break;
		}
		case ADD :
		{
			infix(binOp, " + ");
			break;
		}
		case EQ :
		{
			infix(binOp, " == ");
			break;			
		}
		case NEQ :
		{
			infix(binOp, " != ");
			break;			
		}
		case LT :
		{
			infix(binOp, " < ");
			break;			
		}
		case LEQ :
		{
			infix(binOp, " <= ");
			break;			
		}
		case GEQ :
		{
			infix(binOp, " >= ");
			break;			
		}
		case GT :
		{
			infix(binOp, " > ");
			break;			
		}
		case DIV :
		{
			infix(binOp, " / ");
			break;			
		}
		case MINUS :
		{
			infix(binOp, " - ");
			break;			
		}
		case MOD :
		{
			infix(binOp, " % ");
			break;			
		}
		case MULT :
		{
			infix(binOp, " * ");
			break;			
		}
		default :
			throw new UnsupportedOperationException("Unexpected temporal operator in Boolean expression :"+binOp);
		}
		return null;
	}

	public void infix(BinOp binOp, String op) {
		pw.append("(");
		binOp.left.accept(this);
		pw.append(op);
		binOp.right.accept(this);
		pw.append(")");
	}

	@Override
	public Void visitBool(BoolConstant boolConstant) {
		pw.append(boolConstant.getValue()!=0?"true":"false");
		return null;
	}

	@Override
	public Void visit(ParamRef paramRef) {
		throw new UnsupportedOperationException("Unexpected ParamRef in Boolean expression : "+paramRef);
	}

	@Override
	public Void visit(ArrayVarRef arrayVarRef) {
		throw new UnsupportedOperationException("Unexpected Array Ref in expression translated to C : "+ arrayVarRef);
	}

	@Override
	public Void visit(TransRef transRef) {
		throw new UnsupportedOperationException("Unexpected Transition Ref in expression translated to C :"+transRef);
	}

	@Override
	public Void visit(NaryOp naryOp) {
		String symbol = null;
		switch (naryOp.getOp()) {
		case AND :
			symbol = "&&";
			break;
		case OR :
			symbol = "||";
			break;
		case ADD :
			symbol = "+";
			break;
		case MULT :
			symbol = "*";
			break;
		default :
			throw new UnsupportedOperationException("Unexpected Nary operator in expression translated to C : " + naryOp);
		}
		pw.append("(");
		for (int i=0, ie = naryOp.nbChildren() ; i < ie ; i++) {
			Expression child = naryOp.childAt(i);
			child.accept(this);
			if (i < ie -1) {
				pw.append(symbol);
			}
		}
		pw.append(")");		
		return null;
	}

	
	
}
