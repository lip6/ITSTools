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
package fr.lip6.move.coloane.projects.its;

/**
 * An interface for a type list.
 * NOTE: not used consistently currently.
 * TODO: refactor to pull up more stuff from TypeList.
 * @author Yann
 *
 */
public interface ITypeList  {
	/**
	 * Add a type declaration to this model.
	 * @param t the type declaration
	 */
	void addTypeDeclaration(ITypeDeclaration t);
	/**
	 * Remove a type declaration from the model.
	 * @param t to remove
	 */
	void removeTypeDeclaration(ITypeDeclaration t);
	
}
