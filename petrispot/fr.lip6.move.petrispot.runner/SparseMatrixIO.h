#ifndef SPARSE_MATRIX_IO_H
#define SPARSE_MATRIX_IO_H

#include "MatrixCol.h"
#include <cstdint>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <limits>
#include <string>

/**
 * Binary format for sparse integer matrices (KERS format).
 *
 * Header (16 bytes):
 *   magic   : 4 bytes  "KERS"
 *   version : 1 byte   (= 1)
 *   flags   : 1 byte   (reserved, = 0)
 *   nrows   : 4 bytes  uint32 (little-endian)
 *   ncols   : 4 bytes  uint32 (little-endian)
 *   padding : 2 bytes
 *
 * Body — non-empty columns only, in ascending column order:
 *   col_idx : 4 bytes  uint32
 *   nnz     : 4 bytes  uint32
 *   repeated nnz times:
 *     row_idx : 4 bytes uint32
 *     value   : 8 bytes int64 (little-endian)
 *
 * Terminator:
 *   col_idx = 0xFFFFFFFF
 *
 * Row indices within a column are sorted ascending.
 * Values are int64; the caller narrows/widens as needed for T.
 */

static const char KERS_MAGIC[4] = {'K', 'E', 'R', 'S'};
static const uint8_t KERS_VERSION = 1;
static const uint32_t KERS_END = 0xFFFFFFFF;

template<typename T>
class SparseMatrixIO {
public:

    static bool write(const MatrixCol<T> &matrix, const std::string &filename) {
        FILE *f = fopen(filename.c_str(), "wb");
        if (!f) {
            std::cerr << "Error: cannot open '" << filename << "' for writing\n";
            return false;
        }

        uint32_t nrows = (uint32_t) matrix.getRowCount();
        uint32_t ncols = (uint32_t) matrix.getColumnCount();

        // Header
        uint8_t header[16] = {};
        memcpy(header, KERS_MAGIC, 4);
        header[4] = KERS_VERSION;
        header[5] = 0; // flags
        memcpy(header + 6,  &nrows, 4);
        memcpy(header + 10, &ncols, 4);
        // header[14..15] = padding (zero)
        fwrite(header, 1, 16, f);

        // Columns
        const auto &cols = matrix.getColumns();
        for (uint32_t ci = 0; ci < ncols; ++ci) {
            const SparseArray<T> &col = cols[ci];
            if (col.size() == 0) continue;

            uint32_t nnz = (uint32_t) col.size();
            fwrite(&ci,  4, 1, f);
            fwrite(&nnz, 4, 1, f);
            for (size_t i = 0; i < col.size(); ++i) {
                uint32_t row = (uint32_t) col.keyAt(i);
                int64_t  val = (int64_t)  col.valueAt(i);
                fwrite(&row, 4, 1, f);
                fwrite(&val, 8, 1, f);
            }
        }

        // Terminator
        fwrite(&KERS_END, 4, 1, f);
        fclose(f);
        return true;
    }

    // Returns a MatrixCol<T> read from file.
    // nrows and ncols are set from the header.
    static MatrixCol<T> read(const std::string &filename) {
        FILE *f = fopen(filename.c_str(), "rb");
        if (!f) {
            std::cerr << "Error: cannot open '" << filename << "' for reading\n";
            return MatrixCol<T>();
        }

        uint8_t header[16];
        if (fread(header, 1, 16, f) != 16) {
            std::cerr << "Error: truncated header in '" << filename << "'\n";
            fclose(f);
            return MatrixCol<T>();
        }

        if (memcmp(header, KERS_MAGIC, 4) != 0) {
            std::cerr << "Error: bad magic in '" << filename << "'\n";
            fclose(f);
            return MatrixCol<T>();
        }
        if (header[4] != KERS_VERSION) {
            std::cerr << "Error: unsupported KERS version " << (int)header[4] << "\n";
            fclose(f);
            return MatrixCol<T>();
        }

        uint32_t nrows, ncols;
        memcpy(&nrows, header + 6,  4);
        memcpy(&ncols, header + 10, 4);

        MatrixCol<T> matrix(nrows, ncols);

        uint32_t col_idx;
        while (fread(&col_idx, 4, 1, f) == 1) {
            if (col_idx == KERS_END) break;

            uint32_t nnz;
            if (fread(&nnz, 4, 1, f) != 1) {
                std::cerr << "Error: truncated nnz for column " << col_idx << "\n";
                break;
            }

            SparseArray<T> &col = matrix.getColumn(col_idx);
            for (uint32_t i = 0; i < nnz; ++i) {
                uint32_t row;
                int64_t  val;
                if (fread(&row, 4, 1, f) != 1 || fread(&val, 8, 1, f) != 1) {
                    std::cerr << "Error: truncated entry in column " << col_idx << "\n";
                    fclose(f);
                    return matrix;
                }
                // Overflow check: warn if int64 value doesn't fit in T
                if (val > (int64_t) std::numeric_limits<T>::max() ||
                    val < (int64_t) std::numeric_limits<T>::min()) {
                    std::cerr << "Warning: value " << val << " in column " << col_idx
                              << " row " << row << " overflows T (width "
                              << sizeof(T) << " bytes); truncated.\n";
                }
                col.append((size_t) row, (T) val);
            }
        }

        fclose(f);
        return matrix;
    }

    // Convenience: read and also return nrows/ncols separately (useful when
    // caller needs to know dimensions even if matrix has empty columns).
    static MatrixCol<T> read(const std::string &filename,
                             uint32_t &nrows_out, uint32_t &ncols_out) {
        FILE *f = fopen(filename.c_str(), "rb");
        if (!f) {
            std::cerr << "Error: cannot open '" << filename << "' for reading\n";
            nrows_out = ncols_out = 0;
            return MatrixCol<T>();
        }

        uint8_t header[16];
        if (fread(header, 1, 16, f) != 16) {
            std::cerr << "Error: truncated header in '" << filename << "'\n";
            fclose(f); nrows_out = ncols_out = 0;
            return MatrixCol<T>();
        }
        if (memcmp(header, KERS_MAGIC, 4) != 0 || header[4] != KERS_VERSION) {
            std::cerr << "Error: bad magic or version in '" << filename << "'\n";
            fclose(f); nrows_out = ncols_out = 0;
            return MatrixCol<T>();
        }

        memcpy(&nrows_out, header + 6,  4);
        memcpy(&ncols_out, header + 10, 4);
        fclose(f);

        return read(filename);
    }
};

#endif // SPARSE_MATRIX_IO_H
