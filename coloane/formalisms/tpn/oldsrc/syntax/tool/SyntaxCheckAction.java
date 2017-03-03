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
package fr.lip6.move.coloane.projects.its.syntax.tool;

import fr.lip6.move.coloane.interfaces.model.IGraph;
import fr.lip6.move.coloane.interfaces.objects.result.IResult;
import fr.lip6.move.coloane.projects.its.syntax.ISyntaxChecker;
import fr.lip6.move.coloane.projects.its.syntax.tpn.IntegerExpressionCheck;
import fr.lip6.move.coloane.projects.its.syntax.tpn.TPNSyntaxChecker;

import java.util.ArrayList;
import java.util.List;

public class SyntaxCheckAction implements IColoaneAction {

	ISyntaxChecker tpncheck;
	
	public SyntaxCheckAction() {
		tpncheck = new TPNSyntaxChecker();
		tpncheck.addRule(new IntegerExpressionCheck());
	}
	
	public List<IResult> run(IGraph model) {
		List<IResult> res=new ArrayList<IResult>();
		res.add(tpncheck.check(model));
		return res;
	}

}
