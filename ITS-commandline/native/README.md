# `native/` — ITS-Tools as one executable

`build-native.sh` compiles the command-line product ahead of time with GraalVM
`native-image`, over the same flat class path as `its-tools-flat.sh`. The result
is one file (about 80 MB) that starts in 10 ms where the Eclipse launcher takes
0.3 s warm and 1 s cold, and runs the Java side at JIT speed or a little better
(no warm-up); the external engines are unchanged. It needs the product's
`plugins/` folder beside it for the binaries (put the executable in the
unzipped product, next to `its-tools`); `-Dfr.lip6.binaries.root=DIR` names
another `plugins/` folder.

`config/reachability-metadata.json` is the closed world: every class reached by
reflection, every resource and proxy, as the tracing agent recorded them over
AirplaneLD PT-0010 (OneSafe, deadlock, LTLC, UB) and COL-0010 (LTLF, CTLF, RC).
`trace-agent.sh` adds runs to it. A miss at run time is a `ClassNotFoundException`,
`NoSuchMethodException` or `MissingReflectionRegistrationError` naming the
element (a missing resource, a null a few frames later): trace that run, rebuild.

Measured on AirplaneLD (2026-09-07, GraalVM 25.0.4, default march):
OneSafe 10 ms against 240 ms flat JVM; LTLC 8.4 s against 9.3 s; CTLC 4.5 s
against 5.1 s; RF and UB equal (external engines); all verdicts identical.
