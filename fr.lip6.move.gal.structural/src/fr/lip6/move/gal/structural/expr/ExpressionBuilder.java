package fr.lip6.move.gal.structural.expr;

import fr.lip6.move.gal.And;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.util.GalSwitch;

import java.util.logging.Logger;

import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.QualifiedReference;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.UnaryMinus;
import fr.lip6.move.gal.VariableReference;

public class ExpressionBuilder extends GalSwitch<Expression> {
	INextBuilder inb;

	public ExpressionBuilder(INextBuilder inb) {
		this.inb = inb;
	}

	@Override
	public Expression caseAnd(And object) {
		return new BinOp(Operator.AND, doSwitch(object.getLeft()), doSwitch(object.getRight()));
	}

	@Override
	public Expression caseOr(Or object) {
		return new BinOp(Operator.OR, doSwitch(object.getLeft()), doSwitch(object.getRight()));
	}

	@Override
	public Expression caseNot(Not object) {
		return new BinOp(Operator.NOT, doSwitch(object.getValue()), null);
	}

	@Override
	public Expression caseComparison(Comparison object) {
		Operator op = null;
		switch (object.getOperator()) {
		case EQ:
			op = Operator.EQ;
			break;
		case GE:
			op = Operator.GEQ;
			break;
		case GT:
			op = Operator.GT;
			break;
		case LE:
			op = Operator.LEQ;
			break;
		case LT:
			op = Operator.LT;
			break;
		case NE:
			op = Operator.NEQ;
			break;
		}

		return new BinOp(op, doSwitch(object.getLeft()), doSwitch(object.getRight()));
	}

	@Override
	public Expression caseBinaryIntExpression(BinaryIntExpression object) {
		switch (object.getOp()) {
		case "+":
			return new BinOp(Operator.ADD, doSwitch(object.getLeft()), doSwitch(object.getRight()));
		case "-":
			return new BinOp(Operator.MINUS, doSwitch(object.getLeft()), doSwitch(object.getRight()));
		case "*":
			return new BinOp(Operator.MULT, doSwitch(object.getLeft()), doSwitch(object.getRight()));
		case "/":
			return new BinOp(Operator.DIV, doSwitch(object.getLeft()), doSwitch(object.getRight()));
		}
		Logger.getLogger("fr.lip6.move.gal")
				.warning("Unexpected binary arithmetic operator " + object.getOp() + " when tranlating predicate.");
		return super.caseBinaryIntExpression(object);
	}

	@Override
	public Expression caseTrue(True object) {
		return new Constant(1);
	}

	@Override
	public Expression caseFalse(False object) {
		return new Constant(0);
	}

	@Override
	public Expression caseConstant(fr.lip6.move.gal.Constant object) {
		return new Constant(object.getValue());
	}

	@Override
	public Expression caseUnaryMinus(UnaryMinus object) {
		return new BinOp(Operator.MINUS, new Constant(0), doSwitch(object.getValue()));
	}

	@Override
	public Expression caseQualifiedReference(QualifiedReference object) {
		return new VarRef(inb.getIndex(object));
	}

	@Override
	public Expression caseReference(Reference object) {
		return new VarRef(inb.getIndex(object));
	}

	@Override
	public Expression caseVariableReference(VariableReference object) {
		return new VarRef(inb.getIndex(object));
	}
}
