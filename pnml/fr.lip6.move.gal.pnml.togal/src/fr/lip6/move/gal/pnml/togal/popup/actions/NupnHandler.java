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
package fr.lip6.move.gal.pnml.togal.popup.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.lip6.move.gal.order.IOrder;


/**
 * A class to parse a Romeo model from an XML file.
 * @author Yann TM 
 */
public class NupnHandler extends DefaultHandler {
	private final Logger logger = Logger.getLogger("fr.lip6.move.gal"); //$NON-NLS-1$

	// context stack
	private Stack<Object> stack = new Stack<Object>();

//	// to reconstruct Romeo IDS when parsing arcs.
//	private Map<String, INode> placeIds = null;
//	private Map<String, INode> transIds = null;
//
	// object constructed
	private IOrder order;
//
//	private Map<String, List<INode>> transColors;
//
//	private Map<String, List<INode>> placeColors;





	/** {@inheritDoc} */
	@Override
	public final void startElement(String uri, String localName, String baliseName, Attributes attributes) throws SAXException {
//		// Balise MODEL
//		if ("unit".equals(baliseName)) { //$NON-NLS-1$
//			// stack a the place
//			stack.push(handleUnit(attributes));
//			
//		} else if ("places".equals(baliseName)) { //$NON-NLS-1$
//			// stack a the place
//			stack.push(handlePlace(attributes));
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





	/** {@inheritDoc} */
	@Override
	public final void endElement(String uri, String localName, String baliseName) throws SAXException {
		// Balise MODEL
		if ("TPN".equals(baliseName)) { //$NON-NLS-1$
//			if (! hasColors) {
//				// means the romeo model has default appearance = skyBlue places and yellow transitions.
//				for (INode node : graph.getNodes()) 
//					if ("place".equals(node.getNodeFormalism().getName())) {
//						node.getGraphicInfo().setBackground(ColorConstants.lightBlue);						
//					} else {
//						node.getGraphicInfo().setBackground(ColorConstants.yellow);												
//					}
//			}
//			// cleanup
//			placeIds = null;
//			transIds = null;
//			transColors = null;
////			arcColors = null;
//			placeColors = null;
//			hasColors = false;
		} else if ("place".equals(baliseName)) { //$NON-NLS-1$
			// pop place from context stack
			stack.pop();
		} else if ("graphics".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("position".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("deltaLabel".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("nail".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("transition".equals(baliseName)) { //$NON-NLS-1$
			// pop transition from context stack
			stack.pop();
		} else if ("arc".equals(baliseName)) { //$NON-NLS-1$
			// stack a the place
			stack.pop();
		} else if ("preferences".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("colorPlace".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("colorTransition".equals(baliseName)) { //$NON-NLS-1$
			// NOP
		} else if ("colorArc".equals(baliseName)) { //$NON-NLS-1$
			// NOP
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
		return order;
	}
}
