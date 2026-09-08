# `fr.lip6.move.hsc.runner` — the libHSC companion

`HscRunner` hands a reduced net and its open properties to `hsc-pn` (libHSC,
`tools/README.md` there) on the tool-to-tool protocol of PetriSpot's
`INTEROP.md`: the net as PNET (`fr.lip6.move.gal.interop.PNETFormatIO`), the
properties as s-expression forms (`SexprPropertyPrinter`), `FORMULA` lines
read back as they are printed. Unlike the walker, every verdict is a proof:
`TRUE` and `FALSE` are both final, a bound value is exact.

The binary comes from `fr.lip6.hsc.binaries` (downloaded from the libHSC CI
at build time), or from `-Dhsc.bin=<path>` outside OSGi. `HscRunner.DEBUG`
(1 keeps the exchanged files, 2 echoes the binary's output) is the switch
when a call has to be seen.
