package fr.lip6.mist.io.properties;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.expr.ArrayVarRef;
import fr.lip6.move.gal.structural.expr.AtomicPropRef;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.BoolConstant;
import fr.lip6.move.gal.structural.expr.Constant;
import fr.lip6.move.gal.structural.expr.ExprVisitor;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.ParamRef;
import fr.lip6.move.gal.structural.expr.TransRef;
import fr.lip6.move.gal.structural.expr.VarRef;

public class PropertiesToPNML {

	static Logger log = Logger.getLogger("fr.lip6.move.gal");
	
	private static Logger getLog() {
		return log ;
	}

	/**
	 * 
	 * @param spn
	 * @param path
	 * @param doneProps
	 * @throws IOException
	 */
	public static void transform(List<Property> properties, Map<String,Integer> vars, String path) throws IOException {
		long time = System.currentTimeMillis();
		PrintWriter pw = new PrintWriter(new File(path));
		pw.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		
		pw.append("<property-set xmlns=\"http://mcc.lip6.fr/\">\n");
		int exported=0;
		for (Property prop : properties) {
			pw.append("<property>\n" + 
					"<id>"+ prop.getName() +"</id>\n" + 
					"<description>Automatically generated</description>\n" + 
					"<formula>\n");
			exportProperty(pw, prop.getBody(), prop.getType(), vars);
			pw.append("</formula>\n" + 
					"</property>\n" + 
					"");
			exported++;
		}
		pw.append("</property-set>\n");
		pw.close();
		getLog().info("Export to MCC of "+ exported +" properties in file "+path +" took "+ (System.currentTimeMillis()-time) + " ms.");
	}


	private static void exportProperty(PrintWriter pw, Expression body, PropertyType type, Map<String, Integer> vars) {
		if (body == null) {
			return ;
		} else if (type == PropertyType.DEADLOCK) {
			pw.append("<exists-path><finally><deadlock/></finally></exists-path>\n");
			return ;
		} else if (type == PropertyType.LTL) {
			pw.append("<all-paths>");
			PrintVisitor v = new PrintVisitor(pw,type,vars);
			body.accept(v);
			pw.append("</all-paths>");
			return ;
		} else if (type == PropertyType.BOUNDS && body.getOp() == Op.CONST) {
			pw.append("<place-bound>");
//			for (int i=0; i < body.getValue(); i++) {
//				pw.append("<place>p"+vars.getPlaceCount()+"</place>");
//			}
			pw.append("<integer-constant>"+body.getValue()+"</integer-constant>\n");
			pw.append("</place-bound>\n");			
			return ;			
		} else if (type == PropertyType.INVARIANT && body.getOp() == Op.BOOLCONST) {
			if (body.getValue() == 1) {
				// simplest true formula we can think of, true directly in initial state in particular
				pw.append("<exists-path><finally><integer-le><integer-constant>0</integer-constant><integer-constant>0</integer-constant></integer-le></finally></exists-path>");
			} else {
				// simplest false formula we can think of, false directly in initial state in particular
				pw.append("<all-paths><globally><integer-le><integer-constant>1</integer-constant><integer-constant>0</integer-constant></integer-le></globally></all-paths> ");				
			}
			return ;			
		} else {
			PrintVisitor v = new PrintVisitor(pw,type,vars);
			body.accept(v);
			return ;
		}
	}
		
}

class PrintVisitor implements ExprVisitor<Void> {

	private PrintWriter pw;
	private PropertyType type;
	private Map<Integer,String> vars = new HashMap<>();

	public PrintVisitor(PrintWriter pw, PropertyType type, Map<String, Integer> vars) {
		this.pw = pw;
		this.type = type;
		for (Map.Entry<String, Integer> entry : vars.entrySet()) {
			this.vars.put(entry.getValue(), entry.getKey());
		}
	}

	@Override
	public Void visit(VarRef varRef) {
		if (type == PropertyType.BOUNDS) {
			pw.append("<place-bound>") 
			.append("<place>"+ vars.get(varRef.index)+"</place>") 
			.append("</place-bound>\n");		
		} else {
			pw.append("<tokens-count>") 
			.append("<place>"+ vars.get(varRef.index)+"</place>") 
			.append("</tokens-count>\n");
		}
		return null;
	}

	@Override
	public Void visit(Constant constant) {
		pw.append("<integer-constant>"+constant.value+"</integer-constant>\n");
		return null;
	}

	@Override
	public Void visit(BinOp binOp) {
		switch (binOp.getOp()) {
		case AF :
		{
			pw.append("<all-paths><finally>\n");
			binOp.left.accept(this);
			pw.append("</finally></all-paths>\n");
			break;
		}
		case AG :
		{
			pw.append("<all-paths><globally>\n");
			binOp.left.accept(this);
			pw.append("</globally></all-paths>\n");
			break;
		}
		case EF :
		{
			pw.append("<exists-path><finally>\n");
			binOp.left.accept(this);
			pw.append("</finally></exists-path>\n");
			break;
		}
		case EG :
		{
			pw.append("<exists-path><globally>\n");
			binOp.left.accept(this);
			pw.append("</globally></exists-path>\n");
			break;
		}
		case AU :
		{
			pw.append("<all-paths><until><before>\n");
			binOp.left.accept(this);
			pw.append("</before><reach>");
			binOp.right.accept(this);
			pw.append("</reach></until></all-paths>");
			break;
		}
		case EU :
		{
			pw.append("<exists-path><until><before>\n");
			binOp.left.accept(this);
			pw.append("</before><reach>");
			binOp.right.accept(this);
			pw.append("</reach></until></exists-path>");
			break;
		}
		case AX :
		{
			pw.append("<all-paths><next>");
			binOp.left.accept(this);
			pw.append("</next></all-paths>");
			break;
		}
		case EX :
		{
			pw.append("<exists-path><next>");
			binOp.left.accept(this);
			pw.append("</next></exists-path>");
			break;
		}
		case F :
		{
			pw.append("<finally>\n");
			binOp.left.accept(this);
			pw.append("</finally>\n");
			break;
		}
		case G :
		{
			pw.append("<globally>\n");
			binOp.left.accept(this);
			pw.append("</globally>\n");
			break;
		}
		case X :
		{
			pw.append("<next>\n");
			binOp.left.accept(this);
			pw.append("</next>\n");
			break;
		}
		case NOT :
		{
			pw.append("<negation>\n");
			binOp.left.accept(this);
			pw.append("</negation>\n");
			break;
		}
		case U :
		{
			pw.append("<until><before>\n");
			binOp.left.accept(this);
			pw.append("</before><reach>");
			binOp.right.accept(this);
			pw.append("</reach></until>");
			break;
		}
		case LEQ : 
		{
			// MCC format actually only supports <= : integer-le
			pw.append("<integer-le>\n");			
			binOp.left.accept(this);
			binOp.right.accept(this);
			pw.append("</integer-le>\n");
			break;			
		}
		case GT : 
		{
			// a > b => ! a <= b
			pw.append("<negation><integer-le>\n");			
			binOp.left.accept(this);
			binOp.right.accept(this);
			pw.append("</integer-le></negation>\n");
			break;
		}
		case GEQ : 
		{
			// a >= b => b <= a
			pw.append("<integer-le>\n");			
			binOp.right.accept(this);
			binOp.left.accept(this);
			pw.append("</integer-le>\n");
			break;
		}
		case EQ : 
		{
			// a == b => a <= b && b <= a
			pw.append("<conjunction>\n");
			pw.append("<integer-le>\n");			
			binOp.left.accept(this);
			binOp.right.accept(this);
			pw.append("</integer-le>\n");
			pw.append("<integer-le>\n");			
			binOp.right.accept(this);
			binOp.left.accept(this);
			pw.append("</integer-le>\n");
			pw.append("</conjunction>\n");
			break;
		}
		case NEQ : 
		{
			// a != b => ! (a <= b && b <= a)
			pw.append("<negation>");
			pw.append("<conjunction>\n");
			pw.append("<integer-le>\n");			
			binOp.left.accept(this);
			binOp.right.accept(this);
			pw.append("</integer-le>\n");
			pw.append("<integer-le>\n");			
			binOp.right.accept(this);
			binOp.left.accept(this);
			pw.append("</integer-le>\n");
			pw.append("</conjunction>\n");
			pw.append("</negation>\n");
			break;
		}
		case LT :
		{
			if (binOp.right.getOp() == Op.CONST) {
				// x < K  =>  x <= K-1
				pw.append("<integer-le>\n");			
				binOp.left.accept(this);
				pw.append("<integer-constant>"+(binOp.right.getValue()-1)+"</integer-constant>\n");				
				pw.append("</integer-le>\n");				
			} else {
				// a < b => a <= b && ! b <= a
				pw.append("<conjunction>\n");
				pw.append("<integer-le>\n");			
				binOp.left.accept(this);
				binOp.right.accept(this);
				pw.append("</integer-le>\n");
				pw.append("<negation>");
				pw.append("<integer-le>\n");			
				binOp.right.accept(this);
				binOp.left.accept(this);
				pw.append("</integer-le>\n");
				pw.append("</negation>\n");
				pw.append("</conjunction>\n");
			}
			break;			
		}
		default :
		{
			PropertiesToPNML.log.warning("Unexpected operator in binary formula :" + binOp);
		}
		}
		return null;
	}


	@Override
	public Void visitBool(BoolConstant boolConstant) {
		if (boolConstant.value) {
			// true => 0 <= 0
			pw.append("<integer-le><integer-constant>0</integer-constant><integer-constant>0</integer-constant></integer-le>\n");						
		} else {
			// false => 1 <= 0
			pw.append("<integer-le><integer-constant>1</integer-constant><integer-constant>0</integer-constant></integer-le>\n");									
		}
		return null;
	}

	@Override
	public Void visit(ParamRef paramRef) {
		PropertiesToPNML.log.warning("Unexpected parameter in formula :" + paramRef);
		return null;
	}

	@Override
	public Void visit(ArrayVarRef arrayVarRef) {
		PropertiesToPNML.log.warning("Unexpected array occurrence in formula :" + arrayVarRef);
		return null;
	}

	@Override
	public Void visit(TransRef transRef) {
		PropertiesToPNML.log.warning("Unexpected transition occurrence in formula :" + transRef);
		return null;
	}

	public void combine (String operator, List<Expression> operands) {
		// go for a log expansion to binary constructions.
		int sz = operands.size();
		if (sz == 0) {
			// throw maybe ?
			return;
		} else if (sz == 1) {
			operands.get(0).accept(this);
		} else {
			int mid = sz / 2;
			pw.append("<"+ operator+">\n");			
			combine(operator,operands.subList(0, mid));
			combine(operator,operands.subList(mid, sz));
			pw.append("</"+operator +">\n");
		}
	}
	
	@Override
	public Void visit(NaryOp naryOp) {
		switch (naryOp.getOp()) {
		case AND :
		{
			combine("conjunction", naryOp.getChildren());
			break;
		}
		case OR :
		{
			combine("disjunction", naryOp.getChildren());
			break;
		}
		case ADD : 
		{
			if (type == PropertyType.BOUNDS) {
				pw.append("<place-bound>");
			} else {
				pw.append("<tokens-count>"); 
			}
			for (Expression child : naryOp.getChildren()) {
				if (child.getOp() == Op.PLACEREF) {
					pw.append("<place>p"+ child.getValue()+"</place>");
				} else if (child.getOp() == Op.CONST) {
					throw new IllegalArgumentException("Unexpected constant in sum formula : cannot mix place references and integer constants while translating : " + naryOp);
//					// do we already have such a place ?
//					int val = child.getValue();
//					int ind = usedConstants.indexOf(val);
//					if (ind == -1) {
//						ind = usedConstants.size();
//						usedConstants.add(val);
//					}
//					pw.append("<place>p"+(placeCount+ind)+"</place>");					
				}
			}
			if (type == PropertyType.BOUNDS) {
				pw.append("</place-bound>\n"); 
			} else {
				pw.append("</tokens-count>\n"); 
			}
			break;
		}
		case ENABLED:
		{
			pw.append("<is-fireable>"); 
			for (Expression child : naryOp.getChildren()) {
				if (child.getOp() == Op.TRANSREF) {
					pw.append("<transition>t"+ child.getValue()+"</transition>");
				} else {
					throw new IllegalArgumentException("Unexpected child of enabled should be a transition.");
				}
			}
			pw.append("</is-fireable>\n"); 
			break;
		}
		case CARD:
		{
			pw.append("<tokens-count>"); 
			for (Expression child : naryOp.getChildren()) {
				if (child.getOp() == Op.PLACEREF) {
					pw.append("<place>"+ vars.get(child.getValue()) +"</place>");
				} else if (child.getOp() == Op.CONST) {
					throw new IllegalArgumentException("Unexpected constant in sum formula : cannot mix place references and integer constants. ");
					// do we already have such a place ?
//					int val = child.getValue();
//					int ind = usedConstants.indexOf(val);
//					if (ind == -1) {
//						ind = usedConstants.size();
//						usedConstants.add(val);
//					}
//					pw.append("<place>p"+(placeCount+ind)+"</place>");
				}
			}
			pw.append("</tokens-count>\n"); 
		}
		default :
		{
			PropertiesToPNML.log.warning("Unexpected operator in formula :" + naryOp);
		}
		}
		return null;
	}

	@Override
	public Void visit(AtomicPropRef apRef) {		
		return apRef.getAp().getExpression().accept(this);
	}
}