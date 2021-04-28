package fr.lip6.move.gal.application;

import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.AF;
import fr.lip6.move.gal.AG;
import fr.lip6.move.gal.AU;
import fr.lip6.move.gal.AX;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BoolProp;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.BoundsProp;
import fr.lip6.move.gal.CTLProp;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.EF;
import fr.lip6.move.gal.EG;
import fr.lip6.move.gal.EU;
import fr.lip6.move.gal.EX;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.LTLFuture;
import fr.lip6.move.gal.LTLGlobally;
import fr.lip6.move.gal.LTLNext;
import fr.lip6.move.gal.LTLProp;
import fr.lip6.move.gal.LTLUntil;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.QualifiedReference;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.util.GalSwitch;

public class GalToStructural extends GalSwitch<Expression> {

	
	
	private IDeterministicNextBuilder dnb;
	public GalToStructural(IDeterministicNextBuilder dnb) {
		this.dnb=dnb;
	}

	@Override
	public Expression caseComparison(Comparison o) {
		Op op = image(o.getOperator());
		return Expression.op(op, doSwitch(o.getLeft()), doSwitch(o.getRight()));
	}
	private static Op image(ComparisonOperators operator) {
		switch (operator) {
		case EQ: return Op.EQ;
		case NE: return Op.NEQ;
		case GE: return Op.GEQ;
		case LE: return Op.LEQ;
		case GT: return Op.GT;
		case LT: return Op.LT;
		}
		throw new UnsupportedOperationException(operator.getLiteral());
	}
	@Override
	public Expression caseVariableReference(VariableReference object) {
		return Expression.var(dnb.getIndex(object));
	}
	@Override
	public Expression caseQualifiedReference(QualifiedReference object) {
		return Expression.var(dnb.getIndex(object));
	}
	@Override
	public Expression caseReference(Reference object) {
		return Expression.var(dnb.getIndex(object));
	}
	@Override
	public Expression caseConstant(Constant object) {
		return Expression.constant(object.getValue());
	}
	@Override
	public Expression caseBinaryIntExpression(BinaryIntExpression bin) {
		Op op = imageBinOp(bin.getOp());
		if (op == Op.ADD) {
			List<Expression> operands = new ArrayList<>();
			findOperandsAdd(operands, bin);
			return Expression.nop(Op.ADD, operands);
		} else {
			return Expression.op(op, doSwitch(bin.getLeft()), doSwitch(bin.getRight()));
		}
	}
	
	@Override
	public Expression caseTrue(True object) {
		return Expression.constant(true);
	}

	@Override
	public Expression caseFalse(False object) {
		return Expression.constant(false);
	}
	
	private void findOperandsAdd(List<Expression> operands, IntExpression e) {
		if (e instanceof BinaryIntExpression && "+".equals(((BinaryIntExpression) e).getOp())) {
			BinaryIntExpression or =  (BinaryIntExpression) e;
			findOperandsAdd(operands, or.getLeft());
			findOperandsAdd(operands, or.getRight());
		} else {
			operands.add(doSwitch(e));
		}
	}
	
	private static Op imageBinOp(String op) {
		if ("+".equals(op)) {
			return Op.ADD;
		} else if ("-".equals(op)) {
			return Op.MINUS;
		} else if ("*".equals(op)) {
			return Op.MULT;
		} else if ("/".equals(op)) {
			return Op.DIV;
		} else if ("%".equals(op)) {
			return Op.MOD;
		}
		throw new UnsupportedOperationException("Unknown binary operator :" + op);
	}
	
	// Boolean connectors
	@Override
	public Expression caseOr(Or object) {
		List<Expression> operands = new ArrayList<>();
		findOperandsOr(operands, object);
		return Expression.nop(Op.OR, operands);
	}
	private void findOperandsOr(List<Expression> operands, BooleanExpression e) {
		if (e instanceof Or) {
			Or or = (Or) e;
			findOperandsOr(operands, or.getLeft());
			findOperandsOr(operands, or.getRight());
		} else {
			operands.add(doSwitch(e));
		}
	}

	private void findOperandsAnd(List<Expression> operands, BooleanExpression e) {
		if (e instanceof And) {
			And and = (And) e;
			findOperandsAnd(operands, and.getLeft());
			findOperandsAnd(operands, and.getRight());
		} else {
			operands.add(doSwitch(e));
		}
	}
	
	@Override
	public Expression caseAnd(And object) {
		List<Expression> operands = new ArrayList<>();
		findOperandsAnd(operands, object);
		return Expression.nop(Op.AND, operands);
	}
	@Override
	public Expression caseNot(Not object) {
		return Expression.not(doSwitch(object.getValue()));
	}
	
	// CTL cases 
	@Override
	public Expression caseAF(AF o) {
		return Expression.op(Op.AF, doSwitch(o.getProp()), null);		
	}
	@Override
	public Expression caseAX(AX o) {
		return Expression.op(Op.AX, doSwitch(o.getProp()), null);		
	}
	@Override
	public Expression caseAG(AG o) {
		return Expression.op(Op.AG, doSwitch(o.getProp()), null);		
	}
	@Override
	public Expression caseEF(EF o) {
		return Expression.op(Op.EF, doSwitch(o.getProp()), null);		
	}
	@Override
	public Expression caseEX(EX o) {
		return Expression.op(Op.EX, doSwitch(o.getProp()), null);		
	}
	@Override
	public Expression caseEG(EG o) {
		return Expression.op(Op.EG, doSwitch(o.getProp()), null);		
	}
	@Override
	public Expression caseAU(AU o) {
		return Expression.op(Op.AU, doSwitch(o.getLeft()), doSwitch(o.getRight()));
	}
	@Override
	public Expression caseEU(EU o) {
		return Expression.op(Op.EU, doSwitch(o.getLeft()), doSwitch(o.getRight()));
	}
	
	
	// LTL
	@Override
	public Expression caseLTLFuture(LTLFuture object) {
		return Expression.op(Op.F, doSwitch(object.getProp()), null);
	}
	@Override
	public Expression caseLTLGlobally(LTLGlobally object) {
		return Expression.op(Op.G, doSwitch(object.getProp()), null);
	}
	@Override
	public Expression caseLTLNext(LTLNext object) {
		return Expression.op(Op.X, doSwitch(object.getProp()), null);
	}
	@Override
	public Expression caseLTLUntil(LTLUntil object) {
		return Expression.op(Op.U, doSwitch(object.getLeft()), doSwitch(object.getRight()));
	}

	public Property convert(fr.lip6.move.gal.Property p) {
		PropertyType ptype = imagePropType(p);
		
		if (p.getBody() instanceof BoundsProp) {
			BoundsProp bp = (BoundsProp) p.getBody();
			return new Property(doSwitch(bp.getTarget()), ptype, p.getName());
		} else if (p.getBody() instanceof BoolProp) {
			BoolProp bp = (BoolProp) p.getBody();
			return new Property(doSwitch(bp.getPredicate()), ptype, p.getName());
		}
		throw new UnsupportedOperationException("Unknown property type "+p.getBody().getClass().getName());
	}

	private static PropertyType imagePropType(fr.lip6.move.gal.Property p) {
		if (p.getBody() instanceof BoundsProp) {
			return PropertyType.BOUNDS;
		} else if (p.getBody() instanceof LTLProp) {
			return PropertyType.LTL;
		} else if (p.getBody() instanceof CTLProp) {
			return PropertyType.CTL;
		} else if (p.getBody() instanceof InvariantProp) {
			return PropertyType.INVARIANT;	
		}
		throw new UnsupportedOperationException("Unknown property type :" + p.getBody().getClass().getName());
	}
}
