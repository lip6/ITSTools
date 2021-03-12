package fr.lip6.move.gal.structural.expr;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;

public class AtomicPropManager {
	private Map<Expression, AtomicProp> atomMap = new HashMap<Expression, AtomicProp>();
	private List<AtomicProp> atoms = new ArrayList<>();
	private Map<String,Expression> propsWithAp = new HashMap<>();
	
	
	public AtomicProp registerExpression (Expression e) {
		AtomicProp atom = new AtomicProp("p" + atoms.size(), e);
		atoms.add(atom);
		atomMap.put(e, atom);
		return atom;
	}

	public Map<Expression, AtomicProp> getAtomMap() {
		return atomMap;
	}

	public List<AtomicProp> getAtoms() {
		return atoms;
	}

	public Map<String, Expression> loadAtomicProps(List<Property> props) {
		atoms.clear();
		atomMap.clear();
		propsWithAp.clear();
		Map<String, AtomicProp> uniqueMap = new HashMap<>();
		
		for (Property prop : props) {
			if (prop.getType() == PropertyType.LTL || prop.getType() == PropertyType.CTL || prop.getType() == PropertyType.INVARIANT) {
				Expression e = collectAndRewriteUsingAtoms(prop.getBody(), uniqueMap);
				propsWithAp.put(prop.getName(), e);
			}
		}		
		return propsWithAp;
	}

	public int size() {
		return atoms.size();
	}

	private static boolean isPureBool(Expression obj) {
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
				atom = new AtomicProp("p" + atoms.size(), expr);
				atoms.add(atom);
				uniqueMap.put(stringProp, atom);
			}
			atomMap.put(expr, atom);
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
					
				// rewrite expression
				others.add(Expression.apRef(ap));
				Expression res = Expression.nop(expr.getOp(),others);
				
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
	

}
