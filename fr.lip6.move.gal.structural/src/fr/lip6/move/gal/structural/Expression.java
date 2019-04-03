package fr.lip6.move.gal.structural;

import android.util.SparseIntArray;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.QualifiedReference;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.UnaryMinus;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.semantics.INextBuilder;
import fr.lip6.move.gal.util.GalSwitch;

public interface Expression {
	int eval (SparseIntArray state);
	
	public static Expression buildExpression (BooleanExpression be, INextBuilder inb) {
		ExpressionBuilder eb = new ExpressionBuilder(inb);
		return eb.doSwitch(be);
	}

	static Expression not(Expression be) {
		return new BinOp(Operator.NOT, be, null);
	}
}

class VarRef implements Expression {
	int index;

	public VarRef(int index) {
		this.index = index;
	}

	@Override
	public int eval(SparseIntArray state) {
		return state.get(index);
	}	
}

class Constant implements Expression {
	int value;

	public Constant(int value) {
		this.value = value;
	}

	@Override
	public int eval(SparseIntArray state) {
		return value;
	}
}

enum Operator { ADD, MULT, MINUS, DIV, AND, OR, NOT, EQ, NEQ, GEQ, GT, LEQ, LT }

class BinOp implements Expression {
	Operator op;
	Expression left;
	Expression right;
	public BinOp(Operator op, Expression left, Expression right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}
	@Override
	public int eval(SparseIntArray state) {
		int l = left.eval(state);
		int r = right != null ? right.eval(state) : 0;
		switch (op) {
		case EQ :
			return l == r ? 1 : 0;
		case NEQ :
			return l != r ? 1 : 0;
		case LT :
			return l < r ? 1 : 0;
		case GT :
			return l > r ? 1 : 0;
		case LEQ :
			return l <= r ? 1 : 0;
		case GEQ :
			return l >= r ? 1 : 0;
		case AND :
			return (l == 1) && (r==1) ? 1 : 0;
		case OR :
			return (l == 1) || (r==1) ? 1 : 0;
		case NOT :
			return (l == 0) ? 1 : 0;
		case ADD :
			return l + r;
		case MULT :
			return l * r;
		case DIV :
			return l / r;
		case MINUS :
			return l - r;
		}
		throw new RuntimeException("Unexpected operator type in expression "+op);
	}	
}

class ExpressionBuilder extends GalSwitch<Expression> {
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
		case EQ :
			op = Operator.EQ;
			break;
		case GE :
			op = Operator.GEQ;
			break;
		case GT :
			op = Operator.GT;
			break;
		case LE :
			op = Operator.LEQ;
			break;
		case LT :
			op = Operator.LT;
			break;
		case NE :
			op = Operator.NEQ;
			break;
		}
		
		return new BinOp(op, doSwitch(object.getLeft()), doSwitch(object.getRight()));
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
