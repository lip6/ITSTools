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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.lip6.move.pnml.ptnet.Arc;
import fr.lip6.move.pnml.ptnet.Name;
import fr.lip6.move.pnml.ptnet.Node;
import fr.lip6.move.pnml.ptnet.PNType;
import fr.lip6.move.pnml.ptnet.PTArcAnnotation;
import fr.lip6.move.pnml.ptnet.PTMarking;
import fr.lip6.move.pnml.ptnet.Page;
import fr.lip6.move.pnml.ptnet.PetriNet;
import fr.lip6.move.pnml.ptnet.Place;
import fr.lip6.move.pnml.ptnet.PnObject;
import fr.lip6.move.pnml.ptnet.PtnetFactory;
import fr.lip6.move.pnml.ptnet.ToolInfo;
import fr.lip6.move.pnml.ptnet.Transition;


/**
 * A class to parse a PT model from an PNML file.
 * @author Yann Thierry-Mieg 2015
 */
public class PTNetHandler extends DefaultHandler {
	private final Logger logger = Logger.getLogger("fr.lip6.move.gal"); //$NON-NLS-1$

	// context stack
	private Stack<Object> stack = new Stack<Object>();

	// object constructed
	private PetriNet net = PtnetFactory.eINSTANCE.createPetriNet();
//
	private Map<String, Node> index = new LinkedHashMap<String, Node>();

	private Name lastseen = null;
	private boolean readtext = false;

	private Integer lastint = null;
	private boolean readint = false;


	private NupnHandler nupnHandler;

	private boolean doNupn = false;

	private boolean doIt = false;


	public PTNetHandler(NupnHandler nupnHandler) {
		this.nupnHandler = nupnHandler;
	}


	@Override
	public void characters(char[] chars, int beg, int length) throws SAXException {
		if (doNupn) {
			nupnHandler.characters(chars, beg, length); 
		} else if (doIt) { 
			if (readtext) {
				String laststr = new String(Arrays.copyOfRange(chars, beg, beg+length));
				lastseen = PtnetFactory.eINSTANCE.createName();
				lastseen.setText(laststr);
			} else if (readint) {
				String laststr = new String(Arrays.copyOfRange(chars, beg, beg+length));
				lastint = new Integer(laststr);			
			} 
		} 
	}
	
	
	/** {@inheritDoc} */
	@Override
	public final void startElement(String uri, String localName, String baliseName, Attributes attributes) throws SAXException {
		if (doNupn) {
			nupnHandler.startElement(uri, localName, baliseName, attributes);
		} else if ("net".equals(baliseName)) { //$NON-NLS-1$
			net.setId(attributes.getValue("id"));
			String type = attributes.getValue("type");
			if (PNType.PTNET.getLiteral().equals(type)) {
				net.setType(PNType.PTNET);
			} else {
				throw new NotAPTException(type);
			}
			stack.push(net);
		} else if ("name".equals(baliseName)) {
			readtext = true;
			
		} else if ("page".equals(baliseName)) {
			PetriNet pn = (PetriNet) stack.peek();
			Page page = PtnetFactory.eINSTANCE.createPage();
			page.setId(attributes.getValue("id"));
			pn.getPages().add(page);
			stack.push(page);
		} else if ("place".equals(baliseName)) {
			Page page = (Page) stack.peek();
			Place place = PtnetFactory.eINSTANCE.createPlace();
			String id = attributes.getValue("id");
			place.setId(id);
			index.put(id, place);
			page.getObjects().add(place);
			stack.push(place);
		} else if ("initialMarking".equals(baliseName)) {
			readint = true;
		} else if ("inscription".equals(baliseName)) {
			readint = true;
			
		} else if ("transition".equals(baliseName)) {
			Page page = (Page) stack.peek();
			Transition tr = PtnetFactory.eINSTANCE.createTransition();
			String id = attributes.getValue("id");
			tr.setId(id);
			index.put(id, tr);
			page.getObjects().add(tr);
			stack.push(tr);
		} else if ("arc".equals(baliseName)) {
			Page page = (Page) stack.peek();
			Arc arc = PtnetFactory.eINSTANCE.createArc();
			String id = attributes.getValue("id");
			arc.setId(id);
			Node src = index.get(attributes.getValue("source"));
			Node target = index.get(attributes.getValue("target"));
			arc.setSource(src);
			arc.setTarget(target);
			assert( src != null && target != null);
			page.getObjects().add(arc);
			stack.push(arc);			
		} else if ("toolspecific".equals(baliseName)) {			
			Page page = (Page) stack.peek();			
			ToolInfo tool = PtnetFactory.eINSTANCE.createToolInfo();
			tool.setTool(attributes.getValue("tool"));
			tool.setVersion(attributes.getValue("version"));
			page.getToolspecifics().add(tool);
			stack.push(tool);
			if ("nupn".equals(attributes.getValue("tool"))) {
				doNupn = true;
			}
		} else if ("text".equals(baliseName)) {
			doIt  = true;
		} else if ("graphics".equals(baliseName) || "offset".equals(baliseName) || "position".equals(baliseName) || "fill".equals(baliseName) || "line".equals(baliseName) || "dimension".equals(baliseName)) {
			//skip
		} else {
			logger.warning("Unknown XML tag in source file: "+ baliseName); //$NON-NLS-1$
		}
	}


	/** {@inheritDoc} */
	@Override
	public final void endElement(String uri, String localName, String baliseName) throws SAXException {
		// Balise MODEL
		if ("toolspecific".equals(baliseName)) {
			ToolInfo tool =  (ToolInfo) stack.peek();
			if (doNupn)
				doNupn = false;
			stack.pop();
		} else if (doNupn) {
			nupnHandler.endElement(uri, localName, baliseName);
		} else if ("net".equals(baliseName)) { //$NON-NLS-1$
			stack.pop();
			assert(stack.isEmpty());
		} else if ("name".equals(baliseName)) { //$NON-NLS-1$
			Object context = stack.peek();
			if (context instanceof PetriNet) {
				PetriNet pnet = (PetriNet) context;
				pnet.setName(lastseen);				
			} else if (context instanceof PnObject) {
				PnObject pno = (PnObject) context;
				pno.setName(lastseen);
			} else {
				logger.warning("Unexpected name tag in source file: "+ baliseName + " context =" + context.getClass().getName()); //$NON-NLS-1$
			}
			readtext = false;
			lastseen = null;
		} else if ("page".equals(baliseName)) { //$NON-NLS-1$
			stack.pop();
		} else if ("place".equals(baliseName)) {
			stack.pop();
		} else if ("transition".equals(baliseName)) {
			stack.pop();
		} else if ("arc".equals(baliseName)) {
			stack.pop();
		} else if ("text".equals(baliseName)) {
			doIt = false;
		} else if ("initialMarking".equals(baliseName)) {
			Place p = (Place) stack.peek();
			PTMarking mark = PtnetFactory.eINSTANCE.createPTMarking();
			mark.setText(lastint);
			p.setInitialMarking(mark );
			readint = false;
			lastint = null;			
		} else if ("inscription".equals(baliseName)) {
			Arc p = (Arc) stack.peek();
			PTArcAnnotation arcval = PtnetFactory.eINSTANCE.createPTArcAnnotation();
			arcval.setText(lastint);
			p.setInscription(arcval );
			readint = false;
			lastint = null;			
		
			
//		} else if ("subunits".equals(baliseName)) { //$NON-NLS-1$
//			dosubs = false;
////		} else if ("graphics".equals(baliseName)) { //$NON-NLS-1$
////			// NOP
////		
		} else if ("graphics".equals(baliseName) || "offset".equals(baliseName) || "position".equals(baliseName) || "fill".equals(baliseName) || "line".equals(baliseName) || "dimension".equals(baliseName)) {
			//skip
		} else {
			logger.warning("Unknown XML tag in source file: "+ baliseName); //$NON-NLS-1$
		}
	}




	/**
	 * @return the order loaded from the XML file
	 */
	public PetriNet getParseResult() {
		return net;
	}
}
