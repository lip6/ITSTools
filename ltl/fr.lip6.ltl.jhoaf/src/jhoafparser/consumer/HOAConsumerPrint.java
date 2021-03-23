//==============================================================================
//	
//	Copyright (c) 2014-
//	Authors:
//	* Joachim Klein <klein@tcs.inf.tu-dresden.de>
//	* David Mueller <david.mueller@tcs.inf.tu-dresden.de>
//	
//------------------------------------------------------------------------------
//	
//	This file is part of the jhoafparser library, http://automata.tools/hoa/jhoafparser/
//
//	The jhoafparser library is free software; you can redistribute it and/or
//	modify it under the terms of the GNU Lesser General Public
//	License as published by the Free Software Foundation; either
//	version 2.1 of the License, or (at your option) any later version.
//	
//	The jhoafparser library is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//	Lesser General Public License for more details.
//	
//	You should have received a copy of the GNU Lesser General Public
//	License along with this library; if not, write to the Free Software
//	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
//	
//==============================================================================

package jhoafparser.consumer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import jhoafparser.ast.*;

/**
 * This {@code HOAConsumer} renders the method calls
 * to produce a valid HOA automaton output. 
 */
public class HOAConsumerPrint implements HOAConsumer {
	/** The output writer */
	protected Writer out;
	
	/** 
	 * Constructor
	 * @param out the {@code OutputStream}
	 */
	public HOAConsumerPrint(OutputStream out) {
		this.out = new BufferedWriter(new OutputStreamWriter(out));
	}
	
	/**
	 * Return a factory for building HOAConsumerPrint with the given
	 * output stream.
	 **/
	public static HOAConsumerFactory getFactory(final OutputStream stream)
	{
		return new HOAConsumerFactory() {
			@Override
			public HOAConsumer getNewHOAConsumer()
			{
				return new HOAConsumerPrint(stream);
			}
		};
	}

	@Override
	public boolean parserResolvesAliases() {
		return false;
	}

	@Override
	public void notifyHeaderStart(String version) {
		try {
			out.write("HOA: "+version+"\n");
		} catch (IOException e) {}
	}

	@Override
	public void setNumberOfStates(int numberOfStates) {
		try {
			out.write("States: "+numberOfStates+"\n");
		} catch (IOException e) {}
	}

	@Override
	public void addStartStates(List<Integer> stateConjunction) {
		try {
			out.write("Start: ");
			boolean first = true;
			for (Integer state : stateConjunction) {
				if (!first) out.write(" & ");
				first = false;
				out.write(state.toString());
			}
			out.write("\n");
		} catch (IOException e) {}
	}

	@Override
	public void addAlias(String name, BooleanExpression<AtomLabel> labelExpr) {
		try {
			out.write("Alias: @"+name+" "+labelExpr.toString()+"\n");
		} catch (IOException e) {}
	}

	@Override
	public void setAPs(List<String> aps) {
		try {
			out.write("AP: ");
			out.write(Integer.toString(aps.size()));
			for (String ap : aps) {
				out.write(" ");
				out.write(quoteString(ap));
			}
			out.write("\n");
		} catch (IOException e) {}
	}

	@Override
	public void setAcceptanceCondition(int numberOfSets,
			BooleanExpression<AtomAcceptance> accExpr) {
		try {
			out.write("Acceptance: ");
			out.write(Integer.toString(numberOfSets));
			out.write(" ");
			out.write(accExpr.toString());
			out.write("\n");
		} catch (IOException e) {}
	}

	@Override
	public void provideAcceptanceName(String name, List<Object> extraInfo) {
		try {
			out.write("acc-name: ");
			out.write(name);
			for (Object info : extraInfo) {
				out.write(" ");
				out.write(info.toString());
			}
			out.write("\n");
		} catch (IOException e) {}
	}

	@Override
	public void setName(String name) {
		try {
			out.write("name: ");
			out.write(quoteString(name));
			out.write("\n");
		} catch (IOException e) {}
	}

	@Override
	public void setTool(String name, String version) {
		try {
			out.write("tool: ");
			out.write(quoteString(name));
			if (version != null) {
			    out.write(" "+quoteString(version));
			}
			out.write("\n");
		} catch (IOException e) {}
	}

	@Override
	public void addProperties(List<String> properties) {
		try {
			out.write("properties: ");
			for (String property : properties) {
				out.write(property);
				out.write(" ");
			}
			out.write("\n");
		} catch (IOException e) {}
	}

	@Override
	public void addMiscHeader(String name, List<Object> content) {
		try {
			out.write(name+": ");
			for (Object c : content) {
				out.write(c.toString());
				out.write(" ");
			}
			out.write("\n");
		} catch (IOException e) {}
	}

	@Override
	public void notifyBodyStart() {
		try {
			out.write("--BODY--\n");
		} catch (IOException e) {}
	}

	@Override
	public void addState(int id, String info, BooleanExpression<AtomLabel> labelExpr,
			List<Integer> accSignature) {
		try {
			out.write("State: ");
			if (labelExpr != null) {
				out.write("["+labelExpr.toString()+"] ");
			}
			out.write(Integer.toString(id));
			if (info != null) {
				out.write(" ");
				out.write(quoteString(info));
			}
			if (accSignature != null) {	
				out.write(" {");
				boolean first = true;
				for (Integer acc : accSignature) {
					if (!first) out.write(" ");
					first = false;
					out.write(acc.toString());
				}
				out.write("}");
			}
			out.write("\n");
		} catch (IOException e) {}
	}

	@Override
	public void addEdgeImplicit(int stateId, List<Integer> conjSuccessors,
			List<Integer> accSignature) {
		try {
			boolean first = true;
			for (Integer succ : conjSuccessors) {
				if (!first) out.write("&");
				first = false;
				out.write(succ.toString());
			}
			if (accSignature != null) {
				out.write(" {");
				first = true;
				for (Integer acc : accSignature) {
					if (!first) out.write(" ");
					first = false;
					out.write(acc.toString());
				}
				out.write("}");
			}
			out.write("\n");
		} catch (IOException e) {}
	}

	@Override
	public void addEdgeWithLabel(int stateId, BooleanExpression<AtomLabel> labelExpr,
			List<Integer> conjSuccessors, List<Integer> accSignature) {
		try {
			if (labelExpr != null) {
				out.write("[");
				out.write(labelExpr.toString());
				out.write("] ");
			}
			
			boolean first = true;
			for (Integer succ : conjSuccessors) {
				if (!first) out.write("&");
				first = false;
				out.write(succ.toString());
			}
			
			if (accSignature != null) {
				out.write(" {");
				first = true;
				for (Integer acc : accSignature) {
					if (!first) out.write(" ");
					first = false;
					out.write(acc.toString());
				}
				out.write("}");
			}
			out.write("\n");
		} catch (IOException e) {}
	}

	@Override
	public void notifyEndOfState(int stateId) throws HOAConsumerException
	{
	}

	@Override
	public void notifyEnd() {
		try {
			out.write("--END--\n");
			out.flush();
		} catch (IOException e) {}
	}

	@Override
	public void notifyAbort() {
		try {
			out.write("--ABORT--\n");
			out.flush();
		} catch (IOException e) {}
	}

	/** Returns the argument, quoted according to HOA quoting rules.*/
	protected String quoteString(String s) {
		String result = s.replaceAll("\\\\", "\\\\");
		result = result.replaceAll("\"", "\\\"");
		return "\"" + s + "\"";
	}

	@Override
	public void notifyWarning(String warning) throws HOAConsumerException
	{
		System.err.println("Warning: "+warning);
	}

}
