# ITS-Tools

A model checker for Petri nets and for its own language GAL: structural
reductions, SMT over the state equation, symbolic decision diagrams (ITS),
partial-order explicit engines (LTSmin), LTL through Spot, random and guided
walks (PetriSpot), assembled per property class. It runs in the
[Model Checking Contest](https://mcc.lip6.fr) every year.

Project homepage: <https://lip6.github.io/ITSTools-web/>

## Getting it

* **In Eclipse**, with editors for GAL and the graphical integration:
  Help, Install New Software, add the update site
  <https://lip6.github.io/ITSTools/>.
* **Command line**, for scripts and pipelines: download the archive for
  your platform (Linux, Windows, macOS) from <https://lip6.github.io/ITSTools/>,
  unzip, run `its-tools` (`eclipsec` on Windows). Java 21 or later is required.
  That page documents the arguments: `-pnfolder DIR -examination NAME` for a
  contest instance, `-i model.gal -reach|-ctl|-ltl` for a GAL model.
* **Contest packaging**: the driver scripts and installation used in the
  contest are in <https://github.com/yanntm/ITS-Tools-MCC>.

The Linux archive also carries `its-tools-flat.sh`, the same classes on a
plain class path without the Eclipse framework, and the site publishes
`its-tools-native`, a GraalVM native image built for the contest driver
(see `ITS-commandline/native/README.md`). The image is experimental: a run it
was not built for may fail with an exception naming a class; report it with
the command line, and use `its-tools` from the same folder meanwhile.

## Building

Maven with Tycho, from `fr.lip6.move.gal.parent`:

    cd fr.lip6.move.gal.parent && mvn install

builds every plugin, the update site (`fr.lip6.move.gal.updatesite`) and the
command-line products (`ITS-commandline/fr.lip6.move.gal.itscl.product`).
The CI (`.github/workflows/build.yml`) does this on every push to master and
publishes the site, the products and the native image to the `gh-pages`
branch. Java 21 is the minimum for the code; the CI builds with a recent JDK.

## Reporting issues

<https://github.com/lip6/ITSTools/issues>, with the command line, the model
when it can be shared, and the output.

## Licence

ITS-Tools is distributed under the terms of the GPL v3.

LIP6, CNRS & Sorbonne Université.
