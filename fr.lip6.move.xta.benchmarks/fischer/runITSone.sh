#! /bin/bash

echo `date` >> trace.out
for j in  2 4 8 16 ; do for i in *input_*$j.one.flat.gal ; do (/data/ythierry/download/memtime-1.3/memtime -m 4000000 -c 600 /data/ythierry/DDD/libits/bin/its-reach -i $i -t GAL  --quiet) >> trace.out 2>> trace.out ; done ; done ;
