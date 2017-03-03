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

import fr.lip6.move.coloane.projects.its.checks.CheckList;
import fr.lip6.move.coloane.projects.its.obs.ISimpleObserver;
import fr.lip6.move.coloane.projects.its.obs.SimpleObservable;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A model class to represent a type list.
 * Is iterable like a TypeDeclaration container
 * provides update notifications to registered observers.
 * @author Yann
 *
 */
public final class TypeList extends SimpleObservable implements ITypeList, Iterable<ITypeDeclaration>, ISimpleObserver {

	private List<ITypeDeclaration> table = new ArrayList<ITypeDeclaration>();
	private List<CheckList> checks = new ArrayList<CheckList>();
	private URI path;
	
	public TypeList (URI path) {
		this.path = path;
	}
	
	/**
	 * Add a type declaration to the set.
	 * @param t the type to add
	 */
	public void addTypeDeclaration(ITypeDeclaration t) {
		table.add(t);
		t.addObserver(this);
		notifyObservers();
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeTypeDeclaration(ITypeDeclaration t) {
		table.remove(t);
		for (ITypeDeclaration td : this) {
			td.unsetTypeDeclaration(t);
		}
		t.deleteObserver(this);
		notifyObservers();
	}


	/**
	 * iterable behavior of the type list
	 * @return free foreach statements
	 */
	public Iterator<ITypeDeclaration> iterator() {
		return table.iterator();
	}

	/**
	 * Used in table provider
	 * @return table.toArray().
	 */
	public Object[] toArray() {
		return table.toArray();
	}

	/**
	 * Clear all type declarations
	 */
	public void clear() {
		table.clear();
		notifyObservers();
	}

	/**
	 * @return number of type declarations contained
	 */
	public int size() {
		return table.size();
	}

	/**
	 * invoked when a nested object changes
	 */
	public void update() {
		notifyObservers();
	}

	/**
	 * Attempts to reload all models from their respective files, ensuring we
	 * are up to date w.r.t. modifications outside the program. <br>
	 * NB: any potential exceptions are caught, but the model may contain
	 * problems post reload.
	 */
	public void reload() {
		for (ITypeDeclaration td : this) {
			try {
				td.reload();
			} catch (IOException e) {
				removeTypeDeclaration(td);
			}
		}
		notifyObservers();
	}
	
	/**
	 * A type list may be associated to a check list.
	 * TODO: bad dependency orientation.
	 * @return the checks defined on this typelist
	 */
	public List<CheckList> getChecks() {
		return checks;
	}
	
	/**
	 * Create a new CheckList for this TypeList.
	 * @param cl the check to add
	 */
	public void addCheckList(CheckList cl) {
		checks.add(cl);
	}
	
	
	public URI getPath() {
		return path;
	}
}
