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
package fr.lip6.move.coloane.projects.its.checks;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import fr.lip6.move.coloane.projects.its.ui.forms.ITSEditorPlugin;
import fr.lip6.move.gal.itstools.BinaryToolsPlugin;
import fr.lip6.move.gal.itstools.BinaryToolsPlugin.Tool;


public class CheckService extends ITSCheckService {

	private static final String REACHABLE_PARAM = "Reachable ?";

	public CheckService(CheckList parent) {
		super(parent, "ITS Reachability");		
		getParameters().addParameter(REACHABLE_PARAM, "", "Test reachability of some condition.");
	}

	
	@Override
	protected List<String> buildCommandArguments() {
		List<String> cmd = super.buildCommandArguments();
		if (! getParameters().getParameterValue(REACHABLE_PARAM).isEmpty()) {
			cmd.add("-reachable");
			cmd.add(getParameters().getParameterValue(REACHABLE_PARAM));
		}
		return cmd;
	}

	@Override
	protected URI getToolPath() {
		
		try {
			return BinaryToolsPlugin.getProgramURI(Tool.reach);
		} catch (IOException e) {
			return ITSEditorPlugin.getDefault().getITSReachPath();
		}
	}

}
