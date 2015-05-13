package fr.lip6.move.gal.logic.saxparse;

import java.util.Arrays;
import java.util.Stack;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.logic.Not;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.logic.Af;
import fr.lip6.move.gal.logic.Ag;
import fr.lip6.move.gal.logic.Au;
import fr.lip6.move.gal.logic.Ax;
import fr.lip6.move.gal.logic.BooleanExpression;
import fr.lip6.move.gal.logic.CardMarking;
import fr.lip6.move.gal.logic.Comparison;
import fr.lip6.move.gal.logic.ComparisonOperators;
import fr.lip6.move.gal.logic.Constant;
import fr.lip6.move.gal.logic.Ef;
import fr.lip6.move.gal.logic.Eg;
import fr.lip6.move.gal.logic.Enabling;
import fr.lip6.move.gal.logic.Eu;
import fr.lip6.move.gal.logic.Ex;
import fr.lip6.move.gal.logic.IntExpression;
import fr.lip6.move.gal.logic.LogicFactory;
import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.logic.Property;
import fr.lip6.move.gal.logic.PropertyDesc;
import fr.lip6.move.gal.logic.Or;
import fr.lip6.move.gal.logic.And;

public class PropHandler extends DefaultHandler {

	// context stack
	private Stack<Object> stack = new Stack<Object>();

	private boolean dotext = false;

	private Specification spec;

	private Properties props;

	public PropHandler(Specification spec, Properties props) {
		this.spec = spec;
		this.props = props;
	}

	@Override
	public final void startElement(String uri, String localName,
			String baliseName, Attributes attributes) throws SAXException {
		if ("property".equals(baliseName)) { //$NON-NLS-1$

			PropertyDesc pdesc = LogicFactory.eINSTANCE.createPropertyDesc();

			stack.push(pdesc);
			
		} else if ("formula".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("tokens-count".equals(baliseName)) { //$NON-NLS-1$
			CardMarking cmark = LogicFactory.eINSTANCE.createCardMarking();
			stack.push(cmark);
			
		} else if ("is-fireable".equals(baliseName)) { //$NON-NLS-1$
			Enabling enab = LogicFactory.eINSTANCE.createEnabling();
			stack.push(enab);
			
		} else if ("property-set".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("integer-le".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("all-paths".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("finally".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("exits-path".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("negation".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("disjunction".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("conjunction".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("until".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("before".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("reach".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("next".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("description".equals(baliseName)) { //$NON-NLS-1$
			dotext = true;
		} else if ("place".equals(baliseName)) { //$NON-NLS-1$
			dotext = true;
		} else if ("integer-constant".equals(baliseName)) { //$NON-NLS-1$
			dotext = true;
		} else if ("id".equals(baliseName)) { //$NON-NLS-1$
			dotext = true;
		} else if ("transition".equals(baliseName)) { //$NON-NLS-1$
			dotext = true;
		}
	}

	@Override
	public void characters(char[] chars, int beg, int length)
			throws SAXException {
		if (dotext) {
			String name = new String(Arrays.copyOfRange(chars, beg, beg
					+ length));
			stack.push(name);
			
		}
	}

	@Override
	public void endElement(String uri, String localName, String baliseName)
			throws SAXException {
		if ("property".equals(baliseName)) { //$NON-NLS-1$			
			props.getProps().add((PropertyDesc) stack.pop());
			
			// reset to null ?
		} else if ("formula".equals(baliseName)) { //$NON-NLS-1$
			Property prop = LogicFactory.eINSTANCE.createProperty();
			prop.setFormula((BooleanExpression) stack.pop());
			PropertyDesc pdesc = (PropertyDesc) stack.peek();
			pdesc.setProp(prop);
			
		} else if ("integer-le".equals(baliseName)) { //$NON-NLS-1$
			Comparison comp = LogicFactory.eINSTANCE.createComparison();
			comp.setOperator(ComparisonOperators.LE);
			comp.setRight((IntExpression) stack.pop());
			comp.setLeft((IntExpression) stack.pop());
			stack.push(comp);
			

		} else if ("negation".equals(baliseName)) { //$NON-NLS-1$
			Not neg = LogicFactory.eINSTANCE.createNot();
			neg.setValue((BooleanExpression) stack.pop());
			stack.push(neg);
			

		} else if ("tokens-count".equals(baliseName)) { //$NON-NLS-1$

		} else if ("description".equals(baliseName)) { //$NON-NLS-1$
			String name = (String) stack.pop();
			PropertyDesc prop = (PropertyDesc) stack.peek();
			prop.setComment(name);
			dotext = false;
			
		} else if ("place".equals(baliseName)) {			
			String name = (String) stack.pop();		
			CardMarking cmark = (CardMarking) stack.peek();
			cmark.getPlaces().add(findVarDecl(spec, name));

			dotext = false;
			
		} else if ("integer-constant".equals(baliseName)) {
			String name = (String) stack.pop();
			Constant cte = LogicFactory.eINSTANCE.createConstant();
			cte.setValue(Integer.parseInt(name));
			stack.push(cte);
			
			dotext = false;
			
		} else if ("id".equals(baliseName)) { //$NON-NLS-1$
			String name = (String) stack.pop();
			PropertyDesc prop = (PropertyDesc) stack.peek();
			prop.setName(name);
			dotext = false;
			
		} else if ("disjunction".equals(baliseName)) { //$NON-NLS-1$
			Or disjun = LogicFactory.eINSTANCE.createOr();
			disjun.setRight((BooleanExpression) stack.pop());
			disjun.setLeft((BooleanExpression) stack.pop());
			stack.push(disjun);
	
		} else if ("conjunction".equals(baliseName)) { //$NON-NLS-1$
			And cong = LogicFactory.eINSTANCE.createAnd();
			cong.setRight((BooleanExpression) stack.pop());
			cong.setLeft((BooleanExpression) stack.pop());
			stack.push(cong);
			
		} else if ("globally".equals(baliseName) || "finally".equals(baliseName) || "next".equals(baliseName) || "until".equals(baliseName)) {
			
			stack.push(baliseName);
		
		} else if ("all-paths".equals(baliseName)) { //$NON-NLS-1$
			
			String childbalise = (String) stack.pop();
			if (childbalise.equals("globally")) {
				Ag ag = LogicFactory.eINSTANCE.createAg();
				ag.setForm((BooleanExpression) stack.pop());
				stack.push(ag);
				
			} else if (childbalise.equals("finally")) {
				Af af = LogicFactory.eINSTANCE.createAf();
				af.setForm((BooleanExpression) stack.pop());
				stack.push(af);
				
			} else if (childbalise.equals("next")) {
				Ax ax = LogicFactory.eINSTANCE.createAx();
				ax.setForm((BooleanExpression) stack.pop());
				stack.push(ax);
				
			} else if (childbalise.equals("until")) {
				Au au = LogicFactory.eINSTANCE.createAu();
				au.setRight((BooleanExpression) stack.pop());
				au.setLeft((BooleanExpression) stack.pop());
				stack.push(au);
			}

		}  else if ("exists-path".equals(baliseName)) { //$NON-NLS-1$
			String childbalise = (String) stack.pop();
			if (childbalise.equals("globally")) {
				Eg eg = LogicFactory.eINSTANCE.createEg();
				eg.setForm((BooleanExpression) stack.pop());
				stack.push(eg);
				
			} else if (childbalise.equals("finally")) {
				Ef ef = LogicFactory.eINSTANCE.createEf();
				ef.setForm((BooleanExpression) stack.pop());
				stack.push(ef);
				
			} else if (childbalise.equals("next")) {
				Ex ex = LogicFactory.eINSTANCE.createEx();
				ex.setForm((BooleanExpression) stack.pop());
				stack.push(ex);
				
			} else if (childbalise.equals("until")) {
				Eu eu = LogicFactory.eINSTANCE.createEu();
				eu.setRight((BooleanExpression) stack.pop());
				eu.setLeft((BooleanExpression) stack.pop());
				stack.push(eu);
				
			}
		} else if ("transition".equals(baliseName)) {
			String name = (String) stack.pop();
			Enabling enab = (Enabling) stack.peek();
			enab.getTrans().add(findTransition(name));
			
			dotext = false;
			
		}
	}

	private Transition findTransition(String name) {
		name = normalizeName(name);
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				
				for (Transition t : gal.getTransitions()) {
					if (t.getName().equals(name)) {
						return t;
					}
				}
			}
		}
		getLog().warning("Could not find GAL transition for : " + name);
		return null;
	}

	public static String normalizeName(String text) {
		String res = text.replace(' ', '_');
		res = res.replace('-', '_');
		res = res.replace('/', '_');
		res = res.replace('*', 'x');
		res = res.replace('=', '_');
		
		return res;
	}
	
	private VarDecl findVarDecl(Specification spec2, String name) {
		name = normalizeName(name);
		for (TypeDeclaration td : spec2.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				for (Variable var : gal.getVariables()) {
					if (var.getName().equals(name)) {
						return var;
					}
				}
				for (ArrayPrefix var : gal.getArrays()) {
					if (var.getName().equals(name)) {
						return var;
					}
				}
			}
		}
		getLog().warning("Could not find GAL variable for : " + name);
		return null;
	}

	private Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

}
