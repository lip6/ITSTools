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
		return new BinOp(Op.AND, doSwitch(object.getLeft()), doSwitch(object.getRight()));
	}

	@Override
	public Expression caseOr(Or object) {
		return new BinOp(Op.OR, doSwitch(object.getLeft()), doSwitch(object.getRight()));
	}

	@Override
	public Expression caseNot(Not object) {
		return new BinOp(Op.NOT, doSwitch(object.getValue()), null);
	}

	@Override
	public Expression caseComparison(Comparison object) {
		Op op = null;
		switch (object.getOperator()) {
		case EQ:
			op = Op.EQ;
			break;
		case GE:
			op = Op.GEQ;
			break;
		case GT:
			op = Op.GT;
			break;
		case LE:
			op = Op.LEQ;
			break;
		case LT:
			op = Op.LT;
			break;
		case NE:
			op = Op.NEQ;
			break;
		}

		return new BinOp(op, doSwitch(object.getLeft()), doSwitch(object.getRight()));
	}

	@Override
	public Expression caseBinaryIntExpression(BinaryIntExpression object) {
		switch (object.getOp()) {
		case "+":
			return new BinOp(Op.ADD, doSwitch(object.getLeft()), doSwitch(object.getRight()));
		case "-":
			return new BinOp(Op.MINUS, doSwitch(object.getLeft()), doSwitch(object.getRight()));
		case "*":
			return new BinOp(Op.MULT, doSwitch(object.getLeft()), doSwitch(object.getRight()));
		case "/":
			return new BinOp(Op.DIV, doSwitch(object.getLeft()), doSwitch(object.getRight()));
		}
		Logger.getLogger("fr.lip6.move.gal")
				.warning("Unexpected binary arithmetic operator " + object.getOp() + " when tranlating predicate.");
		return super.caseBinaryIntExpression(object);
	}

	@Override
	public Expression caseTrue(True object) {
		return new BoolConstant(true);
	}

	@Override
	public Expression caseFalse(False object) {
		return new BoolConstant(false);
	}

	@Override
	public Expression caseConstant(fr.lip6.move.gal.Constant object) {
		return new Constant(object.getValue());
	}

	@Override
	public Expression caseUnaryMinus(UnaryMinus object) {
		return new BinOp(Op.MINUS, new Constant(0), doSwitch(object.getValue()));
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
