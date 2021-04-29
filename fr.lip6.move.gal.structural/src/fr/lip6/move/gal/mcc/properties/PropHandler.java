package fr.lip6.move.gal.mcc.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;
import fr.lip6.move.gal.structural.expr.Op;

public class PropHandler extends DefaultHandler {
	// context stack
	private Stack<Object> stack = new Stack<Object>();

	private boolean dotext = false;
	private boolean isLTL = false;
	
	private PetriNet spec;

	public PropHandler(PetriNet spn, boolean isLTL) {
		this.spec = spn;
		this.isLTL = isLTL;
	}

	@Override
	public final void startElement(String uri, String localName,
			String baliseName, Attributes attributes) throws SAXException {
		if ("property".equals(baliseName)) { //$NON-NLS-1$
			Property pdesc = new Property();
			stack.push(pdesc);
		} else if ("formula".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("tokens-count".equals(baliseName)) { //$NON-NLS-1$
			stack.push(Expression.nop(Op.CARD));
		} else if ("place-bound".equals(baliseName)) { //$NON-NLS-1$
			stack.push(Expression.nop(Op.BOUND));
		} else if ("is-fireable".equals(baliseName)) { //$NON-NLS-1$
			stack.push(Expression.nop(Op.ENABLED));
		} else if ("property-set".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("integer-le".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("all-paths".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("finally".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("exists-path".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("negation".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("disjunction".equals(baliseName)) { //$NON-NLS-1$
			// prepare Nary operator
			stack.push(Op.OR);
		} else if ("conjunction".equals(baliseName)) { //$NON-NLS-1$
			// prepare Nary operator
			stack.push(Op.AND);
		} else if ("until".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("before".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("reach".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("deadlock".equals(baliseName)) {
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
		} else if ("globally".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING			
		} else {
			getLog().warning("Unexpected XML tag in property file :" + baliseName);
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
			spec.getProperties().add((Property) stack.pop());
			
			// reset to null ?
		} else if ("formula".equals(baliseName)) { //$NON-NLS-1$
			Expression child = (Expression) stack.pop();
			Property pdesc = (Property) stack.peek();
			pdesc.setBody(child);
			
		} else if ("integer-le".equals(baliseName)) { //$NON-NLS-1$
			popBinary(Op.LEQ);
		} else if ("negation".equals(baliseName)) { //$NON-NLS-1$
			stack.push(Expression.not((Expression) stack.pop()));
		} else if ("is-fireable".equals(baliseName)) { //$NON-NLS-1$

		} else if ("tokens-count".equals(baliseName)) { //$NON-NLS-1$

		} else if ("deadlock".equals(baliseName)) {
			stack.push(Expression.op(Op.DEAD, null, null));
		} else if ("description".equals(baliseName)) { //$NON-NLS-1$
			String name = (String) stack.pop();
			Property prop = (Property) stack.peek();
			// prop.setComment(name);
			dotext = false;
			
		} else if ("place".equals(baliseName)) {			
			String name = (String) stack.pop();
			NaryOp context = (NaryOp) stack.peek();
			context.addChild(Expression.var(findPlace(name)));
			dotext = false;
			
		} else if ("integer-constant".equals(baliseName)) {
			String name = (String) stack.pop();
			stack.push(Expression.constant(Integer.parseInt(name)));
			dotext = false;
			
		} else if ("id".equals(baliseName)) { //$NON-NLS-1$
			String name = (String) stack.pop();
			Property prop = (Property) stack.peek();
			prop.setName(name);	
			dotext = false;
			
		} else if ("disjunction".equals(baliseName)) { //$NON-NLS-1$
			popNary(Op.OR);
		} else if ("conjunction".equals(baliseName)) { //$NON-NLS-1$
			popNary(Op.AND);
 		} else if ("transition".equals(baliseName)) {
			String name = (String) stack.pop();
			NaryOp enab = (NaryOp) stack.peek();
			enab.addChild(Expression.trans(findTransition(name)));			
			dotext = false;			
		} else if ("before".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
		} else if ("reach".equals(baliseName)) { //$NON-NLS-1$
			// NOTHING
 		} else if (! isLTL) {
			// temporal operator handling for CTL properties 
			if ( ("globally".equals(baliseName) || "finally".equals(baliseName) || "next".equals(baliseName) || "until".equals(baliseName) ) ) {
				stack.push(baliseName);
			} else if ("all-paths".equals(baliseName)) { //$NON-NLS-1$
				String childbalise = (String) stack.pop();
				if (childbalise.equals("globally")) {
					stack.push(Expression.op(Op.AG, (Expression) stack.pop(), null));
				} else if (childbalise.equals("finally")) {
					stack.push(Expression.op(Op.AF, (Expression) stack.pop(), null));
				} else if (childbalise.equals("next")) {
					stack.push(Expression.op(Op.AX, (Expression) stack.pop(), null));
				} else if (childbalise.equals("until")) {
					popBinary(Op.AU);
				}
			} else if ("exists-path".equals(baliseName)) { //$NON-NLS-1$
				String childbalise = (String) stack.pop();
				if (childbalise.equals("globally")) {
					stack.push(Expression.op(Op.EG, (Expression) stack.pop(), null));
				} else if (childbalise.equals("finally")) {
					stack.push(Expression.op(Op.EF, (Expression) stack.pop(), null));
				} else if (childbalise.equals("next")) {
					stack.push(Expression.op(Op.EX, (Expression) stack.pop(), null));
				} else if (childbalise.equals("until")) {
					popBinary(Op.EU);
				}
			}
		} else {
			// temporal operator handling for LTL properties
			if ("all-paths".equals(baliseName)) { 
				// only as first node 
				// hence leave stack alone and skip this node
			} else if ("globally".equals(baliseName)) {
				stack.push(Expression.op(Op.G, (Expression) stack.pop(), null));
			} else if ("finally".equals(baliseName)) {
				stack.push(Expression.op(Op.F, (Expression) stack.pop(), null));
			} else if ("next".equals(baliseName)) {
				stack.push(Expression.op(Op.X, (Expression) stack.pop(), null));
			} else if ("until".equals(baliseName)) {
				popBinary(Op.U);
			}
		}
	}

	private int findPlace(String name) {
		int index = spec.getPlaceIndex(normalizeName(name));
		if (index < 0) {
			System.out.println("Unknown place :\""+name+"\" in property !");
			throw new IllegalArgumentException("Unknown place :\""+name+"\" in property !");
		}
		return index;
	}

	public void popNary(Op op) {
		List<Expression> operands = new ArrayList<>();
		while (stack.peek() instanceof Expression) {
			Expression r = (Expression) stack.pop();
			operands.add(r);
		}
		// the operator, should match op
		stack.pop();
		stack.push(Expression.nop(op, operands));
	}

	
	public void popBinary(Op op) {
		Expression r = (Expression) stack.pop();
		Expression l = (Expression) stack.pop();
		stack.push(Expression.op(op, l, r));
	}

	private Map<String,Integer> tcache = null;
	private int findTransition(String name) {
		if (spec instanceof ISparsePetriNet) {
			ISparsePetriNet spn = (ISparsePetriNet) spec;
			if (tcache == null) {
				// no reindex https://stackoverflow.com/questions/434989/hashmap-initialization-parameters-load-initialcapacity
				tcache = new HashMap<> ( (spn.getTnames().size() *4+2)/3 );
				int i=0;				
				for (String tr : spn.getTnames()) {
					tcache.put(tr, i++);
				}
			}			
		}
		int index;
		if (tcache == null)
			index = spec.getTransitionIndex(normalizeName(name));
		else
			index = tcache.get(normalizeName(name));
		if (index < 0) {
			System.out.println("Unknown transition named :\""+name+"\""+" in property.");
			throw new IllegalArgumentException("Unknown transition named :\""+name+"\""+" in property.");
		}
		return index;
	}

	public static String normalizeName(String text) {
		String res = text.replace(' ', '_');
		res = res.replace('-', '_');
		res = res.replace('/', '_');
		res = res.replace('*', 'x');
		res = res.replace('=', '_');
		
		return res;
	}
	
	private Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

}
