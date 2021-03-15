package fr.lip6.move.gal.structural.expr;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;

public class AtomicPropManager {
	//private List<AtomicProp> atoms = new ArrayList<>();
	private Map<String, Expression> propsWithAp = new HashMap<>();
	private Map<String, AtomicProp> uniqueMap = new HashMap<>();
	private Map<String, AtomicProp> apMap = new LinkedHashMap<>();
	
	public AtomicProp registerExpression (Expression e) {
		AtomicProp atom = new AtomicProp("p" + apMap.size(), e);
		apMap.put(atom.getName(),atom);
		return atom;
	}

	public Collection<AtomicProp> getAtoms() {
		return apMap.values();
	}

	public Map<String, Expression> loadAtomicProps(List<Property> props) {
		apMap.clear();
		propsWithAp.clear();
		uniqueMap.clear();
		
		for (Property prop : props) {
			if (prop.getType() == PropertyType.LTL || prop.getType() == PropertyType.CTL || prop.getType() == PropertyType.INVARIANT) {
				Expression e = collectAndRewriteUsingAtoms(prop.getBody(), uniqueMap);
				propsWithAp.put(prop.getName(), e);
			}
		}		
		return propsWithAp;
	}

	public int size() {
		return apMap.size();
	}

	public static boolean isPureBool(Expression obj) {
		if (obj == null) {
			return true;
		} else {
			switch (obj.getOp()) {
			case AND:
			case OR:
			case NOT: {
				for (int i = 0, ie = obj.nbChildren(); i < ie; i++) {
					Expression child = obj.childAt(i);
					if (!isPureBool(child)) {
						return false;
					}
				}
				return true;
			}
			case ENABLED:
			case GT:
			case GEQ:
			case EQ:
			case NEQ:
			case LT:
			case LEQ:
			case BOOLCONST:
				return true;
			default:
				return false;
			}
		}
	}

	public static String toString(Expression e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		{
			CExpressionPrinter printer = new CExpressionPrinter(new PrintWriter(baos), "src");
			e.accept(printer);
			printer.close();
		}
		return baos.toString();
	}
	
	public Expression collectAndRewriteUsingAtoms (Expression expr, Map<String, AtomicProp> uniqueMap) {
		if (expr == null) {
			return expr;
		} else if (expr.getOp() == Op.BOOLCONST) {
			return expr;
		} else if (expr.getOp() == Op.NOT) {
			// helps to recognize that !AP is the negation of AP
			// Can reduce number of AP as well as help simplifications
			Expression nc = collectAndRewriteUsingAtoms(expr.childAt(0),uniqueMap);
			if (nc != expr.childAt(0)) {
				return Expression.not(nc);
			} else {
				return expr;
			}			
		} else if (isPureBool(expr)) {
			
			String stringProp = toString(expr);
			AtomicProp atom = uniqueMap.get(stringProp);
			if (atom == null) {
				Expression neg = Simplifier.pushNegation(Expression.not(expr));
				String negstringProp = toString(neg);
				atom = uniqueMap.get(negstringProp);
				if (atom == null) {
					atom = new AtomicProp("p" + apMap.size(), expr);
					apMap.put(atom.getName(),atom);
					uniqueMap.put(stringProp, atom);
				} else {
					// we are the negation of an existing AP
					return Expression.not(Expression.apRef(atom));
				}
			}
			return Expression.apRef(atom);
		} else if ( expr.nbChildren() > 2 && (expr.getOp() == Op.OR || expr.getOp() == Op.AND) ) {
			
			List<Expression> subAtoms = new ArrayList<>();
			List<Expression> others = new ArrayList<>();
			for (int cid=0, cide=expr.nbChildren() ; cid < cide ; cid++) {
				Expression child = expr.childAt(cid);
				if (isPureBool(child)) {
					subAtoms.add(child);
				} else {
					others.add(child);
				}
			}
			if (subAtoms.size() > 2) {
				// build a new atom for these children, we might make too many AP for spot otherwise
				Expression newAtom = Expression.nop(expr.getOp(), subAtoms);
				AtomicProp ap = registerExpression(newAtom);
				
				// rewrite "others"
				List<Expression> resc = new ArrayList<>(others.size()+1);
				for (Expression child:others) {
					resc.add(collectAndRewriteUsingAtoms(child, uniqueMap));					
				}
				// rewrite expression
				resc.add(Expression.apRef(ap));
				
				Expression res = Expression.nop(expr.getOp(),resc);
				
				return res;
			}
		}
		
		// recursive case
		List<Expression> resc = new ArrayList<>(expr.nbChildren());
		boolean changed = false;
		for (int ci=0,cie=expr.nbChildren() ; ci < cie ;  ci++) {
			Expression child = expr.childAt(ci);
			Expression nc = collectAndRewriteUsingAtoms(child, uniqueMap);
			if (nc != child) {
				changed = true;
			}
			resc.add(nc);
		}
		if (changed) {
			return Expression.nop(expr.getOp(),resc);
		} else {
			return expr;
		}		
	}

	public Expression getAPformula(String name) {
		return propsWithAp.get(name);
	}
	public AtomicProp findAP(String name) {
		return apMap.get(name);		
	}

}
