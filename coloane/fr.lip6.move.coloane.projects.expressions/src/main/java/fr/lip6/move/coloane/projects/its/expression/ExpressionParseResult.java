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

package fr.lip6.move.coloane.projects.its.expression;

import java.util.Collections;
import java.util.List;

public class ExpressionParseResult {

	private final IntegerExpression expression;
	private final List<String> errors;
	public IntegerExpression getExpression() {
		return expression;
	}
	@SuppressWarnings("unchecked")
	public List<String> getErrors() {
		if (errors == null)
			return Collections.EMPTY_LIST;
		return errors;
	}
	public ExpressionParseResult(IntegerExpression expression,
			List<String> errors) {
		this.expression = expression;
		this.errors = errors;
	}
	public int getErrorCount() {
		if (errors == null)
			return 0;
		return errors.size();
	}
	
	
}
