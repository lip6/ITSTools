package fr.lip6.move.gal.structural.smt;

import java.util.ArrayList;
import java.util.List;

import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.SMT;

import fr.lip6.move.gal.structural.expr.ArrayVarRef;
import fr.lip6.move.gal.structural.expr.AtomicPropRef;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.BoolConstant;
import fr.lip6.move.gal.structural.expr.Constant;
import fr.lip6.move.gal.structural.expr.ExprVisitor;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;
import fr.lip6.move.gal.structural.expr.ParamRef;
import fr.lip6.move.gal.structural.expr.TransRef;
import fr.lip6.move.gal.structural.expr.VarRef;

public class ExprTranslator implements ExprVisitor<IExpr> {
	private IFactory ef= new SMT().smtConfig.exprFactory;

	@Override
	public IExpr visit(VarRef varRef) {
		return ef.symbol("s"+ varRef.index);
	}

	@Override
	public IExpr visit(Constant constant) {
		return ef.numeral(constant.value);
	}

	@Override
	public IExpr visit(BinOp binOp) {
		IExpr l = binOp.left.accept(this);
		IExpr r = binOp.right != null ? binOp.right.accept(this) : null;
		
		switch (binOp.op) {
		case ADD :
			return ef.fcn(ef.symbol("+"), l,r);
		case AND :
			return ef.fcn(ef.symbol("and"), l,r);
		case EQ :
			return ef.fcn(ef.symbol("="), l,r);
		case GEQ :
			return ef.fcn(ef.symbol(">="), l,r);
		case GT :
			return ef.fcn(ef.symbol(">"), l,r);
		case LEQ :
			return ef.fcn(ef.symbol("<="), l,r);
		case LT :
			return ef.fcn(ef.symbol("<"), l,r);
		case MINUS :
			return ef.fcn(ef.symbol("-"), l,r);
		case OR :
			return ef.fcn(ef.symbol("or"), l,r);
		case NEQ :
			return ef.fcn(ef.symbol("not"),ef.fcn(ef.symbol("="), l,r));
		case NOT :
			return ef.fcn(ef.symbol("not"),l);
		default :
			throw new UnsupportedOperationException("unexpected operator type when translating to SMT.");			
		}
	}

	@Override
	public IExpr visitBool(BoolConstant b) {
		return ef.symbol(Boolean.toString(b.value));
	}

	@Override
	public IExpr visit(ParamRef paramRef) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IExpr visit(ArrayVarRef arrayVarRef) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IExpr visit(TransRef transRef) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IExpr visit(NaryOp nop) {
		List<IExpr> children = new ArrayList<>(nop.getChildren().size());
		for (Expression child : nop.getChildren()) {
			children.add(child.accept(this));
		}
		switch (nop.getOp()) {
		case AND :
			return ef.fcn(ef.symbol("and"), children);
		case OR :			
			return ef.fcn(ef.symbol("or"), children);
		case ADD :			
			return ef.fcn(ef.symbol("+"), children);
		case MULT :			
			return ef.fcn(ef.symbol("*"), children);
		
		default : 
			break;
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public IExpr visit(AtomicPropRef apRef) {
		return apRef.getAp().getExpression().accept(this);
	}
}
