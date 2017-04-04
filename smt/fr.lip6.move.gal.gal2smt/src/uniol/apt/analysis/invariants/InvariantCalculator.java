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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.OpenMapRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector.Entry;

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
// extends REalVector : workaround to iterate on vectors despite bug : https://issues.apache.org/jira/browse/MATH-1329
public class InvariantCalculator extends ArrayRealVector {

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
		public final int k;
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

	/**
	 * Calculates the invariants with the algorithm based on
	 * http://pipe2.sourceforge.net/documents/PIPE-Report.pdf (page 19).
	 * @param mat - the matrix to calculate the invariants from.
	 * @return a generator set of the invariants.
	 */
	private static Set<RealVector> calcInvariantsPIPE(RealMatrix mat) {
		if (mat.getRowDimension() == 0 || mat.getColumnDimension() == 0) {
			return new HashSet<>();
		}
		// incidence matrix
		RealMatrix matC = mat.copy();
		RealMatrix matB = MatrixUtils.createRealIdentityMatrix(mat.getColumnDimension());

		// Phase 1:
		while (! isZero(matC)) {
			//InterrupterRegistry.throwIfInterruptRequestedForCurrentThread();
			
			// [1.1] if there exists a row h in C such that the sets P+ = {j | c_hj > 0},
			// P- = {j | c_hj < 0} satisfy P+ == {} or P- == {} and not (P+ == {} and P- == {})
			// that means it exists a row that all components are positive respectivly negativ
			final List<PpPm> pppms = calcPpPm(matC);
			RealVector h = check11(pppms);
			if (h != null) {
				int [] reindex = new int [matC.getColumnDimension()];
				int r = 0;
				for (int i =0; i < reindex.length ; i++) {
					if (h.getEntry(i) != 0) {
						reindex[i]=-1; 
					} else {
						reindex[i]=r++;
					}
				}
				
				// [1.1.a] delete from the extended matrix all the columns of index j \in P+ \cup P-
				matC = removeColumns(matC, reindex, r);
				matB = removeColumns(matB, reindex, r);
			} else {
				// [1.1.b] if there exists a row h in C such that |P+| == 1 or |P-| == 1
				final Check11bResult chkResult = check11b(pppms);
				if (chkResult != null) {
					// [1.1.b.1] let k be the unique index of column belonging to P+ (resp. to P-)
					for (int j = chkResult.p.nextSetBit(0); j >= 0; j = chkResult.p.nextSetBit(j+1)) {
						// substitute to the column of index j the linear combination of
						//the columns indexed by k and j with the coefficients
						//|chj| and |chk| respectively.
						final double chk = Math.abs(chkResult.h.getEntry(chkResult.k));
						final double chj = Math.abs(chkResult.h.getEntry(j));
						for (int row = 0 ;  row < matC.getRowDimension() ; row++) {
							RealVector line = matC.getRowVector(row);
							line.setEntry(j, line.getEntry(j) * chk + line.getEntry(chkResult.k)*chj);
							line = matB.getRowVector(row);
							line.setEntry(j, line.getEntry(j) * chk + line.getEntry(chkResult.k)*chj);
						}						
					}
					// delete from the extended matrix the column of index k
					matC = deleteColumn(matC, chkResult.k);
					matB = deleteColumn(matB, chkResult.k);
				} else {
					// [1.1.b.1] let h be the index of a non-zero row of C.
					// let k be the index of a column such that chk != 0.
					int k=-1;
					for (int row = 0 ;  row < matC.getRowDimension() ; row++) {
						RealVector line = matC.getRowVector(row);
						for (Iterator<Entry> it = line.sparseIterator(); it.hasNext() ;  ) {
							k = row;
							break;
						}
					}
					h = matC.getRowVector(k);
					
					// for all rows j with j != k and c_hj != 0
					for (Iterator<Entry> it = h.sparseIterator(); it.hasNext() ;  ) {
						Entry ent = it.next();
						int j = ent.getIndex();
						if (ent.getIndex() != k) {
							//substitute to the column of index j the linear combination
							// of the columns of indices k and j with coefficients
							// alpha and beta defined as follows:
							double cHj = ent.getValue();
							double cHk = h.getEntry(k);
							double alpha = ((Math.signum(cHj) * Math.signum(cHk)) < 0)
									? Math.abs(cHj) : -Math.abs(cHj);
							double beta = Math.abs(cHk);
							
							for (int row = 0 ;  row < matC.getRowDimension() ; row++) {
								RealVector line = matC.getRowVector(row);
								line.setEntry(j, line.getEntry(j) * beta + line.getEntry(k) * alpha);
								line = matB.getRowVector(row);
								line.setEntry(j, line.getEntry(j) * beta + line.getEntry(k) * alpha);
							}
						}
					}
					
					// delete from the extended matrix the column of index k
					matC = deleteColumn(matC, k);
					matB = deleteColumn(matB, k);
				}
			}
		}

		// phase 2
		int negRow = -1;
		while ( (negRow = getRowWithNegativeElement(matB))!= -1) {		
			// InterrupterRegistry.throwIfInterruptRequestedForCurrentThread();
			PpPm pppm = new PpPm(matB.getRowVector(negRow));
			RealVector row = matB.getRowVector(negRow);
			if (pppm.pPlus.nextSetBit(0) > 0) {
				for (int j = pppm.pPlus.nextSetBit(0) ; j >= 0 ; j = pppm.pPlus.nextSetBit(j+1)) {
					for (int k = pppm.pMinus.nextSetBit(0) ; k >= 0 ; k = pppm.pMinus.nextSetBit(k+1)) {
						// operate a linear combination on the columns of indices j and k
						// in order to geta  new column having the pair.getFirst element equal
						// to zero
						RealVector column = new OpenMapRealVector(row.getDimension());
						double a = -row.getEntry(k);
						double b = row.getEntry(j);
						
						RealVector ck = matB.getColumnVector(k);
						RealVector cj = matB.getColumnVector(j);
						for (int i = 0; i < ck.getDimension(); i++) {
							column.setEntry(i , a * cj.getEntry(i) + b * ck.getEntry(i));
						}
						// append column to matrix B
						matB = appendColumn(matB, column);
					}
				}
				// Delete from B all the columns of index k \in P-
				for (int k = pppm.pMinus.nextSetBit(0) ; k >= 0 ; k = pppm.pMinus.nextSetBit(k+1)) {
					matB = deleteColumn(matB, k);
				}
			}
		}

		// Phase 3: Retrieve Invariants (the columns)
		Set<RealVector> result = new HashSet<>();
		for (int i = 0; i < matB.getColumnDimension(); ++i) {
			RealVector invariants = matB.getColumnVector(i);
			// Phase 4: Make them minimal
			double gcd = gcd(invariants);
			if (gcd > 1) {
				for (Iterator<Entry> it = invariants.sparseIterator() ; it.hasNext() ; ) {
					Entry ent = it.next();
					ent.setValue( ent.getValue() / gcd);
				}
			}
			result.add(invariants);
		}
		return result;
	}

	private static double gcd(double a, double b) {
		   if (b==0) return a;
		   return gcd(b,a%b);
	}
	
	private static double gcd (RealVector vec) {
		// empty vec
		if (! vec.sparseIterator().hasNext())
			return 0;
		
		Iterator<Entry> iter = vec.sparseIterator();
		double gcd = iter.next().getValue();
		while (iter.hasNext()) {
			gcd = gcd(gcd, iter.next().getValue());
			if (gcd == 1) return 1;
		}
		return gcd;
	}
	
	private static RealMatrix appendColumn(RealMatrix mat, RealVector column) {
		RealMatrix toret = MatrixUtils.createRealMatrix(mat.getRowDimension(), mat.getColumnDimension()+1);
		for (int row= 0 ; row < mat.getRowDimension() ; row++) {
			RealVector vec = mat.getRowVector(row);
			for (Iterator<Entry> it = vec.sparseIterator() ; it.hasNext() ; ) {
				Entry ent = it.next();
				toret.setEntry(row, ent.getIndex(), ent.getValue());
			}
		}
		toret.setColumnVector(mat.getColumnDimension(), column);
		return toret;
	}

	private static int getRowWithNegativeElement(RealMatrix mat) {
		for (int row= 0 ; row < mat.getRowDimension() ; row++) {
			RealVector vec = mat.getRowVector(row);
			for (Iterator<Entry> it = vec.sparseIterator() ; it.hasNext() ; ) {
				Entry ent = it.next();
				if (ent.getValue() < 0) {
					return row;
				}
			}
		}
		return -1;
	}

	private static RealMatrix removeColumns(final RealMatrix matC, int[] reindex, int nbCol) {
		RealMatrix ret = MatrixUtils.createRealMatrix(matC.getRowDimension(), nbCol);
		for (int row= 0 ; row < matC.getRowDimension() ; row++) {
			RealVector vec = matC.getRowVector(row);
			for (Iterator<Entry> it = vec.sparseIterator() ; it.hasNext() ; ) {
				Entry ent = it.next();
				if (reindex[ent.getIndex()] != -1) {
					ret.setEntry(row, reindex[ent.getIndex()], ent.getValue());
				}
			}
		}
		return ret;
	}

	private static RealMatrix deleteColumn(RealMatrix mat, int index) {
		RealMatrix ret = MatrixUtils.createRealMatrix(mat.getRowDimension(), mat.getColumnDimension() -1);
		for (int row= 0 ; row < mat.getRowDimension() ; row++) {
			RealVector vec = mat.getRowVector(row);
			for (Iterator<Entry> it = vec.sparseIterator() ; it.hasNext() ; ) {
				Entry ent = it.next();
				if (ent.getIndex() < index) {
					ret.setEntry(row, ent.getIndex(), ent.getValue());
				} else if (ent.getIndex() > index) {
					// after removed column
					ret.setEntry(row, ent.getIndex() -1, ent.getValue());
				} // else = index => dont copy
			}
		}
		return ret;
	}

	private static boolean isZero(RealMatrix matC) {
		for (int row= 0 ; row < matC.getRowDimension() ; row++) {
			RealVector vec = matC.getRowVector(row);
			for (Iterator<Entry> it = vec.sparseIterator() ; it.hasNext() ; ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Calculates the invariants with the algorithm based on
	 * http://de.scribd.com/doc/49919842/Pn-ESTII (slide 88)
	 * @param mat matrix to calculate the invariants from.
	 * @return a generator set of the invariants.
	 */
	private static Set<RealVector> calcInvariantsFarkas(RealMatrix mat) {
		int rows = mat.getRowDimension();
		int cols = mat.getColumnDimension();
		if (rows == 0 || cols == 0) {
			return new HashSet<>();
		}
		int dcols = cols + rows;

		// initializes d as (C | E) with incidence matrix C and identity E
		// time: O(rows*(cols+rows))
		// place: O(rows*(cols+rows))
		RealMatrix d = MatrixUtils.createRealMatrix(rows, dcols);
		d.setSubMatrix(mat.getData(), 0, 0);
		d.setSubMatrix(MatrixUtils.createRealIdentityMatrix(rows).getData(), 0, cols);
		// for all columns (transitions)
		// time: O()
		// place: O()
		for (int i = 0; i < cols; ++i) {
			int offset = 1;
			// time: O()
			// place: O()
			do {
				rows = d.getRowDimension();
				// for all pairs of rows in d
				// time: O()
				// place: O()
				for (int j1 = 0; j1 < rows - 1; ++j1) {
					final RealVector z1 = d.getRowVector(j1);
					for (int j2 = j1 + offset; j2 < rows; ++j2) {
						// InterrupterRegistry.throwIfInterruptRequestedForCurrentThread();
						final RealVector z2 = d.getRowVector(j2);
						// check opposite signum at position i
						if (Math.signum(z1.getEntry(i)) * Math.signum(z2.getEntry(i)) < 0) {
							// z(i) = 0
							final double z1abs = Math.abs(z1.getEntry(i));
							final double z2abs = Math.abs(z2.getEntry(i));

							// time: O(cols+rows)
							// place: O(cols+rows)
							final RealVector z = new OpenMapRealVector(dcols);
							for (int k = 0; k < dcols; ++k) {
								double a = z2abs * z1.getEntry(k);
								double b = z1abs * z2.getEntry(k);
								double res = a+b;
								if (res != 0) 
									z.setEntry(k, a + b);
								//z.add(k, z2abs * z1.get(k) + z1abs * z2.get(k));
							}
							// normalize z
							final double gcd = gcd(z);
							if (gcd != 1) {
								// time: O(cols+rows)
								// place: O(1)
								for (Iterator<Entry> it = z.sparseIterator() ; it.hasNext() ; ) {
									Entry ent = it.next();
									ent.setValue(ent.getValue() / gcd);
								}
							}
							d = addRow(d,z);
						}
					}
				}
				offset = rows;
				// check new added rows.
			} while (rows < d.getRowDimension());
			// remove all rows z with z(i) != 0
			int rmcount = 0;
			for (int j = d.getRowDimension() - 1; j >= 0; --j) {
				if (d.getRowVector(j).getEntry(i) != 0) {
					rmcount++;
				}
			}
			RealMatrix dcopy = MatrixUtils.createRealMatrix(d.getRowDimension() - rmcount, d.getColumnDimension());
			int realr = 0;
			for (int r=0 ; r < dcopy.getRowDimension() ; r++) {
				if (d.getRowVector(r).getEntry(i) != 0) {
					continue;
				} else {
					dcopy.setRowVector(realr++, d.getRowVector(r));
				}
			}
		}

		// the result is at the right side of d (remove left transitions-count columns).
		// time: O()
		// place: O()
		Set<RealVector> result = new HashSet<>();
		for (int row=0 ; row < d.getRowDimension() ; row++) {
			RealVector z = d.getRowVector(row);
			result.add(z.getSubVector(cols, dcols));
		}
		return result;
	}
	
	private static RealMatrix addRow(RealMatrix d, RealVector z) {
		RealMatrix ret = MatrixUtils.createRealMatrix(d.getRowDimension()+1 , d.getColumnDimension());
		ret.setSubMatrix(d.getData(), 0, 0);
		ret.setRowVector(d.getRowDimension(), z);
		return ret;
	}

	
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
	/**
	 * Calculates the s-invariants of the the given petri net with the pipe
	 * algorithm.
	 * @param pn - the petri net to calculate the s-invariants from.
	 * @return a generator set of the invariants.
	 */
	public static Set<RealVector> calcSInvariants(FlowMatrix pn) {
		return calcSInvariants(pn, InvariantAlgorithm.PIPE);
	}

	/**
	 * Calculates the s-invariants of the the given petri net with the given
	 * algorithm.
	 * @param pn - the petri net to calculate the s-invariants from.
	 * @param algo - the algorithm with which the invariants should be
	 * calculated.
	 * @return a generator set of the invariants.
	 */
	public static Set<RealVector> calcSInvariants(FlowMatrix pn, InvariantAlgorithm algo) {
		switch (algo) {
			case PIPE:
				return InvariantCalculator.calcInvariantsPIPE(pn.getIncidenceMatrix().transpose());
			case FARKAS:
			default:
				return InvariantCalculator.calcInvariantsFarkas(pn.getIncidenceMatrix());
		}
	}

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
