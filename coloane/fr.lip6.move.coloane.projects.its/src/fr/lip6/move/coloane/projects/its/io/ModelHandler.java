package fr.lip6.move.coloane.extension.importExportRomeo.importFromRomeo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Logger;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Color;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.lip6.move.coloane.core.model.factory.GraphModelFactory;
import fr.lip6.move.coloane.interfaces.exceptions.ModelException;
import fr.lip6.move.coloane.interfaces.formalism.IArcFormalism;
import fr.lip6.move.coloane.interfaces.formalism.IFormalism;
import fr.lip6.move.coloane.interfaces.formalism.INodeFormalism;
import fr.lip6.move.coloane.interfaces.model.IArc;
import fr.lip6.move.coloane.interfaces.model.IAttribute;
import fr.lip6.move.coloane.interfaces.model.IGraph;
import fr.lip6.move.coloane.interfaces.model.INode;

public class ModelHandler extends DefaultHandler {
    private final Logger logger = Logger.getLogger("fr.lip6.move.coloane.its");

    private Stack<Object> stack = new Stack<Object>();
    private Map<String, INode> placeIds = null;
    private Map<String, INode> transIds = null;
    private IGraph graph;
    private Map<String, List<INode>> transColors;
    private Map<String, List<INode>> placeColors;
    private boolean hasColors = false;
    private final IFormalism formalism;
    private final INodeFormalism placeFormalism;
    private final INodeFormalism transitionFormalism;
    private final IArcFormalism arcFormalism;
    private final IArcFormalism resetFormalism;
    private final IArcFormalism testFormalism;
    private final IArcFormalism inhibitorFormalism;
    private StringBuilder textBuffer = new StringBuilder(); // Added for potential text content

    public ModelHandler(IFormalism formalism) {
        this.formalism = formalism;
        this.placeFormalism = (INodeFormalism) formalism.getRootGraph().getElementFormalism("place");
        this.transitionFormalism = (INodeFormalism) formalism.getRootGraph().getElementFormalism("transition");
        this.arcFormalism = (IArcFormalism) formalism.getRootGraph().getElementFormalism("arc");
        this.resetFormalism = (IArcFormalism) formalism.getRootGraph().getElementFormalism("reset");
        this.testFormalism = (IArcFormalism) formalism.getRootGraph().getElementFormalism("test");
        this.inhibitorFormalism = (IArcFormalism) formalism.getRootGraph().getElementFormalism("inhibitor");
    }

    @Override
    public void characters(char[] chars, int beg, int length) throws SAXException {
        // Log unexpected character data, but don’t process it since the parser uses attributes
        String data = new String(chars, beg, length);
        textBuffer.append(chars, beg, length);
        logger.fine("Unexpected character data encountered: '" + data + "' (buffer now: '" + textBuffer.toString() + "')");
    }

    @Override
    public final void startElement(String uri, String localName, String baliseName, Attributes attributes) throws SAXException {
        // Clear textBuffer for tags where text might theoretically appear, though not currently used
        if ("place".equals(baliseName) || "transition".equals(baliseName) || "arc".equals(baliseName)) {
            textBuffer.setLength(0);
        }

        if ("TPN".equals(baliseName)) {
            graph = new GraphModelFactory().createGraph(formalism);
            placeIds = new HashMap<String, INode>();
            transIds = new HashMap<String, INode>();
            transColors = new HashMap<String, List<INode>>();
            placeColors = new HashMap<String, List<INode>>();
            hasColors = false;
            for (int i = 0; i < 6; i++) {
                String lab = "c" + i;
                transColors.put(lab, new ArrayList<INode>());
                placeColors.put(lab, new ArrayList<INode>());
            }
        } else if ("place".equals(baliseName)) {
            stack.push(handlePlace(attributes));
        } else if ("graphics".equals(baliseName)) {
            String colorLab = attributes.getValue("color");
            if (colorLab != null) {
                storeObjectColor(colorLab);                
            }
        } else if ("position".equals(baliseName)) {
            handleNodePosition((INode) stack.peek(), attributes);
        } else if ("deltaLabel".equals(baliseName)) {
            handleNodeLabelPosition((INode) stack.peek(), attributes);
        } else if ("scheduling".equals(baliseName)) {
            // TODO: Do something to the place?
        } else if ("transition".equals(baliseName)) {
            stack.push(handleTransition(attributes));
        } else if ("arc".equals(baliseName)) {
            stack.push(handleArc(attributes));
        } else if ("nail".equals(baliseName)) {
            handleArcNail((IArc) stack.peek(), attributes);
        } else if ("preferences".equals(baliseName)) {
            // NOP
        } else if ("colorPlace".equals(baliseName)) {
            hasColors = true;
            handlePlaceColors(attributes);
        } else if ("colorTransition".equals(baliseName)) {
            hasColors = true;
            handleTransitionColors(attributes);
        } else if ("colorArc".equals(baliseName)) {
            // Ignored
        } else {
            logger.warning("Unknown XML tag in source file: " + baliseName);
        }
    }

    @Override
    public final void endElement(String uri, String localName, String baliseName) throws SAXException {
        if ("TPN".equals(baliseName)) {
            if (!hasColors) {
                for (INode node : graph.getNodes()) 
                    if ("place".equals(node.getNodeFormalism().getName())) {
                        node.getGraphicInfo().setBackground(ColorConstants.lightBlue);                        
                    } else {
                        node.getGraphicInfo().setBackground(ColorConstants.yellow);                                                
                    }
            }
            placeIds = null;
            transIds = null;
            transColors = null;
            placeColors = null;
            hasColors = false;
        } else if ("place".equals(baliseName) || "transition".equals(baliseName) || "arc".equals(baliseName)) {
            stack.pop();
            textBuffer.setLength(0); // Clear after processing, though not currently used
        } else if ("graphics".equals(baliseName) || "position".equals(baliseName) || "deltaLabel".equals(baliseName) || 
                   "nail".equals(baliseName) || "preferences".equals(baliseName) || "colorPlace".equals(baliseName) || 
                   "colorTransition".equals(baliseName) || "colorArc".equals(baliseName)) {
            // NOP
        } else {
            logger.warning("Unknown XML tag in source file: " + baliseName);
        }
    }

    // Remaining methods (handlePlace, handleTransition, etc.) unchanged since they use attributes
    private INode handlePlace(Attributes attributes) {
        INode place = null;
        try {
            place = graph.createNode(placeFormalism);
            String label = attributes.getValue("label");
            if (label != null)
                place.getAttribute("name").setValue(label);
            String initialMarking = attributes.getValue("initialMarking");
            if (initialMarking != null)
                place.getAttribute("marking").setValue(initialMarking);
            placeIds.put(attributes.getValue("id"), place);
        } catch (ModelException e) {
            e.printStackTrace();
        }
        return place;
    }

    private INode handleTransition(Attributes attributes) {
        INode transition = null;
        try {
            transition = graph.createNode(transitionFormalism);
            String label = attributes.getValue("label");
            if (label != null)
                transition.getAttribute("label").setValue(label);
            String eft = attributes.getValue("eft");
            transition.getAttribute("earliestFiringTime").setValue(eft);
            String lft = attributes.getValue("lft");
            if (lft.equals("infini")) {
                transition.getAttribute("latestFiringTime").setValue("inf");
            } else {
                transition.getAttribute("latestFiringTime").setValue(lft);                
            }
            transIds.put(attributes.getValue("id"), transition);
        } catch (ModelException e) {
            e.printStackTrace();
        }
        return transition;
    }

    private IArc handleArc(Attributes attributes) {
        IArc arc = null;
        try {
            INode place = placeIds.get(attributes.getValue("place"));
            INode trans = transIds.get(attributes.getValue("transition"));
            String type = attributes.getValue("type");            
            if ("PlaceTransition".equals(type)) {
                arc = graph.createArc(arcFormalism, place, trans);
            } else if ("TransitionPlace".equals(type)) {
                arc = graph.createArc(arcFormalism, trans, place);
            } else if ("flush".equals(type)) {
                arc = graph.createArc(resetFormalism, place, trans);
            } else if ("read".equals(type)) {
                arc = graph.createArc(testFormalism, place, trans);
            } else if ("logicalInhibitor".equals(type)) {
                arc = graph.createArc(inhibitorFormalism, place, trans);
            }
            IAttribute val = arc.getAttribute("valuation");
            if (val != null) {
                String weight = attributes.getValue("weight");
                val.setValue(weight);
            }
        } catch (ModelException e) {
            e.printStackTrace();
        }
        return arc;
    }

    private void handleNodePosition(INode node, Attributes attributes) {
        float x = Float.parseFloat(attributes.getValue("x"));
        float y = Float.parseFloat(attributes.getValue("y"));
        node.getGraphicInfo().setLocation(new Point(x, y));
    }

    private void handleNodeLabelPosition(INode node, Attributes attributes) {
        Point nodePos = node.getGraphicInfo().getLocation();
        float x = Float.parseFloat(attributes.getValue("deltax"));
        float y = Float.parseFloat(attributes.getValue("deltay"));
        Point labPos = new Point(x, y);
        IAttribute label;
        if ("place".equals(node.getNodeFormalism().getName())) {
            label = node.getAttribute("name");
        } else {
            label = node.getAttribute("label");            
        }
        label.getGraphicInfo().setLocation(labPos.translate(nodePos));
    }

    private void handleArcNail(IArc arc, Attributes attributes) {
        float x = Float.parseFloat(attributes.getValue("xnail"));
        float y = Float.parseFloat(attributes.getValue("ynail"));
        if (x != 0 && y != 0) {
            Point nail = new Point(x, y);
            arc.addInflexPoint(nail);
        }
    }

    private void handlePlaceColors(Attributes attributes) {
        for (int i = 0; i < 6; i++) {
            String colorIndex = "c" + i;
            String color = attributes.getValue(colorIndex);
            Color bgColor = getColor(color);
            if (color == null) {
                break;
            }
            for (INode place : placeColors.get(colorIndex)) {                
                place.getGraphicInfo().setBackground(bgColor);
            }
        }
    }

    private void handleTransitionColors(Attributes attributes) {
        for (int i = 0; i < 6; i++) {
            String colorIndex = "c" + i;
            String color = attributes.getValue(colorIndex);
            Color bgColor = getColor(color);
            if (color == null) {
                break;
            }
            for (INode trans : transColors.get(colorIndex)) {                
                trans.getGraphicInfo().setBackground(bgColor);
            }
        }
    }

    private Color getColor(String color) {
        Color bgColor = null;
        if ("cyan".equals(color)) {
            bgColor = ColorConstants.cyan;
        } else if ("yellow".equals(color)) {
            bgColor = ColorConstants.yellow;
        } else if ("gray".equals(color)) {
            bgColor = ColorConstants.gray;
        } else if ("brown".equals(color)) {
            bgColor = ColorConstants.darkGreen;
        } else if ("SkyBlue2".equals(color)) {
            bgColor = ColorConstants.lightBlue;
        } else if ("green".equals(color)) {
            bgColor = ColorConstants.green;
        } 
        return bgColor;
    }

    public final IGraph getGraph() {
        return graph;
    }
}