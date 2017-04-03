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

/** File forked from 
 * taken from : APT ; @psychon

apt/src/module/uniol/apt/analysis/invariants/InvariantCalculator.java

see : https://github.com/CvO-Theory/apt/blob/master/src/module/uniol/apt/analysis/invariants/InvariantCalculator.java
Forked from revision : 284c01c  on Sep 21, 2016
 */

package uniol.apt.analysis.invariants;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;
import org.apache.commons.math.linear.RealVector.Entry;

import fr.lip6.move.gal.gal2smt.bmc.FlowMatrix;
//import uniol.apt.adt.pn.Node;
//import uniol.apt.adt.pn.PetriNet;
//import uniol.apt.util.interrupt.InterrupterRegistry;
//import uniol.apt.util.MathTools;
//import uniol.apt.util.Pair;

/**
 * A calculator for invariants and testing if a net is covered by invariants.
 * Provides two differient algorithms for calculating the invariants. The first
 * algorithm is descripted in http://de.scribd.com/doc/49919842/Pn-ESTII (slide
 * 88) and the other is also based an the farkas algorithm and is descripted in
 * http://pipe2.sourceforge.net/documents/PIPE-Report.pdf (page 19) which is
 * based on the paper of D'Anna and Trigila "Concurrent system analysis using
 * Petri nets – an optimised algorithm for finding net invariants", Mario D'Anna
 * and Sebastiano Trigila, Computer Communications vol 11, no. 4 august 1988.
 * @author Dennis-Michael Borde, Manuel Gieseking
 */
public class InvariantCalculator {

	/**
	 * Hidden constructor
	 */
	private InvariantCalculator() {
	}

	/**
	 * Enumeration for choosing which algorithm should be used.
	 */
	public enum InvariantAlgorithm {

		FARKAS,
		PIPE;
	}

	/**
	 * A class for holding the sets P+ = {j | c_hj &gt; 0} and P- = {j | c_hj less
	 * 0} for a given row.
	 */
	private static class PpPm {

		// The row
		public final RealVector h;
		// P+ set
		public final BitSet pPlus;
		// P- set
		public final BitSet pMinus;

		/**
		 * Constructor creates the sets P- and P+ for a given row.
		 * @param h - the row from which the sets should be created.
		 */
		public PpPm(RealVector h) {
			this.h = h;
			this.pMinus = new BitSet(h.getDimension());
			this.pPlus = new BitSet(h.getDimension());

			for (Iterator<Entry> it = h.sparseIterator(); it.hasNext() ;  ) {
				Entry e = it.next();
				if (e.getValue() > 0) {
					pPlus.set(e.getIndex());
				} else {
					pMinus.set(e.getIndex());
				}
			}
		}
	}

	/**
	 * Calculates for a given matrix the P+ = {j | c_hj &gt; 0} and P- = {j | c_hj
	 * less 0} sets for each row.
	 * @param matC - the matrix from which the sets should be calculated.
	 * @return The result of the calculation
	 */
	private static List<PpPm> calcPpPm(RealMatrix matC) {
		final List<PpPm> result = new ArrayList<>();
		for (int row = 0 ; row < matC.getRowDimension() ; row++) {
			RealVector h = matC.getRowVector(row);
			result.add(new PpPm(h));
		}
		return result;
	}

	/**
	 * Checks if some row of the given matrix satisfy P+ == {} xor P- == {} and
	 * return this row or null if such a row do not exists.
	 * @param pppms - the list of all rows with P+ and P- sets.
	 * @return the row which satisfy P+ == {} xor P- == {} or null if not
	 * existent.
	 */
	private static RealVector check11(List<PpPm> pppms) {
		for (PpPm pppm : pppms) {
			boolean pmEmpty = pppm.pMinus.isEmpty();
			boolean ppEmpty = pppm.pPlus.isEmpty();
			if ((pmEmpty || ppEmpty) && !(pmEmpty && ppEmpty)) {
				return pppm.h;
			}
		}
		return null;
	}

	/**
	 * Holds the result of the check11b-check. That means it holds the row, the
	 * columnindex where a component is less then respectivly greater than zero
	 * and the P+ respectivly P- set if there exists a row in the given matrix
	 * such that |P+| == 1 or |P-| == 1.
	 */
	private static class Check11bResult {

		// The first columnindex where c_hj < 0 respectivly c_hj > 0
		public final Integer k;
		// The whole row
		public final RealVector h;
		// The set P+ respectivly P-
		public final BitSet p;

		/**
		 * Constructor to save the data.
		 * @param i - the first columnindex where a component ist less
		 * respectivly greater than zero.
		 * @param h2 - the whole row.
		 * @param pPlus - the set P+ respectivly P-.
		 */
		public Check11bResult(int i, RealVector h2, BitSet pPlus) {
			this.k = i;
			this.h = h2;
			this.p = pPlus;
		}
	}

	/**
	 * Checks if there exists a row in the given matrix such that |P+| == 1 or
	 * |P-| == 1 and returns the row or null if such a row do not exists.
	 * @param pppms the list of all rows with P+ and P- sets.
	 * @return the row which satisfy |P+| == 1 or |P-| == 1 or null if not
	 * existent.
	 */
	private static Check11bResult check11b(List<PpPm> pppms) {
		for (PpPm pppm : pppms) {
			if ( isSinglebit(pppm.pMinus) ) {
				return new Check11bResult(pppm.pMinus.nextSetBit(0), pppm.h, pppm.pPlus);
			} else if (isSinglebit(pppm.pPlus)) {
				return new Check11bResult(pppm.pPlus.nextSetBit(0), pppm.h, pppm.pMinus);
			}
		}
		return null;
	}

	private static boolean isSinglebit(BitSet bs) {
		int i = bs.nextSetBit(0);
		if (i==-1) {
			return false;
		}
		return bs.nextSetBit(i) == -1;
	}

//	/**
//	 * Calculates the invariants with the algorithm based on
//	 * http://pipe2.sourceforge.net/documents/PIPE-Report.pdf (page 19).
//	 * @param mat - the matrix to calculate the invariants from.
//	 * @return a generator set of the invariants.
//	 */
//	private static Set<List<Integer>> calcInvariantsPIPE(int[][] mat) {
//		if (mat.length == 0 || mat[0].length == 0) {
//			return new HashSet<>();
//		}
//		// incidence matrix
//		final Matrix matC = new Matrix(mat);
//		final Matrix matB = Matrix.identity(matC.getColumnCount(), matC.getColumnCount());
//
//		// Phase 1:
//		while (!matC.isZero()) {
//			InterrupterRegistry.throwIfInterruptRequestedForCurrentThread();
//			// [1.1] if there exists a row h in C such that the sets P+ = {j | c_hj > 0},
//			// P- = {j | c_hj < 0} satisfy P+ == {} or P- == {} and not (P+ == {} and P- == {})
//			// that means it exists a row that all components are positive respectivly negativ
//			final List<PpPm> pppms = calcPpPm(matC);
//			List<Integer> h = check11(pppms);
//			if (h != null) {
//				// [1.1.a] delete from the extended matrix all the columns of index j \in P+ \cup P-
//				for (int j = matC.getColumnCount() - 1; j >= 0; --j) {
//					if (h.get(j) != 0) {
//						matC.deleteColumn(j);
//						matB.deleteColumn(j);
//					}
//				}
//			} else {
//				// [1.1.b] if there exists a row h in C such that |P+| == 1 or |P-| == 1
//				final Check11bResult chkResult = check11b(pppms);
//				if (chkResult != null) {
//					// [1.1.b.1] let k be the unique index of column belonging to P+ (resp. to P-)
//					for (Integer j : chkResult.p) {
//						// substitute to the column of index j the linear combination of
//						//the columns indexed by k and j with the coefficients
//						//|chj| and |chk| respectively.
//						final Integer chk = Math.abs(chkResult.h.get(chkResult.k));
//						final Integer chj = Math.abs(chkResult.h.get(j));
//						for (List<Integer> row : matC) {
//							row.set(j, row.get(j) * chk + row.get(chkResult.k) * chj);
//						}
//						for (List<Integer> row : matB) {
//							row.set(j, row.get(j) * chk + row.get(chkResult.k) * chj);
//						}
//					}
//					// delete from the extended matrix the column of index k
//					matC.deleteColumn(chkResult.k);
//					matB.deleteColumn(chkResult.k);
//				} else {
//					// [1.1.b.1] let h be the index of a non-zero row of C.
//					// let k be the index of a column such that chk != 0.
//					Pair<Integer, List<Integer>> pair = matC.getNoneZeroRow();
//					h = pair.getSecond();
//					int k = pair.getFirst();
//					// for all rows j with j != k and c_hj != 0
//					for (int j = 0; j < h.size(); ++j) {
//						if (j != k && h.get(j) != 0) {
//							//substitute to the column of index j the linear combination
//							// of the columns of indices k and j with coefficients
//							// alpha and beta defined as follows:
//							int cHj = h.get(j);
//							int cHk = h.get(k);
//							int alpha = ((Math.signum(cHj) * Math.signum(cHk)) < 0)
//								? Math.abs(cHj) : -Math.abs(cHj);
//							int beta = Math.abs(cHk);
//							for (List<Integer> row : matC) {
//								row.set(j, row.get(j) * beta + row.get(k) * alpha);
//							}
//							for (List<Integer> row : matB) {
//								row.set(j, row.get(j) * beta + row.get(k) * alpha);
//							}
//						}
//					}
//					// delete from the extended matrix the column of index k
//					matC.deleteColumn(k);
//					matB.deleteColumn(k);
//				}
//			}
//		}
//
//		// phase 2
//		Pair<Integer, List<Integer>> pair;
//		while ((pair = matB.getRowWithNegativeElement()) != null) {
//			InterrupterRegistry.throwIfInterruptRequestedForCurrentThread();
//			PpPm pppm = new PpPm(pair.getSecond());
//			List<Integer> row = pair.getSecond();
//			if (pppm.pPlus.size() > 0) {
//				for (Integer j : pppm.pPlus) {
//					for (Integer k : pppm.pMinus) {
//						// operate a linear combination on the columns of indices j and k
//						// in order to geta  new column having the pair.getFirst element equal
//						// to zero
//						List<Integer> column = new ArrayList<>(row.size());
//						int a = -row.get(k);
//						int b = row.get(j);
//						List<Integer> ck = matB.getColumn(k);
//						List<Integer> cj = matB.getColumn(j);
//						for (int i = 0; i < ck.size(); i++) {
//							column.add(a * cj.get(i) + b * ck.get(i));
//						}
//						// append column to matrix B
//						matB.appendColumn(column);
//					}
//				}
//				// Delete from B all the columns of index k \in P-
//				for (Integer idx : pppm.pMinus) {
//					matB.deleteColumn(idx);
//				}
//			}
//		}
//
//		// Phase 3: Retrieve Invariants (the columns)
//		Set<List<Integer>> result = new HashSet<>();
//		for (int i = 0; i < matB.getColumnCount(); ++i) {
//			List<Integer> invariants = matB.getColumn(i);
//			// Phase 4: Make them minimal
//			int gcd = MathTools.gcd(invariants);
//			if (gcd > 1) {
//				for (int j = 0; j < invariants.size(); ++j) {
//					invariants.set(j, invariants.get(j) / gcd);
//				}
//			}
//			result.add(invariants);
//		}
//		return result;
//	}
//
//	/**
//	 * Calculates the invariants with the algorithm based on
//	 * http://de.scribd.com/doc/49919842/Pn-ESTII (slide 88)
//	 * @param mat matrix to calculate the invariants from.
//	 * @return a generator set of the invariants.
//	 */
//	private static Set<List<Integer>> calcInvariantsFarkas(int[][] mat) {
//		int rows = mat.length;
//		if (mat.length == 0 || mat[0].length == 0) {
//			return new HashSet<>();
//		}
//		int cols = mat[0].length;
//		int dcols = cols + rows;
//
//		// initializes d as (C | E) with incidence matrix C and identity E
//		// time: O(rows*(cols+rows))
//		// place: O(rows*(cols+rows))
//		List<List<Integer>> d = new ArrayList<>();
//		for (int i = 0; i < rows; ++i) {
//			List<Integer> row = new ArrayList<>();
//			d.add(i, row);
//			for (int j = 0; j < dcols; ++j) {
//				if (j < cols) {
//					row.add(j, mat[i][j]);
//				} else {
//					row.add(j, (i != j - cols) ? 0 : 1);
//				}
//			}
//		}
//		// for all columns (transitions)
//		// time: O()
//		// place: O()
//		for (int i = 0; i < cols; ++i) {
//			int offset = 1;
//			// time: O()
//			// place: O()
//			do {
//				rows = d.size();
//				// for all pairs of rows in d
//				// time: O()
//				// place: O()
//				for (int j1 = 0; j1 < rows - 1; ++j1) {
//					final List<Integer> z1 = d.get(j1);
//					for (int j2 = j1 + offset; j2 < rows; ++j2) {
//						InterrupterRegistry.throwIfInterruptRequestedForCurrentThread();
//						final List<Integer> z2 = d.get(j2);
//						// check opposite signum at position i
//						if (Math.signum(z1.get(i)) * Math.signum(z2.get(i)) < 0) {
//							// z(i) = 0
//							final int z1abs = Math.abs(z1.get(i));
//							final int z2abs = Math.abs(z2.get(i));
//
//							// time: O(cols+rows)
//							// place: O(cols+rows)
//							final List<Integer> z = new ArrayList<>();
//							for (int k = 0; k < dcols; ++k) {
//								int a = z2abs * z1.get(k);
//								int b = z1abs * z2.get(k);
//								z.add(k, a + b);
//								//z.add(k, z2abs * z1.get(k) + z1abs * z2.get(k));
//
//							}
//							// normalize z
//							final int gcd = MathTools.gcd(z);
//							if (gcd != 1) {
//								// time: O(cols+rows)
//								// place: O(1)
//								for (int k = 0; k < dcols; ++k) {
//									z.set(k, z.get(k) / gcd);
//								}
//							}
//							d.add(z);
//						}
//					}
//				}
//				offset = rows;
//				// check new added rows.
//			} while (rows < d.size());
//			// remove all rows z with z(i) != 0
//			for (int j = d.size() - 1; j >= 0; --j) {
//				if (d.get(j).get(i) != 0) {
//					d.remove(j);
//				}
//			}
//		}
//
//		// the result is at the right side of d (remove left transitions-count columns).
//		// time: O()
//		// place: O()
//		Set<List<Integer>> result = new HashSet<>();
//		for (List<Integer> z : d) {
//			result.add(z.subList(cols, dcols));
//		}
//		return result;
//	}
//
//	/**
//	 * Transposes the given matrix.
//	 * @param mat - the matrix to transpose.
//	 * @return the transposed matrix.
//	 */
//	private static int[][] transposeMatrix(int[][] mat) {
//		if (mat.length == 0) {
//			return mat;
//		}
//		final int rows = mat.length;
//		final int cols = mat[0].length;
//		final int[][] matT = new int[cols][rows];
//		for (int i = 0; i < rows; ++i) {
//			for (int j = 0; j < cols; ++j) {
//				matT[j][i] = mat[i][j];
//			}
//		}
//		return matT;
//	}
//
//	/**
//	 * Calculates the s-invariants of the the given petri net with the pipe
//	 * algorithm.
//	 * @param pn - the petri net to calculate the s-invariants from.
//	 * @return a generator set of the invariants.
//	 */
//	public static Set<List<Integer>> calcSInvariants(FlowMatrix pn) {
//		return InvariantCalculator.calcSInvariants(pn, InvariantAlgorithm.PIPE);
//	}
//
//	/**
//	 * Calculates the s-invariants of the the given petri net with the given
//	 * algorithm.
//	 * @param pn - the petri net to calculate the s-invariants from.
//	 * @param algo - the algorithm with which the invariants should be
//	 * calculated.
//	 * @return a generator set of the invariants.
//	 */
//	public static Set<List<Integer>> calcSInvariants(PetriNet pn, InvariantAlgorithm algo) {
//		switch (algo) {
//			case FARKAS:
//				return InvariantCalculator.calcInvariantsFarkas(pn.getIncidenceMatrix());
//			case PIPE:
//				return InvariantCalculator.calcInvariantsPIPE(transposeMatrix(pn.getIncidenceMatrix()));
//			default:
//				return InvariantCalculator.calcInvariantsFarkas(pn.getIncidenceMatrix());
//		}
//	}
//
//	/**
//	 * Calculates the t-invariants of the the given petri net with the pipe
//	 * algorithm.
//	 * @param pn - the petri net to calculate the t-invariants from.
//	 * @return a generator set of the invariants.
//	 */
//	public static Set<List<Integer>> calcTInvariants(PetriNet pn) {
//		return InvariantCalculator.calcTInvariants(pn, InvariantAlgorithm.PIPE);
//	}
//
//	/**
//	 * Calculates the t-invariants of the the given petri net with the given
//	 * algorithm.
//	 * @param pn - the petri net to calculate the t-invariants from.
//	 * @param algo - the algorithm with which the invariants should be
//	 * calculated.
//	 * @return a generator set of the invariants.
//	 */
//	public static Set<List<Integer>> calcTInvariants(PetriNet pn, InvariantAlgorithm algo) {
//		switch (algo) {
//			case FARKAS:
//				return InvariantCalculator.calcInvariantsFarkas(
//						transposeMatrix(pn.getIncidenceMatrix()));
//			case PIPE:
//				return InvariantCalculator.calcInvariantsPIPE(pn.getIncidenceMatrix());
//			default:
//				return InvariantCalculator.calcInvariantsFarkas(
//						transposeMatrix(pn.getIncidenceMatrix()));
//		}
//	}

//	/**
//	 * Checks whether the the given petri net is covered by s-invariants with
//	 * the pipe algorithm. Returns the invariant which covers or null if not
//	 * existent.
//	 * @param pn - the petri net to check covered.
//	 * @return the invariant which covers or null if it is not covered.
//	 */
//	public static Vector coveredBySInvariants(PetriNet pn) {
//		return coveredBySInvariants(pn, InvariantAlgorithm.PIPE);
//	}
//
//	/**
//	 * Checks whether the the given petri net is covered by s-invariants with
//	 * the given algorithm. Returns the invariant which covers or null if not
//	 * existent.
//	 * @param pn - the petri net to check covered.
//	 * @param algo - the algorithm with which it should be checked.
//	 * @return the invariant which covers or null if it is not covered.
//	 */
//	public static Vector coveredBySInvariants(PetriNet pn, InvariantAlgorithm algo) {
//		Set<List<Integer>> invariants = InvariantCalculator.calcSInvariants(pn, algo);
//		return coveredBySInvariants(pn, invariants);
//	}
//
//	/**
//	 * Checks whether the the given petri net is covered by the given s-invariants.
//	 * Returns the invariant which covers or null if not existent.
//	 * @param pn - the petri net to check covered.
//	 * @param invariants - the invariants which should be tested if they cover.
//	 * @return the invariant which covers or null if it is not covered.
//	 */
//	public static Vector coveredBySInvariants(PetriNet pn, Set<List<Integer>> invariants) {
//		if (invariants.isEmpty()) {
//			return null;
//		}
//		Vector invariant = new Vector();
//		for (int i = 0; i < pn.getPlaces().size(); ++i) {
//			int v = 0;
//			for (List<Integer> y : invariants) {
//				v += y.get(i);
//			}
//			if (v == 0) {
//				return null;
//			}
//			invariant.add(v);
//		}
//		return invariant;
//	}
//
//	/**
//	 * Checks whether the the given petri net is covered by t-invariants with
//	 * the pipe algorithm. Returns the invariant which covers or null if not
//	 * existent.
//	 * @param pn - the petri net to check covered.
//	 * @return the invariant which covers or null if it is not covered.
//	 */
//	public static Vector coveredByTInvariants(PetriNet pn) {
//		return coveredByTInvariants(pn, InvariantAlgorithm.PIPE);
//	}
//
//	/**
//	 * Checks whether the the given petri net is covered by t-invariants with
//	 * the given algorithm. Returns the invariant which covers or null if not
//	 * existent.
//	 * @param pn - the petri net to check covered.
//	 * @param algo - the algorithm with which it should be checked.
//	 * @return the invariant which covers or null if it is not covered.
//	 */
//	public static Vector coveredByTInvariants(PetriNet pn, InvariantAlgorithm algo) {
//		Set<List<Integer>> invariants = InvariantCalculator.calcTInvariants(pn, algo);
//		return coveredByTInvariants(pn, invariants);
//	}
//
//	/**
//	 * Checks whether the the given petri net is covered by the given t-invariants.
//	 * Returns the invariant which covers or null if not existent.
//	 * @param pn - the petri net to check covered.
//	 * @param invariants - the invariants which should be tested if they cover.
//	 * @return the invariant which covers or null if it is not covered.
//	 */
//	public static Vector coveredByTInvariants(PetriNet pn, Set<List<Integer>> invariants) {
//		if (invariants.isEmpty()) {
//			return null;
//		}
//		Vector invariant = new Vector();
//		for (int i = 0; i < pn.getTransitions().size(); ++i) {
//			int v = 0;
//			for (List<Integer> y : invariants) {
//				v += y.get(i);
//			}
//			if (v == 0) {
//				return null;
//			}
//			invariant.add(v);
//		}
//		return invariant;
//	}
//
//	/**
//	 * Generates for every given invariant a mapping to the given nodes.
//	 *
//	 * @param <T> - Place or Transition (extends Node) depending on t- or
//	 * s-invariants are given.
//	 * @param nodes - the nodes for the mapping.
//	 * @param invariants - the invariants which should be mapped.
//	 * @return a set of invariants mapped to the given nodes.
//	 */
//	public static <T extends Node> Set<Map<T, Integer>> getMapping(Set<T> nodes, Set<List<Integer>> invariants) {
//		Set<Map<T, Integer>> ret = new HashSet<>();
//		for (List<Integer> invariant : invariants) {
//			Map<T, Integer> map = new HashMap<>();
//			int i = 0;
//			for (Iterator<T> it = nodes.iterator(); it.hasNext();) {
//				T node = it.next();
//				map.put(node, invariant.get(i));
//				++i;
//			}
//			ret.add(map);
//		}
//		return ret;
//	}
}
