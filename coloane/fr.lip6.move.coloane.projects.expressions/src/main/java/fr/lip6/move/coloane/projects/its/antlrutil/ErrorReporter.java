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
package fr.lip6.move.coloane.projects.its.antlrutil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ErrorReporter implements IErrorReporter, Iterable<String> {

	List<String> errors = new LinkedList<String>();
	
	public void reportError(String msg) {
		errors.add(msg);
	}

	public Iterator<String> iterator() {
		return errors.iterator();
	}

}
