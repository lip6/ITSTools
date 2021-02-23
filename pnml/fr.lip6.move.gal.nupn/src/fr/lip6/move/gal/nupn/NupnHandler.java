/**
 * Copyright (c) 2006-2010 MoVe - Laboratoire d'Informatique de Paris 6 (LIP6).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jean-Baptiste VORON (LIP6) - Project Head / Initial contributor
 *   Clément DÉMOULINS (LIP6) - Project Manager
 *   Yann THIERRY-MIEG (LIP6)
 *
 * Official contacts:
 *   coloane@lip6.fr
 *   http://coloane.lip6.fr
 */
package fr.lip6.move.gal.nupn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.lip6.move.gal.order.CompositeGalOrder;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.order.VarOrder;


/**
 * A class to parse a Romeo model from an XML file.
 * @author Yann TM 
 */
public class NupnHandler extends DefaultHandler {
	private final Logger logger = Logger.getLogger("fr.lip6.move.gal"); //$NON-NLS-1$

	// context stack
	private Stack<Unit> stack = new Stack<Unit>();

	// object constructed
	private IOrder order;
	private boolean isSafe = false;
//
	private Map<String, Unit> units = new HashMap<String, Unit>();

	private StringBuilder readString = new StringBuilder();
	private boolean doplaces;

	private boolean dosubs;

	@Override
	public void characters(char[] chars, int beg, int length) throws SAXException {
		if (doplaces || dosubs) {
			readString.append(new String(Arrays.copyOfRange(chars, beg, beg+length)));
		} 
	}
	
	
	/** {@inheritDoc} */
	@Override
	public final void startElement(String uri, String localName, String baliseName, Attributes attributes) throws SAXException {
		if ("unit".equals(baliseName)) { //$NON-NLS-1$
			// stack a the place
			stack.push(findUnit(attributes.getValue("id")));

		} else if ("places".equals(baliseName)) { //$NON-NLS-1$
			doplaces = true;
		}  else if ("subunits".equals(baliseName)) { //$NON-NLS-1$
			dosubs = true;
		}  else if ("size".equals(baliseName)) { //$NON-NLS-1$
			// skip
			//<size places="P" transitions="T" arcs="A"/>
		}  else if ("structure".equals(baliseName)) { //$NON-NLS-1$
			// skip
			//<size places="P" transitions="T" arcs="A"/>
			if ("false".equals(attributes.getValue("safe"))) {
				isSafe = false;
			} else {
				isSafe = true;
			}
			
		} else {
			logger.warning("Unknown XML tag in source file: "+ baliseName); //$NON-NLS-1$
		}
	}





	private Unit findUnit(String id) {
		Unit u = units.get(id);
		if (u == null) {
			u = new Unit(id);
			units.put(id,u);
		}
		return u;
	}





	/** {@inheritDoc} */
	@Override
	public final void endElement(String uri, String localName, String baliseName) throws SAXException {
		// Balise MODEL
		if ("unit".equals(baliseName)) { //$NON-NLS-1$
			stack.pop();
		} else if ( "places".equals(baliseName) || "subunits".equals(baliseName) ) { //$NON-NLS-1$
			
			String[] pnames = readString.toString().replaceAll("\n","").split(" ");
			Unit u = stack.peek();
			for (String name : pnames) {
				if (name.isEmpty()) {
					continue;
				}
				if (dosubs) {
					u.addUnit(findUnit(name));
				} else {
					u.addPlace(name);
				}
			}
			readString = new StringBuilder();
			doplaces = false;
			dosubs = false;
		} else if ("size".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("structure".equals(baliseName)) { //$NON-NLS-1$
			// NOP
//		
		} else {
			logger.warning("Unknown XML tag in source file: "+ baliseName); //$NON-NLS-1$
		}
	}




	/**
	 * @return the order loaded from the XML file
	 */
	public IOrder getOrder() {
		if (units.isEmpty())
			return null;
		if (order == null) {
			// get rid of mixed place/unit units : run on a copy to avoid concurrent modif problems on units
			for (Unit u : new ArrayList<Unit>(units.values())) {
				// this is mixed
				if (! u.getPlaces().isEmpty() && ! u.getSubunits().isEmpty()) {
					// find a new name for the new unit
					String nuname ;
					Unit nunit=null;
					for (int i=1; ; i++) {
						nuname = "u" +i;
						if (! units.containsKey(nuname)) {
							// creates
							nunit = findUnit(nuname);
							break;
						}
					}
					// transfer places
					nunit.getPlaces().addAll(u.getPlaces());
					u.getPlaces().clear();
					// add new as child of current
					u.addUnit(nunit);
				}
			}
			
			// to identify the root of order
			Set<String> refd = new HashSet<String>();
			// map ids to IOrder objects
			Map<String, IOrder> orders = new HashMap<String, IOrder>();
			// Build order : simple first
			for (Unit u : units.values()) {
				if (! u.getPlaces().isEmpty()) {
					orders.put(u.getId(), new VarOrder(u.getPlaces(), u.getId()));
				} 
			}
			// composed now
			for (Unit u : units.values()) {
				if (! u.getSubunits().isEmpty()) {
					CompositeGalOrder ord = (CompositeGalOrder) orders.get(u.getId());
					if (ord == null) {
						ord = new CompositeGalOrder(new ArrayList<IOrder>(),u.getId());
						orders.put(u.getId(), ord);
					}
					for (Unit sub : u.getSubunits()) {
						refd.add(sub.getId());
						IOrder subord = orders.get(sub.getId());
						if (subord == null) {
							// sub is necessarily a composite, or "simple" loop would have created it
							subord = new CompositeGalOrder(new ArrayList<IOrder>(),sub.getId());
							orders.put(sub.getId(), subord);							
						}
						ord.getChildren().add(subord);
					}					
				}
			}
			//only one unrefd in principle
			HashSet<String> master = new HashSet<String>(orders.keySet());
			master.removeAll(refd);
			if (master.size() == 1) {
				order = orders.get(master.iterator().next());
			}
			
		}
		return order;
	}
	
	public boolean isSafe() {
		return isSafe;
	}
}
