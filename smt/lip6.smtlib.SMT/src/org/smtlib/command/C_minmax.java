/*
 * This file is part of the SMT project.
 * Copyright 2010 David R. Cok
 * Created August 2010
 */
package org.smtlib.command;

import java.io.IOException;

import org.smtlib.IExpr;
import org.smtlib.IParser.ParserException;
import org.smtlib.IResponse;
import org.smtlib.ISolver;
import org.smtlib.IVisitor;
import org.smtlib.impl.Command;
import org.smtlib.sexpr.Parser;
import org.smtlib.sexpr.Printer;

/** Implements the assert command */
public class C_minmax extends Command {
	
	private boolean isMax;

	/** Constructs a command object given the expression to assert */
	public C_minmax(IExpr expr, boolean isMax) {
		formula = expr;
		this.isMax = isMax; 
	}
	
	/** Returns the asserted formula */
	public IExpr expr() {
		return formula;
	}
	
	/** The command name */
	public static final String minCommandName = "minimize";
	public static final String maxCommandName = "maximize";
	
	/** The command name */
	public String commandName() { 
		if (isMax) return maxCommandName;
		else return minCommandName; 
	}

	/** The formula to assert */
	protected IExpr formula;

	/** Writes out the command in S-expression syntax using the given printer */
	public void write(Printer p) throws IOException, IVisitor.VisitorException {
		p.writer().append("(" + commandName() + " ");
		formula.accept(p);
		p.writer().append(")");
	}

	/** Parses the arguments of the command, producing a new command instance */
	static public /*@Nullable*/ C_minmax parse(Parser p) throws IOException, ParserException {
		IExpr expr = p.parseExpr();
		if (expr == null) return null;
		return new C_minmax(expr, false);
	}

	@Override
	public IResponse execute(ISolver solver) {
		if (isMax) {
			return solver.maximize(formula);
		} else {
			return solver.minimize(formula);
		}
	}
	
	@Override
	public <T> T accept(IVisitor<T> v) throws IVisitor.VisitorException {
		return v.visit(this);
	}


}
