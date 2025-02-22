package fr.lip6.move.gal.nupn;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
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
	private final Logger logger = Logger.getLogger("fr.lip6.move.gal");

	private Stack<Object> stack = new Stack<Object>();
	private PetriNet net = PtnetFactory.eINSTANCE.createPetriNet();
	private Map<String, Node> index = new LinkedHashMap<String, Node>();
	private Map<Arc, String> topatchs = new HashMap<Arc, String>();
	private Map<Arc, String> topatcht = new HashMap<Arc, String>();

	private Name lastseen = null;
	private boolean readtext = false;
	private Long lastint = null;
	private boolean readint = false;
	private NupnHandler nupnHandler;
	private boolean doNupn = false;
	private boolean inOpaqueToolSpecific = false;
	private boolean doIt = false;
	private StringBuilder textBuffer = new StringBuilder(); // New buffer for accumulation

	public PTNetHandler(NupnHandler nupnHandler) {
		this.nupnHandler = nupnHandler;
	}

	@Override
	public void characters(char[] chars, int beg, int length) throws SAXException {
		if (inOpaqueToolSpecific) {
			return;
		} else if (doNupn) {
			nupnHandler.characters(chars, beg, length);
		} else if (readtext || readint) { // Accumulate when expecting text or int
			textBuffer.append(chars, beg, length);
			// Optional: Add logging here if needed
		}
	}

	@Override
	public final void startElement(String uri, String localName, String baliseName, Attributes attributes)
			throws SAXException {
		if (doNupn) {
			nupnHandler.startElement(uri, localName, baliseName, attributes);
		} else {
			// Clear buffer at the start of elements we care about
			if ("name".equals(baliseName) || "initialMarking".equals(baliseName) || "inscription".equals(baliseName)) {
				textBuffer.setLength(0);
			}

			if ("net".equals(baliseName)) {
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
				if (src == null) {
					topatchs.put(arc, attributes.getValue("source"));
				}
				if (target == null) {
					topatcht.put(arc, attributes.getValue("target"));
				}
				page.getObjects().add(arc);
				stack.push(arc);
			} else if ("toolspecific".equals(baliseName)) {
				try {
					PnObject page = (PnObject) stack.peek();
					ToolInfo tool = PtnetFactory.eINSTANCE.createToolInfo();
					tool.setTool(attributes.getValue("tool"));
					tool.setVersion(attributes.getValue("version"));
					page.getToolspecifics().add(tool);
					stack.push(tool);
					if ("nupn".equals(attributes.getValue("tool"))) {
						doNupn = true;
					}
				} catch (ClassCastException e) {
					logger.warning("Skipping unknown tool specific annotation: " + attributes.getValue("tool"));
					inOpaqueToolSpecific = true;
				}
			} else if ("text".equals(baliseName)) {
				doIt = true;
			} else if ("graphics".equals(baliseName) || "offset".equals(baliseName) || "position".equals(baliseName)
					|| "fill".equals(baliseName) || "line".equals(baliseName) || "dimension".equals(baliseName)) {
				// Skip
			} else if ("pnml".equals(baliseName)) {
				// Skip
			} else {
				logger.warning("Unknown XML tag in source file: " + baliseName);
			}
		}
	}

	@Override
	public final void endElement(String uri, String localName, String baliseName) throws SAXException {
		if ("toolspecific".equals(baliseName)) {
			if (inOpaqueToolSpecific) {
				inOpaqueToolSpecific = false;
			} else {
				ToolInfo tool = (ToolInfo) stack.peek();
				if (doNupn)
					doNupn = false;
				stack.pop();
			}
		} else if (inOpaqueToolSpecific) {
			return;
		} else if (doNupn) {
			nupnHandler.endElement(uri, localName, baliseName);
		} else if ("net".equals(baliseName)) {
			stack.pop();
			assert (stack.isEmpty());
		} else if ("name".equals(baliseName)) {
			Object context = stack.peek();
			if (!textBuffer.toString().isEmpty()) { // Only set if we have accumulated text
				lastseen = PtnetFactory.eINSTANCE.createName();
				lastseen.setText(textBuffer.toString());
			}
			if (context instanceof PetriNet) {
				PetriNet pnet = (PetriNet) context;
				pnet.setName(lastseen);
			} else if (context instanceof PnObject) {
				PnObject pno = (PnObject) context;
				pno.setName(lastseen);
			} else {
				logger.warning("Unexpected name tag in source file: " + baliseName + " context ="
						+ context.getClass().getName());
			}
			readtext = false;
			lastseen = null;
			textBuffer.setLength(0);
		} else if ("page".equals(baliseName)) {
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
			if (!textBuffer.toString().isEmpty()) {
				try {
					lastint = Long.parseLong(textBuffer.toString().trim());
				} catch (NumberFormatException e) {
					logger.warning(
							"Failed to parse initialMarking from '" + textBuffer.toString() + "': " + e.getMessage());
					lastint = 0L; // Default on error
				}
			} else {
				lastint = 0L; // Default if no text
			}
			mark.setText(lastint);
			p.setInitialMarking(mark);
			readint = false;
			lastint = null;
			textBuffer.setLength(0);
		} else if ("inscription".equals(baliseName)) {
			Arc p = (Arc) stack.peek();
			PTArcAnnotation arcval = PtnetFactory.eINSTANCE.createPTArcAnnotation();
			if (!textBuffer.toString().isEmpty()) {
				try {
					lastint = Long.parseLong(textBuffer.toString().trim());
				} catch (NumberFormatException e) {
					logger.warning(
							"Failed to parse inscription from '" + textBuffer.toString() + "': " + e.getMessage());
					lastint = 1L; // Default on error (arc weight)
				}
			} else {
				lastint = 1L; // Default if no text
			}
			arcval.setText(lastint);
			p.setInscription(arcval);
			readint = false;
			lastint = null;
			textBuffer.setLength(0);
		} else if ("pnml".equals(baliseName)) {
			for (Entry<Arc, String> elt : topatcht.entrySet()) {
				Node target = index.get(elt.getValue());
				if (target == null) {
					throw new RuntimeException("Problem when linking arc " + elt.getValue());
				}
				elt.getKey().setTarget(target);
			}
			topatcht.clear();
			for (Entry<Arc, String> elt : topatchs.entrySet()) {
				Node source = index.get(elt.getValue());
				if (source == null) {
					throw new RuntimeException("Problem when linking arc " + elt.getValue());
				}
				elt.getKey().setSource(source);
			}
			topatchs.clear();
		} else if ("graphics".equals(baliseName) || "offset".equals(baliseName) || "position".equals(baliseName)
				|| "fill".equals(baliseName) || "line".equals(baliseName) || "dimension".equals(baliseName)) {
			// Skip
		} else {
			logger.warning("Unknown XML tag in source file: " + baliseName);
		}
	}

	public PetriNet getParseResult() {
		return net;
	}
}