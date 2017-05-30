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

import android.util.SparseArray;


/**
 * A Matrix specified for the invariant module, stored by COLUMN so that deletColumn has good complexity.
 * @author Manuel Gieseking, Dennis-Michael Borde, Yann Thierry-Mieg
 */
public class MatrixCol {

	private int iRows;
	private int iCols;
	private final List<SparseArray<Integer>> lCols;

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
			SparseArray<Integer> toadd = new SparseArray<>();
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
			final SparseArray<Integer> toadd = new SparseArray<>();
			for (int row = 0; row < this.iRows; ++row) {
				int val = src[row][col];
				if (val != 0) {
					toadd.put(row,val);
				}
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
	public SparseArray<Integer> getColumn(int i) {
		return lCols.get(i);
	}

	public int get (int row, int col) {
		return lCols.get(col).get(row,0);
	}
	
	public void set (int row, int col, int val) {
		if (val != 0) {
			lCols.get(col).put(row,val);
		} else {
			lCols.get(col).remove(row);
		}
	}
	
	/**
	 * Returns a row which has at least one component different from zero. It also returns the index of the column
	 * where a component not equal to zero was found. If such a row does not exists, than null.
	 * @return the index of the column with a none zero component and the addicted row or null if not existent.
	 */
	public Pair<Integer, Integer> getNoneZeroRow() {
		for (int tcol = 0; tcol < getColumnCount(); tcol++) {
			if (lCols.get(tcol).size()==0) {
				continue;
			} else {
				return new Pair<>(lCols.get(tcol).keyAt(0) , tcol);
			}
		}
		return null;
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
	public void appendColumn(SparseArray<Integer> column) {
		assert column.size()==0 || iRows > column.keyAt(column.size()-1);
		lCols.add(column);
		this.iCols++;
	}

	/**
	 * Checks if this matrix only contains of components equal to zero.
	 * @return true if this matrix has just components equal to zero.
	 */
	public boolean isZero() {
		for (SparseArray<Integer> row : this.lCols) {
			if (row.size() != 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "Matrix{" + "lRows=" + lCols + '}';
	}
}