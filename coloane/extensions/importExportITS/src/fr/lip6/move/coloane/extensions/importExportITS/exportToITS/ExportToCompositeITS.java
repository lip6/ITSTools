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
package fr.lip6.move.coloane.extensions.importExportITS.exportToITS;

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

/**
 * Export models to ITS XML format
 *
 * @author Yann
 */
public class ExportToCompositeITS implements IExportTo {

	/**
	 * Export a model to ITS XML formatted file
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
		monitor.beginTask("Export to ITS", totalWork);

		try {
			// File creation
			writer = new FileOutputStream(new File(filePath)); //$NON-NLS-1$
			BufferedWriter sb = new BufferedWriter(new OutputStreamWriter(writer));

			// Translation
			// write header
			sb.append("<?xml version='1.0' encoding='UTF-8'?>\n");
			// TODO : add a schema reference
			sb.append("<model>\n");
			// First export nodes: should be only instances and synchronizations
			for (INode node : model.getNodes()) {
				sb.append("<");
				sb.append(node.getNodeFormalism().getName());
				sb.append(" ");
				// Add the id
				sb.append("id='" + Integer.toString(node.getId()) + "' ");
				for (IAttribute a : node.getAttributes()) {
					sb.append(a.getName());
					sb.append("='");
					sb.append(a.getValue());
					sb.append("' ");
				}
				sb.append("/>\n");
			}

			// Now export the arcs
			for (IArc arc : model.getArcs()) {
				sb.append("<");
				sb.append(arc.getArcFormalism().getName());
				sb.append(" ");
				INode src = arc.getSource();
				INode dest = arc.getTarget();
				// check the arc is in the right direction
				if (!"instance".equals(src.getNodeFormalism().getName())) {
					// swap src and dest if not
					INode tmp = src;
					src = dest;
					dest = tmp;
				}
				// now print source and dest id
				sb.append(" instance='" + src.getId() + "' ");
				sb.append(" synchronization='" + dest.getId() + "' ");
				// add the labels (and any other attributes ??)
				for (IAttribute a : arc.getAttributes()) {
					sb.append(a.getName());
					sb.append("='");
					sb.append(a.getValue());
					sb.append("' ");
				}
				sb.append("/>\n");

			}
			// Now export the size if it exists (scalar and circular set)
			IAttribute sizeatt = model.getAttribute("size");
			if (sizeatt != null && sizeatt.getValue() != null) {
				sb.append("<size size='");
				sb.append(sizeatt.getValue());
				sb.append("' ");
				sb.append("/>\n");
			}

			sb.append("</model>\n");
			sb.newLine();
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

}

