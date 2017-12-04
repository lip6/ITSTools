package fr.lip6.move.gal.gal2smt.tosmt;

import java.util.logging.Logger;

import org.smtlib.IExpr;
import org.smtlib.IExpr.IFcnExpr;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.SMT.Configuration;

import fr.lip6.move.gal.AbstractParameter;
import fr.lip6.move.gal.And;
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
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.UnaryMinus;
import fr.lip6.move.gal.WrapBoolExpr;

public abstract class ExpressionTranslator {
	
	protected final IExpr.IFactory efactory;
	
	public ExpressionTranslator(Configuration conf) {
		efactory = conf.exprFactory;
	}
	
	/**
	 * translate int expression of gal to SMT
	 * @param expr 
	 * @param state the state we are using
	 * @return
	 */
	public IExpr translate(IntExpression expr, IExpr state) {					
		/* Constant */
		if (expr instanceof Constant) {
			Constant cte = (Constant) expr;
			if(cte.getValue() >= 0)
				return efactory.numeral(Integer.toString(cte.getValue()));
			getLog().warning("Invalide value " + cte.getValue());
		} else if (expr instanceof BinaryIntExpression) {
		/* BinaryIntExpression */ 		
			BinaryIntExpression binop = (BinaryIntExpression) expr;
			IExpr limg = translate(binop.getLeft(), state);
			IExpr rimg = translate(binop.getRight(), state);

			if(checkOp(binop.getOp()))
				return efactory.fcn(efactory.symbol(toSMT(binop.getOp())), limg, rimg);
			getLog().warning("Unknown op " + binop.getOp());			
		} else if(expr instanceof UnaryMinus){
			/* UnaryMinus */ 		
			UnaryMinus unamin = (UnaryMinus) expr;
			IExpr value = translate(unamin.getValue(),state);
			
			return efactory.fcn(efactory.symbol("-"), value);
		}else if(expr instanceof BitComplement){
			/* BitComplement */ 		
			BitComplement bitcomp = (BitComplement) expr;
			IExpr value = translate(bitcomp.getValue(), state);
						
			return efactory.fcn(efactory.symbol("~"), value);
		}else if(expr instanceof Reference){
			return handleReference((Reference) expr,state);
			
		}else if(expr instanceof ParamRef){
			/* ParamRef */ 		
			ParamRef pRef = (ParamRef) expr;
			AbstractParameter param = pRef.getRefParam();
			return efactory.fcn(efactory.symbol(param.getName()));
		}else if(expr instanceof WrapBoolExpr){
			/* WrapBoolExpr */ 		
			WrapBoolExpr wrapbool = (WrapBoolExpr) expr;
			
			return translateBool(wrapbool.getValue(),state);
		}
		
		getLog().warning("Unknown expression class " + expr);
		return null;
	}

	protected abstract IExpr handleReference(Reference expr, IExpr state) ;

	/**
	 * translate boolean expression of gal to SMT
	 * @param value
	 * @param state the vector containing the variables 
	 * @return
	 */
	public IExpr translateBool(BooleanExpression value, IExpr state) {
		
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
			expr.args().add(translateBool(and.getLeft(),state));
			expr.args().add(translateBool(and.getRight(),state));
			
			return expr;
		} else if (value instanceof Or) {
			/* Or */
			Or or = (Or) value;
			IFcnExpr expr = efactory.fcn(efactory.symbol("or"));
			expr.args().add(translateBool(or.getLeft(),state));
			expr.args().add(translateBool(or.getRight(),state));
			
			return expr;
		} else if (value instanceof Not) {
			/* Not */
			Not not = (Not) value;
			IFcnExpr expr = efactory.fcn(efactory.symbol("not"));
			expr.args().add(translateBool(not.getValue(),state));
			
			return expr;
		} else if (value instanceof Comparison) {
			/* Comparison */
			Comparison comparison = (Comparison) value;
			if (comparison.getOperator() == ComparisonOperators.NE) {
				IFcnExpr expr = efactory.fcn(efactory.symbol("not"));
				expr.args().add( efactory.fcn( toSMT(ComparisonOperators.EQ),
						translate(comparison.getLeft(),state),
						translate(comparison.getRight(),state)));
				return expr;

			} else {
				IFcnExpr condition = efactory.fcn( toSMT(comparison.getOperator()),
					translate(comparison.getLeft(),state),
					translate(comparison.getRight(),state));
				return condition;
			}			
		}
		getLog().warning("Unknown boolean expression type :"+value.getClass().getSimpleName());
		
		return null;
		
	}
	
	
	private static boolean checkOp(String op) {
		if(op.equals("+") || op.equals("-") || op.equals("*") || 
		op.equals("/") || op.equals("**") || op.equals("%"))
			return true;
		// || op.equals("&") || op.equals("|") 
		return false;
	}

	private String toSMT(String op) {
		if (op.equals("%")) {
			return "mod";
		} else {
			return op;
		}
	}


	
	private ISymbol toSMT (ComparisonOperators op) {
		switch (op) {
		case EQ :return efactory.symbol("=");
		case GT :return efactory.symbol(">");
		case LT :return efactory.symbol("<");
		case GE :return efactory.symbol(">=");
		case LE :return efactory.symbol("<=");

		case NE : // return efactory.symbol("not");
			// Fall thru : have to build something more complex for not equal
		default :
			getLog().warning("untreated operator : " + op);
			throw new RuntimeException("Translation does not handle this operator : "+ op);
		}
	}

	public IExpr translateProperty(LogicProp body, IExpr state) {

		if (body instanceof ReachableProp || body instanceof NeverProp){
			// SAT = trace to state satisfying P for reach (verdict TRUE)
			// SAT = trace to c-e satisfying P for never (verdict FALSE)
			return translateBool(((SafetyProp) body).getPredicate(), state);
		} else if (body instanceof InvariantProp) {
			// SAT = trace to c-e satisfying !P for invariant (verdict FALSE)
			return efactory.fcn(
					efactory.symbol("not"),
					translateBool(((SafetyProp) body).getPredicate(), state));			
		} 
		getLog().warning("Unknown LogicProp expression type :"+body.getClass().getSimpleName());		
		return null;
		
	}

	public static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}
	
	public IExpr.IFactory getEfactory() {
		return efactory;
	}


}
