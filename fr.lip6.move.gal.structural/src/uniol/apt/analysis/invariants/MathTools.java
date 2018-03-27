/*-
 * APT - Analysis of Petri Nets and labeled Transition systems
 * Copyright (C) 2012-2013  Members of the project group APT
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package uniol.apt.analysis.invariants;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;

import android.util.SparseIntArray;

/**
 * Some methods for mathematical calculations like gcd and modulo.
 * @author Manuel Gieseking
 */
public class MathTools {

	/**
	 * Hidden constructor.
	 */
	private MathTools() {
	}

	/**
	 * Calculates the gcd of a given set of BigIntegers.
	 * @param set - the BigIntegers to calculate the gcd from.
	 * @return the gcd of the BigIntegers of the given set.
	 */
	public static BigInteger gcdBigInteger(Collection<BigInteger> set) {
		if (set.isEmpty())
			return BigInteger.ZERO;
		Iterator<BigInteger> iter = set.iterator();
		BigInteger gcd = iter.next();
		while (iter.hasNext()) {
			BigInteger b = iter.next();
			gcd = gcd.compareTo(b) < 0 ? b.gcd(gcd) : gcd.gcd(b);
		}
		return gcd;
	}

	/**
	 * Calculates the gcd of a given set of integers.
	 * @param set - the integers to calculate the gcd from.
	 * @return the gcd of the integers of the given set.
	 */
	public static int gcd(Collection<Integer> set) {
		if (set.isEmpty())
			return 0;
		Iterator<Integer> iter = set.iterator();
		int gcd = iter.next();
		while (iter.hasNext()) {
			gcd = gcd(gcd, iter.next());
			if (gcd == 1) return 1;
		}
		return gcd;
	}
	
	public static int gcd(SparseIntArray set) {
		if (set.size()==0)
			return 0;
		int gcd = set.valueAt(0);
		for (int i =1 ; i < set.size() ; i++) {
			gcd = gcd(gcd, set.valueAt(i));
			if (gcd == 1) return 1;
		}
		return gcd;
	}

	/**
	 * Calculates the gcd of two integers.
	 * @param a - first integer for calculating the gcd.
	 * @param b - second integer for calculating the gcd.
	 * @return the gcd of the two given integers.
	 */
	public static int gcd(int a, int b) {
		if (a < b) {
			return BigInteger.valueOf(b).gcd(BigInteger.valueOf(a)).intValue();
		} else {
			return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
		}
	}
	
//	private static int gcd(int a, int b) {
//		   if (b==0) return a;
//		   return gcd(b,a%b);
//	}

	/**
	 * Calculates the lcm of two numbers.
	 * @param a - first number for calculating the lcm.
	 * @param b - second number for calculating the lcm.
	 * @return the lcm of the two given numbers.
	 */
	public static BigInteger lcm(BigInteger a, BigInteger b) {
		return a.divide(a.gcd(b)).multiply(b).abs();
	}

	/**
	 * Calculates the lcm of two integers.
	 * @param a - first integer for calculating the lcm.
	 * @param b - second integer for calculating the lcm.
	 * @return the lcm of the two given integers.
	 */
	public static int lcm(int a, int b) {
		return bigIntToInt(lcm(BigInteger.valueOf(a), BigInteger.valueOf(b)));
	}

	private static int bigIntToInt(BigInteger value) {
		if (value.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0 ||
				value.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) < 0)
			throw new ArithmeticException("Cannot represent value as int: " + value);
		return value.intValue();
	}

	/**
	 * Calculates the mathematical modulo, so that it's given the positive value.
	 * @param a - divisor.
	 * @param b - dividend.
	 * @return a modulo b.
	 */
	public static int mod(int a, int b) {
		return BigInteger.valueOf(a).mod(BigInteger.valueOf(b)).intValue();
	}
}