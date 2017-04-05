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
import java.util.Iterator;
import java.util.List;


/**
 * A Matrix specified for the invariant module.
 * @author Manuel Gieseking, Dennis-Michael Borde
 */
public class Matrix implements Iterable<List<Integer>> {

	private int iRows;
	private int iCols;
	private final List<List<Integer>> lRows;

	/**
	 * Returns the identity matrix with given col and row count.
	 * @param m - the row count of the identity matrix.
	 * @param n - the col count of the identity matrix.
	 * @return the identity matrix with the given col and row count.
	 */
	public static Matrix identity(int m, int n) {
		Matrix result = new Matrix(m, n);
		for (int i = 0; i < m; ++i) {
			final List<Integer> row = result.lRows.get(i);
			if (i < n) {
				row.set(i, 1);
			}
		}
		return result;
	}

	/**
	 * Constructor for a new Matrix with the given col and row count.
	 * @param m - the row count of the resulting matrix.
	 * @param n - the col count of the resulting matrix.
	 */
	public Matrix(int m, int n) {
		this.iRows = m;
		this.iCols = n;
		this.lRows = new ArrayList<>(this.iRows);

		for (int i = 0; i < this.iRows; ++i) {
			List<Integer> col = new ArrayList<>(this.iCols);
			for (int j = 0; j < this.iCols; ++j) {
				col.add(0);
			}
			this.lRows.add(col);
		}
	}

	/**
	 * Constructor for a new Matrix with the values from the given array.
	 * @param src - the template to create the matrix from.
	 */
	public Matrix(int[][] src) {
		this.iRows = src.length;
		this.iCols = src[0].length;
		this.lRows = new ArrayList<>(this.iRows);

		for (int i = 0; i < this.iRows; ++i) {
			final int[] row = src[i];
			final List<Integer> vRow = new ArrayList<>(this.iCols);
			for (int j = 0; j < this.iCols; ++j) {
				vRow.add(row[j]);
			}
			lRows.add(vRow);
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

	/**
	 * Returns the row with the given index in this matrix. Changes to this row will have affect to this matrix.
	 * @param i - the index of the wished row of this matrix.
	 * @return the row with the given index in this matrix.
	 */
	public List<Integer> getRow(int i) {
		return this.lRows.get(i);
	}

	/**
	 * Returns a copy of the column with the given index of this matrix.
	 * @param i - the index of the wished column of this matrix.
	 * @return a copy of the column with the given index of this matrix.
	 */
	public List<Integer> getColumn(int i) {
		List<Integer> result = new ArrayList<>(iCols);
		for (List<Integer> row : lRows) {
			result.add(row.get(i));
		}
		return result;
	}

	/**
	 * Returns a row which has at least one component different from zero. It also returns the index of the column
	 * where a component not equal to zero was found. If such a row does not exists, than null.
	 * @return the index of the column with a none zero component and the addicted row or null if not existent.
	 */
	public Pair<Integer, List<Integer>> getNoneZeroRow() {
		for (List<Integer> row : this.lRows) {
			for (int i = 0; i < row.size(); ++i) {
				if (row.get(i) != 0) {
					return new Pair<>(i, row);
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
		for (int i = 0; i < lRows.size(); ++i) {
			List<Integer> row = lRows.get(i);
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
			return new Pair<Integer, List<Integer>>(min, lRows.get(min));
	}

	/**
	 * Deletes the column with the given index from this matrix.
	 * @param j - the index of the column which should be deleted.
	 */
	public void deleteColumn(int j) {
		for (List<Integer> row : this.lRows) {
			row.remove(j);
		}
		this.iCols -= 1;
	}

	/**
	 * Appends a given column to this matrix. That means adding the given column from the right side to this matrix.
	 * @param column - the column to append.
	 */
	public void appendColumn(List<Integer> column) {
		assert iRows == column.size();
		for (int i = 0; i < iRows; ++i) {
			lRows.get(i).add(column.get(i));
		}
		this.iCols++;
	}

	/**
	 * Checks if this matrix only contains of components equal to zero.
	 * @return true if this matrix has just components equal to zero.
	 */
	public boolean isZero() {
		for (List<Integer> row : this.lRows) {
			for (Integer i : row) {
				if (i != 0) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public Iterator<List<Integer>> iterator() {
		return this.lRows.iterator();
	}

	@Override
	public String toString() {
		return "Matrix{" + "lRows=" + lRows + '}';
	}
}