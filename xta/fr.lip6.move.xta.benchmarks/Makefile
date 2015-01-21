
# Please edit these configuration variables

# This sets up max memory 4 GB (4 million KB) of RAM
MAXMEM=4000000
# This sets up maximum time as 10 minutes (600 seconds)
MAXTIME=600

# give absolute path to its-reach
ITSREACH := /data/ythierry/download/ITS_linux_64/its-reach

# Give absolute path to Uppaal if it is not on path
UPPAAL := /data/ythierry/download/uppaal64-4.1.16/bin-Linux/verifyta 
# Add these flags to get state counts, but perl scripts don't support it yet.
# -u -t 0

# give path to memtime, see http://www.update.uu.se/~johanb/memtime/
# this tool is part of Uppaal. 
# we actually recommend this version for 64 bit, patched to support more than 4Go of ram
# http://tiger.cs.tsinghua.edu.cn/Students/yangjl/memtime/
MEMTIMEPATH=/data/ythierry/download/memtime-1.3/memtime 


################################### END of user configuration variables ############

export MEMTIMECMD := ${MEMTIMEPATH} -m ${MAXMEM} -c ${MAXTIME} 


export UPPAALCMD := ${MEMTIMECMD} ${UPPAAL}


export ITSREACHCMD := ${MEMTIMECMD} ${ITSREACH} -t GAL --quiet -i


# this target generates all input XTA files.
gen: CSMA/gen.sh fischer/gen.sh hddi/gen.sh
	cd CSMA; ./gen.sh; cd ..
	cd fischer; ./gen.sh; cd ..
	cd hddi; ./gen.sh; cd ..

perfs: 
	cd CSMA; ./runUppaal.sh; ./runITS.sh ; cd ..
	cd fischer; ./runUppaal.sh; ./runITS.sh ; cd ..
	cd hddi; ./runUppaal.sh; ./runITS.sh ; cd ..

trace.csv :
	cp CSMA/upptrace.out . ; 
	cat fischer/upptrace.out >> upptrace.out ; 
	cat hddi/upptrace.out >> upptrace.out;
	cp CSMA/trace.out  . ;
	cat fischer/trace.out >> trace.out ; 
	cat hddi/trace.out >> trace.out;
	traceAnalysis/fusetraces.pl > trace.csv
	traceAnalysis/fuseUpp.pl >> trace.csv
		
stats : trace.csv
	cp trace.csv traceAnalysis/fusedTrace.out
	cd traceAnalysis ; ./sum_all.sh ; cd .. ;

plots : trace.csv
	cp trace.csv traceAnalysis/fusedTrace.out
	cd traceAnalysis ; ./plot_all.sh ; cd .. ;
	cp traceAnalysis/*.eps .
	
		 	
cleangal:
	rm -f **/*.gal

clean:
	rm -f **/*.q **/*.xta **/*.gal **/*.reach
