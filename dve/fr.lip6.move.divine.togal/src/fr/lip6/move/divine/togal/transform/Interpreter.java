package fr.lip6.move.divine.togal.transform;

import fr.lip6.move.divine.divine.ArithmeticSigned;
import fr.lip6.move.divine.divine.ArrayReference;
import fr.lip6.move.divine.divine.ArrayVarAccess;
import fr.lip6.move.divine.divine.Comparison;
import fr.lip6.move.divine.divine.Constant;
import fr.lip6.move.divine.divine.Expression;
import fr.lip6.move.divine.divine.Minus;
import fr.lip6.move.divine.divine.MultiDivMod;
import fr.lip6.move.divine.divine.NumberLiteral;
import fr.lip6.move.divine.divine.OrAndXor;
import fr.lip6.move.divine.divine.Plus;
import fr.lip6.move.divine.divine.Shift;
import fr.lip6.move.divine.divine.Variable;
import fr.lip6.move.divine.divine.VariableOrConstantReference;

public class Interpreter {
	
	private Converter conv;

	public Interpreter() {
		this.conv = new Converter();
	}

	public int interpret(Expression e) {
		
		if (e instanceof NumberLiteral) {
			return ((NumberLiteral) e).getValue();
		} 
		else if (conv.isBinaryIntExpression(e)) {
			return interpretBinaryIntExpression(e);	
		}
		else if (e instanceof ArithmeticSigned) {
			ArithmeticSigned as = (ArithmeticSigned) e;
			return interpret(as.getExpression());
		}
		else if (conv.isReference(e))
			return interpretReference(e);
		else if (e instanceof Comparison) { // cas ((var==255)*255) ... fischer.2.dve
			Comparison comp = (Comparison) e;
			if (comp.getOp().equals("==")) {
				if (interpret(comp.getLeft()) == interpret(comp.getRight()))
					return 1;
				else 
					return 0;
			}
		}
		else {
			throw new UnsupportedOperationException("Unexpected type received for conversion to Int expr : " + e.getClass().getSimpleName());
		}
		
		return 0;
	}
	
	private int interpretBinaryIntExpression(Expression e) {
		
		int result = 0;
		
		if (e instanceof Plus){
			Plus bin = (Plus) e;
			result = interpret(bin.getLeft()) + interpret(bin.getRight()); 
		}
		else if (e instanceof Minus) {
			Minus bin = (Minus) e;
			result = interpret(bin.getLeft()) - interpret(bin.getRight()); 
		}	
		else if (e instanceof MultiDivMod) {
			MultiDivMod bin = (MultiDivMod) e;
			if (bin.getOp().equals("*"))
				result = interpret(bin.getLeft()) * interpret(bin.getRight());
			else if (bin.getOp().equals("/"))
				result = interpret(bin.getLeft()) / interpret(bin.getRight());
			else
				result = interpret(bin.getLeft()) % interpret(bin.getRight());			
		}
		else if (e instanceof Shift) {
			Shift bin = (Shift) e;
			if (bin.getOp().equals(">>"))
				result = interpret(bin.getLeft()) >> interpret(bin.getRight());
			else
				result = interpret(bin.getLeft()) << interpret(bin.getRight());			
		}
		else if (e instanceof OrAndXor) {
			OrAndXor bin = (OrAndXor) e;
			if (bin.getOp().equals("&"))
				result = interpret(bin.getLeft()) & interpret(bin.getRight());
			else if (bin.getOp().equals("|"))
				result = interpret(bin.getLeft()) | interpret(bin.getRight());
			else
				result = interpret(bin.getLeft()) ^ interpret(bin.getRight());	
		}
		else {
			throw new UnsupportedOperationException(
					"Unexpected type received for interpretation as Int expr : " + e);
		}
		
		return result;
	}
	
	private int interpretReference(Expression e) {
		int result = 0;
		
		// e is a reference of variabe or constant
		if (e instanceof VariableOrConstantReference) {
			VariableOrConstantReference ref = (VariableOrConstantReference) e;
			if (ref.getRef() instanceof Variable) {
				Variable var = (Variable) ref.getRef();
				return interpret(var.getInitValue());
			}
			else if (ref.getRef() instanceof Constant) {
				Constant c = (Constant) ref.getRef();
				return interpret(c.getInitValue());
				
			} else
				throw new UnsupportedOperationException(
					"Undeclared variable or constant " + ref);
		}
		// e is a reference of array
		else if (e instanceof ArrayReference) {
			ArrayReference ref = (ArrayReference) e;
			if (ref.getRef() instanceof ArrayVarAccess) {
				ArrayVarAccess ava = (ArrayVarAccess) ref.getRef();
				return interpret(ava.getPrefix().getInitValue().get(interpret(ava.getIndex())));
				
			} else
				throw new UnsupportedOperationException("Undeclared array " + ref);
		}

		return result;
	}
	
}

