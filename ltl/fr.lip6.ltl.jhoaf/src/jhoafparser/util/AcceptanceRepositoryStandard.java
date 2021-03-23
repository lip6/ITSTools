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

import java.util.ArrayList;
import java.util.List;

import jhoafparser.ast.*;


/**
 * An acceptance repository with all the acceptance conditions specified
 * in the format specification.
 */
public class AcceptanceRepositoryStandard implements AcceptanceRepository
{
	// identifiers for the various standard acceptance conditions
	private static final String ACC_ALL = "all";
	private static final String ACC_NONE = "none";

	private static final String ACC_BUCHI = "Buchi";
	private static final String ACC_COBUCHI = "coBuchi";

	private static final String ACC_GENERALIZED_BUCHI = "generalized-Buchi";
	private static final String ACC_GENERALIZED_COBUCHI = "generalized-coBuchi";

	private static final String ACC_RABIN = "Rabin";
	private static final String ACC_STREETT = "Streett";

	private static final String ACC_GENERALIZED_RABIN = "generalized-Rabin";

	private static final String ACC_PARITY = "parity";

	@Override
	public BooleanExpression<AtomAcceptance> getCanonicalAcceptanceExpression(String accName, List<Object> extraInfo) throws IllegalArgumentException {
		switch (accName) {
		case ACC_ALL:
			return forAll(extraInfo);
		case ACC_NONE:
			return forNone(extraInfo);

		case ACC_BUCHI:
			return forBuchi(extraInfo);
		case ACC_COBUCHI:
			return forCoBuchi(extraInfo);

		case ACC_GENERALIZED_BUCHI:
			return forGenBuchi(extraInfo);
		case ACC_GENERALIZED_COBUCHI:
			return forGenCoBuchi(extraInfo);

		case ACC_RABIN:
			return forRabin(extraInfo);
		case ACC_STREETT:
			return forStreett(extraInfo);
		case ACC_GENERALIZED_RABIN:
			return forGeneralizedRabin(extraInfo);
		case ACC_PARITY:
			return forParity(extraInfo);
		}
		return null;
	}

	/** Get canonical for 'all' */
	public static BooleanExpression<AtomAcceptance> forAll(List<Object> extraInfo) {
		checkNumberOfArguments(ACC_ALL, extraInfo, 0);

		return new BooleanExpression<AtomAcceptance>(true);
	}

	/** Get canonical for 'none' */
	public static BooleanExpression<AtomAcceptance> forNone(List<Object> extraInfo) {
		checkNumberOfArguments(ACC_NONE, extraInfo, 0);

		return new BooleanExpression<AtomAcceptance>(false);
	}

	/** Get canonical for 'Buchi' */
	public static BooleanExpression<AtomAcceptance> forBuchi(List<Object> extraInfo) {
		checkNumberOfArguments(ACC_BUCHI, extraInfo, 0);

		return new BooleanExpression<AtomAcceptance>(AtomAcceptance.Inf(0));
	}

	/** Get canonical for 'coBuchi' */
	public static BooleanExpression<AtomAcceptance> forCoBuchi(List<Object> extraInfo) {
		checkNumberOfArguments(ACC_COBUCHI, extraInfo, 0);

		return new BooleanExpression<AtomAcceptance>(AtomAcceptance.Fin(0));
	}

	/** Get canonical for 'generalized-Buchi' */
	public static BooleanExpression<AtomAcceptance> forGenBuchi(List<Object> extraInfo) {
		checkNumberOfArguments(ACC_GENERALIZED_BUCHI, extraInfo, 1);
		int numberOfInf = extraInfoToIntegerList(ACC_GENERALIZED_BUCHI, extraInfo).get(0);

		if (numberOfInf == 0) {
			return new BooleanExpression<AtomAcceptance>(true);
		}

		BooleanExpression<AtomAcceptance> result = null;
		for (int i = 0; i < numberOfInf; i++) {
			BooleanExpression<AtomAcceptance> inf = new BooleanExpression<AtomAcceptance>(AtomAcceptance.Inf(i));

			if (i == 0) {
				result = inf;
			} else {
				result = result.and(inf);
			}
		}

		return result;
	}

	/** Get canonical for 'generalized-coBuchi' */
	public static BooleanExpression<AtomAcceptance> forGenCoBuchi(List<Object> extraInfo) {
		checkNumberOfArguments(ACC_GENERALIZED_COBUCHI, extraInfo, 1);
		int numberOfFin = extraInfoToIntegerList(ACC_GENERALIZED_COBUCHI, extraInfo).get(0);

		if (numberOfFin == 0) {
			return new BooleanExpression<AtomAcceptance>(false);
		}

		BooleanExpression<AtomAcceptance> result = null;
		for (int i = 0; i < numberOfFin; i++) {
			BooleanExpression<AtomAcceptance> fin = new BooleanExpression<AtomAcceptance>(AtomAcceptance.Fin(i));
			
			if (i == 0) {
				result = fin;
			} else {
				result = result.and(fin);
			}
		}

		return result;
	}

	/** Get canonical for 'Rabin' */
	public static BooleanExpression<AtomAcceptance> forRabin(List<Object> extraInfo) {
		checkNumberOfArguments(ACC_RABIN, extraInfo, 1);
		int numberOfPairs = extraInfoToIntegerList(ACC_RABIN, extraInfo).get(0);

		if (numberOfPairs == 0) {
			return new BooleanExpression<AtomAcceptance>(false);
		}

		BooleanExpression<AtomAcceptance> result = null;
		for (int i = 0; i < numberOfPairs; i++) {
			BooleanExpression<AtomAcceptance> fin = new BooleanExpression<AtomAcceptance>(AtomAcceptance.Fin(2*i));
			BooleanExpression<AtomAcceptance> inf = new BooleanExpression<AtomAcceptance>(AtomAcceptance.Inf(2*i+1));

			BooleanExpression<AtomAcceptance> pair = fin.and(inf);

			if (i == 0) {
				result = pair;
			} else {
				result = result.or(pair);
			}
		}

		return result;
	}

	/** Get canonical for 'Streett' */
	public static BooleanExpression<AtomAcceptance> forStreett(List<Object> extraInfo) {
		checkNumberOfArguments(ACC_STREETT, extraInfo, 1);
		int numberOfPairs = extraInfoToIntegerList(ACC_STREETT, extraInfo).get(0);

		if (numberOfPairs == 0) {
			return new BooleanExpression<AtomAcceptance>(true);
		}

		BooleanExpression<AtomAcceptance> result = null;
		for (int i = 0; i < numberOfPairs; i++) {
			BooleanExpression<AtomAcceptance> fin = new BooleanExpression<AtomAcceptance>(AtomAcceptance.Fin(2*i));
			BooleanExpression<AtomAcceptance> inf = new BooleanExpression<AtomAcceptance>(AtomAcceptance.Inf(2*i+1));

			BooleanExpression<AtomAcceptance> pair = fin.or(inf);

			if (i == 0) {
				result = pair;
			} else {
				result = result.and(pair);
			}
		}

		return result;
	}

	/** Get canonical for 'generalized-Rabin' */
	public static BooleanExpression<AtomAcceptance> forGeneralizedRabin(List<Object> extraInfo) {
		List<Integer> parameters = extraInfoToIntegerList(ACC_GENERALIZED_RABIN, extraInfo);

		if (parameters.size() == 0) {
			throw new IllegalArgumentException("Acceptance "+ACC_GENERALIZED_RABIN+" needs at least one argument");
		}

		int numberOfPairs = parameters.get(0);
		if (parameters.size() != numberOfPairs + 1) {
			throw new IllegalArgumentException("Acceptance "+ACC_GENERALIZED_RABIN+" with " + numberOfPairs +" generalized pairs: There is not exactly one argument per pair");
		}

		BooleanExpression<AtomAcceptance> result = null;
		int currentIndex = 0;
		for (int i = 0; i < numberOfPairs; i++) {
			int numberOfInf = parameters.get(i+1);

			BooleanExpression<AtomAcceptance> fin = new BooleanExpression<AtomAcceptance>(AtomAcceptance.Fin(currentIndex++));
			BooleanExpression<AtomAcceptance> pair = fin;
			for (int j = 0; j< numberOfInf; j++) {
				BooleanExpression<AtomAcceptance> inf = new BooleanExpression<AtomAcceptance>(AtomAcceptance.Inf(currentIndex++));
				pair = pair.and(inf);
			}

			if (i == 0) {
				result = pair;
			} else {
				result = result.or(pair);
			}
		}

		return result;
	}

	/** Get canonical for 'parity' */
	public static BooleanExpression<AtomAcceptance> forParity(List<Object> extraInfo) {
		checkNumberOfArguments(ACC_PARITY, extraInfo, 3);
		
		boolean min = false;
		boolean even = false;
		
		String minMax = extraInfoToString(ACC_PARITY, extraInfo, 0);
		switch (minMax) {
		case "min": min = true; break;
		case "max": min = false; break;
		default: throw new IllegalArgumentException("For acceptance " + ACC_PARITY +", the first argument has to be either 'min' or 'max'");
		}
		String evenOdd = extraInfoToString(ACC_PARITY, extraInfo, 1);
		switch (evenOdd) {
		case "even": even = true; break;
		case "odd":  even = false; break;
		default: throw new IllegalArgumentException("For acceptance " + ACC_PARITY +", the second argument has to be either 'even' or 'odd'");
		}

		int colors;
		if (!(extraInfo.get(2) instanceof Integer)) {
			throw new IllegalArgumentException("For acceptance " + ACC_PARITY + ", the third argument has to be the number of colors");
		}
		colors = (Integer)extraInfo.get(2);
		if (colors < 0) {
			throw new IllegalArgumentException("For acceptance " + ACC_PARITY + ", the third argument has to be the number of colors (non-negative)");
		}

		if (colors == 0) {
			if ( min &&  even) return new BooleanExpression<AtomAcceptance>(true);
			if (!min &&  even) return new BooleanExpression<AtomAcceptance>(false);
			if ( min && !even) return new BooleanExpression<AtomAcceptance>(false);
			if (!min && !even) return new BooleanExpression<AtomAcceptance>(true);
		}

		BooleanExpression<AtomAcceptance> result = null;

		boolean reversed = min;
		boolean infOnOdd = !even;

        for (int i = 0; i < colors; i++) {
        	int color = (reversed ? colors-i-1 : i);

        	boolean produceInf;
            if (color % 2 == 0) {
            	produceInf = !infOnOdd;
            } else {
            	produceInf = infOnOdd;
            }

            BooleanExpression<AtomAcceptance> node;
            if (produceInf) {
            	node = new BooleanExpression<AtomAcceptance>(AtomAcceptance.Inf(color));
            } else {
            	node = new BooleanExpression<AtomAcceptance>(AtomAcceptance.Fin(color));
            }

            if (result == null) {
            	result = node;
            } else {
            	if (produceInf) {
            		// Inf always with |
            		result = node.or(result);
            	} else {
            		// Fin always with &
            		result = node.and(result);
            	}
            }
        }

		return result;
	}

	/** Convert extra info to list of integers */
	private static List<Integer> extraInfoToIntegerList(String accName, List<Object> extraInfo) {
		List<Integer> result = new ArrayList<Integer>(extraInfo.size());
		for (Object i : extraInfo) {
			if (!(i instanceof Integer)) {
				throw new IllegalArgumentException("For acceptance " + accName + ", all arguments have to be integers");
			}
			
			result.add((Integer)i);
		}

		return result;
	}

	/** Helper: Extract a String from extraInfo, throw exception otherwise */
	private static String extraInfoToString(String accName, List<Object> extraInfo, int index) {
		if (extraInfo.get(index) instanceof String)
			return (String) extraInfo.get(index);
		
		throw new IllegalArgumentException("Argument "+(index-1)+" for acceptance " + accName +" has to be a string!");
	}

	/** Check the number of arguments in extra info */
	private static void checkNumberOfArguments(String accName, List<Object> extraInfo, int expectedNumberOfArguments) throws IllegalArgumentException
	{
		if (expectedNumberOfArguments != extraInfo.size()) {
			throw new IllegalArgumentException("For acceptance " + accName + ", expected " + expectedNumberOfArguments + " arguments, got " + extraInfo.size());
		}
	}

}
