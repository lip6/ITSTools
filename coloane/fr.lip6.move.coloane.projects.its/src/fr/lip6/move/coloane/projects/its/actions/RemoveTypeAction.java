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
package fr.lip6.move.coloane.projects.its.actions;

import fr.lip6.move.coloane.projects.its.ITypeDeclaration;
import fr.lip6.move.coloane.projects.its.TypeList;

import org.eclipse.jface.action.Action;

/**
 * The action of removing a type.
 * @author Yann
 *
 */
public final class RemoveTypeAction extends Action {

	private TypeList types;
	private ITypeDeclaration td;

	/**
	 * Ctor
	 * @param types the owning type list
	 * @param td the type to remove
	 */
	public RemoveTypeAction(TypeList types, ITypeDeclaration td) {
		this.types = types;
		this.td = td;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		types.removeTypeDeclaration(td);
	}
}
