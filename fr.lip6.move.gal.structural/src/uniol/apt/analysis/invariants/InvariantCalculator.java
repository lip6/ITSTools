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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.util.SparseBoolArray;
import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.FlowMatrix;
import fr.lip6.move.gal.util.MatrixCol;

/**
 * A calculator for invariants and testing if a net is covered by invariants.
 * Provides two differient algorithms for calculating the invariants. The first
 * algorithm is descripted in http://de.scribd.com/doc/49919842/Pn-ESTII (slide
 * 88) and the other is also based an the farkas algorithm and is descripted in
 * http://pipe2.sourceforge.net/documents/PIPE-Report.pdf (page 19) which is
 * based on the paper of D'Anna and Trigila "Concurrent system analysis using
 * Petri nets – an optimised algorithm for finding net invariants", Mario D'Anna
 * and Sebastiano Trigila, Computer Communications vol 11, no. 4 august 1988.
 * @author Dennis-Michael Borde, Manuel Gieseking , Adapted to ITS-tools by Yann Thierry-Mieg, 2017.
 * 
 */
public class InvariantCalculator {

	static final boolean DEBUG = false;
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
		public final int row;
		// P+ set
		public final SparseBoolArray pPlus = new SparseBoolArray();
		// P- set
		public final SparseBoolArray pMinus= new SparseBoolArray();

		/**
		 * initially empty.
		 * @param row
		 */
		public PpPm (int row) {
			this.row = row;
		}

		public void setValue(int j, int val) {
			if (val == 0) {
				pMinus.clear(j);
				pPlus.clear(j);
			} else if (val < 0) {
				pMinus.set(j);
				pPlus.clear(j);
			} else {
				pMinus.clear(j);
				pPlus.set(j);
			}
		}

		@Override
		public String toString() {
			return "PpPm [row=" + row + ", pPlus=" + pPlus + ", pMinus=" + pMinus + "]";
		}
		
		
	}

	/**
	 * Calculates for a given matrix the P+ = {j | c_hj &gt; 0} and P- = {j | c_hj
	 * less 0} sets for each row.
	 * @param matC - the matrix from which the sets should be calculated.
	 * @return The result of the calculation
	 */
	private static List<PpPm> calcPpPm(MatrixCol matC) {
		final List<PpPm> result = new ArrayList<>();
		for (int row = 0; row < matC.getRowCount() ; row++) {
			result.add(new PpPm(row));
		}
		for (int icol=0, cole=matC.getColumnCount() ; icol < cole ; icol++) {
			SparseIntArray col = matC.getColumn(icol);
			for (int i=0,ie=col.size() ; i < ie ;  i++) {
				PpPm toedit = result.get(col.keyAt(i));
				if (col.valueAt(i) < 0) {
					toedit.pMinus.append(icol,true);
				} else {
					toedit.pPlus.append(icol,true);
				}
			}
		}
		return result;
	}


	/**
	 * Holds the result of the check11b-check. That means it holds the row, the
	 * columnindex where a component is less then respectivly greater than zero
	 * and the P+ respectivly P- set if there exists a row in the given matrix
	 * such that |P+| == 1 or |P-| == 1.
	 */
	private static class Check11bResult {

		// The first columnindex where c_hj < 0 respectivly c_hj > 0
		public final int col;
		// The whole row
		public final int row;
		// The set P+ respectivly P-
		public final SparseBoolArray p;
		/**
		 * Constructor to save the data.
		 * @param k - the first columnindex where a component ist less
		 * respectivly greater than zero.
		 * @param h - the whole row.
		 * @param pPlus - the set P+ respectivly P-.
		 */
		public Check11bResult(int k, int row, SparseBoolArray pPlus) {
			this.col = k;
			this.row = row;
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
			if (pppm.pMinus.size() == 1) {
				return new Check11bResult(pppm.pMinus.keyAt(0), pppm.row, pppm.pPlus);
			} else if (pppm.pPlus.size() == 1) {
				return new Check11bResult(pppm.pPlus.keyAt(0), pppm.row, pppm.pMinus);
			}
		}
		return null;
	}
	
	
	/**
	 * Calculates the invariants with the algorithm based on
	 * http://pipe2.sourceforge.net/documents/PIPE-Report.pdf (page 19).
	 * @param mat - the matrix to calculate the invariants from.
	 * @param onlyPositive whether we just stop at Flows or go for Semi-Flows
	 * @param pnames variable names 
	 * @return a generator set of the invariants.
	 */
	public static Set<SparseIntArray> calcInvariantsPIPE(MatrixCol mat, boolean onlyPositive, List<String> pnames) {
		if (mat.getColumnCount() == 0 || mat.getRowCount() == 0) {
			return new HashSet<>();
		}
		MatrixCol tmat = mat.transpose();
		Set<SparseIntArray> normed = new HashSet<>();
		for (int i=0; i < tmat.getColumnCount() ; i++) {
			SparseIntArray norm = tmat.getColumn(i);
			normalize(norm);
			normed.add(norm);
		}
		if (normed.size() < tmat.getColumnCount()) {
			System.out.println("Normalized transition count is "+normed.size() + " out of "+ tmat.getColumnCount() + " initially.");			
		}
		MatrixCol matnorm = new MatrixCol(tmat.getRowCount(),0);
		for (SparseIntArray col : normed) {
			matnorm.appendColumn(col);
		}
		final MatrixCol matB = phase1PIPE(matnorm.transpose(),pnames);
		
//		final MatrixCol matB = phase1PIPE(new MatrixCol(mat));
		// We want to work with columns in this part of the algorithm
		// We add and remove columns all day => we want to switch to a column based representation
		// order of rows is really irrelevant + columns which are identical up to scaling factor are useless
		// let's use a set of columns.
		Set<SparseIntArray> colsBsparse = new HashSet<>(2*matB.getColumnCount());
		for (int i=0; i < matB.getColumnCount() ; i++) {
			SparseIntArray col = matB.getColumn(i);
			if (col.size() != 0) {
				normalizeWithSign(col);
				colsBsparse.add(col);
			}
		}
		
		if (! onlyPositive) {
			return colsBsparse;
		} 
		
		MatrixCol colsB = new MatrixCol(pnames.size(), 0);
		for (SparseIntArray cb : colsBsparse) {
			colsB.appendColumn(cb);			
		}
				
		// phase 2				
		System.out.println("// Phase 2 : computing semi flows from basis of "+ colsB.getColumnCount() +" invariants ");
		
		int iter=0;
		SparseBoolArray treated = new SparseBoolArray();
		colsBsparse = new HashSet<>();
		while (colsB.getColumnCount() < 2000) {
			/// InterrupterRegistry.throwIfInterruptRequestedForCurrentThread();
			if (treated.size() >0) {
				for (int i=treated.size()-1; i >=0; i--) {
					colsBsparse.add(colsB.getColumn(treated.keyAt(i)));
					colsB.deleteColumn(treated.keyAt(i));
				}
				treated.clear();
			}
		
			List<PpPm> pppms = calcPpPm(colsB);
			SparseBoolArray negRows = new SparseBoolArray();
						
			int minRow = -1 ;
			int minRowWeight = -1;
			for (int row = 0, rowe=pppms.size() ; row < rowe ; row++) {
				PpPm pp = pppms.get(row);
				int pps = pp.pPlus.size();
				int ppm = pp.pMinus.size();
				int weight = pps + ppm;
				
				if (pps == 0) {
					for (int i=0;i<ppm;i++) {
						negRows.set(pp.pMinus.keyAt(i));
					}
				}
				if (pps > 0 && ppm > 0) {
					if (pps==1 || ppm==1) {
						// can't grow the size
						minRow =row;
						break;
					}					
					if (minRow == -1 || minRowWeight > weight) {
						int refinedweight = 0;
						for (int i=0,ie=pp.pPlus.size();i<ie;i++) {
							refinedweight += colsB.getColumn(pp.pPlus.keyAt(i)).size();
						}
						for (int i=0,ie=pp.pMinus.size();i<ie;i++) {
							refinedweight += colsB.getColumn(pp.pMinus.keyAt(i)).size();
						}
						if (minRow == -1 || minRowWeight > refinedweight) {
							minRow = row;
							minRowWeight = refinedweight;
						}
					}
				}
			}
			
			if (negRows.size()>0) {
				// cleanup
				for (int j=negRows.size()-1 ; j >= 0 ; j--) {
					colsB.deleteColumn(negRows.keyAt(j));
				}
				continue;
			}
			// check for a pure positive column
			int purePos = -1;
			
			for (int i=0,ie=colsB.getColumnCount(); i<ie ; i++) {
				if (treated.get(i)) {
					continue;
				}
				SparseIntArray col = colsB.getColumn(i);
				boolean hasNeg = false;
				for (int j=0,je=col.size(); j<je ; j++) {
					if (col.valueAt(j)<0) {
						hasNeg=true;
						break;
					}
				}
				if (!hasNeg) {
					// check intersection
					boolean needed = false;
					for (int j=0,je=col.size(); j<je ; j++) {
						int row = col.keyAt(j);
						PpPm ppm = pppms.get(row);
						if (ppm.pMinus.size()>0) {
							needed = true;
							purePos = i;
							minRow = row;
							break;
						}
					}
					if (!needed) {
						treated.set(i);
					} else {
						break;
					}
				}
			}
						
			int targetRow = minRow;
			if (targetRow == -1) {
				// no more negative rows to treat
				break;
			}
			PpPm ppm = pppms.get(targetRow);			
			if (ppm.pPlus.size() > 0) {
				for (int j=0,je=ppm.pPlus.size();j<je;j++) {
					SparseIntArray colj = colsB.getColumn(ppm.pPlus.keyAt(j));
					if (purePos != -1) {
						colj = colsB.getColumn(purePos);
						j=je;
					} 
					for (int k=0,ke=ppm.pMinus.size();k<ke;k++) {
						SparseIntArray colk = colsB.getColumn(ppm.pMinus.keyAt(k));
						// operate a linear combination on the columns of indices j and k
						// in order to get a  new column having the pair.getFirst element equal
						// to zero
						int a = -colk.get(targetRow);
						int b = colj.get(targetRow);
						SparseIntArray column = SparseIntArray.sumProd(a, colj, b, colk);
						// add normalization step : we don't need scalar scaling of each other
						normalize(column);
						// append column to matrix B
						// tests existence 
						if (column.size() >0)
							colsB.appendColumn(column);
					}
				}
				// Delete from B all the columns of index k \in P-
				// cleanup
				for (int j=ppm.pMinus.size()-1 ; j >= 0 ; j--) {
					colsB.deleteColumn(ppm.pMinus.keyAt(j));
					treated.deleteAndShift(ppm.pMinus.keyAt(j));
				}
			}
		//	System.out.println("Phase 2 iter "+ (iter++) + " rows : " + colsB.getRowCount() + " cols " + colsB.getColumnCount() + " treated " + colsBsparse.size());
		//	System.out.println(colsB);
		}
		// System.out.println("Found "+ colsB.getColumnCount() + " invariants.");
		
		for (SparseIntArray l : colsB.getColumns()) {
			if (l.size() > 0)
				colsBsparse.add(l);
		}
		// System.out.println("Found "+ colsBsparse.size() + " different invariants.");
		colsBsparse.removeIf(a -> {
			for (int i=0,ie=a.size();i<ie;i++) {
				if (a.valueAt(i)<0) {
					return true;
				}
			}
			return false;
			
		});
		System.out.println("Found "+ colsBsparse.size() + " positive invariants.");
		return colsBsparse;
	}

	
	
	private static List<Integer> normalize(SparseIntArray col, int size) {
		List<Integer> list = new ArrayList<>(size);
		boolean allneg = true;
		for (int i=0 ; i < col.size() ; i++) {
			if (col.valueAt(i) > 0) {
				allneg = false;
				break;
			}
		}
		if (allneg) {
			for (int i=0 ; i < col.size() ; i++) {
				col.setValueAt(i , - col.valueAt(i));
			}
		}
		
		for (int i=0; i < size ; i++) {
			list.add(col.get(i, 0));
		}		
		normalize(list);
		return list;
	}

	private static MatrixCol phase1PIPE(MatrixCol matC, List<String> pnames) {
		// incidence matrix
		final MatrixCol matB = MatrixCol.identity(matC.getColumnCount(), matC.getColumnCount());
		
		System.out.println("// Phase 1: matrix "+matC.getRowCount()+" rows "+matC.getColumnCount()+" cols");
		List<PpPm> pppms = calcPpPm(matC);
		while (! matC.isZero()) {
			test1b(matC, matB, pppms, pnames);
		}
		return matB;
	}

	private static void test1b(final MatrixCol matC, final MatrixCol matB, final List<PpPm> pppms, List<String> pnames) {
		// [1.1.b] if there exists a row h in C such that |P+| == 1 or |P-| == 1
		final Check11bResult chkResult = check11b(pppms);
		if (chkResult != null) {
			test1b1(matC, matB, pppms, chkResult,pnames);
		} else {
			test1b2(matC, matB, pppms,pnames);
		}
	}
	
	public static SparseBoolArray sumProdInto(double alpha, SparseIntArray ta, double beta, SparseIntArray tb) throws ArithmeticException {
    	SparseBoolArray changed = new SparseBoolArray();
		SparseIntArray flow = new SparseIntArray(Math.max(ta.size(), tb.size()));

    	int i = 0;
    	int j = 0; 
    	while (i < ta.size() || j < tb.size()) {					
    		int ki = i==ta.size() ? Integer.MAX_VALUE : ta.keyAt(i);
    		int kj = j==tb.size() ? Integer.MAX_VALUE : tb.keyAt(j);
    		if (ki == kj) {    			
    			double dval = alpha * ta.valueAt(i)+ beta* tb.valueAt(j); 					
				if (dval >= Integer.MAX_VALUE || dval < Integer.MIN_VALUE) {
					throw new ArithmeticException();
				}
				int val = (int) dval;
    			if (val != 0) {
    				flow.append(ki, val);
    			}
    			if (val != ta.valueAt(i)) {
    				changed.set(ki);
    			}
    			i++;
    			j++;
    		} else if (ki < kj) {
    			double dval = alpha * ta.valueAt(i); 					
				if (dval >= Integer.MAX_VALUE || dval < Integer.MIN_VALUE) {
					throw new ArithmeticException();
				}
				int val = (int) dval;
    			if (val != 0) flow.append(ki, val);
    			if (val != ta.valueAt(i)) {
    				changed.set(ki);
    			}
    			i++;
    		} else if (kj < ki) {
    			double dval = beta * tb.valueAt(j); 					
				if (dval >= Integer.MAX_VALUE || dval < Integer.MIN_VALUE) {
					throw new ArithmeticException();
				}
				int val = (int) dval;
    			if (val != 0) flow.append(kj, val);
    			if (val != 0) {
    				changed.set(kj);
    			}
    			j++;
    		}
    	}
    	ta.move(flow);
    	return changed;
	}

	private static void test1b2(final MatrixCol matC, final MatrixCol matB, final List<PpPm> pppms, List<String> pnames) {
		// [1.1.b.1] let tRow be the index of a non-zero row of C.
		// let tCol be the index of a column such that c[trow][tcol] != 0.

		int candidate = -1;
		int szcand = Integer.MAX_VALUE;
		int totalcand = Integer.MAX_VALUE;
		for (int col = 0; col < matC.getColumnCount(); col++) {
			int size = matC.getColumn(col).size();
			if (size==0) {
				continue;
			} else if (size <= szcand) {				
				int total = sumAbsValues(matC.getColumn(col));
				if ( size < szcand || ( size == szcand && total <= totalcand)) {
					candidate = col;
					szcand = size;
					totalcand = total;
				}						
			}
		}
		// int [] pair = matC.getNoneZeroRow();
		int tRow = matC.getColumn(candidate).keyAt(0);		
		int tCol = candidate;
		
		int cHk = matC.get(tRow,tCol);
		int bbeta = Math.abs(cHk);
		
		if (DEBUG) System.out.println("Rule 1b2 : "+pnames.get(tCol));
		// for all cols j with j != tCol and c[tRow][j] != 0
		for (int j = 0; j < matC.getColumnCount(); ++j) {
			SparseIntArray colj = matC.getColumn(j); 

			int cHj = colj.get(tRow);
			if (j != tCol && cHj != 0) {
				//substitute to the column of index j the linear combination
				// of the columns of indices tCol and j with coefficients
				// alpha and beta defined as follows:
				int alpha = ((Math.signum(cHj) * Math.signum(cHk)) < 0)
					? Math.abs(cHj) : -Math.abs(cHj);
				if (alpha == 0 && bbeta == 1) {
					continue;
				}
				int gcd = MathTools.gcd(alpha,bbeta);
				alpha /= gcd;
				int beta = bbeta /gcd; 
				
				SparseBoolArray changed = sumProdInto(beta, colj, alpha, matC.getColumn(tCol));
				for (int ind=0, inde = changed.size(); ind < inde ; ind++) {
					pppms.get(changed.keyAt(ind)).setValue(j,colj.get(changed.keyAt(ind)));
				}
				SparseIntArray coljb = matB.getColumn(j);
				sumProdInto(beta, coljb, alpha, matB.getColumn(tCol));
			}
		}
		clearColumn(tCol, matC, matB, pppms);
	}


	public static void clearColumn(int tCol, final MatrixCol matC, final MatrixCol matB, final List<PpPm> pppms) {
		// delete from the extended matrix the column of index k
		SparseIntArray colk = matC.getColumn(tCol);
		for (int i=0,ie=colk.size() ; i < ie ; i++) {
			pppms.get(colk.keyAt(i)).setValue(tCol,0);
		}
		colk.clear();
		matB.getColumn(tCol).clear();
	}

	private static int sumAbsValues(SparseIntArray col) {
		int tot = 0;
		for (int i=0; i < col.size(); i++) {
			tot += Math.abs(col.valueAt(i));
		}
		return tot;
	}

	private static void test1b1(final MatrixCol matC, final MatrixCol matB, final List<PpPm> pppms,
			final Check11bResult chkResult, List<String> pnames) {
		if (DEBUG) System.out.println("Rule 1b.1 : "+pnames.get(chkResult.row));
		// [1.1.b.1] let k be the unique index of column belonging to P+ (resp. to P-)
		while ( chkResult.p.size() > 0 ) {
			int j = chkResult.p.keyAt(0);
			// substitute to the column of index j the linear combination of
			//the columns indexed by k and j with the coefficients
			//|chj| and |chk| respectively.
			int chk = Math.abs(matC.get(chkResult.row, chkResult.col));
			int chj = Math.abs(matC.get(chkResult.row,j));
			int gcd = MathTools.gcd(chk, chj);
			chk /= gcd;
			chj /= gcd;
			
			SparseBoolArray changed = sumProdInto(chk, matC.getColumn(j), chj, matC.getColumn(chkResult.col));
			for (int ind=0, inde = changed.size(); ind < inde ; ind++) {
				pppms.get(changed.keyAt(ind)).setValue(j,matC.getColumn(j).get(changed.keyAt(ind)));
			}
			SparseIntArray coljb = matB.getColumn(j);
			sumProdInto(chk, coljb, chj, matB.getColumn(chkResult.col));
		}
		// delete from the extended matrix the column of index k
		clearColumn(chkResult.col, matC, matB, pppms);
	}


	private static void normalize(List<Integer> invariants) {
		int gcd = MathTools.gcd(invariants);
		if (gcd > 1) {
			for (int j = 0; j < invariants.size(); ++j) {
				int norm =  invariants.get(j) / gcd;								
				invariants.set(j, norm);
			}
		}
	}
	
	public static void normalizeWithSign (SparseIntArray col) {
		boolean allneg = true;
		for (int i=0 ; i < col.size() ; i++) {
			if (col.valueAt(i) > 0) {
				allneg = false;
				break;
			}
		}
		if (allneg) {
			for (int i=0 ; i < col.size() ; i++) {
				col.setValueAt(i , - col.valueAt(i));
			}
		}
		
		int gcd = MathTools.gcd(col);
		if (gcd > 1) {
			for (int j = 0; j < col.size(); ++j) {
				int norm =  col.valueAt(j) / gcd;								
				col.setValueAt(j, norm);
			}
		}
	}
	
	public static void normalize (SparseIntArray invariants) {
		int gcd = MathTools.gcd(invariants);
		if (gcd > 1) {
			for (int j = 0; j < invariants.size(); ++j) {
				int norm =  invariants.valueAt(j) / gcd;								
				invariants.setValueAt(j, norm);
			}
		}
	}

	/**
	 * Calculates the invariants with the algorithm based on
	 * http://de.scribd.com/doc/49919842/Pn-ESTII (slide 88)
	 * @param mat matrix to calculate the invariants from.
	 * @return a generator set of the invariants.
	 */
	private static Set<SparseIntArray> calcInvariantsFarkas(int[][] mat) {
		int rows = mat.length;
		if (mat.length == 0 || mat[0].length == 0) {
			return new HashSet<>();
		}
		int cols = mat[0].length;
		int dcols = cols + rows;

		System.out.println("Initialize :");
		// initializes d as (C | E) with incidence matrix C and identity E
		// time: O(rows*(cols+rows))
		// place: O(rows*(cols+rows))
		List<List<Integer>> d = new ArrayList<>();
		for (int i = 0; i < rows; ++i) {
			List<Integer> row = new ArrayList<>();
			d.add(i, row);
			for (int j = 0; j < dcols; ++j) {
				if (j < cols) {
					row.add(j, mat[i][j]);
				} else {
					row.add(j, (i != j - cols) ? 0 : 1);
				}
			}
		}
		System.out.println("Phase 2 : rows :" + d.size()+ " cols : " + d.get(0).size());
		// for all columns (transitions)
		// time: O()
		// place: O()
		for (int i = 0; i < cols; ++i) {
			int offset = 1;
			// time: O()
			// place: O()
			do {
				rows = d.size();
				// for all pairs of rows in d
				// time: O()
				// place: O()
				for (int j1 = 0; j1 < rows - 1; ++j1) {
					final List<Integer> z1 = d.get(j1);
					for (int j2 = j1 + offset; j2 < rows; ++j2) {
					//	InterrupterRegistry.throwIfInterruptRequestedForCurrentThread();
						final List<Integer> z2 = d.get(j2);
						// check opposite signum at position i
						if (Math.signum(z1.get(i)) * Math.signum(z2.get(i)) < 0) {
							// z(i) = 0
							final int z1abs = Math.abs(z1.get(i));
							final int z2abs = Math.abs(z2.get(i));

							// time: O(cols+rows)
							// place: O(cols+rows)
							final List<Integer> z = new ArrayList<>();
							for (int k = 0; k < dcols; ++k) {
								int a = z2abs * z1.get(k);
								int b = z1abs * z2.get(k);
								z.add(k, a + b);
								//z.add(k, z2abs * z1.get(k) + z1abs * z2.get(k));

							}
							// normalize z
							final int gcd = MathTools.gcd(z);
							if (gcd != 1) {
								// time: O(cols+rows)
								// place: O(1)
								for (int k = 0; k < dcols; ++k) {
									z.set(k, z.get(k) / gcd);
								}
							}
							d.add(z);
						}
					}
				}
				offset = rows;
				// check new added rows.
				System.out.println("Phase 2 : rows :" + d.size()+ " cols : " + d.get(0).size());
			} while (rows < d.size());
			// remove all rows z with z(i) != 0
			for (int j = d.size() - 1; j >= 0; --j) {
				if (d.get(j).get(i) != 0) {
					d.remove(j);
				}
			}
			System.out.println("Phase 2 end of iteration : rows :" + d.size()+ " cols : " + d.get(0).size());
		}

		// the result is at the right side of d (remove left transitions-count columns).
		// time: O()
		// place: O()
		Set<List<Integer>> result = new HashSet<>();
		for (List<Integer> z : d) {
			result.add(z.subList(cols, dcols));
		}
		
		
		HashSet<SparseIntArray> colsBsparse = new HashSet<>(2*result.size());
		for (List<Integer> l : result) {
			colsBsparse.add(new SparseIntArray(l));
		}
		
		return colsBsparse;
	}

	/**
	 * Calculates the s-invariants of the the given petri net with the pipe
	 * algorithm.
	 * @param pn - the petri net to calculate the s-invariants from.
	 * @return a generator set of the invariants.
	 */
	public static Set<SparseIntArray> calcSInvariants(FlowMatrix pn, boolean onlyPositive, List<String> pnames) {
		return calcSInvariants(pn, InvariantAlgorithm.PIPE, onlyPositive, pnames);
	}
	
	/**
	 * Calculates the s-invariants of the the given petri net with the given
	 * algorithm.
	 * @param pn - the petri net to calculate the s-invariants from.
	 * @param algo - the algorithm with which the invariants should be
	 * calculated.
	 * @return a generator set of the invariants.
	 */
	public static Set<SparseIntArray> calcSInvariants(FlowMatrix pn, InvariantAlgorithm algo, boolean onlyPositive, List<String> pnames) {
		switch (algo) {
			case FARKAS:
				return InvariantCalculator.calcInvariantsFarkas(pn.getIncidenceMatrix().explicit());
			case PIPE:
				return InvariantCalculator.calcInvariantsPIPE(pn.getIncidenceMatrix().transpose(), onlyPositive,pnames);
			default:
				return InvariantCalculator.calcInvariantsFarkas(pn.getIncidenceMatrix().explicit());
		}
	}

	/**
	 * Calculates the t-invariants of the the given petri net with the given
	 * algorithm.
	 * @param pn - the petri net to calculate the t-invariants from.
	 * @param algo - the algorithm with which the invariants should be
	 * calculated.
	 * @return a generator set of the invariants.
	 */
	public static Set<SparseIntArray> calcTInvariants(FlowMatrix pn, InvariantAlgorithm algo, List<String> tnames) {
		switch (algo) {
			case FARKAS:
				return InvariantCalculator.calcInvariantsFarkas(
						pn.getIncidenceMatrix().explicit());
			case PIPE:
				return InvariantCalculator.calcInvariantsPIPE(pn.getIncidenceMatrix(),true,tnames);
			default:
				return InvariantCalculator.calcInvariantsFarkas(
						pn.getIncidenceMatrix().transpose().explicit());
		}
	}

//	/**
//	 * Checks whether the the given petri net is covered by s-invariants with
//	 * the pipe algorithm. Returns the invariant which covers or null if not
//	 * existent.
//	 * @param pn - the petri net to check covered.
//	 * @return the invariant which covers or null if it is not covered.
//	 */
//	public static Vector coveredBySInvariants(FlowMatrix pn) {
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
//	public static Vector coveredBySInvariants(FlowMatrix pn, InvariantAlgorithm algo) {
//		Set<List<Integer>> invariants = InvariantCalculator.calcSInvariants(pn, algo);
//		return coveredBySInvariants(pn, invariants);
//	}

//	/**
//	 * Checks whether the the given petri net is covered by the given s-invariants.
//	 * Returns the invariant which covers or null if not existent.
//	 * @param pn - the petri net to check covered.
//	 * @param invariants - the invariants which should be tested if they cover.
//	 * @return the invariant which covers or null if it is not covered.
//	 */
//	public static Vector coveredBySInvariants(FlowMatrix pn, Set<List<Integer>> invariants) {
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