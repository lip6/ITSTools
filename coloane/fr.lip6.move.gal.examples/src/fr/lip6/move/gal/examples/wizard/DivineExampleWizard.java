/**
 * Copyright (c) 2006, 2008 IBM Corporation, Zeligsoft Inc., and others.
 * Copyright (c) 2006-2010 MoVe - Laboratoire d'Informatique de Paris 6 (LIP6).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *   Zeligsoft - Bug 233486
 * Contributors:
 *   Yann THIERRY-MIEG (LIP6)
 *
 * Official contacts:
 *   coloane@lip6.fr
 *   http://coloane.lip6.fr
 */

package fr.lip6.move.gal.examples.wizard;





public class DivineExampleWizard
	extends AbstractExampleWizard {
	
	@Override
	public String getProjectName() {
		return "fr.lip6.move.divine.example";
	}



	@Override
	public String getZipFile() {
		return "zips/divine.zip";
	}
}
