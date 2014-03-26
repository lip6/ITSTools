You need Uppaal, ITS-tools and ITS-Modeler Eclipse plugins (see ddd.lip6.fr) to reproduce.
The scripts are meant for running in a bash environment, so no windows machine 
unless you also have bash (mingW, cygwin...).
The scripts contain some paths that should be edited to point to your binaries.
We measure time/memory usage by sampling with tool memtime 1.3 from Uppaal toolset.
Some of the post-analysis scripts require gnuplot and other utilities,
 but these are not essential.

Steps to reproduce :
1. Run gen.sh in each example folder to produce the .XTA
2. In Eclipse, create a project, import the example folder
3. Select the folder containing the XTA models then right-click and select 'TA to GAL-> Transform to GAL (essential states)' 
4. Do the same but select 'Transform to GAL (time unit step)'
Archive contains results of these steps already, i.e. 4 GAL models per input XTA.

To collect statistics we used scripts runITS.sh and runUppaal.sh located in each folder.
Basically it runs its-reach on each *.flat.gal model and verifyta on each XTA model.

The raw traces obtained are in traceAnalysis/ (trace.out and upptrace.out).
Use fusetraces.pl to preanalyze traces from ITS-tools, and fuseUpp.pl to preanalyze traces of Uppaal.
The resulting files can be merged, they are homogeneous in columns etc...

The file fusedTrace.out is the result for this set of data.

The scripts used to post treat the traces, plot them, and the resulting CSV or excel files are there too.
The data we put in the paper is highlighted in the excel version.

