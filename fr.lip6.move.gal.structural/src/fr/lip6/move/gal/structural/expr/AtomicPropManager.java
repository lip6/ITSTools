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
	private List<AtomicProp> atoms = new ArrayList<>();
	private Map<Expression, AtomicProp> atomMap = new HashMap<Expression, AtomicProp>();

	public void loadAtomicProps(List<Property> props) {
		atoms.clear();
		atomMap.clear();
		Map<String, AtomicProp> uniqueMap = new HashMap<>();
		// look for atomic propositions
		if (!props.isEmpty()) {
			for (Property prop : props) {
				if (prop.getType() == PropertyType.INVARIANT) {
					Expression be = ((BinOp) prop.getBody()).left;
					if (prop.getBody().getOp() == Op.EF) {
						be = Expression.not(be);
					}
					atoms.add(new AtomicProp(prop.getName().replaceAll("-", ""), be));
				} else if (prop.getType() == PropertyType.LTL || prop.getType() == PropertyType.CTL) {
					collectAP(prop.getBody(), uniqueMap);
				}
			}

		}

	}

	private Void collectAP(Expression obj, Map<String, AtomicProp> uniqueMap) {
		if (isPureBool(obj)) {
			// helps to recognize that !AP is the negation of AP
			// Can reduce number of AP as well as help simplifications
			if (obj.getOp() == Op.NOT) {
				obj = obj.childAt(0);
			}
			String stringProp = toString(obj);
			AtomicProp atom = uniqueMap.get(stringProp);
			if (atom == null) {
				atom = new AtomicProp("p" + atoms.size(), obj);
				atoms.add(atom);
				uniqueMap.put(stringProp, atom);
			}
			atomMap.put(obj, atom);
		} else {
			obj.forEachChild(c -> collectAP(c, uniqueMap));
		}
		return null;
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
			case GT:
			case GEQ:
			case EQ:
			case NEQ:
			case LT:
			case LEQ:
				return true;
			default:
				return false;
			}
		}
	}

	public List<AtomicProp> getAtoms() {
		return atoms;
	}

	public Map<Expression, AtomicProp> getAtomMap() {
		return atomMap;
	}

	public int size() {
		return atoms.size();
	}
}
