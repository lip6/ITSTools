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
package fr.lip6.move.coloane.projects.its.ui.forms;

import java.net.URI;

import fr.lip6.move.coloane.core.main.Coloane;
import fr.lip6.move.coloane.projects.its.ITypeDeclaration;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * A utility class that opens the editor.
 * 
 * @author Yann
 * 
 */
public abstract class OpenEditorAction {

	/**
	 * open or focus the editor on the file proposed
	 * 
	 * @param td
	 *            current selected type
	 */
	public static void openEditor(ITypeDeclaration td) {
		if (td != null) {
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			try {
				if (td != null) {
					URI filePath = td.getTypeFile();
					IFile[] res = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(filePath); 
					if (res.length != 0) {
						IDE.openEditor(page, res[0] );
					} else {
						Coloane.showWarningMsg("Could not open referenced model file "+ filePath + ". It has to be in the workspace to allow editing.");
					}
				}
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
	}
}
