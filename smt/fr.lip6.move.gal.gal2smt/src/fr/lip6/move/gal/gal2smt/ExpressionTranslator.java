package fr.lip6.move.gal.gal2smt;

import org.smtlib.IExpr;
import org.smtlib.IExpr.IFcnExpr;
import org.smtlib.IExpr.INumeral;
import org.smtlib.IExpr.ISymbol;

import fr.lip6.move.gal.AbstractParameter;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BitComplement;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.LogicProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.QualifiedReference;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.UnaryMinus;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.WrapBoolExpr;

public class ExpressionTranslator {

	private static IExpr.IFactory efactory = GalToSMT.getSMT().smtConfig.exprFactory;

	
	/**
	 * translate int expression of gal to SMT
	 * @param expr 
	 * @param index the time step at which the variables are
	 * @return
	 */
	public static IExpr translate(IntExpression expr, IExpr index) {					
		/* Constant */
		if (expr instanceof Constant) {
			Constant cte = (Constant) expr;
			if(cte.getValue() >= 0)
				return efactory.symbol(Integer.toString(cte.getValue()));
			GalToSMT.getLog().warning("Invalide value " + cte.getValue());
		} else if (expr instanceof BinaryIntExpression) {
		/* BinaryIntExpression */ 		
			BinaryIntExpression binop = (BinaryIntExpression) expr;
			IExpr limg = translate(binop.getLeft(), index);
			IExpr rimg = translate(binop.getRight(), index);

			if(checkOp(binop.getOp()))
				return efactory.fcn(efactory.symbol(binop.getOp()), limg, rimg);
			GalToSMT.getLog().warning("Unknown op " + binop.getOp());			
		} else if(expr instanceof UnaryMinus){
			/* UnaryMinus */ 		
			UnaryMinus unamin = (UnaryMinus) expr;
			IExpr value = translate(unamin.getValue(),index);
			
			return efactory.fcn(efactory.symbol("-"), value);
		}else if(expr instanceof BitComplement){
			/* BitComplement */ 		
			BitComplement bitcomp = (BitComplement) expr;
			IExpr value = translate(bitcomp.getValue(), index);
						
			return efactory.fcn(efactory.symbol("~"), value);
		}else if(expr instanceof Reference){
			/* Reference */ 		
			Reference ref = (Reference) expr;
			if(ref instanceof VariableReference){
				VariableReference vr = (VariableReference) ref;
				if(vr.getRef() != null){
					
					IExpr res;
					if(vr.getIndex() != null){
						/* Array */
						INumeral i = efactory.numeral(((Constant) vr.getIndex()).getValue());
						res = efactory.fcn(efactory.symbol("select"), efactory.symbol(vr.getRef().getName()),  i);						
					}else{
						res = efactory.symbol(vr.getRef().getName());
					}
					return efactory.fcn(efactory.symbol("select"), res, index);
				}			
				GalToSMT.getLog().info("Reference null");				
			}else if(ref instanceof QualifiedReference){
				GalToSMT.getLog().info("Cannot handle qualified refs currently !");
			}
			
		}else if(expr instanceof ParamRef){
			/* ParamRef */ 		
			ParamRef pRef = (ParamRef) expr;
			AbstractParameter param = pRef.getRefParam();
			return efactory.fcn(efactory.symbol(param.getName()));
		}else if(expr instanceof WrapBoolExpr){
			/* WrapBoolExpr */ 		
			WrapBoolExpr wrapbool = (WrapBoolExpr) expr;
			
			return translateBool(wrapbool.getValue(),index);
		}
		
		GalToSMT.getLog().warning("Unknown expression class " + expr);
		return null;
	}

	/**
	 * translate boolean expression of gal to SMT
	 * @param value
	 * @param index the time step at which the variables are
	 * @return
	 */
	public static IExpr translateBool(BooleanExpression value, IExpr index) {
		
		if (value instanceof True) {
			/* True */
			return efactory.symbol("true");
		} else if (value instanceof False) {
			/* False */
			return efactory.symbol("false");
		} else if (value instanceof And) {
			/* And */
			And and = (And) value;
			IFcnExpr expr = efactory.fcn(efactory.symbol("and"));
			expr.args().add(translateBool(and.getLeft(),index));
			expr.args().add(translateBool(and.getRight(),index));
			
			return expr;
		} else if (value instanceof Or) {
			/* Or */
			Or or = (Or) value;
			IFcnExpr expr = efactory.fcn(efactory.symbol("or"));
			expr.args().add(translateBool(or.getLeft(),index));
			expr.args().add(translateBool(or.getRight(),index));
			
			return expr;
		} else if (value instanceof Not) {
			/* Not */
			Not not = (Not) value;
			IFcnExpr expr = efactory.fcn(efactory.symbol("not"));
			expr.args().add(translateBool(not.getValue(),index));
			
			return expr;
		} else if (value instanceof Comparison) {
			/* Comparison */
			Comparison comparison = (Comparison) value;
			IFcnExpr condition = efactory.fcn( toSMT(comparison.getOperator()),
					translate(comparison.getLeft(),index),
					translate(comparison.getRight(),index));
			
			return condition;
		}
		GalToSMT.getLog().warning("Unknown boolean expression type :"+value.getClass().getSimpleName());
		
		return null;
		
	}
	
	
	private static boolean checkOp(String op) {
		if(op.equals("+") || op.equals("-") || op.equals("*") || 
		op.equals("/") || op.equals("**") || op.equals("&") || op.equals("|"))
			return true;
		return false;
	}
	
	private static ISymbol toSMT (ComparisonOperators op) {
		switch (op) {
		case EQ :return efactory.symbol("=");
		case NE :return efactory.symbol("not");
		case GT :return efactory.symbol(">");
		case LT :return efactory.symbol("<");
		case GE :return efactory.symbol(">=");
		case LE :return efactory.symbol("<=");
		default :
			GalToSMT.getLog().warning("untreated operator : " + op);
		}
		return null;
	}

	public static IExpr translateProperty(LogicProp body, IExpr index) {

		if (body instanceof ReachableProp || body instanceof NeverProp){
			// SAT = trace to state satisfying P for reach (verdict TRUE)
			// SAT = trace to c-e satisfying P for never (verdict FALSE)
			return ExpressionTranslator.translateBool(body.getPredicate(), index);
		} else if (body instanceof InvariantProp) {
			// SAT = trace to c-e satisfying !P for invariant (verdict FALSE)
			return efactory.fcn(
					efactory.symbol("not"),
					ExpressionTranslator.translateBool(body.getPredicate(), index));			
		} 
		GalToSMT.getLog().warning("Unknown LogicProp expression type :"+body.getClass().getSimpleName());		
		return null;
		
	}

	
	public static IExpr translate(Variable vr, IExpr index) {
		return efactory.fcn(efactory.symbol("select"), efactory.symbol(vr.getName()), index);
	}

	
	public static IExpr sucrerie(Assignment ass, IExpr indexNow) {
		if (ass.getType() == AssignType.INCR ) {
			return efactory.fcn(efactory.symbol("+"), 
					translate(ass.getLeft(), indexNow),
					translate(ass.getRight(), indexNow));							
		}else if (ass.getType() == AssignType.DECR ) {
			return efactory.fcn(efactory.symbol("-"), 
					translate(ass.getLeft(), indexNow),
					translate(ass.getRight(), indexNow));							
		}
		return translate(ass.getRight(), indexNow);		
	}

}
