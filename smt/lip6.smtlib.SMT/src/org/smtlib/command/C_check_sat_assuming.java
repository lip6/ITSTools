/*
 * This file is part of the SMT project.
 * Copyright 2010 David R. Cok
 * Created August 2010
 */
package org.smtlib.command;

import java.io.IOException;
import java.util.List;

import org.smtlib.ICommand.Icheck_sat_assuming;
import org.smtlib.IExpr;
import org.smtlib.IParser.ParserException;
import org.smtlib.IResponse;
import org.smtlib.ISolver;
import org.smtlib.IVisitor;
import org.smtlib.IVisitor.VisitorException;
import org.smtlib.impl.Command;
import org.smtlib.sexpr.Parser;
import org.smtlib.sexpr.Printer;

/** Implements the check-sat command */
public class C_check_sat_assuming extends Command implements Icheck_sat_assuming {
	
	private IExpr[] exprs;

	/** Creates a check_sat command (which has no arguments) */
	public C_check_sat_assuming(IExpr... exprs) {
		this.exprs = exprs;
	}
	
	/** Parses the arguments of the command, producing a new command instance */
	static public /*@Nullable*/ C_check_sat_assuming parse(Parser p) throws ParserException {
//		if (SMT.Configuration.isVersion(SMTLIB.V20)) {
//			p.error("The check-sat-assuming command is not valid in V2.0", p.peekToken().pos());
//			return null;
//		}
		System.err.println("NOT IMPLEMENTED");
		return p.checkNoArg() ? new C_check_sat_assuming() : null;
	}


	/** The command name */
	public static final String commandName = "check-sat-assuming";
	
	/** The command name */
	@Override
	public String commandName() { return commandName; }
	
	/** Writes the command in the syntax of the given printer 
	 * @throws VisitorException */
	public void write(Printer p) throws IOException, VisitorException {
		p.writer().append("(" + commandName + " (");
		for (IExpr e : exprs) {
			e.accept(p);
			p.writer().append(" ");
		}
		p.writer().append("))");
	}
	
	@Override
	public IResponse execute(ISolver solver) {
		return solver.check_sat_assuming(exprs);
	}

	@Override
	public <T> T accept(IVisitor<T> v) throws IVisitor.VisitorException {
		return v.visit(this);
	}

	@Override
	public List<IExpr> assumptions() {
		return assumptions();
	}
}
