package fr.lip6.move.gal.nupn;

import java.util.ArrayList;
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

public class NupnHandler extends DefaultHandler {
	private final Logger logger = Logger.getLogger("fr.lip6.move.gal");

	private Stack<Unit> stack = new Stack<Unit>();
	private IOrder order;
	private boolean isSafe = false;
	private Map<String, Unit> units = new HashMap<String, Unit>();
	private StringBuilder readString = new StringBuilder();
	private boolean doplaces = false;
	private boolean dosubs = false;

	@Override
	public void characters(char[] chars, int beg, int length) throws SAXException {
		if (doplaces || dosubs) {
			readString.append(chars, beg, length); // Append directly for efficiency
			// Optional: logger.fine("Accumulating: '" + new String(chars, beg, length) + "'
			// -> " + readString.toString());
		}
	}

	@Override
	public final void startElement(String uri, String localName, String baliseName, Attributes attributes)
			throws SAXException {
		if ("unit".equals(baliseName)) {
			stack.push(findUnit(attributes.getValue("id")));
		} else if ("places".equals(baliseName)) {
			doplaces = true;
			readString.setLength(0); // Clear buffer at start
			// Optional: logger.fine("Starting <places>, cleared readString");
		} else if ("subunits".equals(baliseName)) {
			dosubs = true;
			readString.setLength(0); // Clear buffer at start
			// Optional: logger.fine("Starting <subunits>, cleared readString");
		} else if ("size".equals(baliseName)) {
			// Skip
		} else if ("structure".equals(baliseName)) {
			if ("false".equals(attributes.getValue("safe"))) {
				isSafe = false;
			} else {
				isSafe = true;
			}
		} else {
			logger.warning("Unknown XML tag in source file: " + baliseName);
		}
	}

	private Unit findUnit(String id) {
		Unit u = units.get(id);
		if (u == null) {
			u = new Unit(id);
			units.put(id, u);
		}
		return u;
	}

	@Override
	public final void endElement(String uri, String localName, String baliseName) throws SAXException {
		if ("unit".equals(baliseName)) {
			stack.pop();
		} else if ("places".equals(baliseName) || "subunits".equals(baliseName)) {
			String[] pnames = readString.toString().replaceAll("\n", " ").replaceAll("\\s+", " ").split(" ");
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
			readString.setLength(0); // Clear buffer after processing
			doplaces = false;
			dosubs = false;
			// Optional: logger.fine("Processed " + baliseName + ": " +
			// Arrays.toString(pnames));
		} else if ("size".equals(baliseName)) {
			// NOP
		} else if ("structure".equals(baliseName)) {
			// NOP
		} else {
			logger.warning("Unknown XML tag in source file: " + baliseName);
		}
	}

	public IOrder getOrder() {
		if (units.isEmpty())
			return null;
		if (order == null) {
			for (Unit u : new ArrayList<Unit>(units.values())) {
				if (!u.getPlaces().isEmpty() && !u.getSubunits().isEmpty()) {
					String nuname;
					Unit nunit = null;
					for (int i = 1;; i++) {
						nuname = "u" + i;
						if (!units.containsKey(nuname)) {
							nunit = findUnit(nuname);
							break;
						}
					}
					nunit.getPlaces().addAll(u.getPlaces());
					u.getPlaces().clear();
					u.addUnit(nunit);
				}
			}

			Set<String> refd = new HashSet<String>();
			Map<String, IOrder> orders = new HashMap<String, IOrder>();
			for (Unit u : units.values()) {
				if (!u.getPlaces().isEmpty()) {
					orders.put(u.getId(), new VarOrder(u.getPlaces(), u.getId()));
				}
			}
			for (Unit u : units.values()) {
				if (!u.getSubunits().isEmpty()) {
					CompositeGalOrder ord = (CompositeGalOrder) orders.get(u.getId());
					if (ord == null) {
						ord = new CompositeGalOrder(new ArrayList<IOrder>(), u.getId());
						orders.put(u.getId(), ord);
					}
					for (Unit sub : u.getSubunits()) {
						refd.add(sub.getId());
						IOrder subord = orders.get(sub.getId());
						if (subord == null) {
							subord = new CompositeGalOrder(new ArrayList<IOrder>(), sub.getId());
							orders.put(sub.getId(), subord);
						}
						ord.getChildren().add(subord);
					}
				}
			}
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