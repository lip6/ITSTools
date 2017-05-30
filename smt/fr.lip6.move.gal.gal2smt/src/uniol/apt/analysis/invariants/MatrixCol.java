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
import java.util.List;


/**
 * A Matrix specified for the invariant module, stored by COLUMN so that deletColumn has good complexity.
 * @author Manuel Gieseking, Dennis-Michael Borde, Yann Thierry-Mieg
 */
public class MatrixCol {

	private int iRows;
	private int iCols;
	private final List<List<Integer>> lCols;

	/**
	 * Returns the identity matrix with given col and row count.
	 * @param m - the row count of the identity matrix.
	 * @param n - the col count of the identity matrix.
	 * @return the identity matrix with the given col and row count.
	 */
	public static MatrixCol identity(int m, int n) {
		MatrixCol result = new MatrixCol(m, n);
		for (int i = 0; i < m && i < n; ++i) {
			result.set(i,i, 1);			
		}
		return result;
	}

	/**
	 * Constructor for a new Matrix with the given col and row count.
	 * @param m - the row count of the resulting matrix.
	 * @param n - the col count of the resulting matrix.
	 */
	public MatrixCol(int m, int n) {
		this.iRows = m;
		this.iCols = n;
		this.lCols = new ArrayList<>(this.iCols);

		for (int col = 0 ; col < iCols ; col++) {
			List<Integer> toadd = new ArrayList<>(this.iRows);
			for (int row = 0 ; row < iRows ; row++) {
				toadd.add(0);
			}
			lCols.add(toadd);
		}
	}

	/**
	 * Constructor for a new Matrix with the values from the given array.
	 * @param src - the template to create the matrix from.
	 */
	public MatrixCol(int[][] src) {
		this.iRows = src.length;
		this.iCols = src[0].length;
		this.lCols = new ArrayList<>(this.iCols);

		for (int col = 0; col < this.iCols; ++col) {
			final List<Integer> toadd = new ArrayList<>(this.iRows);
			for (int row = 0; row < this.iRows; ++row) {
				toadd.add(src[row][col]);
			}
			lCols.add(toadd);
		}
	}

	/**
	 * Returns the count of rows of this matrix.
	 * @return the count of rows of this matrix.
	 */
	public int getRowCount() {
		return this.iRows;
	}

	/**
	 * Returns the count of cols of this matrix.
	 * @return the count of cols of this matrix.
	 */
	public int getColumnCount() {
		return this.iCols;
	}

//	/**
//	 * Returns the row with the given index in this matrix. Changes to this row will have affect to this matrix.
//	 * @param i - the index of the wished row of this matrix.
//	 * @return the row with the given index in this matrix.
//	 */
//	public List<Integer> getRow(int i) {
//	List<Integer> result = new ArrayList<>(iRows);
//	for (List<Integer> row : lCols) {
//		result.add(row.get(i));
//	}
//	return result;
////		return this.lCols.get(i);
//	}

	/**
	 * Returns the column with the given index of this matrix.
	 * @param i - the index of the wished column of this matrix.
	 * @return a copy of the column with the given index of this matrix.
	 */
	public List<Integer> getColumn(int i) {
		return lCols.get(i);
	}

	public int get (int row, int col) {
		return lCols.get(col).get(row);
	}
	public void set (int row, int col, int val) {
		lCols.get(col).set(row,val);
	}
	
	/**
	 * Returns a row which has at least one component different from zero. It also returns the index of the column
	 * where a component not equal to zero was found. If such a row does not exists, than null.
	 * @return the index of the column with a none zero component and the addicted row or null if not existent.
	 */
	public Pair<Integer, Integer> getNoneZeroRow() {
		int trow = -1;
		int tcol = -1;
		for (trow = 0; trow < getRowCount(); trow++) {
			for (tcol = 0; tcol < getColumnCount(); tcol++) {
				if (get(trow, tcol) != 0) {
					return new Pair<>(trow, tcol);
				}
			}
		}
		return null;
	}

	/**
	 * Returns a row and it's index in this matrix which has at least one negative component. If such a row does not
	 * exists, than null.
	 * @return the index of row and the row itself, which has at least one negative component or null if not
	 *         existent.
	 */
	public Pair<Integer, List<Integer>> getRowWithNegativeElement() {
		int min = -1 ;
		int minRow = -1;
		for (int i = 0; i < lCols.size(); ++i) {
			List<Integer> row = lCols.get(i);
			boolean hasNeg = false;
			int sz = 0;
			for (Integer val : row) {
				if (val < 0) {
					hasNeg = true;
					sz++;
				} else if (val > 0) {
					sz++;
				}
			}
			if (hasNeg) {
				if (min == -1 || minRow > sz) {
					min = i;
					minRow = sz;
				}
			}
		}
		if (min == -1)
			return null;
		else
			return new Pair<Integer, List<Integer>>(min, lCols.get(min));
	}

	/**
	 * Deletes the column with the given index from this matrix.
	 * @param j - the index of the column which should be deleted.
	 */
	public void deleteColumn(int j) {
		lCols.remove(j);
		this.iCols -= 1;
	}

	/**
	 * Appends a given column to this matrix. That means adding the given column from the right side to this matrix.
	 * @param column - the column to append.
	 */
	public void appendColumn(List<Integer> column) {
		assert iRows == column.size();
		lCols.add(column);
		this.iCols++;
	}

	/**
	 * Checks if this matrix only contains of components equal to zero.
	 * @return true if this matrix has just components equal to zero.
	 */
	public boolean isZero() {
		for (List<Integer> row : this.lCols) {
			for (Integer i : row) {
				if (i != 0) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "Matrix{" + "lRows=" + lCols + '}';
	}
}