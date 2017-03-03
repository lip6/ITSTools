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


import fr.lip6.move.coloane.projects.its.actions.AddTypeAction;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.part.ResourceTransfer;

/**
 * A drag and drop "drop" adapter to allow drop of files onto tree view.
 * @author Yann
 *
 */
public final class TypesViewDropAdapter extends ViewerDropAdapter {

	private AddTypeAction action;

	/**
	 * Constructor.
	 * @param viewer the host viewer
	 * @param master the master block (to access types)
	 */
	public TypesViewDropAdapter(Viewer viewer, ScrolledPropertiesBlock master) {
		super(viewer);
		this.action = new AddTypeAction(master.getPage());
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validateDrop(Object target, int op, TransferData type) {
		boolean b = FileTransfer.getInstance().isSupportedType(type)
		|| LocalSelectionTransfer.getTransfer().isSupportedType(type)
		|| ResourceTransfer.getInstance().isSupportedType(type);
		return b;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean performDrop(Object data) {
		if (data instanceof String[]) {
			final String[] strings = (String[]) data;
			for (String string : strings) {
				getAddAction().setHint(string);
				getAddAction().run();
			}
			return true;
		} else if (data instanceof TreeSelection) {
			TreeSelection tsel = (TreeSelection) data;
			for (Object element : tsel.toArray()) {
				if (element instanceof IFile) {
					IFile file = (IFile) element;
					getAddAction().setHint(file.getLocation().toString());
					getAddAction().run();
				}
			}
		}
		return false;
	}
	/**
	 * singleton action.
	 * @return the action
	 */
	private AddTypeAction getAddAction() {
		return action;
	}


}
