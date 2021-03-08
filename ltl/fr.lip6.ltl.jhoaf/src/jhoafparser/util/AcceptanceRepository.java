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

package jhoafparser.util;

import java.util.List;

import jhoafparser.ast.*;

/**
 * Interface to a repository of known acceptance names. This can be used to
 * construct the canonical acceptance expression for a given acc-name header, i.e., 
 * the acceptance name (with the extra parameters) for known acceptance names.
 */
public interface AcceptanceRepository
{
	/**
	 * For a given acc-name header, construct the corresponding canonical acceptance expression.
	 * If the acc-name is not known, return {@code null}.
	 * 
	 * @param accName the acceptance name, as passed in an acc-name header
	 * @param extraInfo extra info, as passed in an acc-name header
	 * @return the canonical acceptance expression for this name, {@code null} if not known
	 * @throws IllegalArgumentException if the acceptance name is known, but there is an error with the extraInfo
	 */
	public BooleanExpression<AtomAcceptance> getCanonicalAcceptanceExpression(String accName, List<Object> extraInfo) throws IllegalArgumentException;		

}
