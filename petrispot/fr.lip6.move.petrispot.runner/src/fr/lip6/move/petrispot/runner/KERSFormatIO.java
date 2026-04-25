package fr.lip6.move.petrispot.runner;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import android.util.SparseIntArray;
import fr.lip6.move.gal.util.IntMatrixCol;

/**
 * Read and write sparse integer matrices in KERS (Kernel/Elimination Result Sparse) binary format.
 *
 * All multi-byte integers are little-endian.
 *
 * Header (16 bytes):
 *   magic   : 4 bytes  "KERS"
 *   version : 1 byte   (= 1)
 *   flags   : 1 byte   (= 0, reserved)
 *   nrows   : 4 bytes  uint32 LE
 *   ncols   : 4 bytes  uint32 LE
 *   padding : 2 bytes  (zero)
 *
 * Body — non-empty columns only, in ascending column order:
 *   col_idx : 4 bytes  uint32 LE
 *   nnz     : 4 bytes  uint32 LE
 *   repeated nnz times:
 *     row_idx : 4 bytes  uint32 LE
 *     value   : 8 bytes  int64  LE   (row indices sorted ascending within each column)
 *
 * Terminator:
 *   4 bytes  0xFFFFFFFF
 */
public class KERSFormatIO {

	private static final Logger log = Logger.getLogger("fr.lip6.move.gal");

	private static final byte[] MAGIC = { 'K', 'E', 'R', 'S' };
	private static final byte VERSION = 1;

	// ---- little-endian primitives ----
	// DataInput/Output is big-endian; reverseBytes() swaps to LE after a full-word read/write.

	private static int readIntLE(DataInputStream in) throws IOException {
		return Integer.reverseBytes(in.readInt());
	}

	private static long readLongLE(DataInputStream in) throws IOException {
		return Long.reverseBytes(in.readLong());
	}

	private static void writeIntLE(DataOutputStream out, int v) throws IOException {
		out.writeInt(Integer.reverseBytes(v));
	}

	private static void writeLongLE(DataOutputStream out, long v) throws IOException {
		out.writeLong(Long.reverseBytes(v));
	}

	// ---- public API ----

	public static void write(IntMatrixCol matrix, Path path) throws IOException {
		try (DataOutputStream out = new DataOutputStream(
				new BufferedOutputStream(Files.newOutputStream(path)))) {
			write(matrix, out);
		}
	}

	public static void write(IntMatrixCol matrix, DataOutputStream out) throws IOException {
		int nrows = matrix.getRowCount();
		int ncols = matrix.getColumnCount();

		// Header
		out.write(MAGIC);
		out.writeByte(VERSION);
		out.writeByte(0);        // flags
		writeIntLE(out, nrows);
		writeIntLE(out, ncols);
		out.writeByte(0);        // padding
		out.writeByte(0);

		// Non-empty columns in ascending order
		for (int ci = 0; ci < ncols; ci++) {
			SparseIntArray col = matrix.getColumn(ci);
			int nnz = col.size();
			if (nnz == 0) continue;
			writeIntLE(out, ci);
			writeIntLE(out, nnz);
			// Contiguous row indices block
			for (int i = 0; i < nnz; i++) {
				writeIntLE(out, col.keyAt(i));
			}
			// Contiguous values block
			for (int i = 0; i < nnz; i++) {
				writeLongLE(out, (long) col.valueAt(i));
			}
		}

		// Terminator: 0xFFFFFFFF
		writeIntLE(out, -1);
	}

	public static IntMatrixCol read(Path path) throws IOException {
		try (DataInputStream in = new DataInputStream(
				new BufferedInputStream(Files.newInputStream(path)))) {
			return read(in);
		}
	}

	public static IntMatrixCol read(DataInputStream in) throws IOException {
		// Validate header
		byte[] magic = new byte[4];
		in.readFully(magic);
		if (magic[0] != MAGIC[0] || magic[1] != MAGIC[1]
				|| magic[2] != MAGIC[2] || magic[3] != MAGIC[3]) {
			throw new IOException("Bad KERS magic");
		}
		byte version = in.readByte();
		if (version != VERSION) {
			throw new IOException("Unsupported KERS version: " + version);
		}
		in.readByte();           // flags (ignored)
		int nrows = readIntLE(in);
		int ncols = readIntLE(in);
		in.readByte();           // padding
		in.readByte();

		IntMatrixCol matrix = new IntMatrixCol(nrows, 0);

		// Stream column entries until sentinel 0xFFFFFFFF (== -1 as signed int)
		int colIdx;
		int[] rowBuf  = new int[0];
		long[] valBuf = new long[0];

		while ((colIdx = readIntLE(in)) != -1) {
			int nnz = readIntLE(in);
			// Grow scratch buffers only when needed
			if (nnz > rowBuf.length) {
				rowBuf = new int[nnz];
				valBuf = new long[nnz];
			}
			// Read contiguous row-indices block, then contiguous values block
			for (int i = 0; i < nnz; i++) rowBuf[i] = readIntLE(in);
			for (int i = 0; i < nnz; i++) valBuf[i] = readLongLE(in);

			// Pre-allocate exactly nnz slots — avoids all GrowingArrayUtils resizing
			boolean skip = false;
			SparseIntArray col = new SparseIntArray(nnz);
			for (int i = 0; i < nnz; i++) {
				int  ival = (int) valBuf[i];
				if (ival != valBuf[i]) {
					log.warning("KERS value " + valBuf[i] + " at col=" + colIdx
							+ " row=" + rowBuf[i] + " overflows int; skipping row.");
					skip = true;
					break;
				}
				// Row indices sorted ascending in file: append is O(1) — no binary search
				col.append(rowBuf[i], ival);
			}
			// add the invariant
			if (!skip)
				matrix.appendColumn(col);
		}

		return matrix;
	}
}
