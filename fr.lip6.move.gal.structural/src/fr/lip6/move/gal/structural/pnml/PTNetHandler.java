package fr.lip6.move.gal.structural.pnml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.lip6.move.gal.structural.SparsePetriNet;

/**
 * A class to parse a PT model from an PNML file.
 * 
 * @author Yann Thierry-Mieg
 */
public class PTNetHandler extends DefaultHandler {

	static final String PTNET = "http://www.pnml.org/version-2009/grammar/ptnet";

	private static final boolean IGNORE_NAMES = true;

	private final Logger logger = Logger.getLogger("fr.lip6.move.gal"); //$NON-NLS-1$

	// context stack
	private Stack<Object> stack = new Stack<Object>();

	// object constructed
	private SparsePetriNet net = new SparsePetriNet();

	private enum NodeType {
		PLACE, TRANSITION;
	}

	private static record Node(NodeType type, int index) {
	}

	private Map<String, Node> nodes = new LinkedHashMap<String, Node>();

	private static class Arc {
		String source;
		String target;
		int value;

		public Arc(String source, String target, int value) {
			this.source = source;
			this.target = target;
			this.value = value;
		}
	}

	private List<Arc> topatch = new ArrayList<>();

	private String lastseen = null;
	private boolean readtext = false;
	private Long lastint = null;
	private boolean readint = false;
	private StringBuilder textBuffer = new StringBuilder(); // Added for text accumulation

	// private NupnHandler nupnHandler;

	// private boolean doNupn = false;
	private boolean inOpaqueToolSpecific = false;

	private boolean doIt = false;

	@Override
	public void characters(char[] chars, int beg, int length) throws SAXException {
		if (inOpaqueToolSpecific) {
			return;
		} else if (doIt && (readtext || readint)) {
			textBuffer.append(chars, beg, length); // Accumulate text
		}
	}

	/** {@inheritDoc} */
	@Override
	public final void startElement(String uri, String localName, String baliseName, Attributes attributes)
			throws SAXException {
		if ("net".equals(baliseName)) { //$NON-NLS-1$
			net.setName(attributes.getValue("id"));
			String type = attributes.getValue("type");
			if (PTNET.equals(type)) {
				logger.info("Found a PT net.");
			} else {
				throw new IllegalArgumentException(
						"Parser only supports ptnet grammar. Type " + type + " is not supported.");
			}
			stack.push(net);
		} else if ("name".equals(baliseName)) {
			readtext = true;
			textBuffer.setLength(0); // Clear buffer
		} else if ("page".equals(baliseName)) {
			// ignore pages
		} else if ("place".equals(baliseName)) {
			String id = normalizeName(attributes.getValue("id"));
			int pindex = net.addPlace(id, 0);
			Node place = new Node(NodeType.PLACE, pindex);
			nodes.put(id, place);
			stack.push(place);
		} else if ("initialMarking".equals(baliseName)) {
			readint = true;
			textBuffer.setLength(0); // Clear buffer
		} else if ("inscription".equals(baliseName)) {
			readint = true;
			textBuffer.setLength(0); // Clear buffer
		} else if ("transition".equals(baliseName)) {
			String id = normalizeName(attributes.getValue("id"));
			int tindex = net.addTransition(id);
			Node trans = new Node(NodeType.TRANSITION, tindex);
			nodes.put(id, trans);
			stack.push(trans);
		} else if ("arc".equals(baliseName)) {
			Arc arc = new Arc(attributes.getValue("source"), attributes.getValue("target"), 1);
			topatch.add(arc);
			stack.push(arc);
		} else if ("toolspecific".equals(baliseName)) {
			logger.warning("Skipping unknown tool specific annotation : " + attributes.getValue("tool"));
			inOpaqueToolSpecific = true;
		} else if ("text".equals(baliseName)) {
			doIt = true;
		} else if ("graphics".equals(baliseName) || "offset".equals(baliseName) || "position".equals(baliseName)
				|| "fill".equals(baliseName) || "line".equals(baliseName) || "dimension".equals(baliseName)) {
			// skip
		} else if ("pnml".equals(baliseName)) {
			// skip
		} else {
			logger.warning("Unknown XML tag in source file: " + baliseName); //$NON-NLS-1$
		}
	}

	public static String normalizeName(String text) {
		String res = text.replace(' ', '_');
		res = res.replace('-', '_');
		res = res.replace('/', '_');
		res = res.replace('*', 'x');
		res = res.replace('=', '_');

		return res;
	}

	/** {@inheritDoc} */
	@Override
	public final void endElement(String uri, String localName, String baliseName) throws SAXException {
		// Balise MODEL
		if ("toolspecific".equals(baliseName)) {
			if (inOpaqueToolSpecific) {
				inOpaqueToolSpecific = false;
			}
		} else if (inOpaqueToolSpecific) {
			// skipping this stuff
			return;
		} else if ("net".equals(baliseName)) { //$NON-NLS-1$
			stack.pop();
			assert (stack.isEmpty());
		} else if ("name".equals(baliseName)) { //$NON-NLS-1$
			Object context = stack.peek();
			lastseen = textBuffer.toString().trim();
			if (context instanceof SparsePetriNet spn) {
				spn.setName(lastseen);
			} else if (context instanceof Node node) {
				if (IGNORE_NAMES) {
					if (node.type == NodeType.PLACE) {
						net.getPnames().set(node.index, normalizeName(lastseen));
					} else if (node.type == NodeType.TRANSITION) {
						net.getTnames().set(node.index, normalizeName(lastseen));
					}
				}
			} else {
				logger.warning("Unexpected name tag in source file: " + baliseName + " context =" //$NON-NLS-1$
						+ context.getClass().getName());
			}
			readtext = false;
			lastseen = null;
			textBuffer.setLength(0);
		} else if ("page".equals(baliseName)) { //$NON-NLS-1$
		} else if ("place".equals(baliseName)) {
			stack.pop();
		} else if ("transition".equals(baliseName)) {
			stack.pop();
		} else if ("arc".equals(baliseName)) {
			stack.pop();
		} else if ("text".equals(baliseName)) {
			doIt = false;
		} else if ("initialMarking".equals(baliseName)) {
			Node p = (Node) stack.peek();
			lastint = Long.parseLong(textBuffer.toString().trim());
			net.getMarks().set(p.index, Math.toIntExact(lastint));
			readint = false;
			lastint = null;
			textBuffer.setLength(0);
		} else if ("inscription".equals(baliseName)) {
			Arc p = (Arc) stack.peek();
			lastint = Long.parseLong(textBuffer.toString().trim());
			p.value = Math.toIntExact(lastint);
			readint = false;
			lastint = null;
			textBuffer.setLength(0);
		} else if ("graphics".equals(baliseName) || "offset".equals(baliseName) || "position".equals(baliseName)
				|| "fill".equals(baliseName) || "line".equals(baliseName) || "dimension".equals(baliseName)) {
			// skip
		} else if ("pnml".equals(baliseName)) {
			// add all arcs
			for (Arc arc : topatch) {
				Node src = nodes.get(arc.source);
				Node target = nodes.get(arc.target);
				int value = arc.value;
				if (src == null || target == null) {
					throw new RuntimeException("Problem when linking arc " + arc);
				}
				if (src.type == NodeType.PLACE) {
					net.addPreArc(src.index, target.index, value);
				} else if (src.type == NodeType.TRANSITION) {
					net.addPostArc(src.index, target.index, value);
				}
			}
		} else {
			logger.warning("Unknown XML tag in source file: " + baliseName); //$NON-NLS-1$
		}
	}

	/**
	 * @return the net loaded from the XML file
	 */
	public SparsePetriNet getParseResult() {
		return net;
	}
}