package fr.lip6.move.gal.mcc.properties;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
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
	 * @return true if we had to introduce a special "one" place to represent constants.
	 * @throws IOException
	 */
	public static boolean transform(SparsePetriNet spn, String path, DoneProperties doneProps) throws IOException {
		long time = System.currentTimeMillis();
		PrintWriter pw = new PrintWriter(new File(path));
		pw.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		
		pw.append("<property-set xmlns=\"http://mcc.lip6.fr/\">\n");
		boolean usesConstants = false;
		for (Property prop : spn.getProperties()) {
			if (! doneProps.containsKey(prop.getName())) {
				pw.append("  <property>\n" + 
						"    <id>"+ prop.getName() +"</id>\n" + 
						"    <description>Automatically generated</description>\n" + 
						"    <formula>\n");
				if (exportProperty(pw, prop.getBody(), prop.getType(), spn)) {
					usesConstants = true;
				}
				pw.append("    </formula>\n" + 
						"  </property>\n" + 
						"");
			} else {
				continue;
				// This block would output trivially true/false properties.
//				if (res) {
//					// true => 0 <= 0
//					pw.append("<integer-le><integer-constant>0</integer-constant><integer-constant>0</integer-constant></integer-le>\n");						
//				} else {
//					// false => 1 <= 0
//					pw.append("<integer-le><integer-constant>1</integer-constant><integer-constant>0</integer-constant></integer-le>\n");														
//				}
			}
		}
		pw.append("</property-set>\n\n");
		pw.close();
		getLog().info("Export to MCC properties in file "+path +" took "+ (System.currentTimeMillis()-time) + " ms.");
		return usesConstants;
	}


	private static boolean exportProperty(PrintWriter pw, Expression body, PropertyType type, ISparsePetriNet spn) {
		if (body == null) {
			return false;
		} else if (type == PropertyType.DEADLOCK) {
			pw.append("      <exists-path><finally><deadlock/></finally></exists-path>\n");
			return false;
		} else {
			PrintVisitor v = new PrintVisitor(pw,type,spn.getPlaceCount());
			body.accept(v);
			return v.getUsesConstant();
		}
	}
		
}

class PrintVisitor implements ExprVisitor<Void> {

	private PrintWriter pw;
	private boolean usesConstant=false;
	private PropertyType type;
	private int placeCount;

	public PrintVisitor(PrintWriter pw, PropertyType type, int placeCount) {
		this.pw = pw;
		this.type = type;
		this.placeCount = placeCount;
	}

	public boolean getUsesConstant() {
		return usesConstant;
	}
	
	@Override
	public Void visit(VarRef varRef) {
		if (type == PropertyType.BOUNDS) {
			pw.append("              <place-bound>\n") 
			.append("                <place>p"+ varRef.index+"</place>\n") 
			.append("              </place-bound>\n");		
		} else {
			pw.append("              <tokens-count>\n") 
			.append("                <place>p"+ varRef.index+"</place>\n") 
			.append("              </tokens-count>\n");
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

	@Override
	public Void visit(NaryOp naryOp) {
		switch (naryOp.getOp()) {
		case AND :
		{
			pw.append("<conjunction>\n");
			for (Expression child : naryOp.getChildren()) {
				child.accept(this);
			}
			pw.append("</conjunction>\n");
			break;
		}
		case OR :
		{
			pw.append("<disjunction>\n");
			for (Expression child : naryOp.getChildren()) {
				child.accept(this);
			}
			pw.append("</disjunction>\n");
			break;
		}
		case ADD : 
		{
			if (type == PropertyType.BOUNDS) {
				pw.append("              <place-bound>\n");
			} else {
				pw.append("              <tokens-count>\n"); 
			}
			for (Expression child : naryOp.getChildren()) {
				if (child.getOp() == Op.PLACEREF) {
					pw.append("                <place>p"+ child.getValue()+"</place>\n");
				} else if (child.getOp() == Op.CONST) {
					for (int i=0; i < child.getValue(); i++) {
						pw.append("                <place>p"+placeCount+"</place>\n");
					}
					usesConstant = true;
				}
			}
			if (type == PropertyType.BOUNDS) {
				pw.append("              </place-bound>\n"); 
			} else {
				pw.append("              </tokens-count>\n"); 
			}
			break;
		}
		case ENABLED:
		{
			pw.append("              <is-fireable>\n"); 
			for (Expression child : naryOp.getChildren()) {
				if (child.getOp() == Op.TRANSREF) {
					pw.append("                <transition>t"+ child.getValue()+"</transition>\n");
				} else {
					throw new IllegalArgumentException("Unexpected child of enabled should be a transition.");
				}
			}
			pw.append("              </is-fireable>\n"); 
			break;
		}
		case CARD:
		{
			pw.append("              <tokens-count>\n"); 
			for (Expression child : naryOp.getChildren()) {
				if (child.getOp() == Op.PLACEREF) {
					pw.append("                <place>p"+ child.getValue()+"</place>\n");
				} else if (child.getOp() == Op.CONST) {
					for (int i=0; i < child.getValue(); i++) {
						pw.append("                <place>p"+placeCount+"</place>\n");
					}
					usesConstant = true;
				}
			}
			pw.append("              </tokens-count>\n"); 
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