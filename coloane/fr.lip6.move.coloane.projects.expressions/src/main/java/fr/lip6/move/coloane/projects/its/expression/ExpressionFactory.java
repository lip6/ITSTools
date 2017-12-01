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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import fr.lip6.move.coloane.projects.its.expression.parser.IntegerExpressionParserLexer;
import fr.lip6.move.coloane.projects.its.expression.parser.IntegerExpressionParserParser;
import fr.lip6.move.coloane.projects.its.antlrutil.ErrorReporter;

public class ExpressionFactory {

	
	
	public static ExpressionParseResult parseExpression (String expr) {
		if (expr != null && ! "".equals(expr)) {
			//				if (name.equals("marking")
			//						|| name.equals("earliestFiringTime")
			//						|| name.equals("latestFiringTime")
			//						|| name.equals("valuation")
			//						|| name.equals("size")) {
			IntegerExpressionParserLexer lexer;
			lexer = new IntegerExpressionParserLexer(new ANTLRStringStream(expr));
			ErrorReporter report = new ErrorReporter();
			lexer.setErrorReporter(report);
			CommonTokenStream tokens = new CommonTokenStream(lexer);

			IntegerExpressionParserParser parser = new IntegerExpressionParserParser(tokens);
			
			parser.setErrorReporter(report);
			try {
				IntegerExpression pexpr = 
					parser.prog();
				
				
				List<String> errors = new ArrayList<String>();
				for (String err: report) {
					errors.add(err);
				}
				return new ExpressionParseResult(pexpr, errors);
			} catch (RecognitionException e) {
				String msg = "Syntax error parsing integer expression for \""+expr+"\". Use $ to prefix variables, and arithmetic operations only.\n";
				msg += e.getLocalizedMessage();
				return new ExpressionParseResult(null, Collections.singletonList(msg));
			}
		}

		return new ExpressionParseResult(null, null);

	}


}
