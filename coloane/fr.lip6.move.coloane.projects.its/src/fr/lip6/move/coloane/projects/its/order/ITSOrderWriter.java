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
package fr.lip6.move.coloane.projects.its.order;

import fr.lip6.move.coloane.interfaces.exceptions.ExtensionException;
import fr.lip6.move.coloane.projects.its.ITypeDeclaration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

/**
 * A class to dump ITS variable orders, in native ITS syntax (one order per
 * type).
 * 
 * @author Yann
 * 
 */
public final class ITSOrderWriter {

	/**
	 * Writes the variable order corresponding to the provided type into a file.
	 * 
	 * @param orderFileName
	 *            file name to be built
	 * @param td
	 *            the type declaration this order is for
	 * @param order
	 *            the order to be output
	 * @throws ExtensionException
	 *             if any issues with I/O...
	 */
	public void writeOrder(String orderFileName, ITypeDeclaration td,
			Ordering order) throws ExtensionException {
		try {
			// test folder existence, create if it does not exist

			// File creation
			FileOutputStream writer = new FileOutputStream(new File(
					orderFileName)); //$NON-NLS-1$
			BufferedWriter sb = new BufferedWriter(new OutputStreamWriter(
					writer));

			Ordering order2 = order.iterator().next();
			// Start of file
			sb.append("#TYPE " + td.getTypeName() + "\n");
			for (Ordering o : order2) {
				sb.append(o.getName() + "\n");
			}
			sb.append("#END\n");

			// End of writing : clean & close
			sb.flush();
			writer.flush();
			sb.close();
			writer.close();
		} catch (FileNotFoundException fe) {
			Logger.getLogger("fr.lip6.move.coloane.core").warning(
					"Error when creating file : bad file name." + fe);
			throw new ExtensionException("Invalid filename !" + fe);
		} catch (IOException ioe) {
			Logger.getLogger("fr.lip6.move.coloane.core").warning(
					"Error writing in file " + ioe);
			throw new ExtensionException("Write error :" + ioe.getMessage());
		}

	}

}
