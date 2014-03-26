


# This sets up max memory 4 GB of RAM
MAXMEM=4000000
# This sets up maximum time as 10 minutes (600 seconds)
MAXTIME=600

# give path to memtime, see http://www.update.uu.se/~johanb/memtime/
# this tool is part of Uppaal. 
# we actually recommend this version for 64 bit, patched to support more than 4Go of ram
# http://tiger.cs.tsinghua.edu.cn/Students/yangjl/memtime/
MEMTIMEPATH=/data/ythierry/download/memtime-1.3/memtime 

MEMTIMECMD=$MEMTIMEPATH -m $MAXMEM -s $MAXTIME 

# Give absolute path to Uppaal if it is not on path
UPPAAL=/data/ythierry/download/uppaal64-4.1.16/bin-Linux/verifyta

# give absolute path to its-reach
ITSREACH=/data/ythierry/download/ITS_linux_64/its-reach


# this target generates all input XTA files.
gen: CSMA/gen.sh fischer/gen.sh hddi/gen.sh
	cd CSMA; ./gen.sh; cd ..
	cd fischer; ./gen.sh; cd ..
	cd hddi; ./gen.sh; cd..



cleangal:
	rm -f **/*.gal

clean:
	rm -f **/*.q **/*.xta **/*.gal
