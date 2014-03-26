#! /bin/bash

jlist="0 1 10 50"

echo `date` >> trace.out
for j in  $jlist ;  do for i in hddi_input_*_*_$j.pop.flat.gal ; do (/data/ythierry/download/memtime-1.3/memtime -m 4000000 -c 600 /data/ythierry/DDD/libits/bin/its-reach -i $i -t GAL --gen-order FOLLOW --quiet ) >> trace.out 2>> trace.out ; done ; done ;
