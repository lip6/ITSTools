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
package fr.lip6.move.coloane.extensions.importExportTINA.exportToTINA;

import fr.lip6.move.coloane.extensions.importExportTINA.Activator;
import fr.lip6.move.coloane.interfaces.exceptions.ExtensionException;
import fr.lip6.move.coloane.interfaces.extensions.IExportTo;
import fr.lip6.move.coloane.interfaces.formalism.INodeFormalism;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IProgressMonitor;


/**
 * Export models to TINA format
 *
 * @author Yann Thierry-Mieg
 */
public class ExportToTINA implements IExportTo {
	
	/**
	 * Export a model to TINA formatted file
	 * @param model The model to export
	 * @param filePath The path of the destination file
	 * @param monitor A monitor to follow the export progression
	 * @throws ExtensionException Something wrong has happened.
	 */
	public final void export(IGraph model, String filePath, IProgressMonitor monitor) throws ExtensionException {
		FileOutputStream writer;

		// Filename checks
		if (filePath == null || "".equals(filePath)) { 
			throw new ExtensionException("The filename is not correct. Please provide a valid filename");
		}

		int totalWork = model.getNodes().size() + model.getArcs().size();
		monitor.beginTask("Export to TINA", totalWork);

		checkAndSetUnique(model);
		try {
			// File creation
			writer = new FileOutputStream(new File(filePath)); //$NON-NLS-1$
			BufferedWriter sb = new BufferedWriter(new OutputStreamWriter(writer));

			// header
			sb.append("# Tina .net format TPN built from Coloane model \"" + filePath + "\"\n");
			
			sb.append("net fromColoane\n");
			// nodes
			for (INode node : model.getNodes()) {
				if ("place".equals(node.getNodeFormalism().getName())) {
					exportPlace(node, sb);
				} else {
					exportTransition(node, sb);
				}
			}

			// trailer
			sb.append("\n");


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

	private void exportTransition(INode node, BufferedWriter sb) throws IOException {
		if ("public".equals(node.getAttribute("visibility").getValue())) {
			return;
		}
		sb.append("tr ");
		sb.append(transId(node));
		// label
		if ("public".equals(node.getAttribute("visibility").getValue())) {
			sb.append(": {" + node.getAttribute("label").getValue() + "} ");
		} 
		// eft/lft
		sb.append("[" + node.getAttribute("earliestFiringTime").getValue() + ",");
		String lft = node.getAttribute("latestFiringTime").getValue();
		try {
			sb.append(Integer.parseInt(lft) +"] ");
		} catch (NumberFormatException e) {
			sb.append("w[ ");
		}
		for (IArc arc : node.getIncomingArcs()) {
			exportArc(arc,sb);
		}
		sb.append(" -> ");
		for (IArc arc : node.getOutgoingArcs()) {
			exportArc(arc,sb);
		}
		sb.append("\n");
	}
	
	private void exportArc(IArc arc, BufferedWriter sb) throws IOException {
		if (arc.getSource().getNodeFormalism().getName().equals("place")) {
			sb.append(placeId(arc.getSource()));
		} else {
			sb.append(placeId(arc.getTarget()));					
		}
		String arcType = arc.getArcFormalism().getName(); 
		if ("arc".equals(arcType)) {
			sb.append("*");
		} else if ("inhibitor".equals(arcType)){
			sb.append("?-");						
		} else if ("reset".equals(arcType)){
			Logger.getLogger(Activator.PLUGIN_ID).warning("Warning : reset (flush) arcs are not supported by Tina. Reset arc exported as plain arc.");
			sb.append("*1");
			return;
		} else if ("test".equals(arcType)){
			sb.append("?");
		} else {
			throw new UnsupportedOperationException("unknown arc type!!");
		}
		sb.append(arc.getAttribute("valuation").getValue()+" ");
	}

	private String placeId (INode node) {
		return "{" + /* "P" + node.getId() + "_" + */ node.getAttribute("name").getValue()+"}" ;
	}

	private String transId (INode node) {
		return "{" + /*"T" + node.getId() + "_" + */ node.getAttribute("label").getValue()+"}" ;
	}

	
	private void exportPlace(INode node, BufferedWriter sb) throws IOException {
		sb.append("pl ");
		sb.append(placeId(node));
		String mark = node.getAttribute("marking").getValue();
		if (Integer.parseInt(mark) != 0) {
			sb.append(" ("+mark+") ");
		}
		sb.append("\n");
	}

	
	private void checkAndSetUnique(IGraph graph) {
		// for unnamed objects
		int nextId = 0;
		Map<INodeFormalism, Set<String>> idMap = new HashMap<INodeFormalism, Set<String>>();
		for (INode node : graph.getNodes()) {
			Set<String> formMap = idMap.get(node.getNodeFormalism());
			if (formMap == null) {
				formMap = new HashSet<String>();
				idMap.put(node.getNodeFormalism(), formMap);
			}
			IAttribute name = node.getAttribute("name");
			if (name==null) {
				name = node.getAttribute("label");
								
				if (name==null || "public".equals(node.getAttribute("visibility").getValue()))
					continue;
			}
			if (name.getValue()==null || name.getValue().isEmpty()) {
				name.setValue(node.getNodeFormalism().getName().substring(0,4)+nextId++);
			}
			if (formMap.contains(name.getValue())) {
				for (int i=0; i < graph.getNodes().size() ; i++) {
					String test = name.getValue()+i;
					if (! formMap.contains(test)) {
						name.setValue(test);
						break;
					}
				}
			}
			assert(!formMap.contains(name.getValue()));
			formMap.add(name.getValue());
		}
	}



}
