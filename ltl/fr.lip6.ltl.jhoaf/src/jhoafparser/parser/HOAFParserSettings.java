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

package jhoafparser.parser;

/**
 * Stores flags / settings for the HOAFParser.
 */
public class HOAFParserSettings
{
	/** Flag: do validation of the parsed automaton */
	private boolean validate = true;

	/** Flag: ignore acc-name header */
	private boolean ignoreAccName = false;
	
	/** Flag: reject unknown misc headers with semantics */
	private boolean rejectSemanticMiscHeaders = false;

	/** Default constructor. */
	public HOAFParserSettings()
	{
	}

	/** Get flag: do validation of the parsed automaton */
	public boolean getFlagValidate()
	{
		return validate;
	}

	/** Set flag: do validation of the parsed automaton? */
	public void setFlagValidate(boolean value)
	{
		this.validate = value;
	}


	/** Get flag: ignore the acc-name header */
	public boolean getFlagIgnoreAccName()
	{
		return ignoreAccName;
	}

	/** Set flag: ignore acc-name header? */
	public void setFlagIgnoreAccName(boolean value)
	{
		this.ignoreAccName = value;
	}

	/** Get flag: reject semantic misc headers? */
	public boolean getFlagRejectSemanticMiscHeaders()
	{
		return rejectSemanticMiscHeaders;
	}

	/** Set flag: reject semantic misc headers */
	public void setFlagRejectSemanticMiscHeaders(boolean value)
	{
		this.rejectSemanticMiscHeaders = value;
	}
}
