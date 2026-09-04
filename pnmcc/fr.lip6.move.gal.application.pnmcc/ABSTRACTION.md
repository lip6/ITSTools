# Place projection: over-approximation by dropping constraints

Status: design and first implementation (2026-09-04), used by `UpperBoundsSolver`
only. Package `fr.lip6.move.gal.application.solver.abstraction`.

## Idea

Removing a place from a P/T net removes its arcs (read arcs included). Every
firing sequence of the original net is still fireable and the effects on the
remaining places are unchanged, so the reachable markings of the kept places
of the projection are a superset of the projections of the reachable markings
of the original. A projection is therefore a sound over-approximation for:

* `AG psi` with psi over kept places: true on the projection, true originally;
* `EF phi`: false on the projection, false originally;
* an upper bound: the maximum of a kept place on the projection bounds it
  originally;
* it says nothing about deadlocks (removing constraints removes deadlocks).

It is the same move as the existing "discard test arcs" fallback (remove
constraints), pushed to whole places, and it is complementary to the SMT
state-equation over-approximation: the state equation keeps every place but
forgets the ordering of firings; the projection forgets places but is exact
on the ordering among the kept ones. Bounds and invariants that come from
control flow gating data (one-hot counters unfolded from colours, phase
controllers, capacity places refilled per phase) are where the state equation
answers `?` and a small projection answers exactly. BridgeAndVehicles
(V80P50N10): the semi-flow gives 50 for the bridge, the walk reaches 10; the
projection on capacity, bridge, phase and counter places (20 of 188 places)
has 264 states and proves 10.

## Boundedness

A projection can be unbounded (a place whose producers lost all their inputs
pumps tokens), and an unbounded abstraction is useless to an exhaustive
engine. Guard: the kept set is a union of supports of P-semi-flows of the
original. A semi-flow whose support is entirely kept is a semi-flow of the
projection, so every kept place is covered and the projection is structurally
bounded. This also prices a refinement: a semi-flow of constant c on k places
admits at most C(c+k-1, k-1) markings, so 1-safe control cycles and one-hot
counters cost a factor of a few units while a conservation law over 80
vehicles is expensive and comes last.

Alternative guard, not implemented: when a bound b of a kept place is known
(from invariants or an earlier verdict) but no kept semi-flow covers it, add a
complement place with b tokens consumed by its producers and refilled by its
consumers. Sound (every concrete run respects b) and less restrictive than
keeping the original admission control.

## The CEGAR loop (`ProjectionCegar`)

1. Kept set: the cheapest semi-flows covering the places of the property.
   A target place covered by no semi-flow stops the method (potentially
   unbounded).
2. Build the projection (`PlaceProjection`): kept places renumbered,
   transitions restricted to kept places, empty transitions dropped,
   identical transitions merged; the map from abstract transitions to their
   concrete members is kept for replay.
3. Check the property on the projection (`AbstractChecker`, supplied by the
   caller): PROVED ends the loop with a sound verdict; UNKNOWN ends it without
   one.
4. REFUTED: the checker may return an abstract witness trace (the PetriSpot
   walker with `--trace` on the small net is the cheap source). Replay it on
   the concrete net, trying every concrete member of each abstract transition:
   a trace that replays to the end is a real refutation (for bounds, a real
   marking: the lower bound improves); a blocked trace names the dropped
   places that were missing, and the cheapest semi-flows covering them are
   added. Without a trace, add the cheapest semi-flow sharing a transition
   with the kept set.
5. Stop when the kept set is the whole net or the budget is spent.

## Bounds (`ProjectionBoundsSolver`)

For an open bound with `maxSeen < maxStruct`, the question is the invariant
`AG body <= maxSeen`. Checker: the exhaustive engine (`verifyWithSDD`, when
`-its` is on) on a copy of the translator carrying the projected net and the
translated property; on FALSE, the PetriSpot walker looks for
`body > maxSeen` on the projection with a trace. A real refutation raises
`maxSeen` and the loop continues; a proof closes the property with
`maxSeen` as the bound. Runs after the reduction and walk iterations and
before `testWithReachability`, on the residual properties only.

## Extension

`ProjectionCegar` is independent of the property kind: a reachability
`AG`/`EF` checker plugs in unchanged, and an LTL checker would too as long as
the atoms are over kept places (projection preserves the traces' projections,
so LTL over kept places is over-approximated in the same direction). Only the
checker and the witness source are specific.
