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
package fr.lip6.move.coloane.extension.importExportRomeo.exportToRomeo;

import fr.lip6.move.coloane.interfaces.exceptions.ExtensionException;
import fr.lip6.move.coloane.interfaces.extensions.IExportTo;
import fr.lip6.move.coloane.interfaces.model.IArc;
import fr.lip6.move.coloane.interfaces.model.IAttribute;
import fr.lip6.move.coloane.interfaces.model.IGraph;
import fr.lip6.move.coloane.interfaces.model.INode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Point;


/**
 * Export models to Romeo format
 *
 * @author Yann Thierry-Mieg
 */
public class ExportToRomeo implements IExportTo {
	
	// the indent level for pretty printing
	private int indent = 0;
	/**
	 * Export a model to Romeo formatted file
	 * @param model The model to export
	 * @param filePath The path of the destination file
	 * @param monitor A monitor to follow the export progression
	 * @throws ExtensionException Something wrong has happened.
	 */
	public final void export(IGraph model, String filePath, IProgressMonitor monitor) throws ExtensionException {
		FileOutputStream writer;

		// Filename checks
		if (filePath.equalsIgnoreCase("") || filePath == null) { //$NON-NLS-1$
			throw new ExtensionException("The filename is not correct. Please provide a valid filename");
		}

		int totalWork = model.getNodes().size() + model.getArcs().size();
		monitor.beginTask("Export to Romeo", totalWork);

		try {
			// File creation
			writer = new FileOutputStream(new File(filePath)); //$NON-NLS-1$
			BufferedWriter sb = new BufferedWriter(new OutputStreamWriter(writer));

			// header
			sb.append("<TPN name=\"" + filePath + "\">\n");
			indent++;
			// nodes
			for (INode node : model.getNodes()) {
				if ("place".equals(node.getNodeFormalism().getName())) {
					exportPlace(node, sb);
				} else {
					exportTransition(node, sb);
				}
			}
			// arcs
			for (IArc arc : model.getArcs()) {
				String arcType = arc.getArcFormalism().getName(); 
				if ("arc".equals(arcType)) {
					if (arc.getSource().getNodeFormalism().getName().equals("place")) {
						exportArc(arc, "PlaceTransition", sb);
					} else {
						exportArc(arc, "TransitionPlace", sb);						
					}
				} else if ("inhibitor".equals(arcType)){
					exportArc(arc, "logicalInhibitor", sb);											
				} else if ("reset".equals(arcType)){
					exportArc(arc, "flush", sb);
				} else if ("test".equals(arcType)){
					exportArc(arc, "read", sb);
				} else {
					throw new UnsupportedOperationException("unknown arc type!!");
				}
			}

			// trailer
			sb.append("</TPN>\n");


			// End of writing : clean & close
			sb.flush();
			writer.flush();
			sb.close();
			writer.close();
		} catch (FileNotFoundException fe) {
			Logger.getLogger("fr.lip6.move.coloane.core").warning("Echec lors de la création du fichier : Nom de fichier invalide");
			throw new ExtensionException("Invalid filename !");
		} catch (IOException ioe) {
			Logger.getLogger("fr.lip6.move.coloane.core").warning("Erreur lors de l'écriture dans le fichier");
			throw new ExtensionException("Write error :" + ioe.getMessage());
		}
		monitor.done();
	}

	private void exportArc(IArc arc, String type, BufferedWriter sb) throws IOException {
		INode place, trans;
		if ("place".equals(arc.getSource().getNodeFormalism().getName())) {
			place = arc.getSource();
			trans = arc.getTarget();
		} else {
			place = arc.getTarget();
			trans = arc.getSource();
		}
		indent(sb);
		sb.append("<arc ");
		sb.append("place=\"" + place.getId() + "\" ");
		sb.append("transition=\"" + trans.getId() + "\" ");
		sb.append("type=\""+ type+ "\" ");
		IAttribute val = arc.getAttribute("valuation");
		if (val != null) {
			sb.append("weight=\""+ val.getValue()+ "\" ");
		} else {
			sb.append("weight=\"1\" ");			
		}
		sb.append(">\n");
		//inflex points
		indent++;
		indent(sb);
		if (!arc.getInflexPoints().isEmpty()) {
			// Romeo only supports a single nail per arc.
			Point loc = arc.getGraphicInfo().findMiddlePoint();
			sb.append("<nail xnail=\"" + loc.x + "\" ynail=\"" + loc.y + "\"/>\n");
		} else {
			// no inflex point
			sb.append("<nail xnail=\"0\" ynail=\"0\"/>\n");			
		}
		indent--;
		indent(sb);
		// close arc
		sb.append("</arc>\n");
		
	}

	private void exportTransition(INode node, BufferedWriter sb) throws IOException {
		indent(sb);
		sb.append("<transition ");
		sb.append("id=\"" + node.getId() + "\" ");
		sb.append("label=\"" + node.getAttribute("label").getValue() + "\"");
		if ("public".equals(node.getAttribute("visibility").getValue())) {
			sb.append(" public=\"1\" ");
		} else {
			sb.append(" public=\"0\" ");
		}
		sb.append("eft=\"" + node.getAttribute("earliestFiringTime").getValue() + "\" ");
		String lft = node.getAttribute("latestFiringTime").getValue();
		try {
			sb.append("lft=\"" + Integer.parseInt(lft) + "\" ");
		} catch (NumberFormatException e) {
			sb.append("lft=\"infini\" ");
		}
		sb.append(">\n");

		indent++;
		exportNodeGraphics(node, sb);

		indent--;
		indent(sb);
		sb.append("</transition>\n");
	}

	private void exportPlace(INode node, BufferedWriter sb) throws IOException {
		indent(sb);
		sb.append("<place ");
		sb.append("id=\"" + node.getId() + "\" ");
		sb.append("label=\"" + node.getAttribute("name").getValue() + "\" ");
		sb.append("initialMarking=\"" + node.getAttribute("marking").getValue() + "\" ");
		sb.append(">\n");
		indent++;
		exportNodeGraphics(node, sb);

		indent(sb);
		// not really sure what this reflects ?
		sb.append("<scheduling gamma=\"0\" omega=\"0\"/>\n");

		indent--;
		indent(sb);
		sb.append("</place>\n");
	}

	/** 
	 * Export the graphical position of a node and its name tag.
	 * @param node the node (place or transition)
	 * @param sb the output
	 * @throws IOException if write problems
	 */
	private void exportNodeGraphics(INode node, BufferedWriter sb) throws IOException {
		indent(sb);
		sb.append("<graphics>\n");
		indent++;
		// node position
		Point loc = node.getGraphicInfo().getLocation();
		indent(sb);
		sb.append("<position x=\"" + loc.x + "\" y=\"" + loc.y + "\"/>\n");

		// label position (delta)
		IAttribute lab;
		if (node.getNodeFormalism().getName().equals("transition")) {
			lab = node.getAttribute("label");
		} else {
			// place 
			lab = node.getAttribute("name");			
		}
		Point loctag = lab.getGraphicInfo().getLocation();
		indent(sb);
		sb.append("<deltaLabel deltax=\"" + (loctag.x - loc.x) + "\" deltay=\"" + (loctag.y - loc.y) + "\"/>\n");
		indent--;

		indent(sb);
		sb.append("</graphics>\n");
	}
	/**
	 * Adds 2 whitespace per indent level.
	 * @param sb to add to
	 * @throws IOException if write problems
	 */
	private void indent(BufferedWriter sb) throws IOException {
		for (int i = 0; i < indent; i++) {
			sb.append("  ");
		}
	}


}
