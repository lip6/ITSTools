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
//
	private Map<String, Unit> units = new HashMap<String, Unit>();

	private boolean doplaces;

	private boolean dosubs;

	@Override
	public void characters(char[] chars, int beg, int length) throws SAXException {
		if (doplaces || dosubs) {
			String seen = new String(Arrays.copyOfRange(chars, beg, beg+length));
			String[] pnames = seen.split(" ");
			Unit u = stack.peek();
			for (String name : pnames) {
				if (dosubs) {
					u.addUnit(findUnit(name));
				} else {
					u.addPlace(name);
				}
			}
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
		}

		//		} else if ("graphics".equals(baliseName)) { //$NON-NLS-1$
//			String colorLab = attributes.getValue("color");
//			if (colorLab != null) {
//				storeObjectColor(colorLab);				
//			}
//		} else if ("position".equals(baliseName)) { //$NON-NLS-1$
//			handleNodePosition((INode) stack.peek(),attributes);
//		} else if ("deltaLabel".equals(baliseName)) { //$NON-NLS-1$
//			handleNodeLabelPosition((INode) stack.peek(),attributes);
//		} else if ("scheduling".equals(baliseName)) { //$NON-NLS-1$
//			// TODO : Do something to the place ?
//		} else if ("transition".equals(baliseName)) { //$NON-NLS-1$
//			// stack a the place
//			stack.push(handleTransition(attributes));
//		} else if ("arc".equals(baliseName)) { //$NON-NLS-1$
//			// stack a the place
//			stack.push(handleArc(attributes));
//		} else if ("nail".equals(baliseName)) { //$NON-NLS-1$
//			handleArcNail((IArc) stack.peek(), attributes);
//		} else if ("preferences".equals(baliseName)) { //$NON-NLS-1$
//			// NOP
//		} else if ("colorPlace".equals(baliseName)) { //$NON-NLS-1$
//			hasColors = true;
//			handlePlaceColors(attributes);
//		} else if ("colorTransition".equals(baliseName)) { //$NON-NLS-1$
//			hasColors = true;
//			handleTransitionColors(attributes);
//		} else if ("colorArc".equals(baliseName)) { //$NON-NLS-1$
//			// Happily ignore arc colors, feature unimplemented in Romeo GUI anyway.
//			//			handleArcColors(attributes);
//		} else {
//			logger.warning("Unknown XML tag in source file: "+ baliseName); //$NON-NLS-1$
//		}
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
		} else if ("places".equals(baliseName)) { //$NON-NLS-1$
			doplaces = false;
		} else if ("subunits".equals(baliseName)) { //$NON-NLS-1$
			dosubs = false;
//		} else if ("graphics".equals(baliseName)) { //$NON-NLS-1$
//			// NOP
//		
		} else {
			logger.warning("Unknown XML tag in source file: "+ baliseName); //$NON-NLS-1$
		}
	}

//
//	/**
//	 * Handle parse of a place.
//	 * @param attributes the attributes of the place element
//	 * @return the node constructed.
//	 */
//	private INode handlePlace(Attributes attributes) {
//		INode place = null;
//		try {
//			place = graph.createNode(placeFormalism);
//			String label = attributes.getValue("label");
//			if (label != null)
//				place.getAttribute("name").setValue(label);
//			String initialMarking = attributes.getValue("initialMarking");
//			if (initialMarking != null)
//				place.getAttribute("marking").setValue(initialMarking);
//			
//			// add to traces
//			placeIds.put(attributes.getValue("id"), place);
//		} catch (ModelException e) {
//			e.printStackTrace();
//		}
//		return place;
//	}
//
//	/**
//	 * Handle parse of a Transition.
//	 * @param attributes The attributes of the XML element.
//	 * @return the constructed node.
//	 */
//	private INode handleTransition(Attributes attributes) {
//		INode transition = null;
//		try {
//			transition = graph.createNode(transitionFormalism);
//			String label = attributes.getValue("label");
//			if (label != null)
//				transition.getAttribute("label").setValue(label);
//			
//			String eft = attributes.getValue("eft");
//			transition.getAttribute("earliestFiringTime").setValue(eft);
//			
//			String lft = attributes.getValue("lft");
//			if (lft.equals("infini")) {
//				transition.getAttribute("latestFiringTime").setValue("inf");
//			} else {
//				transition.getAttribute("latestFiringTime").setValue(lft);				
//			}
//
//			// add to traces
//			transIds.put(attributes.getValue("id"), transition);
//		} catch (ModelException e) {
//			e.printStackTrace();
//		}
//		return transition;
//	}
//
//
//	/**
//	 * Handle parse of an arc.
//	 * @param attributes The attributes of the XML element.
//	 * @return the arc constructed.
//	 */
//	private IArc handleArc(Attributes attributes) {
//		IArc arc = null;
//		try {
//			INode place = placeIds.get(attributes.getValue("place"));
//			INode trans = transIds.get(attributes.getValue("transition"));
//			
//			
//			String type = attributes.getValue("type");			
//			if ("PlaceTransition".equals(type)) {
//				arc = graph.createArc(arcFormalism, place, trans);
//			} else if ("TransitionPlace".equals(type)) {
//				arc = graph.createArc(arcFormalism, trans, place);
//			} else if ("flush".equals(type)) {
//				arc = graph.createArc(resetFormalism, place, trans);
//			} else if ("read".equals(type)) {
//				arc = graph.createArc(testFormalism, place, trans);
//			} else if ("logicalInhibitor".equals(type)) {
//				arc = graph.createArc(inhibitorFormalism, place, trans);
//			}
//			IAttribute val = arc.getAttribute("valuation");
//			if (val != null) {
//				String weight = attributes.getValue("weight");
//				val.setValue(weight);
//			}
//			
//		} catch (ModelException e) {
//			e.printStackTrace();
//		}
//		return arc;
//	}
//
//	/**
//	 * Parse the position of a node.
//	 * @param node the node
//	 * @param attributes The attributes of the XML element.
//	 */
//	private void handleNodePosition(INode node, Attributes attributes) {
//		float x = Float.parseFloat(attributes.getValue("x"));
//		float y = Float.parseFloat(attributes.getValue("y"));
//		node.getGraphicInfo().setLocation(new Point(x,y));
//	}
//
//	/**
//	 * Parse the position of a node label.
//	 * @param node The node
//	 * @param attributes The attributes of the XML element.
//	 */
//	private void handleNodeLabelPosition(INode node, Attributes attributes) {
//		Point nodePos = node.getGraphicInfo().getLocation();
//		float x = Float.parseFloat(attributes.getValue("deltax"));
//		float y = Float.parseFloat(attributes.getValue("deltay"));
//		Point labPos = new Point(x,y);
//		IAttribute label;
//		if ("place".equals(node.getNodeFormalism().getName())) {
//			label = node.getAttribute("name");
//		} else {
//			label = node.getAttribute("label");			
//		}
//		label.getGraphicInfo().setLocation(labPos.translate(nodePos));
//	}
//


	/**
	 * @return the order loaded from the XML file
	 */
	public IOrder getOrder() {
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
							// sub is necessarily a compsite, or "simple" loop would have created it
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
}
