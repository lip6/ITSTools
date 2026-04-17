# KERS — Kernel/Elimination Result Sparse format

KERS is a compact binary format for sparse integer matrices.
It is used by PetriSpot both for **input** (incidence matrices) and **output** (invariant bases),
enabling efficient program-to-program transfer without going through PNML or ASCII representations.

## Design goals

- Single format for input and output: the caller reads the result the same way it wrote the input.
- Suitable for large models: 10⁵ × 10⁵ matrices with millions of non-zeros load in milliseconds.
- No external dependencies: plain binary, no compression, no serialisation library.
- Self-describing dimensions: a reader can allocate correctly before parsing any entries.

## File structure

All multi-byte integers are **little-endian**.

### Header (16 bytes)

| Offset | Size | Type   | Description                        |
|--------|------|--------|------------------------------------|
| 0      | 4    | char[4]| Magic: `K` `E` `R` `S` (0x4B 0x45 0x52 0x53) |
| 4      | 1    | uint8  | Version: `1`                       |
| 5      | 1    | uint8  | Flags: reserved, must be `0`       |
| 6      | 4    | uint32 | `nrows` — number of rows           |
| 10     | 4    | uint32 | `ncols` — number of columns        |
| 14     | 2    | —      | Padding (zero)                     |

### Body — column entries

Only non-empty columns are written, in **ascending column order**.

For each non-empty column:

| Size | Type   | Description                              |
|------|--------|------------------------------------------|
| 4    | uint32 | Column index (0-based)                   |
| 4    | uint32 | `nnz` — number of non-zero entries in this column |
| 4×nnz | uint32 | Row index (0-based, repeated nnz times as `(row, value)` pairs — see below) |

Each entry is a `(row_index, value)` pair:

| Size | Type   | Description       |
|------|--------|-------------------|
| 4    | uint32 | Row index (0-based)|
| 8    | int64  | Value (signed)    |

Row indices within a column are **sorted in ascending order**.
This allows readers to use `append()` (amortised O(1)) rather than `put()` (O(log n)).

### Terminator

After the last column, a 4-byte sentinel marks end of data:

| Size | Type   | Value        |
|------|--------|--------------|
| 4    | uint32 | `0xFFFFFFFF` |

## Semantic conventions

### Input matrix (incidence matrix)

When used as input to PetriSpot via `--loadKERS`:

- `nrows` = number of **variables** (places for P-flows, transitions for T-flows)
- `ncols` = number of **constraints** (transitions for P-flows, places for T-flows)
- The matrix is the **incidence matrix** C = flowTP − flowPT
- For T-flows/T-semiflows, pass the same C; PetriSpot transposes internally

### Output matrix (invariant basis)

When written by PetriSpot via `--basisKERS`:

- `nrows` = number of variables (same as input)
- `ncols` = number of basis vectors (invariants)
- Each column is one basis vector: a sparse integer vector in the null space of C

No constant terms are stored (constants depend on the initial marking and are not part of the basis).

## Usage with PetriSpot

```
# Export incidence matrix of a Petri net in KERS format
petri64 -i model.pnml --exportAsKERS=model.kers

# Compute P-semiflows from a KERS matrix, export result as KERS
petri64 --loadKERS=model.kers --Psemiflows --basisKERS=basis.kers

# Equivalent: compute directly from PNML, export basis
petri64 -i model.pnml --Psemiflows --basisKERS=basis.kers
```

## kersconv — ASCII conversion utility

`kersconv` is a small companion tool for inspecting and constructing KERS files without PetriSpot.
It is built alongside the `petri*` binaries by the autoconf/automake build system.

### Usage

```
kersconv --decode <input.kers> [output.txt]   # KERS binary → ASCII (stdout if no output file)
kersconv --encode <input.txt>  <output.kers>  # ASCII → KERS binary
```

### ASCII format

The text representation is the same as `--exportAsMatrix`:

```
<nrows> <ncols>
<rowIdx> <colIdx>:<val> <colIdx>:<val> ...
...
```

- First line: matrix dimensions.
- Each subsequent line encodes one **non-empty row**: the row index followed by space-separated `colIdx:value` pairs.
- Empty rows are omitted.
- Values are signed integers.

### Example

```sh
# Inspect a basis file
kersconv --decode basis.kers

# Roundtrip: decode then re-encode
kersconv --decode basis.kers basis.txt
kersconv --encode basis.txt basis2.kers
```

### Consistency with PNML-based output

When comparing `--loadKERS` output to direct PNML computation, the invariant **coefficients and
count are identical**. The following differences are expected and by design:

| Property | PNML path | KERS path |
|---|---|---|
| Variable names | Real place/transition names from PNML | `x0`, `x1`, … (no name table in binary format) |
| RHS constant | Dot product with initial marking (e.g., `= 1`) | Always `0` (no marking stored in KERS) |
| Log lines | Includes parsing and model info | Only computation lines |
| "Computed" line | `Computed N P flows in T ms.` | `Computed N P flows in T ms.` (same) |

For pure **flows** (as opposed to semiflows), `= 0` on the RHS is mathematically correct regardless.
For **semiflows**, the PNML path annotates each invariant with the conserved quantity value
(the invariant dotted with the initial marking), which is unavailable in the KERS path.

## Size estimate

For a matrix with `N` non-zero entries:
- Header: 16 bytes
- Per non-empty column header: 8 bytes
- Per entry: 12 bytes (4 row + 8 value)
- Terminator: 4 bytes

Total ≈ 16 + 8×ncols_nonempty + 12×N bytes.

For N = 10⁶ entries: approximately **12 MB**.

## Overflow behaviour

PetriSpot ships three binaries: `petri32` (int), `petri64` (long), `petri128` (long long).
All three read and write values as `int64` in the file.
When reading into a narrower type (e.g. `petri32`), values outside `[-2³¹, 2³¹-1]` trigger
a warning on stderr and are truncated. Use `petri64` or `petri128` for large coefficients.
