# `fr.lip6.move.gal.interop` — tool-to-tool formats

The formats ITS-Tools shares with its native companions, PetriSpot
(`petrispot/`) and libHSC (`hsc/`), as specified in PetriSpot's `KERS.md`
and `INTEROP.md` (sections 3 to 5):

* `KERSFormatIO` — sparse integer matrices, both directions.
* `PNETFormatIO` — a P/T net as three KERS blocks (pre, post, initial
  marking); places and transitions are identified by index.
* `SexprPropertyPrinter` — properties and CTL formulas as s-expression forms
  over indices (`reach`, `invariant`, `deadlock`, `bound`, `ctl`).

Runners (`PetriSpotWalker`, `HscRunner`) write these files and read the
`FORMULA` line protocol back; this plugin only depends on the structural
model.
